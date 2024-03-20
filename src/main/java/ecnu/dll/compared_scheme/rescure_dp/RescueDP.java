package ecnu.dll.compared_scheme.rescure_dp;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.basic.MatrixArray;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.map.MapUtils;
import edu.ecnu.dll.tools.collection.ArraysUtils;
import org.apache.commons.math3.linear.MatrixUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class RescueDP {

    /**
     * 行表示region，列表示时间
     */
    protected double[][] regionValueArray;
    protected double[][] regionStatisticArray;
    protected boolean[][] isSampledArray;

    public static int adaptiveSampling(double kP, double kI, double kD, double[] eArray, double kNow, double kBefore, int intervalBefore, int thetaScale, double lambda) {
        double eSum = BasicCalculation.getSum(eArray);
        double delta = kP * eArray[eArray.length-1] + kI * eSum / eArray.length + kD * eArray[eArray.length-1] / (kNow - kBefore);
        int intervalNow = (int)Math.floor(Math.max(1, intervalBefore + thetaScale * (1 - Math.pow(delta / lambda, 2))));
        return intervalNow;
    }

    public static double adaptiveBudgetAllocation(double epsilon, int interval, double[] beforeWMinusOneEpsilonArray, double maxEpsilon, double phiScaleFactor, double maxP) {
        double beforeWTotalBudget = BasicCalculation.getSum(beforeWMinusOneEpsilonArray);
        double remainEpsilon = epsilon - beforeWTotalBudget;
        double portionP = Math.min(phiScaleFactor * Math.log1p(interval), maxP);
        double epsilonNow = Math.min(portionP * remainEpsilon, maxEpsilon);
        return epsilonNow;
    }

    protected double[][] getSampleHistoryMatrix(int currentTime, int sampleWindowSize) {
        double[][] result = new double[this.regionStatisticArray.length][sampleWindowSize];
        int tempTime, resultTime;
        for (int i = 0; i < this.regionValueArray.length; i++) {
            for (tempTime = currentTime - 1, resultTime = sampleWindowSize - 1; tempTime >= 0 && resultTime >= 0; tempTime++) {
                if (this.isSampledArray[i][tempTime]) {
                    result[i][resultTime] = this.regionValueArray[i][tempTime];
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

    public void dynamicGrouping(int currentTime, int sampleWindowSize, double tao1, double tao2, double tao3) {
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
        List<Integer> increaseRemainRegionIndex = getIncreaseRegionIndexByValue(regionStatisticsArray, remainIndexList);

    }

    public void perturbation() {

    }

    public void filtering() {

    }




}
