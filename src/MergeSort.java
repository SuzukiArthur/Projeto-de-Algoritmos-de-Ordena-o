/**
 * Merge Sort recursivo (top-down) com vetores auxiliares na intercalação.
 * Contabiliza comparações, trocas (escritas no vetor) e tempo de execução.
 *
 * @autor Alice Martins Miranda
 */
public class MergeSort extends BaseSort {

    /**
     * Ordena o vetor em ordem crescente e registra as métricas.
     * 
     * @param a O vetor a ser ordenado
     */
    @Override
    public void sort(int[] a) {
        reset();
        long start = System.nanoTime();
        mergeSort(a, 0, a.length - 1);
        executionTime = System.nanoTime() - start;
    }

    /**
     * Ordena recursivamente o subvetor {@code a[left..right]}.
     * 
     * @param a     O vetor
     * @param left  O índice inicial
     * @param right O índice final
     */
    private void mergeSort(int[] a, int left, int right) {
        // caso base: subvetor com 0 ou 1 elemento já está ordenado
        if (left >= right) {
            return;
        }

        // divide ao meio (forma que evita overflow de left + right)
        int middle = left + (right - left) / 2;
        mergeSort(a, left, middle);
        mergeSort(a, middle + 1, right);
        // conquista: intercala as duas metades já ordenadas
        merge(a, left, middle, right);
    }

    /**
     * Intercala os subvetores ordenados {@code a[left..middle]} e
     * {@code a[middle+1..right]} em um único trecho ordenado.
     * 
     * @param a      O vetor
     * @param left   O índice inicial da primeira metade
     * @param middle O índice final da primeira metade
     * @param right  O índice final da segunda metade
     */
    private void merge(int[] a, int left, int middle, int right) {
        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        // cópias auxiliares das duas metades: memória extra O(n)
        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = a[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = a[middle + 1 + j];
        }

        // i percorre leftArray, j percorre rightArray, k escreve em a
        int i = 0;
        int j = 0;
        int k = left;

        // escolhe sempre o menor entre os dois próximos elementos
        while (i < leftSize && j < rightSize) {
            comparisons++;
            // <= mantém a estabilidade: em empate, o da esquerda sai primeiro
            if (leftArray[i] <= rightArray[j]) {
                a[k] = leftArray[i];
                swaps++; // conta cada escrita no vetor original
                i++;
            } else {
                a[k] = rightArray[j];
                swaps++;
                j++;
            }
            k++;
        }

        // sobras da metade esquerda (se a direita acabou primeiro)
        while (i < leftSize) {
            a[k] = leftArray[i];
            swaps++;
            i++;
            k++;
        }

        // sobras da metade direita (se a esquerda acabou primeiro)
        while (j < rightSize) {
            a[k] = rightArray[j];
            swaps++;
            j++;
            k++;
        }
    }
}
