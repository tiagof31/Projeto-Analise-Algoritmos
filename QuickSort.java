import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class QuickSort {
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
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

        try (FileWriter csvWriter = new FileWriter("serial_quick_sort_results.csv")) {
            csvWriter.append("Tamanho do Array,Amostra,Tempo de Execucao (ms)\n");

            for (int size : sizes) {
                System.out.println("\nTamanho do Array: " + size);
                long totalTime = 0;

                for (int sample = 0; sample < numSamples; sample++) {
                    int[] array = generateRandomArray(size);

                    long startTime = System.currentTimeMillis();
                    quickSort(array, 0, array.length - 1);
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