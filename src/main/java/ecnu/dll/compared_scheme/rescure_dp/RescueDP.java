package ecnu.dll.compared_scheme.rescure_dp;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.MatrixArray;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.KalmanFiltering;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.RescueDPUtils;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.TimeValue;
import ecnu.dll.config.Constant;

import java.util.*;

public class RescueDP {
    protected Integer regionSize;
    protected Integer timeUpperBound;
    protected Double privacyBudget;
    protected Double[][] timePrivacyBudget;

    /**
     * 行表示region，列表示时间
     */
    protected Double[][] regionStatisticMatrix;
    protected Boolean[][] isSampledMatrix;

    protected Double[][] noiseStatisticMatrix;
    protected Double[][] estimateStatisticMatrix;
    // 记录每个region的PValue，每调用一次Filter要更新一次
    protected Double[] pArray;
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
        this.regionSize = regionSize;
        this.timeUpperBound = timeUpperBound;
        this.privacyBudget = privacyBudget;
        this.regionStatisticMatrix = new Double[regionSize][timeUpperBound];
        this.isSampledMatrix = new Boolean[regionSize][timeUpperBound];
        this.samplingRecord = new Map[regionSize];
        this.noiseStatisticMatrix = new Double[regionSize][timeUpperBound];
        this.estimateStatisticMatrix = new Double[regionSize][timeUpperBound];
        this.pArray = new Double[regionSize];
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
        BasicArrayUtil.setDoubleArrayToZero(this.pArray);

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


