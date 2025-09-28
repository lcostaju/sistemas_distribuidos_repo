import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BancoDados {
    private AtomicReference<String> dados = new AtomicReference<>("Dados iniciais");
    private AtomicInteger versao = new AtomicInteger(0);
    private volatile boolean escritorAtivo = false;
    private AtomicInteger contadorLeitores = new AtomicInteger(0);
    
    public synchronized boolean tentarIniciarLeitura() throws InterruptedException {
        while (escritorAtivo) {
            wait();
        }
        contadorLeitores.incrementAndGet();
        return true;
    }
    
    public synchronized void finalizarLeitura() {
        contadorLeitores.decrementAndGet();
        notifyAll();
    }
    
    public synchronized boolean tentarIniciarEscrita() throws InterruptedException {
        while (escritorAtivo || contadorLeitores.get() > 0) {
            wait();
        }
        escritorAtivo = true;
        return true;
    }
    
    public synchronized void finalizarEscrita() {
        escritorAtivo = false;
        versao.incrementAndGet();
        notifyAll();
    }
    
    public String lerDados(String nomeLeitor) {
        String dadosLidos = dados.get();
        System.out.println("[" + System.currentTimeMillis() + "] " + nomeLeitor + 
                          " leu: '" + dadosLidos + "' (versao " + versao.get() + 
                          ", leitores ativos: " + contadorLeitores.get() + ")");
        return dadosLidos;
    }
    
    public void escreverDados(String novosDados, String nomeEscritor) {
        String dadosAntigos = dados.get();
        dados.set(novosDados);
        System.out.println("[" + System.currentTimeMillis() + "] " + nomeEscritor + 
                          " alterou de '" + dadosAntigos + "' para '" + novosDados + 
                          "' (nova versao: " + (versao.get() + 1) + ")");
    }
    
    public int getVersao() {
        return versao.get();
    }
    
    public int getContadorLeitores() {
        return contadorLeitores.get();
    }
    
    public boolean isEscritorAtivo() {
        return escritorAtivo;
    }
}