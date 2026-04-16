package ecnu.dll.schemes.compared_scheme.w_event_dp;

import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import cn.edu.dll.map.MapUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.SPASUtils;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.schemes.compared_scheme.w_event_dp.struct.EpsilonHistoricalStructure;
import ecnu.dll.schemes.compared_scheme.w_event_dp.struct.GeneralizedFixedHistoryStructure;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class SPAS extends Mechanism {

    protected int currentTime;
    protected Integer windowSize;
    protected Double epsilonS, epsilonP, epsilonS1, epsilonS2;
    protected Integer currentReleaseCount, dimensionSize;
//    protected StreamNoiseCountData lastReleaseNoiseCountMap;
    protected GeneralizedFixedHistoryStructure<TreeMap<String, Double>> releaseNoisyHistory;

    protected Double deltaS, deltaP;
    protected Double noiseRho;

    protected EpsilonHistoricalStructure epsilonPUsedHistory;
//    protected Double[] lastNoisyStatisticArray;
    protected TreeMap<String, Double> lastNoisyStatisticMap;

//    protected Integer sampleCount;

    public SPAS(Integer windowSize, Double epsilon, Integer initializedReleaseCount, Set<String> attributeSet) {
        this.windowSize = windowSize;
        this.epsilonS = epsilon * Constant.SampleEpsilonRatio;
        this.epsilonP = epsilon - this.epsilonS;
        this.currentReleaseCount = initializedReleaseCount;
//        lastNoisyStatisticArray = BasicArrayUtil.getInitializedArray(0D, dimensionSize);
        lastNoisyStatisticMap = MapUtils.getInitializedTreeMap(attributeSet, 0D);
        this.deltaS = this.deltaP = 1D;

        this.epsilonS1 = this.epsilonS2 = this.epsilonS / 2;
        this.noiseRho = LaplaceUtils.getLaplaceNoise(this.deltaS, this.epsilonS1);
        this.releaseNoisyHistory = new GeneralizedFixedHistoryStructure<>((int)Math.ceil(windowSize * Constant.paramLRatio)+1);
    }

    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
        ++this.currentTime;
        TreeMap<String, Integer> statisticMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
        Integer[] rawStatisticArray = SPASUtils.toArray(statisticMap, Integer[].class);
        Double[] lastNoisyStatisticArray = SPASUtils.toArray(lastNoisyStatisticMap, Double[].class);
        Double currentDH = SPASUtils.getDH(rawStatisticArray, lastNoisyStatisticArray);
        Double currentEpsilonP = this.epsilonP / this.currentReleaseCount;
        boolean judgeA = currentDH + LaplaceUtils.getLaplaceNoise(2*this.currentReleaseCount*this.deltaS, epsilonS2) > this.currentReleaseCount * deltaS / epsilonP + noiseRho;
        boolean judgeB = this.epsilonPUsedHistory.getSum() <= this.epsilonP;
        TreeMap<String, Double> laplaceNoiseCount;
        if (judgeA && judgeB) {
            laplaceNoiseCount = SchemeUtils.getLaplaceNoiseCount(statisticMap, this.deltaP, currentEpsilonP);
        } else {
            currentEpsilonP = 0D;
            laplaceNoiseCount = this.lastNoisyStatisticMap;
        }

        SPASUtils.calculateAdjacentDistanceVariance();
        SPASUtils.calculateCStar(this.epsilonP, this.deltaP, historyDifferenceVariance);

        this.epsilonPUsedHistory.add(currentEpsilonP);
        this.lastNoisyStatisticMap = laplaceNoiseCount;

    }

}