    public List<Integer> adaptiveSampling(Integer currentTime, List<Integer> sampleRegionIndexList, List<Double> nextTimeRemainBudgetList) {
        List<Integer> result = new ArrayList<>();
        if (currentTime.equals(0)) {
            ListUtils.addValue(result, 1, sampleRegionIndexList.size());
            RescueDPUtils.setNextTimeValueAndStatus(this.isSampledMatrix, this.samplingRecord, sampleRegionIndexList, currentTime);
            return result;
        }
        TreeMap<Integer, TimeValue[]> noiseHistoryMatrix = RescueDPUtils.getSampleHistoryMatrix(this.estimateStatisticMatrix, this.isSampledMatrix, currentTime, sampleRegionIndexList, Constant.PID_PI - 1);
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
            eArrayList.add(this.estimateStatisticMatrix[tempIndex][currentTime]);
            eArray = eArrayList.toArray(new Double[0]);
            intervalBefore = this.samplingRecord[tempIndex].get(currentTime);
            tempInterval = RescueDPUtils.getNewSampleInterval(Constant.KP, Constant.KI, Constant.KD, eArray, currentTime, kBefore, intervalBefore, Constant.THETA_SCALE, 1/nextTimeRemainBudgetList.get(tempIndex));
            result.add(tempInterval);
            RescueDPUtils.setNextValueAndStatus(this.estimateStatisticMatrix, this.isSampledMatrix, this.timePrivacyBudget, this.samplingRecord, tempIndex, currentTime, this.estimateStatisticMatrix[tempIndex][currentTime], tempInterval);
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

    public void adaptiveBudgetAllocation(Integer currentTime, List<Integer> sampleRegionIndexList, List<Double> beforeTotaludgetList, List<Integer> intervalList) {
        Double beforeWTotalBudget, remainEpsilon, portionP, maxEpsilon, newRegionBudgetAllocation;
        Integer regionIndex;
        for (int i = 0; i < sampleRegionIndexList.size(); i++) {
            regionIndex = sampleRegionIndexList.get(i);
//            beforeWTotalBudget = RescueDPUtils.getSumOfHistoricalPrivacyBudget(this.timePrivacyBudget, regionIndex, currentTime, this.sampleWindowSize);
            beforeWTotalBudget = beforeTotaludgetList.get(i);
            remainEpsilon = this.privacyBudget - beforeWTotalBudget;
            portionP = Math.min(Constant.PHI_Scale * Math.log1p(intervalList.get(i)), Constant.P_MAX);
            // 这里规定epsilonMax是总epsilon的0.2
            maxEpsilon = this.privacyBudget * 0.2;
            newRegionBudgetAllocation = Math.min(portionP * remainEpsilon, maxEpsilon);
            this.timePrivacyBudget[regionIndex][currentTime] = newRegionBudgetAllocation;
        }
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

    public List<Double> perturbation(Integer currentTime, List<List<Integer>> groupList) {
//        double minimalEpsilon = BasicArrayUtil.getMinimalValue(elementEpsilonArray);
        List<List<Double>> groupEpsilonList = RescueDPUtils.getCurrentPrivacyBudgetAsGroup(this.timePrivacyBudget, currentTime, groupList);
        Double totalValue, tempMinimalEpsilon;
//        Double[] noiseValueArray = new Double[groupList.size()];
        List<Double> noiseValueList = new ArrayList<>();
        List<Integer> tempGroupElement;
        List<Double> tempGroupEpsilon;
        for (int i = 0; i < groupList.size(); i++) {
            tempGroupElement = groupList.get(i);
            tempGroupEpsilon = groupEpsilonList.get(i);
            totalValue = 0D;
            for (Integer index : tempGroupElement) {
                totalValue += this.regionStatisticMatrix[index][currentTime];
            }
            tempMinimalEpsilon = ListUtils.getMinimalValue(tempGroupEpsilon);

            totalValue += LaplaceUtils.getLaplaceNoise(1, tempMinimalEpsilon);
            noiseValueList.add(totalValue / tempGroupElement.size());
        }
        RescueDPUtils.setNoiseValue(this.noiseStatisticMatrix, currentTime, groupList, noiseValueList);
        return noiseValueList;
    }

    public Double filtering(Integer currentTime, List<Integer> sampleRegionIndexList) {
        Double rPri, zNow, pBefore;
        Double[] result = null;
        for (Integer index : sampleRegionIndexList) {
            if (currentTime <= 1) {
                rPri = 0D;
                pBefore = 0D;
            } else {
                rPri = this.estimateStatisticMatrix[index][currentTime - 1];
                pBefore = this.pArray[index];
            }
            zNow = this.noiseStatisticMatrix[index][currentTime];
            result = KalmanFiltering.estimate(rPri, zNow, pBefore, Constant.Q_VARIANCE, Constant.R_VARIANCE);
            this.estimateStatisticMatrix[index][currentTime] = result[0];
            this.pArray[index] = result[1];
        }
        return result[0];
    }

    public void privateRelease() {
        List<Integer> sampleIntervalList = null;
        List<Double> sumOfHistoricalPrivacyBudget, remainPrivacyBudget;
        List<Integer> sampleRegionIndexListNow, sampleRegionIndexListNext;

        sampleRegionIndexListNext = getSamplingRegionIndexList(0);
        remainPrivacyBudget = ListUtils.generateListWithFixedElement(this.privacyBudget, sampleRegionIndexListNext.size());
        sampleIntervalList = adaptiveSampling(0, sampleRegionIndexListNext, remainPrivacyBudget);
        for (int currentTime = 0; currentTime < this.timeUpperBound; currentTime++) {
//            MatrixArray.setColValueAsGivenVector(this.regionStatisticMatrix, currentTime);
            sampleRegionIndexListNow = sampleRegionIndexListNext;
            List<List<Integer>> groupingList = dynamicGrouping(currentTime, sampleRegionIndexListNow);

            sumOfHistoricalPrivacyBudget = RescueDPUtils.getSumOfHistoricalPrivacyBudget(this.timePrivacyBudget, sampleRegionIndexListNow, currentTime, this.sampleWindowSize);
            adaptiveBudgetAllocation(currentTime, sampleRegionIndexListNow, sumOfHistoricalPrivacyBudget, sampleIntervalList);
            perturbation(currentTime, groupingList);
            filtering(currentTime, sampleRegionIndexListNow);

//            sumOfHistoricalPrivacyBudget = RescueDPUtils.getSumOfHistoricalPrivacyBudget(this.timePrivacyBudget, sampleRegionIndexList, currentTime + 1, this.sampleWindowSize);
//            remainPrivacyBudget = RescueDPUtils.getRemainPrivacyBudget(sumOfHistoricalPrivacyBudget, this.privacyBudget);

            sampleRegionIndexListNext = getSamplingRegionIndexList(currentTime + 1);
            remainPrivacyBudget = RescueDPUtils.getRemainPrivacyBudget(this.timePrivacyBudget, sampleRegionIndexListNext, currentTime + 1, this.sampleWindowSize, this.privacyBudget);
            sampleIntervalList = adaptiveSampling(currentTime + 1, sampleRegionIndexListNext, remainPrivacyBudget);


        }

        System.out.println("Finish all event!");;
    }


    public static void main(String[] args) {

    }



}
