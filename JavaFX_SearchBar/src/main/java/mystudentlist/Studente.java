/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mystudentlist;

/**
 *
 * @author lucagreco
 */
public class Studente {
    
    private String nome;
    private String cognome;
    private String matricola;

    public Studente(String nome, String cognome, String matricola) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    @Override
    public String toString() {
        return "Studente{" + "nome=" + nome + ", cognome=" + cognome + ", matricola=" + matricola + '}';
    }
    
    
    
    
    
}
