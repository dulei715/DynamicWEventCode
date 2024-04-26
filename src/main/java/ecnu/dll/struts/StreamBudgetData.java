package ecnu.dll.struts;

import java.util.List;

public class StreamBudgetData {
    protected Integer timeSlot;
    protected List<Double> budgetList;

    public StreamBudgetData(Integer timeSlot, List<Double> budgetList) {
        this.timeSlot = timeSlot;
        this.budgetList = budgetList;
    }

    public Integer getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Integer timeSlot) {
        this.timeSlot = timeSlot;
    }

    public List<Double> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<Double> budgetList) {
        this.budgetList = budgetList;
    }
}
