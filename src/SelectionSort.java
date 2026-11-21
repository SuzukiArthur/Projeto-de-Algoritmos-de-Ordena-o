
/**
 * Selection Sort
 * Encontra o menor elemento da parte não ordenada do vetor
 * e o coloca na posição correta.
 * Contabiliza comparações, trocas e tempo de execução.
 *
 * @author Arthur Yuji Mendes Suzuki
 */
public class SelectionSort extends BaseSort {

    /**
     * Ordena o vetor em ordem crescente e registra as métricas.
     * @param a O vetor a ser ordenado
     */
    @Override
    public void sort(int[] a) {
        reset();
        long start = System.nanoTime();
        int n = a.length;

        for (int i = 0; i < n - 1; i++) {
            int min = i;

            // Procura o menor elemento da parte não ordenada
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (a[j] < a[min]) min = j;
            }

            // Coloca o menor elemento na posição correta
            if (min != i) {
                int t = a[i];
                a[i] = a[min];
                a[min] = t;
                swaps++;
            }
        }

        executionTime = System.nanoTime() - start;
    }
}
