public class SelectionSort extends BaseSort {

    @Override
    
    public void sort(int[] a) {
        reset();
        long start = System.nanoTime();
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (a[j] < a[min]) min = j;
            }
            if (min != i) {
                int t = a[i]; a[i] = a[min]; a[min] = t;
                swaps++;
            }
        }
        executionTime = System.nanoTime() - start;
    }
}