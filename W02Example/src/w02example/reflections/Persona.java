package w02example.reflections;

public class Persona {
    private String nome;
    private String cognome;
    private String codiceFiscale;

    public Persona(String nome, String cognome, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Persona persona = (Persona) o;
        return codiceFiscale.equals(persona.codiceFiscale);
    }

    @Override
    public int hashCode() {
        return codiceFiscale.hashCode();
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", codiceFiscale='" + codiceFiscale + '\'' +
                '}';
    }
}
