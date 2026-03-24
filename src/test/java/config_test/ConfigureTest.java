package config_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import org.junit.Test;

import java.util.List;

public class ConfigureTest {
    @Test
    public void fun1() {
        String fileName = "log";
        String result = ConfigureUtils.getFileHandleInfo(fileName, "combineRound");
        System.out.println(result);
    }
    @Test
    public void fun2() {
        List<Integer> positionSizeList = ConfigureUtils.getDefaultPositionSizeList();
        MyPrint.showList(positionSizeList, ", ");
    }
}
