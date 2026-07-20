package ecnu.dll.struts.personalized_struct;

public class PersonalizedMechanismPartADetailStruct {
    protected Double epsilon;
    protected Double dissimilarity;
    protected Double finalError;
    protected PersonalizedMechanismErrorDetailStruct errorDetails;

    public PersonalizedMechanismPartADetailStruct(Double epsilon, Double dissimilarity, Double finalError, PersonalizedMechanismErrorDetailStruct errorDetails) {
        this.epsilon = epsilon;
        this.dissimilarity = dissimilarity;
        this.finalError = finalError;
        this.errorDetails = errorDetails;
    }

    public Double getEpsilon() {
        return epsilon;
    }

    public Double getDissimilarity() {
        return dissimilarity;
    }

    public Double getFinalError() {
        return finalError;
    }

    public PersonalizedMechanismErrorDetailStruct getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "PersonalizedMechanismPartADetailStruct{" +
                "epsilon=" + epsilon +
                ", dissimilarity=" + dissimilarity +
                ", finalError=" + finalError +
                ", errorDetails=" + errorDetails +
                '}';
    }
}
