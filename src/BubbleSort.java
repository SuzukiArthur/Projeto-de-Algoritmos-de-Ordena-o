/**
 * Bubble Sort: a cada passagem, leva o maior valor restante até o fim do vetor,
 * trocando de posição apenas pares vizinhos que estão fora de ordem.
 * Conta comparações e trocas e registra o tempo de execução.
 *
 * @author Arthur Souza Ribeiro
 */
public class BubbleSort extends BaseSort {

    /**
     * Organiza o vetor recebido em ordem crescente, trabalhando diretamente sobre ele.
     *
     * @param a vetor de inteiros a ser organizado
     */
    @Override
    public void sort(int[] a) {
        // zera os contadores que possam ter sobrado de uma execução anterior
        reset();

        // guarda o instante em que a ordenação começa
        long start = System.nanoTime();

        // quantidade de elementos do vetor
        int n = a.length;

        // cada passagem fixa mais um elemento na sua posição final
        for (int i = 0; i < n - 1; i++) {

            // registra se houve alguma troca nesta passagem
            boolean swapped = false;

            // percorre só a parte que ainda não foi fixada no fim
            for (int j = 0; j < n - i - 1; j++) {
                // conta mais uma comparação
                comparisons++;

                // vizinhos fora de ordem: o da esquerda é maior que o da direita
                if (a[j] > a[j + 1]) {
                    // troca os dois valores usando uma variável auxiliar
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;

                    // conta a troca e marca que a passagem não está limpa
                    swaps++;
                    swapped = true;
                }
            }

            // sem nenhuma troca na passagem, o vetor já está ordenado: encerra
            if (!swapped) {
                break;
            }
        }

        // tempo decorrido desde o início, em nanossegundos
        executionTime = System.nanoTime() - start;
    }
}
