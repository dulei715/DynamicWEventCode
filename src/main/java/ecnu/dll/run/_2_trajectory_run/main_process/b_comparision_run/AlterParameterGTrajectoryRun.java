package ecnu.dll.run._2_trajectory_run.main_process.b_comparision_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.common_tools.GridTools;
import ecnu.dll.construction.run._1_total_run.main_process.a_single_scheme_run.DAMRun;
import ecnu.dll.construction.run._2_trajectory_run.main_process.a_single_scheme_run.version_1.LDPTraceRun;
import ecnu.dll.construction.run._2_trajectory_run.main_process.a_single_scheme_run.version_1.PivotTraceRun;
import ecnu.dll.construction.run._2_trajectory_run.main_process.a_single_scheme_run.version_2.PivotTraceRunEnhanced;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.TrajectoryCommonTool;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedSchemeTool;

import java.util.*;


public class AlterParameterGTrajectoryRun {
    public static Map<String, List<ExperimentResult>> run(final List<List<TwoDimensionalDoublePoint>> doubleTrajectoryData, double inputSideLength, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {

        int arraySize = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_Trajectory_Comparison.length;

        /*
            1. 设置cell大小的变化参数（同时也是设置整数input的边长大小）
         */
//        double[] cellQuantityInSideLength = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;
        double[] cellQuantityInSideLength = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_Trajectory_Comparison;
        double[] unitCellLength = new double[arraySize];
        for (int i = 0; i < unitCellLength.length; i++) {
            // 记录每种参数下一个网格的cell的具体长度
            unitCellLength[i] = inputSideLength / cellQuantityInSideLength[i];
        }

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET_for_DAM_and_Trajectory_Comparison;

        /*
            3. 根据inputIntegerSizeLengthArray，分别计算Rhombus和Disk方案对应的Optimal sizeB的取值
         */
        int[] sizeDArray = new int[arraySize];
        int[] alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizeDArray[i] = (int)Math.ceil(cellQuantityInSideLength[i]);
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, sizeDArray[i]);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对Disk, LDPTrace, PivotTrace 分别计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey;
        String ldpTraceKey = Constant.ldpTraceSchemeKey;
        String pivotTraceKey = Constant.pivotTraceSchemeKey;
        ExperimentResult tempDiskExperimentResult, tempLDPTraceExperimentResult, tempPivotTraceExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>();
        List<ExperimentResult> ldpTraceExperimentResultList = new ArrayList<>();
        List<ExperimentResult> pivotTraceExperimentResultList = new ArrayList<>();
        List<List<TwoDimensionalIntegerPoint>> integerTrajectoryData;
        List<TwoDimensionalIntegerPoint> integerPointList, integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;


        TwoDimensionalDoublePoint leftBottomPoint = new TwoDimensionalDoublePoint(xBound, yBound);
        TwoDimensionalDoublePoint rightTopPoint = new TwoDimensionalDoublePoint(xBound+inputSideLength, yBound + inputSideLength);

        for (int i = 0; i < arraySize; i++) {

            System.out.println("Privacy Budget is " + epsilon + ", Input Length Size is " + cellQuantityInSideLength[i]);

            // 计算不同的gridLength对应的不同的integerTrajectory
            integerTrajectoryData = GridTools.fromDoubleTrajectoryListToGridTrajectoryList(doubleTrajectoryData, sizeDArray[i], leftBottomPoint, rightTopPoint);
//            integerPointList = Grid.toIntegerPoint(doubleTrajectoryData, new Double[]{xBound, yBound}, gridLengthArray[i]);
            integerPointList = TrajectoryCommonTool.fromIntegerTrajectoryListToIntegerPointList(integerTrajectoryData);


            // 计算不同的gridLength对应的不同的rawStatistic
            integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList(sizeDArray[i]);
            rawDataStatistic = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

            // for DMA
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, unitCellLength[i], inputSideLength, alterDiskOptimalSizeB[i]*unitCellLength[i], epsilon, kParameter, xBound, yBound);
//            tempDiskExperimentResult = tempDiskExperimentResultAndScheme.getExperimentResult();
            tempDiskExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            diskExperimentResultList.add(tempDiskExperimentResult);


            // for ldpTrace, pivotTrace
            tempLDPTraceExperimentResult = LDPTraceRun.run(integerTrajectoryData, rawDataStatistic, unitCellLength[i], inputSideLength, epsilon);
            tempLDPTraceExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            ldpTraceExperimentResultList.add(tempLDPTraceExperimentResult);

            tempPivotTraceExperimentResult = PivotTraceRun.run(integerTrajectoryData, rawDataStatistic, unitCellLength[i], inputSideLength, epsilon);
            tempPivotTraceExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            pivotTraceExperimentResultList.add(tempPivotTraceExperimentResult);


        }


