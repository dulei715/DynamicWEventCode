package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.real_dataset.sub_thread.trajectory_sub_thead;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll.dataset.real.datasetA.handled_struct.TrajectorySimplifiedBean;

import java.io.File;
import java.util.*;
@Deprecated
public class TestTrajectorySplitByTimeSubThread implements Runnable{
    private int cacheSize;
    private int timeStamp;
    private String outputDirectoryPath;
    private Long threadStartTrajectoryTimeSlot;
    private Long threadEndTrajectoryTimeSlot;
    private Long timeInterval;
    private File[] inputFiles;

    public TestTrajectorySplitByTimeSubThread(int cacheSize, int timeStamp, String outputDirectoryPath, Long threadStartTrajectoryTimeSlot, Long threadEndTrajectoryTimeSlot, Long timeInterval, File[] inputFiles) {
        this.cacheSize = cacheSize;
        this.timeStamp = timeStamp;
        this.outputDirectoryPath = outputDirectoryPath;
        this.threadStartTrajectoryTimeSlot = threadStartTrajectoryTimeSlot;
        this.threadEndTrajectoryTimeSlot = threadEndTrajectoryTimeSlot;
        this.timeInterval = timeInterval;
        this.inputFiles = inputFiles;
    }

    private void subSplitByTime() {
//        BasicRead basicRead = new BasicRead(",");
//        BasicWrite basicWrite = new BasicWrite(",");
        List<String> tempDataList;

        // 记录第t个时间间隔内每个user对应的country名
        Map<Integer, List<String>> cacheMap = new TreeMap<>();
        List<String> tempTimeSlotDataList;
        Map<Long, Integer> helpMap = new HashMap<>();
        String[] tempStringArray;
        long cacheLeft = threadStartTrajectoryTimeSlot;
        long cacheRight;
        long timeSlotRightBound, tempUserTime;
        TrajectorySimplifiedBean tempBean;
//        int tempTimeStamp;
        while (cacheLeft <= threadEndTrajectoryTimeSlot) {
            if (timeStamp == 1770) {
                System.out.println("TimeStamp 1770");
            }
            cacheMap.clear();
            helpMap.clear();
            cacheRight = cacheLeft + cacheSize * timeInterval;
//            tempLatestUserTimeSlotLocationMap = new HashMap<>();
            for (long tempTimeSlot = cacheLeft; tempTimeSlot < cacheRight && tempTimeSlot <= threadEndTrajectoryTimeSlot; tempTimeSlot += timeInterval) {
                cacheMap.put(timeStamp, new ArrayList<>());
                helpMap.put(tempTimeSlot, timeStamp);
                ++timeStamp;
            }

            for (File inputFile : inputFiles) {
//                basicRead.startReading(inputFile.getAbsolutePath());
//                tempDataList = basicRead.readAllWithoutLineNumberRecordInFile();
//                basicRead.endReading();
                for (long timeSlotLeftBound = cacheLeft; timeSlotLeftBound < cacheRight && timeSlotLeftBound <= threadEndTrajectoryTimeSlot; timeSlotLeftBound += timeInterval) {
                    timeSlotRightBound = timeSlotLeftBound + timeInterval;
                    tempTimeSlotDataList = cacheMap.get(helpMap.get(timeSlotLeftBound));
//                    System.out.println("filter data");

                }

            }
            // 遍历cacheMap写文件
            Integer tempTimeStamp;
            String tempOutputTimeStampPath;
            for (Map.Entry<Integer, List<String>> cacheEntry : cacheMap.entrySet()) {
                tempTimeStamp = cacheEntry.getKey();
                tempTimeSlotDataList = cacheEntry.getValue();
                tempOutputTimeStampPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputDirectoryPath, String.format("timestamp_%d.txt", tempTimeStamp));
                System.out.println("write data to file " + String.format("timestamp_%d.txt", tempTimeStamp));
            }
            cacheLeft = cacheRight;
        }


    }
    @Override
    public void run() {
        System.out.println("start run...");
        subSplitByTime();
        System.out.println("end run...");
    }
}
