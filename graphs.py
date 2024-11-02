import pandas as pd
import matplotlib.pyplot as plt

# Carregando os resultados dos arquivos CSV
bubble_sort_results = pd.read_csv('parallel_bubble_sort_results.csv', encoding='latin1')
quick_sort_results = pd.read_csv('parallel_quick_sort_results.csv', encoding='latin1')
merge_sort_results = pd.read_csv('parallel_merge_sort_results.csv', encoding='latin1')
insertion_sort_results = pd.read_csv('parallel_insertion_sort_results.csv', encoding='latin1')
serial_bubble_sort_results = pd.read_csv('serial_bubble_sort_results.csv', encoding='latin1')
serial_quick_sort_results = pd.read_csv('serial_quick_sort_results.csv', encoding='latin1')
serial_merge_sort_results = pd.read_csv('serial_merge_sort_results.csv', encoding='latin1')
serial_insertion_sort_results = pd.read_csv('serial_insertion_sort_results.csv', encoding='latin1')

# Agrupando os resultados para obter o tempo médio de cada configuração
bubble_sort_avg = bubble_sort_results.groupby(['Numero de Threads', 'Tamanho do Array'])['Tempo de Execucao (ms)'].mean().reset_index()
quick_sort_avg = quick_sort_results.groupby(['Numero de Threads', 'Tamanho do Array'])['Tempo de Execucao (ms)'].mean().reset_index()
merge_sort_avg = merge_sort_results.groupby(['Numero de Threads', 'Tamanho do Array'])['Tempo de Execucao (ms)'].mean().reset_index()
insertion_sort_avg = insertion_sort_results.groupby(['Numero de Threads', 'Tamanho do Array'])['Tempo de Execucao (ms)'].mean().reset_index()
serial_bubble_sort_avg = serial_bubble_sort_results.groupby('Tamanho do Array')['Tempo de Execucao (ms)'].mean().reset_index()
serial_quick_sort_avg = serial_quick_sort_results.groupby('Tamanho do Array')['Tempo de Execucao (ms)'].mean().reset_index()
serial_merge_sort_avg = serial_merge_sort_results.groupby('Tamanho do Array')['Tempo de Execucao (ms)'].mean().reset_index()
serial_insertion_sort_avg = serial_insertion_sort_results.groupby('Tamanho do Array')['Tempo de Execucao (ms)'].mean().reset_index()

# Gráfico para Bubble Sort
plt.figure(figsize=(10, 6))
for num_threads in bubble_sort_avg['Numero de Threads'].unique():
    subset = bubble_sort_avg[bubble_sort_avg['Numero de Threads'] == num_threads]
    plt.plot(subset['Tamanho do Array'], subset['Tempo de Execucao (ms)'], label=f'Bubble Sort ({num_threads} threads)', marker='o')
plt.plot(serial_bubble_sort_avg['Tamanho do Array'], serial_bubble_sort_avg['Tempo de Execucao (ms)'], label='Bubble Sort (Serial)', linestyle='--', marker='x')

plt.xlabel('Tamanho do Array')
plt.ylabel('Tempo Médio de Execução (ms)')
plt.title('Desempenho do Bubble Sort com Diferentes Quantidades de Threads')
plt.legend()
plt.grid(True)
plt.show()

# Gráfico para Quick Sort
plt.figure(figsize=(10, 6))
for num_threads in quick_sort_avg['Numero de Threads'].unique():
    subset = quick_sort_avg[quick_sort_avg['Numero de Threads'] == num_threads]
    plt.plot(subset['Tamanho do Array'], subset['Tempo de Execucao (ms)'], label=f'Quick Sort ({num_threads} threads)', marker='o')
plt.plot(serial_quick_sort_avg['Tamanho do Array'], serial_quick_sort_avg['Tempo de Execucao (ms)'], label='Quick Sort (Serial)', linestyle='--', marker='x')

plt.xlabel('Tamanho do Array')
plt.ylabel('Tempo Médio de Execução (ms)')
plt.title('Desempenho do Quick Sort com Diferentes Quantidades de Threads')
plt.legend()
plt.grid(True)
plt.show()

# Gráfico para Merge Sort
plt.figure(figsize=(10, 6))
for num_threads in merge_sort_avg['Numero de Threads'].unique():
    subset = merge_sort_avg[merge_sort_avg['Numero de Threads'] == num_threads]
    plt.plot(subset['Tamanho do Array'], subset['Tempo de Execucao (ms)'], label=f'Merge Sort ({num_threads} threads)', marker='o')
plt.plot(serial_merge_sort_avg['Tamanho do Array'], serial_merge_sort_avg['Tempo de Execucao (ms)'], label='Merge Sort (Serial)', linestyle='--', marker='x')

plt.xlabel('Tamanho do Array')
plt.ylabel('Tempo Médio de Execução (ms)')
plt.title('Desempenho do Merge Sort com Diferentes Quantidades de Threads')
plt.legend()
plt.grid(True)
plt.show()

# Gráfico para Insertion Sort
plt.figure(figsize=(10, 6))
for num_threads in insertion_sort_avg['Numero de Threads'].unique():
    subset = insertion_sort_avg[insertion_sort_avg['Numero de Threads'] == num_threads]
    plt.plot(subset['Tamanho do Array'], subset['Tempo de Execucao (ms)'], label=f'Insertion Sort ({num_threads} threads)', marker='o')
plt.plot(serial_insertion_sort_avg['Tamanho do Array'], serial_insertion_sort_avg['Tempo de Execucao (ms)'], label='Insertion Sort (Serial)', linestyle='--', marker='x')

plt.xlabel('Tamanho do Array')
plt.ylabel('Tempo Médio de Execução (ms)')
plt.title('Desempenho do Insertion Sort com Diferentes Quantidades de Threads')
plt.legend()
plt.grid(True)
plt.show()