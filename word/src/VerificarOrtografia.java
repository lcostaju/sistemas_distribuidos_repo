public class VerificarOrtografia implements Runnable {
  private StringBuffer buffer;

    

    public VerificarOrtografia(StringBuffer buffer) {
        this.buffer = buffer;
    }
    @Override
    public void run() {
        while (true) {
          System.out.println("Verificando ortografia "+buffer.toString());
          try {
            Thread.sleep(1500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
    }

}
