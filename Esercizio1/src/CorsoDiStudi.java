public enum CorsoDiStudi {
    LT("06127"),
    LM("06227"),
    PhD("8805");

    private final String codice;

    CorsoDiStudi(String codice){
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }
}
