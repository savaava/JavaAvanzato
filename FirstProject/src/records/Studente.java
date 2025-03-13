package records;

public record Studente(String nome, String cognome, String matricola) implements Comparable<Studente> {
    private static int count = 0;

//    public Studente(String nome, String cognome, String matricola){
//        if(!matricola.matches("^\\d{9}$"))
//            throw new IllegalArgumentException();
//
//        this.nome = nome;
//        this.cognome = cognome;
//        this.matricola = matricola;
//    }
    public Studente{
        if(!matricola.matches("^\\d{9}$"))
            throw new IllegalArgumentException();
    }

    public Studente(String nome, String cognome, CorsoDiStudi cds) {
        this(nome, cognome, cds.getPrefisso()+String.format("%04d",++count));
    }

    @Override
    public int compareTo(Studente o) {
        return matricola.compareTo(o.matricola);
    }

    @Override
    public String toString(){
        return nome+cognome+matricola;
    }
}
