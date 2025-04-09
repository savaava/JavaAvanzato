package com.savaava;

import java.util.function.BiFunction;

public class Agriturismo {
    String comuneAzienda;
    String titolare;
    String denominazioneAzienda;
    String indirizzoAzienda;
    int postiLetto;
    int postiMacchina;
    int postiTenda;
    int postiRoulotte;
    String recapiti;
    boolean pernottamento;
    boolean camping;

    public Agriturismo(String comuneAzienda, String titolare, String denominazioneAzienda, String indirizzoAzienda, int postiLetto, int postiMacchina, int postiTenda, int postiRoulotte, String recapiti) {
        this.comuneAzienda = comuneAzienda;
        this.titolare = titolare;
        this.denominazioneAzienda = denominazioneAzienda;
        this.indirizzoAzienda = indirizzoAzienda;
        this.postiLetto = postiLetto;
        this.postiMacchina = postiMacchina;
        this.postiTenda = postiTenda;
        this.postiRoulotte = postiRoulotte;
        this.recapiti = recapiti;

        this.pernottamento = false;
        this.camping = false;
    }

    public String getComuneAzienda() {
        return comuneAzienda;
    }
    public void setComuneAzienda(String comuneAzienda) {
        this.comuneAzienda = comuneAzienda;
    }

    public String getTitolare() {
        return titolare;
    }
    public void setTitolare(String titolare) {
        this.titolare = titolare;
    }

    public String getDenominazioneAzienda() {
        return denominazioneAzienda;
    }
    public void setDenominazioneAzienda(String denominazioneAzienda) {
        this.denominazioneAzienda = denominazioneAzienda;
    }

    public String getIndirizzoAzienda() {
        return indirizzoAzienda;
    }
    public void setIndirizzoAzienda(String indirizzoAzienda) {
        this.indirizzoAzienda = indirizzoAzienda;
    }

    public int getPostiLetto() {
        return postiLetto;
    }
    public void setPostiLetto(int postiLetto) {
        this.postiLetto = postiLetto;
    }

    public int getPostiMacchina() {
        return postiMacchina;
    }
    public void setPostiMacchina(int postiMacchina) {
        this.postiMacchina = postiMacchina;
    }

    public int getPostiTenda() {
        return postiTenda;
    }
    public void setPostiTenda(int postiTenda) {
        this.postiTenda = postiTenda;
    }

    public int getPostiRoulotte() {
        return postiRoulotte;
    }

    public int getPostiCamping() {
        return postiRoulotte + postiTenda;
    }

    public void setPostiRoulotte(int postiRoulotte) {
        this.postiRoulotte = postiRoulotte;
    }

    public String getRecapiti() {
        return recapiti;
    }
    public void setRecapiti(String recapiti) {
        this.recapiti = recapiti;
    }

    public boolean isPernottamento() {
        return pernottamento;
    }
    public void setPernottamento(boolean pernottamento) {
        this.pernottamento = pernottamento;
    }

    public boolean isCamping() {
        return camping;
    }
    public void setCamping(boolean camping) {
        this.camping = camping;
    }

    public String shortInfo(String info1, String info2, BiFunction<String, String, String> biFunction){
        return biFunction.apply(info1, info2);
    }
    public String shortInfo(){
        return shortInfo(titolare, denominazioneAzienda, (info1, info2) -> "Agriturismo short info: [Titolare="+info1+" - Azienda="+info2+"]");
    }

    @Override
    public String toString(){
        return "Agriturismo [comuneAzienda="+comuneAzienda
                +", titolare="+titolare
                +", denominazioneAzienda="+denominazioneAzienda
                +", indirizzoAzienda="+indirizzoAzienda
                +", postiLetto="+postiLetto
                +", postiMacchina="+postiMacchina
                +", postiTenda="+postiTenda
                +", postiRoulotte="+postiRoulotte
                +", recapiti="+recapiti
                +", pernottamento="+pernottamento
                +", camping=" + camping + "]";
    }
}
