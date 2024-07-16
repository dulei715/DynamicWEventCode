package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run;

import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function.TLNSFunction;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.utils.SyntheticGenerationUtils;
import ecnu.dll.utils.CatchSignal;

public class TLNSDatasetPreprocessRun {
    public static void generateProbabilityParameters() {
        // 生成20000个user，1个位置，10000个timestamp
        // 保证所有user的统计满足某个函数的分布
        Double p0 = 0.5;
        Double gaussAverage = 0D;
        Double gaussStandardVariance = 0.0025;
        String datasetName = "tlns";
        TLNSFunction TLNSFunction = new TLNSFunction(p0, gaussAverage, gaussStandardVariance);
        int timeStampSize = Integer.valueOf(ConfigureUtils.getFileHandleInfo(datasetName, "timeStampSize"));
        SyntheticGenerationUtils.generateProbability(TLNSFunction, timeStampSize, true);

    }

    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        // 1. 生成每个时刻的概率参数到 basic_info/TLNS.txt
        generateProbabilityParameters();

        // 2. 生成userID
        Integer userSize = Integer.valueOf(ConfigureUtils.getFileHandleInfo("tlns", "userSize"));
        SyntheticGenerationUtils.generateUserID(Constant.tlnsFilePath, userSize);

        // 3. 生成userTypeID
        Integer userTypeSize = Integer.valueOf(ConfigureUtils.getFileHandleInfo("tlns", ""))

        // 2. 生成输入数据到 runInput 文件夹下


    }
}
