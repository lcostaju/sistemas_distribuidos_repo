import java.net.ServerSocket;
import java.net.Socket;

public class ServidorNew {
    public static void main(String[] args) throws Exception {
        try (
            ServerSocket serverSocket = new ServerSocket(50000,10000)
        ){
            System.out.println("Servidor iniciado. Aguardando conexões...");
            while (true) {
                Socket conexao = serverSocket.accept();
                System.out.println("Conexão estabelecida!");
                new Thread(new ServidorSocketThread(conexao),"ThreadConexão").start();
            }
        } 
    }
}
