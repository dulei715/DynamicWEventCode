package others.signal_test;

import java.util.concurrent.TimeUnit;

@Deprecated
public class SignalCancelTest {
    public static void main(String[] args) throws InterruptedException {
        Thread threadWithSignalHandler = new Thread(new SignalRunner());
        threadWithSignalHandler.start();

        // 等待线程注册信号处理器
        TimeUnit.SECONDS.sleep(10);

        // 取消对该线程的signal捕获
        threadWithSignalHandler.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("Signal handling canceled.");
        });

        // 模拟发送信号
        // 这里的代码会抛出ArithmeticException，但由于signal处理已经取消，因此不会被捕获
        threadWithSignalHandler.stop(); // 注意：不推荐使用stop()，这里仅作为演示
    }

    static class SignalRunner implements Runnable {
        @Override
        public void run() {
            // 设置一个简单的signal处理器
            sun.misc.Signal.handle(new sun.misc.Signal("TERM"), signal -> {
                System.out.println("Caught signal: TERM");
                // 处理信号的逻辑...
            });

            // 模拟长时间运行的任务
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
