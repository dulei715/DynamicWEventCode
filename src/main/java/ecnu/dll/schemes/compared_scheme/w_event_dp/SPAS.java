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
    protected Double privacyBudget, epsilonS, epsilonP, epsilonS1, epsilonS2;
    protected Integer currentReleaseCount, dimensionSize;
//    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected Double deltaS, deltaP;
    protected Double noiseRho;

    protected EpsilonHistoricalStructure epsilonPUsedHistory;
    //    protected Double[] lastNoisyStatisticArray;
    protected StreamNoiseCountData lastReleaseNoiseCountData;
    protected NoisyStatisticHistoricalStructure releaseNoisyHistory;

//    protected Integer sampleCount;

    public SPAS(List<String> dataTypeList, Integer windowSize, Double epsilon, Integer initializedReleaseCount) {
        this.currentTime = -1;
        this.windowSize = windowSize;
        this.privacyBudget = epsilon;
        this.epsilonS = epsilon * Constant.SampleEpsilonRatio;
        this.epsilonP = epsilon - this.epsilonS;
        this.currentReleaseCount = initializedReleaseCount;
//        lastNoisyStatisticArray = BasicArrayUtil.getInitializedArray(0D, dimensionSize);
//        lastNoisyStatisticMap = MapUtils.getInitializedTreeMap(dataTypeList, 0D);
        this.lastReleaseNoiseCountData = new StreamNoiseCountData(this.currentTime, dataTypeList);
        this.deltaS = this.deltaP = 1D;

        this.epsilonS1 = this.epsilonS2 = this.epsilonS / 2;
        this.noiseRho = LaplaceUtils.getLaplaceNoise(this.deltaS, this.epsilonS1);
        this.releaseNoisyHistory = new NoisyStatisticHistoricalStructure((int)Math.ceil(windowSize * Constant.paramLRatio));
    }

    public Double getPrivacyBudget() {
        return privacyBudget;
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
        ++this.currentTime;
        TreeMap<String, Integer> statisticMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
        Integer[] rawStatisticArray = SPASUtils.toArray(statisticMap, Integer[].class);
        Double[] lastNoisyStatisticArray = SPASUtils.toArray(this.lastReleaseNoiseCountData);
        Double currentDH = SPASUtils.getDH(rawStatisticArray, lastNoisyStatisticArray);
        Double currentEpsilonP = this.epsilonP / this.currentReleaseCount;
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

        List<TreeMap<String, Double>> effectiveNoisyStatisticList = this.releaseNoisyHistory.getEffectiveNoisyStatisticList();
        Double historyDifferenceVariance = SPASUtils.calculateAdjacentDistanceVariance(effectiveNoisyStatisticList);

        this.currentReleaseCount = SPASUtils.calculateCStar(this.epsilonP, this.deltaP, historyDifferenceVariance);



        this.epsilonPUsedHistory.add(currentEpsilonP);
        this.releaseNoisyHistory.add(releaseNoiseCountData.getDataMap(), finalJudge);
        this.lastReleaseNoiseCountData = releaseNoiseCountData;

        return finalJudge;

    }

}
