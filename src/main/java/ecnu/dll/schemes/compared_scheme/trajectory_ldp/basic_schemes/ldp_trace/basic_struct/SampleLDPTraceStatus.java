package ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct.TrajectoryEstimationStruct;

public class SampleLDPTraceStatus {
    public Integer trajectoryLength;
    public Integer currentPointIndex;
    public TwoDimensionalIntegerPoint currentPoint;
    public TrajectoryEstimationStruct trajectoryEstimationStruct;
    public Integer colSize;
    public Integer rowSize;
    public Double alpha;
    public Double bata;
}
