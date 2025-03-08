package enumexamples;

public class Main {
    public static void main(String[] args) {
        /* in realtà sto instanziando un oggetto, quindi un Enum è una classe */
        Colore c = Colore.GIALLO;

        System.out.println(c.getRGB());

        Semaforo s = new Semaforo(c);
        s.indicazioni();

        new Semaforo(Colore.ROSSO).indicazioniSwitchStatement();


        int p[] = new int[3];
        //var p1[];
        /* var -> non si può avere più tipi di dati con la C syntax:
        noi sappiamo che l'array è una classe */
        var p1 = new int[3];
    }
}
