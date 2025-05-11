package locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Risorsa {
    private int cont;
    private final Lock lock;

    public Risorsa(){
        lock = new ReentrantLock(true);
    }

    public int getCont(){
        int out;
        lock.lock();
        try {
            out = cont;
        }finally {
            lock.unlock();
        }
        return out;
    }

    public void m1(){
        try {
            lock.lockInterruptibly();
        }catch(InterruptedException ex){
            System.err.println(
                    Thread.currentThread().getName()+" Interrotto"
            );
            return;
        }
        try{
            System.out.println(
                    Thread.currentThread().getName()+" ha il lock -> accede alla risorsa condivisa"
            );
            System.out.println("Operazione: "+cont+" -> "+(cont+1));
            Thread.sleep(1000*1);
            cont++;
            m2();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void m2(){
        lock.lock();
        try {
            System.out.println(
                    Thread.currentThread().getName()+" ha il lock -> accede alla risorsa condivisa"
            );
            System.out.println("Operazione: "+cont+" -> "+(cont*2));
            Thread.sleep(1000*2);
            cont *= 2;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void m3(){
        boolean isLocked;
        try {
            isLocked = lock.tryLock(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(!isLocked){
            System.err.println(
                    Thread.currentThread().getName()+" termina perchè è scaduto il timer di 3 sec"
            );
            return;
        }

        try{
            System.out.println(
                    Thread.currentThread().getName()+" ha il lock -> accede alla risorsa condivisa"
            );
            System.out.println("Operazione: "+cont+" -> "+(cont^2));
            Thread.sleep(1000*2);
            cont ^= 2;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
