package ecnu.dll.schemes._scheme_utils;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import ecnu.dll.schemes.compared_scheme.w_event_dp.struct.GeneralizedFixedHistoryStructure;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.lang.reflect.Array;
import java.util.*;

public class SPASUtils {
    public static final Boolean Sampled = true;
    public static final Boolean NotSampled = false;
    public static List<Boolean> weightedSVT(List<Double> dHiList, List<Double> privacyBudgetWeightList, List<Double> thresholdList, Double privacyBudget, Double sensitivity) {
        double epsilonA = privacyBudget / 2, epsilonB = epsilonA;
        double rho = LaplaceUtils.getLaplaceNoise(sensitivity, epsilonA);
        int dataSize = dHiList.size();
        double weight = 0, tempNosie, tempWeighted;
        List<Boolean> resultList = new ArrayList<>(dataSize);
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

    public static <T> T[] toArray(TreeMap<String, T> countDataMap, Class<T[]> clazz) {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(clazz.getComponentType(), countDataMap.size());

        int i = 0;
        for (T value : countDataMap.values()) {
            result[i++] = value;
        }
        return result;
    }


    public static Double[] toArray(Map<String, Double> countDataMap, List<String> keyList) {
        int resultSize = keyList.size();
        Double[] result = new Double[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = countDataMap.getOrDefault(keyList.get(i), 0D);
        }
        return result;
    }

    public static Double[] toArray(StreamNoiseCountData lastReleaseNoiseCountData) {
        TreeMap<String, Double> countDataMap = lastReleaseNoiseCountData.getDataMap();
        List<String> keyList = new ArrayList<>(countDataMap.keySet());
        return toArray(countDataMap, keyList);
    }


    public static Double getDH(Integer[] dataA, Double[] dataB) {
        return BasicCalculation.get1Norm(dataA, dataB) / dataA.length;
    }

//    public static Double getDH() {
//
//    }

    public static Double calculateAdjacentDistanceVariance(List<TreeMap<String, Double>> countDataList, List<String> attributeList) {
        int slidingWindowSize = countDataList.size();
        double differSquareSum = 0, differSum = 0, tempDiffer;
        Double[] beforeCountArray = toArray(countDataList.get(0), attributeList);
        Double[] newCountArray;
        for (int i = 1; i < slidingWindowSize; i++) {
            newCountArray = toArray(countDataList.get(i), attributeList);
            tempDiffer = BasicCalculation.get1Norm(newCountArray, beforeCountArray);
            differSum += tempDiffer;
            differSquareSum += tempDiffer * tempDiffer;
            beforeCountArray = newCountArray;
        }
        return differSquareSum / (slidingWindowSize - 1) - Math.pow(differSum / (slidingWindowSize - 1), 2);
    }

    public static Double calculateAdjacentDistanceVariance(List<TreeMap<String, Double>> countDataList) {
        Set<String> keySet = countDataList.get(0).keySet();
        return calculateAdjacentDistanceVariance(countDataList, new ArrayList<>(keySet));
    }

//    public static Double calculateAdjacentDistanceVariance(GeneralizedFixedHistoryStructure<TreeMap<String, Double>> historyData) {
//
//    }

    public static Integer calculateCStar(Double epsilonP, Double deltaP, Double adjacentDistanceVariance) {
        return (int)Math.ceil(epsilonP / (6 * deltaP) * Math.sqrt(3 * adjacentDistanceVariance));
    }
}
