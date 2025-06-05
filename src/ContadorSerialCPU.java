import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//classe que realiza a contagem sequencial (sem paralelismo) de uma palavra em um arquivo
public class ContadorSerialCPU {

    //método que lê o arquivo e conta quantas vezes a palavra aparece
    public static int contarPalavra(String caminhoArquivo, String palavraBuscada) throws IOException {
        String conteudo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
        String[] palavras = conteudo.split("\\W+"); //divide o texto por qualquer caractere que não seja letra ou número

        int contador = 0;
        for (String palavra : palavras) {
            if (palavra.equalsIgnoreCase(palavraBuscada)) {
                contador++;
            }
        }

        return contador;
    }
}
