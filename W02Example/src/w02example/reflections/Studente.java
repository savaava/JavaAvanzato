package w02example.reflections;

public class Studente extends Persona{
    private String matricola;

    public Studente(String nome, String cognome, String codiceFiscale, String matricola) {
        super(nome, cognome, codiceFiscale);
        this.matricola = matricola;
    }

    public String getMatricola() {
        return matricola;
    }

    @DaImplementare(value = "Mario",assegnatoA = "we",assegnatoB = "wewe")
    private String generaMatricola() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString()+
                " matricola=" + matricola;
    }
}
