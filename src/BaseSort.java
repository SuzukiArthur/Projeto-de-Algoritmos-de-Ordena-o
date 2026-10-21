public abstract class BaseSort implements SortingAlgorithm {
    protected long comparisons;
    protected long swaps;
    protected long executionTime; // em nanossegundos

    protected void reset() {
        comparisons = 0;
        swaps = 0;
        executionTime = 0;
    }

    @Override public long getComparisons()   { return comparisons; }
    @Override public long getSwaps()         { return swaps; }
    @Override public long getExecutionTime() { return executionTime; }

    @Override
    public boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) return false;
        }
        return true;
    }
}