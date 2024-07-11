package ecnu.dll.run.c_dataset_run;

import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.utils.DatasetRunUtils;
import ecnu.dll.utils.CatchSignal;

public class TrajectoryDataSetRun {



    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        String basicPath = Constant.trajectoriesFilePath;
        String dataTypeFileName = "cell.txt";
        Integer singleBatchSize = 2;
        DatasetRunUtils.basicDatasetRun(basicPath, dataTypeFileName, singleBatchSize);
    }
}
