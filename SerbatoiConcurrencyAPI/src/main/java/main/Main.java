package main;

import java.util.concurrent.atomic.AtomicInteger;

//5 serbatoi, con volumi di liquido differenti, che una volta che si sono riempiti, devono scaricare il loro contenuto nel serbatoio 6, tramite un condotto condiviso.
//I serbatoi da 1 a 4 possono scaricare solo uno alla volta tra di loro, mentre il quinto ha facoltà di poter scaricare non appena è pieno indipendentemente dagli altri.
// Non appena un serbatoio conclude la sua operazione di scaricamento, deve aggiornare la variabile contatore del serbatoio 6, indicante quanti litri sono stati versati.
// Una volta che si conclude il ciclo di scaricamento da parte di tutti i 5 serbatoi (cioè devono scaricare 1 volta e poi mettersi in attesa, anche se si riempiono nel mentre),
// è possibile effettuare il mescolamento dei liquidi nel serbatoio 6.
public class Main {
    private static int NUM=5;
    private static int EXEC_TIME=60;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        SerbatoioFinale serbatoioFinale = new SerbatoioFinale(NUM+1, NUM, () -> {
            System.out.println("Mescolamento e Versamento ...");
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Operazione di scaricamento terminata !");
        });

        SerbatoioVersante[] serbatoi = new SerbatoioVersante[NUM];
        for(int i=0; i<NUM; i++){
            serbatoi[i] = new SerbatoioVersante(i+1, i+1, i+1==NUM, serbatoioFinale);
            Thread t = new Thread(serbatoi[i]);
            t.setDaemon(true);
            t.start();
        }

        Thread.sleep(EXEC_TIME*1000);
    }
}
