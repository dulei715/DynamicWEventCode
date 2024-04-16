package basic_test;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.statistic.DistributionUtil;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll.schemes.main_scheme.metric.MechanismError;
import org.junit.Test;

import java.util.TreeMap;

public class MechanismErrorTest {

    @Test
    public void fun1() {
        double[] epsilonArray = BasicArrayUtil.getIncreasedoubleNumberArray(0.1, 0.01, 10, 2);
        MyPrint.showArray(epsilonArray, "; ");
        int size = epsilonArray.length;
        Double[] randomDoubleArray = RandomUtil.getRandomDoubleArray(0D, 1D, size);
//        MyPrint.showArray(randomDoubleArray, "; ");
        Double sum = BasicArrayUtil.getSum(randomDoubleArray);
//        System.out.println(sum);
        BasicArrayUtil.linearTransform(randomDoubleArray, 1.0/sum, 0D);
//        MyPrint.showArray(randomDoubleArray, "; ");

        TreeMap<Double, Double> map = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            map.put(epsilonArray[i], randomDoubleArray[i]);
        }

        Double[] errorArray = new Double[size], sampleErrorArray = new Double[size], dpErrorArray = new Double[size];
        for (int i = 0; i < size; i++) {
            sampleErrorArray[i] = MechanismError.getSampleError(1000, map, epsilonArray[i]);
            dpErrorArray[i] = MechanismError.getDPError(epsilonArray[i]);
            errorArray[i] = sampleErrorArray[i] + dpErrorArray[i];
        }
        MyPrint.showArray(sampleErrorArray, "; ");
        MyPrint.showArray(dpErrorArray, "; ");
        MyPrint.showArray(errorArray, "; ");
    }
}
