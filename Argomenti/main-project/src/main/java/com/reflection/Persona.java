package com.reflection;

@Info("Classe di esempio per test Reflection")
public class Persona {

    @Info("Campo privato nome")
    private String nome;

    @Info("Campo pubblico età")
    public int eta;

    static int cont = 0;

    @Info("Costruttore senza parametri")
    public Persona() {
        this("Sconosciuto", 0);
    }

    @Info("Costruttore con parametri")
    public Persona(String nome, int eta) {
        this.nome = nome;
        this.eta = eta;
        cont++;
    }

    private void prova(){}

    @Info("Metodo getter per nome")
    public String getNome() {
        return nome;
    }

    @Info("Metodo setter per nome")
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Info("Metodo statico di esempio")
    public static int getContPersona() {
        return cont;
    }

    @Info("Ridefinizione del metodo toString di Object")
    @Override
    public String toString() {
        return "Persona[nome=" + nome + ", eta=" + eta + "]";
    }
}

