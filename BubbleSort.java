import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BubbleSort {
    public static void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Troca os elementos
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }

            // Se não houve troca, o array já está ordenado
            if (!swapped) break;
        }
    }

    public static void main(String[] args) {
        int[] sizes = {100, 10000, 100000}; // Tamanhos dos arrays para teste
        int numSamples = 5; // Número de amostras por tamanho

        try (FileWriter csvWriter = new FileWriter("serial_bubble_sort_results.csv")) {
            csvWriter.append("Tamanho do Array,Amostra,Tempo de Execucao (ms)\n");

            for (int size : sizes) {
                System.out.println("\nTamanho do Array: " + size);
                long totalTime = 0;

                for (int sample = 0; sample < numSamples; sample++) {
                    int[] array = generateRandomArray(size);

                    long startTime = System.currentTimeMillis();
                    bubbleSort(array);
                    long endTime = System.currentTimeMillis();

                    long elapsedTime = endTime - startTime;
                    totalTime += elapsedTime;
                    System.out.println("Amostra " + (sample + 1) + " - Tempo de execução: " + elapsedTime + " ms");

                    // Escrevendo os resultados no arquivo CSV
                    csvWriter.append(size + "," + (sample + 1) + "," + elapsedTime + "\n");
                }

                long averageTime = totalTime / numSamples;
                System.out.println("Tempo médio de execução para tamanho " + size + ": " + averageTime + " ms");
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

    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}