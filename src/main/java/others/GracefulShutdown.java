package others;

public class GracefulShutdown {
    public static void main(String[] args) {
        // 注册JVM关闭时要调用的hook
        Runtime.getRuntime().addShutdownHook(new Thread(GracefulShutdown::doShutdown));

        // 模拟程序的运行
        while (true) {
            // 检查是否已经收到终止信号
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("程序被终止...");
                break;
            }
            try {
                // 模拟程序的工作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 如果线程被中断，就退出循环
                break;
            }
        }
    }

    private static void doShutdown() {
        System.out.println("执行关闭操作...");
    }
}