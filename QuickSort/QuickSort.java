package QuickSort;

import selectionSort.SortingAlgorithm; // importei a interface SortingAlgorithm do pacote selectionSort //
import java.util.Random;

public class QuickSort implements SortingAlgorithm {

    private long comparisons;
    private long swaps;
    private long executionTime; // em ns
    private final Random random = new Random(42); // seed fixa p/ reprodutibilidade

    @Override
    public void sort(int[] array) {
        comparisons = 0;
        swaps = 0;

        long start = System.nanoTime();
        if (array != null && array.length > 1) {
            quickSort(array, 0, array.length - 1);
        }
        executionTime = System.nanoTime() - start;
    }

    private void quickSort(int[] a, int low, int high) {
        // Recursão só na partição menor; a maior vira laço.
        // Garante profundidade O(log n) e evita StackOverflowError.
        while (low < high) {
            int p = partition(a, low, high);
            if (p - low < high - p) {
                quickSort(a, low, p - 1);
                low = p + 1;
            } else {
                quickSort(a, p + 1, high);
                high = p - 1;
            }
        }
    }

    private int partition(int[] a, int low, int high) {
        // Pivô aleatório: evita o pior caso O(n²) em entradas ordenadas/inversas
        int r = low + random.nextInt(high - low + 1);
        swap(a, r, high);

        int pivot = a[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;
            if (a[j] <= pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, high);
        return i + 1;
    }

    private void swap(int[] a, int i, int j) {
        if (i == j)
            return; // não conta troca de um elemento com ele mesmo
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        swaps++;
    }

    @Override
    public long getComparisons() {
        return comparisons;
    }

    @Override
    public long getSwaps() {
        return swaps;
    }

    @Override
    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i])
                return false;
        }
        return true;
    }
}
