import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private static List<ServidorSocketThread> clientes = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        try (ServerSocket s = new ServerSocket(50000,10)) {
            while (true) { 
                System.out.println("Esperando conexão... ");
                Socket con = s.accept();
                System.out.println("Conexão estabelecida");
                ServidorSocketThread socketThread = new ServidorSocketThread(con);
                adicionarCliente(socketThread);
                new Thread(socketThread).start();
            }
        }        
    }
    
    public static synchronized void adicionarCliente(ServidorSocketThread cliente) {
        clientes.add(cliente);
    }
    
    public static synchronized void removerCliente(ServidorSocketThread cliente) {
        clientes.remove(cliente);
    }
    
    public static synchronized void broadcast(String mensagem, ServidorSocketThread remetente) {
        for (ServidorSocketThread cliente : clientes) {
            if (cliente != remetente) {
                cliente.enviarMensagem(mensagem);
            }
        }
    }
}
