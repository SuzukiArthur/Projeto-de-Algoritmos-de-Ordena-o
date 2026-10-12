import selectionSort.SelectionSort;
import selectionSort.SortingAlgorithm;
import QuickSort.QuickSort;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class Main {

    private static final int[] TAMANHOS = { 100, 1000, 5000, 10000, 50000, 100000 };
    private static final int LIMITE_QUADRATICO = 50000;

    // Nome, arquivo CSV, fábrica do algoritmo //
    private static final Object[][] ALGORITMOS = {
            { "Selection Sort", "resultados_selection_sort.csv",
                    (Supplier<SortingAlgorithm>) SelectionSort::new, true },
            { "Quick Sort", "resultados_quick_sort.csv",
                    (Supplier<SortingAlgorithm>) QuickSort::new, false },
    };

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        for (Object[] alg : ALGORITMOS) {
            String nome = (String) alg[0];
            String nomeArquivo = (String) alg[1];
            Supplier<SortingAlgorithm> fabrica = (Supplier<SortingAlgorithm>) alg[2];
            boolean quadratico = (boolean) alg[3];

            System.out.println("\n##############################");
            System.out.println(nome);
            System.out.println("##############################");

            try (PrintWriter arquivo = new PrintWriter(new FileWriter(nomeArquivo))) {

                arquivo.println(
                        "tamanho;tipo;media_comparacoes;media_trocas;"
                                + "tempo_medio_ns;tempo_medio_ms;validacao");

                for (int tamanho : TAMANHOS) {

                    if (quadratico && tamanho > LIMITE_QUADRATICO) {
                        continue;
                    }

                    System.out.println("\n==============================");
                    System.out.println("Tamanho do vetor: " + tamanho);
                    System.out.println("==============================");

                    // Seed fixa: todos os algoritmos recebem as mesmas entradas
                    int[] aleatorio = gerarAleatorio(tamanho);
                    int[] crescente = gerarCrescente(tamanho);
                    int[] decrescente = gerarDecrescente(tamanho);

                    executarTeste(arquivo, fabrica, tamanho, "Aleatorio", aleatorio);
                    executarTeste(arquivo, fabrica, tamanho, "Crescente", crescente);
                    executarTeste(arquivo, fabrica, tamanho, "Decrescente", decrescente);
                }

                System.out.println("\nResultados salvos em " + nomeArquivo);

            } catch (IOException e) {
                System.out.println("Erro ao salvar " + nomeArquivo + ": " + e.getMessage());
            }
        }
    }

    public static int[] gerarAleatorio(int tamanho) {
        int[] vetor = new int[tamanho];
        Random gerador = new Random(42);
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = gerador.nextInt(tamanho * 10 + 1);
        }
        return vetor;
    }

    public static int[] gerarCrescente(int tamanho) {
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = i;
        }
        return vetor;
    }

    public static int[] gerarDecrescente(int tamanho) {
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = tamanho - i;
        }
        return vetor;
    }

    // Executa cinco vezes, descarta a primeira e salva a média
    public static void executarTeste(
            PrintWriter arquivo,
            Supplier<SortingAlgorithm> fabrica,
            int tamanho,
            String tipo,
            int[] vetorOriginal) {

        long somaComparacoes = 0;
        long somaTrocas = 0;
        long somaTempos = 0;

        for (int repeticao = 1; repeticao <= 5; repeticao++) {

            int[] copia = Arrays.copyOf(vetorOriginal, vetorOriginal.length);

            SortingAlgorithm ordenador = fabrica.get();
            ordenador.sort(copia);

            if (!ordenador.isSorted(copia)) {
                System.out.println("ERRO: vetor não ordenado!");
                return;
            }

            // Descarta a primeira execução (warm-up)
            if (repeticao == 1) {
                continue;
            }

            somaComparacoes += ordenador.getComparisons();
            somaTrocas += ordenador.getSwaps();
            somaTempos += ordenador.getExecutionTime();
        }

        double mediaComparacoes = somaComparacoes / 4.0;
        double mediaTrocas = somaTrocas / 4.0;
        double mediaTempoNs = somaTempos / 4.0;
        double mediaTempoMs = mediaTempoNs / 1_000_000.0;

        System.out.println("\nTipo de vetor: " + tipo);
        System.out.println("Média de comparações: " + mediaComparacoes);
        System.out.println("Média de trocas: " + mediaTrocas);
        System.out.println("Tempo médio (ns): " + mediaTempoNs);
        System.out.println("Tempo médio (ms): " + mediaTempoMs);
        System.out.println("Validação: vetor ordenado corretamente");

        arquivo.println(
                tamanho + ";"
                        + tipo + ";"
                        + mediaComparacoes + ";"
                        + mediaTrocas + ";"
                        + mediaTempoNs + ";"
                        + mediaTempoMs + ";"
                        + "Sim");
    }
}