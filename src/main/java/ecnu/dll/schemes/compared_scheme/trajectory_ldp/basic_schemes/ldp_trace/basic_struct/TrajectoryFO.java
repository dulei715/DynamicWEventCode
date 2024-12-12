package ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct;

import cn.edu.dll.differential_privacy.ldp.frequency_oracle.FrequencyOracle;
import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.OptimizedUnaryEncoding;
import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct.UserTrajectoryOneHotStruct;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct.UserTrajectoryOriginalStruct;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct.sub_struct.CellNeighboring;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.utils.TrajectoryFOUtils;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.basic_one_hot_struct.AbsolutePositionOneHot;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.basic_one_hot_struct.CellNeighboringOneHot;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.basic_one_hot_struct.TrajectoryLengthOneHot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrajectoryFO implements FrequencyOracle<UserTrajectoryOriginalStruct, UserTrajectoryOneHotStruct> {
    private double totalPrivacyBudget;
    private int maxTrajectoryLength;
    private int gridRowSize;
    private int gridColSize;

    private OptimizedUnaryEncoding<Integer> trajectoryLengthOUE;
    private OptimizedUnaryEncoding<CellNeighboring> innerPointOUE;
    private OptimizedUnaryEncoding<TwoDimensionalIntegerPoint> startPointOUE;
    private OptimizedUnaryEncoding<TwoDimensionalIntegerPoint> endPointOUE;



    public TrajectoryFO (double totalPrivacyBudget, int maxTrajectoryLength, int gridRowSize, int gridColSize) {
        this.totalPrivacyBudget = totalPrivacyBudget;
        this.maxTrajectoryLength = maxTrajectoryLength;
        this.gridRowSize = gridRowSize;
        this.gridColSize = gridColSize;

        double[] privacyBudgetSplitArray = TrajectoryFOUtils.getSplitPrivacyBudget(this.totalPrivacyBudget);
        this.trajectoryLengthOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[0]);
        this.innerPointOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[1]);
        this.startPointOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[2]/2);
        this.endPointOUE = new OptimizedUnaryEncoding<>(privacyBudgetSplitArray[2]/2);
    }

    @Override
    public UserTrajectoryOneHotStruct perturb(UserTrajectoryOriginalStruct userTrajectoryOriginalStruct) {
        OneHot<Integer> trajectoryLengthOneHot = new TrajectoryLengthOneHot(this.maxTrajectoryLength);
        int cellNeighboringListSize = userTrajectoryOriginalStruct.cellNeighboringList.size();
        List<OneHot<CellNeighboring>> cellNeighboringOneHotList = new ArrayList<>(cellNeighboringListSize);
        OneHot<CellNeighboring> tempCellNeighboringOneHot;
        OneHot<TwoDimensionalIntegerPoint> startCellOneHot = new AbsolutePositionOneHot(this.gridRowSize, this.gridColSize);
        OneHot<TwoDimensionalIntegerPoint> endCellOneHot = new AbsolutePositionOneHot(this.gridRowSize, this.gridColSize);

        trajectoryLengthOneHot.setElement(userTrajectoryOriginalStruct.trajectoryLength);
        for (int i = 0; i < cellNeighboringListSize; ++i) {
            tempCellNeighboringOneHot = new CellNeighboringOneHot(this.gridRowSize, this.gridColSize);
            tempCellNeighboringOneHot.setElement(userTrajectoryOriginalStruct.cellNeighboringList.get(i));
            cellNeighboringOneHotList.add(tempCellNeighboringOneHot);
        }

        startCellOneHot.setElement(userTrajectoryOriginalStruct.startIndex);
        endCellOneHot.setElement(userTrajectoryOriginalStruct.endIndex);

        OneHot<Integer> newTrajectoryOneHot = this.trajectoryLengthOUE.perturb(trajectoryLengthOneHot);
        List<OneHot<CellNeighboring>> newCellNeighboringOneHotList = new ArrayList<>(cellNeighboringListSize);
        OneHot<CellNeighboring> tempNewCellNeighboringOneHot;
        Double totalInnerPointPrivacyBudget = this.innerPointOUE.getQ();
        Double eachPartPrivacyBudget = totalInnerPointPrivacyBudget / cellNeighboringListSize;
        this.innerPointOUE.resetEpsilon(eachPartPrivacyBudget);
        for (int i = 0; i < cellNeighboringListSize; ++i) {
            tempNewCellNeighboringOneHot = this.innerPointOUE.perturb(cellNeighboringOneHotList.get(i));
            newCellNeighboringOneHotList.add(tempNewCellNeighboringOneHot);
        }
        this.innerPointOUE.resetEpsilon(totalInnerPointPrivacyBudget);
        OneHot<TwoDimensionalIntegerPoint> newStartOneHot = this.startPointOUE.perturb(startCellOneHot);
        OneHot<TwoDimensionalIntegerPoint> newEndOneHot = this.startPointOUE.perturb(endCellOneHot);

        return new UserTrajectoryOneHotStruct(newTrajectoryOneHot, newCellNeighboringOneHotList, newStartOneHot, newEndOneHot);
    }

    @Deprecated
    @Override
    public double aggregate(int targetNoiseEstimationCount, int userSize) {
        /*
            1. trajectory length 和用户一一对应，他独立于其他两个分布
            2. cell neighboring 和 start/end point 用于构建转移状态函数，因此需要获取每一个cell到其周围节点的统计量
         */
        /**
         * 需要写三个函数用于对perturb之后的
         */
        throw new RuntimeException("You are not use this function, use other aggregating function instead!");
    }


    public Double[] aggregateTrajectoryLength(Collection<OneHot<Integer>> trajectoryLengthDataCollection) {
        int userSize = trajectoryLengthDataCollection.size();
        Integer[] trajectoryLengthCount = OptimizedUnaryEncoding.count(trajectoryLengthDataCollection);
        Double[] estimationResult = this.trajectoryLengthOUE.unbias(trajectoryLengthCount, userSize);
        return estimationResult;
    }

