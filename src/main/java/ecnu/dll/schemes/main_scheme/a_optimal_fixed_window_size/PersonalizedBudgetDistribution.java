package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PersonalizedBudgetDistribution extends OptimalFixedWindowSizeMechanism{

    private List<Deque<Double>> publicationHistoricalBudgetList;

    private String getWindowStatusString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Deque<Double> queue : publicationHistoricalBudgetList) {
            stringBuilder.append(StringUtil.join(", ", queue));
            stringBuilder.append(ConstantValues.LINE_SPLIT);
        }
        return stringBuilder.toString();
    }

    public PersonalizedBudgetDistribution(List<String> dataTypeList, List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        super(dataTypeList, privacyBudgetList, windowSizeList);
        this.publicationHistoricalBudgetList = new ArrayList<>();
        Deque<Double> tempDeque;
        List<Double> tempDoubleList;
        for (int i = 0; i < privacyBudgetList.size(); i++) {
            tempDoubleList = ListUtils.generateListWithFixedElement(0D, this.windowSizeList.get(i));
            tempDeque = new LinkedList<>(tempDoubleList);
            this.publicationHistoricalBudgetList.add(tempDeque);
        }
    }

    private void updateHistoricalBudgetList(List<Double> currentPublicationEpsilonList) {
        Deque<Double> tempQueue;
        Double tempEpsilon;
        for (int i = 0; i < this.publicationHistoricalBudgetList.size(); i++) {
            tempEpsilon = currentPublicationEpsilonList.get(i);
            tempQueue = this.publicationHistoricalBudgetList.get(i);
            tempQueue.addLast(tempEpsilon);
            tempQueue.poll();
        }
    }
    private void updateHistoricalBudgetList() {
        Deque<Double> tempQueue;
        Double tempEpsilon;
        for (int i = 0; i < this.publicationHistoricalBudgetList.size(); i++) {
            tempQueue = this.publicationHistoricalBudgetList.get(i);
            tempQueue.addLast(0.0);
            tempQueue.poll();
        }
    }


    private Double getBudgetSumInWindow(int budgetIndex) {
        Deque<Double> tempQueue = this.publicationHistoricalBudgetList.get(budgetIndex);
        Double result = 0D;
        for (Double tempBudget : tempQueue) {
            result += tempBudget;
        }
        return result;
    }


    @Override
    protected void setCalculationPrivacyBudgetList() {
        int userSize = this.privacyBudgetList.size();
        this.calculationPrivacyBudgetList = new ArrayList<>();
        for (int i = 0; i < userSize; i++) {
            this.calculationPrivacyBudgetList.add(this.privacyBudgetList.get(i) / (this.windowSizeList.get(i) * 2));
        }
    }

    @Override
    protected void setPublicationPrivacyBudgetList() {
        int userSize = this.privacyBudgetList.size();
        this.publicationPrivacyBudgetList = new ArrayList<>();
        Double tempRemainBudget;
        for (int i = 0; i < userSize; i++) {
            tempRemainBudget = this.privacyBudgetList.get(i) / 2 - getBudgetSumInWindow(i);
            this.publicationPrivacyBudgetList.add(tempRemainBudget / 2);
        }
    }

    @Override
    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
        boolean updateStatus = super.updateNextPublicationResult(nextDataElementList);
        if (updateStatus) {
            updateHistoricalBudgetList(this.publicationPrivacyBudgetList);
        } else {
            updateHistoricalBudgetList();
        }
//        System.out.println("window status start...");
//        System.out.println(getWindowStatusString());
//        System.out.println("window status end...");
        return updateStatus;
    }
}
