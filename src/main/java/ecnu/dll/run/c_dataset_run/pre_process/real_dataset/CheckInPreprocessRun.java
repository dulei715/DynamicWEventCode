package ecnu.dll.run.c_dataset_run.pre_process.real_dataset;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.pair.IdentityPair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetA.TrajectoryTools;
import ecnu.dll.dataset.real.datasetB.handled_struct.CheckInSimplifiedBean;
import ecnu.dll.dataset.real.datasetB.spetial_tools.CheckInBeanUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckInPreprocessRun {

    private static final String checkInDataFileName = "dataset_TIST2015_Checkins.txt";
    public static void dataSplit(int unitSize) {
        String dataPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, checkInDataFileName);
        String outputSuperDirectory = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "split");
//        System.out.println(dataPath);
//        System.out.println(outputSuperDirectory);

        File outputDataSuperPathDirectory = new File(outputSuperDirectory);
        if (!outputDataSuperPathDirectory.exists()) {
            outputDataSuperPathDirectory.mkdirs();
        }
        int k = 1;
        BasicRead basicRead = new BasicRead();
        BasicWrite basicWrite = new BasicWrite();
        List<String> recordList;

        basicRead.startReading(dataPath);
        do {
            recordList = basicRead.readGivenLineSize(unitSize);
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, outputDataSuperPathDirectory, k+".txt"));
            basicWrite.writeStringListWithoutSize(recordList);
            System.out.println("Finish " + k + ".txt");
            basicWrite.endWriting();
            ++k;
        } while (recordList != null && !recordList.isEmpty() && recordList.size() >= unitSize);

    }

    public static void dataJoin() {
        String outputDataSuperPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "join");
        File outputDataSuperPathDirectory = new File(outputDataSuperPath);
        String splitDirectory = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "split");
        if (!outputDataSuperPathDirectory.exists()) {
            outputDataSuperPathDirectory.mkdirs();
        }
        CheckInBeanUtils.transformSplitFilesToCountry(Constant.checkInFilePath, splitDirectory, outputDataSuperPath);
    }

    private static final String LowBoundKey = "lowerBound";
    private static final String UpperBoundKey = "upperBound";
    private static void updateSubMapValue(Map<Integer, Map<String, Long>> map, Integer userID, Long value) {
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

    private static Map<Integer, Map<String, Long>> getBoundMap(File[] files) {
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

    // for test
    private static Map<Integer, Map<String, Double>> format(Map<Integer, Map<String, Long>> rawMap, Long startTag) {
        Map<Integer, Map<String, Double>> result = new HashMap<>();
        for (Map.Entry<Integer, Map<String, Long>> rawEntry : rawMap.entrySet()) {
            Integer rawKey = rawEntry.getKey();
            Map<String, Long> rawValue = rawEntry.getValue();
            Map<String, Double> newValue = new HashMap<>();
            for (Map.Entry<String, Long> innerRawEntry : rawValue.entrySet()) {
                String innerRawKey = innerRawEntry.getKey();
                Long innerRawValue = innerRawEntry.getValue();
                Double innerNewValue = (innerRawValue - startTag) / 1000.0 / 60.0;
                newValue.put(innerRawKey, innerNewValue);
            }
            result.put(rawKey,newValue);
        }
        return result;
    }

    public static void toExperimentRawData() {
        int cache = 5000;
        int cacheK = 0;
        String inputDirectoryName = "join";
        String outputDirectoryName = "runInput";
        String inputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, inputDirectoryName);
        String outputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, outputDirectoryName);
        Long startCheckInTimeSlot = 1333476006000L; // 在/Users/admin/MainFiles/5.GitTrans/2.github_code/DynamicWEventCode/src/test/java/important_test/DatasetCheckInTest.testTime()测试中得到
        Long endCheckInTimeSlot = 1379373855000L;
        Long timeInterval = ConfigureUtils.getTimeInterval("checkIn");
        File fileDirectory = new File(inputDirectoryPath);
        File[] inputFiles = fileDirectory.listFiles();
        BasicRead basicRead = new BasicRead(",");
        BasicWrite basicWrite = new BasicWrite(",");
        Map<Integer, Map<String, Long>> boundMap = getBoundMap(inputFiles);
        Map<Integer, Map<String, Double>> formatBoundMap = format(boundMap, startCheckInTimeSlot);
        MyPrint.showMap(formatBoundMap);


//        for (long i = startCheckInTimeSlot; i <= endCheckInTimeSlot; i+= timeInterval) {
//            if (cacheK % cache == 0) {
//
//                cacheK = 0;
//            }
//        }

    }



    public static void main1(String[] args) {
        int unitSize = 204800;
        dataSplit(unitSize);
//        System.out.println("Hello RealDataSetPreprocessRun");
    }

    public static void main2(String[] args) {
        dataJoin();
    }

    public static void main3(String[] args) {
        String inputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "taxi_log_2008_by_id_filter");
        String outputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "filter_sample");
        Double ratio = Constant.Sample_Ratio_For_Picture;
        int bufferSize = 1000;
        TrajectoryTools.sampleData(inputDir, outputDir, ratio, bufferSize);
    }

    public static void main(String[] args) {
        toExperimentRawData();
    }
}
