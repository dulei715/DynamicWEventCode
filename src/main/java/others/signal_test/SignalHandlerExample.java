package others.signal_test;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class SignalHandlerExample {
    public static void main(String[] args) {
        SignalHandler handler = new SignalHandler() {
            public void handle(Signal signal) {
                // 处理信号，这里只是简单地打印信息
                System.out.println("Received signal: " + signal.getName());
                // 可以添加额外的清理代码
                System.exit(0); // 优雅地退出程序
            }
        };

        try {
            Signal sig = new Signal("TERM");
            Signal.handle(sig, handler);

            // 程序主逻辑
            System.out.println("Program is running...");
            Thread.sleep(Integer.MAX_VALUE); // 防止程序立即退出
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
