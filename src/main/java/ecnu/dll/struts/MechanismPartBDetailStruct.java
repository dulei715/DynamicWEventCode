package ecnu.dll.struts;

import java.util.Arrays;

public class MechanismPartBDetailStruct {
    protected boolean status;
    protected Double[] errorArray;

    public MechanismPartBDetailStruct(boolean status, Double[] errorArray) {
        this.status = status;
        this.errorArray = errorArray;
    }

    public boolean getStatus() {
        return status;
    }

    public Double[] getErrorArray() {
        return errorArray;
    }

    @Override
    public String toString() {
        return "MechanismPartBDetailStruct{" +
                "status=" + status +
                ", errorArray=" + Arrays.toString(errorArray) +
                '}';
    }

    public static void main(String[] args) {
        Double[] errorArray = {1.0, 2.0, 3.0};
        MechanismPartBDetailStruct mechanismPartBDetailStruct = new MechanismPartBDetailStruct(true, errorArray);
        System.out.println(mechanismPartBDetailStruct);
    }
}
