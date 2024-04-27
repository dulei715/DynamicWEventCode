package ecnu.dll.schemes.compared_scheme.w_event;

import ecnu.dll.struts.StreamNoiseCountData;

import java.util.List;

public abstract class WEventMechanism {
    protected int currentTime;

    //    protected List<StreamDataElement<Boolean>> currentDataElementList;
    protected Double privacyBudget;
    protected Integer windowSize;

    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected Double calculationPrivacyBudget;
    protected Double publicationPrivacyBudget;



}
