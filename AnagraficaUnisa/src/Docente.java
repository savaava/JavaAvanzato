public final class Docente extends MembroUnisa {
    private final String insegnamento;

    public Docente(String codiceFiscale, String nomeCompleto, String matricola, String insegnamento){
        super(codiceFiscale, nomeCompleto, matricola);
        this.insegnamento = insegnamento;
    }

    public String getInsegnamento() {
        return insegnamento;
    }

    @Override
    public String toString() {
        return super.toString()+" - "+insegnamento;
    }
}
