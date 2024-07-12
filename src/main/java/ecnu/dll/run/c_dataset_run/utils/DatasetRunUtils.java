package ecnu.dll.run.c_dataset_run.utils;

import ecnu.dll._config.ConfigureUtils;
import ecnu.dll.run.b_parameter_run.basic.FixedParameterRun;

import java.util.List;

public class DatasetRunUtils {
    public static void basicDatasetRun(String basicPath, String dataTypeFileName, Integer singleBatchSize) {
        List<Double> budgetChangeList = ConfigureUtils.getIndependentPrivacyBudgetList("default");
        List<Integer> windowSizeChangeList = ConfigureUtils.getIndependentWindowSizeList("default");

        Double budgetDefault = budgetChangeList.get(2);
        Integer windowSizeDefault = windowSizeChangeList.get(2);

        Runnable tempRunnable;
        Thread tempThread;

        for (Double budget : budgetChangeList) {
            tempRunnable =  new FixedParameterRun(basicPath, dataTypeFileName, singleBatchSize, budget, windowSizeDefault);
            tempThread = new Thread(tempRunnable);
            tempThread.start();
            System.out.println("Start thread " + tempThread.getName() + " with id " + tempThread.getId());

            //todo: for test
//            break;
        }


        for (int i = 0; i < windowSizeChangeList.size(); i++) {
            if (i == 2) {
                continue;
            }
            Integer windowSize = windowSizeChangeList.get(i);
            tempRunnable =  new FixedParameterRun(basicPath, dataTypeFileName, singleBatchSize, budgetDefault, windowSize);
            tempThread = new Thread(tempRunnable);
            tempThread.start();
            System.out.println("Start thread " + tempThread.getName() + " with id " + tempThread.getId());
        }

        

    }
}
