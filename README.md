# Análise Comparativa de Algoritmos de Busca com Paralelismo

## Resumo
Esse trabalho tem como objetivo realizar uma análise comparativa de desempenho entre algoritmos de busca sequencial, paralelismo na CPU (multithread) e paralelismo na GPU (OpenCL). A tarefa consiste na contagem de ocorrências de uma palavra em um texto, considerando a otimização de desempenho com diferentes abordagens de paralelização.

---

## Introdução
O trabalho explora três abordagens principais:

- **Serial (Sequencial)**: Implementação simples que percorre o texto linearmente.
- **Paralelismo na CPU**: Utilização de múltiplas threads para dividir o texto em partes e processá-las em paralelo, acelerando a busca.
- **Paralelismo na GPU**: Implementação usando OpenCL, onde cada thread da GPU verifica uma posição específica do texto, explorando o processamento massivamente paralelo.

A escolha dessas abordagens permite observar as diferenças práticas no tempo de execução e desempenho conforme o tipo de paralelismo aplicado.

---

## Metodologia
Foi adotada uma análise estatística dos resultados obtidos para identificar padrões de desempenho e comparar os algoritmos sob diferentes condições. 

As etapas realizadas incluem:
- Execução de cada algoritmo (Serial, Paralelo na CPU e Paralelo na GPU).
- Medição dos tempos de execução (em ms).
- Contagem do número de ocorrências da palavra.
- Comparação dos tempos sob diferentes quantidades de threads na CPU (2, 4 e 8 threads).
- Análise dos tempos considerando overheads como transferência de dados para GPU e compilação do kernel.

---

## Resultados e Discussão

### Resultados Obtidos(na minha máquina):
| Método               | Ocorrências | Tempo (ms)  |
|----------------------|-------------|--------------|
| Serial (CPU)         | 188         | 531          |
| Paralelo CPU (2 thr) | 188         | 565          |
| Paralelo CPU (4 thr) | 188         | 379          |
| Paralelo CPU (8 thr) | 188         | 379          |
| Paralelo GPU         | 188         | 1394         |

### Análise dos Resultados:
- O algoritmo **sequencial** apresentou desempenho estável, servindo como base de comparação.
- O paralelismo na **CPU mostrou ganhos significativos** com 4 threads, atingindo o melhor tempo. A partir de 8 threads, o tempo não melhorou, indicando saturação do hardware (overhead de gerenciamento das threads supera o ganho).
- O paralelismo na **GPU teve desempenho abaixo**, devido ao tempo de **"warm-up" do kernel OpenCL**, tempo de **transferência dos dados da CPU para a GPU**, e à complexidade de verificação de palavras isoladas, que envolve muitas instruções condicionais, prejudicando a performance em GPUs.
- A quantidade de ocorrências encontradas foi correta em todos os métodos, validando a precisão do algoritmo.

---

## Conclusão
Através dos testes, foi possível observar que:
- O paralelismo na CPU é altamente eficiente até certo limite de threads, sendo fácil de implementar e com excelente ganho de desempenho.
- A GPU, apesar de sua capacidade massiva de paralelização, não obteve o melhor desempenho neste problema específico, devido ao overhead de inicialização, transferência de dados e complexidade lógica que não se beneficia tanto da arquitetura SIMD da GPU.
- A análise destaca a importância de escolher a arquitetura correta conforme a natureza do problema. GPUs são altamente eficazes em tarefas matemáticas intensivas e pouco condicionais, mas podem ser menos eficientes em problemas com muita lógica condicional, como busca de palavras isoladas em texto.

---

## Referências
- Silva, R. A. da. *Programação Paralela e Distribuída: conceitos, técnicas e ferramentas*. 1ª ed. Rio de Janeiro: LTC, 2017.
- OpenCL Specification. [https://registry.khronos.org/OpenCL/](https://registry.khronos.org/OpenCL/)
- Oracle Documentation - Java Threads. [https://docs.oracle.com/javase/tutorial/essential/concurrency/](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- Material e aulas da disciplina de Computação Paralela e Recorrente – Universidade.

---

## ⚙️ Como Compilar e Executar

### Pré-requisitos

1. **Java 8 ou superior instalado**.
2. **Drivers com suporte a OpenCL** instalados na máquina:
   - **NVIDIA**: [Baixar driver mais recente](https://www.nvidia.com/Download/index.aspx?lang=pt)
   - **AMD**: [Baixar driver](https://www.amd.com/pt/support)
   - **Intel** (para processadores com gráficos integrados): [Intel OpenCL Runtime](https://www.intel.com/content/www/us/en/developer/tools/opencl/opencl-runtime.html)

> Caso contrário, a execução da versão GPU resultará no erro:  
> `CL_PLATFORM_NOT_FOUND_KHR`

3. **Bibliotecas incluídas** no projeto (pasta `lib/`):
   - `jocl-2.0.4.jar`
   - `jfreechart-1.0.19.jar`
   - `jcommon-1.0.23.jar`

---

### Compilar o Projeto

Abra o terminal na raiz do projeto e execute:

```bash
javac -cp "lib/*" -d bin src/*.java
```
---

### Compilar o Projeto
```bash
java -cp "bin;lib/*" Main
```

---

### Observações Importantes
- O código tentará executar a contagem na GPU usando OpenCL via JOCL. Certifique-se de que sua máquina tem suporte a OpenCL.

- Caso o erro **CL_PLATFORM_NOT_FOUND_KHR** apareça, verifique se os drivers da GPU estão atualizados e se o OpenCL Runtime está corretamente instalado.

- O gráfico gerado com JFreeChart será exibido em uma janela separada ao final da execução.

---

### 🔗 Repositório no GitHub
👉 [Acessar repositório no GitHub](https://github.com/angelo-kaue/Trab-Av3-Compt-Paralela)
