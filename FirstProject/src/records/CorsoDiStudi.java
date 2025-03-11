package records;

public enum CorsoDiStudi {
    LT("06127"),
    LM("02270"),
    PhD("88500");

    private final String prefisso;

    CorsoDiStudi(String prefisso) {
        this.prefisso = prefisso;
    }

    public String getPrefisso() {
        return prefisso;
    }
}
