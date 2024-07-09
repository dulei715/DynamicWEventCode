package ecnu.dll.run.a_mechanism_run;

import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes.compared_scheme.w_event.WEventMechanism;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedEventMechanism;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class _2_PersonalizedEventMechanismRun {
    /**
     * dataList 的外层List代表timestamp，内层list代表user
     */
    @Deprecated
    public static ExperimentResult run(Class clazz, List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, List<StreamCountData> rawPublicationList, List<Double> privacyBudgetList, List<Integer> windowSizeList) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor constructor = clazz.getDeclaredConstructor(List.class, List.class, List.class);
//        WEventMechanism scheme = new BudgetDistribution(dataType, privacyBudget, windowSize);
        PersonalizedEventMechanism scheme = (PersonalizedEventMechanism) constructor.newInstance(dataType, privacyBudgetList, windowSizeList);

        ExperimentResult experimentResult = new ExperimentResult();
        int timeUpperBound = dataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double varianceStatistic = 0;
        List<StreamNoiseCountData> publicationList = new ArrayList<>(timeUpperBound);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeUpperBound; i++) {
            scheme.updateNextPublicationResult(dataList.get(i));
            publicationList.add(scheme.getReleaseNoiseCountMap());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, scheme.getSimpleName());
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(ListUtils.getMinimalValue(privacyBudgetList)));
        experimentResult.addPair(Constant.WindowSize, String.valueOf(ListUtils.getMaximalValue(windowSizeList, 1)));
        for (int i = 0; i < timeUpperBound; i++) {
            rawPublicationData = rawPublicationList.get(i);
            publicationData = publicationList.get(i);
            varianceStatistic += StatisticTool.getVariance(rawPublicationData.getDataMap(), publicationData.getDataMap());
        }
        varianceStatistic /= timeUpperBound;
        experimentResult.addPair(Constant.MRE, String.valueOf(varianceStatistic));
        return experimentResult;
    }

    public static ExperimentResult runBatch(PersonalizedEventMechanism scheme, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList, List<StreamCountData> rawPublicationBatchList, List<Double> privacyBudgetList, List<Integer> windowSizeList) {
//        Constructor constructor = clazz.getDeclaredConstructor(List.class, List.class, List.class);
//        PersonalizedEventMechanism scheme = (PersonalizedEventMechanism) constructor.newInstance(dataType, privacyBudgetList, windowSizeList);

        ExperimentResult experimentResult = new ExperimentResult();
        int timeBatchSize = batchDataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double batchTotalVarianceStatistic = 0;
        List<StreamNoiseCountData> publicationList = new ArrayList<>(timeBatchSize);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeBatchSize; i++) {
            scheme.updateNextPublicationResult(batchDataList.get(i));
            publicationList.add(scheme.getReleaseNoiseCountMap());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, scheme.getSimpleName());
        experimentResult.addPair(Constant.BatchName, String.valueOf(batchID));
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(ListUtils.getMinimalValue(privacyBudgetList)));
        experimentResult.addPair(Constant.WindowSize, String.valueOf(ListUtils.getMaximalValue(windowSizeList, 1)));
        for (int i = 0; i < timeBatchSize; i++) {
            rawPublicationData = rawPublicationBatchList.get(i);
            publicationData = publicationList.get(i);
            batchTotalVarianceStatistic += StatisticTool.getVariance(rawPublicationData.getDataMap(), publicationData.getDataMap());
        }
//        batchTotalVarianceStatistic /= timeUpperBound;
        experimentResult.addPair(Constant.BRE, String.valueOf(batchTotalVarianceStatistic));
        return experimentResult;
    }
}
