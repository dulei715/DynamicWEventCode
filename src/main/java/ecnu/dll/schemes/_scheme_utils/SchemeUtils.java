package ecnu.dll.schemes._scheme_utils;

import cn.edu.dll.map.MapUtils;
import cn.edu.dll.statistic.StatisticTool;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SchemeUtils {

    public static Double[] selectOptimalBudget(List<Double> privacyBudgetList) {
        Map<Double, Integer> countMap = StatisticTool.countHistogramNumber(privacyBudgetList);
        return MechanismErrorUtils.getMinimalEpsilonAndError((TreeMap<Double, Integer>) countMap);
    }

    public static Double getDissimilarity(Double[] budgetArray) {
        return null;
    }


}
