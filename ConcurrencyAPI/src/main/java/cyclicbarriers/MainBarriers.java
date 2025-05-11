package cyclicbarriers;

import java.util.concurrent.CyclicBarrier;

public class MainBarriers {
    public static void main(String[] args) throws InterruptedException {
        int n = 3;
        CyclicBarrier luogoDellaFesta = new CyclicBarrier(
                n, () -> System.out.println(n+" presenti -> si inizia")
        );
        Festa festa = new Festa(luogoDellaFesta);

        new Thread(festa, "Antonio").start(); Thread.sleep(2*1000);
        new Thread(festa, "Marcello").start(); Thread.sleep(2*1000);
        new Thread(festa, "Serena").start();
    }
}
