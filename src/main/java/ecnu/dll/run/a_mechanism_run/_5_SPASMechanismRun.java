package ecnu.dll.run.a_mechanism_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes.compared_scheme.w_event_dp.SPAS;
import ecnu.dll.schemes.compared_scheme.w_event_dp.WEventMechanism;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class _5_SPASMechanismRun {
    /**
     * dataList 的外层List代表timestamp，内层list代表user
     */
    public static ExperimentResult runBatch(SPAS scheme, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList, List<StreamCountData> rawPublicationBatchList) {
        Double privacyBudget = scheme.getPrivacyBudget();
        Integer windowSize = scheme.getWindowSize();
        ExperimentResult experimentResult = new ExperimentResult();
        int timeBatchSize = batchDataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double batchTotalVarianceStatistic = 0;
        double batchTotalDivergence = 0;
        double batchWassersteinDistance = 0;
        List<StreamNoiseCountData> publicationList = new ArrayList<>(timeBatchSize);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeBatchSize; i++) {
            scheme.updateNextPublicationResult(batchDataList.get(i));
            publicationList.add(scheme.getReleaseNoiseCountData());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, scheme.getSimpleName());
        experimentResult.addPair(Constant.BatchName, String.valueOf(batchID));
        experimentResult.addPair(Constant.BatchRealSize, String.valueOf(timeBatchSize));
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(privacyBudget));
        experimentResult.addPair(Constant.WindowSize, String.valueOf(windowSize));

        for (int i = 0; i < timeBatchSize; i++) {
            rawPublicationData = rawPublicationBatchList.get(i);
            publicationData = publicationList.get(i);
            batchTotalVarianceStatistic += StatisticTool.getVariance(rawPublicationData.getDataMap(), publicationData.getDataMap());
            batchTotalDivergence += StatisticTool.getJSDivergence(rawPublicationData.getDataMap(), publicationData.getDataMap());
            batchWassersteinDistance += StatisticTool.getWassersteinDistance(rawPublicationData.getDataMap(), publicationData.getDataMap());
        }
        experimentResult.addPair(Constant.BRE, String.valueOf(batchTotalVarianceStatistic));
        experimentResult.addPair(Constant.BJSD, String.valueOf(batchTotalDivergence));
        experimentResult.addPair(Constant.BWD, String.valueOf(batchWassersteinDistance));
        return experimentResult;
    }

}
