package cyclicbarriers;

import java.util.concurrent.CyclicBarrier;

public class Festa implements Runnable {
    private final CyclicBarrier barrier;

    public Festa(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try{
            System.out.println("E' in attesa degli altri partecipanti");
            barrier.await();
            System.out.println("Ha iniziato a festeggiare");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
