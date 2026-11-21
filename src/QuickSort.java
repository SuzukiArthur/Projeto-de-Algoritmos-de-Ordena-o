/**
 * Quick Sort com pivô por mediana de três e particionamento de Lomuto.
 * Contabiliza comparações, trocas e tempo de execução.
 * 
 * @author Luiz Miguel Mazzega Lamas
 * @version 1.0
 * @since 2026
 */
public class QuickSort extends BaseSort {

    /**
     * Ordena o vetor em ordem crescente e registra as métricas.
     * 
     * @param a O vetor a ser ordenado
     */

    @Override
    public void sort(int[] a) {
        reset();
        long start = System.nanoTime();
        quickSort(a, 0, a.length - 1);
        executionTime = System.nanoTime() - start;
    }

    /**
     * Ordena recursivamente o subvetor {@code a[lo..hi]}.
     * 
     * @param a  O vetor
     * @param lo O índice inicial
     * @param hi O índice final
     */

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

    /**
     * Particiona o subvetor {@code a[lo..hi]} em torno do pivô.
     * 
     * @param a  O vetor
     * @param lo O índice inicial
     * @param hi O índice final
     * @return A posição final do pivô
     */

    private int partition(int[] a, int lo, int hi) {
        // mediana de três
        int mid = lo + (hi - lo) / 2;
        if (cmp(a[mid], a[lo]) < 0)
            swap(a, mid, lo);
        if (cmp(a[hi], a[lo]) < 0)
            swap(a, hi, lo);
        if (cmp(a[hi], a[mid]) < 0)
            swap(a, hi, mid);
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

    /**
     * Compara dois inteiros e conta a comparação.
     * 
     * @param x O primeiro valor
     * @param y O segundo valor
     * @return Negativo, zero ou positivo conforme a ordem de x e y
     */

    private int cmp(int x, int y) {
        comparisons++;
        return Integer.compare(x, y);
    }

    /**
     * Troca dois elementos do vetor e conta a troca.
     * 
     * @param a O vetor
     * @param i O índice do primeiro elemento
     * @param j O índice do segundo elemento
     */
    
    private void swap(int[] a, int i, int j) {
        if (i == j)
            return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        swaps++;
    }
}