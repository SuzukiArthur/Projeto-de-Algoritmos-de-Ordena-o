package selectionSort;
public class SelectionSort implements SortingAlgorithm {

    private long comparacoes;
    private long trocas;
    private long tempoExecucao;

    @Override
    public void sort(int[] vetor) {

        comparacoes = 0;
        trocas = 0;
        tempoExecucao = 0;

        if (vetor == null) {
            throw new IllegalArgumentException(
                "O vetor não pode ser nulo."
            );
        }

        long inicio = System.nanoTime();

        for (int i = 0; i < vetor.length - 1; i++) {

            int posicaoMenor = i;

            for (int j = i + 1; j < vetor.length; j++) {

                comparacoes++;

                if (vetor[j] < vetor[posicaoMenor]) {
                    posicaoMenor = j;
                }
            }

            if (posicaoMenor != i) {

                int temporario = vetor[i];
                vetor[i] = vetor[posicaoMenor];
                vetor[posicaoMenor] = temporario;

                trocas++;
            }
        }

        long fim = System.nanoTime();

        tempoExecucao = fim - inicio;
    }

    @Override
    public long getComparisons() {
        return comparacoes;
    }

    @Override
    public long getSwaps() {
        return trocas;
    }

    @Override
    public long getExecutionTime() {
        return tempoExecucao;
    }

    @Override
    public boolean isSorted(int[] vetor) {

        if (vetor == null) {
            return false;
        }

        for (int i = 0; i < vetor.length - 1; i++) {

            if (vetor[i] > vetor[i + 1]) {
                return false;
            }
        }

        return true;
    }
}