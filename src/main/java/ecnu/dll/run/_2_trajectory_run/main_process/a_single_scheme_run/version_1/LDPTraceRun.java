package ecnu.dll.run._2_trajectory_run.main_process.a_single_scheme_run.version_1;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.TrajectoryCommonTool;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.LDPTrace;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.TrajectoryFO;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.TrajectoryEstimationStruct;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.UserTrajectoryOneHotStruct;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.UserTrajectoryOriginalStruct;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.sub_struct.CellNeighboring;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.utils.TrajectoryFOUtils;
import tools.others.Sinkhorn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class LDPTraceRun {

    private static final Integer maxTravelDistance = Constant.TrajectorySamplingLengthUpperBound;

    private static Integer sampleSize = Constant.generatingTrajectorySizeFromSynthetic;

    public static ExperimentResult run(List<List<TwoDimensionalIntegerPoint>> trajectoryDataSet, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double unitCellLength, double inputLength, Double epsilon) {
        System.out.println("Start LDPTrace Run version 1...");
        Integer gridSideLength = (int)Math.round(inputLength / unitCellLength);
        LDPTrace ldpTrace = new LDPTrace(gridSideLength, gridSideLength, epsilon, maxTravelDistance);

        long startTime = System.currentTimeMillis();
        TrajectoryEstimationStruct trajectoryEstimationStruct = trajectoryDisturbAndCollection(trajectoryDataSet, ldpTrace);
        ldpTrace.setTrajectoryEstimationStruct(trajectoryEstimationStruct);
        List<List<TwoDimensionalIntegerPoint>> generatingTrajectoryList = new ArrayList<>(sampleSize);
        for (int i = 0; i < sampleSize; ++i) {
            generatingTrajectoryList.add(ldpTrace.trajectorySynthesis());
        }
        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = TrajectoryCommonTool.statistic(generatingTrajectoryList, rawDataStatistic.keySet());
        long endTime = System.currentTimeMillis();
        long postProcessTime = endTime - startTime;
        System.out.println("Total process time is " + postProcessTime);
        //

        ExperimentResult experimentResult = null;
        Double wassersteinDistance1 = 0D;
        Double wassersteinDistance2 = Sinkhorn.getWassersteinDistanceBySinkhorn(estimationResult, rawDataStatistic, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND, Constant.SINKHORN_ITERATOR_UPPERBOUND);
        Double klDivergence = 0D;
        experimentResult = new ExperimentResult();
        // 这里实际上是trajectorySize，变量名和Excel头就不改了
        experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(trajectoryDataSet.size()));
        experimentResult.addPair(Constant.schemeNameKey, Constant.ldpTraceSchemeKey);
        experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
        experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(unitCellLength));
        experimentResult.addPair(Constant.dataTypeSizeKey, String.valueOf(inputLength * inputLength));
        experimentResult.addPair(Constant.sizeDKey, String.valueOf(0));
        experimentResult.addPair(Constant.sizeBKey, String.valueOf(0));
        experimentResult.addPair(Constant.privacyBudgetKey, String.valueOf(epsilon));
        experimentResult.addPair(Constant.contributionKKey, String.valueOf(0));
        experimentResult.addPair(Constant.wassersteinDistance1Key, String.valueOf(wassersteinDistance1));
        experimentResult.addPair(Constant.wassersteinDistance2Key, String.valueOf(wassersteinDistance2));
        experimentResult.addPair(Constant.klDivergenceKey, String.valueOf(klDivergence));
        return experimentResult;
    }



    protected static TrajectoryEstimationStruct trajectoryDisturbAndCollection(List<List<TwoDimensionalIntegerPoint>> trajectoryData, LDPTrace ldpTrace) {
//        List<TwoDimensionalIntegerPoint> tempGridTrajectory;
        UserTrajectoryOriginalStruct tempOriginalTrajectoryInfo;
        UserTrajectoryOneHotStruct tempTrajectoryOneHotStruct;
        Double totalPrivacyBudget = ldpTrace.getTotalPrivacyBudget();

        int rowSize = ldpTrace.getRowSize();
        int colSize = ldpTrace.getColSize();
        int gridSideLength;
        if (rowSize != colSize) {
            throw new RuntimeException("You need to make the row size and col size equal!");
        }
        gridSideLength = rowSize;

        TrajectoryFO trajectoryFO = new TrajectoryFO(totalPrivacyBudget, maxTravelDistance, rowSize, colSize);
        Collection<OneHot<Integer>> trajectorySizeCollection = new ArrayList<>();
        Collection<List<OneHot<CellNeighboring>>> neighboringCollection = new ArrayList<>();
        Collection<OneHot<TwoDimensionalIntegerPoint>> startCellCollection = new ArrayList<>();
        Collection<OneHot<TwoDimensionalIntegerPoint>> endCellCollection = new ArrayList<>();
        for (List<TwoDimensionalIntegerPoint> tempTrajectory : trajectoryData) {
//            tempGridTrajectory = GridTools.fromDoubleTrajectoryToGridTrajectory(trajectory, gridSideLength, leftBottomPoint, rightTopPoint);
            tempOriginalTrajectoryInfo = TrajectoryFOUtils.extractOriginalTraceInformation(tempTrajectory, gridSideLength, gridSideLength);
            tempTrajectoryOneHotStruct = trajectoryFO.perturb(tempOriginalTrajectoryInfo);
            trajectorySizeCollection.add(tempTrajectoryOneHotStruct.getTrajectoryLength());
            neighboringCollection.add(tempTrajectoryOneHotStruct.getCellNeighboringList());
            startCellCollection.add(tempTrajectoryOneHotStruct.getStartIndex());
            endCellCollection.add(tempTrajectoryOneHotStruct.getEndIndex());
        }
        Double[] trajectoryLengthEstimation = trajectoryFO.aggregateTrajectoryLength(trajectorySizeCollection);
        Double[] neighboringEstimation = trajectoryFO.aggregateCellNeighboring(neighboringCollection);
        Double[] startCellEstimation = trajectoryFO.aggregateStartCell(startCellCollection);
        Double[] endCellEstimation = trajectoryFO.aggregateEndCell(endCellCollection);
        return new TrajectoryEstimationStruct(trajectoryLengthEstimation, neighboringEstimation, startCellEstimation, endCellEstimation);
    }

}
