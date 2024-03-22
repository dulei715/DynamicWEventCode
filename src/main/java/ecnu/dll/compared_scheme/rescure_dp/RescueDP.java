package ecnu.dll.compared_scheme.rescure_dp;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.basic.MatrixArray;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import cn.edu.dll.struct.pair.BasicPair;
import com.google.common.collect.Lists;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.KalmanFiltering;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.RescueDPUtils;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.TimeValue;
import ecnu.dll.config.Constant;

import java.util.*;

public class RescueDP {
    protected Double privacyBudget;

    /**
     * 行表示region，列表示时间
     */
    protected Double[][] regionStatisticMatrix;
    protected Boolean[][] isSampledMatrix;

    protected Double[][] noiseStatisticMatrix;
    //记录每个sampling个体的上次sample间隔(time->lastInterval)
    protected Map<Integer, Integer>[] samplingRecord;

    protected Double tao1, tao2, tao3;
    protected Integer previousSampleSize;
    protected Integer sampleWindowSize;

//    private int[]

    @Deprecated
    public RescueDP() {
        this.tao1 = Constant.TAO_1;
        this.tao2 = Constant.TAO_2;
        this.tao3 = Constant.TAO_3;
        this.sampleWindowSize = Constant.SAMPLE_WINDOW_SIZE;
    }

    public RescueDP(Integer regionSize, Integer timeUpperBound, Double privacyBudget) {
        this.privacyBudget = privacyBudget;
        this.regionStatisticMatrix = new Double[regionSize][timeUpperBound];
        this.isSampledMatrix = new Boolean[regionSize][timeUpperBound];
        this.samplingRecord = new Map[regionSize];
        this.noiseStatisticMatrix = new Double[regionSize][timeUpperBound];
        initialize();
    }

    private void initialize() {
        this.tao1 = Constant.TAO_1;
        this.tao2 = Constant.TAO_2;
        this.tao3 = Constant.TAO_3;
        this.sampleWindowSize = Constant.SAMPLE_WINDOW_SIZE;
        // 假设0时刻和1时刻每个region都被sampling（不然算个der呀！）
        MatrixArray.setValue(this.isSampledMatrix, -1, 0, true);
        MatrixArray.setValue(this.isSampledMatrix, -1, 1, true);
        // 设置每个region的0时刻的前一个sample区间为0以及1时刻的前一个sample区间为1
        RescueDPUtils.initializeAndAddFirstItemForAllMap(this.samplingRecord, 0, 0);
        RescueDPUtils.initializeAndAddFirstItemForAllMap(this.samplingRecord, 1, 1);

    }

    public Double[][] getRegionStatisticMatrix() {
        return regionStatisticMatrix;
    }

    public void setRegionStatisticMatrix(Double[][] regionStatisticMatrix) {
        this.regionStatisticMatrix = regionStatisticMatrix;
    }

    public Boolean[][] getIsSampledMatrix() {
        return isSampledMatrix;
    }

    public void setIsSampledMatrix(Boolean[][] isSampledMatrix) {
        this.isSampledMatrix = isSampledMatrix;
    }


    public List<Integer> adaptiveSampling(Integer currentTime, List<Integer> sampleRegionIndexList, Double nextTimeRemainBudget) {
        List<Integer> result = new ArrayList<>();
        if (currentTime.equals(0)) {
            ListUtils.addValue(result, 1, sampleRegionIndexList.size());
            RescueDPUtils.setNextTimeValueAndStatus(this.isSampledMatrix, this.samplingRecord, sampleRegionIndexList, currentTime);
            return result;
        }
        TreeMap<Integer, TimeValue[]> noiseHistoryMatrix = RescueDPUtils.getSampleHistoryMatrix(this.noiseStatisticMatrix, this.isSampledMatrix, currentTime, sampleRegionIndexList, Constant.PID_PI - 1);
        Integer tempIndex, tempInterval;
        Double[] eArray;
        TimeValue[] tempTimeValueArray;
        List<Double> eArrayList;
        Integer kBefore, intervalBefore;
        Double element;
        for (Map.Entry<Integer, TimeValue[]> historyItem : noiseHistoryMatrix.entrySet()) {
            tempIndex = historyItem.getKey();
            tempTimeValueArray = historyItem.getValue();
            // 这里假设history里面包含sample项
            kBefore = tempTimeValueArray[tempTimeValueArray.length-1].getTimePoint();
            eArrayList = new ArrayList<>();
            for (TimeValue tValue : tempTimeValueArray) {
                element = tValue.getStatisticValue();
                if (element >= 0) {
                    eArrayList.add(element);
                }
            }
            eArrayList.add(this.noiseStatisticMatrix[tempIndex][currentTime]);
            eArray = eArrayList.toArray(new Double[0]);
            intervalBefore = this.samplingRecord[tempIndex].get(currentTime);
            tempInterval = RescueDPUtils.getNewSampleInterval(Constant.KP, Constant.KI, Constant.KD, eArray, currentTime, kBefore, intervalBefore, Constant.THETA_SCALE, 1/nextTimeRemainBudget);
            result.add(tempInterval);
            RescueDPUtils.setNextValueAndStatus(this.noiseStatisticMatrix, this.isSampledMatrix, this.samplingRecord, tempIndex, currentTime, this.noiseStatisticMatrix[tempIndex][currentTime], tempInterval);
        }
        return result;
    }

