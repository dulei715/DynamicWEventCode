package scheme_test;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.result.ExperimentResult;
import ecnu.dll.run.b_parameter_run.basic.version_1.ChangeBudgetRun;
import ecnu.dll.run.b_parameter_run.basic.version_1.ChangeWindowSizeRun;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes.basic_scheme.NonPrivacyMechanism;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetAbsorption;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetDistribution;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.utils.TestTools;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    @Test
    public void nonPrivacyMechanismTest() {
        int userSize = 100;
        int typeSize = 5;

//        int windowSize = 5;
//        double privacyBudget = 0.5;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
        NonPrivacyMechanism scheme = new NonPrivacyMechanism(dataElementList.get(0).getKeyList());
        TreeMap<String, Integer> realMapResult;

        for (int i = 0; i < timeUpperBound; i++) {
            System.out.println(i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
            realMapResult = BooleanStreamDataElementUtils.getCountByGivenElementType(true, dataElementList);
            boolean isPublication = scheme.updateNextPublicationResult(dataElementList);
//            String.format("status: %s; dis: %f; err: %f", isPublication, )
            System.out.println(isPublication);
            MyPrint.showMap(scheme.getReleaseCountMap().getDataMap());
//            MyPrint.showList(dataElementList, ConstantValues.LINE_SPLIT);
//            MyPrint.showList(budgetList);
//            MyPrint.showList(windowSizeList);
            MyPrint.showSplitLine("*", 50);
            MyPrint.showMap(realMapResult);
            MyPrint.showSplitLine("*", 150);

        }
    }

    @Test
    public void resultChangeBudgetTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int userSize = 100;
        int characterTypeSize = 5;

        int windowSize = 5;
//        double privacyBudget = 0.5;
        List<Double> privacyBudgetList = Arrays.asList(0.1, 0.28, 0.46, 0.64, 0.82, 1.0);

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, characterTypeSize);
        List<String> dataType = dataElementList.get(0).getKeyList();

        NonPrivacyMechanism scheme = new NonPrivacyMechanism(dataType);
        TreeMap<String, Integer> realMapResult;
        List<List<StreamDataElement<Boolean>>> dataList = new ArrayList<>();
        for (int i = 0; i < timeUpperBound; i++) {
//            System.out.println(i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, characterTypeSize);
            dataList.add(dataElementList);
        }

        List<ExperimentResult> result = ChangeBudgetRun.run(dataType, dataList, privacyBudgetList, windowSize);
        MyPrint.showList(result, ConstantValues.LINE_SPLIT);

    }

    @Test
    public void resultChangeWindowSizeTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int userSize = 100;
        int typeSize = 5;

        double privacyBudget = 1.0;
        List<Integer> windowSizeList = Arrays.asList(4, 5, 10, 12, 3, 20);

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
        List<String> dataType = dataElementList.get(0).getKeyList();

        NonPrivacyMechanism scheme = new NonPrivacyMechanism(dataType);
        TreeMap<String, Integer> realMapResult;
        List<List<StreamDataElement<Boolean>>> dataList = new ArrayList<>();
        for (int i = 0; i < timeUpperBound; i++) {
//            System.out.println(i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
            dataList.add(dataElementList);
        }

        List<ExperimentResult> result = ChangeWindowSizeRun.run(dataType, dataList, privacyBudget, windowSizeList);
        MyPrint.showList(result, ConstantValues.LINE_SPLIT);

    }





























}
