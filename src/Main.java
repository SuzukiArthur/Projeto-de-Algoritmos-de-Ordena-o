import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


/**
 * Classe principal
 * Gera vetores de diferentes tamanhos e configurações,
 * valida os resultados e exporta as métricas para arquivos CSV.
 */
public class Main {

    // Tamanhos dos vetores utilizados nos testes
    static final int[] TAMANHOS = {100, 1000, 5000, 10000, 50000, 100000};

    // Quantidade de execuções por cenário, incluindo o aquecimento
    static final int EXECUCOES = 5;

    public static void main(String[] args) throws IOException {

        // Registra os algoritmos que serão avaliados
        Map<String, SortingAlgorithm> algoritmos = new LinkedHashMap<>();
        algoritmos.put("Bubble", new BubbleSort());
        algoritmos.put("Insertion", new InsertionSort());
        algoritmos.put("Selection", new SelectionSort());
        algoritmos.put("Merge", new MergeSort());
        algoritmos.put("Quick", new QuickSort());

        // Algoritmos quadráticos são limitados a vetores de até 50.000 elementos
        Set<String> quadraticos = Set.of("Bubble", "Insertion", "Selection");

        // Tipos de entrada utilizados nos testes
        String[] tipos = {"aleatorio", "ordenado", "inverso"};

        // Cria o diretório para armazenar os resultados
        new File("resultados").mkdirs();

        try (PrintWriter csv = new PrintWriter("resultados/resultados.csv");
             PrintWriter bubbleCsv = new PrintWriter("resultados/resultados_bubble_sort.csv");
             PrintWriter insertionCsv = new PrintWriter("resultados/resultados_insertion_sort.csv");
             PrintWriter selectionCsv = new PrintWriter("resultados/resultados_selection_sort.csv");
             PrintWriter mergeCsv = new PrintWriter("resultados/resultados_merge_sort.csv");
             PrintWriter quickCsv = new PrintWriter("resultados/resultados_quick_sort.csv")) {

            // Associa cada algoritmo ao seu respectivo arquivo CSV
            Map<String, PrintWriter> csvPorAlgoritmo = new LinkedHashMap<>();
            csvPorAlgoritmo.put("Bubble", bubbleCsv);
            csvPorAlgoritmo.put("Insertion", insertionCsv);
            csvPorAlgoritmo.put("Selection", selectionCsv);
            csvPorAlgoritmo.put("Merge", mergeCsv);
            csvPorAlgoritmo.put("Quick", quickCsv);

            // Define os cabeçalhos dos arquivos CSV
            csv.println("algoritmo,tipo,n,tempo_ms,comparacoes,trocas,memoria_kb");

            for (PrintWriter arquivo : csvPorAlgoritmo.values()) {
                arquivo.println(
                    "tamanho;tipo;media_comparacoes;media_trocas;tempo_medio_ns;tempo_medio_ms;validacao");
            }

            // Percorre os tipos de entrada e os tamanhos dos vetores
            for (String tipo : tipos) {
                for (int n : TAMANHOS) {

                    int[] base = gerar(tipo, n);
                    int[] ref = base.clone();
                    Arrays.sort(ref);

                    // Executa cada algoritmo com os mesmos dados de entrada
                    for (var e : algoritmos.entrySet()) {

                        if (quadraticos.contains(e.getKey()) && n > 50000) continue;

                        double somaTempo = 0, somaMem = 0;
                        long somaComparacoes = 0, somaTrocas = 0;
                        long somaTempoNs = 0;

                        // Executa os testes e descarta a primeira execução (aquecimento)
                        for (int r = 0; r < EXECUCOES; r++) {
                            int[] copia = base.clone();
                            long memAntes = usoMemoria();

                            e.getValue().sort(copia);

                            long memDepois = usoMemoria();

                            // Verifica se o vetor foi ordenado corretamente
                            if (!e.getValue().isSorted(copia) || !Arrays.equals(copia, ref))
                                throw new IllegalStateException(
                                    "Falha: " + e.getKey() + " " + tipo + " n=" + n);

                            if (r == 0) continue;

                            // Acumula as métricas das execuções válidas
                            somaTempo += e.getValue().getExecutionTime() / 1e6;
                            somaTempoNs += e.getValue().getExecutionTime();
                            somaMem += Math.max(0, memDepois - memAntes) / 1024.0;
                            somaComparacoes += e.getValue().getComparisons();
                            somaTrocas += e.getValue().getSwaps();
                        }

                        // Calcula as médias das execuções consideradas
                        int validas = EXECUCOES - 1;
                        double mediaComparacoes = somaComparacoes / (double) validas;
                        double mediaTrocas = somaTrocas / (double) validas;
                        double tempoMedioNs = somaTempoNs / (double) validas;
                        double tempoMedioMs = somaTempo / validas;

                        // Registra os resultados nos arquivos CSV
                        csv.printf(Locale.US, "%s,%s,%d,%.4f,%d,%d,%.2f%n",
                            e.getKey(), tipo, n, tempoMedioMs, Math.round(mediaComparacoes),
                            Math.round(mediaTrocas), somaMem / validas);

                        csvPorAlgoritmo.get(e.getKey()).printf(
                            Locale.US, "%d;%s;%.2f;%.2f;%.2f;%.6f;Sim%n",
                            n, tipo, mediaComparacoes, mediaTrocas, tempoMedioNs,
                            tempoMedioMs);

                        // Exibe o resultado no console
                        System.out.printf("%s | %s | n=%d | %.2f ms%n",
                            e.getKey(), tipo, n, tempoMedioMs);
                    }
                }
            }
        }
    }

    /**
     * Gera vetores conforme o tipo de entrada solicitado.
     *
     * @param tipo O tipo de ordenação inicial dos dados
     * @param n A quantidade de elementos do vetor
     * @return O vetor gerado
     */
    static int[] gerar(String tipo, int n) {
        return switch (tipo) {
            case "aleatorio" -> DataGenerator.random(n, 42);
            case "ordenado" -> DataGenerator.sorted(n);
            default -> DataGenerator.reversed(n);
        };
    }

    /**
     * Obtém uma estimativa do uso atual de memória da JVM.
     *
     * @return A memória utilizada em bytes
     */
    static long usoMemoria() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}
