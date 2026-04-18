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
    protected Double privacyBudget, epsilonS, epsilonP, epsilonS1, epsilonS2, warmUpAverageEpsilon;
    protected Integer currentReleaseCount, dimensionSize;
//    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected Double deltaS, deltaP;
    protected Double noiseRho;

    protected EpsilonHistoricalStructure epsilonPUsedHistory;
    //    protected Double[] lastNoisyStatisticArray;
    protected StreamNoiseCountData lastReleaseNoiseCountData;
    protected Integer historicalLengthRetain;
    protected NoisyStatisticHistoricalStructure releaseNoisyHistory;

//    protected Integer sampleCount;

    public SPAS(List<String> dataTypeList, Integer windowSize, Double epsilon) {
        this.currentTime = -1;
        this.windowSize = windowSize;
        this.privacyBudget = epsilon;
        this.epsilonS = epsilon * Constant.SampleEpsilonRatio;
        this.epsilonP = epsilon - this.epsilonS;
        this.warmUpAverageEpsilon = epsilon * Constant.WarmUpM / windowSize;
//        this.currentReleaseCount = initializedReleaseCount;
//        lastNoisyStatisticArray = BasicArrayUtil.getInitializedArray(0D, dimensionSize);
//        lastNoisyStatisticMap = MapUtils.getInitializedTreeMap(dataTypeList, 0D);
        this.epsilonPUsedHistory = new EpsilonHistoricalStructure(windowSize);
        this.lastReleaseNoiseCountData = new StreamNoiseCountData(this.currentTime, dataTypeList);
        this.deltaS = this.deltaP = 1D;

        this.epsilonS1 = this.epsilonS2 = this.epsilonS / 2;
        this.noiseRho = LaplaceUtils.getLaplaceNoise(this.deltaS, this.epsilonS1);
        this.historicalLengthRetain = (int)Math.ceil(windowSize * Constant.paramLRatio);
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
        if (this.currentTime < this.historicalLengthRetain) {
            return updateNextPublicationResultWithinWarmUp(nextDataElementList);
        }
        if (this.currentTime == this.historicalLengthRetain) {
            this.currentReleaseCount = updateVarEDisAndGetNewReleaseCount();
//            System.out.println("init: " + this.currentReleaseCount) ;
        }
        return updateNextPublicationResultAfterWarmUp(nextDataElementList);
    }

    public boolean updateNextPublicationResultWithinWarmUp(List<StreamDataElement<Boolean>> nextDataElementList) {
        StreamNoiseCountData releaseNoiseCountData;
        Double currentEpsilonP;
        boolean finalJudge = this.currentTime % Constant.WarmUpM == 0;
        if (finalJudge) {
            currentEpsilonP = this.warmUpAverageEpsilon;
            TreeMap<String, Integer> statisticMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
            TreeMap<String, Double> laplaceNoiseCount = SchemeUtils.getLaplaceNoiseCount(statisticMap, this.deltaP, currentEpsilonP);
            releaseNoiseCountData = new StreamNoiseCountData(this.currentTime, laplaceNoiseCount);
        } else {
            currentEpsilonP = 0D;
            releaseNoiseCountData = this.lastReleaseNoiseCountData;
        }

        this.epsilonPUsedHistory.add(currentEpsilonP);
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
        Double currentEpsilonP = this.epsilonP / this.currentReleaseCount;
        // 先将最新的currentEpsilonP放到epsilonPUsedHistory中，这样刚好能组成一个以当前currentEpsilonP为末尾的判断窗口
        this.epsilonPUsedHistory.add(currentEpsilonP);
        boolean judgeA = currentDH + LaplaceUtils.getLaplaceNoise(2*this.currentReleaseCount*this.deltaS, epsilonS2) > this.currentReleaseCount * deltaS / epsilonP + noiseRho;
        boolean judgeB = this.epsilonPUsedHistory.getSum() <= this.epsilonP;
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


        this.releaseNoisyHistory.add(releaseNoiseCountData.getDataMap(), finalJudge);
        this.lastReleaseNoiseCountData = releaseNoiseCountData;


        this.currentReleaseCount = updateVarEDisAndGetNewReleaseCount();


        return finalJudge;

    }

    protected Integer updateVarEDisAndGetNewReleaseCount() {
        List<TreeMap<String, Double>> effectiveNoisyStatisticList = this.releaseNoisyHistory.getEffectiveNoisyStatisticList();
        Double historyDifferenceVariance = SPASUtils.calculateAdjacentDistanceVariance(effectiveNoisyStatisticList);
        return SPASUtils.calculateCStar(this.epsilonP, this.deltaP, historyDifferenceVariance, Constant.MinimumCValue);
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
