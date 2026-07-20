package ecnu.dll.schemes.compared_scheme.w_event_dp;

import ecnu.dll.struts.non_personalized_struct.MechanismDetailStruct;
import ecnu.dll.struts.non_personalized_struct.MechanismPartBDetails;
import ecnu.dll.struts.stream_data.StreamBudgetData;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.List;

public class BudgetAbsorption extends WEventMechanism {

    private StreamBudgetData lastTimePublicationBudgetData;
    private Double nullifiedTimeStamp;

    public BudgetAbsorption(List<String> dataTypeList, Double privacyBudget, Integer windowSize) {
        super(dataTypeList, privacyBudget, windowSize);
        this.lastTimePublicationBudgetData = new StreamBudgetData(-1, this.privacyBudget / (2*this.windowSize));

    }

    @Override
    protected void setPublicationPrivacyBudget() {
        Integer lastPublicationTimeSlot = this.lastTimePublicationBudgetData.getTimeSlot();
        double tempAbsorbedTimestamp = this.currentTime - lastPublicationTimeSlot - this.nullifiedTimeStamp;
        this.publicationPrivacyBudget = this.privacyBudget / (2*this.windowSize) * Math.min(tempAbsorbedTimestamp, this.windowSize);
    }

    @Override
    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
        ++this.currentTime;
        Double dissimilarity = mechanismPartA(nextDataElementList);
        Double lastTimePublicationBudget = this.lastTimePublicationBudgetData.getBudgetList().get(0);
        this.nullifiedTimeStamp = lastTimePublicationBudget / (this.privacyBudget / (2*this.windowSize)) - 1;
        if (this.currentTime - this.lastTimePublicationBudgetData.getTimeSlot() <= this.nullifiedTimeStamp) {
            return false;
        }
        setPublicationPrivacyBudget();
        return mechanismPartB(nextDataElementList, dissimilarity);
    }

    @Override
    public MechanismDetailStruct updateNextPublicationResultDetails(List<StreamDataElement<Boolean>> nextDataElementList) {
        ++this.currentTime;
        Double[] partADetails;
        Double dissimilarity, partAScale;
        partADetails = mechanismPartADetails(nextDataElementList);
        dissimilarity = partADetails[0];
        partAScale = partADetails[1];
        MechanismPartBDetails mechanismPartBDetails;
        Double lastTimePublicationBudget = this.lastTimePublicationBudgetData.getBudgetList().get(0);
        this.nullifiedTimeStamp = lastTimePublicationBudget / (this.privacyBudget / (2*this.windowSize)) - 1;
        if (this.currentTime - this.lastTimePublicationBudgetData.getTimeSlot() <= this.nullifiedTimeStamp) {
            mechanismPartBDetails = new MechanismPartBDetails(false, false, 0D);
            return new MechanismDetailStruct(partAScale, mechanismPartBDetails);
        }
        setPublicationPrivacyBudget();
        mechanismPartBDetails = mechanismPartBDetails(nextDataElementList, dissimilarity);
        return new MechanismDetailStruct(partAScale, mechanismPartBDetails);
    }

    @Override
    public String getSimpleName() {
        return "BA";
    }
}
