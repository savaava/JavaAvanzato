package com.functional_interfaces;

import java.util.function.Supplier;

public class MainFunc {
    public static void main(String[] args) throws Exception {
        Supplier<Prova> provaSupplier = Prova::new;
        Prova p = provaSupplier.get();

        new Thread(p::stampa).start();
        new Thread(Prova::stampaProva).start();
    }
}

class Prova{
    private String str;

    public Prova(){
        this("No String");
    }

    public Prova(String str){
        this.str = str;
    }

    public void stampa(){
        System.out.println("Sono un'istanza della classe Prova -> "+str);
    }

    public static void stampaProva(){
        System.out.println("Classe statica Prova");
    }
}