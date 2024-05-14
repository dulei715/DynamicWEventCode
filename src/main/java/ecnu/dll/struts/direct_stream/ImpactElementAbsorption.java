package ecnu.dll.struts.direct_stream;

public class ImpactElementAbsorption extends ImpactElement{
    protected Double publicationBudgetUsage;
    public ImpactElementAbsorption(Integer timeSlot, Double totalPrivacyBudget, Integer windowSize) {
        super(timeSlot, totalPrivacyBudget, windowSize);
        this.publicationBudgetUsage = 0D;
    }

    public Double getPublicationBudgetUsage() {
        return publicationBudgetUsage;
    }
    public void updatePublicationBudgetUsage(Double budget) {
        this.publicationBudgetUsage += budget;
    }
}
