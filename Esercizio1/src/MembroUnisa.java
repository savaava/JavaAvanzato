public abstract sealed class MembroUnisa extends Persona permits Studente,Docente{
    private final String matricola;

    public MembroUnisa(String codiceFiscale, String nomeCompleto, String matricola){
        super(codiceFiscale, nomeCompleto);
        this.matricola = matricola;
    }

    public String getMatricola() {
        return matricola;
    }

    @Override
    public int hashCode(){
        return matricola.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null) return false;
        if(obj==this) return true;
        if(obj.getClass()!=MembroUnisa.class) return false;

        MembroUnisa mObj = (MembroUnisa) obj;
        return mObj.matricola.equals(matricola);
    }

    @Override
    public String toString(){
        return super.toString()+" - "+matricola;
    }
}
