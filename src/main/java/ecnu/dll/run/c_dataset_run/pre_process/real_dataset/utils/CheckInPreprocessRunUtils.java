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
    public static final String LowBoundKey = "lowerBound";
    public static final String UpperBoundKey = "upperBound";
    public static void updateSubMapValue(Map<Integer, Map<String, Long>> map, Integer userID, Long value) {
        Map<String, Long> boundMap = map.get(userID);
        Long tempBoundLower, tempBoundUpper;
        if (boundMap == null) {
            boundMap = new HashMap<>();
            boundMap.put(LowBoundKey, Long.MAX_VALUE);
            boundMap.put(UpperBoundKey, 0L);
            map.put(userID, boundMap);
        }
        tempBoundLower = boundMap.get(LowBoundKey);
        tempBoundUpper = boundMap.get(UpperBoundKey);
        if (value < tempBoundLower) {
            boundMap.put(LowBoundKey, value);
        }
        if (value > tempBoundUpper) {
            boundMap.put(UpperBoundKey, value);
        }
    }

    // for test
    public static Map<Integer, Map<String, Double>> format(Map<Integer, Map<String, Long>> rawMap, Long startTag) {
        Map<Integer, Map<String, Double>> result = new HashMap<>();
        for (Map.Entry<Integer, Map<String, Long>> rawEntry : rawMap.entrySet()) {
            Integer rawKey = rawEntry.getKey();
            Map<String, Long> rawValue = rawEntry.getValue();
            Map<String, Double> newValue = new HashMap<>();
            for (Map.Entry<String, Long> innerRawEntry : rawValue.entrySet()) {
                String innerRawKey = innerRawEntry.getKey();
                Long innerRawValue = innerRawEntry.getValue();
                Double innerNewValue = (innerRawValue - startTag) / 1000.0 / 60.0 / 60 / 24;
                newValue.put(innerRawKey, innerNewValue);
            }
            result.put(rawKey,newValue);
        }
        return result;
    }

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
                updateSubMapValue(result, tempBean.getUserID(), tempBean.getCheckInTimeStamp());
            }
        }
        return result;
    }

    public static void updateLatestTimeSlotData(Map<Integer, BasicPair<Long, String>> map, Integer userID, Long timeSlot, String position) {
        BasicPair<Long, String> originalData = map.get(userID);
        if (originalData == null) {
            originalData = new BasicPair<>(timeSlot, position);
            map.put(userID, originalData);
        } else {
            Long originalTimeSlot = originalData.getKey();
            if (timeSlot > originalTimeSlot) {
                map.put(userID, new BasicPair<>(timeSlot, position));
            }
        }
    }
    public static void updateMostOriginalTimeSlotData(Map<Integer, BasicPair<Long, String>> map, Integer userID, Long timeSlot, String position) {
        BasicPair<Long, String> originalData = map.get(userID);
        if (originalData == null) {
            originalData = new BasicPair<>(timeSlot, position);
            map.put(userID, originalData);
        } else {
            Long originalTimeSlot = originalData.getKey();
            if (timeSlot < originalTimeSlot) {
                map.put(userID, new BasicPair<>(timeSlot, position));
            }
        }
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
                updateMostOriginalTimeSlotData(result, tempBean.getUserID(), tempBean.getCheckInTimeStamp(), tempBean.getCountryName());
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

    public static Map<Integer, BasicPair<Long, String>> copyUserTimeSlotLocationMap(Map<Integer, BasicPair<Long, String>> originalMap) {
        Map<Integer, BasicPair<Long, String>> result = new TreeMap<>();
        BasicPair<Long, String> originalPair, newValue;
        for (Map.Entry<Integer, BasicPair<Long, String>> entry : originalMap.entrySet()) {
            originalPair = entry.getValue();
            newValue = new BasicPair<>(originalPair.getKey(), originalPair.getValue());
            result.put(entry.getKey(), newValue);
        }
        return result;
    }

    /**
     * 及支持形如xxx_yyy.txt
     * 其中yyy是文件编号
     * @param fileName
     * @return
     */
    public static String extractNumberString(String fileName) {
        String[] splitString = fileName.split("_");
        return splitString[splitString.length-1].split("\\.")[0];
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
        String numStr = extractNumberString(fileName);
        System.out.println(numStr);
        Long longValue = Long.valueOf(numStr);
        System.out.println(longValue);
    }
}
