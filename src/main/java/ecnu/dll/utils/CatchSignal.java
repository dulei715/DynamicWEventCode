package ecnu.dll.utils;

import others.signal_handle.NoTerminalHandler;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CatchSignal {
    private Collection<Signal> caughtSignalCollection;
    private SignalHandler handler;

    public CatchSignal(List<String> caughtSignalNameList, Integer... allowKilledSignalID) {
        this.caughtSignalCollection = new ArrayList<>();
        for (String signalName : caughtSignalNameList) {
            this.caughtSignalCollection.add(new Signal(signalName));
        }
        this.handler = new NoTerminalHandler(allowKilledSignalID);
    }

    /**
     * 默认初始化只捕获TERM和INT中断，并且只有在收到INT时终止。
     */
    public CatchSignal() {
        this.caughtSignalCollection = new ArrayList<>();
        this.caughtSignalCollection.add(new Signal("TERM"));
        this.caughtSignalCollection.add(new Signal("INT"));
        this.handler = new NoTerminalHandler(2);
    }

    public void startCatch() {
        for (Signal signal : this.caughtSignalCollection) {
            Signal.handle(signal, handler);
        }
    }

    public void endCatch() {
        // 待定...
    }
}