    public List<Integer> getSamplingRegionIndexList(Integer currentTime) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < this.isSampledMatrix.length; i++) {
            if (this.isSampledMatrix[i][currentTime]) {
                result.add(i);
            }
        }
        return result;
    }

    public Double adaptiveBudgetAllocation(Integer interval, Double[] beforeWMinusOneEpsilonArray, Double maxEpsilon, Double phiScaleFactor, Double maxP) {
        Double beforeWTotalBudget = BasicCalculation.getSum(beforeWMinusOneEpsilonArray);
        Double remainEpsilon = this.privacyBudget - beforeWTotalBudget;
        Double portionP = Math.min(phiScaleFactor * Math.log1p(interval), maxP);
        return Math.min(portionP * remainEpsilon, maxEpsilon);
    }



    public List<List<Integer>> dynamicGrouping(Integer currentTime, List<Integer> sampleRegionIndexList) {
        List<List<Integer>> groupList = new ArrayList<>();
        List<Integer> tempGroup;
        Integer tempRegionIndex;
        Double tempAverageRegionStatistic;

        // 处理 currentTime为0的情况
        if (currentTime.equals(0)) {
            for (Integer index : sampleRegionIndexList) {
                tempGroup = new ArrayList<>();
                tempGroup.add(index);
                groupList.add(tempGroup);
            }
            return groupList;
        }

        // step 1
        TreeMap<Integer, TimeValue[]> sampleHistoryTree = RescueDPUtils.getSampleHistoryMatrix(this.regionStatisticMatrix, this.isSampledMatrix, currentTime, sampleRegionIndexList, this.sampleWindowSize);
        TreeMap<Integer, Double> regionAverageStatisticsMap = RescueDPUtils.getNonNegativeAverageOfSampleRegion(sampleHistoryTree);

        // step 2
        List<Integer> remainIndexList = new ArrayList<>();
        for (Map.Entry<Integer, Double> regionItem : regionAverageStatisticsMap.entrySet()) {
            tempRegionIndex = regionItem.getKey();
            tempAverageRegionStatistic = regionItem.getValue();
            if (tempAverageRegionStatistic > this.tao1) {
                tempGroup = new ArrayList<>();
                tempGroup.add(tempRegionIndex);
                groupList.add(tempGroup);
            } else {
                remainIndexList.add(tempRegionIndex);
            }
        }

        // step 3
        Integer firstRemainIndex;
        double tempGroupStatisticSum = 0, tempSimilarity;
        Iterator<Integer> tempIterator;
        Double firstAverageStatistic;

        List<Integer> increaseRemainRegionIndex = RescueDPUtils.getIncreaseRegionIndexByValue(regionAverageStatisticsMap, remainIndexList);

        while (!increaseRemainRegionIndex.isEmpty()) {
            tempGroup = new ArrayList<>();
            firstRemainIndex = increaseRemainRegionIndex.remove(0);
            tempGroup.add(firstRemainIndex);
            firstAverageStatistic = regionAverageStatisticsMap.get(firstRemainIndex);
            tempGroupStatisticSum += firstAverageStatistic;
            tempIterator = increaseRemainRegionIndex.iterator();
            while (tempIterator.hasNext()) {
                tempRegionIndex = tempIterator.next();
                tempAverageRegionStatistic = regionAverageStatisticsMap.get(tempRegionIndex);
                if (tempAverageRegionStatistic - firstAverageStatistic < this.tao3 && tempGroupStatisticSum < this.tao1) {
                    // step 6
                    tempSimilarity = RescueDPUtils.getNonNegativePearsonCorrelationCoefficient(sampleHistoryTree.get(firstRemainIndex), sampleHistoryTree.get(tempRegionIndex));
                    if (tempSimilarity > this.tao2) {
                        tempIterator.remove();
                        tempGroup.add(tempRegionIndex);
                        tempGroupStatisticSum += tempAverageRegionStatistic;
                    }
                } else {
                    // step 7: part_1 (之前已经 remove fi_1 了)
                    break;
                }
            }
            // step 7: part_2 (为了解决末尾并组问题，将这步放在了while循环外)
            groupList.add(tempGroup);
        }

        return groupList;

    }

    public Double[] perturbation(Integer time, List<List<Integer>> group, List<List<Double>> groupEpsilonList) {
//        double minimalEpsilon = BasicArrayUtil.getMinimalValue(elementEpsilonArray);
        Double totalRealValue, tempMinimalEpsilon;
        Double[] noiseValueArray = new Double[group.size()];
        List<Integer> tempGroupElement;
        List<Double> tempGroupEpsilon;
        for (int i = 0; i < group.size(); i++) {
            tempGroupElement = group.get(i);
            tempGroupEpsilon = groupEpsilonList.get(i);
            totalRealValue = 0D;
            for (Integer index : tempGroupElement) {
                totalRealValue += this.regionStatisticMatrix[index][time];
            }
            tempMinimalEpsilon = ListUtils.getMinimalValue(tempGroupEpsilon);

            totalRealValue += LaplaceUtils.getLaplaceNoise(1, tempMinimalEpsilon);
            noiseValueArray[i] = totalRealValue;
        }
        return noiseValueArray;
    }

    public Double[] filtering(Double rPri, Double zNow, Double pBefore, Double noiseQ, Double noiseR) {
        return KalmanFiltering.estimate(rPri, zNow, pBefore, noiseQ, noiseR);
    }

    public Double[] privateRelease(Integer currentTime, Double[] realStatisticNow) {
        MatrixArray.setColValueAsGivenVector(this.regionStatisticMatrix, currentTime, realStatisticNow);
        List<Integer> sampleRegionIndexList = getSamplingRegionIndexList(currentTime);
        List<List<Integer>> groupingList = dynamicGrouping(currentTime, sampleRegionIndexList);
        adaptiveBudgetAllocation();
        return null;
    }


    public static void main(String[] args) {

    }



}
