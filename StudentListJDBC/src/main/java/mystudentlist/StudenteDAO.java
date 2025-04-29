package mystudentlist;

import java.util.List;

public interface StudenteDAO {
    void inserisci(Studente s) throws Exception;
    void rimuovi(Studente s) throws Exception;
    void aggiorna(Studente s) throws Exception;
    Studente cerca(String matricola) throws Exception;
    List<Studente> elencaTutti() throws Exception;
}
