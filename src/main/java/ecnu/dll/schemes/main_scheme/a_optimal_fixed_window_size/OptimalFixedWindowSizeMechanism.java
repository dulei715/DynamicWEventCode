package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size;

import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll.schemes._scheme_utils.MechanismErrorUtils;
import ecnu.dll.schemes._scheme_utils.PersonalizedDPTools;
import ecnu.dll.struts.StreamDataElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class OptimalFixedWindowSizeMechanism {

    protected int currentTime;

    protected List<StreamDataElement<Boolean>> currentDataElementList;
    protected List<Double> privacyBudgetList;
    protected List<Integer> windowSizeList;
    protected abstract List<Double> getCalculationPrivacyBudgetList();

    protected abstract List<Double> getPublicationPrivacyBudgetList();

    public void setParameters(List<StreamDataElement<Boolean>> currentDataElementList, List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        this.currentDataElementList = currentDataElementList;
        this.privacyBudgetList = privacyBudgetList;
        this.windowSizeList = windowSizeList;
    }



    public Double getNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList, List<Double> nextPrivacyBudgetList, List<Integer> nextWindowSizeList) {
        this.setParameters(nextDataElementList, nextPrivacyBudgetList, nextWindowSizeList);
        // M_{t,1}
        List<Double> calculationBudgetList = getCalculationPrivacyBudgetList();
        Map<Double, Integer> budgetCountMap = StatisticTool.countHistogramNumber(this.privacyBudgetList);
        Double[] minimalEpsilonAndError = MechanismErrorUtils.getMinimalEpsilonAndError((TreeMap<Double, Integer>) budgetCountMap);
        List<Integer> sampleIndexList = PersonalizedDPTools.sampleIndex(this.privacyBudgetList, minimalEpsilonAndError[0]);
        sampleIndexList.get()

        ++this.currentTime;
    }

}
