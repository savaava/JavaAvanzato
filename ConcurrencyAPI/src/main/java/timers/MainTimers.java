package timers;

import java.util.Timer;
import java.util.TimerTask;

public class MainTimers {
    public static void main(String[] args) {
        TimerTask tt = new TimerTask(){
            @Override
            public void run() {
                System.out.println("world");
            }
        };

        Timer t = new Timer();
        //t.schedule(tt, 5*1000);
        t.scheduleAtFixedRate(tt, 3*1000, 1000);
        System.out.println("hello");
    }
}