package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function;


import java.util.ArrayList;
import java.util.List;

public class SinFunction implements DataGenerationFunction<Double> {
    private Integer currentTime = 0;
    private Double parameterA;
    private Double parameterOmega;
    private Double parameterH;

    public SinFunction(Double parameterA, Double parameterOmega, Double parameterH) {
        this.parameterA = parameterA;
        this.parameterOmega = parameterOmega;
        this.parameterH = parameterH;
    }


    @Override
    public List<Double> nextProbability(int size) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ++currentTime;
            result.add(getCurrentValue());
        }
        return result;
    }

    @Override
    public List<Double> nextProbability() {
        return nextProbability(1);
    }

    @Override
    public Double getInitializedValue() {
        return parameterH;
    }

    @Override
    public Double getCurrentValue() {
        return parameterA * Math.sin(parameterOmega * currentTime) + parameterH;
    }

    @Override
    public void reset() {
        this.currentTime = 0;
    }

    @Override
    public String getName() {
        return "sin";
    }
}
