package ecnu.dll.schemes._scheme_utils;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PersonalizedDPTools {
    public static List<Integer> sampleIndex(List<Double> epsilonList, Double threshold) {
        List<Integer> result = new ArrayList<Integer>();
        Double epsilon, probability;
        for (int index = 0; index < epsilonList.size(); index++) {
            epsilon = epsilonList.get(index);
            if (epsilon >= threshold) {
                result.add(index);
            } else {
                probability = (Math.exp(epsilon) - 1) / (Math.exp(threshold) - 1);
                if (RandomUtil.isChosen(probability)) {
                    result.add(index);
                }
            }
        }
        return result;
    }


    public static TreeMap<Double, Double> getNoiseCount(TreeMap<Double, Integer> data) {
        Double tempBudget;
        Integer tempCount;
        Double tempNoiseCount;
        TreeMap<Double, Double> result = new TreeMap<Double, Double>();
        for (Map.Entry<Double, Integer> entry : data.entrySet()) {
            tempBudget = entry.getKey();
            tempCount = entry.getValue();
            tempNoiseCount = tempCount + LaplaceUtils.getLaplaceNoise(1, tempBudget);
            result.put(tempBudget, tempNoiseCount);
        }
        return result;
    }

}
