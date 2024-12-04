package ecnu.dll.run._2_trajectory_run.main_process.a_single_scheme_run.version_2;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.common_tools.GridTools;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.TrajectoryCommonTool;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.AnchorBasedPivotSampling;
import tools.others.Sinkhorn;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 相比于version_1这个版本的兴趣点选为doubleTwoDimensionalPoint，，数据损失更小，精度更高
 */
public class PivotTraceRunEnhanced {
    public static ExperimentResult runEnhanced(List<List<TwoDimensionalDoublePoint>> trajectoryDataSet, List<TwoDimensionalDoublePoint> poi, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double cellLength, double inputLength, Double epsilon, TwoDimensionalDoublePoint leftBottomPoint, TwoDimensionalDoublePoint rightTopPoint) {
        System.out.println("Start PivotTrace Run version 2...");
        List<List<TwoDimensionalIntegerPoint>> estimationTrajectoryList = new ArrayList<>(trajectoryDataSet.size());
        List<TwoDimensionalDoublePoint> tempEstimationDoubleTrajectory;
        List<TwoDimensionalIntegerPoint> tempEstimationIntegerTrajectory;
        long startTime = System.currentTimeMillis();
        AnchorBasedPivotSampling pivotTrace = new AnchorBasedPivotSampling(poi);
        int k = 0;
        Integer gridSideLength = (int) Math.ceil(inputLength / cellLength);
        for (List<TwoDimensionalDoublePoint> tempDoubleTrajectory : trajectoryDataSet) {
            System.out.println("start pivot trace trajectory " + (k++) + "...");
            tempEstimationDoubleTrajectory = pivotTrace.execute(tempDoubleTrajectory, epsilon);
//            tempEstimationIntegerTrajectory = TwoDimensionalIntegerPointUtils.toSimpleIntegerPoint(tempEstimationDoubleTrajectory);
            // 将估计后的double点映射到其网格中
            tempEstimationIntegerTrajectory = GridTools.fromDoubleTrajectoryToGridTrajectory(tempEstimationDoubleTrajectory, gridSideLength, leftBottomPoint, rightTopPoint);
            estimationTrajectoryList.add(tempEstimationIntegerTrajectory);
        }
        TreeMap<TwoDimensionalIntegerPoint, Double> estimationResult = TrajectoryCommonTool.statistic(estimationTrajectoryList, rawDataStatistic.keySet());
        long endTime = System.currentTimeMillis();
        long postProcessTime = endTime - startTime;
        System.out.println("Total process time is " + postProcessTime);

        ExperimentResult experimentResult = null;
        Double wassersteinDistance1 = 0D;
        Double wassersteinDistance2 = Sinkhorn.getWassersteinDistanceBySinkhorn(estimationResult, rawDataStatistic, 2, Constant.SINKHORN_LAMBDA, Constant.SINKHORN_LOWER_BOUND, Constant.SINKHORN_ITERATOR_UPPERBOUND);
        Double klDivergence = 0D;
        experimentResult = new ExperimentResult();
        // 这里实际上是trajectorySize，变量名和Excel头就不改了
        experimentResult.addPair(Constant.dataPointSizeKey, String.valueOf(trajectoryDataSet.size()));
        experimentResult.addPair(Constant.schemeNameKey, Constant.pivotTraceSchemeKey);
        experimentResult.addPair(Constant.postProcessTimeKey, String.valueOf(postProcessTime));
        experimentResult.addPair(Constant.gridUnitSizeKey, String.valueOf(cellLength));
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
}
