public class Tempo {
    private long tempoInicio;
    private long tempoFim;

    //inicia a contagem do tempo
    public void iniciar() {
        tempoInicio = System.nanoTime();
    }

    //finaliza a contagem do tempo
    public void parar() {
        tempoFim = System.nanoTime();
    }

    public long obterTempoDecorridoEmMilissegundos() {
        return (tempoFim - tempoInicio) / 1_000_000;
    }
}