package ecnu.dll._config;

import cn.edu.dll.basic.MatrixArray;
import cn.edu.dll.basic.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class ParameterUtils {
    public static List<Double> generateDoubleList(Double lowerBound, Double upperBound, int typeSize, int groupElementSize, int sizeUpperBound) {
        List<Double> resultList = new ArrayList<>();
        Double step = (upperBound - lowerBound) / (typeSize - 1);
        if (step <= 0) {
            for (int i = 0; i < groupElementSize; i++) {
                resultList.add(lowerBound);
            }
            return resultList;
        }
        int currentSize = 0;
        for (double value = lowerBound; value <= upperBound; value+=step) {
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
    public static List<Integer> generateDoubleList(Integer lowerBound, Integer upperBound, int typeSize, int groupElementSize, int sizeUpperBound) {
        List<Integer> resultList = new ArrayList<>();
        Integer step = (upperBound - lowerBound) / (typeSize - 1);
        if (step <= 0) {
            for (int i = 0; i < groupElementSize; i++) {
                resultList.add(lowerBound);
            }
            return resultList;
        }
        int currentSize = 0;
        for (int value = lowerBound; value <= upperBound; value+=step) {
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




    /**
     * for forward and backward privacy
     */

    //这里doubleListLowerBound是按照typeSize和sizeUpperBound分好的组（每组中，元素对应的doubleListLowerBound相等）
    /*
     * 返回的结果外层List代表timestamp，内层list代表user
     */
    public static List<List<Double>> generateRandomDoubleList(List<Double> doubleListLowerBound, Double upperBound, int elementSizeInAGroup, int resultSize) {
        List<List<Double>> result = new ArrayList<>(resultSize);
        List<Double> tempList;
        int rowSize = doubleListLowerBound.size();
        int i = 0;
        Double tempLowerBound;
        while (i < rowSize) {
            tempLowerBound = doubleListLowerBound.get(i);
            tempList = generateRandomDoubleList(tempLowerBound, upperBound, 1, resultSize, resultSize);
            for (int j = 0; j < elementSizeInAGroup && i < rowSize; ++j, ++i) {
                result.add(tempList);
            }
        }
        return MatrixArray.getTransposition(result);
    }
    public static List<List<Integer>> generateRandomIntegerList(Integer lowerBound, List<Integer> integerListUpperBound, int elementSizeInAGroup, int resultSize) {
        List<List<Integer>> result = new ArrayList<>(resultSize);
        List<Integer> tempList;
        int rowSize = integerListUpperBound.size();
        int i = 0;
        Integer tempUpperBound;
        while (i < rowSize) {
            tempUpperBound = integerListUpperBound.get(i);
            tempList = generateRandomIntegerList(lowerBound, tempUpperBound, 1, resultSize, resultSize);
            for (int j = 0; j < elementSizeInAGroup && i < rowSize; ++j, ++i) {
                result.add(tempList);
            }
        }
        return MatrixArray.getTransposition(result);
    }

    public static List<List<Double>> generateRandomDoubleListWithDifferBound(List<Double> doubleListLowerBound, Double differLowerBound, Double differUpperBound, int elementSizeInAGroup, int resultSize) {
        List<List<Double>> result = new ArrayList<>(resultSize);
        List<Double> tempList;
        int rowSize = doubleListLowerBound.size();
        int i = 0;
        Double tempLowerBound;
        while (i < rowSize) {
            tempLowerBound = doubleListLowerBound.get(i);
            tempList = generateRandomDoubleList(tempLowerBound + differLowerBound, tempLowerBound + differUpperBound, 1, resultSize, resultSize);
            for (int j = 0; j < elementSizeInAGroup && i < rowSize; ++j, ++i) {
                result.add(tempList);
            }
        }
        return MatrixArray.getTransposition(result);
    }

    public static List<List<Double>> generateRandomDifferenceDoubleList(Double differenceLowerBound, Double differneceUpperBound, int typeSize, int groupElementSize, int sizeUpperBound, int resultSize) {
        List<List<Double>> result = new ArrayList<>();
        List<Double> tempList;
        for (int i = 0; i < resultSize; i++) {
            tempList = generateRandomDoubleList(differenceLowerBound, differneceUpperBound, typeSize, groupElementSize, sizeUpperBound);
            result.add(tempList);
        }
        return result;
    }



}
