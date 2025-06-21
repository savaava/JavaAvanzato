package com.functional_interfaces;

import java.util.function.Supplier;

public class MainFunc {
    public static void main(String[] args) throws Exception {
        Supplier<Prova> provaSupplier = () -> new Prova("wewewe");
        Prova p = provaSupplier.get();

        new Thread(p::stampa).start();
        new Thread(Prova::stampaProva).start();
    }
}

class Prova{
    private String str;

    public Prova(String str){
        this.str = str;
    }

    public void stampa(){
        System.out.println(str);
    }

    public static void stampaProva(){
        System.out.println("Classe statica Prova");
    }
}