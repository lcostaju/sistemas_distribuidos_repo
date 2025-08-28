public class App {
    public static void main(String[] args) throws Exception {
        Corrida schumacher = new Corrida("Schomacher");
        Corrida sena = new Corrida("Sena");
        Corrida barrichello = new Corrida("Barrichello");

        Thread thread = new Thread(schumacher);
        Thread thread1 = new Thread(sena);
        Thread thread2 = new Thread(barrichello);

        thread.start();
        thread1.start();
        thread2.start();

        thread.join();
        thread1.join();
        thread2.join();

        System.out.println("Acabou a corrida");

    }
}
