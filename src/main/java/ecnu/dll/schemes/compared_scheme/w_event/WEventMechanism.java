package ecnu.dll.schemes.compared_scheme.w_event;

import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.PersonalizedDPTools;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;
import java.util.TreeMap;

public abstract class WEventMechanism {
    protected int currentTime;

    //    protected List<StreamDataElement<Boolean>> currentDataElementList;
    protected Double privacyBudget;
    protected Integer windowSize;

    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected Double publicationPrivacyBudget;


    public WEventMechanism(List<String> dataTypeList, Double privacyBudget, Integer windowSize) {
        this.currentTime = -1;
        this.privacyBudget = privacyBudget;
        this.windowSize = windowSize;
        this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, dataTypeList);
    }

    protected abstract void setPublicationPrivacyBudget();

    public StreamNoiseCountData getReleaseNoiseCountMap() {
        return this.lastReleaseNoiseCountMap;
    }

    protected Double mechanismPartA(List<StreamDataElement<Boolean>> nextDataElementList) {
        TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
        Double epsilon = this.privacyBudget / (2 * this.windowSize);
        return SchemeUtils.getDissimilarity(sampleCountMap, this.lastReleaseNoiseCountMap, epsilon);
    }

    protected boolean mechanismPartB(List<StreamDataElement<Boolean>> nextDataElementList, Double dissimilarity) {
        setPublicationPrivacyBudget();
        Double publicationLambda = 1.0 / this.publicationPrivacyBudget;
        if (dissimilarity > publicationLambda) {
            TreeMap<String, Integer> sampleCountMap = BooleanStreamDataElementUtils.getCountByGivenElementType(true, nextDataElementList);
            TreeMap<String, Double> releaseDataMap = PersonalizedDPTools.getNoiseCount(sampleCountMap, this.publicationPrivacyBudget);
            this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, releaseDataMap);
            return true;
        }
        return false;
    }

    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {

        ++this.currentTime;
        // M_{t,1}
        Double dissimilarity = mechanismPartA(nextDataElementList);

        // M_{t,2}
        return mechanismPartB(nextDataElementList, dissimilarity);
    }

    public abstract String getSimpleName();

}
