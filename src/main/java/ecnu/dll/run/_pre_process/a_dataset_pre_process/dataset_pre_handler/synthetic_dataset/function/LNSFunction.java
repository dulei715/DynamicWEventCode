package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function;

import cn.edu.dll.basic.NumberUtil;
import cn.edu.dll.differential_privacy.noise.GaussUtils;
import org.apache.commons.math3.analysis.function.Gaussian;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LNSFunction implements DataGenerationFunction<Double>{
    private Double initializedValue;
    private Double gaussianAverage;
    private Double gaussianStandardVariance;
    private GaussUtils gaussUtils;
    private Double currentValue;
    private int precision = 2;

    public LNSFunction(Double initializedValue, Double gaussianAverage, Double gaussianStandardVariance) {
        this.initializedValue = initializedValue;
        this.gaussianAverage = gaussianAverage;
        this.gaussianStandardVariance = gaussianStandardVariance;
        gaussUtils = new GaussUtils();
        this.currentValue = this.initializedValue;
    }



    @Override
    public List<Double> nextProbability(int timeSize) {
        double[] gaussNoise = this.gaussUtils.getGaussNoise(this.gaussianAverage, this.gaussianStandardVariance, timeSize);
        List<Double> result = new ArrayList<>(timeSize);
        for (int i = 0; i < gaussNoise.length; i++) {
            this.currentValue = NumberUtil.roundFormat(this.currentValue + gaussNoise[i], this.precision);
            result.add(this.currentValue);
        }
        return result;
    }

    @Override
    public List<Double> nextProbability() {
        return nextProbability(1);
    }

    @Override
    public Double getInitializedValue() {
        return this.initializedValue;
    }

    @Override
    public Double getCurrentValue() {
        return this.currentValue;
    }

    @Override
    public void reset() {
        this.currentValue = this.initializedValue;
    }

    @Override
    public String getName() {
        return "lns";
    }
}
