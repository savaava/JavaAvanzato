package com.savaava;

public class Film {
    private String titolo;
    private Genere genere;
    private int valutazione;

    public Film(String titolo, Genere genere, int valutazione) {
        this.titolo = titolo;
        this.genere = genere;
        this.valutazione = valutazione;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
   
    public Genere getGenere() {
        return genere;
    }
    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public int getValutazione() {
        return valutazione;
    }
    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return titolo.equals(film.titolo);
    }

    @Override
    public int hashCode() {
        return titolo.hashCode();
    }

    @Override
    public String toString() {
        return "Film [titolo=" + titolo + ", genere=" + genere + ", valutazione=" + valutazione + "]";
    }
}
