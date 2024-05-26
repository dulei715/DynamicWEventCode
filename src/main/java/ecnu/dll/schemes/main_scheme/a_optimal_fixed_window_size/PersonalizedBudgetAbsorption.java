package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size;

import cn.edu.dll.collection.ListUtils;
import ecnu.dll.schemes._scheme_utils.nullified.AverageNullifiedBound;
import ecnu.dll.schemes._scheme_utils.nullified.MaximalNullifiedBound;
import ecnu.dll.schemes._scheme_utils.nullified.MinimalNullifiedBound;
import ecnu.dll.schemes._scheme_utils.nullified.NullifiedBound;
import ecnu.dll.struts.stream_data.StreamBudgetData;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.ArrayList;
import java.util.List;

public class PersonalizedBudgetAbsorption extends OptimalFixedWindowSizeMechanism{

    private StreamBudgetData lastTimePublicationBudgetData;
    private List<Double> nullifiedTimeStampList;

    private NullifiedBound nullifiedBound;

    public PersonalizedBudgetAbsorption(List<String> dataTypeList, List<Double> privacyBudgetList, List<Integer> windowSizeList, int nullifiedBoundType) {
        super(dataTypeList, privacyBudgetList, windowSizeList);
        int userSize = this.privacyBudgetList.size();
        List<Double> lastTimeBudgetList = new ArrayList<>(userSize);
        for (int i = 0; i < userSize; i++) {
            lastTimeBudgetList.add(this.privacyBudgetList.get(i) / (2*this.windowSizeList.get(i)));
        }
        this.lastTimePublicationBudgetData = new StreamBudgetData(-1, lastTimeBudgetList);
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
        Double tempAbsorbedTimestamp, tempPublicationBudget;
        Integer lastPublicationTimeSlot = this.lastTimePublicationBudgetData.getTimeSlot();
        Integer tempWindowSize;
        for (int i = 0; i < userSize; i++) {
            tempAbsorbedTimestamp = Math.max(this.currentTime - lastPublicationTimeSlot - this.nullifiedTimeStampList.get(i), 0);
            tempWindowSize = this.windowSizeList.get(i);
            tempPublicationBudget = this.privacyBudgetList.get(i) / (2*tempWindowSize) * Math.min(tempAbsorbedTimestamp, tempWindowSize);
            this.publicationPrivacyBudgetList.add(tempPublicationBudget);
        }
    }

    @Override
    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
//        return super.updateNextPublicationResult(nextDataElementList);
        ++this.currentTime;
        Double dissimilarity = mechanismPartA(nextDataElementList);
        this.nullifiedTimeStampList = new ArrayList<>();
        List<Double> lastTimePublicationBudgetList = this.lastTimePublicationBudgetData.getBudgetList();
        for (int i = 0; i < lastTimePublicationBudgetList.size(); i++) {
            this.nullifiedTimeStampList.add(lastTimePublicationBudgetList.get(i) / (this.privacyBudgetList.get(i) / (2*this.windowSizeList.get(i))) - 1);
        }
//        double averageNullifiedTimeStamp = ListUtils.sum(this.nullifiedTimeStampList) / this.nullifiedTimeStampList.size();
//        double nullifiedTimeStampBound = ListUtils.getMinimalValue(this.nullifiedTimeStampList);
        double nullifiedTimeStampBound = nullifiedBound.getNullifiedBound(this.nullifiedTimeStampList);
        if (this.currentTime - this.lastTimePublicationBudgetData.getTimeSlot() <= nullifiedTimeStampBound) {
            return false;
        }
        setPublicationPrivacyBudgetList();
        return mechanismPartB(nextDataElementList, dissimilarity);
    }
}
