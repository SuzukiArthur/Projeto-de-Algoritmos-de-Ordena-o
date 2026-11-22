# Projeto de Algoritmos de Ordenação

**Integrantes:** Alice Martins Miranda, Arthur Souza Ribeiro, Arthur Yuji Mendes Suzuki, Felipe Souza de Jesus e Luiz Miguel Mazzega Lamas

Projeto em Java que implementa e compara cinco algoritmos de ordenação: **Bubble Sort**, **Insertion Sort**, **Selection Sort**, **Merge Sort** e **Quick Sort**.
Cada algoritmo é executado sobre vetores de vários tamanhos e tipos de entrada. O programa valida a ordenação, mede comparações, trocas, tempo e memória, e exporta tudo para arquivos CSV.

## Algoritmos

| Algoritmo | Observações da implementação | Complexidade (médio caso) |
|-----------|------------------------------|---------------------------|
| Bubble Sort | Com parada antecipada quando uma passagem não faz trocas | O(n²) |
| Insertion Sort | Desloca elementos em vez de trocar pares | O(n²) |
| Selection Sort | Uma troca por posição, no máximo | O(n²) |
| Merge Sort | Top-down, com vetores auxiliares na intercalação (estável) | O(n log n) |
| Quick Sort | Pivô por mediana de três, particionamento de Lomuto, recursão apenas na menor metade (pilha O(log n)) | O(n log n) |

## Estrutura do projeto

```
.
├── src/
│   ├── Main.java              # Executa os experimentos e gera os CSVs
│   ├── SortingAlgorithm.java  # Interface comum dos algoritmos
│   ├── BaseSort.java          # Classe base: contadores, reset() e isSorted()
│   ├── DataGenerator.java     # Geração dos vetores de teste
│   ├── BubbleSort.java
│   ├── InsertionSort.java
│   ├── SelectionSort.java
│   ├── MergeSort.java
│   └── QuickSort.java
└── resultados/                # CSVs gerados (e planilha de apoio)
```

Todos os algoritmos estendem `BaseSort`, que implementa `SortingAlgorithm` e fornece os contadores de comparações, trocas e tempo de execução (em nanossegundos).

## Como funciona o experimento

- **Tamanhos:** 100, 1.000, 5.000, 10.000, 50.000 e 100.000 elementos.
- **Tipos de entrada:**
  - `aleatorio`: valores entre 0 e 999.999, com semente fixa (42) para ser reproduzível;
  - `ordenado`: já em ordem crescente;
  - `inverso`: em ordem decrescente.
- **Execuções:** 5 por cenário. A primeira é descartada (aquecimento da JVM) e as outras 4 entram na média.
- **Mesma entrada para todos:** cada algoritmo recebe uma cópia do mesmo vetor base.
- **Validação:** após cada execução, o resultado é comparado com `Arrays.sort`. Se houver divergência, o programa lança `IllegalStateException`.
- **Limite dos quadráticos:** Bubble, Insertion e Selection não rodam para `n > 50.000`.

## Como executar

Requer JDK 17 ou superior (o código usa `switch` com expressões e `var`).

Na raiz do projeto:

```bash
javac -d out src/*.java
java -cp out Main
```

Execute sempre a partir da raiz, pois a pasta `resultados/` é criada no diretório atual. O progresso aparece no console, uma linha por cenário:

```
Merge | aleatorio | n=10000 | 1.10 ms
```

No VS Code, o arquivo `.vscode/settings.json` já aponta `src` como pasta de código-fonte.

## Resultados gerados

Todos os arquivos ficam em `resultados/`.

**`resultados.csv`** reúne todos os algoritmos (separador vírgula):

| Coluna | Descrição |
|--------|-----------|
| `algoritmo` | Nome do algoritmo |
| `tipo` | `aleatorio`, `ordenado` ou `inverso` |
| `n` | Tamanho do vetor |
| `tempo_ms` | Tempo médio em milissegundos |
| `comparacoes` | Média de comparações |
| `trocas` | Média de trocas |
| `memoria_kb` | Estimativa média de memória usada |

**`resultados_<algoritmo>_sort.csv`** traz um arquivo por algoritmo (separador ponto e vírgula), com as colunas `tamanho`, `tipo`, `media_comparacoes`, `media_trocas`, `tempo_medio_ns`, `tempo_medio_ms` e `validacao`.

## Observações sobre as métricas

- **O que conta como "troca" varia:** em Bubble, Selection e Quick é uma troca real de dois elementos. Em Insertion é um deslocamento e, em Merge, uma escrita no vetor original. Por isso, compare as trocas com cuidado entre algoritmos diferentes.
- **Memória é uma estimativa:** é a diferença de `totalMemory() - freeMemory()` da JVM antes e depois da ordenação. Com a coleta de lixo agindo no meio, o valor pode sair zerado ou impreciso.
- **Tempos pequenos oscilam:** em vetores de 100 elementos, o ruído da JVM pesa mais que o algoritmo em si.

## Autores

- Bubble Sort: Arthur Souza Ribeiro
- Selection Sort: Arthur Yuji Mendes Suzuki
- Quick Sort: Luiz Miguel Mazzega Lamas
- Insertion Sort: Felipe Souza de Jesus
- Merge Sort: Alice Martins Miranda
