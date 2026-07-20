package ecnu.dll.struts.non_personalized_struct;

public class MechanismPartBDetails {
    protected boolean publicationStatus;
    protected boolean nonNullStatus;
    protected Double scale;

    public MechanismPartBDetails(boolean publicationStatus, boolean nonNullStatus, Double scale) {
        this.publicationStatus = publicationStatus;
        this.nonNullStatus = nonNullStatus;
        this.scale = scale;
    }

    public boolean getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(boolean publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public boolean getNonNullStatus() {
        return nonNullStatus;
    }

    public void setNonNullStatus(boolean nonNullStatus) {
        this.nonNullStatus = nonNullStatus;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "MechanismPartBDetails{" +
                "publicationStatus=" + publicationStatus +
                ", nonNullStatus=" + nonNullStatus +
                ", scale=" + scale +
                '}';
    }
}
