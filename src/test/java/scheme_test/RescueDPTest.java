package scheme_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.compared_scheme.rescure_dp.RescueDP;
import org.junit.Test;

import java.util.List;

public class RescueDPTest {
    @Test
    public void fun1() {
        double[][] regionValueMatrix = new double[][]{
                {90, 50, 10, 12, 12, 12, 12, 18, 18, 20},
                {4, 3, 5, 5, 5, 5, 10, 10, 14, 18},
                {20, 10, 10, 20, 20, 20, 50, 60, 80, 80}
        };
        double[][] regionStatisticMatrix;
        boolean[][] isSampledMatrix = new boolean[][]{
                {false, false, true, true, false, false, false, true, false},
                {false, false, true, false, false, false, true, false, true},
                {false, false, false, false, false, false, true, true, true}
        };
        RescueDP rescueDP = new RescueDP();
        rescueDP.setRegionValueMatrix(regionValueMatrix);
        rescueDP.setIsSampledMatrix(isSampledMatrix);
        int currentTime = 9;
        int sampleSize = 3;
        double tao1 = 50, tao2 = 0.8, tao3 = 20;
        List<List<Integer>> grouping = rescueDP.dynamicGrouping(currentTime, sampleSize, tao1, tao2, tao3);
        MyPrint.showList(grouping);
    }
}
