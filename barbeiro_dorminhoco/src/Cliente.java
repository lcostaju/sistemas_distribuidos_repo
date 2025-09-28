public class Cliente extends Thread {
    private final Barbearia barbearia;
    private final String nome;

    public Cliente(Barbearia barbearia, String nome) {
        this.barbearia = barbearia;
        this.nome = nome;
    }

    public void run() {
        if (barbearia.entrarBarbearia(nome)) {
            System.out.println(nome + " está cortando o cabelo...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(nome + " saiu da barbearia (cabelo cortado!)");
        }
    }
}
