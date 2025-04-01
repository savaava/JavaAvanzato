import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Videoteca {
    private List <Film> videoteca;

    public Videoteca(){
        videoteca=new ArrayList<>();
    }

    public void aggiungi(Film f){
        videoteca.add(f);
    }

    //metodo che filtra la videoteca--> restituisce un sottoinsime di film
    //definire un interfaccia che ci iconsete di implemntare un filtro film
    //scorre tutti gli elementi e restituisce la sottocollezione
    //deve avere un filtro come parametro
    public Videoteca filtra(FiltroFilm ff){
        Videoteca vc=new Videoteca();
        for(Film f: videoteca){
            if(ff.verifica(f))  
                vc.aggiungi(f);
        }
        return vc;
    }

    /* aggiorna seconda la mia interfaccia func */
    public void aggiorna(AggiornaFilm af) {
        /* aggiorno tutta la lista */
        for(Film f: videoteca){
            af.aggiornaFilm(f);
        }
    }

    public <T> Collection<T> estraiCampi(EstraiCampo<T> ec){
        Collection<T> c = new ArrayList<>();

        for(Film f: videoteca){
            c.add(ec.estrai(f));
        }

        return c;
    }

    @Override
    public String toString() {
        StringBuffer strb = new StringBuffer("Videoteca:\n");
        videoteca.forEach(fi -> strb.append(fi).append("\n"));
        return strb.toString();
    }
}
