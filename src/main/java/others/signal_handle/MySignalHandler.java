package others.signal_handle;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class MySignalHandler implements SignalHandler {
    @Override
    public void handle(Signal signal) {
        // 处理信号，这里只是简单地打印信息
        System.out.println("Received signal: " + signal.getName());
        // 可以添加额外的清理代码
        System.exit(0); // 优雅地退出程序
    }
}
