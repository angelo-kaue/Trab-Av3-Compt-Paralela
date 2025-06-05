import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        String caminhoArquivo = "Dracula-165307.txt";
        String palavraBuscada = "of";

        try {
            Tempo cronometro = new Tempo();

            //execução Serial
            cronometro.iniciar();
            int totalSerial = ContadorSerialCPU.contarPalavra(caminhoArquivo, palavraBuscada);
            cronometro.parar();
            long tempoSerial = cronometro.obterTempoDecorridoEmMilissegundos();
            System.out.println("SerialCPU: " + totalSerial + " ocorrências em " + tempoSerial + " ms");
            CSVs.gravarResultado("resultados.csv", "SerialCPU", palavraBuscada, totalSerial, tempoSerial);

            //execução Paralela CPU - 2 threads
            cronometro.iniciar();
            int totalParaleloCPU2 = ContadorParaleloCPU.contarPalavra(caminhoArquivo, palavraBuscada, 2);
            cronometro.parar();
            long tempoParaleloCPU2 = cronometro.obterTempoDecorridoEmMilissegundos();
            System.out.println("ParaleloCPU (2 threads): " + totalParaleloCPU2 + " ocorrências em " + tempoParaleloCPU2 + " ms");
            CSVs.gravarResultado("resultados.csv", "ParaleloCPU-2Threads", palavraBuscada, totalParaleloCPU2, tempoParaleloCPU2);

            //execução Paralela CPU - 4 threads
            cronometro.iniciar();
            int totalParaleloCPU4 = ContadorParaleloCPU.contarPalavra(caminhoArquivo, palavraBuscada, 4);
            cronometro.parar();
            long tempoParaleloCPU4 = cronometro.obterTempoDecorridoEmMilissegundos();
            System.out.println("ParaleloCPU (4 threads): " + totalParaleloCPU4 + " ocorrências em " + tempoParaleloCPU4 + " ms");
            CSVs.gravarResultado("resultados.csv", "ParaleloCPU-4Threads", palavraBuscada, totalParaleloCPU4, tempoParaleloCPU4);

            //execução Paralela CPU - 8 threads
            cronometro.iniciar();
            int totalParaleloCPU8 = ContadorParaleloCPU.contarPalavra(caminhoArquivo, palavraBuscada, 8);
            cronometro.parar();
            long tempoParaleloCPU8 = cronometro.obterTempoDecorridoEmMilissegundos();
            System.out.println("ParaleloCPU (8 threads): " + totalParaleloCPU8 + " ocorrências em " + tempoParaleloCPU8 + " ms");
            CSVs.gravarResultado("resultados.csv", "ParaleloCPU-8Threads", palavraBuscada, totalParaleloCPU8, tempoParaleloCPU8);

            //execução Paralela GPU (Simulada)
            cronometro.iniciar();
            int totalParaleloGPU = ContadorParaleloGPU.contarPalavra(caminhoArquivo, palavraBuscada);
            cronometro.parar();
            long tempoParaleloGPU = cronometro.obterTempoDecorridoEmMilissegundos();
            System.out.println("ParaleloGPU: " + totalParaleloGPU + " ocorrências em " + tempoParaleloGPU + " ms");
            CSVs.gravarResultado("resultados.csv", "ParaleloGPU", palavraBuscada, totalParaleloGPU, tempoParaleloGPU);

            //geração do gráfico comparativo
            Grafico.gerarGrafico(
                    palavraBuscada,
                    (int) tempoSerial,
                    (int) tempoParaleloCPU2,
                    (int) tempoParaleloCPU4,
                    (int) tempoParaleloCPU8,
                    (int) tempoParaleloGPU
            );

        } catch (IOException | InterruptedException | ExecutionException e) {
            System.err.println("Ocorreu um erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}