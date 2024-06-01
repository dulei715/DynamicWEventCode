package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size;

import ecnu.dll.schemes._scheme_utils.nullified.AverageNullifiedBound;
import ecnu.dll.schemes._scheme_utils.nullified.MaximalNullifiedBound;
import ecnu.dll.schemes._scheme_utils.nullified.MinimalNullifiedBound;
import ecnu.dll.schemes._scheme_utils.nullified.NullifiedBound;
import ecnu.dll.struts.direct_stream.*;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersonalizedDynamicBudgetAbsorption extends DynamicWindowSizeMechanism{

    private List<Double> nullifiedTimeStampList;
    private NullifiedBound nullifiedBound;

    public PersonalizedDynamicBudgetAbsorption(List<String> dataTypeList, int userSize, int nullifiedBoundType) {
        super(dataTypeList, userSize);
        switch (nullifiedBoundType) {
            case NullifiedBound.MinimalType:
                this.nullifiedBound = new MinimalNullifiedBound();
                break;
            case NullifiedBound.MaximalType:
                this.nullifiedBound = new MaximalNullifiedBound();
                break;
            default:
                this.nullifiedBound = new AverageNullifiedBound();
                break;
        }
    }

    @Override
    protected void initializeStream() {
        for (int i = 0; i < this.userSize; i++) {
            this.forwardImpactStreamList.add(new ForwardImpactStreamAbsorption());
            this.backwardHistoricalStreamList.add(new BackwardHistoricalStream());
        }
    }

    protected void updateImpactStreamUsageList() {
        for (int i = 0; i < this.userSize; i++) {
            ((ForwardImpactStreamAbsorption)this.forwardImpactStreamList.get(i)).updateStreamUsageAndRightBorder(this.publicationPrivacyBudgetList.get(i));
        }
    }

    @Override
    protected void setPublicationPrivacyBudgetList(List<Double> backwardBudgetList, List<Integer> backwardWindowSizeList) {
//        List<Double> minimalAbsorbBudgetList = new ArrayList<>(this.userSize);
        Double tempMinimalForwardBudget, tempValue, tempBackwardBudgetRemain, tempBackwardBudget;
        Integer tempBackwardWindowSize;
        ForwardImpactStream tempImpactStream;
        BackwardHistoricalStream tempBackwardStream;
        ImpactElementAbsorption tempImpactElement;
        Iterator<ImpactElement> tempIterator;
        this.publicationPrivacyBudgetList = new ArrayList<>();
        for (int i = 0; i < this.userSize; i++) {
            tempImpactStream = this.forwardImpactStreamList.get(i);
            tempIterator = tempImpactStream.forwardImpactElementIterator();
            tempMinimalForwardBudget = Double.MAX_VALUE;
            while (tempIterator.hasNext()) {
                tempImpactElement = (ImpactElementAbsorption) tempIterator.next();
                tempValue = (this.currentTime - tempImpactElement.getRightBorder()) * tempImpactElement.getTotalPrivacyBudget() / (2 * tempImpactElement.getWindowSize());
                tempMinimalForwardBudget = Math.min(tempMinimalForwardBudget, tempValue);
            }
            tempBackwardBudget = backwardBudgetList.get(i);
            tempBackwardStream = this.backwardHistoricalStreamList.get(i);
            tempBackwardWindowSize = backwardWindowSizeList.get(i);
            tempBackwardBudgetRemain = tempBackwardBudget / 2 - tempBackwardStream.getHistoricalPublicationBudgetSum(tempBackwardWindowSize-1);
            this.publicationPrivacyBudgetList.add(Math.min(tempMinimalForwardBudget, tempBackwardBudgetRemain));
        }
    }


    private void setMaxPublicationBudgetUsageSumToNullifiedList() {
        this.nullifiedTimeStampList = new ArrayList<>(this.userSize);
        Double tempNullified, tempMaxNullified;
        ImpactElementAbsorption element;
        ForwardImpactStream forwardImpactStream;
        Iterator<ImpactElement> impactElementIterator;
        for (int i = 0; i < this.userSize; i++) {
            forwardImpactStream = this.forwardImpactStreamList.get(i);
            impactElementIterator = forwardImpactStream.forwardImpactElementIterator();
//            tempMaxNullified = 0D;
            tempMaxNullified = -1D;
            while (impactElementIterator.hasNext()) {
                element = (ImpactElementAbsorption) impactElementIterator.next();
                tempNullified = element.getRightBorder();
                tempMaxNullified = Math.max(tempMaxNullified, tempNullified);
            }
            this.nullifiedTimeStampList.add(tempMaxNullified);
        }
    }


    @Override
    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> backwardBudgetList, List<Integer> backwardWindowSizeList, List<Double> forwardBudgetList, List<Integer> forwardWindowSizeList) {
        ++this.currentTime;
        this.updateForwardImpactStreamList(forwardBudgetList, forwardWindowSizeList);
        // M_{t,1}
        Double dissimilarity = mechanismPartA(nextDataElementList, backwardBudgetList, backwardWindowSizeList);
        // M_{t,2}
        Double maxPublicationBudgetSum;
        this.setMaxPublicationBudgetUsageSumToNullifiedList();
//        double maximalNullified = ListUtils.getMaximalValue(this.nullifiedTimeStampList, 0D);
        double nullifiedTimestampBound = this.nullifiedBound.getNullifiedBound(this.nullifiedTimeStampList);
        if (this.currentTime <= nullifiedTimestampBound) {
            return false;
        }
        this.setPublicationPrivacyBudgetList(backwardBudgetList, backwardWindowSizeList);
        boolean updateStatus = mechanismPartB(nextDataElementList, backwardBudgetList, backwardWindowSizeList, dissimilarity);

        this.updateBackwardHistoricalStreamList();
        this.updateImpactStreamUsageList();
        return updateStatus;
    }
}
