package ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct;

public class TrajectoryEstimationStruct {
    public Double[] trajectoryLengthEstimation = null;
    public Double[] neighboringEstimation = null;
    public Double[] startCellEstimation = null;
    public Double[] endCellEstimation = null;

    public TrajectoryEstimationStruct(Double[] trajectoryLengthEstimation, Double[] neighboringEstimation, Double[] startCellEstimation, Double[] endCellEstimation) {
        this.trajectoryLengthEstimation = trajectoryLengthEstimation;
        this.neighboringEstimation = neighboringEstimation;
        this.startCellEstimation = startCellEstimation;
        this.endCellEstimation = endCellEstimation;
    }

}
