package com.savaava;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {
    /*
    * Anche con gli Optional è possibile applicare filter, map, ...
    * con la sola differenza che nel caso di uno stream si ha una corrispondenza per ogni
    * elemento della collezione nel capo dell'optional si ha naturalmente la corrispondenza
    * del singolo elemento
    * */

    public static void main(String[] args) {
        Optional<Genere> genere = Optional.ofNullable(Genere.AZIONE);
        genere.map(g -> g.toString().length())
                .filter(i -> i>5)
                .isPresent();

        List<String> l = List.of("uno", "due", "tre", "quattro", "cinque", "sei", "sette");

        l.stream()
                .map(String::length)
                .filter(len -> len>3)
                .forEach(len -> System.out.println("Curr len: "+len));

        int x = l.stream()
                .mapToInt(String::length)
                .filter(len -> len>3)
                .sum();
        System.out.println(x);

        double d = l.stream()
                .mapToDouble(s -> s.length()/2.0)
                .filter(value -> value>2.5)
                .average()
                .getAsDouble();
        System.out.println(d);

        double d2 = l.stream()
                .mapToDouble(s -> s.length()/2.0)
                .filter(value -> value>3.5)
                .average()
                .orElseGet(() -> 0.0);
        System.out.println(d2);

        System.out.println( getTitoloMaiuscolo(null) );
        System.out.println( getTitoloMaiuscolo("wewe") );

        //m();
    }
    public static String getTitoloMaiuscolo(String titolo) {
        Optional<String> opt = Optional.ofNullable(titolo);
        return opt.orElse("NESSUN TITOLO").toUpperCase();
    }

    private static void m(){
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


        Stream<Film> s2 = v.stream()
                .filter(f -> f.getGenere()==Genere.COMMEDIA);
        s2.forEach(f -> f.setValutazione(5)); /* VIENE ALTERATA LA COLLEZIONE E QUESTO E' SCONSIGLIATO */
        System.out.println(v);


        int sum = v.stream()
                .mapToInt(Film::getValutazione)
                .sum();
        System.out.println(sum);


        double av = v.stream()
                .mapToDouble(Film::getValutazione)
                .average()
                .orElseGet(() -> 0.0);
        System.out.println(av);


        List<?> titoli = v.stream()
                .map(Film::getGenere)
                .toList();
        System.out.println(titoli);


        List<?> parole = v.stream()
                .flatMap(f -> Stream.of(f.getTitolo().split(" ")))
                .toList();
        System.out.println(parole);

        Set<?> s = v.stream()
                .filter(film -> film.getGenere()==Genere.COMMEDIA)
                .map(Film::getTitolo)
                .collect(Collectors.toCollection(TreeSet::new));
        /* IMPORTANTE: Collectors Collect per ottenere altre collezioni e non una lista da toList() */
    }
}
