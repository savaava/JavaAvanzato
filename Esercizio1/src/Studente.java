public final class Studente extends MembroUnisa{
    private final double votoMedio;
    private final CorsoDiStudi corsoDiStudi;

    public Studente(String codiceFiscale, String nomeCompleto, String matricola, double votoMedio, CorsoDiStudi corsoDiStudi){
        super(codiceFiscale, nomeCompleto, matricola);
        if(! matricola.startsWith(corsoDiStudi.getCodice()))
            throw new IllegalArgumentException("Matricola e corso di studi non compatibili");
        this.votoMedio = votoMedio;
        this.corsoDiStudi = corsoDiStudi;
    }

    public double getVotoMedio() {
        return votoMedio;
    }
    public CorsoDiStudi getCorsoDiStudi() {
        return corsoDiStudi;
    }

    @Override
    public String toString() {
        return super.toString()+" - "+votoMedio+" - "+corsoDiStudi;
    }
}
