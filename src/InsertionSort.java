public class InsertionSort extends BaseSort {
    @Override
    public void sort(int[] a) {
        reset();
        long start = System.nanoTime();

        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= 0) {
                comparisons++;
                if (a[j] <= key) {
                    break;
                }
                a[j + 1] = a[j];
                swaps++;
                j--;
            }
            a[j + 1] = key;
        }

        executionTime = System.nanoTime() - start;
    }
}
