package main;

public class SerbatoioVersante extends Serbatoio implements Runnable {
    private final SerbatoioFinale serbatoioFinale;
    private final boolean isIndipendent;
    private boolean hasPoored;

    public SerbatoioVersante(Integer ID, Integer volume, boolean isIndipendent, SerbatoioFinale serbatoioFinale) {
        super(ID, volume);
        this.serbatoioFinale = serbatoioFinale;
        this.isIndipendent = isIndipendent;
        this.hasPoored = false;
    }

    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                Thread.sleep(1000 * (Math.round(Math.random()*4) + 1)); /* attesa prima del riempimento */
                System.out.println("Riempimento del serbatoio "+getID()+" con "+getVolume()+" litri ...");
                Thread.sleep(1000 * getVolume().longValue()); /* attesa per il riempimento */

                if(hasPoored){
                    serbatoioFinale.getCyclicBarrier().await();
                }

                serbatoioFinale.getSem().acquireUninterruptibly();
                serbatoioFinale.getSem().release();

                hasPoored = true;
                serbatoioFinale.versaLiquido(getID(), isIndipendent, getVolume());

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
