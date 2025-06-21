package com.records;

/* classe corrispondente al record */
public final class StudenteClass {
    private final String nome;
    private final String cognome;
    private final String matricola;

    public StudenteClass(String nome, String cognome, String matricola) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
    }

    public String nome() {
        return nome;
    }

    public String cognome() {
        return cognome;
    }

    public String matricola() {
        return matricola;
    }

    @Override
    public String toString() {
        return "Studente[nome=" + nome + ", cognome=" + cognome + ", matricola=" + matricola + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != this.getClass())
            return false;

        StudenteClass sObj = (StudenteClass) obj;
        return nome.equals(sObj.nome) &&
                cognome.equals(sObj.cognome) &&
                matricola.equals(sObj.matricola);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(nome, cognome, matricola);
        /*
        final int prime = 31;
        int result = 1;
        result = prime * result + nome.hashCode();
        result = prime * result + cognome.hashCode();
        result = prime * result + matricola.hashCode();
        return result;
        */
    }
}