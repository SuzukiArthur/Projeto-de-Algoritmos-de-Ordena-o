/* Classe que implementa o algoritmo de ordenação Insertion Sort.
    Estende BaseSort, então herda contadores e isSorted, e só precisa implementar o sort.
    A ideia: o vetor é dividido em uma parte já ordenada (à esquerda) e outra não ordenada. 
    A cada passo, pega-se um elemento da parte não ordenada e 
    insere-se na posição certa dentro da parte ordenada,
    como ao organizar cartas na mão.
    A cada inserção, os elementos da parte ordenada que forem maiores que o elemento a ser inserido
    são deslocados para a direita, abrindo espaço para o novo elemento.
*/
 // @autor Felipe Souza de Jesus

public class InsertionSort extends BaseSort { 

    /**
        * Ordena o vetor recebido em ordem crescente, trabalhando diretamente sobre ele.
     * @param a O vetor de inteiros a ser ordenado.
     */
    @Override
    public void sort(int[] a) { // Organiza o vetor recebido em ordem crescente, trabalhando diretamente sobre ele.

        reset(); // zera os contadores que possam ter sobrado de uma execução anterior

        long start = System.nanoTime(); // guarda o instante em que a ordenação começa

        for (int i = 1; i < a.length; i++) { // percorre o vetor a partir do segundo elemento, 
                                            // pois o primeiro já está "ordenado" por si só

            int key = a[i]; // elemento a ser inserido na parte ordenada

            int j = i - 1; // índice do último elemento da parte ordenada

            while (j >= 0) { // percorre a parte ordenada da direita para a esquerda, 
                            //procurando a posição correta para o elemento key
                comparisons++; // conta mais uma comparação

                if (a[j] <= key) { // se o elemento da parte ordenada for menor ou igual ao key, 
                                    // a posição correta foi encontrada
                    break;
                }
                a[j + 1] = a[j]; // desloca o elemento da parte ordenada para a direita, abrindo espaço para o key
                swaps++;// conta a troca (deslocamento) realizada
                j--; // move para o próximo elemento da parte ordenada
            }
            a[j + 1] = key; // insere o elemento key na posição correta da parte ordenada
        }

        executionTime = System.nanoTime() - start; // tempo decorrido desde o início, em nanossegundos
    }
}
