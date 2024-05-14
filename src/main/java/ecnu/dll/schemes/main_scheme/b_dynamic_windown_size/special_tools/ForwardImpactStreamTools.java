package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.special_tools;

import ecnu.dll.struts.direct_stream.BackwardHistoricalStream;
import ecnu.dll.struts.direct_stream.ImpactElement;
import ecnu.dll.struts.direct_stream.ForwardImpactStream;

import java.util.Iterator;
import java.util.List;

public class ForwardImpactStreamTools {

    @Deprecated
    public static void batchAddElement(List<ForwardImpactStream> streamList, List<Double> budgetList, List<Integer> windowSizeList) {
        int size = streamList.size();
        ForwardImpactStream tempStream;
        Double tempPrivacyBudget;
        Integer tempWindowSize;
        for (int i = 0; i < size; i++) {
            tempStream = streamList.get(i);
            tempPrivacyBudget = budgetList.get(i);
            tempWindowSize = windowSizeList.get(i);
            tempStream.addElement(tempPrivacyBudget, tempWindowSize);
        }
    }

    public static Double getMinimalHalfAverageBudgetInWindow(ForwardImpactStream forwardImpactStream) {
        Double result = Double.MAX_VALUE;
        Iterator<ImpactElement> elementIterator = forwardImpactStream.forwardImpactElementIterator();
        ImpactElement tempElement;
        Double tempTotalBudget;
        Integer tempWindowSize;
        while (elementIterator.hasNext()) {
            tempElement = elementIterator.next();
            tempTotalBudget = tempElement.getTotalPrivacyBudget();
            tempWindowSize = tempElement.getWindowSize();
            result = Math.min(result, tempTotalBudget / 2 / tempWindowSize);
        }
        return result;
    }

    /**
     * 此处的forwardImpactStream是已经更新了的（加入了当前的forward信息），backwardHistoricalStream是还未更新的（虽然也不影响）
     * @param forwardImpactStream
     * @param backwardHistoricalStream
     * @return
     */
    public static Double getMinimalForwardRemainPublicationBudget(ForwardImpactStream forwardImpactStream, BackwardHistoricalStream backwardHistoricalStream) {
        Double forwardMinimalRemain = Double.MAX_VALUE;
        Iterator<ImpactElement> impactElementIterator = forwardImpactStream.forwardImpactElementIterator();
        ImpactElement tempElement;
        Double tempForwardTotalBudget, tempForwardRemain;
        Integer currentTime = forwardImpactStream.getCurrentTime(), tempImpactTime;
        while (impactElementIterator.hasNext()) {
            tempElement = impactElementIterator.next();
            tempImpactTime = tempElement.getTimeSlot();
            tempForwardTotalBudget = tempElement.getTotalPrivacyBudget();
            tempForwardRemain = tempForwardTotalBudget / 2 - backwardHistoricalStream.getHistoricalPublicationBudgetSum(currentTime - tempImpactTime);
            forwardMinimalRemain = Math.min(forwardMinimalRemain, tempForwardRemain);
        }
        return forwardMinimalRemain;
    }





























}
