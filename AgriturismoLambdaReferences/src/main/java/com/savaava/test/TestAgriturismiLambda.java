package com.savaava.test;

import com.savaava.model.Agriturismo;
import com.savaava.model.ElencoAgriturismi;

import java.util.function.Supplier;

public class TestAgriturismiLambda {
    public static void main(String[] args) {
        ElencoAgriturismi elencoAgriturismo = ElencoAgriturismi.caricaCSV("Agriturismi-Napoli.csv");

        Supplier<Agriturismo> agriturismoFactoryGragnano = () -> new Agriturismo(
                "GRAGNANO",
                "SCOLA VINCENZO",
                "CASA SCOLA",
                "LOC CASTELLO",
                50, 50, 20, 20,
                "info@casascola.it"
        );
        Agriturismo agriturismo = agriturismoFactoryGragnano.get();
        elencoAgriturismo.aggiungi(agriturismo);
        System.out.println(
                agriturismo.shortInfo()+"\n"+
                agriturismo.shortInfo(agriturismo.getComuneAzienda(),agriturismo.getIndirizzoAzienda(), (info1,info2) -> "Agriturismo -> Comune="+info1+", Indirizzo="+info2)
        );

        System.out.println(
                "\nelenco dei comuni degli agriturismi: "+elencoAgriturismo.esporta(a -> a.getComuneAzienda())+
                "\nelenco dei titolari degli agriturismi: "+elencoAgriturismo.esporta(a -> a.getTitolare())+
                "\nelenco degli indirizzi degli agriturismi: "+elencoAgriturismo.esporta(a -> a.getIndirizzoAzienda())
        );


        ElencoAgriturismi e = elencoAgriturismo.filtra(a -> a.getPostiLetto()>16);
        System.out.println("\nAgriturismi con posti letto > 16"+e+e.numeroAgriturismi());


        elencoAgriturismo.ordina(
                (o1, o2) -> o1.getDenominazioneAzienda().compareToIgnoreCase(o2.getDenominazioneAzienda())
        );
        System.out.println("\nAgriturismi ordinati per denominazioneAzienda:"+elencoAgriturismo);


        elencoAgriturismo.aggiorna(ai ->{
            if(ai.getPostiLetto()>0)
                ai.setPernottamento(true);
        });
        elencoAgriturismo.aggiorna(ai ->{
            if(ai.getPostiRoulotte()>0 || ai.getPostiTenda()>0)
                ai.setCamping(true);
        });
        System.out.println("Aggiornamento agriturismi di Pernottamento e Camping:"+elencoAgriturismo);

        ElencoAgriturismi e2 = elencoAgriturismo.filtra(a -> a.getComuneAzienda().equalsIgnoreCase("SORRENTO"));
        Integer sommaPostiLetto = e2.somma(a -> a.getPostiLetto());
        System.out.println("Agriturismi Gragnano"+e2+"Somma dei posti letto: "+sommaPostiLetto);
    }
}
