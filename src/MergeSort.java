public class MergeSort extends BaseSort {
    @Override
    public void sort(int[] a) {
        reset();
        long start = System.nanoTime();
        mergeSort(a, 0, a.length - 1);
        executionTime = System.nanoTime() - start;
    }

    private void mergeSort(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;
        mergeSort(a, left, middle);
        mergeSort(a, middle + 1, right);
        merge(a, left, middle, right);
    }

    private void merge(int[] a, int left, int middle, int right) {
        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = a[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = a[middle + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            comparisons++;
            if (leftArray[i] <= rightArray[j]) {
                a[k] = leftArray[i];
                swaps++;
                i++;
            } else {
                a[k] = rightArray[j];
                swaps++;
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            a[k] = leftArray[i];
            swaps++;
            i++;
            k++;
        }

        while (j < rightSize) {
            a[k] = rightArray[j];
            swaps++;
            j++;
            k++;
        }
    }
}
