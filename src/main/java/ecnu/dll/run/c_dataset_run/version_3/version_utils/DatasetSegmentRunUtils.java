package ecnu.dll.run.c_dataset_run.version_3.version_utils;

import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll.run.b_parameter_run.basic.version_3.FixedSegmentBasicParameterRun;
import ecnu.dll.run.b_parameter_run.basic.version_3.FixedSegmentInternalParameterRun;
import ecnu.dll.utils.filters.NumberTxtFilter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DatasetSegmentRunUtils {
    public static void basicDatasetRun(String basicPath, String dataTypeFileName, Integer singleBatchSize) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Double> budgetChangeList = ConfigureUtils.getIndependentPrivacyBudgetList("default");
        List<Integer> windowSizeChangeList = ConfigureUtils.getIndependentWindowSizeList("default");

        Double budgetDefault = budgetChangeList.get(2);
        Integer windowSizeDefault = windowSizeChangeList.get(2);

        Runnable tempRunnable;
        Thread tempThread;

        File dirFile = new File(basicPath, "runInput");
        File[] timeStampDataFiles = dirFile.listFiles(new NumberTxtFilter());
        int totalFileSize = timeStampDataFiles.length;

//        Integer segmentUnitSize = 4;
        PureTriple<String, Integer, List<Integer>> independentData = ConfigureUtils.getIndependentData("SegmentUnitSize", "default", "default");
        Integer segmentUnitSize = independentData.getValue();

        Integer startIndex, endIndex;
        Integer segmentID = 0;
        Integer segmentSize = (int) Math.ceil(totalFileSize * 1.0 / segmentUnitSize);
        Integer totalSubThreadSize = segmentSize * (budgetChangeList.size() + windowSizeChangeList.size() - 1);
        CountDownLatch latch = new CountDownLatch(totalSubThreadSize);
        for (int segmentIndex = 0; segmentIndex < totalFileSize; segmentIndex+=segmentUnitSize, ++segmentID) {
            startIndex = segmentIndex;
            endIndex = Math.min(startIndex + segmentUnitSize - 1, totalFileSize - 1);

            CountDownLatch innerLatch = new CountDownLatch(budgetChangeList.size() + windowSizeChangeList.size() - 1);

            for (Double budget : budgetChangeList) {
                tempRunnable =  new FixedSegmentBasicParameterRun(basicPath, dataTypeFileName, singleBatchSize, budget, windowSizeDefault, timeStampDataFiles, startIndex, endIndex, segmentID, latch, innerLatch);
                tempThread = new Thread(tempRunnable);
                tempThread.start();
                System.out.println("Start thread " + tempThread.getName() + " with id " + tempThread.getId() + " in segment " + segmentID);

                //todo: for test
//            break;
            }


            for (int i = 0; i < windowSizeChangeList.size(); i++) {
                if (i == 2) {
                    continue;
                }
                Integer windowSize = windowSizeChangeList.get(i);
                tempRunnable =  new FixedSegmentBasicParameterRun(basicPath, dataTypeFileName, singleBatchSize, budgetDefault, windowSize, timeStampDataFiles, startIndex, endIndex, segmentID, latch, innerLatch);
                tempThread = new Thread(tempRunnable);
                tempThread.start();
                System.out.println("Start thread " + tempThread.getName() + " with id " + tempThread.getId() + " in segment " + segmentID);
            }

            try {
                innerLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void internalDatasetRun(String basicPath, String dataTypeFileName, Integer singleBatchSize) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Double[] budgetChangeArray = ConfigureUtils.getTwoFixedPrivacyBudget();
        Integer[] windowSizeChangeArray = ConfigureUtils.getTwoFixedWindowSize();
        List<Double> ratioList = ConfigureUtils.getIndependentUserRatioList("default");

        Double budgetDefault = budgetChangeArray[1];
        Integer windowSizeDefault = windowSizeChangeArray[1];

        Runnable tempRunnable;
        Thread tempThread;

        File dirFile = new File(basicPath, "runInput");
        File[] timeStampDataFiles = dirFile.listFiles(new NumberTxtFilter());
        int totalFileSize = timeStampDataFiles.length;

        PureTriple<String, Integer, List<Integer>> independentData = ConfigureUtils.getIndependentData("SegmentUnitSize", "default", "default");
        Integer segmentUnitSize = independentData.getValue();

        Integer startIndex, endIndex;
        Integer segmentID = 0;
        Integer segmentSize = (int) Math.ceil(totalFileSize * 1.0 / segmentUnitSize);
        Integer totalSubThreadSize = segmentSize * ratioList.size() * 2;
        CountDownLatch latch = new CountDownLatch(totalSubThreadSize);
        for (int segmentIndex = 0; segmentIndex < totalFileSize; segmentIndex+=segmentUnitSize, ++segmentID) {
            startIndex = segmentIndex;
            endIndex = Math.min(startIndex + segmentUnitSize - 1, totalFileSize - 1);

            CountDownLatch innerLatch = new CountDownLatch(ratioList.size() * 2);

            for (Double userRatio : ratioList) { // for privacy change
                /*
                    String basicPath, String dataTypeFileName, Integer singleBatchSize,
                    Double userRatio, File[] timeStampDataFiles, int startFileIndex,
                    int endFileIndex, Integer segmentID, Boolean changeStatus, CountDownLatch latch,
                    CountDownLatch innerLatch
                 */
                tempRunnable =  new FixedSegmentInternalParameterRun(basicPath, dataTypeFileName, singleBatchSize, userRatio, timeStampDataFiles, startIndex, endIndex, segmentID, FixedSegmentInternalParameterRun.Change_Two_Privacy_Budget_Status, latch, innerLatch);
                tempThread = new Thread(tempRunnable);
                tempThread.start();
                System.out.println("Start thread " + tempThread.getName() + " with id " + tempThread.getId() + " in segment " + segmentID);

                //todo: for test
            }


            for (Double userRatio: ratioList) { // for window size change
                 /*
                    String basicPath, String dataTypeFileName, Integer singleBatchSize,
                    Double userRatio, File[] timeStampDataFiles, int startFileIndex,
                    int endFileIndex, Integer segmentID, Boolean changeStatus, CountDownLatch latch,
                    CountDownLatch innerLatch
                 */
                tempRunnable =  new FixedSegmentInternalParameterRun(basicPath, dataTypeFileName, singleBatchSize, userRatio, timeStampDataFiles, startIndex, endIndex, segmentID, FixedSegmentInternalParameterRun.Change_Two_Window_Size_Status, latch, innerLatch);
                tempThread = new Thread(tempRunnable);
                tempThread.start();
                System.out.println("Start thread " + tempThread.getName() + " with id " + tempThread.getId() + " in segment " + segmentID);
            }

            try {
                innerLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
