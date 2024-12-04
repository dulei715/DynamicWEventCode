package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.TrajectoryFO;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils.AbsolutePositionOneHotUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils.CellNeighboringOneHotUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.TrajectoryEstimationStruct;

import java.util.ArrayList;
import java.util.List;

public class LDPTrace {
    private int rowSize;
    private int colSize;
    private Double totalPrivacyBudget;
    private Integer maxTravelDistance;
    private double alpha = Constant.LDPTraceAlpha;
    private double beta = Constant.LDPTraceBeta;

    private TrajectoryFO trajectoryFO;

    // 这个参数在调用trajectorySynthesis前完成就行
    private TrajectoryEstimationStruct trajectoryEstimationStruct;

    public LDPTrace(int gridRowSize, int gridColSize, Double totalPrivacyBudget, Integer maxTravelDistance) {
        this.rowSize = gridRowSize;
        this.colSize = gridColSize;
        this.totalPrivacyBudget = totalPrivacyBudget;
        this.maxTravelDistance = maxTravelDistance;
        this.trajectoryFO = new TrajectoryFO(totalPrivacyBudget, maxTravelDistance, gridRowSize, gridColSize);
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public Double getTotalPrivacyBudget() {
        return totalPrivacyBudget;
    }

    public Integer getMaxTravelDistance() {
        return maxTravelDistance;
    }

    public TrajectoryFO getTrajectoryFO() {
        return trajectoryFO;
    }

    protected static int sampleLength(Double[] lengthEstimationData) {
        Integer index = RandomUtil.getRandomIndexGivenCountPoint(lengthEstimationData);
        return index + 1;
    }

    protected TwoDimensionalIntegerPoint sampleStartCell(Double[] startEstimationData) {
        Integer index = RandomUtil.getRandomIndexGivenCountPoint(startEstimationData);
        TwoDimensionalIntegerPoint result = AbsolutePositionOneHotUtils.toOriginalData(index, this.colSize);
        return result;
    }

    protected TwoDimensionalIntegerPoint sampleNextCell(Double[] cellNeighboringEstimationData, Double[] endEstimationData, int trajectoryLength, TwoDimensionalIntegerPoint currentCell) {
        if (cellNeighboringEstimationData.length == 0) {
            return currentCell;
        }
        int[] innerIndexRange = CellNeighboringOneHotUtils.toOneHotDataIndexRange(currentCell, this.rowSize, this.colSize);
        int endIndex = AbsolutePositionOneHotUtils.toOneHotDataIndex(currentCell, this.colSize);
//        Integer chosenIndex = RandomUtil.getRandomIndexGivenCountPoint(cellNeighboringEstimationData, innerIndexRange[0], innerIndexRange[1]);
        Double[] endReWeightEstimation = new Double[]{endEstimationData[endIndex]*(this.alpha+this.beta*trajectoryLength)};
        Integer[] chosenIndexPair = RandomUtil.getTwoPartsRandomIndexGivenCountPoint(cellNeighboringEstimationData, innerIndexRange[0], innerIndexRange[1], endReWeightEstimation, 0, 0);
        if (chosenIndexPair[0] == 0) {
//            return CellNeighboringOneHotUtils.toOriginalData(chosenIndexPair[1], this.rowSize, this.colSize);
            return CellNeighboringOneHotUtils.toOriginalNeighboringData(chosenIndexPair[1], this.rowSize, this.colSize);
        }
        return CellNeighboringOneHotUtils.getNullNeighboringPoint();
    }

//    public List<TwoDimensionalIntegerPoint> trajectorySynthesis(Double[] lengthEstimationData, Double[] cellNeighboringEstimationData, Double[] startEstimationData, Double[] endEstimationData) {
//        int trajectoryLength = sampleLength(lengthEstimationData);
//        List<TwoDimensionalIntegerPoint> resultList = new ArrayList<>();
//        TwoDimensionalIntegerPoint tempPoint = this.sampleStartCell(startEstimationData);
//        resultList.add(tempPoint);
//        for (int i = 1; i < trajectoryLength; ++i) {
//            tempPoint = sampleNextCell(cellNeighboringEstimationData, endEstimationData, trajectoryLength, tempPoint);
//            if (CellNeighboringOneHotUtils.isNullPoint(tempPoint)) {
//                break;
//            }
//            resultList.add(tempPoint);
//        }
//        return resultList;
//    }
    public void setTrajectoryEstimationStruct(TrajectoryEstimationStruct trajectoryEstimationStruct) {
        this.trajectoryEstimationStruct = trajectoryEstimationStruct;
    }

    public List<TwoDimensionalIntegerPoint> trajectorySynthesis() {
        int trajectoryLength = sampleLength(this.trajectoryEstimationStruct.trajectoryLengthEstimation);
        List<TwoDimensionalIntegerPoint> resultList = new ArrayList<>();
        TwoDimensionalIntegerPoint tempPoint = this.sampleStartCell(this.trajectoryEstimationStruct.startCellEstimation);
        resultList.add(tempPoint);
        for (int i = 1; i < trajectoryLength; ++i) {
            tempPoint = sampleNextCell(this.trajectoryEstimationStruct.neighboringEstimation, this.trajectoryEstimationStruct.endCellEstimation, trajectoryLength, tempPoint);
            if (CellNeighboringOneHotUtils.isNullPoint(tempPoint)) {
                break;
            }
            resultList.add(tempPoint);
        }
        return resultList;
    }

//    public List<TwoDimensionalIntegerPoint> trajectorySynthesis(TrajectoryEstimationStruct trajectoryEstimationStruct) {
//        return this.trajectorySynthesis(trajectoryEstimationStruct.trajectoryLengthEstimation, trajectoryEstimationStruct.neighboringEstimation, trajectoryEstimationStruct.startCellEstimation, trajectoryEstimationStruct.endCellEstimation);
//    }



}
