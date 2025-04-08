package com.savaava;

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

    public Videoteca filtra(FiltroFilm ff){
        Videoteca vc=new Videoteca();
        for(Film f: videoteca){
            if(ff.verifica(f))
                vc.aggiungi(f);
        }
        return vc;
    }
    /*
    public Videoteca filtra(Predicate<Film> ff){
        Videoteca vc=new Videoteca();
        for(Film f: videoteca){
            if(ff.test(f))
                vc.aggiungi(f);
        }
        return vc;
    }
    * */

    public void aggiorna(AggiornaFilm af) {
        /* aggiorno tutta la lista */
        for(Film f: videoteca){
            af.aggiornaFilm(f);
        }
    }
    /*
    public void aggiorna(Consumer<Film> af) {
        for(Film f: videoteca){
        af.accept(f);
    }
}   * */

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
