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
        try (PrintWriter csv = new PrintWriter("resultados/resultados.csv")) {
            csv.println("algoritmo,tipo,n,tempo_ms,comparacoes,trocas,memoria_kb");

            for (String tipo : tipos) {
                for (int n : TAMANHOS) {
                    int[] base = gerar(tipo, n);
                    int[] ref = base.clone();
                    Arrays.sort(ref);

                    for (var e : algoritmos.entrySet()) {
                        if (quadraticos.contains(e.getKey()) && n > 50000) continue;

                        double somaTempo = 0, somaMem = 0;
                        long comps = 0, trocas = 0;

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
                            somaMem += Math.max(0, memDepois - memAntes) / 1024.0;
                            comps = e.getValue().getComparisons();
                            trocas = e.getValue().getSwaps();
                        }
                        int validas = EXECUCOES - 1;
                        csv.printf(Locale.US, "%s,%s,%d,%.4f,%d,%d,%.2f%n",
                            e.getKey(), tipo, n, somaTempo / validas, comps, trocas,
                            somaMem / validas);
                        System.out.printf("%s | %s | n=%d | %.2f ms%n",
                            e.getKey(), tipo, n, somaTempo / validas);
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