package com.savaava.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class ElencoAgriturismi {
    private final List<Agriturismo> elenco;

    public ElencoAgriturismi() {
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
    public ElencoAgriturismi filtra(Predicate<Agriturismo> predicate){
        ElencoAgriturismi out = new ElencoAgriturismi();

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

    public static ElencoAgriturismi caricaCSV(String filename) {
        try(Scanner s = new Scanner(new BufferedReader(new FileReader("src/main/resources/"+filename)))) {
            ElencoAgriturismi ag = new ElencoAgriturismi();
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
        }catch(IOException e){
            e.printStackTrace();
            return new ElencoAgriturismi();
        }
    }


    public static ElencoAgriturismi caricaCSVStream(String filename) {
        try (Stream<String> linesStream = Files.lines(Path.of("src/main/resources/"+filename))) {
            ElencoAgriturismi ea =  new ElencoAgriturismi();

            linesStream
                    .skip(1) //salta il primo rigo
                    .map(ElencoAgriturismi::lineToAgriturismo)
                    .forEach(ea::aggiungi);

            return ea;
        } catch(IOException e) {
            e.printStackTrace();
            return new ElencoAgriturismi();
        }
    }
    public static Agriturismo lineToAgriturismo(String line) {
        String[] data = line.split(";");
        return new Agriturismo(
                data[0],
                data[1],
                data[2],
                data[3],
                Integer.parseInt(data[4].isEmpty() ? "0" : data[4]),
                Integer.parseInt(data[5].isEmpty() ? "0" : data[5]),
                Integer.parseInt(data[6].isEmpty() ? "0" : data[6]),
                Integer.parseInt(data[7].isEmpty() ? "0" : data[7]),
                data[8]
        );
    }
    public Stream<Agriturismo> stream() {
        return elenco.stream();
    }


    @Override
    public String toString() {
        StringBuffer strb = new StringBuffer("\n***Elenco Agriturismi***\n");

        elenco.forEach(ai -> strb.append(ai).append("\n"));

        return strb.toString();
    }
}
