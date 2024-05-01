package ecnu.dll.struts;

public class ForwardImpactElement implements Comparable<ForwardImpactElement> {
    private Integer timeSlot;
    private Double totalPrivacyBudget;
    private Integer windowSize;

    public ForwardImpactElement(Integer timeSlot, Double totalPrivacyBudget, Integer windowSize) {
        this.timeSlot = timeSlot;
        this.totalPrivacyBudget = totalPrivacyBudget;
        this.windowSize = windowSize;
    }

    public Integer getTimeSlot() {
        return timeSlot;
    }

    public Double getTotalPrivacyBudget() {
        return totalPrivacyBudget;
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    @Override
    public int compareTo(ForwardImpactElement element) {
        return this.timeSlot - element.timeSlot;
    }
}
