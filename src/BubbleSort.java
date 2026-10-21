public class BubbleSort extends BaseSort {
    @Override
    public void sort(int[] a) {
        reset();
        long start = System.nanoTime();
        int n = a.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }

        executionTime = System.nanoTime() - start;
    }
}
