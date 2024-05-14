package scheme_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetAbsorption;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetDistribution;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.utils.TestTools;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class BasicMechanismTest {

    private List<StreamDataElement<Boolean>> dataList;
    private Random random = new Random(1);

    @Test
    public void basicBudgetDistributionTest() {

        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        double privacyBudget = 0.5;
        int windowSize = 5;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
        BudgetDistribution pbd = new BudgetDistribution(dataElementList.get(0).getKeyList(), privacyBudget, windowSize);
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
    public void basicBudgetAbsorptionTest() {

        int userSize = 100;
        int typeSize = 5;

        int windowSize = 5;
        double privacyBudget = 0.5;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
        BudgetAbsorption budgetAbsorption = new BudgetAbsorption(dataElementList.get(0).getKeyList(), privacyBudget, windowSize);
        TreeMap<String, Integer> realMapResult;

        for (int i = 0; i < timeUpperBound; i++) {
            System.out.println(i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
            realMapResult = BooleanStreamDataElementUtils.getCountByGivenElementType(true, dataElementList);
            boolean isPublication = budgetAbsorption.updateNextPublicationResult(dataElementList);
//            String.format("status: %s; dis: %f; err: %f", isPublication, )
            System.out.println(isPublication);
            MyPrint.showMap(budgetAbsorption.getReleaseNoiseCountMap().getDataMap());
//            MyPrint.showList(dataElementList, ConstantValues.LINE_SPLIT);
//            MyPrint.showList(budgetList);
//            MyPrint.showList(windowSizeList);
            MyPrint.showSplitLine("*", 50);
            MyPrint.showMap(realMapResult);
            MyPrint.showSplitLine("*", 150);

        }


    }





























}
