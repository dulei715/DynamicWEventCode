package ecnu.dll.schemes._scheme_utils;

import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import cn.edu.dll.map.MapUtils;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll.struts.StreamNoiseCountData;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SchemeUtils {

    public static Double[] selectOptimalBudget(List<Double> privacyBudgetList) {
        Map<Double, Integer> countMap = StatisticTool.countHistogramNumber(privacyBudgetList);
        return MechanismErrorUtils.getMinimalEpsilonAndError((TreeMap<Double, Integer>) countMap);
    }

    public static Double getDissimilarity(TreeMap<String, Integer> currentCountMap, StreamNoiseCountData lastTimeNoiseCountData, Double epsilon) {
        int size = currentCountMap.size();
        TreeMap<String, Double> lastTimeNoiseMap = lastTimeNoiseCountData.getDataMap();
        if (size != lastTimeNoiseMap.size()) {
            throw new RuntimeException("The size of current count result is not equal to the historical noise count result!");
        }
        Double average = 0D;
        for (Map.Entry<String, Integer> currentEntry : currentCountMap.entrySet()) {
//            System.out.println(currentEntry.getValue() + ";;; " + lastTimeNoiseMap);
//            if (currentEntry.getValue() == null) {
//                System.out.println("null");
//            }
            average += Math.abs(currentEntry.getValue() - lastTimeNoiseMap.get(currentEntry.getKey()));
        }
        average /= size;
        return average + LaplaceUtils.getLaplaceNoise(1.0/size, epsilon);
    }


}
