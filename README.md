# Projeto de Algoritmos de Ordenação Paralela e Serial

## Visão Geral
Este projeto envolve a implementação de vários algoritmos de ordenação em Java, tanto em suas versões seriais quanto paralelas. O objetivo é analisar e comparar o desempenho desses algoritmos de ordenação em diferentes condições e visualizar os resultados por meio de gráficos gerados. O projeto inclui algoritmos clássicos como Bubble Sort, Quick Sort, Merge Sort e Insertion Sort, cada um fornecido em versão serial e paralela.

## Estrutura do Projeto
Os arquivos do projeto estão organizados da seguinte forma:

### Arquivos Principais
- **Main.java**: Este arquivo é o ponto de entrada do projeto. Ele é responsável por compilar e executar todos os outros arquivos de algoritmos de ordenação. Também chama um script Python (`graphs.py`) ao final para gerar gráficos de comparação dos resultados de execução.

- **BubbleSort.java**: Implementa a versão serial do algoritmo Bubble Sort. Esta versão ordena um array em ordem crescente e registra o tempo necessário para tamanhos diferentes de entrada.

- **InsertionSort.java**: Implementa a versão serial do algoritmo Insertion Sort. Assim como o Bubble Sort, ordena arrays de diferentes tamanhos e registra o tempo de execução.

- **MergeSort.java**: Implementa a versão serial do algoritmo Merge Sort. Ele divide e mescla recursivamente os arrays para realizar a ordenação, enquanto monitora o desempenho.

- **QuickSort.java**: Implementa a versão serial do algoritmo Quick Sort. Utiliza uma abordagem de partição para ordenar e registra o tempo de execução para diferentes tamanhos de arrays.

### Versões Paralelas
- **ParallelBubbleSort.java**: Implementa a versão paralela do algoritmo Bubble Sort. O array é dividido entre múltiplas threads para acelerar o processo de ordenação.

- **ParallelInsertionSort.java**: Implementa a versão paralela do algoritmo Insertion Sort. Esta versão utiliza múltiplas threads para ordenar sub-arrays em paralelo.

- **ParallelMergeSort.java**: Implementa a versão paralela do algoritmo Merge Sort. Utiliza o `ForkJoinPool` do Java para paralelizar o processo de mesclagem, resultando em uma execução mais rápida.

- **ParallelQuickSort.java**: Implementa a versão paralela do algoritmo Quick Sort. Também utiliza `ForkJoinPool` para dividir o trabalho entre diferentes threads, melhorando o desempenho.

### Arquivos de Suporte
- **graphs.py**: Este script Python é responsável por gerar comparações gráficas do desempenho de todos os algoritmos de ordenação. Ele lê os dados de execução dos arquivos CSV gerados pelos programas Java e cria gráficos de linhas comparando os tempos de execução para diferentes tamanhos de entrada e contagens de threads.

## Como Executar
1. **Preparar o Ambiente**
   - Antes de compilar e executar o código, certifique-se de excluir quaisquer arquivos CSV antigos que estejam presentes no diretório do projeto. Isso é necessário porque os códigos Java atuais irão apenas adicionar novas informações aos arquivos já existentes, o que pode resultar em dados duplicados.
   - Para remover os arquivos CSV antigos, execute o seguinte comando no terminal:
   
   ```sh
   rm *.csv
   ```

2. **Compilar e Executar o Código**
   - Para compilar e executar todo o projeto, basta executar o arquivo `Main.java`. Ele irá compilar todos os arquivos `.java`, executar cada um dos algoritmos de ordenação e gerar arquivos CSV contendo os dados de desempenho.
   - Os arquivos `.class` compilados são salvos em um diretório chamado `.class`.

   ```sh
   javac Main.java
   java Main
   ```

2. **Gerar Gráficos**
   - Assim que todos os algoritmos de ordenação forem executados, o script `graphs.py` será executado automaticamente para gerar gráficos comparando o desempenho dos diferentes métodos de ordenação.
   - Os gráficos são gerados a partir de arquivos CSV nomeados como `serial_bubble_sort_results.csv` e `parallel_bubble_sort_results.csv`.

   Caso precise executar o script Python manualmente:
   ```sh
   python graphs.py
   ```

## Resultados
O projeto gera arquivos CSV para cada algoritmo de ordenação com dados sobre os tempos de execução. Os dados são coletados para diferentes tamanhos de arrays e números variados de threads (para as versões paralelas). Os gráficos fornecem insights sobre a eficiência de cada algoritmo e como a paralelização afeta o desempenho.

## Estrutura de Diretórios
- **.class/**: Contém os arquivos `.class` compilados dos programas Java.
- **BubbleSort.java, InsertionSort.java, MergeSort.java, QuickSort.java**: Implementações seriais dos algoritmos de ordenação.
- **ParallelBubbleSort.java, ParallelInsertionSort.java, ParallelMergeSort.java, ParallelQuickSort.java**: Implementações paralelas dos algoritmos de ordenação.
- **graphs.py**: Script Python para gerar gráficos de comparação de desempenho.

## Requisitos
- **Java 8 ou superior**: Para compilar e executar os programas Java.
- **Terminal Bash**: Necessário para executar o comando de remoção dos arquivos CSV antigos.
- **Python 3**: Para gerar os gráficos a partir dos resultados.
- **Matplotlib e Pandas**: Bibliotecas Python necessárias para o `graphs.py` ler os arquivos CSV e gerar gráficos.

## Observações
- O desempenho dos algoritmos paralelos depende do número de núcleos de processamento disponíveis. Executar em um sistema com mais núcleos trará melhores resultados para as versões paralelas.
- Certifique-se de que tanto o Java quanto o Python estejam disponíveis no PATH do sistema para permitir uma execução sem problemas pelo terminal.

## Autores
Tiago Farias Rodrigues - 1914187
Ana Laís Nunes de Vasconcelos - 2216984
Nós dividimos este projeto, onde cada um ficou responsável por dois dos quatro algoritmos presentes aqui neste projeto, que foi desenvolvido como parte de um curso de computação paralela para entender melhor as vantagens e limitações da paralelização de diferentes tipos de algoritmos de ordenação.