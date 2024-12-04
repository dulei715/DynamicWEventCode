package ecnu.dll.run._2_trajectory_run.main_process.c_data_set_run;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.io.write.ExperimentResultWrite;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.common_tools.GridTools;
import ecnu.dll.construction.extend_tools.trajectory_io.TrajectoryRead;
import ecnu.dll.construction.run._2_trajectory_run.main_process.b_comparision_run.AlterParameterBudgetTrajectoryRun;
import ecnu.dll.construction.run._2_trajectory_run.main_process.b_comparision_run.AlterParameterGTrajectoryRun;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.TrajectoryCommonTool;
import ecnu.dll.construction.schemes.new_scheme.discretization.tool.DiscretizedSchemeTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("Duplicates")
public class DataSetTrajectoryRun {
    public static Map<String, Map<String, List<ExperimentResult>>> runAndWrite(String dataSetPath, String datasetName, String outputDir, double xBound, double yBound, double inputSideLength) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {
        /**
         *  1. 读取 DoublePointList
         *  2. 生成 IntegerPointList
         *  3. 统计 rawStatistics
         *  4. 调用 AlterParameterBudgetTrajectoryRun
         *  5. 调用 AlterParameterGTrajectoryRun
         */
//        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
//        pointRead.readPointWithFirstLineCount();
//        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();
        TrajectoryRead trajectoryRead = new TrajectoryRead();
        trajectoryRead.startReading(dataSetPath);
        List<List<TwoDimensionalDoublePoint>> doubleTrajectoryList = trajectoryRead.readAllTrajectoryAndSize();
        trajectoryRead.endReading();

        TwoDimensionalDoublePoint leftBottomPoint = new TwoDimensionalDoublePoint(xBound, yBound);
        TwoDimensionalDoublePoint rightTopPoint = new TwoDimensionalDoublePoint(xBound+inputSideLength, yBound + inputSideLength);


        List<List<TwoDimensionalIntegerPoint>> integerTrajectoryList = GridTools.fromDoubleTrajectoryListToGridTrajectoryList(doubleTrajectoryList, (int)Math.round(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison), leftBottomPoint, rightTopPoint);
//        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison);
        List<TwoDimensionalIntegerPoint> integerPointList = TrajectoryCommonTool.fromIntegerTrajectoryListToIntegerPointList(integerTrajectoryList);

        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

//        String outputFileName;
        Map<String, Map<String, List<ExperimentResult>>> datasetResult = new HashMap<>();

        /**
         * 执行budget变化
         */
        runAlteringBudget(datasetName, outputDir, xBound, yBound, inputSideLength, integerTrajectoryList, rawStatisticMap, datasetResult);
        /**
         * 执行g变化
         */
        runAlteringG(datasetName, outputDir, xBound, yBound, inputSideLength, doubleTrajectoryList, datasetResult);

        System.gc();
        Runtime.getRuntime().gc();

        return datasetResult;

    }
    public static Map<String, Map<String, List<ExperimentResult>>> runAndWriteEnhanced(String dataSetPath, String datasetName, String poiPath, String outputDir, double xBound, double yBound, double inputSideLength) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {
        /**
         *  1. 读取 DoublePointList
         *  2. 生成 IntegerPointList
         *  3. 统计 rawStatistics
         *  4. 调用 AlterParameterBudgetTrajectoryRun
         *  5. 调用 AlterParameterGTrajectoryRun
         */
//        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
//        pointRead.readPointWithFirstLineCount();
//        List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();
        TrajectoryRead trajectoryRead = new TrajectoryRead();
        trajectoryRead.startReading(dataSetPath);
        List<List<TwoDimensionalDoublePoint>> doubleTrajectoryList = trajectoryRead.readAllTrajectoryAndSize();
        trajectoryRead.endReading();

        TwoDimensionalDoublePoint leftBottomPoint = new TwoDimensionalDoublePoint(xBound, yBound);
        TwoDimensionalDoublePoint rightTopPoint = new TwoDimensionalDoublePoint(xBound+inputSideLength, yBound + inputSideLength);


        List<List<TwoDimensionalIntegerPoint>> integerTrajectoryList = GridTools.fromDoubleTrajectoryListToGridTrajectoryList(doubleTrajectoryList, (int)Math.round(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison), leftBottomPoint, rightTopPoint);
//        List<TwoDimensionalIntegerPoint> integerPointList = Grid.toIntegerPoint(doublePointList, new Double[]{xBound, yBound}, inputSideLength / Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison);
        List<TwoDimensionalIntegerPoint> integerPointList = TrajectoryCommonTool.fromIntegerTrajectoryListToIntegerPointList(integerTrajectoryList);

        List<TwoDimensionalIntegerPoint> integerPointTypeList = DiscretizedSchemeTool.getRawTwoDimensionalIntegerPointTypeList((int) Math.ceil(Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_DAM_and_SubsetGeoI_Comparison));
        TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap = StatisticTool.countHistogramRatioMap(integerPointTypeList, integerPointList);

//        String outputFileName;
        Map<String, Map<String, List<ExperimentResult>>> datasetResult = new HashMap<>();

        List<TwoDimensionalDoublePoint> poi = TwoDimensionalPointRead.readPointWithFirstLineCount(poiPath);

        /**
         * 执行budget变化
         */
        runAlteringBudgetEnhanced(datasetName, outputDir, poi, xBound, yBound, inputSideLength, doubleTrajectoryList, rawStatisticMap, datasetResult);
        /**
         * 执行g变化
         */
        runAlteringGEnhanced(datasetName, outputDir, poi, xBound, yBound, inputSideLength, doubleTrajectoryList, datasetResult);

        System.gc();
        Runtime.getRuntime().gc();

        return datasetResult;

    }

