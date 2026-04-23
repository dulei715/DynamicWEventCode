package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function;

import cn.edu.dll.basic.NumberUtil;
import cn.edu.dll.differential_privacy.noise.GaussUtils;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.pair.PurePair;
import ecnu.dll._config.Constant;

import java.util.ArrayList;
import java.util.List;

public class TLNSFunction implements DataGenerationFunction<Double>{
    private Double initializedValue;
    private Double gaussianAverage;
    private Double gaussianStandardVariance;
    private GaussUtils gaussUtils;
    private Double currentValue;
    private int precision = 2;

    public static final Integer NoneClip = 0;
    public static final Integer UpperClip = 1;
    public static final Integer LowerClip = -1;

    public TLNSFunction(Double initializedValue, Double gaussianAverage, Double gaussianStandardVariance) {
        this.initializedValue = initializedValue;
        this.gaussianAverage = gaussianAverage;
        this.gaussianStandardVariance = gaussianStandardVariance;
        gaussUtils = new GaussUtils(Constant.DefaultSeed);
        this.currentValue = this.initializedValue;
    }



    @Override
    public List<Double> nextProbability(int timeSize) {
        double[] gaussNoise = this.gaussUtils.getGaussNoise(this.gaussianAverage, this.gaussianStandardVariance, timeSize);
        List<Double> result = new ArrayList<>(timeSize);
        for (int i = 0; i < gaussNoise.length; i++) {
            this.currentValue = NumberUtil.roundFormat(this.currentValue + gaussNoise[i], this.precision);
            this.currentValue = Math.max(this.currentValue, 0D);
            this.currentValue = Math.min(this.currentValue, 1D);
            result.add(this.currentValue);
        }
        return result;
    }

    public PurePair<List<Double>, List<Integer>> nextProbabilityWithClip(int timeSize) {
        double[] gaussNoise = this.gaussUtils.getGaussNoise(this.gaussianAverage, this.gaussianStandardVariance, timeSize);
        List<Double> result = new ArrayList<>(timeSize);
        List<Integer> clipList = new ArrayList<>(timeSize);
        for (int i = 0; i < gaussNoise.length; i++) {
            this.currentValue = NumberUtil.roundFormat(this.currentValue + gaussNoise[i], this.precision);
            if (this.currentValue > 1D) {
                clipList.add(UpperClip);
                this.currentValue = 1D;
            } else if (this.currentValue < 0D) {
                clipList.add(LowerClip);
                this.currentValue = 0D;
            } else {
                clipList.add(NoneClip);
            }
            result.add(this.currentValue);
        }
        return new PurePair<>(result, clipList);
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
        return "tlns";
    }
}
