package generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsExample {
    private static void print1(List<? extends Number> l){
//        l.add(8.6);

        for(Number e : l){
            System.out.println(e+" → "+e.getClass());
        }
    }
    private static void print2(List<? super Double> l){ /* ? può essere Double, Number o Object */
        Double n = 8.6;
        l.add(n);

        for(Object e : l){
            System.out.println(e+" → "+e.getClass());
        }
    }
    private static <T extends Number> void print3(List<T> l, T n){
        l.add(n);

        for(T e : l){
            System.out.println(e+" → "+e.getClass());
        }
    }

    public static void main(String[] args) {
        List<Number> l = new ArrayList<>();
        l.add(2.2);
        l.add(0.1f);
        l.add(5);

        print1(l);
        System.out.println();
        print2(l);
        System.out.println();

        List<Double> l2 = new ArrayList<>();
        l2.add(2.2);
        l2.add(0.1);
        print3(l2, 5.9);

//        checkNumbersE(2.2, new ArrayList<Number>());
//        checkNumbersS(2.2, new ArrayList<Object>());
//
//        checkNumbersG(2, new ArrayList<Integer>());
//        checkNumbersG(2.2, new ArrayList<Double>());
//
//        System.out.println();
//
//        Range<Integer> ri = new Range<>(2,4);
//        System.out.println(ri.contains(3));
//        System.out.println(ri.contains(5));
    }

    /* PECS: Producer Extends Consumer Super
    * se vogliamo scrivere dobbiamo usare super -> dobbiamo specificare l'ultima
    * sottoclasse */

    /* il compilatore non sa quale tipo specifico sia, quindi impedisce modifiche alla lista */
    public static void checkNumbersE(Number n, List<? extends Number> l) {
//        l.add(n);
        /* significa che l può contenere oggetti di un qualsiasi sottotipo di Number (es. Integer, Double, Float)
         ma non puoi aggiungere elementi alla lista perché il compilatore non può garantire che il tipo specifico
         di n sia compatibile con quello della lista.
          Funziona solo la lettura.
          usare super per avere anche la possibilità di scrivere */

        for(Number nCurr : l){
            System.out.println(nCurr.doubleValue() > n.doubleValue());
        }
    }

    public static void checkNumbersS(Double n, List<? super Double> l) { /* si potrebbe mettere Object, Number o Double */
        l.add(n); /* l'unico tipo accettabile è quello del limite inferiore o i sottotipi di
         Double per cui si fa l'upcast a Double o Number o Object */

        /* Non va bene perchè dal pov del chiamante si può sempre mettere ad esempio Object
        * quindi non si può fare un downCast */
//        for(Number nCurr : l){
//            System.out.println(nCurr);
//        }

        /* di conseguenza siamo costretti a mettere sempre Object in questo caso perchè non siamo limitati superiormente con <? super Double>
        * e quindi si può fare upcast o il tipo è effettivamente Object */
        for(Object nCurr : l){
            System.out.println(nCurr);
        }
    }

    /* tipo generico U, che viene determinato al momento della chiamata */
    public static <U extends Number> void checkNumbersG(U n, List<U> l){
        l.add(n);

        for(U nCurr : l){
            System.out.println(nCurr);
        }
    }
}
