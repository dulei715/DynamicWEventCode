package others.signal_handle;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class NoTerminalHandler implements SignalHandler {
    private Integer number = null;
    public NoTerminalHandler(Integer number) {
        this.number = number;
    }

    @Override
    public void handle(Signal signal) {
        // 处理信号，这里只是简单地打印信息
        int signalNumber = signal.getNumber();
        System.out.println("Received signal: " + signal.getName() + ", its signal number is: " + signalNumber);
        if (this.number != null && this.number.equals(signalNumber)) {
            System.out.println("The program exit with signal number: " + signalNumber);
            System.exit(0);
        }
    }
}
