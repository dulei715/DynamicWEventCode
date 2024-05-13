package ecnu.dll.utils;

import ecnu.dll.struts.StreamDataElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestTools {
    public static List<StreamDataElement<Boolean>> generateStreamDataElementListA(Random random, int userSize, int typeSize) {
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
        }
        return result;
    }

    public static List<StreamDataElement<Boolean>> generateStreamDataElementList(Random random, int userSize, int typeSize) {
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

    public static List<Double> generateEpsilonList(Random random, int userSize) {
        List<Double> result = new ArrayList<>();
        Double randomDouble;

        for (int i = 0; i < userSize; i++) {
            while ((randomDouble = random.nextDouble())<0.01);
            randomDouble = BigDecimal.valueOf(randomDouble*10).setScale(2, RoundingMode.HALF_UP).doubleValue();
            result.add(randomDouble);
        }
        return result;
    }

    public static List<Integer> generateWindowSizeList(Random random, int userSize, int windowSizeUpperBound) {
        List<Integer> windowSizeList = new ArrayList<>();
        Integer randomInt;
        for (int i = 0; i < userSize; i++) {
            randomInt = random.nextInt(windowSizeUpperBound) + 1;
            windowSizeList.add(randomInt);
        }
        return windowSizeList;
    }

    public static void generateAndSetLegalBackwardPrivacyBudgetAndWindowSizeList(Random random, int userSize, int windowSizeUpperBound, int currentTime, ArrayList<ArrayList<Double>> historicalBudgetListList, List<Double> budgetList, List<Integer> windowSizeList) {
        budgetList.clear();
        windowSizeList.clear();
        Double tempTotalBudgets, randomDouble, remainBudget;
        Integer randomInt;
        List<Double> tempHistoricalBudgetList;
        for (int i = 0; i < userSize; i++) {
            tempHistoricalBudgetList = historicalBudgetListList.get(i);
            while (true) {
                randomDouble = random.nextDouble();
                if (randomDouble < 0.01) {
                    continue;
                }
                randomInt = random.nextInt(windowSizeUpperBound) + 1;
                tempTotalBudgets = 0D;
                for (int j = Math.max(0, currentTime-randomInt);  j < currentTime; ++j) {
                    tempTotalBudgets += tempHistoricalBudgetList.get(j);
                }
                randomDouble = BigDecimal.valueOf(randomDouble*10).setScale(2, RoundingMode.HALF_UP).doubleValue();
                remainBudget = randomDouble - tempTotalBudgets;
                if (remainBudget < 0.01) {
                    continue;
                }
                break;
            }
            budgetList.add(randomDouble);
            windowSizeList.add(randomInt);
        }
    }

}
