package ecnu.dll.run.c_dataset_run.version_1_2;

import cn.edu.dll.signal.CatchSignal;
import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.version_1_2.version_utils.DatasetRunUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CheckInDataSetRun {


    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        String basicPath = Constant.checkInFilePath;
        String dataTypeFileName = "country.txt";
//        Integer singleBatchSize = 2;
        PureTriple<String, Integer, List<Integer>> independentData = ConfigureUtils.getIndependentData("BatchUnitSize", "default", "default");
        Integer singleBatchSize = independentData.getValue();
//        System.out.println(singleBatchSize);
        DatasetRunUtils.basicDatasetRun(basicPath, dataTypeFileName, singleBatchSize);
    }
}
