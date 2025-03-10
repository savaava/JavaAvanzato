package generics;

/* quando si definisce un tipo generico con più vincoli (bound multipli), si usa l'operatore extends anche per le interfacce */
public class Range<T extends Number & Comparable<T>> {
    private final T low;
    private final T high;

    public Range(T low, T high) {
        this.low = low;
        this.high = high;

        if(low.compareTo(high) >= 0)
            throw new IllegalArgumentException();
    }

    public T getLow() {
        return low;
    }
    public T getHigh() {
        return high;
    }

    public boolean contains(T e) {
        return e.compareTo(low)>=0 && e.compareTo(high)<=0;
    }

    @Override
    public String toString() {
        return "["+low+","+high+"]";
    }
}
