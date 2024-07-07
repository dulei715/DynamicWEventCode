package ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetB.handled_struct.CheckInSimplifiedBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckInPreprocessRunUtils {




    public static Map<Integer, Map<String, Long>> getBoundMap(File[] files) {
        Map<Integer, Map<String, Long>> result = new HashMap<>();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempData;
        CheckInSimplifiedBean tempBean;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempData = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String item : tempData) {
                tempBean = CheckInSimplifiedBean.toBean(basicRead.split(item));
                PreprocessRunUtils.updateSubMapValue(result, tempBean.getUserID(), tempBean.getCheckInTimeStamp());
            }
        }
        return result;
    }



    public static Map<Integer, BasicPair<Long, String>> getInitialUserTimeSlotLocationMap(File[] files) {
        List<String> tempData;
        BasicRead basicRead = new BasicRead(",");
        CheckInSimplifiedBean tempBean;
        Map<Integer, BasicPair<Long, String>> result = new TreeMap<>();
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempData = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String dataLine : tempData) {
                tempBean = CheckInSimplifiedBean.toBean(basicRead.split(dataLine));
                PreprocessRunUtils.updateMostOriginalTimeSlotData(result, tempBean.getUserID(), tempBean.getCheckInTimeStamp(), tempBean.getCountryName());
            }
            basicRead.endReading();
        }
        return result;
    }

    public static String toSimpleCheckInString(Map.Entry<Integer, BasicPair<Long, String>> entry) {
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






    public static void main0(String[] args) {
        int cacheSize = 10;
        int timeStamp = 0;
        String inputDirectoryName = "join";
        String outputDirectoryName = "runInput";
        String inputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, inputDirectoryName);
        String outputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, outputDirectoryName);
        Long startCheckInTimeSlot = 1333476006000L; // 在/Users/admin/MainFiles/5.GitTrans/2.github_code/DynamicWEventCode/src/test/java/important_test/DatasetCheckInTest.testTime()测试中得到
        Long endCheckInTimeSlot = 1379373855000L;
        Long timeInterval = ConfigureUtils.getTimeInterval("checkIn");
        File fileDirectory = new File(inputDirectoryPath);
        File[] inputFiles = fileDirectory.listFiles();
        Map<Integer, BasicPair<Long, String>> result = getInitialUserTimeSlotLocationMap(inputFiles);
        MyPrint.showMap(result);
    }

    public static void main(String[] args) {
        String fileName = "timestamp_00002.txt";
        String numStr = PreprocessRunUtils.extractNumberString(fileName);
        System.out.println(numStr);
        Long longValue = Long.valueOf(numStr);
        System.out.println(longValue);
    }
}
