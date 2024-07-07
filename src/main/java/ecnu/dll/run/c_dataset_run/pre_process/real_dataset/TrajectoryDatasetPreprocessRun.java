package ecnu.dll.run.c_dataset_run.pre_process.real_dataset;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetA.basic_struct.TrajectoryBean;
import ecnu.dll.dataset.real.datasetA.handled_struct.TrajectorySimplifiedBean;
import ecnu.dll.run.c_dataset_run.pre_process.real_dataset.sub_thread.trajectory.TrajectorySplitByTimeSubThread;
import ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils.*;
import ecnu.dll.utils.CatchSignal;
import ecnu.dll.utils.FormatFileName;

import java.io.File;
import java.util.*;

public class TrajectoryDatasetPreprocessRun {
//    public static void main(String[] args) {
//        System.out.println("hello");
//    }

    /**
     * 1. 抽取数据在经度[116,116.8]和纬度[39.5,40.3]之间的数据（和user_guide.pdf图像展示保持一致）
     */
    public static void extract() {

        String trajectoryDirectoryPath = Constant.trajectoriesFilePath;
        Double longitudeLeft = 116.0, longitudeRight = 116.8;
        Double latitudeLeft = 39.5, latitudeRight = 40.3;
        Integer longitudeShareSize = ConfigureUtils.getTrajectoryLongitudeSize();
        Integer latitudeShareSize = ConfigureUtils.getTrajectoryLatitudeSize();
        File filterFile = new File(trajectoryDirectoryPath, "taxi_log_2008_by_id_filter");
        File[] files = filterFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempList;
        TrajectoryBean tempBean;
        String outputDirectory = "extract_data";
        Double tempLongitude, tempLatitude;
        BasicWrite basicWrite = new BasicWrite(",");
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            if (tempList.isEmpty()) {
                continue;
            }
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryDirectoryPath, outputDirectory, file.getName()));
            for (String stringLine : tempList) {
                tempBean = TrajectoryBean.toTrajectoryBeanWithFormatTime(basicRead.split(stringLine));
                tempLongitude = tempBean.getLongitude();
                tempLatitude = tempBean.getLatitude();
                if (tempLongitude >= longitudeLeft && tempLongitude <= longitudeRight && tempLatitude >= latitudeLeft && tempLatitude <= latitudeRight) {
                    basicWrite.writeOneLine(StringUtil.join(",", tempBean.getUserID(), tempBean.getTimestamp(), tempLongitude, tempLatitude, tempBean.getRangeIndex(longitudeLeft, longitudeRight, longitudeShareSize, latitudeLeft, latitudeRight, latitudeShareSize)));
                }
            }
            basicWrite.endWriting();
        }
    }

    /**
     * 2. 将时间转成时间戳，同时将趋于划分为longitudeSplitSize*latitudeSplitSize，并用划分后的区域编号表示它们
     */
    private static int getIndex(double value, double unitValue) {
        double share = value / unitValue;
        return (int)Math.floor(share);
    }
    @Deprecated
    public static void transformToSimpleData() {
        Integer longitudeSplitSize = ConfigureUtils.getTrajectoryLongitudeSize();
        Integer latitudeSplitSize = ConfigureUtils.getTrajectoryLatitudeSize();
        double longitudeUnit = 255.3 / longitudeSplitSize;
        double latitudeUnit = 96.06767 / latitudeSplitSize;
        String trajectoryDirectoryPath = Constant.trajectoriesFilePath;
        File filterFile = new File(trajectoryDirectoryPath, "taxi_log_2008_by_id_filter");
        File[] files = filterFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempList;
        TrajectoryBean tempBean;
        String outputDirectory = "data_format";
        Double tempLongitude, tempLatitude;
        Integer tempLongitudeIndex, tempLatitudeIndex, tempAreaIndex;
        BasicWrite basicWrite = new BasicWrite(",");
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryDirectoryPath, outputDirectory, file.getName()));
            for (String lineString : tempList) {
                tempBean = TrajectoryBean.toTrajectoryBeanWithFormatTime(basicRead.split(lineString));
                tempLongitude = tempBean.getLongitude();
                tempLatitude = tempBean.getLatitude();
                tempLongitudeIndex = getIndex(tempLongitude, longitudeUnit);
                tempLatitudeIndex = getIndex(tempLatitude, latitudeUnit);
                tempAreaIndex = tempLongitudeIndex * latitudeSplitSize + tempLatitudeIndex;
                basicWrite.writeOneLine(StringUtil.join(",", tempBean.getUserID(), tempBean.getTimestamp(), tempAreaIndex));
            }
            basicWrite.endWriting();
        }
    }

    /**
     * 计算出最小和最大时间戳（用于方便时间分割），见test.important_test:testTimeSlotRange
     * 最小时间戳：1201930244000; 最大时间戳：1202463559000
     */


    /**
     * 3. 按照时间段重新划分各个文件，使得每个文件表示一个时间段，文件中记录该时间段用户的位置
     */
    public static void splitByTime() {
        int cacheSize = 10;
        int timeStamp = 0;
        String inputDirectoryName = "extract_data";
        String outputDirectoryName = "shuffle_by_time_slot";
        String inputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, inputDirectoryName);
        String outputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, outputDirectoryName);
        Long startTrajectoryTimeSlot = 1201930244000L; // 在/Users/admin/MainFiles/5.GitTrans/2.github_code/DynamicWEventCode/src/test/java/important_test/DatasetTrajectoryTest.testTime()测试中得到
        Long endTrajectoryTimeSlot = 1202463559000L;
        Long timeInterval = ConfigureUtils.getTimeInterval("trajectories");
        File fileDirectory = new File(inputDirectoryPath);
        File[] inputFiles = fileDirectory.listFiles();
