import java.util.Random;

public class Escritor extends Thread {
    private final BancoDados bancoDados;
    private final String nome;
    private final int numeroOperacoes;
    private final Random random = new Random();
    private int contadorEscrita = 1;
    
    public Escritor(BancoDados bancoDados, String nome, int numeroOperacoes) {
        this.bancoDados = bancoDados;
        this.nome = nome;
        this.numeroOperacoes = numeroOperacoes;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= numeroOperacoes; i++) {
            try {
                System.out.println("[" + System.currentTimeMillis() + "] " + nome + 
                                 " tentando iniciar escrita " + i);
                
                if (bancoDados.tentarIniciarEscrita()) {
                    System.out.println("[" + System.currentTimeMillis() + "] " + nome + 
                                     " iniciou escrita " + i + " (ACESSO EXCLUSIVO)");
                    
                    Thread.sleep(random.nextInt(1500) + 1000);
                    
                    String novosDados = "Dados modificados por " + nome + " #" + contadorEscrita++;
                    bancoDados.escreverDados(novosDados, nome);
                    
                    bancoDados.finalizarEscrita();
                    System.out.println("[" + System.currentTimeMillis() + "] " + nome + 
                                     " finalizou escrita " + i + " (LIBERANDO ACESSO)");
                }
                
                Thread.sleep(random.nextInt(3000) + 2000);
                
            } catch (InterruptedException e) {
                System.out.println(nome + " foi interrompido");
                break;
            }
        }
        System.out.println("[" + System.currentTimeMillis() + "] " + nome + " finalizou todas as operacoes");
    }
}