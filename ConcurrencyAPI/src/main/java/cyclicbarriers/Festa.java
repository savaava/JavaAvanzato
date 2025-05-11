package cyclicbarriers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Festa implements Runnable {
    private final CyclicBarrier barrier;

    public Festa(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try{
            System.out.println(Thread.currentThread().getName()+" E' in attesa degli altri partecipanti");
            barrier.await();
            System.out.println(Thread.currentThread().getName()+" Ha iniziato a festeggiare");
        } catch (InterruptedException | BrokenBarrierException ex) {
            ex.printStackTrace();
        }
    }
}
