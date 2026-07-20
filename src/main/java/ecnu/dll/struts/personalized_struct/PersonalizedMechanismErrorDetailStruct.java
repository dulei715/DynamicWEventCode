package ecnu.dll.struts.personalized_struct;

public class PersonalizedMechanismErrorDetailStruct {
    protected PersonalizedSampleErrorStruct personalizedSampleErrorStruct = null;
    protected Double dpError = null;

    public PersonalizedMechanismErrorDetailStruct(PersonalizedSampleErrorStruct personalizedSampleErrorStruct, Double dpError) {
        this.personalizedSampleErrorStruct = personalizedSampleErrorStruct;
        this.dpError = dpError;
    }

    public PersonalizedMechanismErrorDetailStruct(Double sampleError, Double countVar, Double biasSquare, Double dpError) {
        this.personalizedSampleErrorStruct = new PersonalizedSampleErrorStruct(sampleError, countVar, biasSquare);
        this.dpError = dpError;
    }

    public PersonalizedSampleErrorStruct getSampleErrorStruct() {
        return personalizedSampleErrorStruct;
    }

    public Double getDpError() {
        return dpError;
    }

    @Override
    public String toString() {
        return "PersonalizedMechanismErrorDetailStruct{" +
                "personalizedSampleErrorStruct=" + personalizedSampleErrorStruct +
                ", dpError=" + dpError +
                '}';
    }
}
