package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run;

import ecnu.dll.utils.CatchSignal;

public class LNSDatasetPreprocessRun {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        // 生成20000个user，1个位置，10000个timestamp
        // 保证所有user的统计满足某个函数的分布

    }
}
