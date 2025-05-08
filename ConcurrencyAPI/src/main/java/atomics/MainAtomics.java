package atomics;

import java.util.concurrent.atomic.AtomicInteger;

public class MainAtomics {
    public static void main(String[] args) {
        AtomicInteger at = new AtomicInteger(10);
        at.addAndGet(4);
        at.incrementAndGet();
        System.out.println(at.getAndSet(500));
    }
}
