package others.signal_handle;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.ArrayList;
import java.util.Collection;

public class NoTerminalHandler implements SignalHandler {
    private Collection<Integer> numberCollection = null;
    public NoTerminalHandler(Integer... numberArray) {
        this.numberCollection = new ArrayList<>();
        for (Integer number : numberArray) {
            this.numberCollection.add(number);
        }
    }

    @Override
    public void handle(Signal signal) {
        // 处理信号，这里只是简单地打印信息
        int signalNumber = signal.getNumber();
        System.out.println("Received signal: " + signal.getName() + ", its signal number is: " + signalNumber);
        if (this.numberCollection.contains(signalNumber)) {
            System.out.println("The program exit with signal number: " + signalNumber);
            System.exit(0);
        }
    }
}
