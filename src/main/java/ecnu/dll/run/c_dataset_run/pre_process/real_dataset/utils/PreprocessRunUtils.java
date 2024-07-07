package ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils;

import cn.edu.dll.struct.pair.BasicPair;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PreprocessRunUtils {
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
}
