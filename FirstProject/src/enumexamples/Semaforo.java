package enumexamples;

import static java.lang.System.out;

public class Semaforo {
    Colore c;

    public Semaforo(Colore c){
        this.c = c;
    }

    public void indicazioni() {
        /* costrutto switch classico: */
        switch(c){
            case NERO:
                out.println("Semaforo spento");
                break;
            case ROSSO:
                out.println("Attenzione non attraversare");
                break;
            case GIALLO:
                out.println("Liberare in fretta l'incrocio");
                break;
            case VERDE:
                out.println("E' possibile attraversare l'incrocio");
                break;
            default:
                out.println("wewe");
                break;
        }

        /* sintassi più compatta e risolve il problema del break trough per cui se
        * non metto break controlla comunque tutti i case
        * per i raggruppamenti si può fare case GIALLO,VERDE */
        switch(c){
            case NERO -> out.println("Semaforo spento");
            case ROSSO -> {
                int i = 0;
                out.println("Attenzione non attraversare "+i);
            }
            case GIALLO,VERDE -> out.println("E' possibile attraversare l'incrocio");
            default -> out.println("wewe");
        }

        /* Espressione switch: può restituire un valore di ritorno */
    }

    public void indicazioniSwitchStatement() {
        var nome = "Mario";

        /* keyword var -> assume il tipo di dato in base al ritorno
        * si usa spesso con switch statement */
        /* A differenza di Object, var deduce il tipo preciso e diventa int o String
        * in questo caso.
        * - non possono essere utilizzati come parametri del metodo ad esempio per l'overload
        * perchè di deve specificare il tipo */
        var s = switch(c){
            case NERO -> 0;
            case ROSSO -> {
                int i = 0;
                yield "Attenzione non attraversare "+i;
            }
            case GIALLO,VERDE -> "E' possibile attraversare l'incrocio";
        }; /* visto che è un'espressione si mette ; alla fine perchè è un'inizializzazione
         di variabile
         PROPRIETA' di esaustività: obbligo di avere sempre tutti i casi coperti (quindi
         si potrebbe mettere default per coprirli tutti) */
        out.println(s);

        /* jdk>17 il case può valutare non solo rispetto al valore ma anche
         sulla gerarchia */
    }
}
