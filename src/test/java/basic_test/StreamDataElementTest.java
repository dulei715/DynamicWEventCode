package basic_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.struts.stream_data.StreamDataElement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class StreamDataElementTest {
    @Test
    public void fun1() {
        List<StreamDataElement<Boolean>> dataList = new ArrayList<>();
        StreamDataElement<Boolean> tempElement;
        List<Boolean> tempList;
        Random random = new Random(1);
        for (int i = 0; i < 10; i++) {
            tempList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                tempList.add(random.nextBoolean());
            }
            tempElement = new StreamDataElement<>(tempList);
            dataList.add(tempElement);
            System.out.println(tempElement);
        }

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
