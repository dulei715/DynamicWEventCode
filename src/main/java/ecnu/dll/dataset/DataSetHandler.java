package ecnu.dll.dataset;

import cn.edu.dll.statistic.StatisticTool;
import org.apache.commons.math3.analysis.function.Gaussian;

import java.util.Random;

public class DataSetHandler {
    public static Double[] getLinearNormalSequence(Double startValue, Double noiseStandardVariance, int size) {
        Double[] result = new Double[size+1];
        result[0] = startValue;
        Random random = new Random();
        for (int i = 1; i <= size; i++) {
            result[i] = result[i-1] + random.nextGaussian()*noiseStandardVariance;
        }
        return result;
    }
}
