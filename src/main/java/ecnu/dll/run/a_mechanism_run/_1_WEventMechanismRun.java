package ecnu.dll.run.a_mechanism_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes.compared_scheme.w_event_dp.WEventMechanism;
import ecnu.dll.struts.non_personalized_struct.MechanismDetailStruct;
import ecnu.dll.struts.non_personalized_struct.MechanismPartBDetails;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class _1_WEventMechanismRun {
    /**
     * dataList 的外层List代表timestamp，内层list代表user
     */
    @Deprecated
    public static ExperimentResult run(Class clazz, List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, List<StreamCountData> rawPublicationList, Double privacyBudget, Integer windowSize) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor constructor = clazz.getDeclaredConstructor(List.class, Double.class, Integer.class);
        WEventMechanism scheme = (WEventMechanism) constructor.newInstance(dataType, privacyBudget, windowSize);

        ExperimentResult experimentResult = new ExperimentResult();
        int timeUpperBound = dataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double varianceStatistic = 0;
        double divergenceStatistic = 0;
        List<StreamNoiseCountData> publicationList = new ArrayList<>(timeUpperBound);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeUpperBound; i++) {
            scheme.updateNextPublicationResult(dataList.get(i));
            publicationList.add(scheme.getReleaseNoiseCountData());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, scheme.getSimpleName());
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(privacyBudget));
        experimentResult.addPair(Constant.WindowSize, String.valueOf(windowSize));
        for (int i = 0; i < timeUpperBound; i++) {
            rawPublicationData = rawPublicationList.get(i);
            publicationData = publicationList.get(i);
            varianceStatistic += StatisticTool.getVariance(rawPublicationData.getDataMap(), publicationData.getDataMap());
            divergenceStatistic += StatisticTool.getJSDivergence(rawPublicationData.getDataMap(), publicationData.getDataMap());
        }
        varianceStatistic /= timeUpperBound;
        divergenceStatistic /= timeUpperBound;
        experimentResult.addPair(Constant.MRE, String.valueOf(varianceStatistic));
        experimentResult.addPair(Constant.MJSD, String.valueOf(divergenceStatistic));
        return experimentResult;
    }
    public static ExperimentResult runBatch(WEventMechanism scheme, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList, List<StreamCountData> rawPublicationBatchList) {
        Double privacyBudget = scheme.getPrivacyBudget();
        Integer windowSize = scheme.getWindowSize();
        ExperimentResult experimentResult = new ExperimentResult();
        int timeBatchSize = batchDataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double batchTotalVarianceStatistic = 0;
        double batchTotalDivergence = 0;
        double batchTotalWassersteinDistance = 0;
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
            batchTotalWassersteinDistance += StatisticTool.getWassersteinDistance(rawPublicationData.getDataMap(), publicationData.getDataMap());
        }
        experimentResult.addPair(Constant.BRE, String.valueOf(batchTotalVarianceStatistic));
        experimentResult.addPair(Constant.BJSD, String.valueOf(batchTotalDivergence));
        experimentResult.addPair(Constant.BWD, String.valueOf(batchTotalWassersteinDistance));
        return experimentResult;
    }
    public static ExperimentResult runBatchDetails(WEventMechanism scheme, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList, List<StreamCountData> rawPublicationBatchList) {
        Double privacyBudget = scheme.getPrivacyBudget();
        Integer windowSize = scheme.getWindowSize();
        ExperimentResult experimentResult = new ExperimentResult();
        int timeBatchSize = batchDataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double batchTotalVarianceStatistic = 0;
        double batchTotalDivergence = 0;
        double batchTotalWassersteinDistance = 0;

        double batchTotalPartAScale = 0;
        double batchTotalPartBScale = 0;

        List<StreamNoiseCountData> publicationList = new ArrayList<>(timeBatchSize);
        MechanismDetailStruct tempMechanismDetailStruct;
        List<MechanismDetailStruct> mechanismDetailStructList = new ArrayList<>(timeBatchSize);
        boolean tempNonNullStatus;
        Integer partBNonNullCount = 0;
        MechanismPartBDetails tempMechanismPartBDetails;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeBatchSize; i++) {
            tempMechanismDetailStruct = scheme.updateNextPublicationResultDetails(batchDataList.get(i));
            mechanismDetailStructList.add(tempMechanismDetailStruct);
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
            batchTotalWassersteinDistance += StatisticTool.getWassersteinDistance(rawPublicationData.getDataMap(), publicationData.getDataMap());

            tempMechanismDetailStruct = mechanismDetailStructList.get(i);
            batchTotalPartAScale += tempMechanismDetailStruct.getPartAScale();

            tempMechanismPartBDetails = tempMechanismDetailStruct.getMechanismPartBDetails();
            if (tempMechanismPartBDetails.getNonNullStatus()) {
                batchTotalPartBScale += tempMechanismPartBDetails.getScale();
                partBNonNullCount ++;
            }
        }
        experimentResult.addPair(Constant.BRE, String.valueOf(batchTotalVarianceStatistic));
        experimentResult.addPair(Constant.BJSD, String.valueOf(batchTotalDivergence));
        experimentResult.addPair(Constant.BWD, String.valueOf(batchTotalWassersteinDistance));

        // 原本应这样写，但是为了对齐PBA，就采取后面的写法
//        experimentResult.addPair(Constant.PartA_Scale, String.valueOf(batchTotalPartAScale));
//        experimentResult.addPair(Constant.PartB_Scale, String.valueOf(batchTotalPartBScale));
//        experimentResult.addPair(Constant.NonNullCount, String.valueOf(partBNonNullCount));


        experimentResult.addPair(Constant.PartA_BDPVar, String.valueOf(batchTotalPartAScale));
        experimentResult.addPair(Constant.PartB_BDPVar, String.valueOf(batchTotalPartBScale));
        experimentResult.addPair(Constant.PartA_BSampleVar, String.valueOf(0));
        experimentResult.addPair(Constant.PartB_BSampleVar, String.valueOf(0));
        experimentResult.addPair(Constant.PartA_BCountVar, String.valueOf(0));
        experimentResult.addPair(Constant.PartB_BCountVar, String.valueOf(0));
        experimentResult.addPair(Constant.PartA_BiasSquare, String.valueOf(0));
        experimentResult.addPair(Constant.PartB_BiasSquare, String.valueOf(0));
        experimentResult.addPair(Constant.NonNullCount, String.valueOf(partBNonNullCount));


        return experimentResult;
    }



}
