package basic_test;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.statistic.DistributionUtil;
import cn.edu.dll.statistic.StatisticTool;
import org.junit.Test;

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

        
    }
}
