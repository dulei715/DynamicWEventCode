package ecnu.dll.run.c_dataset_run.version_3;

import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.version_3.version_utils.DatasetSegmentRunUtils;
import ecnu.dll.utils.CatchSignal;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TLNSDataSetRun {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        String basicPath = Constant.tlnsFilePath;
        String dataTypeFileName = "status.txt";
        PureTriple<String, Integer, List<Integer>> independentData = ConfigureUtils.getIndependentData("BatchUnitSize", "default", "default");
        Integer singleBatchSize = independentData.getValue();
        DatasetSegmentRunUtils.basicDatasetRun(basicPath, dataTypeFileName, singleBatchSize);
    }
}
