import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Period;

public class ServidorSocketThread implements Runnable {

    private final Socket socketClient;

    public ServidorSocketThread(Socket socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public void run() {
        try (ObjectOutputStream saida = new ObjectOutputStream(socketClient.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socketClient.getInputStream())) {
            while (true) {
                Pedido pedido = (Pedido) entrada.readObject();
                System.out.println("Recebi o pedido: " + pedido);
                saida.writeObject("Objeto recebido");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