        alterParameterMap.put(ldpTraceKey, ldpTraceExperimentResultList);
        alterParameterMap.put(pivotTraceKey, pivotTraceExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);

        return alterParameterMap;

    }
    public static Map<String, List<ExperimentResult>> runEnhanced(final List<List<TwoDimensionalDoublePoint>> doubleTrajectoryData, List<TwoDimensionalDoublePoint> poi, double inputSideLength, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {

        int arraySize = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_Trajectory_Comparison.length;

        /*
            1. 设置cell大小的变化参数（同时也是设置整数input的边长大小）
         */
//        double[] cellQuantityInSideLength = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;
        double[] cellQuantityInSideLength = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_Trajectory_Comparison;
        double[] unitCellLength = new double[arraySize];
        for (int i = 0; i < unitCellLength.length; i++) {
            // 记录每种参数下一个网格的cell的具体长度
            unitCellLength[i] = inputSideLength / cellQuantityInSideLength[i];
        }

        /*
            2. 设置隐私预算budget
         */
        double epsilon = Constant.DEFAULT_PRIVACY_BUDGET_for_DAM_and_Trajectory_Comparison;

        /*
            3. 根据inputIntegerSizeLengthArray，分别计算Rhombus和Disk方案对应的Optimal sizeB的取值
         */
        int[] sizeDArray = new int[arraySize];
        int[] alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizeDArray[i] = (int)Math.ceil(cellQuantityInSideLength[i]);
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilon, sizeDArray[i]);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对Disk, LDPTrace, PivotTrace 分别计算对应grid下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey;
        String ldpTraceKey = Constant.ldpTraceSchemeKey;
        String pivotTraceKey = Constant.pivotTraceSchemeKey;
        ExperimentResult tempDiskExperimentResult, tempLDPTraceExperimentResult, tempPivotTraceExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>();
        List<ExperimentResult> ldpTraceExperimentResultList = new ArrayList<>();
        List<ExperimentResult> pivotTraceExperimentResultList = new ArrayList<>();
        List<List<TwoDimensionalIntegerPoint>> integerTrajectoryData;
        List<TwoDimensionalIntegerPoint> integerPointList, integerPointTypeList;
        TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic;


        TwoDimensionalDoublePoint leftBottomPoint = new TwoDimensionalDoublePoint(xBound, yBound);
        TwoDimensionalDoublePoint rightTopPoint = new TwoDimensionalDoublePoint(xBound+inputSideLength, yBound + inputSideLength);

        for (int i = 0; i < arraySize; i++) {

            System.out.println("Privacy Budget is " + epsilon + ", Input Length Size is " + cellQuantityInSideLength[i]);

            // 计算不同的gridLength对应的不同的integerTrajectory
            integerTrajectoryData = GridTools.fromDoubleTrajectoryListToGridTrajectoryList(doubleTrajectoryData, sizeDArray[i], leftBottomPoint, rightTopPoint);
//            integerPointList = Grid.toIntegerPoint(doubleTrajectoryData, new Double[]{xBound, yBound}, gridLengthArray[i]);
            integerPointList = TrajectoryCommonTool.fromIntegerTrajectoryListToIntegerPointList(integerTrajectoryData);


            // 计算不同的gridLength对应的不同的rawStatistic
            integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList(sizeDArray[i]);
            rawDataStatistic = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

            // for DMA
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, unitCellLength[i], inputSideLength, alterDiskOptimalSizeB[i]*unitCellLength[i], epsilon, kParameter, xBound, yBound);
//            tempDiskExperimentResult = tempDiskExperimentResultAndScheme.getExperimentResult();
            tempDiskExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            diskExperimentResultList.add(tempDiskExperimentResult);


            // for ldpTrace, pivotTrace
            tempLDPTraceExperimentResult = LDPTraceRun.run(integerTrajectoryData, rawDataStatistic, unitCellLength[i], inputSideLength, epsilon);
            tempLDPTraceExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            ldpTraceExperimentResultList.add(tempLDPTraceExperimentResult);

            tempPivotTraceExperimentResult = PivotTraceRunEnhanced.runEnhanced(doubleTrajectoryData, poi, rawDataStatistic, unitCellLength[i], inputSideLength, epsilon, leftBottomPoint, rightTopPoint);
            tempPivotTraceExperimentResult.addPair(1, Constant.areaLengthKey, String.valueOf(inputSideLength));
            pivotTraceExperimentResultList.add(tempPivotTraceExperimentResult);


        }

        alterParameterMap.put(ldpTraceKey, ldpTraceExperimentResultList);
        alterParameterMap.put(pivotTraceKey, pivotTraceExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);

        return alterParameterMap;

    }


}
