package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run;

import ecnu.dll._config.ConfigureUtils;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function.LNSFunction;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.utils.SyntheticGenerationUtils;
import ecnu.dll.utils.CatchSignal;

public class LNSDatasetPreprocessRun {
    public static void generateProbabilityParameters() {
        // 生成20000个user，1个位置，10000个timestamp
        // 保证所有user的统计满足某个函数的分布
        Double p0 = 0.05;
        Double gaussAverage = 0D;
        Double gaussStandardVariance = 0.0025;
        String datasetName = "lns";
        LNSFunction lnsFunction = new LNSFunction(p0, gaussAverage, gaussStandardVariance);
        int timeStampSize = Integer.valueOf(ConfigureUtils.getFileHandleInfo(datasetName, "timeStampSize"));
        SyntheticGenerationUtils.generateProbability(lnsFunction, timeStampSize, true);

    }
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        // 1. 生成每个时刻的概率参数到 basic_info/LNS.txt
        generateProbabilityParameters();

        // 2. 生成输入数据到 runInput 文件夹下


    }
}
