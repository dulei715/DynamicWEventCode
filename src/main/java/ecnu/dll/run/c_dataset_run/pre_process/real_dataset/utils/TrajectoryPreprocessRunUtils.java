package ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetA.handled_struct.TrajectoryComplicatedBean;
import ecnu.dll.dataset.real.datasetA.handled_struct.TrajectorySimplifiedBean;
import ecnu.dll.dataset.real.datasetB.handled_struct.CheckInSimplifiedBean;

import java.io.File;
import java.util.*;

public class TrajectoryPreprocessRunUtils {




    public static Map<Integer, Map<String, Long>> getBoundMap(File[] files) {
        Map<Integer, Map<String, Long>> result = new HashMap<>();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempData;
        TrajectorySimplifiedBean tempBean;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempData = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String item : tempData) {
                tempBean = TrajectorySimplifiedBean.toBean(basicRead.split(item));
                PreprocessRunUtils.updateSubMapValue(result, tempBean.getUserID(), tempBean.getTimestamp());
            }
        }
        return result;
    }



    public static Map<Integer, BasicPair<Long, String>> getInitialUserTimeSlotLocationMap(File[] files) {
        List<String> tempData;
        BasicRead basicRead = new BasicRead(",");
        TrajectoryComplicatedBean tempBean;
        Map<Integer, BasicPair<Long, String>> result = new TreeMap<>();
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempData = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String dataLine : tempData) {
                tempBean = TrajectoryComplicatedBean.toBean(basicRead.split(dataLine));
                PreprocessRunUtils.updateMostOriginalTimeSlotData(result, tempBean.getUserID(), tempBean.getTimestamp(), tempBean.getAreaIndex()+"");
            }
            basicRead.endReading();
        }
        return result;
    }

    public static String toSimpleTrajectoryString(Map.Entry<Integer, BasicPair<Long, String>> entry) {
        StringBuilder stringBuilder = new StringBuilder();
        BasicPair<Long, String> pairValue = entry.getValue();
        stringBuilder.append(entry.getKey()).append(",");
        stringBuilder.append(pairValue.getValue()).append(",");
        stringBuilder.append(pairValue.getKey());
        return stringBuilder.toString();
    }

    public static Map<Integer, BasicPair<Long, String>> getInitialUserTimeSlotLocationMap(String directoryPath) {
        File file = new File(directoryPath);
        File[] files = file.listFiles();
        return getInitialUserTimeSlotLocationMap(files);
    }

    public static List<Integer> getUserIDList(String userIDDir) {
        File file = new File(userIDDir);
        String[] fileNameArray = file.list();
        List<Integer> result = new ArrayList<>(fileNameArray.length);
        for (String fileName : fileNameArray) {
            result.add(Integer.valueOf(fileName.split("\\.")[0]));
        }
        return result;
    }






    public static void main0(String[] args) {
//        String inputDirectoryName = "taxi_log_2008_by_id";
        String inputDirectoryName = "extract_data";
        String outputDirectoryName = "runInput";
        String inputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, inputDirectoryName);
        File fileDirectory = new File(inputDirectoryPath);
        File[] inputFiles = fileDirectory.listFiles();
        Map<Integer, BasicPair<Long, String>> result = getInitialUserTimeSlotLocationMap(inputFiles);
        MyPrint.showMap(result);
    }

    public static void main1(String[] args) {
        String fileName = "timestamp_00002.txt";
        String numStr = PreprocessRunUtils.extractNumberString(fileName);
        System.out.println(numStr);
        Long longValue = Long.valueOf(numStr);
        System.out.println(longValue);
    }

    public static void main(String[] args) {
        String userIDDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "taxi_log_2008_by_id_filter");
        List<Integer> result = getUserIDList(userIDDir);
        MyPrint.showList(result);
        System.out.println(result.size());
    }
}
