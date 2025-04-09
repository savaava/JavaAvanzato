package com.savaava.test;

import com.savaava.model.Agriturismo;

public class Filtri {
    public static void aggiornaPernottamento(Agriturismo agriturismo){
        if(agriturismo.getPostiLetto()!=0)
            agriturismo.setPernottamento(true);
    }

    public static void aggiornaCamping(Agriturismo agriturismo){
        if(agriturismo.getPostiRoulotte() != 0 || agriturismo.getPostiTenda() != 0)
            agriturismo.setCamping(true);
    }
}
