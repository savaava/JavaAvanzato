package com.savaava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.*;

public class ElencoAgriturismo {
    private final List<Agriturismo> elenco;

    public ElencoAgriturismo() {
        elenco = new ArrayList<>();
    }

    public void aggiungi(Agriturismo a){
        elenco.add(a);
    }

    public int numeroAgriturismi(){return elenco.size();}

    public <T> Collection<T> esporta(Function<Agriturismo,T> function){
        Collection<T> out = new HashSet<>();

        elenco.forEach(ai ->{
            T attributeToAdd = function.apply(ai);
            out.add(attributeToAdd);
        });

        return out;
    }
    public ElencoAgriturismo filtra(Predicate<Agriturismo> predicate){
        ElencoAgriturismo out = new ElencoAgriturismo();

        elenco.forEach(ai ->{
            if(predicate.test(ai))
                out.aggiungi(ai);
        });

        return out;
    }

    public void ordina(Comparator<Agriturismo> comparator) {
        elenco.sort(comparator);
    }

    public void aggiorna(Consumer<Agriturismo> consumer){
        elenco.forEach(ai -> consumer.accept(ai));
    }

    public int somma(Function<Agriturismo,Integer> function) {
        int cont = 0;

        for(Agriturismo ai : elenco){
           cont += function.apply(ai);
        }

        return cont;
    }

    public static ElencoAgriturismo caricaCSV(String filename) {
        try(Scanner s = new Scanner(new BufferedReader(new InputStreamReader(ElencoAgriturismo.class.getClassLoader().getResourceAsStream(filename))))) {
            ElencoAgriturismo ag = new ElencoAgriturismo();
            s.useDelimiter("[;\n]");
            s.nextLine();
            while(s.hasNext()) {
                String comune = s.next();
                String titolare = s.next();
                String denominazione = s.next();
                String indirizzo = s.next();
                int postiLetto = 0;
                if(s.hasNextInt())
                    postiLetto = s.nextInt();
                else
                    s.next();
                int postiMacchina = 0;
                if(s.hasNextInt())
                    postiMacchina = s.nextInt();
                else
                    s.next();
                int postiTenda = 0;
                if(s.hasNextInt())
                    postiTenda = s.nextInt();
                else
                    s.next();
                int postiRoulotte = 0;
                if(s.hasNextInt())
                    postiRoulotte = s.nextInt();
                else
                    s.next();
                String email = s.next();

                ag.aggiungi(new Agriturismo(comune,titolare,denominazione,indirizzo,postiLetto,postiMacchina,postiTenda,postiRoulotte,email));
            }

            return ag;
        }
    }

    @Override
    public String toString() {
        StringBuffer strb = new StringBuffer("\n***Elenco Agriturismi***\n");

        elenco.forEach(ai -> strb.append(ai).append("\n"));

        return strb.toString();
    }
}
