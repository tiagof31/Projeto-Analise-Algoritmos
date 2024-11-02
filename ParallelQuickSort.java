import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends RecursiveAction {
    private int[] array;
    private int low, high;

    public ParallelQuickSort(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            invokeAll(new ParallelQuickSort(array, low, pivotIndex - 1),
                      new ParallelQuickSort(array, pivotIndex + 1, high));
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }

    public static void main(String[] args) {
        int[] sizes = {100, 10000, 100000}; // Tamanhos dos arrays para teste
        int numSamples = 5; // Número de amostras por tamanho
        int maxThreads = Runtime.getRuntime().availableProcessors(); // Número máximo de threads disponíveis
        int[] threadCounts = {2, 4, maxThreads}; // Diferentes números de threads para o Quick Sort Paralelo
        int[] extra = {2, 4, 8, maxThreads}; // Caso tenha uma disponibilidade maior de threads
        if (maxThreads > 8) {
            threadCounts = extra;
        }

        try (FileWriter csvWriter = new FileWriter("parallel_quick_sort_results.csv")) {
            csvWriter.append("Numero de Threads,Tamanho do Array,Amostra,Tempo de Execucao (ms)\n");

            for (int numThreads : threadCounts) {
                ForkJoinPool pool = new ForkJoinPool(numThreads);
                for (int size : sizes) {
                    long totalTime = 0;

                    for (int sample = 0; sample < numSamples; sample++) {
                        int[] array = generateRandomArray(size);

                        ParallelQuickSort sortTask = new ParallelQuickSort(array, 0, array.length - 1);

                        long startTime = System.currentTimeMillis();
                        pool.invoke(sortTask);
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

    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(10000); // Gera números aleatórios entre 0 e 9999
        }
        return array;
    }
}