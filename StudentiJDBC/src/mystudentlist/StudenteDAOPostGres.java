package mystudentlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudenteDAOPostGres implements StudenteDAO {
    private final String URL = "jdbc:postgresql://localhost:5432/studentiDB";
    private final String USER = "postgres";
    private final String PASS = "postAndrea.55";

    @Override
    public boolean inserisci(Studente s) throws Exception {
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                Statement st = conn.createStatement();
        ){
            if(cerca(s.getMatricola())!=null){
                System.out.println("Studente con matricola "+s.getMatricola()+" già presente -> impossibile inserire");
                return false;
            }

            String update = String.format(
                    "INSERT INTO studente VALUES ('%s', '%s', '%s');",
                    s.getMatricola(),s.getNome(),s.getCognome()
            );

            st.executeUpdate(update);
        }

        return true;
    }

    @Override
    public void rimuovi(Studente s) throws Exception {
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                Statement st = conn.createStatement();
        ){
            String update = String.format(
                    "DELETE FROM studente WHERE matricola = '%s';",
                    s.getMatricola()
            );

            st.executeUpdate(update);
        }
    }

    @Override
    public void aggiorna(Studente s) throws Exception {
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                Statement st = conn.createStatement();
        ){
            String update = String.format(
                    "UPDATE studente SET nome='%s', cognome='%s' WHERE matricola='%s';",
                    s.getNome(),s.getCognome(),s.getMatricola()
            );

            st.executeUpdate(update);
        }
    }

    @Override
    public Studente cerca(String matricola) throws Exception {
        Studente sOut = null;

        String query = "SELECT * FROM studente WHERE matricola = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, matricola);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sOut = new Studente(
                            rs.getString("matricola"),
                            rs.getString("nome"),
                            rs.getString("cognome")
                    );
                }
            }
        }

        return sOut;
    }


    @Override
    public List<Studente> elencaTutti() throws Exception {
        List<Studente> studentiOut = new ArrayList<>();

        String query = "SELECT * FROM studente;";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)
        ) {
            while (rs.next()) {
                studentiOut.add(new Studente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("matricola")
                ));
            }
        }

        return studentiOut;
    }

}
