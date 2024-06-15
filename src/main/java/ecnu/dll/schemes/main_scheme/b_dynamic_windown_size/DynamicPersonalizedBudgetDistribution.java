package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size;

import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.special_tools.ForwardImpactStreamTools;
import ecnu.dll.struts.direct_stream.BackwardHistoricalStream;
import ecnu.dll.struts.direct_stream.ForwardImpactStream;

import java.util.ArrayList;
import java.util.List;

public class DynamicPersonalizedBudgetDistribution extends DynamicPersonalizedWindowSizeMechanism {

    public DynamicPersonalizedBudgetDistribution(List<String> dataTypeList, Integer userSize) {
        super(dataTypeList, userSize);
    }

    @Override
    protected void setPublicationPrivacyBudgetList(List<Double> backwardBudgetList,
                                                   List<Integer> backwardWindowSizeList) {
        Double tempBackwardBudget, tempMinimalCandidateForwardBudget, tempMinimalCandidateBackwardBudget;
        Integer tempBackwardWindowSize;
        ForwardImpactStream tempForwardStream;
        BackwardHistoricalStream tempBackwardStream;

        this.publicationPrivacyBudgetList = new ArrayList<>();
        for (int userID = 0; userID < this.userSize; ++userID) {
            tempForwardStream = this.forwardImpactStreamList.get(userID);
            tempBackwardStream = this.backwardHistoricalStreamList.get(userID);
            tempBackwardBudget = backwardBudgetList.get(userID);
            tempBackwardWindowSize = backwardWindowSizeList.get(userID);

            tempMinimalCandidateForwardBudget = 0.5*ForwardImpactStreamTools.getMinimalForwardRemainPublicationBudget(tempForwardStream, tempBackwardStream);
            tempMinimalCandidateBackwardBudget = (tempBackwardBudget / 2 - tempBackwardStream.getHistoricalPublicationBudgetSum(tempBackwardWindowSize-1));

            this.publicationPrivacyBudgetList.add(Math.min(tempMinimalCandidateForwardBudget, tempMinimalCandidateBackwardBudget));
        }
    }

    @Override
    protected void setPublicationPrivacyBudgetListWithRemainBackwardBudget(List<Double> remainBackwardBudgetList) {
        Double tempRemainBackwardBudget, tempMinimalCandidateForwardBudget, tempMinimalCandidateBackwardBudget;
        ForwardImpactStream tempForwardStream;
        BackwardHistoricalStream tempBackwardStream;

        this.publicationPrivacyBudgetList = new ArrayList<>();
        for (int userID = 0; userID < this.userSize; ++userID) {
            tempForwardStream = this.forwardImpactStreamList.get(userID);
            tempBackwardStream = this.backwardHistoricalStreamList.get(userID);
            tempRemainBackwardBudget = remainBackwardBudgetList.get(userID);

            tempMinimalCandidateForwardBudget = 0.5*ForwardImpactStreamTools.getMinimalForwardRemainPublicationBudget(tempForwardStream, tempBackwardStream);
            tempMinimalCandidateBackwardBudget = tempRemainBackwardBudget / 2;

            this.publicationPrivacyBudgetList.add(Math.min(tempMinimalCandidateForwardBudget, tempMinimalCandidateBackwardBudget));
        }
    }

    @Override
    public String getSimpleName() {
        return "PDBD";
    }
}
