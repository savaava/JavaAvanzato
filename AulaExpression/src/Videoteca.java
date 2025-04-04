import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Videoteca {
    private Set<Film> videoteca;

    public Videoteca(){
        videoteca=new HashSet<>();
    }

    public void aggiungi(Film f){
        videoteca.add(f);
    }

    //metodo che filtra la videoteca--> restituisce un sottoinsime di film
    //definire un interfaccia che ci iconsete di implemntare un filtro film
    //scorre tutti gli elementi e restituisce la sottocollezione
    //deve avere un filtro come parametro
    public Videoteca filtra(FiltroFilm ff){
        Videoteca vc=new Videoteca();
        for(Film f: videoteca){
            if(ff.verifica(f))
                vc.aggiungi(f);
        }
        return vc;
    }

    /*
    E' l'equivalente generico di FiltroFilm
    In più Predicate definisce anche metodi di default
    che mi permettono di concatenare filtri con or, and

    public Videoteca filtra(Predicate<Film> ff){
        Videoteca vc=new Videoteca();
        for(Film f: videoteca){
            if(ff.test(f))
                vc.aggiungi(f);
        }
        return vc;
    }
    * */

    /* aggiorna seconda la mia interfaccia func */
    public void aggiorna(AggiornaFilm af) {
        /* aggiorno tutta la lista */
        for(Film f: videoteca){
            af.aggiornaFilm(f);
        }
    }

    /*
    E' l'equivalente generico di AggiornaFilm

    public void aggiorna(Consumer<Film> af) {
        for(Film f: videoteca){
        af.accept(f);
    }
}
    * */

    public <T> Collection<T> estraiCampi(EstraiCampo<T> ec){
        Collection<T> c = new ArrayList<>();

        for(Film f: videoteca){
            c.add(ec.estrai(f));
        }

        return c;
    }

    public Stream<Film> stream(){
        return videoteca.stream();
    }

    @Override
    public String toString() {
        StringBuffer strb = new StringBuffer("Videoteca:\n");
        videoteca.forEach(fi -> strb.append(fi).append("\n"));
        return strb.toString();
    }
}
