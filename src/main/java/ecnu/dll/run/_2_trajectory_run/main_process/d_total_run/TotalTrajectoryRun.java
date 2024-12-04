package ecnu.dll.run._2_trajectory_run.main_process.d_total_run;

import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.result.ExperimentResult;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run._2_trajectory_run.main_process.c_data_set_run.DataSetTrajectoryRun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class TotalTrajectoryRun {
    public static Map<String, Map<String, Map<String, List<ExperimentResult>>>> runAndWrite(DataSetAreaInfo nycDataSetInfo, String nycOutputDir) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {

        String dataSetPath, dataSetName;
        Double xBound, yBound, length;

        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = new HashMap<>();

        Map<String, Map<String, List<ExperimentResult>>> tempDataSetResult = null;

        int k;
        DataSetAreaInfo dataSetInfo;

        // NYC
        if (nycDataSetInfo != null) {
            MyPrint.showSplitLine("*", 50);
            System.out.println("Start NYC ...");
            dataSetInfo = nycDataSetInfo;
            dataSetPath = dataSetInfo.getDataSetPath();
            dataSetName = dataSetInfo.getDataSetName();
            xBound = dataSetInfo.getxBound();
            yBound = dataSetInfo.getyBound();
            length = dataSetInfo.getLength();
            tempDataSetResult = DataSetTrajectoryRun.runAndWrite(dataSetPath, dataSetName, nycOutputDir, xBound, yBound, length);
            resultMap.put(dataSetName, tempDataSetResult);
        }



        return resultMap;
    }
    public static Map<String, Map<String, Map<String, List<ExperimentResult>>>> runAndWriteEnhanced(DataSetAreaInfo nycDataSetInfo, String poiPath, String nycOutputDir) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {

        String dataSetPath, dataSetName;
        Double xBound, yBound, length;

        Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = new HashMap<>();

        Map<String, Map<String, List<ExperimentResult>>> tempDataSetResult = null;

        int k;
        DataSetAreaInfo dataSetInfo;

        // NYC
        if (nycDataSetInfo != null) {
            MyPrint.showSplitLine("*", 50);
            System.out.println("Start NYC ...");
            dataSetInfo = nycDataSetInfo;
            dataSetPath = dataSetInfo.getDataSetPath();
            dataSetName = dataSetInfo.getDataSetName();
            xBound = dataSetInfo.getxBound();
            yBound = dataSetInfo.getyBound();
            length = dataSetInfo.getLength();
            tempDataSetResult = DataSetTrajectoryRun.runAndWriteEnhanced(dataSetPath, dataSetName, poiPath, nycOutputDir, xBound, yBound, length);
            resultMap.put(dataSetName, tempDataSetResult);
        }



        return resultMap;
    }


}
