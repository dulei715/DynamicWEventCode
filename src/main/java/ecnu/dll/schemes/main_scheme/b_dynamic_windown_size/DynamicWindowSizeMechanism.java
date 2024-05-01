package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size;

import ecnu.dll.struts.StreamDataElement;
import ecnu.dll.struts.StreamNoiseCountData;

import java.util.List;

public abstract class DynamicWindowSizeMechanism {
    protected int currentTime;
//    protected List<Double> privacyBudgetList;
//    protected List<Integer> windowSizeList;

//    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected List<Double> calculationPrivacyBudgetList;
    protected List<Double> publicationPrivacyBudgetList;

//    protected List<>

    protected abstract void setCalculationPrivacyBudgetList();

    protected abstract void setPublicationPrivacyBudgetList();



    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> backwardBudgetList,
                                               List<Integer> backwardWindowSizeList, List<Double> forwardBudgetList,
                                               List<Integer> forwardWindowSizeList) {

        ++this.currentTime;



        // M_{t,1}
//        Double dissimilarity = mechanismPartA(nextDataElementList);

        // M_{t,2}
//        return mechanismPartB(nextDataElementList, dissimilarity);

        return false;
    }
}
