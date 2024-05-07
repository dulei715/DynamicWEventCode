package ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.special_tools;

import ecnu.dll.struts.ImpactElement;
import ecnu.dll.struts.ForwardImpactStream;

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
}
