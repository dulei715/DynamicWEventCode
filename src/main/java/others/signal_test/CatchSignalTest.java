package others.signal_test;

import ecnu.dll.utils.CatchSignal;
import sun.misc.Signal;

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
