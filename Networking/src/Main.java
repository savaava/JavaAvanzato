public class Main {
    public static void main(String[] args) throws Exception {
        //m1();

        Server s = new Server(12100,System.out::println);
        s.connect();
        /* E' molto basic questa implementazione, si dovrebbe considerare che un
        * singolo server abbia più connessioni sulla stessa porta */
    }

    private static void m1() throws Exception {
        Server s = new Server(12100, msg -> System.out.println("Ricevuto: "+msg));
        s.connect();
        System.out.println("server in attesa di connessione");
        Thread.sleep(2*1000);

        Client c = new Client("127.0.0.1",12100,msg -> System.out.println("Ricevuto: "+msg));
        c.connect(); /* si ferma a ServerSocket(port).accept() fino a quando
         non arriva un client che si prova a connettere */
        System.out.println("client in attesa di connessione");
        Thread.sleep(2*1000);

        c.sendMsg("hello");
        Thread.sleep(1000);
        s.sendMsg("world");
        Thread.sleep(1000);
        c.sendMsg("bye");

        c.disconnect();
        s.disconnect();
    }
}