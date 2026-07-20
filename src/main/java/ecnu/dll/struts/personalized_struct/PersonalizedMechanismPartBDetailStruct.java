package ecnu.dll.struts.personalized_struct;

public class PersonalizedMechanismPartBDetailStruct {
    protected boolean newPublicationStatus;
    protected boolean nonNullStatus;
    protected Double epsilon;
    protected Double finalError;
    protected PersonalizedMechanismErrorDetailStruct errorDetails;

    public PersonalizedMechanismPartBDetailStruct(boolean newPublicationStatus, boolean nonNullStatus, Double epsilon, Double finalError, PersonalizedMechanismErrorDetailStruct error) {
        this.newPublicationStatus = newPublicationStatus;
        this.nonNullStatus = nonNullStatus;
        this.epsilon = epsilon;
        this.finalError = finalError;
        this.errorDetails = error;
    }

    public boolean getNewPublicationStatus() {
        return newPublicationStatus;
    }

    public boolean getNonNullStatus() {
        return nonNullStatus;
    }

    public Double getEpsilon() {
        return epsilon;
    }

    public Double getFinalError() {
        return finalError;
    }

    public PersonalizedMechanismErrorDetailStruct getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "PersonalizedMechanismPartBDetailStruct{" +
                "newPublicationStatus=" + newPublicationStatus +
                ", nonNullStatus=" + nonNullStatus +
                ", epsilon=" + epsilon +
                ", finalError=" + finalError +
                ", errorDetails=" + errorDetails +
                '}';
    }

    public void setNonNullStatus(boolean nonNullStatus) {
        this.nonNullStatus = nonNullStatus;
    }

    public static void main(String[] args) {
        Double finalError = 1.0;
        PersonalizedMechanismErrorDetailStruct errorDetails = new PersonalizedMechanismErrorDetailStruct(2.0, 1.1, 0.9, 3.0);
        PersonalizedMechanismPartBDetailStruct personalizedMechanismPartBDetailStruct = new PersonalizedMechanismPartBDetailStruct(true, true, 0.5, finalError, errorDetails);
        System.out.println(personalizedMechanismPartBDetailStruct);
    }
}
