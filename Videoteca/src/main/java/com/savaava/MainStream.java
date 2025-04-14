package com.savaava;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {
    public static void main(String[] args) {
        //m1();
        m2();
        //m3();
    }
    public static String getTitoloMaiuscolo1(String titolo) {
        Optional<String> opt = Optional.ofNullable(titolo);
        return opt.orElseGet(() -> "NESSUN TITOLO").toUpperCase();
    }
    public static String getTitoloMaiuscolo2(String titolo) {
        Optional<String> opt = Optional.ofNullable(titolo);
        return opt.orElseThrow(IllegalArgumentException::new).toUpperCase();
    }
    public static Optional<Integer> getLengthIfShort(String s) {
        return s.length() < 5 ? Optional.empty() : Optional.of(s.length());
    }

    private static void m1(){
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

        /*double d3 = l.stream()
                .mapToDouble(s -> s.length()/2.0)
                .filter(value -> value>3.5)
                .average()
                .orElseThrow(IllegalAccessError::new);
        System.out.println(d3);*/

        System.out.println( getTitoloMaiuscolo1("wewe") );
        System.out.println( getTitoloMaiuscolo1(null) );
        System.out.println( getTitoloMaiuscolo2("wewe") );
        //System.out.println( getTitoloMaiuscolo2(null) );

        Film f = new Film("Fast and Furious", Genere.AZIONE, 7);
        Optional<Film> opt = Optional.ofNullable(f);
        boolean isPresent = opt.map(Film::getTitolo)
                .filter(title -> title.contains("and"))
                .isPresent();
        System.out.println(isPresent);

        Optional<String> maybeName = Optional.of("Andrea");
        Optional<Integer> nameLength1 = maybeName.map(String::length);
        Optional<Integer> nameLength2 = maybeName.flatMap(MainStream::getLengthIfShort);
    }
    private static void m2(){
        Stream.ofNullable("ciao").mapToInt(String::length).forEach(System.out::println);

        System.out.println();

        Stream<Integer> pari = Stream.iterate(2, n -> n<12, n -> n+2);
        pari.forEach(System.out::println);
        Stream.iterate(2, n -> n+2).skip(5).limit(5)
                .forEach(System.out::println);

        Stream<Double> numeriCasuali = Stream.generate(Math::random).limit(5);
        numeriCasuali.forEach(System.out::println);

    }
    private static void m3(){
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