//    public Double[] aggregateCellNeighboring(Collection<OneHot<CellNeighboring>> cellNeighboringDataCollection) {
//        int userSize = cellNeighboringDataCollection.size();
//        Integer[] cellNeighboringCount = OptimizedUnaryEncoding.count(cellNeighboringDataCollection);
//        Double[] estimationResult = this.innerPointOUE.unbias(cellNeighboringCount, userSize);
//        return estimationResult;
//    }
    public Double[] aggregateCellNeighboring(Collection<List<OneHot<CellNeighboring>>> cellNeighboringDataListCollection) {
        int userSize = cellNeighboringDataListCollection.size();
        Integer[] cellNeighboringCount = OptimizedUnaryEncoding.countMultiple(cellNeighboringDataListCollection);
        Double[] estimationResult = this.innerPointOUE.unbias(cellNeighboringCount, userSize);
        return estimationResult;
    }

    public Double[] aggregateStartCell(Collection<OneHot<TwoDimensionalIntegerPoint>> cellStartDataCollection) {
        int userSize = cellStartDataCollection.size();
        Integer[] cellStartCount = OptimizedUnaryEncoding.count(cellStartDataCollection);
        Double[] estimationResult = this.startPointOUE.unbias(cellStartCount, userSize);
        return estimationResult;
    }
    public Double[] aggregateEndCell(Collection<OneHot<TwoDimensionalIntegerPoint>> cellEndDataCollection) {
        int userSize = cellEndDataCollection.size();
        Integer[] cellEndCount = OptimizedUnaryEncoding.count(cellEndDataCollection);
        Double[] estimationResult = this.endPointOUE.unbias(cellEndCount, userSize);
        return estimationResult;
    }


}
