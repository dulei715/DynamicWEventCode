package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size;

import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.PersonalizedDPTools;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.special_tools.ForwardImpactStreamTools;
import ecnu.dll.struts.direct_stream.BackwardHistoricalStream;
import ecnu.dll.struts.direct_stream.ForwardImpactStream;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public abstract class DynamicPersonalizedWindowSizeMechanism extends Mechanism {
    protected int userSize;
    protected int currentTime;
//    protected List<Double> privacyBudgetList;
//    protected List<Integer> windowSizeList;

    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected List<Double> calculationPrivacyBudgetList;
    protected List<Double> publicationPrivacyBudgetList;

    // Each user holds a forwardImpactStream
    protected List<ForwardImpactStream> forwardImpactStreamList;
    protected List<BackwardHistoricalStream> backwardHistoricalStreamList;

    public DynamicPersonalizedWindowSizeMechanism(List<String> dataTypeList, int userSize) {
        this.userSize = userSize;
        this.forwardImpactStreamList = new ArrayList<>(userSize);
        this.backwardHistoricalStreamList = new ArrayList<>();
        initializeStream();
        this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, dataTypeList);
    }

    protected void initializeStream() {
        for (int i = 0; i < this.userSize; i++) {
            this.forwardImpactStreamList.add(new ForwardImpactStream());
            this.backwardHistoricalStreamList.add(new BackwardHistoricalStream());
        }
    }

    public List<BackwardHistoricalStream> getBackwardHistoricalStreamList() {
        return backwardHistoricalStreamList;
    }

    //    protected List<>

    protected void updateForwardImpactStreamList(List<Double> forwardBudgetList,
                                       List<Integer> forwardWindowSizeList) {
        ForwardImpactStream tempForwardStream;
        Double tempForwardBudget;
        Integer tempForwardWindowSize;
        for (int userID = 0; userID < this.userSize; ++userID) {
            tempForwardBudget = forwardBudgetList.get(userID);
            tempForwardWindowSize = forwardWindowSizeList.get(userID);
            tempForwardStream = this.forwardImpactStreamList.get(userID);
            tempForwardStream.addElement(tempForwardBudget, tempForwardWindowSize);
        }
    }

    protected void updateBackwardHistoricalStreamList() {
        Double tempCalculationBudget, tempPublicationPrivacyBudget;
        for (int userID = 0; userID < this.userSize; ++userID) {
            tempCalculationBudget = this.calculationPrivacyBudgetList.get(userID);
            tempPublicationPrivacyBudget = this.publicationPrivacyBudgetList.get(userID);
            this.backwardHistoricalStreamList.get(userID).addElement(tempCalculationBudget, tempPublicationPrivacyBudget);
        }
    }


    protected void setCalculationPrivacyBudgetList(List<Double> backwardBudgetList,
                                                   List<Integer> backwardWindowSizeList){
        Double tempBackwardBudget, tempForwardAverageBudget, tempBackwardBudgetRemain, tempCalculationBudget;
        Integer tempBackwardWindowSize;
        ForwardImpactStream tempForwardStream;
        BackwardHistoricalStream tempBackwardStream;

        this.calculationPrivacyBudgetList = new ArrayList<>();
        // todo: alter it to subclass
        for (int userID = 0; userID < this.userSize; ++userID) {
            tempForwardStream = this.forwardImpactStreamList.get(userID);
            tempBackwardStream = this.backwardHistoricalStreamList.get(userID);
            tempBackwardWindowSize = backwardWindowSizeList.get(userID);
            tempBackwardBudget = backwardBudgetList.get(userID);

            tempForwardAverageBudget = ForwardImpactStreamTools.getMinimalHalfAverageBudgetInWindow(tempForwardStream);
            tempBackwardBudgetRemain = tempBackwardBudget / 2 - tempBackwardStream.getHistoricalCalculationBudgetSum(tempBackwardWindowSize-1);
            tempCalculationBudget = Math.min(tempForwardAverageBudget, Math.max(tempBackwardBudgetRemain, 0));
            this.calculationPrivacyBudgetList.add(tempCalculationBudget);
        }
    }
    protected void setCalculationPrivacyBudgetListAccordingBackwardPrivacyBudgetDifference(List<Double> remainBackwardBudgetList){
        Double tempRemainBackwardBudget, tempForwardAverageBudget, tempBackwardBudgetRemain, tempCalculationBudget;
        ForwardImpactStream tempForwardStream;
        this.calculationPrivacyBudgetList = new ArrayList<>();
        // todo: alter it to subclass
        for (int userID = 0; userID < this.userSize; ++userID) {
            tempForwardStream = this.forwardImpactStreamList.get(userID);
            tempRemainBackwardBudget = remainBackwardBudgetList.get(userID);

            tempForwardAverageBudget = ForwardImpactStreamTools.getMinimalHalfAverageBudgetInWindow(tempForwardStream);
            tempBackwardBudgetRemain = tempRemainBackwardBudget / 2 ;
            tempCalculationBudget = Math.min(tempForwardAverageBudget, Math.max(tempBackwardBudgetRemain, 0));
            this.calculationPrivacyBudgetList.add(tempCalculationBudget);
        }
    }

    protected abstract void setPublicationPrivacyBudgetList(List<Double> backwardBudgetList,
                                                            List<Integer> backwardWindowSizeList);
    protected abstract void setPublicationPrivacyBudgetListWithRemainBackwardBudget(List<Double> remainBackwardBudgetList);


    protected Double mechanismPartA(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> backwardBudgetList,
                                    List<Integer> backwardWindowSizeList) {
        this.setCalculationPrivacyBudgetList(backwardBudgetList, backwardWindowSizeList);
        Double[] minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(this.calculationPrivacyBudgetList);
        List<Integer> sampleIndexList = PersonalizedDPTools.sampleIndex(this.calculationPrivacyBudgetList, minimalEpsilonAndError[0]);
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
        Double dissimilarity = SchemeUtils.getDissimilarity(sampleCountMap, this.lastReleaseNoiseCountMap, minimalEpsilonAndError[0]);
        return dissimilarity;
    }
    protected Double mechanismPartAWithRemainBackwardBudget(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> remainBackwardBudgetList) {
        this.setCalculationPrivacyBudgetListAccordingBackwardPrivacyBudgetDifference(remainBackwardBudgetList);
        Double[] minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(this.calculationPrivacyBudgetList);
        List<Integer> sampleIndexList = PersonalizedDPTools.sampleIndex(this.calculationPrivacyBudgetList, minimalEpsilonAndError[0]);
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
        Double dissimilarity = SchemeUtils.getDissimilarity(sampleCountMap, this.lastReleaseNoiseCountMap, minimalEpsilonAndError[0]);
        return dissimilarity;
    }

    protected boolean mechanismPartB(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> backwardBudgetList,
                                     List<Integer> backwardWindowSizeList, Double dissimilarity) {
        TreeMap<String, Integer> sampleCountMap;
        Double[] minimalEpsilonAndError;
        List<Integer> sampleIndexList;
        this.setPublicationPrivacyBudgetList(backwardBudgetList, backwardWindowSizeList);
        minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(this.publicationPrivacyBudgetList);
        TreeMap<String, Double> releaseDataMap;
        if (dissimilarity > Math.sqrt(minimalEpsilonAndError[1])) {
            sampleIndexList = PersonalizedDPTools.sampleIndex(this.publicationPrivacyBudgetList, minimalEpsilonAndError[0]);
            sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
            releaseDataMap = PersonalizedDPTools.getNoiseCount(sampleCountMap, minimalEpsilonAndError[0]);
            this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            return true;
        }
        return false;
    }
    protected boolean mechanismPartBWithRemainBackwardBudget(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> remainBackwardBudgetList,
                                                             Double dissimilarity) {
        TreeMap<String, Integer> sampleCountMap;
        Double[] minimalEpsilonAndError;
        List<Integer> sampleIndexList;
        this.setPublicationPrivacyBudgetListWithRemainBackwardBudget(remainBackwardBudgetList);
        minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(this.publicationPrivacyBudgetList);
        TreeMap<String, Double> releaseDataMap;
        if (dissimilarity > Math.sqrt(minimalEpsilonAndError[1])) {
            sampleIndexList = PersonalizedDPTools.sampleIndex(this.publicationPrivacyBudgetList, minimalEpsilonAndError[0]);
            sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList, sampleIndexList);
            releaseDataMap = PersonalizedDPTools.getNoiseCount(sampleCountMap, minimalEpsilonAndError[0]);
            this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            return true;
        }
        return false;
    }


    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> backwardBudgetList,
                                               List<Integer> backwardWindowSizeList, List<Double> forwardBudgetList,
                                               List<Integer> forwardWindowSizeList) {

        ++this.currentTime;

        this.updateForwardImpactStreamList(forwardBudgetList, forwardWindowSizeList);
        // M_{t,1}
        Double dissimilarity = mechanismPartA(nextDataElementList, backwardBudgetList, backwardWindowSizeList);

        // M_{t,2}
        boolean updateStatus = mechanismPartB(nextDataElementList, backwardBudgetList, backwardWindowSizeList, dissimilarity);

        this.updateBackwardHistoricalStreamList();
        return updateStatus;

    }
    public boolean updateNextPublicationResultWithDifferenceBackwardBudgetList(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> differenceBackwardBudgetList,
                                               List<Double> forwardBudgetList, List<Integer> forwardWindowSizeList) {

        ++this.currentTime;

        this.updateForwardImpactStreamList(forwardBudgetList, forwardWindowSizeList);
        // M_{t,1}
        Double dissimilarity = mechanismPartAWithRemainBackwardBudget(nextDataElementList, differenceBackwardBudgetList);

        // M_{t,2}
        boolean updateStatus = mechanismPartBWithRemainBackwardBudget(nextDataElementList, differenceBackwardBudgetList, dissimilarity);

        this.updateBackwardHistoricalStreamList();
        return updateStatus;

    }



    public StreamNoiseCountData getReleaseNoiseCountMap() {
        return this.lastReleaseNoiseCountMap;
    }

    // for test
    public List<Double> getHistoricalPrivacyBudgetSum(int beforeLength) {
        List<Double> historicalBudgetSumList = new ArrayList<>();
        Double tempSum;
        for (int i = 0; i < this.userSize; i++) {
            tempSum = this.backwardHistoricalStreamList.get(i).getHistoricalBudgetSum(beforeLength);
            historicalBudgetSumList.add(tempSum);
        }
        return historicalBudgetSumList;
    }

    public abstract String getSimpleName();

}
