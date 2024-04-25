package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.collection.ListUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PersonalizedBudgetDistribution extends OptimalFixedWindowSizeMechanism{

    private List<Deque<Double>> historicalBudgetList;

    public PersonalizedBudgetDistribution(List<String> dataTypeList, List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        super(dataTypeList, privacyBudgetList, windowSizeList);
        this.historicalBudgetList = new ArrayList<>();
        Deque<Double> tempDeque;
        List<Double> tempDoubleList;
        for (int i = 0; i < privacyBudgetList.size(); i++) {
            tempDoubleList = ListUtils.generateListWithFixedElement(0D, this.windowSizeList.get(i));
            tempDeque = new LinkedList<>(tempDoubleList);
            this.historicalBudgetList.add(tempDeque);
        }
    }


    @Override
    protected List<Double> getCalculationPrivacyBudgetList() {
        int userSize = this.privacyBudgetList.size();
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < userSize; i++) {
            result.add(this.privacyBudgetList.get(i) / (this.windowSizeList.get(i) * 2));
        }
        return result;
    }

    @Override
    protected List<Double> getPublicationPrivacyBudgetList() {
        int userSize = this.privacyBudgetList.size();
        return null;
    }
}
