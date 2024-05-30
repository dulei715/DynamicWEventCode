package ecnu.dll.struts.test;

public class BackwardForwardData {
    private Double backwardBudget;
    private Integer backwardWindowSize;
    private Double forwardBudget;
    private Integer forwardWindowSize;

    public BackwardForwardData(Double backwardBudget, Integer backwardWindowSize, Double forwardBudget, Integer forwardWindowSize) {
        this.backwardBudget = backwardBudget;
        this.backwardWindowSize = backwardWindowSize;
        this.forwardBudget = forwardBudget;
        this.forwardWindowSize = forwardWindowSize;
    }

    public Double getBackwardBudget() {
        return backwardBudget;
    }

    public Integer getBackwardWindowSize() {
        return backwardWindowSize;
    }

    public Double getForwardBudget() {
        return forwardBudget;
    }

    public Integer getForwardWindowSize() {
        return forwardWindowSize;
    }
}
