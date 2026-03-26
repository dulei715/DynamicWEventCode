package config_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ConfigureTest {
    @Test
    public void fun1() {
        String fileName = "log";
        String result = ConfigureUtils.getFileHandleInfo(fileName, "combineRound");
        System.out.println(result);
    }
//    @Test
//    public void fun2() {
//        List<Integer> positionSizeList = ConfigureUtils.getDefaultPositionSizeList();
//        MyPrint.showList(positionSizeList, ", ");
//    }

    @Test
    public void fun3() {
        List<Integer> positionSizeList = ConfigureUtils.getIndependentPositionSizeList("default");
        MyPrint.showList(positionSizeList, ", ");
    }

    @Test
    public void fun4() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Double> epsilonList = ConfigureUtils.getIndependentPrivacyBudgetList("default");
        MyPrint.showList(epsilonList, ", ");
        List<Object> epsilonObjList = ConfigureUtils.getIndependentData("PrivacyBudget", "default", "default").getTag();
        MyPrint.showList(epsilonObjList, ", ");
    }
}
