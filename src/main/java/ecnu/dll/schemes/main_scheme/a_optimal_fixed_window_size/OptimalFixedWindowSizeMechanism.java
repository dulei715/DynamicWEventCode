package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size;

import java.util.List;

public abstract class OptimalFixedWindowSizeMechanism {

    protected List<Double> privacyBudgetList;
    protected List<Integer> windowSizeList;
    protected abstract List<Double> getCalculationPrivacyBudgetList();

    protected abstract List<Double> getPublicationPrivacyBudgetList();

    public OptimalFixedWindowSizeMechanism(List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        this.privacyBudgetList = privacyBudgetList;
        this.windowSizeList = windowSizeList;
    }

    public Double getPublicationResult() {
        return null;
    }

}
