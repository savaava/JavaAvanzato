package executors;

import java.util.concurrent.*;

public class MainExecutors {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i=0; i<100; i++){
            Future<Integer> future = service.submit(new Processo(i));
            //System.out.println(" Valore: "+future.get());
            /* con questa get (bloccante) aspettiamo ad ogni iterazione che il task
            * finisca quindi non mi consente di passare al processo successivo prima
            * di ottenere il risultato del task corrente -> non sfruttiamo il pool */
        }
        service.shutdown();

        ExecutorService s = Executors.newSingleThreadExecutor();
        s.submit(() -> {
            System.out.println("hello world da "+Thread.currentThread().getName());
            return 99;
        });
        s.shutdown();
    }
}

class Processo implements Callable<Integer>{
    private int id;

    public Processo(int id){
        this.id=id;
    }

    @Override
    public synchronized Integer call() {
        System.out.println("ID: "+id+" - thread: "+Thread.currentThread().getName());
        return id;
    }
}
