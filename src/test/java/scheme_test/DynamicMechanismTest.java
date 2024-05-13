package scheme_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.PersonalizedDynamicBudgetDistribution;
import ecnu.dll.struts.StreamDataElement;
import ecnu.dll.utils.TestTools;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class DynamicMechanismTest {
    private List<StreamDataElement<Boolean>> dataList;
    private Random random = new Random(1);

    private void initializeHistoricalBudgetListList(ArrayList<ArrayList<Double>> data, int size) {
        for (int i = 0; i < size; i++) {
            data.add(new ArrayList<Double>());
        }
    }
    private void addCurrentElementListToHistoricalBudgetListList(ArrayList<ArrayList<Double>> data, List<Double> colDataList) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).add(colDataList.get(i));
        }
    }

    @Test
    public void personalizeDynamicPrivacyBudgetDistributionTest() {
        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;
        TreeMap<String, Integer> tempRealMapResult;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
//        budgetList = TestTools.generateEpsilonList(this.random, userSize);
//        windowSizeList = TestTools.generateWindowSizeList(this.random, userSize, windowSizeUpperBound);
        PersonalizedDynamicBudgetDistribution pdbd = new PersonalizedDynamicBudgetDistribution(dataElementList.get(0).getKeyList(), userSize);
        List<Double> tempBackwardBudgetList = new ArrayList<>(), tempForwardBudgetList;
        List<Integer> tempBackwardWindowSizeList = new ArrayList<>(), tempForwardWindowSizeList;
//        ArrayList<ArrayList<Double>> historicalBudgetListList = new ArrayList<>();
//        initializeHistoricalBudgetListList(historicalBudgetListList, userSize);
        List<Double> historicalBudgetSumList = new ArrayList<>();
        for (int i = 0; i < timeUpperBound; i++) {
            System.out.printf("Time slot %d\n", i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
            tempRealMapResult = BooleanStreamDataElementUtils.getCountByGivenElementType(true, dataElementList);
            TestTools.generateAndSetLegalBackwardPrivacyBudgetAndWindowSizeList(this.random, userSize, windowSizeUpperBound, i, historicalBudgetListList, tempBackwardBudgetList, tempBackwardWindowSizeList);
            addCurrentElementListToHistoricalBudgetListList(historicalBudgetListList, tempBackwardBudgetList);
            tempForwardBudgetList = TestTools.generateEpsilonList(this.random, userSize);
            tempForwardWindowSizeList = TestTools.generateWindowSizeList(this.random, userSize, windowSizeUpperBound);
            boolean isPublication = pdbd.updateNextPublicationResult(dataElementList, tempBackwardBudgetList, tempBackwardWindowSizeList, tempForwardBudgetList, tempForwardWindowSizeList);
//            String.format("status: %s; dis: %f; err: %f", isPublication, )
            System.out.println(isPublication);
            MyPrint.showMap(pdbd.getReleaseNoiseCountMap().getDataMap());
//            MyPrint.showList(dataElementList, ConstantValues.LINE_SPLIT);
//            MyPrint.showList(budgetList);
//            MyPrint.showList(windowSizeList);
            MyPrint.showSplitLine("*", 50);
            MyPrint.showMap(tempRealMapResult);
            MyPrint.showSplitLine("*", 150);
        }
    }
}
