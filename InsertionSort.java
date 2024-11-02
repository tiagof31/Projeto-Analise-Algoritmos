import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class InsertionSort {
    public static void insertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] sizes = {100, 10000, 100000}; // Tamanhos dos arrays para teste
        int numSamples = 5; // Número de amostras por tamanho

        try (FileWriter csvWriter = new FileWriter("serial_insertion_sort_results.csv")) {
            csvWriter.append("Tamanho do Array,Amostra,Tempo de Execucao (ms)\n");

            for (int size : sizes) {
                System.out.println("\nTamanho do Array: " + size);
                long totalTime = 0;

                for (int sample = 0; sample < numSamples; sample++) {
                    int[] array = generateRandomArray(size);

                    long startTime = System.currentTimeMillis();
                    insertionSort(array);
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