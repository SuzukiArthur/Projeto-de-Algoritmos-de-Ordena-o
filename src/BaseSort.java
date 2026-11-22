/* Classe abstrata que implementa a interface SortingAlgorithm e fornece funcionalidades comuns para algoritmos de ordenação. */
public abstract class BaseSort implements SortingAlgorithm {
    protected long comparisons; // Contador de comparações realizadas durante a ordenação
    protected long swaps; // Contador de trocas realizadas durante a ordenação
    protected long executionTime; // em nanossegundos

    protected void reset() { // Reinicia os contadores de métricas
        comparisons = 0; // Reinicia o contador de comparações
        swaps = 0; // Reinicia o contador de trocas
        executionTime = 0; // Reinicia o tempo de execução
    }

    @Override public long getComparisons()   { return comparisons; } // Retorna o número de comparações realizadas durante a ordenação
    @Override public long getSwaps()         { return swaps; } // Retorna o número de trocas realizadas durante a ordenação
    @Override public long getExecutionTime() { return executionTime; } // Retorna o tempo de execução da ordenação

    @Override
    public boolean isSorted(int[] array) { // Verifica se o vetor está ordenado em ordem crescente
        for (int i = 1; i < array.length; i++) { // Percorre o vetor a partir do segundo elemento
            if (array[i - 1] > array[i]) return false; // Se algum elemento anterior for maior que o atual, o vetor não está ordenado
        }
        return true; // Se nenhum elemento for maior que o anterior, o vetor está ordenado
    }
}