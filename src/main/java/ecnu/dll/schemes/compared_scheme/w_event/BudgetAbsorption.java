package ecnu.dll.schemes.compared_scheme.w_event;

import cn.edu.dll.collection.ListUtils;
import ecnu.dll.struts.StreamBudgetData;
import ecnu.dll.struts.StreamDataElement;

import java.util.ArrayList;
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
}