//        subSplitByTime(cacheSize, timeStamp, outputDirectoryPath, startTrajectoryTimeSlot, endTrajectoryTimeSlot, timeInterval, inputFiles);
        BasicRead basicRead = new BasicRead(",");
        BasicWrite basicWrite = new BasicWrite(",");
        List<String> tempDataList;

        // 记录第t个时间间隔内每个user对应的country名
        Map<Integer, List<String>> cacheMap = new TreeMap<>();
        List<String> tempTimeSlotDataList;
        Map<Long, Integer> helpMap = new HashMap<>();
        String[] tempStringArray;
        long cacheLeft = startTrajectoryTimeSlot;
        long cacheRight;
        long timeSlotRightBound, tempUserTime;
        TrajectorySimplifiedBean tempBean;
//        int tempTimeStamp;
        while (cacheLeft <= endTrajectoryTimeSlot) {
            cacheMap.clear();
            helpMap.clear();
            cacheRight = cacheLeft + cacheSize * timeInterval;
//            tempLatestUserTimeSlotLocationMap = new HashMap<>();
            for (long tempTimeSlot = cacheLeft; tempTimeSlot < cacheRight && tempTimeSlot <= endTrajectoryTimeSlot; tempTimeSlot += timeInterval) {
                cacheMap.put(timeStamp, new ArrayList<>());
                helpMap.put(tempTimeSlot, timeStamp);
                ++timeStamp;
            }
            for (File inputFile : inputFiles) {
                basicRead.startReading(inputFile.getAbsolutePath());
                tempDataList = basicRead.readAllWithoutLineNumberRecordInFile();
                basicRead.endReading();
                for (long timeSlotLeftBound = cacheLeft; timeSlotLeftBound < cacheRight && timeSlotLeftBound <= endTrajectoryTimeSlot; timeSlotLeftBound += timeInterval) {
                    timeSlotRightBound = timeSlotLeftBound + timeInterval;
                    tempTimeSlotDataList = cacheMap.get(helpMap.get(timeSlotLeftBound));
                    for (String dataString : tempDataList) {
                        tempStringArray = basicRead.split(dataString);
                        tempStringArray = new String[]{tempStringArray[0], tempStringArray[1], tempStringArray[4]};
                        tempBean = TrajectorySimplifiedBean.toBean(tempStringArray);
                        tempUserTime = tempBean.getTimestamp();
                        if (tempUserTime >= timeSlotLeftBound && tempUserTime < timeSlotRightBound) {
                            tempTimeSlotDataList.add(StringUtil.join(",", tempStringArray));
                        }
                    }

                }

            }
            // 遍历cacheMap写文件
            Integer tempTimeStamp;
            String tempOutputTimeStampPath;
            for (Map.Entry<Integer, List<String>> cacheEntry : cacheMap.entrySet()) {
                tempTimeStamp = cacheEntry.getKey();
                tempTimeSlotDataList = cacheEntry.getValue();
                tempOutputTimeStampPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputDirectoryPath, String.format("timestamp_%d.txt", tempTimeStamp));
                basicWrite.startWriting(tempOutputTimeStampPath);
                basicWrite.writeStringListWithoutSize(tempTimeSlotDataList);
                basicWrite.endWriting();
            }
            cacheLeft = cacheRight;
        }
    }


    public static void splitByTimeMultiThread() {
        int threadSize = 5;
        int cacheSize = 10;
        int tempTimeStamp = 0;
        String inputDirectoryName = "extract_data";
        String outputDirectoryName = "shuffle_by_time_slot";
        String inputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, inputDirectoryName);
        String outputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, outputDirectoryName);
        Long startTrajectoryTimeSlot = 1201930244000L; // 在/Users/admin/MainFiles/5.GitTrans/2.github_code/DynamicWEventCode/src/test/java/important_test/DatasetTrajectoryTest.testTime()测试中得到
        Long endTrajectoryTimeSlot = 1202463559000L;
        Long threadTimeRange = (endTrajectoryTimeSlot - startTrajectoryTimeSlot) / threadSize;

        Long timeInterval = ConfigureUtils.getTimeInterval("trajectories");
        File fileDirectory = new File(inputDirectoryPath);
        File[] inputFiles = fileDirectory.listFiles();


        List<Long> threadStartList = new ArrayList<>();
        List<Integer> threadStartTimeStamp = new ArrayList<>();
        for (long i = startTrajectoryTimeSlot, threadStep = startTrajectoryTimeSlot; i <= endTrajectoryTimeSlot; i += cacheSize * timeInterval) {
            if (i >= threadStep) {
                threadStartList.add(i);
                threadStartTimeStamp.add(tempTimeStamp);
                threadStep += threadTimeRange;
            }
            long cacheLeft = i;
            long cacheRight = i + cacheSize * timeInterval;
            for (long tempTimeSlot = cacheLeft; tempTimeSlot < cacheRight && tempTimeSlot <= endTrajectoryTimeSlot; tempTimeSlot += timeInterval) {
                ++tempTimeStamp;
            }

        }

        Runnable tempRunnable;
        Thread tempThread;
        for (int i = 0; i < threadStartList.size(); i++) {
            Long threadStartTrajectoryTimeSlot = threadStartList.get(i);
            Long threadEndTrajectoryTimeSlot = Math.min(threadStartTrajectoryTimeSlot + threadTimeRange, endTrajectoryTimeSlot);
            Integer startTimeStamp = threadStartTimeStamp.get(i);
            tempRunnable = new TrajectorySplitByTimeSubThread(cacheSize, startTimeStamp, outputDirectoryPath, threadStartTrajectoryTimeSlot, threadEndTrajectoryTimeSlot, timeInterval, inputFiles);
            tempThread = new Thread(tempRunnable);
            tempThread.start();
            System.out.println("Start a new thread with id " + tempThread.getId());
        }
        // rename
