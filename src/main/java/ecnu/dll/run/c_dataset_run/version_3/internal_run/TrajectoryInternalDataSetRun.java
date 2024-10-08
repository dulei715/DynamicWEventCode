package ecnu.dll.run.c_dataset_run.version_3.internal_run;

import cn.edu.dll.signal.CatchSignal;
import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.version_3.version_utils.DatasetSegmentRunUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TrajectoryInternalDataSetRun {



    public static void runInternalTrajectory() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        String basicPath = Constant.trajectoriesFilePath;
        String dataTypeFileName = "cell.txt";
//        Integer singleBatchSize = 2;
        PureTriple<String, Integer, List<Integer>> independentData = ConfigureUtils.getIndependentData("BatchUnitSize", "default", "default");
        Integer singleBatchSize = independentData.getValue();
        DatasetSegmentRunUtils.internalDatasetRun(basicPath, dataTypeFileName, singleBatchSize);
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        runInternalTrajectory();
    }
}
