package ecnu.dll.struts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ForwardImpactStream {
    private Integer currentTime;
    private HashMap<Integer, ForwardImpactElement> impactStream = null;

    public ForwardImpactStream() {
        this.impactStream = new HashMap<>();
        this.currentTime = -1;
    }

    public void resetStream() {
        this.impactStream.clear();
        this.currentTime = -1;
    }

    private void updateStream() {
        Iterator<Map.Entry<Integer, ForwardImpactElement>> elementIterator = this.impactStream.entrySet().iterator();
        ForwardImpactElement element;
        while (elementIterator.hasNext()) {
            element = elementIterator.next().getValue();
            if (element.getTimeSlot() + element.getWindowSize() - 1 < this.currentTime) {
                elementIterator.remove();
            }
        }
    }

    public void addElement(Double totalPrivacyBudget, Integer windowSize) {
        ++this.currentTime;
        this.impactStream.put(this.currentTime, new ForwardImpactElement(this.currentTime, totalPrivacyBudget, windowSize));
        updateStream();
    }

    public Double getHistoricalEpsilon(Integer timeSlot) {
        ForwardImpactElement element = this.impactStream.get(timeSlot);
        if (element == null) {
            return null;
        }
        return element.getTotalPrivacyBudget();
    }

    public Integer getHistoricalWindowSize(Integer timeSlot) {
        ForwardImpactElement element = this.impactStream.get(timeSlot);
        if (element == null) {
            return null;
        }
        return element.getWindowSize();
    }


}
