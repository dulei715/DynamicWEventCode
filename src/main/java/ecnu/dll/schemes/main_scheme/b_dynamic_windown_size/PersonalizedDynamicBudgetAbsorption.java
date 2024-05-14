package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size;

import cn.edu.dll.collection.ListUtils;
import ecnu.dll.struts.direct_stream.*;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersonalizedDynamicBudgetAbsorption extends DynamicWindowSizeMechanism{

    private List<Double> nullifiedTimeStampList;

    public PersonalizedDynamicBudgetAbsorption(List<String> dataTypeList, int userSize) {
        super(dataTypeList, userSize);
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
            ((ForwardImpactStreamAbsorption)this.forwardImpactStreamList.get(i)).updateStreamUsage(this.publicationPrivacyBudgetList.get(i));
        }
    }

    @Override
    protected void setPublicationPrivacyBudgetList(List<Double> backwardBudgetList, List<Integer> backwardWindowSizeList) {
        for (int i = 0; i < this.userSize; i++) {

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
            tempMaxNullified = 0D;
            while (impactElementIterator.hasNext()) {
                element = (ImpactElementAbsorption) impactElementIterator.next();
                tempNullified = element.getPublicationBudgetUsage() / (element.getTotalPrivacyBudget() / (2 * element.getWindowSize())) - 1 + element.getTimeSlot();
                tempMaxNullified = Math.max(tempMaxNullified, tempNullified);
            }
            this.nullifiedTimeStampList.add(tempMaxNullified);
        }
    }

    @Override
    protected boolean mechanismPartB(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> backwardBudgetList, List<Integer> backwardWindowSizeList, Double dissimilarity) {
        
    }

    @Override
    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> backwardBudgetList, List<Integer> backwardWindowSizeList, List<Double> forwardBudgetList, List<Integer> forwardWindowSizeList) {
        ++this.currentTime;
        this.updateForwardImpactStreamList(forwardBudgetList, forwardWindowSizeList);
        // M_{t,1}
        Double dissimilarity = mechanismPartA(nextDataElementList, backwardBudgetList, backwardWindowSizeList);
        // M_{t,2}
        Double maxPublicationBudgetSum;
        setMaxPublicationBudgetUsageSumToNullifiedList();
        double averageNullified = ListUtils.sum(this.nullifiedTimeStampList) / this.userSize;
        if (this.currentTime <= averageNullified) {
            return false;
        }
        this.setPublicationPrivacyBudgetList(backwardBudgetList, backwardWindowSizeList);
        boolean updateStatus = mechanismPartB(nextDataElementList, backwardBudgetList, backwardWindowSizeList, dissimilarity);

        this.updateBackwardHistoricalStreamList();
        this.updateImpactStreamUsageList();
        return updateStatus;
    }
}
