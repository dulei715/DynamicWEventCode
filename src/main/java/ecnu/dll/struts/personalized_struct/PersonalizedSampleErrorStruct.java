package ecnu.dll.struts.personalized_struct;

public class PersonalizedSampleErrorStruct {
    protected Double sampleError;
    protected Double countVar;
    protected Double biasSquare;

    public PersonalizedSampleErrorStruct(Double sampleError, Double countVar, Double biasSquare) {
        this.sampleError = sampleError;
        this.countVar = countVar;
        this.biasSquare = biasSquare;
    }

    public Double getSampleError() {
        return sampleError;
    }

    public Double getCountVar() {
        return countVar;
    }

    public Double getBiasSquare() {
        return biasSquare;
    }

    @Override
    public String toString() {
        return "PersonalizedSampleErrorStruct{" +
                "sampleError=" + sampleError +
                ", countVar=" + countVar +
                ", biasSquare=" + biasSquare +
                '}';
    }
}
