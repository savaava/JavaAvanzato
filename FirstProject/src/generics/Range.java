package generics;

public class Range<T extends Number & Comparable<T>> {
    private T low;
    private T high;

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
