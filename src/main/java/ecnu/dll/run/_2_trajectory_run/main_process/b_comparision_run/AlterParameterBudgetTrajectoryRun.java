package ecnu.dll.run._2_trajectory_run.main_process.b_comparision_run;

import cn.edu.dll.result.ExperimentResult;
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

import java.util.*;


public class AlterParameterBudgetTrajectoryRun {
    public static Map<String, List<ExperimentResult>> run(final List<List<TwoDimensionalIntegerPoint>> integerTrajectoryData, double inputSideLength, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;
//        System.out.println("Start altering budget run ...");
        int arraySize = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_Trajectory_Comparison.length;
        /*
            1. 设置cell大小的参数（同时也是设置整数input的边长大小）
         */
        double inputLengthSize = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;
        double gridLength = inputSideLength / inputLengthSize;

        /*
            2. 设置隐私预算budget的变化数组
         */
        double[] epsilonArray = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_Trajectory_Comparison;

        /*
            3. 根据budget，计算Disk方案对应的Optimal sizeB的取值
         */
        int inputIntegerLengthSize = (int)Math.ceil(inputLengthSize);
        int[] alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilonArray[i], inputIntegerLengthSize);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对Disk, TraceLDP, PivotLDP 分别计算对应budget下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey;
//        String subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey;
        String ldpTraceKey = Constant.ldpTraceSchemeKey;
        String pivotTraceKey = Constant.pivotTraceSchemeKey;

        ExperimentResult tempDiskExperimentResult, tempLDPTraceExperimentResult, tempPivotTraceExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>();
        List<ExperimentResult> ldpTraceExperimentResultList = new ArrayList<>();
        List<ExperimentResult> pivotTraceExperimentResultList = new ArrayList<>();


        List<TwoDimensionalIntegerPoint> integerPointList = TrajectoryCommonTool.fromIntegerTrajectoryListToIntegerPointList(integerTrajectoryData);


        for (int i = 0; i < arraySize; i++) {

            System.out.println("Privacy Budget is " + epsilonArray[i] + ", Input Length Size is " + inputLengthSize);


            // for ldpTrace
            System.out.println("Start LDP Trace...");
            tempLDPTraceExperimentResult = LDPTraceRun.run(integerTrajectoryData, rawDataStatistic, gridLength, inputSideLength, epsilonArray[i]);
            ldpTraceExperimentResultList.add(tempLDPTraceExperimentResult);

            System.out.println("Start Pivot Trace...");
            // for pivotTrace
            tempPivotTraceExperimentResult = PivotTraceRun.run(integerTrajectoryData, rawDataStatistic, gridLength, inputSideLength, epsilonArray[i]);
            pivotTraceExperimentResultList.add(tempPivotTraceExperimentResult);

            // for DAM
            System.out.println("Start DAM...");
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, gridLength, inputSideLength, alterDiskOptimalSizeB[i]*gridLength, epsilonArray[i], kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);
        }


        alterParameterMap.put(ldpTraceKey, ldpTraceExperimentResultList);
        alterParameterMap.put(pivotTraceKey, pivotTraceExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);

        return alterParameterMap;

    }
    public static Map<String, List<ExperimentResult>> runEnhanced(final List<List<TwoDimensionalDoublePoint>> doubleTrajectoryData, double inputSideLength, List<TwoDimensionalDoublePoint> poi, final TreeMap<TwoDimensionalIntegerPoint, Double> rawDataStatistic, double xBound, double yBound) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
//        String inputDataPath = Constant.DEFAULT_INPUT_PATH;
//        System.out.println("Start altering budget run ...");
        int arraySize = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_Trajectory_Comparison.length;
        /*
            1. 设置cell大小的参数（同时也是设置整数input的边长大小）
         */
        double discreteSizeLength = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison;
        double cellLength = inputSideLength / discreteSizeLength;

        /*
            2. 设置隐私预算budget的变化数组
         */
        double[] epsilonArray = Constant.ALTER_PRIVACY_BUDGET_ARRAY_for_DAM_and_Trajectory_Comparison;

        /*
            3. 根据budget，计算Disk方案对应的Optimal sizeB的取值
         */
        int inputIntegerLengthSize = (int)Math.ceil(discreteSizeLength);
        int[] alterDiskOptimalSizeB = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            alterDiskOptimalSizeB[i] = DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(epsilonArray[i], inputIntegerLengthSize);
        }

        /*
            4. 设置邻居属性smooth的参与率
         */
        double kParameter = Constant.DEFAULT_K_PARAMETER;


        /*
            针对Disk, TraceLDP, PivotLDP 分别计算对应budget下的估计并返回相应的wasserstein距离
         */
        Map<String, List<ExperimentResult>> alterParameterMap = new HashMap<>();
        String diskKey = Constant.diskSchemeKey;
//        String subsetGeoITwoNorm = Constant.subsetGeoITwoNormSchemeKey;
        String ldpTraceKey = Constant.ldpTraceSchemeKey;
        String pivotTraceKey = Constant.pivotTraceSchemeKey;

        ExperimentResult tempDiskExperimentResult, tempLDPTraceExperimentResult, tempPivotTraceExperimentResult;
        List<ExperimentResult> diskExperimentResultList = new ArrayList<>();
        List<ExperimentResult> ldpTraceExperimentResultList = new ArrayList<>();
        List<ExperimentResult> pivotTraceExperimentResultList = new ArrayList<>();


        TwoDimensionalDoublePoint leftBottomPoint, rightTopPoint;
        leftBottomPoint = new TwoDimensionalDoublePoint(xBound, yBound);
        rightTopPoint = new TwoDimensionalDoublePoint(xBound + inputSideLength, yBound + inputSideLength);
        List<List<TwoDimensionalIntegerPoint>> integerTrajectoryList = GridTools.fromDoubleTrajectoryListToGridTrajectoryList(doubleTrajectoryData, inputIntegerLengthSize, leftBottomPoint, rightTopPoint);
        List<TwoDimensionalIntegerPoint> integerPointList = TrajectoryCommonTool.fromIntegerTrajectoryListToIntegerPointList(integerTrajectoryList);


        for (int i = 0; i < arraySize; i++) {

            System.out.println("Privacy Budget is " + epsilonArray[i] + ", Input Length Size is " + discreteSizeLength);


            // for ldpTrace
            System.out.println("Start LDP Trace...");
            tempLDPTraceExperimentResult = LDPTraceRun.run(integerTrajectoryList, rawDataStatistic, cellLength, inputSideLength, epsilonArray[i]);
            ldpTraceExperimentResultList.add(tempLDPTraceExperimentResult);

            System.out.println("Start Pivot Trace...");
            // for pivotTrace
            tempPivotTraceExperimentResult = PivotTraceRunEnhanced.runEnhanced(doubleTrajectoryData, poi, rawDataStatistic, cellLength, inputSideLength, epsilonArray[i], leftBottomPoint, rightTopPoint);
            pivotTraceExperimentResultList.add(tempPivotTraceExperimentResult);

            // for DAM
            System.out.println("Start DAM...");
            tempDiskExperimentResult = DAMRun.run(integerPointList, rawDataStatistic, cellLength, inputSideLength, alterDiskOptimalSizeB[i]*cellLength, epsilonArray[i], kParameter, xBound, yBound);
            diskExperimentResultList.add(tempDiskExperimentResult);
        }


        alterParameterMap.put(ldpTraceKey, ldpTraceExperimentResultList);
        alterParameterMap.put(pivotTraceKey, pivotTraceExperimentResultList);
        alterParameterMap.put(diskKey, diskExperimentResultList);

        return alterParameterMap;

    }
}
