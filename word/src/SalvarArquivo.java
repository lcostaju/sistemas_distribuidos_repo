import java.io.File;
import java.io.PrintWriter;

public class SalvarArquivo implements Runnable {

    private StringBuffer buffer;

    private static final String PATH = "C:\\Users\\lucio\\Downloads\\SAVE.TXT";

    public SalvarArquivo(StringBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            File file = new File(PATH);
            PrintWriter pw = new PrintWriter(file);
            pw.write(buffer.toString());
            pw.close();
            System.out.println("Arquivo salvo em: " + PATH);
        } catch (Exception e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

}
