package selectionSort;
public interface SortingAlgorithm {

    void sort(int[] vetor);

    long getComparisons();

    long getSwaps();

    long getExecutionTime();

    boolean isSorted(int[] vetor);
}