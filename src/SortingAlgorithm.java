/*interface do contrato falando que toda classe de ordenação que a implementar deve seguir este contrato */
public interface SortingAlgorithm {
    void sort(int[] array); // Ordena o vetor
    long getComparisons(); // Retorna o número de comparações realizadas durante a ordenação
    long getSwaps(); // Retorna o número de trocas realizadas durante a ordenação
    long getExecutionTime(); // Retorna o tempo de execução da ordenação
    boolean isSorted(int[] array); // Verifica se o vetor está ordenado
}