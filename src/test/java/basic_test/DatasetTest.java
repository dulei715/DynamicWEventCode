package basic_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.dataset.DataSetHandler;
import org.junit.Test;

public class DatasetTest {
    @Test
    public void fun1() {
        Double p0 = 0.05;
        Double standardVariance = 0.0025;
        int size = 100;
        Double[] result = DataSetHandler.getLinearNormalSequence(p0, standardVariance, size);
        MyPrint.showArray(result);
    }
}
