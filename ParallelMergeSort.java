import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends RecursiveAction {
    private int[] array;
    private int left, right;

    public ParallelMergeSort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int middle = (left + right) / 2;
            invokeAll(new ParallelMergeSort(array, left, middle),
                      new ParallelMergeSort(array, middle + 1, right));
            merge(array, left, middle, right);
        }
    }

    private void merge(int[] array, int left, int middle, int right) {
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
        int maxThreads = Runtime.getRuntime().availableProcessors(); // Número máximo de threads disponíveis
        int[] threadCounts = {2, 4, maxThreads}; // Diferentes números de threads para o Merge Sort Paralelo
        int[] extra = {2, 4, 8, maxThreads}; // Caso tenha uma disponibilidade maior de threads
        if (maxThreads > 8) {
            threadCounts = extra;
        }

        try (FileWriter csvWriter = new FileWriter("parallel_merge_sort_results.csv")) {
            csvWriter.append("Numero de Threads,Tamanho do Array,Amostra,Tempo de Execucao (ms)\n");

            for (int numThreads : threadCounts) {
                ForkJoinPool pool = new ForkJoinPool(numThreads);
                for (int size : sizes) {
                    long totalTime = 0;

                    for (int sample = 0; sample < numSamples; sample++) {
                        int[] array = generateRandomArray(size);

                        ParallelMergeSort sortTask = new ParallelMergeSort(array, 0, array.length - 1);

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
