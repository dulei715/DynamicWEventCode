package ecnu.dll.schemes.compared_scheme.w_event_dp;

import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.SPASUtils;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.schemes.compared_scheme.w_event_dp.struct.EpsilonHistoricalStructure;
import ecnu.dll.schemes.compared_scheme.w_event_dp.struct.NoisyStatisticHistoricalStructure;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;
import java.util.TreeMap;

public class SPAS extends Mechanism {

    protected int currentTime;
    protected Integer windowSize;
    protected Double privacyBudget, epsilonS, epsilonP, epsilonS1, epsilonS2, warmUpAverageEpsilon, warmUpAverageEpsilonPView;
    protected Integer currentReleaseCount, dimensionSize;
//    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected Double deltaS, deltaP;
    protected Double noiseRho;

    protected EpsilonHistoricalStructure epsilonPUsedHistory;
    //    protected Double[] lastNoisyStatisticArray;
    protected StreamNoiseCountData lastReleaseNoiseCountData;
    protected Integer warmUpLength;
    protected Integer historicalLengthRetain;
    protected NoisyStatisticHistoricalStructure releaseNoisyHistory;

//    protected Integer sampleCount;

    public SPAS(List<String> dataTypeList, Double epsilon, Integer windowSize) {
        this.currentTime = -1;
        this.windowSize = windowSize;
        this.privacyBudget = epsilon;
        this.epsilonS = epsilon * Constant.SampleEpsilonRatio;
        this.epsilonP = epsilon - this.epsilonS;
        this.warmUpAverageEpsilon = epsilon * Constant.WarmUpM / windowSize;
        this.warmUpAverageEpsilonPView = this.warmUpAverageEpsilon * (1 - Constant.SampleEpsilonRatio);
        this.warmUpLength = windowSize;
        this.historicalLengthRetain = (int)Math.ceil(windowSize * Constant.paramLRatio);
//        this.currentReleaseCount = initializedReleaseCount;
//        lastNoisyStatisticArray = BasicArrayUtil.getInitializedArray(0D, dimensionSize);
//        lastNoisyStatisticMap = MapUtils.getInitializedTreeMap(dataTypeList, 0D);
        // 只保留之前window size - 1个epsilon使用的历史数据
        this.epsilonPUsedHistory = new EpsilonHistoricalStructure(windowSize - 1);
        this.lastReleaseNoiseCountData = new StreamNoiseCountData(this.currentTime, dataTypeList);
        this.deltaS = this.deltaP = 1D;

        this.epsilonS1= this.epsilonS / 2;
        this.epsilonS2 = this.epsilonS / 2;
        this.noiseRho = LaplaceUtils.getLaplaceNoise(this.deltaS, this.epsilonS1);

        this.releaseNoisyHistory = new NoisyStatisticHistoricalStructure(this.historicalLengthRetain);
    }

    public Double getPrivacyBudget() {
        return privacyBudget;
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
        ++this.currentTime;
        if (this.currentTime < this.warmUpLength) {
            return updateNextPublicationResultWithinWarmUp(nextDataElementList);
        }
        if (this.currentTime == this.warmUpLength) {
            this.currentReleaseCount = updateVarEDisAndGetNewReleaseCount();
//            System.out.println("init: " + this.currentReleaseCount) ;
        }
        return updateNextPublicationResultAfterWarmUp(nextDataElementList);
    }

    public boolean updateNextPublicationResultWithinWarmUp(List<StreamDataElement<Boolean>> nextDataElementList) {
        StreamNoiseCountData releaseNoiseCountData;
        Double currentEpsilon;
        boolean finalJudge = this.currentTime % Constant.WarmUpM == 0;
        if (finalJudge) {
            currentEpsilon = this.warmUpAverageEpsilon;
            TreeMap<String, Integer> statisticMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
            TreeMap<String, Double> laplaceNoiseCount = SchemeUtils.getLaplaceNoiseCount(statisticMap, this.deltaP, currentEpsilon);
            releaseNoiseCountData = new StreamNoiseCountData(this.currentTime, laplaceNoiseCount);
        } else {
            currentEpsilon = 0D;
            releaseNoiseCountData = this.lastReleaseNoiseCountData;
        }

        // 这里的epsilon_p账本只记录epsilon_p那一部分
        this.epsilonPUsedHistory.add(finalJudge ? this.warmUpAverageEpsilonPView : 0D);
        this.releaseNoisyHistory.add(releaseNoiseCountData.getDataMap(), finalJudge);
        this.lastReleaseNoiseCountData = releaseNoiseCountData;

        return finalJudge;
    }

