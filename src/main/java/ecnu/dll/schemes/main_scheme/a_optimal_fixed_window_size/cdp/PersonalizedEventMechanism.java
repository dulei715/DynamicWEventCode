package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.cdp;

import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.PersonalizedDPTools;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.struts.personalized_struct.PersonalizedMechanismDetailStruct;
import ecnu.dll.struts.personalized_struct.PersonalizedMechanismErrorDetailStruct;
import ecnu.dll.struts.personalized_struct.PersonalizedMechanismPartADetailStruct;
import ecnu.dll.struts.personalized_struct.PersonalizedMechanismPartBDetailStruct;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.List;
import java.util.TreeMap;

public abstract class PersonalizedEventMechanism extends Mechanism {

    protected int currentTime;
    protected List<Double> privacyBudgetList;
    protected List<Integer> windowSizeList;

    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected List<Double> calculationPrivacyBudgetList;
    protected List<Double> publicationPrivacyBudgetList;

    public PersonalizedEventMechanism(List<String> dataTypeList, List<Double> privacyBudgetList, List<Integer> windowSizeList) {
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

    public List<Double> getPrivacyBudgetList() {
        return privacyBudgetList;
    }

    public List<Integer> getWindowSizeList() {
        return windowSizeList;
    }

    public StreamNoiseCountData getReleaseNoiseCountData() {
        return this.lastReleaseNoiseCountMap;
    }



    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {

        ++this.currentTime;
        // M_{t,1}
        Double dissimilarity = mechanismPartA(nextDataElementList);

        // M_{t,2}
        return mechanismPartB(nextDataElementList, dissimilarity);
    }
    public PersonalizedMechanismDetailStruct updateNextPublicationResultDetails(List<StreamDataElement<Boolean>> nextDataElementList) {

        throw new RuntimeException("This Class is not PBA! You mustn't use this method!");
    }

    protected Double mechanismPartA(List<StreamDataElement<Boolean>> nextDataElementList) {
        setCalculationPrivacyBudgetList();
        Double[] minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(this.calculationPrivacyBudgetList);
        List<Integer> sampleIndexList = PersonalizedDPTools.sampleIndex(this.calculationPrivacyBudgetList, minimalEpsilonAndError[0]);
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
        Double dissimilarity = SchemeUtils.getDissimilarity(sampleCountMap, this.lastReleaseNoiseCountMap, minimalEpsilonAndError[0]);
        return dissimilarity;
    }

    /**
     * 返回[dissimilarity, optimalError, chosenSamplingError, chosenDPError]
     * @param nextDataElementList
     * @return
     */
    protected PersonalizedMechanismPartADetailStruct mechanismPartADetails(List<StreamDataElement<Boolean>> nextDataElementList) {
        setCalculationPrivacyBudgetList();
        Double[] minimalEpsilonAndError = SchemeUtils.selectOptimalBudgetWithDetails(this.calculationPrivacyBudgetList);
        List<Integer> sampleIndexList = PersonalizedDPTools.sampleIndex(this.calculationPrivacyBudgetList, minimalEpsilonAndError[0]);
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
        Double dissimilarity = SchemeUtils.getDissimilarity(sampleCountMap, this.lastReleaseNoiseCountMap, minimalEpsilonAndError[0]);
//        return new Double[]{dissimilarity, minimalEpsilonAndError[1], minimalEpsilonAndError[2], minimalEpsilonAndError[3]};
        // minimalEpsilonAndError后几个是chosenSamplingError, chosenDPError, chosenCountVar, chosenBiasSquare，因此这里需要调整顺序匹配构造函数
        PersonalizedMechanismErrorDetailStruct personalizedMechanismErrorDetailStruct = new PersonalizedMechanismErrorDetailStruct(minimalEpsilonAndError[2], minimalEpsilonAndError[4], minimalEpsilonAndError[5], minimalEpsilonAndError[3]);
        return new PersonalizedMechanismPartADetailStruct(minimalEpsilonAndError[0], dissimilarity, minimalEpsilonAndError[1], personalizedMechanismErrorDetailStruct);
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
            releaseDataMap = SchemeUtils.getNoiseCount(sampleCountMap, minimalEpsilonAndError[0]);
            this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            return true;
        }

        return false;
    }

    protected PersonalizedMechanismPartBDetailStruct mechanismPartBDetails(List<StreamDataElement<Boolean>> nextDataElementList, Double dissimilarity) {
        TreeMap<String, Integer> sampleCountMap;
        Double[] minimalEpsilonAndError;
        List<Integer> sampleIndexList;
        boolean status;
        setPublicationPrivacyBudgetList();
        minimalEpsilonAndError = SchemeUtils.selectOptimalBudgetWithDetails(this.publicationPrivacyBudgetList);

        TreeMap<String, Double> releaseDataMap;

        PersonalizedMechanismErrorDetailStruct personalizedMechanismErrorDetailStruct;

//        System.out.printf("dis: %f; err: %f\n", dissimilarity, Math.sqrt(minimalEpsilonAndError[1]));

        if (dissimilarity > Math.sqrt(minimalEpsilonAndError[1])) {
            sampleIndexList = PersonalizedDPTools.sampleIndex(this.publicationPrivacyBudgetList, minimalEpsilonAndError[0]);
            sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
            releaseDataMap = SchemeUtils.getNoiseCount(sampleCountMap, minimalEpsilonAndError[0]);
            this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            status = true;
        } else {
            status = false;
        }
        /*
            minimalEpsilonAndError后几个是chosenSamplingError, chosenDPError, chosenCountVar, chosenBiasSquare，因此这里需要调整顺序匹配构造函数
            这里nonNull默认为true，后面只有调用上层判断nonnull的时候才能决定
         */
        return new PersonalizedMechanismPartBDetailStruct(status, true, minimalEpsilonAndError[0], minimalEpsilonAndError[1], new PersonalizedMechanismErrorDetailStruct(minimalEpsilonAndError[2], minimalEpsilonAndError[4], minimalEpsilonAndError[5], minimalEpsilonAndError[3]));
    }

    public abstract String getSimpleName();

}
