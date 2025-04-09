package com.savaava.test;

import com.savaava.model.Agriturismo;
import com.savaava.model.ElencoAgriturismi;

import java.util.Comparator;

public class TestAgriturismiReference {
    public static void main(String[] args) {
        ElencoAgriturismi elencoAgriturismo = ElencoAgriturismi.caricaCSV("Agriturismi-Napoli.csv");

        elencoAgriturismo.aggiorna(Filtri::aggiornaPernottamento);

        elencoAgriturismo.aggiorna(Filtri::aggiornaCamping);

        System.out.println(elencoAgriturismo);

        System.out.println(elencoAgriturismo.esporta(Agriturismo::getComuneAzienda));

        elencoAgriturismo.ordina(Comparator.comparing(Agriturismo::getDenominazioneAzienda));
        System.out.println(elencoAgriturismo);

        System.out.println(elencoAgriturismo.filtra(
                a -> a.getComuneAzienda().equalsIgnoreCase("Gragnano")).somma(Agriturismo::getPostiLetto));
    }
}
