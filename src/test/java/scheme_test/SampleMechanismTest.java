package scheme_test;

import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SampleMechanismTest {
    @Test
    public void fun1() {
//        Double[] threeEpsilon = new Double[]{1.0/2, 3.0/10, 1.0};
//        Double[] threeEpsilon = new Double[]{3.0/10, 2.0/5, 1.0/5};
        Double[] threeEpsilon = new Double[]{3.0/20, 1.0/5, 1.0/10};


//        int[] groupSizeArray = new int[]{40, 50, 10};
        int[] groupSizeArray = new int[]{33, 33, 33};
//        int size = threeEpsilon.length * groupSize;
        List<Double> budgetList = new ArrayList<>();
        for (int k = 0; k < threeEpsilon.length; ++k) {
            for (int i = 0; i < groupSizeArray[k]; i++) {
                budgetList.add(threeEpsilon[k]);
            }
        }
        Double[] minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(budgetList);
        System.out.println(minimalEpsilonAndError[0]);
        System.out.println(minimalEpsilonAndError[1]);
    }
}
