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

    @DaImplementare(value=55, assegnatoA="we", assegnatoB="wewe5")
    private String generaMatricola() {
        return null;
    }

    @Override
    @DaImplementare(55)
    public String toString() {
        return super.toString()+" matricola="+matricola;
    }
}
