import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barbearia {
    private final int cadeiras;
    private int clientesEsperando = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition barbeiroDormindo = lock.newCondition();
    private final Condition cadeiraBarbeiro = lock.newCondition();
    private boolean barbeiroOcupado = false;

    public Barbearia(int cadeiras) {
        this.cadeiras = cadeiras;
    }

    public void cortarCabelo() {
        lock.lock();
        try {
            while (clientesEsperando == 0) {
                System.out.println("Barbeiro está dormindo... (não há clientes)");
                barbeiroDormindo.await();
            }
            System.out.println("Barbeiro acordou! Cliente da sala de espera vai para a cadeira");
            clientesEsperando--;
            System.out.println("Clientes na sala de espera: " + clientesEsperando);
            barbeiroOcupado = true;
            cadeiraBarbeiro.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        
    System.out.println("Barbeiro está cortando cabelo...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    System.out.println("Corte de cabelo finalizado!");
        
        lock.lock();
        try {
            barbeiroOcupado = false;
        } finally {
            lock.unlock();
        }
    }

    public boolean entrarBarbearia(String nome) {
        lock.lock();
        try {
            if (clientesEsperando == cadeiras) {
                System.out.println(nome + " foi embora (sala de espera lotada - " + cadeiras + "/" + cadeiras + ")");
                return false;
            }
            clientesEsperando++;
            System.out.println(nome + " sentou na sala de espera (" + clientesEsperando + "/" + cadeiras + ")");
            barbeiroDormindo.signal();
            while (barbeiroOcupado) {
                cadeiraBarbeiro.await();
            }
            System.out.println(nome + " está na cadeira do barbeiro");
            barbeiroOcupado = true;
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }
}
