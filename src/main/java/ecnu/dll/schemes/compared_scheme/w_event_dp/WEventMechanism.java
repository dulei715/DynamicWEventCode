package ecnu.dll.schemes.compared_scheme.w_event_dp;

import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.struts.non_personalized_struct.MechanismDetailStruct;
import ecnu.dll.struts.non_personalized_struct.MechanismPartBDetails;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;
import java.util.TreeMap;

public abstract class WEventMechanism extends Mechanism {
    protected int currentTime;

    //    protected List<StreamDataElement<Boolean>> currentDataElementList;
    // windowSize窗口内的总的privacyBudget
    protected Double privacyBudget;
    protected Integer windowSize;

    protected StreamNoiseCountData lastReleaseNoiseCountData;

    protected Double publicationPrivacyBudget;


    public WEventMechanism(List<String> dataTypeList, Double privacyBudget, Integer windowSize) {
        this.currentTime = -1;
        this.privacyBudget = privacyBudget;
        this.windowSize = windowSize;
        this.lastReleaseNoiseCountData = new StreamNoiseCountData(this.currentTime, dataTypeList);
    }

    public Double getPrivacyBudget() {
        return privacyBudget;
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    protected abstract void setPublicationPrivacyBudget();

    public StreamNoiseCountData getReleaseNoiseCountData() {
        return this.lastReleaseNoiseCountData;
    }

    protected Double mechanismPartA(List<StreamDataElement<Boolean>> nextDataElementList) {
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
        Double epsilon = this.privacyBudget / (2 * this.windowSize);
        return SchemeUtils.getDissimilarity(sampleCountMap, this.lastReleaseNoiseCountData, epsilon);
    }
    protected Double[] mechanismPartADetails(List<StreamDataElement<Boolean>> nextDataElementList) {
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
        Double epsilon = this.privacyBudget / (2 * this.windowSize);
        return SchemeUtils.getDissimilarityDetails(sampleCountMap, this.lastReleaseNoiseCountData, epsilon);
    }

    protected boolean mechanismPartB(List<StreamDataElement<Boolean>> nextDataElementList, Double dissimilarity) {
        setPublicationPrivacyBudget();
        Double publicationLambda = 1.0 / this.publicationPrivacyBudget;
        if (dissimilarity > publicationLambda) {
            TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
            TreeMap<String, Double> releaseDataMap = SchemeUtils.getNoiseCount(sampleCountMap, this.publicationPrivacyBudget);
            this.lastReleaseNoiseCountData = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            return true;
        }
        return false;
    }
    protected MechanismPartBDetails mechanismPartBDetails(List<StreamDataElement<Boolean>> nextDataElementList, Double dissimilarity) {
        setPublicationPrivacyBudget();
        Double publicationLambda = 1.0 / this.publicationPrivacyBudget;
        // 这里默认设为都是nonNull的，在具体地BA里进行调整
        boolean publicationStatus, nonNullStatus = true;
        if (dissimilarity > publicationLambda) {
            TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
            TreeMap<String, Double> releaseDataMap = SchemeUtils.getNoiseCount(sampleCountMap, this.publicationPrivacyBudget);
            this.lastReleaseNoiseCountData = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            publicationStatus = true;
        } else {
            publicationStatus = false;
        }
        return new MechanismPartBDetails(publicationStatus, nonNullStatus, publicationLambda);
    }

    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {

        ++this.currentTime;
        // M_{t,1}
        Double dissimilarity = mechanismPartA(nextDataElementList);

        // M_{t,2}
        return mechanismPartB(nextDataElementList, dissimilarity);
    }
    public MechanismDetailStruct updateNextPublicationResultDetails(List<StreamDataElement<Boolean>> nextDataElementList) {

        throw new RuntimeException("This Class is not PA! You mustn't use this method!");
    }



}
