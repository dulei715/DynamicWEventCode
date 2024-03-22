package ecnu.dll.compared_scheme.rescure_dp.basic_component;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.map.MapUtils;

import java.util.*;

public class RescueDPUtils {
    public static TreeMap<Integer, Double> getNonNegativeAverageOfSampleRegion(TreeMap<Integer, Double[]> sampleRegionMap) {
        double tempSum;
        int tempSize;
        Integer tempRegionIndex;
        Double[] data;
        TreeMap<Integer, Double> result = new TreeMap<>();
        for (Map.Entry<Integer, Double[]> regionItem : sampleRegionMap.entrySet()) {
            tempRegionIndex = regionItem.getKey();
            data = regionItem.getValue();
            tempSum = 0;
            tempSize = 0;
            for (int j = 0; j < data.length; j++) {
                if (data[j] >= 0) {
                    tempSum += data[j];
                    ++tempSize;
                }
            }
            result.put(tempRegionIndex, tempSum / tempSize);
        }
        return result;
    }

    public static double getNonNegativePearsonCorrelationCoefficient(Double[] originalDataA, Double[] originalDataB) {
        int dataAFirstNonNegativeIndex = 0, dataBFirstNonNegativeIndex = 0;
//            List<Double> dataAReverseList = new ArrayList<>(), dataBReverseList = new ArrayList<>();
        for (; originalDataA[dataAFirstNonNegativeIndex] < 0; dataAFirstNonNegativeIndex++);
        for (; originalDataB[dataBFirstNonNegativeIndex] < 0; dataBFirstNonNegativeIndex++);
        int minimalSize = Math.min(originalDataA.length - dataAFirstNonNegativeIndex, originalDataB.length - dataBFirstNonNegativeIndex);
        double[] dataA = new double[minimalSize], dataB = new double[minimalSize];
        int k = minimalSize;
        for (int dataAIndex = originalDataA.length - 1, dataBIndex = originalDataB.length - 1; dataAIndex >= dataAFirstNonNegativeIndex && dataBIndex >= dataBFirstNonNegativeIndex; dataAIndex--, dataBIndex--) {
            -- k;
            dataA[k] = originalDataA[dataAIndex];
            dataB[k] = originalDataB[dataBIndex];
        }
        double averageA = BasicCalculation.getAverage(dataA);
        double averageB = BasicCalculation.getAverage(dataB);
        double[] differA = BasicArrayUtil.getLinearTransform(dataA, 1, -averageA);
        double[] differB = BasicArrayUtil.getLinearTransform(dataB, 1, -averageB);

        double squareSumA = BasicCalculation.getSquareValue(differA);
        double squareSumB = BasicCalculation.getSquareValue(differB);
        double differProduct = BasicCalculation.getInnerProduct(differA, differB);
        double covAB = differProduct / dataA.length;
        double varA = squareSumA / dataA.length;
        double varB = squareSumB / dataB.length;
        return covAB / Math.sqrt(varA * varB);
    }

    public static List<Integer> getIncreaseRegionIndexByValue(TreeMap<Integer, Double> statisticValueArray, List<Integer> regionIndex) {
        List<Integer> result = new ArrayList<>();
        TreeMap<Double, List<Integer>> orderMap = MapUtils.getSortResult(statisticValueArray, regionIndex);
        for (List<Integer> tempList : orderMap.values()) {
            result.addAll(tempList);
        }
        return result;
    }

    public static Integer getNewSampleInterval(Double kP, Double kI, Double kD, Double[] eArray, Integer kNow, Integer kBefore, Integer intervalBefore, Integer thetaScale, Double lambda) {
        Double eSum = BasicCalculation.getSum(eArray);
        Double delta = kP * eArray[eArray.length-1] + kI * eSum / eArray.length + kD * eArray[eArray.length-1] / (kNow - kBefore);
        return (int)Math.floor(Math.max(1, intervalBefore + thetaScale * (1 - Math.pow(delta / lambda, 2))));
    }

    public static <K,V> void initializeAndAddFirstItemForAllMap(Map<K,V>[] mapArray, K key, V value) {
        for (int i = 0; i < mapArray.length; i++) {
            mapArray[i] = new HashMap<>();
            mapArray[i].put(key, value);
        }
    }

    public static TreeMap<Integer, TimeValue[]> getSampleHistoryMatrix(Double[][] regionStatisticMatrix, Boolean[][] isSampledMatrix, Integer currentTime, List<Integer> sampleRegionIndexList, Integer sampleWindowSize) {
//        Double[][] result = new Double[this.regionStatisticMatrix.length][sampleWindowSize];
        TreeMap<Integer, TimeValue[]> result = new TreeMap<>();
        int tempTime, resultTime;
        TimeValue[] tempWindowHistory;
        for (Integer i : sampleRegionIndexList) {
            tempWindowHistory = new TimeValue[sampleWindowSize];
            for (tempTime = currentTime - 1, resultTime = sampleWindowSize - 1; tempTime >= 0 && resultTime >= 0; tempTime--) {
                if (isSampledMatrix[i][tempTime]) {
                    tempWindowHistory[resultTime] = new TimeValue(tempTime, regionStatisticMatrix[i][tempTime]);
                    -- resultTime;
                }
            }
//            if (resultTime >= 0) {
//                throw new RuntimeException("The historical sample region size is less than sample window size! " +
//                        "("+(sampleWindowSize-resultTime-1)+" < "+sampleWindowSize+")");
//            }
            while (resultTime >= 0) {
                tempWindowHistory[resultTime] = new TimeValue(-1, -1D);
                -- resultTime;
            }
            result.put(i, tempWindowHistory);
        }
        return result;
    }

    public static void setNextValueAndStatus(Double[][] noiseRegionStatisticMatrix, Boolean[][] isSampleMatrix, Map<Integer, Integer>[] sampleRecord, Integer currentRegionIndex, Integer currentTime, Double currentNoise, Integer newInterval) {
        int tempTime = 1;
        for (; tempTime < newInterval; tempTime++) {
            noiseRegionStatisticMatrix[currentRegionIndex][currentTime + tempTime] = currentNoise;
            isSampleMatrix[currentRegionIndex][currentTime + tempTime] = false;
        }
        isSampleMatrix[currentRegionIndex][currentTime + tempTime] = true;
        sampleRecord[currentRegionIndex].put(currentTime + tempTime, newInterval);
    }

    public static void setNextTimeValueAndStatus(Boolean[][] isSampleMatrix, Map<Integer, Integer>[] sampleRecord, List<Integer> currentSampleRegionIndexList, Integer currentTime) {
        for (Integer index : currentSampleRegionIndexList) {
            isSampleMatrix[index][currentTime + 1] = true;
            sampleRecord[index].put(currentTime + 1, 1);

        }
    }

}
