package ecnu.dll.run.c_dataset_run.version_3.basic_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.signal.CatchSignal;
import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.utils.PostProcessUtils;
import ecnu.dll.run.c_dataset_run.version_3.version_utils.DatasetSegmentRunUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TLNSDataSetRun {
    public static void runTLNS() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String basicPath = Constant.tlnsFilePath;
        String dataTypeFileName = "status.txt";
        PureTriple<String, Integer, List<Integer>> independentData = ConfigureUtils.getIndependentData("BatchUnitSize", "default", "default");
        Integer singleBatchSize = independentData.getValue();
        DatasetSegmentRunUtils.basicDatasetRun(basicPath, dataTypeFileName, singleBatchSize);
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.tlnsFilePath;
        String finalResultDirName = "3.tlns_result";

        runTLNS();

        // 4. 后处理
        String rawDataDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "group_output");
        String extractResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "extract_result");
        String finalResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, finalResultDirName);
        PostProcessUtils.combineAndExtractCombineResult(rawDataDir, extractResultDir);
        PostProcessUtils.furtherCombine(extractResultDir, finalResultDir);
    }
}
