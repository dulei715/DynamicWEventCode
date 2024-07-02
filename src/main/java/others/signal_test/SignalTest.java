package others.signal_test;

import others.signal_handle.NoTerminalHandler;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class SignalTest {
    public static void main(String[] args) {
        SignalHandler signalHandler = new NoTerminalHandler(2);
        Integer sleepTime = 60000;

        try {
            Signal signal = new Signal("TERM");
            Signal.handle(signal, signalHandler);
            System.out.println("process starts...");
            Thread.sleep(sleepTime);
            System.out.println("process ends...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
