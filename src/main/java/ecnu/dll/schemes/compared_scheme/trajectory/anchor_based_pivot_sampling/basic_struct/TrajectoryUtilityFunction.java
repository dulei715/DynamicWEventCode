package ecnu.dll.schemes.compared_scheme.trajectory.anchor_based_pivot_sampling.basic_struct;

import cn.edu.dll.differential_privacy.cdp.exponential_mechanism.utility.UtilityFunction;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePointUtils;

public class TrajectoryUtilityFunction implements UtilityFunction<TwoDimensionalDoublePoint, TwoDimensionalDoublePoint> {
    @Override
    public Double getUtilityValue(TwoDimensionalDoublePoint point, TwoDimensionalDoublePoint point2) {
        return -TwoDimensionalDoublePointUtils.get2NormDistance(point, point2);
    }
}
