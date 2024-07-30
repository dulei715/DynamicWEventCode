package others.signal_test;

import cn.edu.dll.signal.signal_handle.NoTerminalHandler;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.TimeUnit;

@Deprecated
public class SignalCancelTest2 {
    public static void main(String[] args) throws InterruptedException {
        Thread threadWithSignalHandler = new Thread(new SignalRunner());
        threadWithSignalHandler.start();

        // 等待线程注册信号处理器
        System.out.println("Start main...");
        TimeUnit.MINUTES.sleep(1);
        System.out.println("Main thread step1 terminates");
        // 取消对该线程的signal捕获
        threadWithSignalHandler.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("Signal handling canceled.");
        });

//        threadWithSignalHandler.stop();
        TimeUnit.MINUTES.sleep(1);
        System.out.println("Main thread step2 terminates");




//        System.out.println("Main thread exits...");

        // 模拟发送信号
        // 这里的代码会抛出ArithmeticException，但由于signal处理已经取消，因此不会被捕获
//        threadWithSignalHandler.stop(); // 注意：不推荐使用stop()，这里仅作为演示
    }

    static class SignalRunner implements Runnable {
        @Override
        public void run() {
            // 设置一个简单的signal处理器
            SignalHandler signalHandler = new NoTerminalHandler();
            Signal signalA = new Signal("TERM");
            Signal signalB = new Signal("INT");
            Signal.handle(signalA, signalHandler);
            Signal.handle(signalB, signalHandler);
            System.out.println("sub thread terminates...");

            // 模拟长时间运行的任务

        }
    }
}
