package ecnu.dll.struts.non_personalized_struct;

public class MechanismDetailStruct {
    protected Double partAScale;
    protected MechanismPartBDetails mechanismPartBDetails;

    public MechanismDetailStruct(Double partAScale, MechanismPartBDetails mechanismPartBDetails) {
        this.partAScale = partAScale;
        this.mechanismPartBDetails = mechanismPartBDetails;
    }

    public Double getPartAScale() {
        return partAScale;
    }

    public MechanismPartBDetails getMechanismPartBDetails() {
        return mechanismPartBDetails;
    }

    @Override
    public String toString() {
        return "MechanismDetailStruct{" +
                "partAScale=" + partAScale +
                ", mechanismPartBDetails=" + mechanismPartBDetails +
                '}';
    }
}
