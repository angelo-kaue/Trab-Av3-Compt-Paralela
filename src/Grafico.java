import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

//classe responsável por gerar gráficos de barras para comparar desempenho dos algoritmos
public class Grafico {

    public static void gerarGrafico(String palavra, int tempoSerial, int tempoParaleloCPU2, int tempoParaleloCPU4, int tempoParaleloCPU8, int tempoParaleloGPU) {
        DefaultCategoryDataset dados = new DefaultCategoryDataset();

        dados.addValue(tempoSerial, "Execução Serial (CPU)", "Serial");
        dados.addValue(tempoParaleloCPU2, "Execução Paralela CPU (2 Threads)", "Paralelo CPU 2");
        dados.addValue(tempoParaleloCPU4, "Execução Paralela CPU (4 Threads)", "Paralelo CPU 4");
        dados.addValue(tempoParaleloCPU8, "Execução Paralela CPU (8 Threads)", "Paralelo CPU 8");
        dados.addValue(tempoParaleloGPU, "Execução Paralela (GPU)", "Paralelo GPU");

        JFreeChart graficoBarras = ChartFactory.createBarChart(
                "Comparação de Desempenho - Palavra: " + palavra,
                "Tipo de Execução",
                "Tempo (ms)",
                dados
        );

        ChartFrame janelaGrafico = new ChartFrame("Gráfico de Desempenho", graficoBarras);
        janelaGrafico.setSize(900, 600);
        janelaGrafico.setVisible(true);
    }
}