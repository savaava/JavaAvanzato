package com.records;

public record Studente(String nome, String cognome, String matricola) implements Comparable<Studente> {
    private static int count = 0;

    // Costruttore canonico
    //public Studente(String nome, String cognome, String matricola)

    // Costruttore canonico compatto
    public Studente{
        if(!matricola.matches("^\\d{9}$"))
            throw new IllegalArgumentException();
    }

    // Costruttore non canonico
    public Studente(String matricola) {
        this("no name","no surname",matricola);
    }

    // Costruttore non canonico
    public Studente(String nome, String cognome, CorsoDiStudi cds) {
        this(nome, cognome, cds.getPrefisso()+String.format("%04d",++count));
    }

    @Override
    public int compareTo(Studente o) {
        return matricola.compareTo(o.matricola);
    }
}

enum CorsoDiStudi {
    INFORMATICA("061270"),
    MATEMATICA("MA"),
    FISICA("FI"),
    CHIMICA("CH"),
    BIOLOGIA("BI");

    private final String prefisso;

    CorsoDiStudi(String prefisso) {
        this.prefisso = prefisso;
    }

    public String getPrefisso() {
        return prefisso;
    }
}
