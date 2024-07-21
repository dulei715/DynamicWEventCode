package ecnu.dll.run.c_dataset_run.version_3.version_utils;

import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll.run.b_parameter_run.basic.version_2.FixedParameterRun;
import ecnu.dll.run.b_parameter_run.basic.version_3.FixedSegmentParameterRun;
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
                tempRunnable =  new FixedSegmentParameterRun(basicPath, dataTypeFileName, singleBatchSize, budget, windowSizeDefault, timeStampDataFiles, startIndex, endIndex, segmentID, latch, innerLatch);
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
                tempRunnable =  new FixedSegmentParameterRun(basicPath, dataTypeFileName, singleBatchSize, budgetDefault, windowSize, timeStampDataFiles, startIndex, endIndex, segmentID, latch, innerLatch);
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