//        File outputDirFile = new File(StringUtil.join(ConstantValues.FILE_SPLIT, outputDirectoryPath));
//        File[] files = outputDirFile.listFiles();
//        String oldFileName, newFileName;
//        int fileSize = files.length; // 从timestamp=0开始
//        int digitSize = 0;
//        for (fileSize -= 1; fileSize > 0; fileSize /= 10, ++digitSize) ;
//        File newFile;
//        for (File file : files) {
//            oldFileName = file.getName();
//            newFileName = FormatFileName.formatFileName(oldFileName, "_", ".", digitSize);
//            newFile = new File(outputDirFile, newFileName);
//            file.renameTo(newFile);
//        }
    }

    public static void formatFileName() {
        String outputDirectoryName = "shuffle_by_time_slot";
        String outputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, outputDirectoryName);
        File outputDirFile = new File(StringUtil.join(ConstantValues.FILE_SPLIT, outputDirectoryPath));
        File[] files = outputDirFile.listFiles();
        String oldFileName, newFileName;
        int fileSize = files.length; // 从timestamp=0开始
        int digitSize = 0;
        for (fileSize -= 1; fileSize > 0; fileSize /= 10, ++digitSize) ;
        File newFile;
        for (File file : files) {
            oldFileName = file.getName();
            newFileName = FormatFileName.formatFileName(oldFileName, "_", ".", digitSize);
            newFile = new File(outputDirFile, newFileName);
            file.renameTo(newFile);
        }
    }



    /**
     * 4. 初始化用户最原始的位置，依次按照时间段文件更新用户状态，并记录每个时间段更新结束的状态
     */
    public static void mergeToExperimentRawData(Long fileNumberStart, Long fileNumberEnd) {
        String path = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "shuffle_by_time_slot");
        File directoryFile = new File(path);
        File[] files = directoryFile.listFiles(new FileMergeEnhancedFilter(fileNumberStart, fileNumberEnd));
        BasicRead basicRead = new BasicRead(",");
        BasicWrite basicWrite = new BasicWrite(",");
        List<String> tempDataList;
        TrajectorySimplifiedBean tempBean;
        Map<Integer, BasicPair<Long, String>> userTimeSlotLocationMap = TrajectoryPreprocessRunUtils.getInitialUserTimeSlotLocationMap(StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "taxi_log_2008_by_id_filter"));
        String outputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "runInput");

        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempDataList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            for (String lineString : tempDataList) {
                tempBean = TrajectorySimplifiedBean.toBean(basicRead.split(lineString));
                PreprocessRunUtils.updateLatestTimeSlotData(userTimeSlotLocationMap, tempBean.getUserID(), tempBean.getTimestamp(), tempBean.getAreaIndex()+"");
            }
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, outputDirectoryPath, file.getName()));
            for (Map.Entry<Integer, BasicPair<Long, String>> entry : userTimeSlotLocationMap.entrySet()) {
                basicWrite.writeOneLine(TrajectoryPreprocessRunUtils.toSimpleTrajectoryString(entry));
            }
            basicWrite.endWriting();
        }
    }


    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
//        extract();
        splitByTimeMultiThread();
        formatFileName();
//        transformToSimpleData();
    }



}
