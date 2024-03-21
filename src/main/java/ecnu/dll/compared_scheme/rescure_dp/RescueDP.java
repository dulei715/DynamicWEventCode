package ecnu.dll.compared_scheme.rescure_dp;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.basic.MatrixArray;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import cn.edu.dll.map.MapUtils;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.KalmanFiltering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class RescueDP {

    /**
     * 行表示region，列表示时间
     */
    protected double[][] regionValueMatrix;
    protected double[][] regionStatisticMatrix;
    protected boolean[][] isSampledMatrix;

    protected double[][] noiseValueMatrix;

    public double[][] getRegionValueMatrix() {
        return regionValueMatrix;
    }

    public void setRegionValueMatrix(double[][] regionValueMatrix) {
        this.regionValueMatrix = regionValueMatrix;
    }

    public double[][] getRegionStatisticMatrix() {
        return regionStatisticMatrix;
    }

    public void setRegionStatisticMatrix(double[][] regionStatisticMatrix) {
        this.regionStatisticMatrix = regionStatisticMatrix;
    }

    public boolean[][] getIsSampledMatrix() {
        return isSampledMatrix;
    }

    public void setIsSampledMatrix(boolean[][] isSampledMatrix) {
        this.isSampledMatrix = isSampledMatrix;
    }

    public static int adaptiveSampling(double kP, double kI, double kD, double[] eArray, double kNow, double kBefore, int intervalBefore, int thetaScale, double lambda) {
        double eSum = BasicCalculation.getSum(eArray);
        double delta = kP * eArray[eArray.length-1] + kI * eSum / eArray.length + kD * eArray[eArray.length-1] / (kNow - kBefore);
        return (int)Math.floor(Math.max(1, intervalBefore + thetaScale * (1 - Math.pow(delta / lambda, 2))));
    }

    public static double adaptiveBudgetAllocation(double epsilon, int interval, double[] beforeWMinusOneEpsilonArray, double maxEpsilon, double phiScaleFactor, double maxP) {
        double beforeWTotalBudget = BasicCalculation.getSum(beforeWMinusOneEpsilonArray);
        double remainEpsilon = epsilon - beforeWTotalBudget;
        double portionP = Math.min(phiScaleFactor * Math.log1p(interval), maxP);
        return Math.min(portionP * remainEpsilon, maxEpsilon);
    }

    protected double[][] getSampleHistoryMatrix(int currentTime, int sampleWindowSize) {
        double[][] result = new double[this.regionValueMatrix.length][sampleWindowSize];
        int tempTime, resultTime;
        for (int i = 0; i < this.regionValueMatrix.length; i++) {
            for (tempTime = currentTime - 1, resultTime = sampleWindowSize - 1; tempTime >= 0 && resultTime >= 0; tempTime--) {
                if (this.isSampledMatrix[i][tempTime]) {
                    result[i][resultTime] = this.regionValueMatrix[i][tempTime];
                    -- resultTime;
                }
            }
            if (resultTime >= 0) {
                throw new RuntimeException("The historical sample region size is less than sample window size! " +
                        "("+(sampleWindowSize-resultTime-1)+" < "+sampleWindowSize+")");
            }
        }
        return result;
    }

    protected List<Integer> getIncreaseRegionIndexByValue(Double[] statisticValueArray, List<Integer> regionIndex) {
        List<Integer> result = new ArrayList<>();
        TreeMap<Double, List<Integer>> orderMap = MapUtils.getSortResult(statisticValueArray, regionIndex);
        for (List<Integer> tempList : orderMap.values()) {
            result.addAll(tempList);
        }
        return result;
    }

    public List<List<Integer>> dynamicGrouping(int currentTime, int sampleWindowSize, double tao1, double tao2, double tao3) {
        List<List<Integer>> group = new ArrayList<>();

        // step 1
        double[][] sampleHistoryMatrix = getSampleHistoryMatrix(currentTime, sampleWindowSize);
        Double[] regionStatisticsArray = MatrixArray.getAverageOfEachRow(sampleHistoryMatrix);

        // step 2
        List<Integer> tempList, remainIndexList = new ArrayList<>();
        for (int i = 0; i < regionStatisticsArray.length; i++) {
            if (regionStatisticsArray[i] > tao1) {
                tempList = new ArrayList<>();
                tempList.add(i);
                group.add(tempList);
            } else {
                remainIndexList.add(i);
            }
        }

        // step 3
        Integer firstRemainIndex, tempIndex;
        List<Integer> increaseRemainRegionIndex = getIncreaseRegionIndexByValue(regionStatisticsArray, remainIndexList);


        double tempGroupStatisticSum, tempSimilarity;
        Iterator<Integer> tempIterator;
//        int
        while (!increaseRemainRegionIndex.isEmpty()) {
            tempList = new ArrayList<>();
            firstRemainIndex = increaseRemainRegionIndex.remove(0);
            tempList.add(firstRemainIndex);
            tempGroupStatisticSum = regionStatisticsArray[firstRemainIndex];
            tempIterator = increaseRemainRegionIndex.iterator();
            while (tempIterator.hasNext()) {
                tempIndex = tempIterator.next();
                if (regionStatisticsArray[tempIndex] - regionStatisticsArray[firstRemainIndex] < tao3 && tempGroupStatisticSum < tao1) {
                    // step 6
                    tempSimilarity = StatisticTool.getPearsonCorrelationCoefficient(sampleHistoryMatrix[firstRemainIndex], sampleHistoryMatrix[tempIndex]);
                    if (tempSimilarity > tao2) {
                        tempIterator.remove();
                        tempList.add(tempIndex);
                        tempGroupStatisticSum += regionStatisticsArray[tempIndex];
                    }
                } else {
                    // step 7: part_1 (之前已经 remove fi_1 了)
                    break;
                }
            }
            // step 7: part_2 (为了解决末尾并组问题，将这步放在了while循环外)
            group.add(tempList);
        }

        return group;

    }

    public double[] perturbation(int time, List<List<Integer>> group, List<List<Double>> groupEpsilonList) {
//        double minimalEpsilon = BasicArrayUtil.getMinimalValue(elementEpsilonArray);
        double totalRealValue, tempMinimalEpsilon;
        double[] noiseValueArray = new double[group.size()];
        List<Integer> tempGroupElement;
        List<Double> tempGroupEpsilon;
        for (int i = 0; i < group.size(); i++) {
            tempGroupElement = group.get(i);
            tempGroupEpsilon = groupEpsilonList.get(i);
            totalRealValue = 0;
            for (Integer index : tempGroupElement) {
                totalRealValue += this.regionValueMatrix[index][time];
            }
            tempMinimalEpsilon = ListUtils.getMinimalValue(tempGroupEpsilon);

            totalRealValue += LaplaceUtils.getLaplaceNoise(1, tempMinimalEpsilon);
            noiseValueArray[i] = totalRealValue;
        }
        return noiseValueArray;
    }

    public void filtering() {
        KalmanFiltering.estimate();
    }




}
