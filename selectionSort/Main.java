package selectionSort;
import java.util.Arrays;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        int[] tamanhos = {100, 1000, 5000, 10000, 50000};

        // Cria o arquivo CSV e escreve o cabeçalho
        try (PrintWriter arquivo = new PrintWriter(
                new FileWriter("resultados_selection_sort.csv"))) {

            arquivo.println(
                "tamanho;tipo;media_comparacoes;media_trocas;"
                + "tempo_medio_ns;tempo_medio_ms;validacao"
            );

            for (int tamanho : tamanhos) {

                System.out.println("\n==============================");
                System.out.println("Tamanho do vetor: " + tamanho);
                System.out.println("==============================");

                int[] aleatorio = gerarAleatorio(tamanho);
                int[] crescente = gerarCrescente(tamanho);
                int[] decrescente = gerarDecrescente(tamanho);

                executarTeste(arquivo, tamanho, "Aleatorio", aleatorio);
                executarTeste(arquivo, tamanho, "Crescente", crescente);
                executarTeste(arquivo, tamanho, "Decrescente", decrescente);
            }

            System.out.println(
                "\nResultados salvos em resultados_selection_sort.csv"
            );

        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
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
            int tamanho,
            String tipo,
            int[] vetorOriginal) {

        long somaComparacoes = 0;
        long somaTrocas = 0;
        long somaTempos = 0;

        for (int repeticao = 1; repeticao <= 5; repeticao++) {

            int[] copia = Arrays.copyOf(
                vetorOriginal, vetorOriginal.length
            );

            SelectionSort ordenador = new SelectionSort();
            ordenador.sort(copia);

            if (!ordenador.isSorted(copia)) {
                System.out.println("ERRO: vetor não ordenado!");
                return;
            }

            // Descarta a primeira execução
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

        // Salva uma linha no CSV
        arquivo.println(
            tamanho + ";"
            + tipo + ";"
            + mediaComparacoes + ";"
            + mediaTrocas + ";"
            + mediaTempoNs + ";"
            + mediaTempoMs + ";"
            + "Sim"
        );
    }
}