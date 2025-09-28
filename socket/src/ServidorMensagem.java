import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorMensagem implements Observador {

    private List<ServidorSocketThread> conexoes = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServidorMensagem servidor = new ServidorMensagem();

        try (
                ServerSocket serverSocket = new ServerSocket(5000, 10000);) {
            System.out.println("Servidor Iniciado, aguardando conexão...");
            while (true) {
                Socket conexao = serverSocket.accept();
                System.out.println("Conexão estabelecida!");
                ServidorSocketThread thread = new ServidorSocketThread(conexao,servidor);

            }
        }

    }

    @Override
    public void enviaMenssagem(String mensagem) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enviaMenssagem'");
    }

}