    private static void runAlteringG(String datasetName, String outputDir, double xBound, double yBound, double inputSideLength, List<List<TwoDimensionalDoublePoint>> doubleTrajectoryList, Map<String, Map<String, List<ExperimentResult>>> datasetResult) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
        String outputFileName;
        System.out.println("Start altering g run ...");
        Map<String, List<ExperimentResult>> alteringGResult = AlterParameterGTrajectoryRun.run(doubleTrajectoryList, inputSideLength, xBound, yBound);
        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterGKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringGResult));
        datasetResult.put(Constant.alterGKey, alteringGResult);
    }


    private static void runAlteringGEnhanced(String datasetName, String outputDir, List<TwoDimensionalDoublePoint> poi, double xBound, double yBound, double inputSideLength, List<List<TwoDimensionalDoublePoint>> doubleTrajectoryList, Map<String, Map<String, List<ExperimentResult>>> datasetResult) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
        String outputFileName;
        System.out.println("Start altering g run ...");
        Map<String, List<ExperimentResult>> alteringGResult = AlterParameterGTrajectoryRun.runEnhanced(doubleTrajectoryList, poi, inputSideLength, xBound, yBound);
        ExperimentResult.addPair(alteringGResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterGKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringGResult));
        datasetResult.put(Constant.alterGKey, alteringGResult);
    }

    private static void runAlteringBudget(String datasetName, String outputDir, double xBound, double yBound, double inputSideLength, List<List<TwoDimensionalIntegerPoint>> integerTrajecotryList, TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap, Map<String, Map<String, List<ExperimentResult>>> datasetResult) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
        String outputFileName;
        System.out.println("Start altering budget run ...");
        Map<String, List<ExperimentResult>> alteringBudgetResult = AlterParameterBudgetTrajectoryRun.run(integerTrajecotryList, inputSideLength, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringBudgetResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringBudgetResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterBudgetKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringBudgetResult));
        datasetResult.put(Constant.alterBudgetKey, alteringBudgetResult);
    }
    private static void runAlteringBudgetEnhanced(String datasetName, String outputDir, List<TwoDimensionalDoublePoint> poi, double xBound, double yBound, double inputSideLength, List<List<TwoDimensionalDoublePoint>> integerTrajecotryList, TreeMap<TwoDimensionalIntegerPoint, Double> rawStatisticMap, Map<String, Map<String, List<ExperimentResult>>> datasetResult) throws InstantiationException, IllegalAccessException, CloneNotSupportedException {
        String outputFileName;
        System.out.println("Start altering budget run ...");
        Map<String, List<ExperimentResult>> alteringBudgetResult = AlterParameterBudgetTrajectoryRun.runEnhanced(integerTrajecotryList, inputSideLength, poi, rawStatisticMap, xBound, yBound);
        ExperimentResult.addPair(alteringBudgetResult, 1, Constant.areaLengthKey, String.valueOf(inputSideLength));
        ExperimentResult.addPair(alteringBudgetResult, 0, Constant.dataSetNameKey, datasetName);
        outputFileName = outputDir + ConstantValues.FILE_SPLIT + Constant.alterBudgetKey + ".csv";
        ExperimentResultWrite.write(outputFileName, ExperimentResult.getCombineResultList(alteringBudgetResult));
        datasetResult.put(Constant.alterBudgetKey, alteringBudgetResult);
    }

    public static void main(String[] args) {
        
    }

}
