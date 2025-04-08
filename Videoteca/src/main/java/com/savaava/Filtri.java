package com.savaava;

public class Filtri {
    public static boolean isBelFilm(Film f) {
        return f.getValutazione() > 5;
    }

    public boolean isFantascienza(Film f) {
        return f.getGenere().equals(Genere.FANSTASCIENZA);
    }
}
