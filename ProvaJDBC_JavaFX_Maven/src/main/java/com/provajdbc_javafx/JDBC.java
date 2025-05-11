package com.provajdbc_javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBC {
    private final String URL = "jdbc:postgresql://localhost:5432/studentiDB";
    private final String USER = "postgres";
    private final String PASS = "postAndrea.55";

    public void m() throws Exception {
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                Statement st = conn.createStatement();
        ){
            String update = String.format(
                    "INSERT INTO studente VALUES ('%s', '%s', '%s');",
                    "82131","allora","vediamo"
            );

            st.executeUpdate(update);
        }
    }
}
