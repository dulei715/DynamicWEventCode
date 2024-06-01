package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size;

import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.special_tools.ForwardImpactStreamTools;
import ecnu.dll.struts.direct_stream.BackwardHistoricalStream;
import ecnu.dll.struts.direct_stream.ForwardImpactStream;

import java.util.ArrayList;
import java.util.List;

public class PersonalizedDynamicBudgetDistribution extends DynamicWindowSizeMechanism{

    public PersonalizedDynamicBudgetDistribution(List<String> dataTypeList, int userSize) {
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

            tempMinimalCandidateForwardBudget = ForwardImpactStreamTools.getMinimalForwardRemainPublicationBudget(tempForwardStream, tempBackwardStream);
            tempMinimalCandidateBackwardBudget = (tempBackwardBudget / 2 - tempBackwardStream.getHistoricalPublicationBudgetSum(tempBackwardWindowSize-1));

            this.publicationPrivacyBudgetList.add(0.5*Math.min(tempMinimalCandidateForwardBudget, tempMinimalCandidateBackwardBudget));
        }
    }
}
