package ecnu.dll.struts;

import cn.edu.dll.map.MapUtils;

import java.util.Map;
import java.util.TreeMap;

public class MechanismError {
    protected Double sampleError = null;
    protected Double biasError = null;

    public MechanismError(Double sampleError, Double biasError) {
        this.sampleError = sampleError;
        this.biasError = biasError;
    }



}
