package executors;

import java.util.concurrent.*;

public class MainExecutors {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i=0; i<100; i++){
            Future<Integer> future = service.submit(new Processo(i));
            System.out.println(" Valore: "+future.get());
        }
        service.shutdown();

        ExecutorService s = Executors.newSingleThreadExecutor();
        s.submit(() -> {
            System.out.println("hello world da "+Thread.currentThread().getName());
            return 99;
        });
        s.shutdown();

        Future<Integer> f = Executors.newSingleThreadExecutor().submit(() -> 33);
        System.out.println(f.get());
    }
}

class Processo implements Callable<Integer>{
    private int id;

    public Processo(int id){
        this.id=id;
    }

    @Override
    public synchronized Integer call() {
        System.out.print("ID: "+id+" - thread: "+Thread.currentThread().getName());
        return id;
    }
}
