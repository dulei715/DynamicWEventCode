package ecnu.dll.run.c_dataset_run.pre_process._parameter_generation;

import cn.edu.dll.basic.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class ParameterGenerator {
    public static List<Double> generatePrivacyBudget(Double lowerBound, Double upperBound, int size) {
        List<Double> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(RandomUtil.getRandomDouble(lowerBound, upperBound));
        }
        return result;
    }

    public static void main(String[] args) {
        String
    }
}
