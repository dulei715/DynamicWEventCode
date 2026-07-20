package ecnu.dll.struts.personalized_struct;

public class PersonalizedMechanismDetailStruct {
    protected PersonalizedMechanismPartADetailStruct personalizedMechanismPartADetailStruct;
    protected PersonalizedMechanismPartBDetailStruct personalizedMechanismPartBDetailStruct;

    public PersonalizedMechanismDetailStruct(PersonalizedMechanismPartADetailStruct personalizedMechanismPartADetailStruct, PersonalizedMechanismPartBDetailStruct personalizedMechanismPartBDetailStruct) {
        this.personalizedMechanismPartADetailStruct = personalizedMechanismPartADetailStruct;
        this.personalizedMechanismPartBDetailStruct = personalizedMechanismPartBDetailStruct;
    }

    public PersonalizedMechanismPartADetailStruct getMechanismPartADetailStruct() {
        return personalizedMechanismPartADetailStruct;
    }

    public PersonalizedMechanismPartBDetailStruct getMechanismPartBDetailStruct() {
        return personalizedMechanismPartBDetailStruct;
    }

    @Override
    public String toString() {
        return "PersonalizedMechanismDetailStruct{" +
                "personalizedMechanismPartADetailStruct=" + personalizedMechanismPartADetailStruct +
                ", personalizedMechanismPartBDetailStruct=" + personalizedMechanismPartBDetailStruct +
                '}';
    }
}
