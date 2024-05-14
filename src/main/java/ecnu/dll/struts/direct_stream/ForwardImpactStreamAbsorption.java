package ecnu.dll.struts.direct_stream;

public class ForwardImpactStreamAbsorption extends ForwardImpactStream{
    @Override
    public void addElement(Double totalPrivacyBudget, Integer windowSize) {
        ++this.currentTime;
        this.impactStream.put(this.currentTime, new ImpactElementAbsorption(this.currentTime, totalPrivacyBudget, windowSize));
        updateStream();
    }

    public void updateStreamUsage(Double currentPublicationBudgetUsage) {
        for (ImpactElement impactElement : impactStream.values()) {
            ((ImpactElementAbsorption) impactElement).updatePublicationBudgetUsage(currentPublicationBudgetUsage);
        }
    }
}
