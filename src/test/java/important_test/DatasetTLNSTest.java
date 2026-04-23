package important_test;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.PurePair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function.DataGenerationFunction;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function.TLNSFunction;
import org.junit.Test;

import java.util.List;

public class DatasetTLNSTest {


    @Test
    public void tlnsClipTest() {
        Double p0 = 0.05;
        Double gaussAverage = 0D;
        Double gaussStandardVariance = 0.0025;
        String datasetName = "tlns";
        int timeStampSize = Integer.parseInt(ConfigureUtils.getFileHandleInfo(datasetName, "timeStampSize"));
//        System.out.println(timeStampSize);
        TLNSFunction tlnsFunction = new TLNSFunction(p0, gaussAverage, gaussStandardVariance);
        PurePair<List<Double>, List<Integer>> listListPurePair = tlnsFunction.nextProbabilityWithClip(timeStampSize);
        List<Double> dataList = listListPurePair.getKey();
        List<Integer> clipList = listListPurePair.getValue();
        int lowerClipSize = 0, upperClipSize = 0, realSize = 0;
        for (Integer status : clipList) {
            if (status == TLNSFunction.NoneClip) {
                realSize++;
            } else if (status == TLNSFunction.LowerClip) {
                lowerClipSize++;
            } else {
                upperClipSize++;
            }
        }
        System.out.println("lowerClipSize:" + lowerClipSize);
        System.out.println("upperClipSize:" + upperClipSize);
        System.out.println("realSize:" + realSize);

        System.out.println((lowerClipSize + upperClipSize) * 1.0 / timeStampSize);
        MyPrint.showSplitLine("*", 150);
        MyPrint.showList(dataList);
    }
}
