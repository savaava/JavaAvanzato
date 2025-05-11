package main;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SerbatoioFinale extends Serbatoio {
    private final AtomicInteger currVolume;
    private final ReentrantLock lock;
    private final CyclicBarrier cyclicBarrier;
    private final Semaphore sem;

    public SerbatoioFinale(int ID, int numSerbatoi, Runnable opFinale) {
        super(ID, 50);

        currVolume = new AtomicInteger(0);
        lock = new ReentrantLock();
        cyclicBarrier = new CyclicBarrier(numSerbatoi, handleOpFinale(opFinale));
        sem = new Semaphore(1);
    }

    private Runnable handleOpFinale(Runnable opFinale) {
        return () -> {
            sem.acquireUninterruptibly();
            try {
                opFinale.run();
            } finally {
                sem.release();
                currVolume.set(0);
            }
        };
    }

    public void versaLiquido(Integer ID, boolean isIndipendent, Integer volume){

        if(!isIndipendent)
            lock.lock();

        try{
            System.out.println("Versamento dal serbatoio "+ID+" con "+volume+" litri ... ");
            Thread.sleep(1000*volume.longValue());
            int litri = currVolume.addAndGet(volume);
            System.out.println("Serbatoio riempito di " +volume + "L dal serbatoio "+ID+". Capienza attuale: "+litri+" L");
        }catch(InterruptedException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    public CyclicBarrier getCyclicBarrier() {
        return this.cyclicBarrier;
    }

    public Semaphore getSem() {
        return sem;
    }
}
