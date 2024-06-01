package ecnu.dll.struts.direct_stream;

public class ImpactElementAbsorption extends ImpactElement{
    protected Double publicationBudgetUsage;
    protected Double rightBorder;
    public ImpactElementAbsorption(Integer timeSlot, Double totalPrivacyBudget, Integer windowSize) {
        super(timeSlot, totalPrivacyBudget, windowSize);
        this.publicationBudgetUsage = 0D;
        this.rightBorder = timeSlot - 1D;
    }

    public Double getPublicationBudgetUsage() {
        return publicationBudgetUsage;
    }
    public void updatePublicationBudgetUsage(Double budget) {
        this.publicationBudgetUsage += budget;
        this.rightBorder = Math.ceil(this.publicationBudgetUsage / (this.totalPrivacyBudget / (2*this.windowSize))) + this.timeSlot - 1;
    }
    public Double getRightBorder() {
        return this.rightBorder;
    }
}
