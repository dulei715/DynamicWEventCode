package others.signal_test;

import cn.edu.dll.signal.CatchSignal;

import java.util.concurrent.TimeUnit;

public class CatchSignalTest {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        try {
            TimeUnit.MINUTES.sleep(1);
            System.out.println("Main terminates");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
