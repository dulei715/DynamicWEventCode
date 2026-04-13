package ecnu.dll.schemes._scheme_utils;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;

import java.util.*;

public class SPASUtils {
    public static final Boolean Sampled = true;
    public static final Boolean NotSampled = false;
    public static List<Boolean> weightedSVT(List<Double> dHiList, List<Double> privacyBudgetWeightList, List<Double> thresholdList, Double privacyBudget, Double sensitivity) {
        double epsilonA = privacyBudget / 2, epsilonB = epsilonA;
        double rho = LaplaceUtils.getLaplaceNoise(sensitivity, epsilonA);
        int dataSize = dHiList.size();
        double weight = 0, tempNosie, tempWeighted;
        List<Boolean> resultList = new ArrayList(dataSize);
        for (int i = 0; i < dataSize; i++) {
            tempWeighted = privacyBudgetWeightList.get(i);
            tempNosie = LaplaceUtils.getLaplaceNoise(2.0/tempWeighted*sensitivity, epsilonB);
            if (dHiList.get(i) + tempNosie > thresholdList.get(i) + rho && weight + tempWeighted <= 1) {
                resultList.add(Sampled);
                weight += tempWeighted;
            } else {
                resultList.add(NotSampled);
            }
        }
        return resultList;
    }

    private static double[] toArray(Map<String, Integer> countDataMap, List<String> keyList) {
        int resultSize = keyList.size();
        double[] result = new double[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = countDataMap.getOrDefault(keyList.get(i), 0);
        }
        return result;
    }

    public static Double calculateAdjacentDistanceVariance(List<TreeMap<String, Integer>> countDataList, List<String> attributeList) {
        int slidingWindowSize = countDataList.size();
        double differSquareSum = 0, differSum = 0, tempDiffer;
        double[] beforeCountArray = toArray(countDataList.get(0), attributeList);
        double[] newCountArray;
        for (int i = 1; i < slidingWindowSize; i++) {
            newCountArray = toArray(countDataList.get(i), attributeList);
            tempDiffer = BasicCalculation.get1Norm(newCountArray, beforeCountArray);
            differSum += tempDiffer;
            differSquareSum += tempDiffer * tempDiffer;
            beforeCountArray = newCountArray;
        }
        return differSquareSum / (slidingWindowSize - 1) - Math.pow(differSum / (slidingWindowSize - 1), 2);
    }

    public static Double calculateCStar(Double epsilonP, Double deltaP, Double adjacentDistanceVariance) {
        return Math.ceil(epsilonP / (6 * deltaP) * Math.sqrt(3 * adjacentDistanceVariance));
    }
}
