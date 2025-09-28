public class App {
    public static void main(String[] args) {
        int cadeiras = 3;
        System.out.println("=== BARBEARIA INICIADA ===");
        System.out.println("Número de cadeiras na sala de espera: " + cadeiras);
        
        Barbearia barbearia = new Barbearia(cadeiras);
        Barbeiro barbeiro = new Barbeiro(barbearia);
        System.out.println("Barbeiro iniciado e está dormindo...");
        barbeiro.start();
        System.out.println("\nCriando 10 clientes...");
        for (int i = 1; i <= 10; i++) {
            Cliente cliente = new Cliente(barbearia, "Cliente-" + i);
            System.out.println("Cliente-" + i + " chegou na barbearia");
            cliente.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
