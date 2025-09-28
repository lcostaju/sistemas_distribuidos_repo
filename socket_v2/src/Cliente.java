
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    public static void main(String args[]) throws Exception {
        Socket socket = new Socket("127.0.0.1",50000);

        DataInputStream input = new DataInputStream(socket.getInputStream()); 
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        Thread leitorMensagens = new Thread(() -> {
            try {
                String mensagem;
                while ((mensagem = input.readUTF()) != null) {
                    System.out.println(mensagem);
                }
            } catch (Exception e) {
                System.out.println("Conexão encerrada");
            }
        });
        leitorMensagens.start();

        String linha;
        while ((linha = teclado.readLine()) != null) {
            output.writeUTF(linha);
            if (linha.equals("/sair")) {
                break;
            }
        }
        
        socket.close();
    }
}
