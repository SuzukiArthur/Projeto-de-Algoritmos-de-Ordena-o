import java.io.*;
import java.util.*;

public class Main {
    static final int[] TAMANHOS = {100, 1000, 5000, 10000, 50000, 100000};
    static final int EXECUCOES = 5;

    public static void main(String[] args) throws IOException {
        Map<String, SortingAlgorithm> algoritmos = new LinkedHashMap<>();
        algoritmos.put("Bubble", new BubbleSort());
        algoritmos.put("Insertion", new InsertionSort());
        algoritmos.put("Selection", new SelectionSort());
        algoritmos.put("Merge", new MergeSort());
        algoritmos.put("Quick", new QuickSort());

        Set<String> quadraticos = Set.of("Bubble", "Insertion", "Selection");
        String[] tipos = {"aleatorio", "ordenado", "inverso"};

        new File("resultados").mkdirs();
        try (PrintWriter csv = new PrintWriter("resultados/resultados.csv");
             PrintWriter bubbleCsv = new PrintWriter("resultados/resultados_bubble_sort.csv");
             PrintWriter insertionCsv = new PrintWriter("resultados/resultados_insertion_sort.csv");
             PrintWriter selectionCsv = new PrintWriter("resultados/resultados_selection_sort.csv");
             PrintWriter mergeCsv = new PrintWriter("resultados/resultados_merge_sort.csv");
             PrintWriter quickCsv = new PrintWriter("resultados/resultados_quick_sort.csv")) {
            Map<String, PrintWriter> csvPorAlgoritmo = new LinkedHashMap<>();
            csvPorAlgoritmo.put("Bubble", bubbleCsv);
            csvPorAlgoritmo.put("Insertion", insertionCsv);
            csvPorAlgoritmo.put("Selection", selectionCsv);
            csvPorAlgoritmo.put("Merge", mergeCsv);
            csvPorAlgoritmo.put("Quick", quickCsv);

            csv.println("algoritmo,tipo,n,tempo_ms,comparacoes,trocas,memoria_kb");
            for (PrintWriter arquivo : csvPorAlgoritmo.values()) {
                arquivo.println(
                    "tamanho;tipo;media_comparacoes;media_trocas;tempo_medio_ns;tempo_medio_ms;validacao");
            }

            for (String tipo : tipos) {
                for (int n : TAMANHOS) {
                    int[] base = gerar(tipo, n);
                    int[] ref = base.clone();
                    Arrays.sort(ref);

                    for (var e : algoritmos.entrySet()) {
                        if (quadraticos.contains(e.getKey()) && n > 50000) continue;

                        double somaTempo = 0, somaMem = 0;
                        long somaComparacoes = 0, somaTrocas = 0;
                        long somaTempoNs = 0;

                        for (int r = 0; r < EXECUCOES; r++) {
                            int[] copia = base.clone();
                            long memAntes = usoMemoria();
                            e.getValue().sort(copia);
                            long memDepois = usoMemoria();

                            if (!e.getValue().isSorted(copia) || !Arrays.equals(copia, ref))
                                throw new IllegalStateException(
                                    "Falha: " + e.getKey() + " " + tipo + " n=" + n);

                            if (r == 0) continue; // warm-up descartado
                            somaTempo += e.getValue().getExecutionTime() / 1e6;
                            somaTempoNs += e.getValue().getExecutionTime();
                            somaMem += Math.max(0, memDepois - memAntes) / 1024.0;
                            somaComparacoes += e.getValue().getComparisons();
                            somaTrocas += e.getValue().getSwaps();
                        }
                        int validas = EXECUCOES - 1;
                        double mediaComparacoes = somaComparacoes / (double) validas;
                        double mediaTrocas = somaTrocas / (double) validas;
                        double tempoMedioNs = somaTempoNs / (double) validas;
                        double tempoMedioMs = somaTempo / validas;

                        csv.printf(Locale.US, "%s,%s,%d,%.4f,%d,%d,%.2f%n",
                            e.getKey(), tipo, n, tempoMedioMs, Math.round(mediaComparacoes),
                            Math.round(mediaTrocas), somaMem / validas);
                        csvPorAlgoritmo.get(e.getKey()).printf(
                            Locale.US, "%d;%s;%.2f;%.2f;%.2f;%.6f;Sim%n",
                            n, tipo, mediaComparacoes, mediaTrocas, tempoMedioNs,
                            tempoMedioMs);
                        System.out.printf("%s | %s | n=%d | %.2f ms%n",
                            e.getKey(), tipo, n, tempoMedioMs);
                    }
                }
            }
        }
    }

    static int[] gerar(String tipo, int n) {
        return switch (tipo) {
            case "aleatorio" -> DataGenerator.random(n, 42);
            case "ordenado" -> DataGenerator.sorted(n);
            default -> DataGenerator.reversed(n);
        };
    }

    static long usoMemoria() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}