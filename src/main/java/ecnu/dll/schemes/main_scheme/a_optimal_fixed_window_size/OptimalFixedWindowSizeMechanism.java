package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size;

import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.PersonalizedDPTools;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.List;
import java.util.TreeMap;

public abstract class OptimalFixedWindowSizeMechanism {

    protected int currentTime;
    protected List<Double> privacyBudgetList;
    protected List<Integer> windowSizeList;

    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected List<Double> calculationPrivacyBudgetList;
    protected List<Double> publicationPrivacyBudgetList;

    public OptimalFixedWindowSizeMechanism(List<String> dataTypeList, List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        this.currentTime = -1;
        this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, dataTypeList);
        this.privacyBudgetList = privacyBudgetList;
        this.windowSizeList = windowSizeList;
    }

    protected abstract void setCalculationPrivacyBudgetList();

    protected abstract void setPublicationPrivacyBudgetList();

    public void setParameters(List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        this.privacyBudgetList = privacyBudgetList;
        this.windowSizeList = windowSizeList;
    }

    public StreamNoiseCountData getReleaseNoiseCountMap() {
        return this.lastReleaseNoiseCountMap;
    }



    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {

        ++this.currentTime;
        // M_{t,1}
        Double dissimilarity = mechanismPartA(nextDataElementList);

        // M_{t,2}
        return mechanismPartB(nextDataElementList, dissimilarity);
    }

    protected Double mechanismPartA(List<StreamDataElement<Boolean>> nextDataElementList) {
        setCalculationPrivacyBudgetList();
        Double[] minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(this.calculationPrivacyBudgetList);
        List<Integer> sampleIndexList = PersonalizedDPTools.sampleIndex(this.calculationPrivacyBudgetList, minimalEpsilonAndError[0]);
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
        Double dissimilarity = SchemeUtils.getDissimilarity(sampleCountMap, this.lastReleaseNoiseCountMap, minimalEpsilonAndError[0]);
        return dissimilarity;
    }

    protected boolean mechanismPartB(List<StreamDataElement<Boolean>> nextDataElementList, Double dissimilarity) {
        TreeMap<String, Integer> sampleCountMap;
        Double[] minimalEpsilonAndError;
        List<Integer> sampleIndexList;
        setPublicationPrivacyBudgetList();
        minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(this.publicationPrivacyBudgetList);

        TreeMap<String, Double> releaseDataMap;

//        System.out.printf("dis: %f; err: %f\n", dissimilarity, Math.sqrt(minimalEpsilonAndError[1]));

        if (dissimilarity > Math.sqrt(minimalEpsilonAndError[1])) {
            sampleIndexList = PersonalizedDPTools.sampleIndex(this.publicationPrivacyBudgetList, minimalEpsilonAndError[0]);
            sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
            releaseDataMap = PersonalizedDPTools.getNoiseCount(sampleCountMap, minimalEpsilonAndError[0]);
            this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            return true;
        }

        return false;
    }

}
