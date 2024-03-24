package ecnu.dll.compared_scheme.rescure_dp;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.KalmanFiltering;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.RescueDPUtils;
import ecnu.dll.compared_scheme.rescure_dp.basic_component.TimeValue;
import ecnu.dll._config.Constant;

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
    // 记录当前sample点的时间间隔以及更新的时间间隔
    protected Integer[] newSampleIntervalArray;

    protected Double tao1, tao2, tao3;
    protected Integer sampleWindowSize;

//    private int[]

//    @Deprecated
//    public RescueDP() {
//        this.tao1 = Constant.TAO_1;
//        this.tao2 = Constant.TAO_2;
//        this.tao3 = Constant.TAO_3;
//        this.sampleWindowSize = Constant.SAMPLE_WINDOW_SIZE;
//    }

    public RescueDP(Integer regionSize, Integer timeUpperBound, Double privacyBudget) {
        this.regionSize = regionSize;
        this.timeUpperBound = timeUpperBound;
        this.privacyBudget = privacyBudget;
        this.regionStatisticMatrix = new Double[regionSize][timeUpperBound];
        this.isSampledMatrix = new Boolean[regionSize][timeUpperBound];
        this.newSampleIntervalArray = new Integer[regionSize];
        this.noiseStatisticMatrix = new Double[regionSize][timeUpperBound];
        this.estimateStatisticMatrix = new Double[regionSize][timeUpperBound];
        this.timePrivacyBudget = new Double[regionSize][timeUpperBound];
        this.pArray = new Double[regionSize];
        initialize();
    }

    private void initialize() {
        this.tao1 = Constant.TAO_1;
        this.tao2 = Constant.TAO_2;
        this.tao3 = Constant.TAO_3;
        this.sampleWindowSize = Constant.SAMPLE_WINDOW_SIZE;
        // 设置每个region的0时刻的前一个sample区间为0
//        RescueDPUtils.initializeAndAddFirstItemForAllMap(this.samplingRecord, 0, 0);
//        BasicArrayUtil.setIntArrayToZero(this.preSampleIntervalArray);
        BasicArrayUtil.setIntArrayToZero(this.newSampleIntervalArray);
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

    public Double[][] getEstimateStatisticMatrix() {
        return estimateStatisticMatrix;
    }



    public List<Integer> adaptiveSampling(Integer currentTime, List<Integer> sampleRegionIndexList, List<Double> nextTimeRemainBudgetList) {
        List<Integer> result = new ArrayList<>();
        if (currentTime < 0) {
            ListUtils.addValue(result, 1, this.regionSize);
            List<Integer> defaultTotalIndexList = BasicArrayUtil.getIncreaseIntegerNumberList(0, 1, this.regionSize - 1);
            RescueDPUtils.setNextTimeValueAndStatus(this.isSampledMatrix, this.newSampleIntervalArray, defaultTotalIndexList, -1);
            return result;
        }
        TreeMap<Integer, TimeValue[]> estimationHistoryMatrix = RescueDPUtils.getSampleHistoryMatrix(this.estimateStatisticMatrix, this.isSampledMatrix, currentTime, sampleRegionIndexList, Constant.PID_PI/*这里正好是积分窗口大小，因为要取邻居差，最前一个正好要去掉*/);
        Integer tempIndex, tempInterval;
        Double[] eArray;
        TimeValue[] tempTimeValueArray;
        List<Double> partEstimationList;
        Integer kBefore, intervalBefore;
        Double element, tempEpsilon;
        Iterator<Double> nextTimeRemainBudgetIterator = nextTimeRemainBudgetList.iterator();
        for (Map.Entry<Integer, TimeValue[]> historyItem : estimationHistoryMatrix.entrySet()) {
            tempIndex = historyItem.getKey();
            tempTimeValueArray = historyItem.getValue();
            // 这里假设history里面包含sample项
            kBefore = tempTimeValueArray[tempTimeValueArray.length-1].getTimePoint();
            partEstimationList = new ArrayList<>();
            for (TimeValue tValue : tempTimeValueArray) {
                element = tValue.getStatisticValue();
                if (element >= 0) {
                    partEstimationList.add(element);
                }
            }
            partEstimationList.add(this.estimateStatisticMatrix[tempIndex][currentTime]);
//            eArray = partEstimationList.toArray(new Double[0]);
            eArray = RescueDPUtils.getEnhancedNeighborAbsDifferenceWithGivenSizeUpperBoundFromEnd(partEstimationList, Constant.PID_PI);
            intervalBefore = this.newSampleIntervalArray[tempIndex];
            tempEpsilon = nextTimeRemainBudgetIterator.next();
            tempInterval = RescueDPUtils.getNewSampleInterval(Constant.KP, Constant.KI, Constant.KD, eArray, currentTime, kBefore, intervalBefore, Constant.THETA_SCALE, 1/tempEpsilon);
            this.newSampleIntervalArray[tempIndex] = tempInterval;
            result.add(tempInterval);
            RescueDPUtils.setNextValueAndStatus(this.estimateStatisticMatrix, this.isSampledMatrix, this.timePrivacyBudget, tempIndex, currentTime, this.estimateStatisticMatrix[tempIndex][currentTime], tempInterval);
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

    public void adaptiveBudgetAllocation(Integer currentTime, List<Integer> sampleRegionIndexList, List<Double> beforeTotalBudgetList) {
        Double beforeWTotalBudget, remainEpsilon, portionP, maxEpsilon, newRegionBudgetAllocation;
        Integer regionIndex;
//        List<Integer> intervalList;
        for (int i = 0; i < sampleRegionIndexList.size(); i++) {
            regionIndex = sampleRegionIndexList.get(i);
//            beforeWTotalBudget = RescueDPUtils.getSumOfHistoricalPrivacyBudget(this.timePrivacyBudget, regionIndex, currentTime, this.sampleWindowSize);
            beforeWTotalBudget = beforeTotalBudgetList.get(i);
            remainEpsilon = this.privacyBudget - beforeWTotalBudget;
//            portionP = Math.min(Constant.PHI_Scale * Math.log1p(intervalList.get(i)), Constant.P_MAX);
            portionP = Math.min(Constant.PHI_Scale * Math.log1p(this.newSampleIntervalArray[regionIndex]), Constant.P_MAX);
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

    public Double[] filtering(Integer currentTime, List<Integer> sampleRegionIndexList) {
        Double rPri, zNow, pBefore;
        Double[] result = null;
        for (Integer index : sampleRegionIndexList) {
            if (currentTime < 1) {
                // currentTime = 0 时，假设前一时刻是0时刻的真实值
//                rPri = this.regionStatisticMatrix[index][0];
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
        return result;
    }

    public void privateRelease() {
        List<Integer> sampleIntervalList = null;
        List<Double> sumOfHistoricalPrivacyBudget, remainPrivacyBudget;
        List<Integer> sampleRegionIndexListNow;

        adaptiveSampling(-1, null, null);
        for (int currentTime = 0; currentTime < this.timeUpperBound; currentTime++) {
            sampleRegionIndexListNow = getSamplingRegionIndexList(currentTime);
            if (sampleRegionIndexListNow.isEmpty()) {
                continue;
            }

            List<List<Integer>> groupingList = dynamicGrouping(currentTime, sampleRegionIndexListNow);

            sumOfHistoricalPrivacyBudget = RescueDPUtils.getSumOfHistoricalPrivacyBudget(this.timePrivacyBudget, sampleRegionIndexListNow, currentTime, this.sampleWindowSize);
            adaptiveBudgetAllocation(currentTime, sampleRegionIndexListNow, sumOfHistoricalPrivacyBudget);
            perturbation(currentTime, groupingList);

            filtering(currentTime, sampleRegionIndexListNow);
//            Double[] testFilter = filtering(currentTime, sampleRegionIndexListNow);
//            if (testFilter == null) {
//                System.out.println("testFilter is null!");
//            }

            if(currentTime < this.timeUpperBound - 1) {
                remainPrivacyBudget = RescueDPUtils.getRemainPrivacyBudget(this.timePrivacyBudget, sampleRegionIndexListNow, currentTime + 1, this.sampleWindowSize, this.privacyBudget);
                adaptiveSampling(currentTime, sampleRegionIndexListNow, remainPrivacyBudget);
            }


        }

        System.out.println("Finish all event!");;
    }


    public static void main(String[] args) {

    }



}
