package com.switches;

import java.time.Month;
import java.util.*;

public class MainSwitches {
    public static void main(String[] args) {
        Month month = List.of(Month.values()).get(new Random().nextInt(12));
        switch(month){
            case DECEMBER, JANUARY, FEBRUARY -> System.out.println(month+" -> winter");
            case MARCH, APRIL, MAY -> System.out.println(month+" -> spring");
            case JUNE, JULY, AUGUST -> System.out.println(month+" -> summer");
        }

        Month month1 = List.of(Month.values()).get(new Random().nextInt(12));
        var season = switch(month1){
            case DECEMBER, JANUARY, FEBRUARY -> "winter";
            case MARCH, APRIL, MAY -> "spring";
            case JUNE, JULY, AUGUST -> "summer";
            case SEPTEMBER, OCTOBER, NOVEMBER -> {
                String out = "autumn";
                yield out;
            }
        };
        System.out.println(month1+" -> "+season);

        Month month2 = Month.values()[new Random().nextInt(12)];
        season = switch(Month.JANUARY){
            case JANUARY:
            case FEBRUARY:
            case MARCH, APRIL, MAY: yield "spring";
            case JUNE, JULY, AUGUST: yield "summer";
            case SEPTEMBER, OCTOBER, NOVEMBER: {
                String out = "autumn";
                yield out;
            }
            case DECEMBER:
                yield null;
        };
        System.out.println(month2+" -> "+season);

        Object obj = List.of(42, "hello", 3.14, true).get(new Random().nextInt(4));
        String result = switch (obj) {
            case Integer i -> "Integer: " + i;
            case String s -> "String: " + s;
            case Double d -> "Double: " + d;
            case Boolean b -> "Boolean: " + b;
            default -> "Unknown type";
        };
        System.out.println("Pattern matching result -> " + result);
    }
}
