import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("=== EXERCICIO LEITURA E ESCRITA COM LOCK OTIMISTA ===\n");
        
        BancoDados bancoDados = new BancoDados();
        GerenciadorLock gerenciador = new GerenciadorLock(bancoDados);
        
        gerenciador.demonstrarConcorrencia();
        
        List<Thread> threads = new ArrayList<>();
        
        System.out.println("Iniciando simulacao com multiplos leitores e escritores...\n");
        
        Leitor leitor1 = new Leitor(bancoDados, "LEITOR-1", 3);
        Leitor leitor2 = new Leitor(bancoDados, "LEITOR-2", 3);
        Leitor leitor3 = new Leitor(bancoDados, "LEITOR-3", 2);
        
        Escritor escritor1 = new Escritor(bancoDados, "ESCRITOR-1", 2);
        Escritor escritor2 = new Escritor(bancoDados, "ESCRITOR-2", 2);
        
        threads.add(leitor1);
        threads.add(leitor2);
        threads.add(leitor3);
        threads.add(escritor1);
        threads.add(escritor2);
        
        for (Thread thread : threads) {
            thread.start();
            Thread.sleep(200);
        }
        
        Thread monitorThread = new Thread(() -> {
            try {
                for (int i = 0; i < 15; i++) {
                    Thread.sleep(3000);
                    gerenciador.relatorioStatus();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        monitorThread.start();
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        monitorThread.interrupt();
        
        System.out.println("\n=== SIMULACAO FINALIZADA ===");
        gerenciador.relatorioStatus();
        
        System.out.println("DEMONSTRACAO CONCLUIDA:");
        System.out.println("- Multiplos leitores acessaram simultaneamente");
        System.out.println("- Escritores tiveram acesso exclusivo");
        System.out.println("- Nao houve deadlock");
        System.out.println("- Lock otimista implementado com controle de versao");
    }
}
