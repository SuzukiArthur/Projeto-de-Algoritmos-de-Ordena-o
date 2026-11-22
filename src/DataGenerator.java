import java.util.Random; /* Classe utilitária para gerar diferentes tipos de vetores de inteiros para testes de algoritmos de ordenação. */


public final class DataGenerator { /* Classe utilitária para gerar diferentes tipos de vetores de inteiros para testes de algoritmos de ordenação. */
    private DataGenerator() { /* Construtor privado para evitar instâncias da classe. */
    }

    public static int[] random(int n, long seed) { /* Gera um vetor de inteiros aleatórios de tamanho n, 
                                    utilizando uma semente específica para o gerador de números aleatórios. */
        Random random = new Random(seed); /* Cria um gerador de números aleatórios com a semente fornecida. */
        int[] array = new int[n]; /* Cria um vetor de inteiros de tamanho n. */
        for (int i = 0; i < n; i++) { /* Preenche o vetor com números aleatórios entre 0 e 999.999. */
            array[i] = random.nextInt(1_000_000); /* Gera um número aleatório entre 0 e 999.999 e o atribui à posição i do vetor. */
        }
        return array; /* Retorna o vetor de inteiros aleatórios gerado. */
    }

    public static int[] sorted(int n) { /* Gera um vetor de inteiros ordenados em ordem crescente de tamanho n. */
        int[] array = new int[n]; /* Cria um vetor de inteiros de tamanho n. */
        for (int i = 0; i < n; i++) { /* Preenche o vetor com números inteiros de 0 a n-1, garantindo que o vetor esteja ordenado. */
            array[i] = i; /* Atribui o valor i à posição i do vetor, garantindo que o vetor esteja ordenado em ordem crescente. */
        }
        return array; /* Retorna o vetor de inteiros ordenados gerado. */
    }

    public static int[] reversed(int n) { /* Gera um vetor de inteiros ordenados em ordem decrescente de tamanho n. */
        int[] array = new int[n]; /* Cria um vetor de inteiros de tamanho n. */
        for (int i = 0; i < n; i++) {  /* Preenche o vetor com números inteiros de n-1 a 0, garantindo que o vetor esteja ordenado. */
            array[i] = n - i - 1;  /* Atribui o valor n - i - 1 à posição i do vetor, garantindo que o vetor esteja ordenado em ordem decrescente. */
        }
        return array; /* Retorna o vetor de inteiros ordenados gerado. */
    }
}
