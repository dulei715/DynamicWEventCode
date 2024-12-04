package ecnu.dll.schemes.compared_scheme.trajectory.anchor_based_pivot_sampling.basic_struct;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.differential_privacy.cdp.exponential_mechanism.ExponentialMechanism;
import cn.edu.dll.differential_privacy.cdp.exponential_mechanism.utility.UtilityFunction;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;

import java.util.List;

public class TrajectoryExponentialMechanism extends ExponentialMechanism<TwoDimensionalDoublePoint, TwoDimensionalDoublePoint> {
    // 这里用欧氏距离替换原文中Haversine距离
    private static final UtilityFunction<TwoDimensionalDoublePoint, TwoDimensionalDoublePoint> utilityFunction = new TrajectoryUtilityFunction();
    public TrajectoryExponentialMechanism(Double epsilon, List<TwoDimensionalDoublePoint> dataList) {
        // 这里要求inputList和outputList是同一个
        super(utilityFunction, epsilon, dataList, dataList);
    }

    @Override
    protected void setDeltaU() {
        super.deltaU = -1.0;
        //inputList和outputList是同一个，因此只用记录一个长度即可
        int listSize = super.inputList.size();
        TwoDimensionalDoublePoint pointA, pointB;
        double tempUtilityA, tempUtilityB;
        for (TwoDimensionalDoublePoint inputElement : super.inputList) {
            for (int j = 0; j < listSize; ++j) {
                pointA = super.outputList.get(j);
                tempUtilityA = utilityFunction.getUtilityValue(inputElement, pointA);
                for (int k = j + 1; k < listSize; ++k) {
                    pointB = super.outputList.get(k);
                    tempUtilityB = utilityFunction.getUtilityValue(inputElement, pointB);
                    super.deltaU = Math.max(super.deltaU, Math.abs(tempUtilityA - tempUtilityB));
                }
            }
        }
    }

    public List<TwoDimensionalDoublePoint> getInputDomain() {
        return this.inputList;
    }

    public List<TwoDimensionalDoublePoint> getOutputDomain() {
        return this.outputList;
    }

    @Override
    public TwoDimensionalDoublePoint disturb(TwoDimensionalDoublePoint inputElement) {
        if (this.inputList.size() == 0) {
            throw new RuntimeException("The input domain is zero!");
        }
        if (!this.inputList.contains(inputElement)) {
            return this.inputList.get(RandomUtil.getRandomInteger(0, this.inputList.size() - 1));
        }
        return super.disturb(inputElement);
    }

    @Override
    protected void setProbabilityConstant() {
        super.probabilityConstant = Math.exp(super.epsilon / (2 * super.deltaU));
    }
}
