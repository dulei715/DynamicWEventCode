package ecnu.dll.struts;

import java.util.*;

public class ForwardImpactStream {
    private Integer currentTime;
    private HashMap<Integer, ImpactElement> impactStream;

    public ForwardImpactStream() {
        this.impactStream = new HashMap<>();
        this.currentTime = -1;
    }

    public void resetStream() {
        this.impactStream.clear();
        this.currentTime = -1;
    }

    private void updateStream() {
        Iterator<Map.Entry<Integer, ImpactElement>> elementIterator = this.impactStream.entrySet().iterator();
        ImpactElement element;
        while (elementIterator.hasNext()) {
            element = elementIterator.next().getValue();
            if (element.getTimeSlot() + element.getWindowSize() - 1 < this.currentTime) {
                elementIterator.remove();
            }
        }
    }

    public Integer getCurrentTime() {
        return currentTime;
    }

    public void addElement(Double totalPrivacyBudget, Integer windowSize) {
        ++this.currentTime;
        this.impactStream.put(this.currentTime, new ImpactElement(this.currentTime, totalPrivacyBudget, windowSize));
        updateStream();
    }

    public ImpactElement getHistoricalEffectiveElement(Integer timeSlot) {
        if (timeSlot > this.currentTime) {
            throw new RuntimeException("The time slot " + timeSlot + " has not achieved!");
        }
        return this.impactStream.get(timeSlot);
    }

    public Iterator<ImpactElement> forwardImpactElementIterator() {
        return this.impactStream.values().iterator();
    }

    public Double getHistoricalEpsilon(Integer timeSlot) {
        ImpactElement element = this.impactStream.get(timeSlot);
        if (element == null) {
            return null;
        }
        return element.getTotalPrivacyBudget();
    }


    public Integer getHistoricalWindowSize(Integer timeSlot) {
        ImpactElement element = this.impactStream.get(timeSlot);
        if (element == null) {
            return null;
        }
        return element.getWindowSize();
    }



    public void showStream() {
        TreeMap<Integer, ImpactElement> sortedMap = new TreeMap<>();
        sortedMap.putAll(this.impactStream);
        Collection<Integer> keyCollection = sortedMap.keySet();
        Collection<ImpactElement> valueCollection = sortedMap.values();
//        System.out.println("");
        for (int i = 0; i < keyCollection.size(); i++) {
            System.out.print("----------------");
        }
        System.out.println();
        for (Integer key : keyCollection) {
            System.out.printf("%d\t\t\t\t", key);
        }
        System.out.println();
        for (int i = 0; i < keyCollection.size(); i++) {
            System.out.print("----------------");
        }
        System.out.println();
        for (ImpactElement element : valueCollection) {
            System.out.printf("%.2f, %d\t\t\t", element.getTotalPrivacyBudget(), element.getWindowSize());
        }
        System.out.println();
    }




}
