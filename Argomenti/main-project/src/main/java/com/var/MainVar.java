package com.var;

public class MainVar {
    public static void main(String[] args) {
        var ogg1 = new LibroSuJava(); // dedotto il tipo LibroSuJava
        var ogg2 = getIstanza(); // scarsa leggibilità
        var bool = false; // dedotto il tipo boolean
        var string = "Foqus";// dedotto il tipo String
        var character= 'J'; // dedotto il tipo char
        var integer = 8; // dedotto il tipo int
        var byteInteger = (byte)8; // dedotto il tipo byte
        var shortInteger = (short)8; // dedotto il tipo short
        var longInteger = 8L; // dedotto il tipo long
        var floatingPoint = 3.14F; // dedotto il tipo float
        var doublePrecisionfloatingPoint = 3.14; // dedotto il tipo double

        System.out.println(
                ogg1+"\n"
                + ogg2+"\n"
                + bool+"\n"
                + string+"\n"
                + character+"\n"
                + integer+"\n"
                + byteInteger+"\n"
                + shortInteger+"\n"
                + longInteger+"\n"
                + floatingPoint+"\n"
                + doublePrecisionfloatingPoint
        );

        /*
        var notInitialized; // NO
        var nullInitialized = null; // NO
        var var1 = 1, var2 = 2; // NO
        var varArray[] = new int[3]; // NO
         */
    }

    static LibroSuJava getIstanza(){
        return new LibroSuJava();
    }
}

class LibroSuJava {
    @Override
    public String toString() {
        return "LibroSuJava istance";
    }
}