public class QuickSort extends BaseSort {
    @Override
    public void sort(int[] a) {
        reset();
        long start = System.nanoTime();
        quickSort(a, 0, a.length - 1);
        executionTime = System.nanoTime() - start;
    }

    private void quickSort(int[] a, int lo, int hi) {
        while (lo < hi) {
            int p = partition(a, lo, hi);
            // recursão na menor metade, laço na maior: pilha O(log n)
            if (p - lo < hi - p) {
                quickSort(a, lo, p - 1);
                lo = p + 1;
            } else {
                quickSort(a, p + 1, hi);
                hi = p - 1;
            }
        }
    }

    private int partition(int[] a, int lo, int hi) {
        // mediana de três
        int mid = lo + (hi - lo) / 2;
        if (cmp(a[mid], a[lo]) < 0) swap(a, mid, lo);
        if (cmp(a[hi], a[lo]) < 0)  swap(a, hi, lo);
        if (cmp(a[hi], a[mid]) < 0) swap(a, hi, mid);
        swap(a, mid, hi); // pivô vai para o fim
        int pivot = a[hi];

        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (cmp(a[j], pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, hi);
        return i + 1;
    }

    private int cmp(int x, int y) { comparisons++; return Integer.compare(x, y); }

    private void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        swaps++;
    }
}