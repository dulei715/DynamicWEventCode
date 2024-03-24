package scheme_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.compared_scheme.rescure_dp.RescueDP;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.RescueDPUtils;
import ecnu.dll.metric.Measurement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RescueDPTest {
//    @Test
//    public void fun1() {
//        Double[][] regionStatisticMatrix = new Double[][]{
//                {90D, 50D, 10D, 12D, 12D, 12D, 12D, 18D, 18D, 20D},
//                {4D, 3D, 5D, 5D, 5D, 5D, 10D, 10D, 14D, 18D},
//                {20D, 10D, 10D, 20D, 20D, 20D, 50D, 60D, 80D, 80D}
//        };
//        Boolean[][] isSampledMatrix = new Boolean[][]{
//                {false, false, true, true, false, false, false, true, false},
//                {false, false, true, false, false, false, true, false, true},
//                {false, false, false, false, false, false, true, true, true}
//        };
//        RescueDP rescueDP = new RescueDP();
//        rescueDP.setRegionStatisticMatrix(regionStatisticMatrix);
//        rescueDP.setIsSampledMatrix(isSampledMatrix);
//        int currentTime = 9;
////        int sampleSize = 3;
////        double tao1 = 50, tao2 = 0.8, tao3 = 20;
//        List<Integer> sampleList = new ArrayList<>();
//        sampleList.add(0);
//        sampleList.add(1);
//        sampleList.add(2);
////        List<List<Integer>> grouping = rescueDP.dynamicGrouping(currentTime, sampleList);
////        MyPrint.showList(grouping);
//    }
    
//    @Test
//    public void fun2() {
//        Double[] dataA = new Double[] {-1D, -1D, 2D, 3D, 4D};
//        Double[] dataB = new Double[] {-1D, -4D, 3D, 7D};
////        Double result = RescueDPUtils.getNonNegativePearsonCorrelationCoefficient(dataA, dataB);
////        System.out.println(result);
//    }

    @Test
    public void fun3() {
        Double[][] regionStatisticMatrix = new Double[][]{
                {90D, 50D, 10D, 12D, 12D, 12D, 12D, 18D, 18D, 20D},
                {4D, 3D, 5D, 5D, 5D, 5D, 10D, 10D, 14D, 18D},
                {20D, 10D, 10D, 20D, 20D, 20D, 50D, 60D, 80D, 80D}
        };
        Integer regionSize = 3;
        Integer timeUpperBound = 10;
        Double privacyBudget = 1D;

        RescueDP rescueDP = new RescueDP(regionSize, timeUpperBound, privacyBudget);
        rescueDP.setRegionStatisticMatrix(regionStatisticMatrix);
        rescueDP.privateRelease();

        MyPrint.show2DimensionDoubleArray(rescueDP.getEstimateStatisticMatrix());

        MyPrint.showSplitLine("*", 150);

        double[] resultA = Measurement.getMeanAbsoluteError(rescueDP.getRegionStatisticMatrix(), rescueDP.getEstimateStatisticMatrix());
        double[] resultB = Measurement.getMeanRelativeError(rescueDP.getRegionStatisticMatrix(), rescueDP.getEstimateStatisticMatrix());

        MyPrint.showArray(resultA, "; ");
        MyPrint.showArray(resultB, "; ");

    }
    
    
}
