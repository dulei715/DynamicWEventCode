package important_test;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import org.junit.Test;

public class PaperExampleTest {
//    @Test
//    public void getPQ() {
//        int d = 2;
//        double[] epsilonArr = {0.1, 0.4, 0.8};
//        double[] g = {2, 5, 3};
//        double[] z_arr = {3, 5, 2, 8, 6};
//        double[] qArr = new double[epsilonArr.length];
//        double[] pArr = new double[epsilonArr.length];
//        int n = 10;
//        int z = (int)BasicArrayUtil.getMaximalValue(z_arr);
//        double var = (n-z)*1.0/(z*(n-1));
//        System.out.println(var);
//        double top = 0, bottom = 0;
//        for (int i = 0; i < pArr.length; ++i) {
//            qArr[i] = BasicCalculation.getPrecisionValue(1.0 / (Math.exp(epsilonArr[i])+d-1), 2);
//            pArr[i] = BasicCalculation.getPrecisionValue(qArr[i] * Math.exp(epsilonArr[i]), 2);
//            top += d * g[i] * qArr[i] * (1-qArr[i]);
//            bottom += g[i] * (pArr[i]-qArr[i]);
//        }
//        var += top / (n * bottom * bottom);
//        MyPrint.showArray(qArr, ", ");
//        MyPrint.showArray(pArr, ", ");
//        System.out.println(var);
//    }
}
