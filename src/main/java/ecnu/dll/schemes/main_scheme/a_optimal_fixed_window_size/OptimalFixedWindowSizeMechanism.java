package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size;

import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.MechanismErrorUtils;
import ecnu.dll.schemes._scheme_utils.PersonalizedDPTools;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.struts.StreamNoiseCountData;
import ecnu.dll.struts.StreamDataElement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class OptimalFixedWindowSizeMechanism {

    protected int currentTime;

    protected List<StreamDataElement<Boolean>> currentDataElementList;
    protected List<Double> privacyBudgetList;
    protected List<Integer> windowSizeList;

    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    public OptimalFixedWindowSizeMechanism(List<String> dataTypeList) {
        this.currentTime = -1;
        this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, dataTypeList);
    }

    protected abstract List<Double> getCalculationPrivacyBudgetList();

    protected abstract List<Double> getPublicationPrivacyBudgetList();

    public void setParameters(List<StreamDataElement<Boolean>> currentDataElementList, List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        this.currentDataElementList = currentDataElementList;
        this.privacyBudgetList = privacyBudgetList;
        this.windowSizeList = windowSizeList;
    }



    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> nextPrivacyBudgetList, List<Integer> nextWindowSizeList) {
        this.setParameters(nextDataElementList, nextPrivacyBudgetList, nextWindowSizeList);
        // M_{t,1}
        List<Double> calculationBudgetList = getCalculationPrivacyBudgetList();
        Double[] minimalEpsilonAndError = MechanismErrorUtils.getMinimalEpsilonAndError(calculationBudgetList);
        List<Integer> sampleIndexList = PersonalizedDPTools.sampleIndex(calculationBudgetList, minimalEpsilonAndError[0]);
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, this.currentDataElementList, sampleIndexList);
        Double dissimilarity = SchemeUtils.getDissimilarity(sampleCountMap, this.lastReleaseNoiseCountMap, minimalEpsilonAndError[0]);

        // M_{t,2}
        List<Double> publicationBudgetList = getPublicationPrivacyBudgetList();
        minimalEpsilonAndError = MechanismErrorUtils.getMinimalEpsilonAndError(publicationBudgetList);

        TreeMap<String, Double> releaseDataMap;
        ++this.currentTime;
        if (dissimilarity > Math.sqrt(minimalEpsilonAndError[1])) {
            sampleIndexList = PersonalizedDPTools.sampleIndex(publicationBudgetList, minimalEpsilonAndError[0]);
            sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, this.currentDataElementList, sampleIndexList);
            releaseDataMap = PersonalizedDPTools.getNoiseCount(sampleCountMap, minimalEpsilonAndError[0]);
            this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            return true;
        }

        return false;


    }

}
