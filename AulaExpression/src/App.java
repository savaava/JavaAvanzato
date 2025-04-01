import java.util.Collection;

public class App {
    public static void main(String[] args) {
        Videoteca v = new Videoteca();
        v.aggiungi(new Film("Fast and Furious", Genere.AZIONE, 7));
        v.aggiungi(new Film("Hulk", Genere.AZIONE, 10));
        v.aggiungi(new Film("La vita è bella", Genere.DRAMMATICO, 8));
        v.aggiungi(new Film("Beveniti al sub", Genere.COMMEDIA, 7));
        v.aggiungi(new Film("Sharkando", Genere.AZIONE, 4));

        System.out.println(v);
        Videoteca v2 = v.filtra(f -> f.getGenere().equals(Genere.AZIONE)).filtra(f -> f.getValutazione()>7);
        System.out.println(v2);

        v.aggiorna(f -> f.setValutazione(8));
        System.out.println(v);

        /* metodo d'istanza di un certo tipo (Film) ->
        * estraiCampi accetta film e restituisce un tipo quindi i get
        * vanno bene */
        EstraiCampo<Genere> ec = f -> f.getGenere();
        System.out.println(
                v.estraiCampi(ec)
        );

        Collection<?> c = v.estraiCampi(f -> f.getGenere());
        System.out.println(c);

        /* ----------------------- */

        System.out.println(
                v.filtra(Filtri::isBelFilm)
        );

        Filtri filtri = new Filtri();
        System.out.println(
                v.filtra(filtri::isFantascienza)
        );

        Collection<?> c2 = v.estraiCampi(Film::getGenere);

        /*
        * Supplier serve soprattuto quando ci serve un'istanza runtime
        *  */
    }
}
