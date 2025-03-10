package generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsExample {
    public static void main(String[] args) {
        checkNumbersE(2.2, new ArrayList<Number>());
        checkNumbersS(2.2, new ArrayList<Object>());

        checkNumbersG(2, new ArrayList<Integer>());
        checkNumbersG(2.2, new ArrayList<Double>());

        System.out.println();

        Range<Integer> ri = new Range<>(2,4);
        System.out.println(ri.contains(3));
        System.out.println(ri.contains(5));
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
        l.add(n);

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
