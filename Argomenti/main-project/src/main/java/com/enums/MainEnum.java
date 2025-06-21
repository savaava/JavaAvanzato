package com.enums;

import java.util.List;

public class MainEnum {
    public static void main(String[] args) {
        Colore gg = Colore.GIALLO;
        System.out.println("gg: "+gg+"\n");

        List.of(Colore.values()).forEach(System.out::println);

        System.out.println("\n"+Colore.valueOf("GIALLO"));
    }

    enum Colore {
        ROSSO(255,0,0),
        NERO(0,0,0),
        GIALLO(255,0,255),
        VERDE(0,255,0);

        private final int r,g,b;

        Colore(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int getR() { return r; }
        public int getG() { return g; }
        public int getB() { return b; }

        public String getRGB() { return "{"+r+","+g+","+b+"}"; }

        @Override
        public String toString(){ return getRGB(); }
    }
}
