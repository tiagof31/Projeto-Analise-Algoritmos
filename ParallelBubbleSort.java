import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelBubbleSort {
    public static void main(String[] args) {
        int[] sizes = {100, 10000, 100000}; // Tamanhos dos arrays para teste
        int numSamples = 5; // Número de amostras por tamanho
        int maxThreads = Runtime.getRuntime().availableProcessors(); // Número máximo de threads disponíveis
        int[] threadCounts = {2, 4, maxThreads}; // Diferentes números de threads para o Bubble Sort Paralelo
        int[] extra = {2, 4, 8, maxThreads}; // Caso tenha uma disponibilidade maior de threads
        if (maxThreads > 8) {
            threadCounts = extra;
        }

        try (FileWriter csvWriter = new FileWriter("parallel_bubble_sort_results.csv")) {
            csvWriter.append("Numero de Threads,Tamanho do Array,Amostra,Tempo de Execucao (ms)\n");

            for (int numThreads : threadCounts) {
                for (int size : sizes) {
                    long totalTime = 0;

                    for (int sample = 0; sample < numSamples; sample++) {
                        int[] array = generateRandomArray(size);

                        long startTime = System.currentTimeMillis();
                        int [] x = parallelBubbleSort(array, numThreads); // Guardar caso necessário
                        long endTime = System.currentTimeMillis();

                        long elapsedTime = endTime - startTime;
                        totalTime += elapsedTime;
                        System.out.println("Número de Threads: " + numThreads + ", Tamanho do Array: " + size + ", Amostra " + (sample + 1) + " - Tempo de execução: " + elapsedTime + " ms");

                        // Escrevendo os resultados no arquivo CSV
                        csvWriter.append(numThreads + "," + size + "," + (sample + 1) + "," + elapsedTime + "\n");
                    }

                    long averageTime = totalTime / numSamples;
                    System.out.println("Tempo médio de execução para tamanho " + size + " com " + numThreads + " threads: " + averageTime + " ms");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] parallelBubbleSort(int[] array, int numThreads) {
        int n = array.length;
        int chunkSize = (n + numThreads - 1) / numThreads; // Divisão aproximada do array

        // ExecutorService para gerenciar threads
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int[][] subArrays = new int[numThreads][];

        // Dividindo o array em sub-arrays
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, n);

            // Evita criar subarrays vazios se não houver mais elementos para dividir
            if (start < end) {
                subArrays[i] = Arrays.copyOfRange(array, start, end);
                int[] subArray = subArrays[i];
                executor.submit(() -> {
                    // System.out.println("Thread " + Thread.currentThread().getName() + " começou a ordenar.");
                    bubbleSort(subArray);
                    // System.out.println("Thread " + Thread.currentThread().getName() + " terminou de ordenar.");
                });
            }
        }

        // Esperar que todas as threads terminem a ordenação
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fusão das sub-listas ordenadas
        return mergeSubArrays(array, subArrays);
    }

    // Método para ordenar cada sub-lista usando Bubble Sort
    public static int[] bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Trocar os elementos
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return array;
    }

    // Método para fundir as sub-listas ordenadas em uma única lista
    public static int[] mergeSubArrays(int[] array, int[][] subArrays) {
        int index = 0;

        // Implementação simples: copiar as sub-listas ordenadas de volta para o array principal
        for (int[] subArray : subArrays) {
            if (subArray != null) {
                for (int value : subArray) {
                    array[index++] = value;
                }
            }
        }

        // Realizar uma ordenação final no array para garantir a ordenação completa
        return bubbleSort(array);
    }

    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(10000); // Gera números aleatórios entre 0 e 9999
        }
        return array;
    }

    // Método para imprimir o array
    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}