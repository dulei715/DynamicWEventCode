package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size;

import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.special_tools.ForwardImpactStreamTools;
import ecnu.dll.struts.BackwardHistoricalStream;
import ecnu.dll.struts.ForwardImpactStream;
import ecnu.dll.struts.StreamDataElement;

import java.util.ArrayList;
import java.util.List;

public abstract class DynamicWindowSizeMechanism {
    protected int userSize;
    protected int currentTime;
//    protected List<Double> privacyBudgetList;
//    protected List<Integer> windowSizeList;

//    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected List<Double> calculationPrivacyBudgetList;
    protected List<Double> publicationPrivacyBudgetList;

    // Each user holds a forwardImpactStream
    protected List<ForwardImpactStream> forwardImpactStreamList;
    protected List<BackwardHistoricalStream> backwardHistoricalStreamList;

    public DynamicWindowSizeMechanism(List<String> dataTypeList, int userSize) {
        this.userSize = userSize;
        this.forwardImpactStreamList = new ArrayList<>(userSize);
        this.backwardHistoricalStreamList = new ArrayList<>();
        for (int i = 0; i < userSize; i++) {
            this.forwardImpactStreamList.add(new ForwardImpactStream());
            this.backwardHistoricalStreamList.add(new BackwardHistoricalStream());
        }
    }

//    protected List<>

    protected void setCalculationPrivacyBudgetList(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> backwardBudgetList,
                                                   List<Integer> backwardWindowSizeList, List<Double> forwardBudgetList,
                                                   List<Integer> forwardWindowSizeList){
        StreamDataElement<Boolean> tempStreamElement;
        Double tempBackwardBudget, tempForwardBudget, tempForwardAverageBudget, tempBackwardBudgetRemain, tempCalculationBudget;
        Integer tempBackwardWindowSize, tempForwardWindowSize;
        ForwardImpactStream tempForwardStream;
        BackwardHistoricalStream tempBackwardStream;

        // todo: alter it to subclass
        for (int userID = 0; userID < this.userSize; ++userID) {
            tempForwardBudget = forwardBudgetList.get(userID);
            tempForwardWindowSize = forwardWindowSizeList.get(userID);
            tempForwardStream = this.forwardImpactStreamList.get(userID);
            tempBackwardStream = this.backwardHistoricalStreamList.get(userID);
            tempBackwardWindowSize = backwardWindowSizeList.get(userID);
            tempBackwardBudget = backwardBudgetList.get(userID);

            tempForwardStream.addElement(tempForwardBudget, tempForwardWindowSize);
            tempForwardAverageBudget = ForwardImpactStreamTools.getMinimalHalfAverageBudgetInWindow(tempForwardStream);
            tempBackwardBudgetRemain = tempBackwardBudget / 2 - tempBackwardStream.getHistoricalCalculationBudgetSum(tempBackwardWindowSize-1);
            tempCalculationBudget = Math.min(tempForwardAverageBudget, Math.max(tempBackwardBudgetRemain, 0));
            this.calculationPrivacyBudgetList.add(tempCalculationBudget);
        }
    }

    protected abstract void setPublicationPrivacyBudgetList();


    protected Double mechanismPartA(List<StreamDataElement<Boolean>> nextDataElementList) {

    }


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
