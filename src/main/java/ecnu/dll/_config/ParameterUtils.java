package ecnu.dll._config;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.MatrixArray;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.collection.SetUtils;
import cn.edu.dll.struct.pair.IdentityPurePair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static List<Double> generateDoubleList(List<Double> doubleTypeList, Integer totalSize, Double... ratios) {
        Integer typeSize = doubleTypeList.size();
        Integer ratioSize = ratios.length;
        Double tempDouble, tempRatio;
        Integer tempSize;
        List<Double> resultList = new ArrayList<>();
        if (typeSize > 1) {
            Double ratioSum = BasicArrayUtil.getSum(ratios);
            if (ratioSize != typeSize - 1 || ratioSum > 1) {
                throw new RuntimeException("The ratios are invalid!");
            }
            for (int i = 0; i < ratios.length; i++) {
                tempDouble = doubleTypeList.get(i);
                tempSize = (int) (Math.round(totalSize * ratios[i]));
                for (int j = 0; j < tempSize; j++) {
                    resultList.add(tempDouble);
                }
            }
            tempRatio = 1.0 - ratioSum;
        } else {
            tempRatio = 1.0;
        }
        tempDouble = doubleTypeList.get(doubleTypeList.size()-1);
        tempSize = (int) (Math.round(totalSize * tempRatio));
        for (int j = 0; j < tempSize; j++) {
            resultList.add(tempDouble);
        }
        return resultList;
    }

    public static <T> List<T> generateRandomList(IdentityPurePair<T> doublePair, Integer totalSize, Double ratio) {
        T firstElement, secondElement;
        Integer tempSize;
        List<T> resultList = new ArrayList<>();
        if (ratio < 0 || ratio > 1) {
            throw new RuntimeException("The ratios are invalid!");
        }
        firstElement = doublePair.getKey();
        secondElement = doublePair.getValue();
        tempSize = (int) (Math.round(totalSize * ratio));
        List<Integer> firstElementIndexList = RandomUtil.getRandomIntegerArrayWithoutRepeat(0, totalSize - 1, tempSize);
        Set<Integer> firstElementIndexSet = new HashSet<>(firstElementIndexList);
        for (int i = 0; i < totalSize; i++) {
            if (firstElementIndexSet.contains(i)) {
                resultList.add(firstElement);
            } else {
                resultList.add(secondElement);
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

    public static List<Integer> generateIntegerList(List<Integer> integerList, Integer totalSize, Double... ratios) {
        Integer typeSize = integerList.size();
        Integer ratioSize = ratios.length;
        Integer tempInteger, tempSize;
        Double tempRatio;
        List<Integer> resultList = new ArrayList<>();
        if (typeSize > 1) {
            Double ratioSum = BasicArrayUtil.getSum(ratios);
            if (ratioSize != typeSize - 1 || ratioSum > 1) {
                throw new RuntimeException("The ratios are invalid!");
            }
            for (int i = 0; i < ratios.length; i++) {
                tempInteger = integerList.get(i);
                tempSize = (int) (Math.round(totalSize * ratios[i]));
                for (int j = 0; j < tempSize; j++) {
                    resultList.add(tempInteger);
                }
            }
            tempRatio = 1.0 - ratioSum;
        } else {
            tempRatio = 1.0;
        }
        tempInteger = integerList.get(integerList.size()-1);
        tempSize = (int) (Math.round(totalSize * tempRatio));
        for (int j = 0; j < tempSize; j++) {
            resultList.add(tempInteger);
        }
        return resultList;
    }




    /**
     * for forward and backward privacy
     */

    // 这里doubleListLowerBound是按照typeSize和sizeUpperBound分好的组（每组中，元素对应的doubleListLowerBound相等）
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

    /*
     * 这里 doubleTypeListForOneItem 代表一个item里出现的 double的种类数，每个item完全一样
     */
    public static List<List<Double>> generateBudgetListListWithIdentityUsersAndTwoTypeBudgetsInRandomTimestamps(IdentityPurePair<Double> doublePair, int itemSize, int resultSize, Double ratio) {
        if (ratio < 0 || ratio > 1) {
            throw new RuntimeException("Invalid ratio!");
        }
        List<List<Double>> resultList = new ArrayList<>();
        List<Double> itemDoubleList = generateRandomList(doublePair, resultSize, ratio);
        for (int i = 0; i < itemSize; i++) {
            resultList.add(itemDoubleList);
        }
        return MatrixArray.getTransposition(resultList);
    }
    public static List<List<Integer>> generateWindowSizeListListWithIdentityUsersAndTwoTypeWindowSizeInRandomTimestamps(IdentityPurePair<Integer> integerPair, int itemSize, int resultSize, Double ratio) {
        if (ratio < 0 || ratio > 1) {
            throw new RuntimeException("Invalid ratio!");
        }
        List<List<Integer>> resultList = new ArrayList<>();
        List<Integer> itemIntegerList = generateRandomList(integerPair, resultSize, ratio);
        for (int i = 0; i < itemSize; i++) {
            resultList.add(itemIntegerList);
        }
        return MatrixArray.getTransposition(resultList);
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
