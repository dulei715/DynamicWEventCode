package scheme_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.nullified.NullifiedBound;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetDistribution;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.utils.TestTools;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class FixedMechanismTest {

    private List<StreamDataElement<Boolean>> dataList;
    private Random random = new Random(1);

    @Test
    public void personalizedBudgetDistributionTest() {

        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;
        List<Double> budgetList;
        List<Integer> windowSizeList;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
        budgetList = TestTools.generateEpsilonList(this.random, userSize);
        windowSizeList = TestTools.generateWindowSizeList(this.random, userSize, windowSizeUpperBound);
        PersonalizedBudgetDistribution pbd = new PersonalizedBudgetDistribution(dataElementList.get(0).getKeyList(), budgetList, windowSizeList);
        TreeMap<String, Integer> realMapResult;

        for (int i = 0; i < timeUpperBound; i++) {
            System.out.println(i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
            realMapResult = BooleanStreamDataElementUtils.getCountByGivenElementType(true, dataElementList);
            boolean isPublication = pbd.updateNextPublicationResult(dataElementList);
//            String.format("status: %s; dis: %f; err: %f", isPublication, )
            System.out.println(isPublication);
            MyPrint.showMap(pbd.getReleaseNoiseCountMap().getDataMap());
//            MyPrint.showList(dataElementList, ConstantValues.LINE_SPLIT);
//            MyPrint.showList(budgetList);
//            MyPrint.showList(windowSizeList);
            MyPrint.showSplitLine("*", 50);
            MyPrint.showMap(realMapResult);
            MyPrint.showSplitLine("*", 150);

        }


    }
    @Test
    public void personalizedBudgetAbsorptionTest() {

        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        int timeUpperBound = 100;
//        int nullifiedType = NullifiedBound.AverageType;
        int nullifiedType = NullifiedBound.MaximalType;

        List<StreamDataElement<Boolean>> dataElementList;
        List<Double> budgetList;
        List<Integer> windowSizeList;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
        budgetList = TestTools.generateEpsilonList(this.random, userSize);
        windowSizeList = TestTools.generateWindowSizeList(this.random, userSize, windowSizeUpperBound);
        PersonalizedBudgetAbsorption pba = new PersonalizedBudgetAbsorption(dataElementList.get(0).getKeyList(), budgetList, windowSizeList, nullifiedType);
        TreeMap<String, Integer> realMapResult;

        for (int i = 0; i < timeUpperBound; i++) {
            System.out.println(i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
            realMapResult = BooleanStreamDataElementUtils.getCountByGivenElementType(true, dataElementList);
            boolean isPublication = pba.updateNextPublicationResult(dataElementList);
//            String.format("status: %s; dis: %f; err: %f", isPublication, )
            System.out.println(isPublication);
            MyPrint.showMap(pba.getReleaseNoiseCountMap().getDataMap());
//            MyPrint.showList(dataElementList, ConstantValues.LINE_SPLIT);
//            MyPrint.showList(budgetList);
//            MyPrint.showList(windowSizeList);
            MyPrint.showSplitLine("*", 50);
            MyPrint.showMap(realMapResult);
            MyPrint.showSplitLine("*", 150);

        }


    }
}
