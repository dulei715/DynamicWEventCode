package basic_test;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import com.sun.javafx.binding.StringFormatter;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetAbsorption;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetDistribution;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetDistribution;
import ecnu.dll.struts.StreamDataElement;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class MechanismTest {

    public List<StreamDataElement<Boolean>> dataList;
    Random random = new Random(1);

    public List<StreamDataElement<Boolean>> generateStreamDataElementListA(int userSize, int typeSize) {
        List<Boolean> tempList;
        StreamDataElement<Boolean> tempElement;
        List<StreamDataElement<Boolean>> result = new ArrayList<>();
        for (int i = 0; i < userSize; i++) {
            tempList = new ArrayList<>();
            for (int j = 0; j < typeSize; j++) {
                tempList.add(random.nextBoolean());
            }
            tempElement = new StreamDataElement<>(tempList);
            result.add(tempElement);
//            System.out.println(tempElement);
        }
        return result;
    }

    public List<StreamDataElement<Boolean>> generateStreamDataElementList(int userSize, int typeSize) {
        List<Boolean> tempList;
        StreamDataElement<Boolean> tempElement;
        List<StreamDataElement<Boolean>> result = new ArrayList<>();
        Integer tempIndex;
        for (int i = 0; i < userSize; i++) {
            tempList = new ArrayList<>();
            tempIndex = random.nextInt(typeSize);
            for (int j = 0; j < typeSize; j++) {
//                tempList.add(random.nextBoolean());
                if (tempIndex.equals(j)) {
                    tempList.add(true);
                } else {
                    tempList.add(false);
                }
            }
            tempElement = new StreamDataElement<>(tempList);
            result.add(tempElement);
        }
        return result;
    }

    public List<Double> generateEpsilonList(int userSize) {
        List<Double> result = new ArrayList<>();
        Double randomDouble;

        for (int i = 0; i < userSize; i++) {
            while ((randomDouble = this.random.nextDouble())<0.01);
            randomDouble = BigDecimal.valueOf(randomDouble*10).setScale(2, RoundingMode.HALF_UP).doubleValue();
            result.add(randomDouble);
        }
        return result;
    }

    public List<Integer> generateWindowSizeList(int userSize, int windowSizeUpperBound) {
        List<Integer> windowSizeList = new ArrayList<>();
        Integer randomInt;
        for (int i = 0; i < userSize; i++) {
            randomInt = this.random.nextInt(windowSizeUpperBound) + 1;
            windowSizeList.add(randomInt);
        }
        return windowSizeList;
    }

    @Test
    public void fun1() {

        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;
        List<Double> budgetList;
        List<Integer> windowSizeList;

        dataElementList = generateStreamDataElementList(userSize, typeSize);
        budgetList = generateEpsilonList(userSize);
        windowSizeList = generateWindowSizeList(userSize, windowSizeUpperBound);
        PersonalizedBudgetDistribution pbd = new PersonalizedBudgetDistribution(dataElementList.get(0).getKeyList(), budgetList, windowSizeList);
        TreeMap<String, Integer> realMapResult;

        for (int i = 0; i < timeUpperBound; i++) {
            System.out.println(i);
            dataElementList = generateStreamDataElementList(userSize, typeSize);
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
    public void fun2() {

        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;
        List<Double> budgetList;
        List<Integer> windowSizeList;

        dataElementList = generateStreamDataElementList(userSize, typeSize);
        budgetList = generateEpsilonList(userSize);
        windowSizeList = generateWindowSizeList(userSize, windowSizeUpperBound);
        PersonalizedBudgetAbsorption pba = new PersonalizedBudgetAbsorption(dataElementList.get(0).getKeyList(), budgetList, windowSizeList);
        TreeMap<String, Integer> realMapResult;

        for (int i = 0; i < timeUpperBound; i++) {
            System.out.println(i);
            dataElementList = generateStreamDataElementList(userSize, typeSize);
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

    @Test
    public void fun3() {

        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        double privacyBudget = 0.5;
        int windowSize = 5;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;

        dataElementList = generateStreamDataElementList(userSize, typeSize);
        BudgetDistribution pbd = new BudgetDistribution(dataElementList.get(0).getKeyList(), privacyBudget, windowSize);
        TreeMap<String, Integer> realMapResult;

        for (int i = 0; i < timeUpperBound; i++) {
            System.out.println(i);
            dataElementList = generateStreamDataElementList(userSize, typeSize);
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
    public void fun4() {

        int userSize = 100;
        int typeSize = 5;

        int windowSize = 5;
        double privacyBudget = 0.5;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;

        dataElementList = generateStreamDataElementList(userSize, typeSize);
        BudgetAbsorption budgetAbsorption = new BudgetAbsorption(dataElementList.get(0).getKeyList(), privacyBudget, windowSize);
        TreeMap<String, Integer> realMapResult;

        for (int i = 0; i < timeUpperBound; i++) {
            System.out.println(i);
            dataElementList = generateStreamDataElementList(userSize, typeSize);
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
