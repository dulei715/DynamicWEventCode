package ecnu.dll.run._pre_process.b_parameter_pre_process.version_4_two_group.parameter_generator;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.PureTriple;
import com.google.common.collect.Lists;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.ParameterUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoGroupParameterGenerator {
    public static void generateTwoFixedPrivacyBudgetForAllUsers(String outputFileDir, Double[] privacyBudgetArray, List<Integer> typeIDList) {
        List<Double> candidateList = Arrays.asList(ConfigureUtils.getTwoFixedPrivacyBudget());
        PureTriple<String, Double, List<Double>> ratioTriple;
        try {
            ratioTriple = ConfigureUtils.getIndependentData("TwoFixedUserRatio", "default", "default");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Double windowRatio = ratioTriple.getValue();
        List<Double> changeRatio = ratioTriple.getTag();
        for (Double currentRatio : changeRatio) {

        }
    }
    public static void generateTwoFixedWindowSizeForAllUsers(String outputFileDir, String windowSizeFileNameForPersonalized, List<Integer> typeIDList, List<Integer> candidateList, List<Double> resultStatistic) {

    }
}
