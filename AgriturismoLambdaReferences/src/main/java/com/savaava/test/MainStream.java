package com.savaava.test;

import com.savaava.model.Agriturismo;
import com.savaava.model.ElencoAgriturismi;
import com.savaava.model.Titolare;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        /* caricare un elenco agriturismi da file tramite stream API mediante uso della classe Files */
        ElencoAgriturismi ea = ElencoAgriturismi.caricaCSVStream("Agriturismi-Benevento-Short.csv");
        //System.out.println("Stato iniziale"+ea);

        /* aggiornare il valore dell'attributo pernottamento (inizializzato a FALSE durante la lettura)
           sulla base della disponibilità di posti letto */
        ea.stream()
                .filter(a -> a.getPostiLetto()>0)
                .forEach(a -> a.setPernottamento(true));
        //ea.stream().forEach(a -> a.setPernottamento(a.getPostiLetto() > 0));
        System.out.println("Aggiornamento attributo pernottamento"+ea);

        /* aggiornare il valore dell'attributo camping (inizializzato a FALSE durante la lettura)
        sulla base dei posti tenda/roulotte*/
        ea.stream()
                .filter(a -> a.getPostiCamping()>0)
                .forEach(a -> a.setCamping(true));
        //ea.stream().forEach(a -> a.setCamping(a.getPostiRoulotte() > 0 || a.getPostiTenda() > 0));
        System.out.println("Aggiornamento attributo camping"+ea);


        /* esportare l'elenco dei comuni che ospitano Agriturismi */
        List<String> elencoComuni = ea.stream()
                .map(Agriturismo::getComuneAzienda)
                .distinct()
                .toList();
        System.out.println("Elenco comuni degli agriturismi:  "+elencoComuni+"\n");

        /* ordinare l'intero elenco alfabeticamente per denominazione azienda */
        List<?> aziendeOrdinate = ea.stream()
                .sorted((o1,o2) -> o1.getDenominazioneAzienda().compareTo(o2.getDenominazioneAzienda()))
                .toList();
        System.out.println("Elenco ordinato per denominazione azienda");
        aziendeOrdinate.forEach(System.out::println);

        /* indicare il comune con il maggior numero di posti campeggio */
        String comuneMaxPosti = ea.stream()
                .max((o1, o2) -> o1.getPostiCamping()-o2.getPostiCamping())
                .map(Agriturismo::getComuneAzienda)
                .orElseGet(() -> "NESSUN COMUNE");
        System.out.println("\nComune con max posti campeggio: "+comuneMaxPosti+"\n");

        /* ottenere una mappa (Map<K,V>) con il numero di posti letto complessivi disponibili per ogni comune */
        Map<?,?> numPostiLettoComuni = ea.stream()
                .collect(Collectors.groupingBy(
                        Agriturismo::getComuneAzienda,
                        Collectors.summingInt(Agriturismo::getPostiLetto)
                ));
        System.out.println("Comune -> posti letto: ");
        numPostiLettoComuni.forEach((comune,posti) -> System.out.println(comune+" -> "+posti));

        /* ottenere una mappa con il numero medio di posti camping degli agriturismi di ogni comune*/
        Map<?,?> averagePostiCampingComuni = ea.stream()
                .collect(Collectors.groupingBy(
                        Agriturismo::getComuneAzienda,
                        Collectors.averagingInt(Agriturismo::getPostiCamping)
                ));
        System.out.println("\nComune -> average posti camping: ");
        averagePostiCampingComuni.forEach((comune,mediaPosti) -> System.out.println(comune+" -> "+mediaPosti));


        /* - definito un record Titolare con attributi (nome, cognome, mail) ottenere un elenco (lista)
           di tutti titolari. Laddove la mail non fosse definita, impostare una mail di
           default ("info@agriturismibenevento.it")*/
        List<Titolare> listaTitolari = ea.stream()
                .map(ai -> {
                    String[] nomeCognome = ai.getTitolare().split(" ",2);
                    String mail = ai.getRecapiti().equals("nd") ? "info@agriturismibenevento.it":ai.getRecapiti();
                    return new Titolare(nomeCognome[1], nomeCognome[0], mail);
                })
                .toList();
        System.out.println("Lista titolari (nome, cognome, mail)\n");
        listaTitolari.forEach(System.out::println);
    }
}
