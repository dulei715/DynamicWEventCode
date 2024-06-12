package ecnu.dll.schemes.compared_scheme.w_event;

import cn.edu.dll.collection.ListUtils;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BudgetDistribution extends WEventMechanism {
    private Deque<Double> historicalBudgetQueue;

    public BudgetDistribution(List<String> dataTypeList, Double privacyBudget, Integer windowSize) {
        super(dataTypeList, privacyBudget, windowSize);
        List<Double> tempDoubleList = ListUtils.generateListWithFixedElement(0D, this.windowSize);
        this.historicalBudgetQueue = new LinkedList<>(tempDoubleList);
    }

    private void updateHistoricalBudgetQueue(Double newBudget) {
        this.historicalBudgetQueue.addLast(newBudget);
        this.historicalBudgetQueue.poll();
    }


    @Override
    protected void setPublicationPrivacyBudget() {
        Double windowBudgetSum = 0D;
        for (Double tempBudget : this.historicalBudgetQueue) {
            windowBudgetSum += tempBudget;
        }
        Double tempRemainBudget = this.privacyBudget / 2 - windowBudgetSum;
        this.publicationPrivacyBudget = tempRemainBudget / 2;
    }

    @Override
    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
        boolean updateStatus = super.updateNextPublicationResult(nextDataElementList);
        if (updateStatus) {
            this.updateHistoricalBudgetQueue(this.publicationPrivacyBudget);
        } else {
            this.updateHistoricalBudgetQueue(0.0);
        }
        return updateStatus;
    }

    @Override
    public String getSimpleName() {
        return "BD";
    }
}
