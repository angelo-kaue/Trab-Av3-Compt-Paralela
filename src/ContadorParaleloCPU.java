import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ContadorParaleloCPU {

    public static int contarPalavra(String caminhoArquivo, String palavra, int numThreads) throws IOException, InterruptedException, ExecutionException {
        String conteudo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
        String[] palavras = conteudo.split("\\W+"); //separa por caracteres não alfanuméricos

        //usa o número de threads definido
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int tamanhoBloco = palavras.length / numThreads;
        Future<Integer>[] resultados = new Future[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int inicio = i * tamanhoBloco;
            int fim = (i == numThreads - 1) ? palavras.length : (i + 1) * tamanhoBloco;

            resultados[i] = executor.submit(new TarefaContagem(Arrays.copyOfRange(palavras, inicio, fim), palavra));
        }

        int totalOcorrencias = 0;
        for (Future<Integer> resultado : resultados) {
            totalOcorrencias += resultado.get();
        }

        executor.shutdown();
        return totalOcorrencias;
    }

    //classe interna que representa a tarefa de contagem em um trecho do texto
    static class TarefaContagem implements Callable<Integer> {
        private final String[] palavras;
        private final String palavraProcurada;

        public TarefaContagem(String[] palavras, String palavraProcurada) {
            this.palavras = palavras;
            this.palavraProcurada = palavraProcurada;
        }

        @Override
        public Integer call() {
            int contador = 0;
            for (String p : palavras) {
                if (p.equalsIgnoreCase(palavraProcurada)) {
                    contador++;
                }
            }
            return contador;
        }
    }
}