package basic_test;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation.utils.PositionGroupUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PositionGroupUtilsTest {
    @Test
    public void fun1() {
        String[] dataArray = new String[]{
                "A", "B", "C", "D", "E", "F", "G"
        };
        Set<String> dataSet = Arrays.stream(dataArray).collect(Collectors.toCollection(HashSet::new));
        MyPrint.showCollection(dataSet, ", ");
        Map<String, String> resultMap = PositionGroupUtils.toGroup(dataSet, 3);
        MyPrint.showMap(resultMap);
    }
}
