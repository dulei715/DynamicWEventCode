package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function.LogFunction;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.utils.SyntheticGenerationUtils;

public class LogDatasetPreprocessRun {
    public static void generateProbabilityParameters() {
        Double parameterA = 0.25;
        Double parameterB = 0.01;
        String datasetName = "log";
        LogFunction logFunction = new LogFunction(parameterA, parameterB);
        int timeStampSize = Integer.valueOf(ConfigureUtils.getFileHandleInfo(datasetName, "timeStampSize"));
        SyntheticGenerationUtils.generateProbability(logFunction, timeStampSize, true);

    }

    public static void generateDataset() {
        String datasetName = "log";
        String datasetPath = Constant.logFilePath;
        String positionFileName = "status.txt";

        // 1. 生成时刻文件 time_stamp.txt
        Integer timeStampSize = Integer.valueOf(ConfigureUtils.getFileHandleInfo(datasetName, "timeStampSize"));
        SyntheticGenerationUtils.generateTimeStamp(datasetPath, timeStampSize);

        // 2. 生成每个时刻的概率参数到 basic_info/probability.txt
        generateProbabilityParameters();

        // 3. 生成userID.txt
        Integer userSize = Integer.valueOf(ConfigureUtils.getFileHandleInfo(datasetName, "userSize"));
        SyntheticGenerationUtils.generateUserID(datasetPath, userSize);

        // 4. 生成位置 status.txt
        Integer statusSize = Integer.valueOf(ConfigureUtils.getFileHandleInfo(datasetName, "locationTypeSize"));
        SyntheticGenerationUtils.generatePositionType(datasetPath, positionFileName, statusSize);

        // 5. 生成输入数据到 runInput 文件夹下
        SyntheticGenerationUtils.generateRunInputData(datasetPath, positionFileName);


    }

    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        generateDataset();
    }
}
