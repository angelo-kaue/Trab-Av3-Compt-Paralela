import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KernelUtils {
    public static String carregarKernel(String caminhoArquivo) {
        try {
            return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar kernel: " + caminhoArquivo, e);
        }
    }
}