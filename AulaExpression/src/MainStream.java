import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainStream {
    /*
     * non devono alterare la collezione
     * Stream consente la modifica ma è altamente sconsigliata
     * Ottenuto lo stream dalla collezione possiamo effetuare le operazioni intermedie
     * oppure terminali -> dopo le terminali dobbiamo ricrearne un altro se ci serve
     *
     * forEach è un metodo terminale è la parte finale di aggiornamento
     * operazioni di aggregazione che sono naturalmente intermedie
     * (aggregazione, ...) che restituiscono sempre uno stream su cui
     * posso effettuare altre operazioni intermedie
     * Pigra -> fino a quando non faccio un'operazione terminale le operazione
     * secondarie non vengono eseguite
     * Ad esemppio se metto molti filter però senza forEach allora non si eseguono
     * per non sprecare risorse (solo nel momento in cui voglio un risultato finale)
     *
     * toList un metodo terminale per ottenere una collezione filtrata oppure
     * di un solo attributo della collezione (esporta di videoteca)
     *
     * una funzione di mapping -> A ogni elemento dello stream iniziale si associa
     * un elemento allo stream finale
     * Il map è estrai per ottenere uno stream finale di un altro tipo eventualmente
     * getAsDouble() è l'operazione finale e non posso fare direttamente average()
     * getAsDouble perchè average restituisce Optional* perchè nel processo di calcolo della media
     * se ho zero elementi -> la media potrebbe dare errore (la somma no) quindi Optional mi permette
     * di avere un comportamento nel momento in cui si ha un null, senza ottenere l'eccezione
     * quindi Optional è un meccanismo atto a evitare che si producano eccezioni.
     *
     * la differenza tra orElse() e orElseGet() è che orElse crea l'oggetto prima di inziare
     * tutte le operazioni anche se non viene tornato il valore nel caso dell'eccezione (null)
     * è solo una questione di efficienza perchè orElseGet() prendendo un Supplier instanzia
     * l'oggetto solo se effettivamente entra in quella condizione da gestire
     * */

    public static void main(String[] args) {
        class A {
            String s;
            Double i;

            public A(String s, Double i) {
                this.s = s;
                this.i = i;
            }

            public String getS() {return s;}
            public Double getI() {return i;}
        }
        List<A> l = new ArrayList<>();
        l.add(new A("ciao",1.3));
        l.add(new A("wewe", 1.0));
        l.add(new A("wewe", 3.0));
        l.add(new A("wewe", 4.1));
        l.add(new A("okok", 5.2));

        Double d = l.stream()
                .filter(a -> a.getS().equals("wewe"))
                .mapToDouble(A::getI)
                .average()
                .orElseGet(() -> 0.0); /* equivalente a orElse(0.0) ma più efficiente */
        System.out.println(d);




        Videoteca v = new Videoteca();
        v.aggiungi(new Film("Fast and Furious", Genere.AZIONE, 7));
        v.aggiungi(new Film("La vita è bella", Genere.DRAMMATICO, 8));
        v.aggiungi(new Film("La vita non è bella", Genere.DRAMMATICO, 9));
        v.aggiungi(new Film("Beveniti al sub", Genere.COMMEDIA, 7));
        v.aggiungi(new Film("Beveniti al nord", Genere.COMMEDIA, 3));
        v.aggiungi(new Film("Beveniti al nord", Genere.AZIONE, 9));
        v.aggiungi(new Film("Sharkando", Genere.AZIONE, 4));


        Stream<Film> s1 = v.stream()
                .filter(f -> f.getGenere()==Genere.COMMEDIA || f.getGenere()==Genere.DRAMMATICO); /* metodo secondario */
        s1.forEach(System.out::println); /* metodo terminale */
//        s1.forEach(System.out::println); /* è stato già consumato (il metodo terminale è stato già inserito) */


        Stream<Film> s2 = v.stream()
                .filter(f -> f.getGenere()==Genere.COMMEDIA);
        s2.forEach(f -> f.setValutazione(5)); /* VIENE ALTERATA LA COLLEZIONE E QUESTO E' SCONSIGLIATO */
        System.out.println(v);


        int sum = v.stream()
                .mapToInt(Film::getValutazione)
                .sum(); /* sulla somma non c'è problema perchè è sempre effettuabile */
        System.out.println(sum);


        double av = v.stream()
                .mapToDouble(Film::getValutazione)
                .average()
                .orElseGet(() -> 0.0);
        System.out.println(av);


        List<?> titoli = v.stream()
                .map(Film::getTitolo)
                .toList();
        System.out.println(titoli);


        /* se il  */
        List<?> parole = v.stream()
                .flatMap(f -> Stream.of(f.getTitolo().split(" ")))
                .toList();
        System.out.println(parole);

        /* IMPORTANTE: Collectors Collect per ottenere altre collezioni e non una lista da toList() */
    }
}
