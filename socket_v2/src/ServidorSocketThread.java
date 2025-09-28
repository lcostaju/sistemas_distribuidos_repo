
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServidorSocketThread implements Runnable {

    private Socket socketClient;
    private DataOutputStream saida;
    private String nomeCliente;

    public ServidorSocketThread(Socket socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public void run() {
        try (DataInputStream entrada = new DataInputStream(socketClient.getInputStream())) {
            saida = new DataOutputStream(socketClient.getOutputStream());
            
            saida.writeUTF("Digite seu nome:");
            nomeCliente = entrada.readUTF();
            System.out.println("Cliente conectado: " + nomeCliente);
            
            Servidor.broadcast(nomeCliente + " entrou no chat", this);
            
            String linha;
            while ((linha = entrada.readUTF()) != null && !linha.trim().isEmpty()) {
                if (linha.equals("/sair")) {
                    break;
                }
                String mensagem = nomeCliente + ": " + linha;
                System.out.println(mensagem);
                Servidor.broadcast(mensagem, this);
            }
        } catch(IOException e) {
            System.out.println("Cliente desconectado: " + nomeCliente);
        } finally {
            Servidor.removerCliente(this);
            if (nomeCliente != null) {
                Servidor.broadcast(nomeCliente + " saiu do chat", this);
            }
            try {
                socketClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void enviarMensagem(String mensagem) {
        try {
            if (saida != null) {
                saida.writeUTF(mensagem);
            }
        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem para " + nomeCliente);
        }
    }

}
