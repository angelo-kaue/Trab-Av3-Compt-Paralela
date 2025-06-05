import java.io.FileWriter;
import java.io.IOException;

public class CSVs {

    public static void gravarResultado(String nomeArquivo, String nomeAlgoritmo, String palavra, int quantidadeOcorrencias, long tempoExecucao) {
        try (FileWriter escritor = new FileWriter(nomeArquivo, true)) {
            escritor.append(nomeAlgoritmo)
                    .append(",")
                    .append(palavra)
                    .append(",")
                    .append(String.valueOf(quantidadeOcorrencias))
                    .append(",")
                    .append(String.valueOf(tempoExecucao))
                    .append("ms\n");
        } catch (IOException erro) {
            erro.printStackTrace();
        }
    }
}
