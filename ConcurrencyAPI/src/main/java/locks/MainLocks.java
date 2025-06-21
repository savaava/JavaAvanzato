package locks;

public class MainLocks {
    public static void main(String[] args) throws InterruptedException {
        Risorsa r = new Risorsa();

        Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            Runnable task;
            String threadName = "Thread-"+i;

            task = switch(i){
                case 0 -> r::m2; // Thread 0 esegue m2 (nessun controllo di interruzione)
                case 1 -> r::m3; // Thread 4 usa tryLock con timeout da 3 secondi
                default -> r::m1; // Gli altri usano lockInterruptibly
            };

            threads[i] = new Thread(task, threadName);
            threads[i].start();
        }

        // Dopo 4 secondi interrompo il terzo thread che è in attesa del lock
        Thread.sleep(4000);
        threads[2].interrupt();
    }
}
