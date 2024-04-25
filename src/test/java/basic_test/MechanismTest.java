package basic_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
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

    public List<StreamDataElement<Boolean>> generateStreamDataElementList(int userSize, int typeSize) {
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

    public List<Double> generateEpsilonList(int userSize) {
        List<Double> result = new ArrayList<>();
        Double randomDouble;

        for (int i = 0; i < userSize; i++) {
            while ((randomDouble = this.random.nextDouble())<0.1);
            randomDouble = BigDecimal.valueOf(randomDouble*10).setScale(2, RoundingMode.HALF_UP).doubleValue();
            result.add(randomDouble);
        }
        return result;
    }

    public List<Integer> generateWindowSizeList(int userSize) {
        List<Integer> windowSizeList = new ArrayList<>();
        Integer randomInt;
        int windowSizeUpperBound = 6;
        for (int i = 0; i < userSize; i++) {
            randomInt = this.random.nextInt(windowSizeUpperBound);
            windowSizeList.add(randomInt);
        }
        return windowSizeList;
    }

    @Test
    public void fun1() {

        StreamDataElement<Boolean> tempElement;




        MyPrint.showSplitLine("*", 150);
        List<Integer> indexList = new ArrayList<>();
        indexList.add(1);
        indexList.add(3);
        indexList.add(4);
        indexList.add(5);
        indexList.add(8);
        indexList.add(9);

        TreeMap<String, Integer> result = BooleanStreamDataElementUtils.getCountByGivenElementType(true, dataList, indexList);
        MyPrint.showMap(result);
    }
}
