package ecnu.dll.schemes._scheme_utils;

import cn.edu.dll.basic.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class ParameterUtils {
    public static List<Double> generateDoubleList(Double lowerBound, Double upperBound, int typeSize, int groupElementSize) {
        List<Double> resultList = new ArrayList<>();
        Double step = (upperBound - lowerBound) / (typeSize - 1);
        if (step <= 0) {
            for (int i = 0; i < groupElementSize; i++) {
                resultList.add(lowerBound);
            }
            return resultList;
        }
        for (double value = lowerBound; value <= upperBound; value+=step) {
            for (int i = 0; i < groupElementSize; i++) {
                resultList.add(value);
            }
        }
        return resultList;
    }
    public static List<Double> generateRandomDoubleList(Double lowerBound, Double upperBound, int typeSize, int groupElementSize, int sizeUpperBound) {
        List<Double> resultList = new ArrayList<>();
        Double[] doubleTypeList = RandomUtil.getRandomDoubleArray(lowerBound, upperBound, typeSize);
        int currentSize = 0;
        for (Double value : doubleTypeList) {
            for (int i = 0; i < groupElementSize; i++) {
                resultList.add(value);
                ++currentSize;
                if (currentSize >= sizeUpperBound) {
                    return resultList;
                }
            }
        }
        return resultList;
    }
    public static List<Integer> generateDoubleList(Integer lowerBound, Integer upperBound, int typeSize, int groupElementSize) {
        List<Integer> resultList = new ArrayList<>();
        Integer step = (upperBound - lowerBound) / (typeSize - 1);
        if (step <= 0) {
            for (int i = 0; i < groupElementSize; i++) {
                resultList.add(lowerBound);
            }
            return resultList;
        }
        for (int value = lowerBound; value <= upperBound; value+=step) {
            for (int i = 0; i < groupElementSize; i++) {
                resultList.add(value);
            }
        }
        return resultList;
    }
    public static List<Integer> generateRandomIntegerList(Integer lowerBound, Integer upperBound, int typeSize, int groupElementSize, int sizeUpperBound) {
        List<Integer> resultList = new ArrayList<>();
        Integer[] doubleTypeList = RandomUtil.getRandomIntegerArray(lowerBound, upperBound, typeSize);
        int currentSize = 0;
        for (Integer value : doubleTypeList) {
            for (int i = 0; i < groupElementSize; i++) {
                resultList.add(value);
                ++currentSize;
                if (currentSize >= sizeUpperBound) {
                    return resultList;
                }
            }
        }
        return resultList;
    }

}
