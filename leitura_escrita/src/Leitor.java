import java.util.Random;

public class Leitor extends Thread {
    private final BancoDados bancoDados;
    private final String nome;
    private final int numeroOperacoes;
    private final Random random = new Random();
    
    public Leitor(BancoDados bancoDados, String nome, int numeroOperacoes) {
        this.bancoDados = bancoDados;
        this.nome = nome;
        this.numeroOperacoes = numeroOperacoes;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= numeroOperacoes; i++) {
            try {
                System.out.println("[" + System.currentTimeMillis() + "] " + nome + 
                                 " tentando iniciar leitura " + i);
                
                if (bancoDados.tentarIniciarLeitura()) {
                    System.out.println("[" + System.currentTimeMillis() + "] " + nome + 
                                     " iniciou leitura " + i);
                    
                    Thread.sleep(random.nextInt(1000) + 500);
                    
                    bancoDados.lerDados(nome);
                    
                    bancoDados.finalizarLeitura();
                    System.out.println("[" + System.currentTimeMillis() + "] " + nome + 
                                     " finalizou leitura " + i);
                }
                
                Thread.sleep(random.nextInt(2000) + 1000);
                
            } catch (InterruptedException e) {
                System.out.println(nome + " foi interrompido");
                break;
            }
        }
        System.out.println("[" + System.currentTimeMillis() + "] " + nome + " finalizou todas as operacoes");
    }
}