    public boolean updateNextPublicationResultAfterWarmUp(List<StreamDataElement<Boolean>> nextDataElementList) {
        TreeMap<String, Integer> statisticMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
        Integer[] rawStatisticArray = SPASUtils.toArray(statisticMap, Integer[].class);
        Double[] lastNoisyStatisticArray = SPASUtils.toArray(this.lastReleaseNoiseCountData);
        Double currentDH = SPASUtils.getDH(rawStatisticArray, lastNoisyStatisticArray);
//        System.out.println(this.currentReleaseCount);

//        // todo: for test
//        if (this.currentReleaseCount < 0) {
//            System.err.println("[DEBUG] SPAS - currentReleaseCount is NEGATIVE before calculation:");
//            System.err.println("  currentTime: " + this.currentTime);
//            System.err.println("  currentReleaseCount: " + this.currentReleaseCount);
//            System.err.println("  epsilonP: " + this.epsilonP);
//            System.err.println("  deltaS: " + this.deltaS);
//        }

        if (this.currentReleaseCount == null || this.currentReleaseCount <= 0) {
            this.currentReleaseCount = 1;
        }


        Double currentEpsilonP = this.epsilonP / this.currentReleaseCount;
        double sensitivity = 2*this.currentReleaseCount * this.deltaS;
//        // todo: for test
//        if (sensitivity <= 0) {
//            System.err.println("[DEBUG] SPAS - Sensitivity is non-positive:");
//            System.err.println("  currentReleaseCount: " + this.currentReleaseCount);
//            System.err.println("  deltaS: " + this.deltaS);
//            System.err.println("  sensitivity: " + sensitivity);
//        }


        boolean judgeA = currentDH + LaplaceUtils.getLaplaceNoise(sensitivity, epsilonS2) > this.currentReleaseCount * deltaS / epsilonP + noiseRho;
        boolean judgeB = this.epsilonPUsedHistory.getSum() + currentEpsilonP <= this.epsilonP;
        boolean finalJudge = judgeA & judgeB;
        TreeMap<String, Double> laplaceNoiseCount;
        StreamNoiseCountData releaseNoiseCountData;
        if (finalJudge) {
            laplaceNoiseCount = SchemeUtils.getLaplaceNoiseCount(statisticMap, this.deltaP, currentEpsilonP);
            releaseNoiseCountData = new StreamNoiseCountData(this.currentTime, laplaceNoiseCount);
        } else {
            currentEpsilonP = 0D;
            releaseNoiseCountData = this.lastReleaseNoiseCountData;
        }

        this.epsilonPUsedHistory.add(currentEpsilonP);

        this.releaseNoisyHistory.add(releaseNoiseCountData.getDataMap(), finalJudge);
        this.lastReleaseNoiseCountData = releaseNoiseCountData;


        this.currentReleaseCount = updateVarEDisAndGetNewReleaseCount();

//        // todo: for test
//        if (this.currentReleaseCount < 0) {
//            System.err.println("[DEBUG] SPAS - currentReleaseCount became NEGATIVE after update:");
//            System.err.println("  currentTime: " + this.currentTime);
//            System.err.println("  new currentReleaseCount: " + this.currentReleaseCount);
//        }


        return finalJudge;

    }

    protected Integer updateVarEDisAndGetNewReleaseCount() {
        List<TreeMap<String, Double>> effectiveNoisyStatisticList = this.releaseNoisyHistory.getEffectiveNoisyStatisticList();
//        if (effectiveNoisyStatisticList == null || effectiveNoisyStatisticList.isEmpty()) {
//            System.out.println("wocwoc!!");
//        }
        Double historyDifferenceVariance = SPASUtils.calculateAdjacentDistanceVariance(effectiveNoisyStatisticList);
        // todo: for test

        Integer newReleaseCount =  SPASUtils.calculateCStar(this.epsilonP, this.deltaP, historyDifferenceVariance, Constant.MinimumCValue, this.windowSize);
//        if (newReleaseCount > 100000 || newReleaseCount < 0) {
//            System.err.println("[DEBUG] updateVarEDisAndGetNewReleaseCount ABNORMAL:");
//            System.err.println("  currentTime: " + this.currentTime);
//            System.err.println("  old currentReleaseCount: " + this.currentReleaseCount);
//            System.err.println("  effectiveNoisyStatisticList size: " + (effectiveNoisyStatisticList == null ? "null" : effectiveNoisyStatisticList.size()));
//            System.err.println("  historyDifferenceVariance: " + historyDifferenceVariance);
//            System.err.println("  newReleaseCount: " + newReleaseCount);
//            System.err.println("  epsilonP: " + this.epsilonP);
//            System.err.println("  deltaP: " + this.deltaP);
//        }
        return newReleaseCount;
    }

    @Override
    public String getSimpleName() {
        return "SPAS";
    }

    @Override
    public StreamNoiseCountData getReleaseNoiseCountData() {
        return this.lastReleaseNoiseCountData;
    }
}
