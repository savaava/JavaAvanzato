public abstract class Persona {
    private final String codiceFiscale;
    private final String nomeCompleto;

    public Persona(String codiceFiscale, String nomeCompleto) {
        this.codiceFiscale = codiceFiscale;
        this.nomeCompleto = nomeCompleto;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null) return false;
        if(obj==this) return true;
        if(obj.getClass()!=Persona.class) return false;

        Persona pObj = (Persona)obj;
        return pObj.codiceFiscale.equals(codiceFiscale);
    }

    @Override
    public String toString() {
        return codiceFiscale+" - "+nomeCompleto;
    }
}
