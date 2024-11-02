import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MergeSort {
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    private static void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, middle + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] sizes = {100, 10000, 100000}; // Tamanhos dos arrays para teste
        int numSamples = 5; // Número de amostras por tamanho

        try (FileWriter csvWriter = new FileWriter("serial_merge_sort_results.csv")) {
            csvWriter.append("Tamanho do Array,Amostra,Tempo de Execucao (ms)\n");

            for (int size : sizes) {
                System.out.println("\nTamanho do Array: " + size);
                long totalTime = 0;

                for (int sample = 0; sample < numSamples; sample++) {
                    int[] array = generateRandomArray(size);

                    long startTime = System.currentTimeMillis();
                    mergeSort(array, 0, array.length - 1);
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