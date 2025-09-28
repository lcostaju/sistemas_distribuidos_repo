import java.util.concurrent.atomic.AtomicInteger;

public class GerenciadorLock {
    private final BancoDados bancoDados;
    private final AtomicInteger tentativasLeitura = new AtomicInteger(0);
    private final AtomicInteger tentativasEscrita = new AtomicInteger(0);
    private final AtomicInteger leiturasBemSucedidas = new AtomicInteger(0);
    private final AtomicInteger escritasBemSucedidas = new AtomicInteger(0);
    
    public GerenciadorLock(BancoDados bancoDados) {
        this.bancoDados = bancoDados;
    }
    
    public void relatorioStatus() {
        System.out.println("\n=== RELATORIO DE STATUS ===");
        System.out.println("Versao atual do banco: " + bancoDados.getVersao());
        System.out.println("Escritor ativo: " + (bancoDados.isEscritorAtivo() ? "SIM" : "NAO"));
        System.out.println("Leitores ativos: " + bancoDados.getContadorLeitores());
        System.out.println("Tentativas de leitura: " + tentativasLeitura.get());
        System.out.println("Leituras bem-sucedidas: " + leiturasBemSucedidas.get());
        System.out.println("Tentativas de escrita: " + tentativasEscrita.get());
        System.out.println("Escritas bem-sucedidas: " + escritasBemSucedidas.get());
        System.out.println("============================\n");
    }
    
    public boolean executarLeitura(String nomeLeitor) {
        tentativasLeitura.incrementAndGet();
        try {
            if (bancoDados.tentarIniciarLeitura()) {
                leiturasBemSucedidas.incrementAndGet();
                return true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(nomeLeitor + " foi interrompido durante tentativa de leitura");
        }
        return false;
    }
    
    public boolean executarEscrita(String nomeEscritor) {
        tentativasEscrita.incrementAndGet();
        try {
            if (bancoDados.tentarIniciarEscrita()) {
                escritasBemSucedidas.incrementAndGet();
                return true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(nomeEscritor + " foi interrompido durante tentativa de escrita");
        }
        return false;
    }
    
    public void demonstrarConcorrencia() {
        System.out.println("\n=== DEMONSTRACAO DE CONCORRENCIA ===");
        
        System.out.println("1. Multiplos leitores podem acessar simultaneamente:");
        System.out.println("   - Leitor1, Leitor2, Leitor3 podem ler ao mesmo tempo");
        System.out.println("   - Nao ha conflito entre leituras");
        
        System.out.println("\n2. Escritor tem acesso exclusivo:");
        System.out.println("   - Quando um escritor esta ativo, nenhum leitor pode acessar");
        System.out.println("   - Apenas um escritor por vez");
        
        System.out.println("\n3. Lock otimista com controle de versao:");
        System.out.println("   - Cada escrita incrementa a versao do banco");
        System.out.println("   - Leitores podem verificar se os dados mudaram");
        
        System.out.println("======================================\n");
    }
}