package config_test;

import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import org.junit.Test;

public class ConfigureTest {
    @Test
    public void fun1() {
        String fileName = "log";
        String result = ConfigureUtils.getFileHandleInfo(fileName, "combineRound");
        System.out.println(result);
    }
}
