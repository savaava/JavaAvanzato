package generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsExample {
    public static void main(String[] args) {
        checkNumbersE(2.2, new ArrayList<Number>());
        checkNumbersS(2.2, new ArrayList<Object>());
        checkNumbersG(2.2, new ArrayList<Double>());

        Range<Integer> ri = new Range<>(2,4);
        System.out.println(ri.contains(3));
        System.out.println(ri.contains(5));
    }

    /* PECS: Producer Extends Consumer Super
    * se vogliamo scrivere dobbiamo usare super -> dobbiamo specificare l'ultima
    * sottoclasse */

    public static void checkNumbersE(Number n, List<? extends Number> l) {
        l.add(n);

        for(Number nCurr : l){
            System.out.println(nCurr.doubleValue() > n.doubleValue());
        }
    }

    public static void checkNumbersS(Double n, List<? super Double> l) {
        l.add(n);

        /* Non va bene perchè dal pov del chiamante si può sempre mettere ad esempio Object
        * quindi non si può fare un downCast */
        for(Number nCurr : l){
            System.out.println(nCurr);
        }

        for(Object nCurr : l){
            System.out.println(nCurr);
        }
    }

    public static <U extends Number> void checkNumbersG(U n, List<U> l){
        l.add(n);

        for(U nCurr : l){
            System.out.println(nCurr);
        }
    }
}
