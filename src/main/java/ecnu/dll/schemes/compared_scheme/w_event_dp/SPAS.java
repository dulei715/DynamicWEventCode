package ecnu.dll.schemes.compared_scheme.w_event_dp;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.SPASUtils;
import ecnu.dll.schemes.compared_scheme.w_event_dp.struct.HistoricalStructure;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;
import java.util.TreeMap;

public class SPAS extends Mechanism {

    protected int currentTime;
    protected Integer windowSize;
    protected Double epsilonS, epsilonP, epsilonS1, epsilonS2;
    protected Integer currentReleaseCount, dimensionSize;
    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected Double deltaS, deltaP;
    protected Double noiseRho;

    protected HistoricalStructure epsilonPUsedHistory;
    protected Double[] lastNoisyStatisticArray;

//    protected Integer sampleCount;

    public SPAS(Integer windowSize, Double epsilon, Integer initializedReleaseCount, Integer dimensionSize) {
        this.windowSize = windowSize;
        this.epsilonS = epsilon * Constant.SampleEpsilonRatio;
        this.epsilonP = epsilon - this.epsilonS;
        this.currentReleaseCount = initializedReleaseCount;
        lastNoisyStatisticArray = BasicArrayUtil.getInitializedArray(0D, dimensionSize);
        this.deltaS = this.deltaP = 1D;

        this.epsilonS1 = this.epsilonS2 = this.epsilonS / 2;
        this.noiseRho = LaplaceUtils.getLaplaceNoise(this.deltaS, this.epsilonS1);
    }

    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
        ++this.currentTime;
        TreeMap<String, Integer> statisticMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
        Integer[] rawStatisticArray = SPASUtils.toArray(statisticMap);
        Double currentDH = SPASUtils.getDH(rawStatisticArray, this.lastNoisyStatisticArray);
        Double currentEpsilonP = this.epsilonP / this.currentReleaseCount;
        if (currentDH + LaplaceUtils.getLaplaceNoise()) {

        }
//        SPASUtils.calculateAdjacentDistanceVariance(statisticMap, this.dimensionSize);
    }

}
