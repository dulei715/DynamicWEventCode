package ecnu.dll.schemes.compared_scheme.w_event;

import ecnu.dll.struts.StreamBudgetData;

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
}
