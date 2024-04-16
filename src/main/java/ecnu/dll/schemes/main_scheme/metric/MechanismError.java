package ecnu.dll.schemes.main_scheme.metric;

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

    public static Double getSampleError(Integer itemSize, TreeMap<Double, Double> epsilonRatioMap, Double epsilonTheta) {
        Double totalRatio = MapUtils.getValueSum(epsilonRatioMap);
        if (Math.abs(totalRatio - 1) > Math.pow(10, -6)) {
            throw new RuntimeException("The sum of epsilonRatioMap's value is not equal to 1!");
        }
        Double result = 0D, countVar = 0D, bias = 0D, tempEpsilon, tempRatio, tempProbability, tempValue;
        for (Map.Entry<Double, Double> entry : epsilonRatioMap.entrySet()) {
            tempEpsilon = entry.getKey();
            if (tempEpsilon >= epsilonTheta) {
                break;
            }
            tempProbability = (Math.exp(tempEpsilon) - 1) / (Math.exp(epsilonTheta) - 1);
            tempRatio = entry.getValue();
            tempValue = itemSize * tempRatio * (1 - tempProbability);
            countVar += tempValue * tempProbability;
            bias += tempValue;
        }
        result = countVar + bias * bias;
        return  result;
    }

    public static Double getDPError(Double epsilon, Double sensitivity) {
        return 2.0 * Math.pow(sensitivity / epsilon, 2);
    }

    public static Double getDPError(Double epsilon) {
        return getDPError(epsilon, 1.0);
    }

}
