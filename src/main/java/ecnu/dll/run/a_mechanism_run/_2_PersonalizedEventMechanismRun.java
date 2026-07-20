package ecnu.dll.run.a_mechanism_run;

import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.cdp.PersonalizedEventMechanism;
import ecnu.dll.struts.personalized_struct.PersonalizedMechanismDetailStruct;
import ecnu.dll.struts.personalized_struct.PersonalizedMechanismPartADetailStruct;
import ecnu.dll.struts.personalized_struct.PersonalizedMechanismPartBDetailStruct;
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
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(ListUtils.getMinimalValue(privacyBudgetList)));
        experimentResult.addPair(Constant.WindowSize, String.valueOf(ListUtils.getMaximalValue(windowSizeList, 1)));
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

    public static ExperimentResult runBatch(PersonalizedEventMechanism scheme, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList, List<StreamCountData> rawPublicationBatchList) {
        List<Double> privacyBudgetList = scheme.getPrivacyBudgetList();
        List<Integer> windowSizeList = scheme.getWindowSizeList();
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
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(ListUtils.getMinimalValue(privacyBudgetList)));
        int maximalWindowSizeValue = ListUtils.getMaximalValue(windowSizeList, 1);
        experimentResult.addPair(Constant.WindowSize, String.valueOf(maximalWindowSizeValue));
        for (int i = 0; i < timeBatchSize; i++) {
            rawPublicationData = rawPublicationBatchList.get(i);
            publicationData = publicationList.get(i);
            batchTotalVarianceStatistic += StatisticTool.getVariance(rawPublicationData.getDataMap(), publicationData.getDataMap());
            batchTotalDivergence += StatisticTool.getJSDivergence(rawPublicationData.getDataMap(), publicationData.getDataMap());
            batchTotalWassersteinDistance += StatisticTool.getWassersteinDistance(rawPublicationData.getDataMap(), publicationData.getDataMap());
        }
//        batchTotalVarianceStatistic /= timeUpperBound;
        experimentResult.addPair(Constant.BRE, String.valueOf(batchTotalVarianceStatistic));
        experimentResult.addPair(Constant.BJSD, String.valueOf(batchTotalDivergence));
        experimentResult.addPair(Constant.BWD, String.valueOf(batchTotalWassersteinDistance));
        return experimentResult;
    }
    public static ExperimentResult runBatchDetails(PersonalizedEventMechanism scheme, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList, List<StreamCountData> rawPublicationBatchList) {
        List<Double> privacyBudgetList = scheme.getPrivacyBudgetList();
        List<Integer> windowSizeList = scheme.getWindowSizeList();
        ExperimentResult experimentResult = new ExperimentResult();
        int timeBatchSize = batchDataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double batchTotalVarianceStatistic = 0;
        double batchTotalDivergence = 0;
        double batchTotalWassersteinDistance = 0;
        double batchPartATotalDPVarianceStatistic = 0, batchPartBTotalDPVarianceStatistic = 0;
        double batchPartATotalSampleVarianceStatistic = 0, batchPartBTotalSampleVarianceStatistic = 0;
        double batchPartATotalCountVar = 0, batchPartBTotalCountVar = 0;
        double batchPartATotalBiasSquare = 0, batchPartBTotalBiasSquare = 0;
        int partBNonNullCount = 0;
        boolean nullNonStatus;
        List<StreamNoiseCountData> publicationList = new ArrayList<>(timeBatchSize);
        List<PersonalizedMechanismDetailStruct> personalizedMechanismDetailStructList = new ArrayList<>(timeBatchSize);
        PersonalizedMechanismDetailStruct tempPersonalizedMechanismDetailStruct;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeBatchSize; i++) {
            tempPersonalizedMechanismDetailStruct = scheme.updateNextPublicationResultDetails(batchDataList.get(i));
            personalizedMechanismDetailStructList.add(tempPersonalizedMechanismDetailStruct);
            publicationList.add(scheme.getReleaseNoiseCountData());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, scheme.getSimpleName());
        experimentResult.addPair(Constant.BatchName, String.valueOf(batchID));
        experimentResult.addPair(Constant.BatchRealSize, String.valueOf(timeBatchSize));
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(ListUtils.getMinimalValue(privacyBudgetList)));
        int maximalWindowSizeValue = ListUtils.getMaximalValue(windowSizeList, 1);
        experimentResult.addPair(Constant.WindowSize, String.valueOf(maximalWindowSizeValue));

        PersonalizedMechanismPartADetailStruct tempPersonalizedMechanismPartADetailStruct;
        PersonalizedMechanismPartBDetailStruct tempPersonalizedMechanismPartBDetailStruct;
        for (int i = 0; i < timeBatchSize; i++) {
            rawPublicationData = rawPublicationBatchList.get(i);
            publicationData = publicationList.get(i);
            batchTotalVarianceStatistic += StatisticTool.getVariance(rawPublicationData.getDataMap(), publicationData.getDataMap());
            batchTotalDivergence += StatisticTool.getJSDivergence(rawPublicationData.getDataMap(), publicationData.getDataMap());
            batchTotalWassersteinDistance += StatisticTool.getWassersteinDistance(rawPublicationData.getDataMap(), publicationData.getDataMap());

            tempPersonalizedMechanismDetailStruct = personalizedMechanismDetailStructList.get(i);
            tempPersonalizedMechanismPartADetailStruct = tempPersonalizedMechanismDetailStruct.getMechanismPartADetailStruct();
            tempPersonalizedMechanismPartBDetailStruct = tempPersonalizedMechanismDetailStruct.getMechanismPartBDetailStruct();
            // DPError
            batchPartATotalDPVarianceStatistic += tempPersonalizedMechanismPartADetailStruct.getErrorDetails().getDpError();

            // SampleError
            batchPartATotalSampleVarianceStatistic += tempPersonalizedMechanismPartADetailStruct.getErrorDetails().getSampleErrorStruct().getSampleError();

            // CountVar
            batchPartATotalCountVar += tempPersonalizedMechanismPartADetailStruct.getErrorDetails().getSampleErrorStruct().getCountVar();


            // BiasSquare
            batchPartATotalBiasSquare += tempPersonalizedMechanismPartADetailStruct.getErrorDetails().getSampleErrorStruct().getBiasSquare();

            if (tempPersonalizedMechanismPartBDetailStruct.getNonNullStatus()) {
                partBNonNullCount++;
                batchPartBTotalDPVarianceStatistic += tempPersonalizedMechanismPartBDetailStruct.getErrorDetails().getDpError();
                batchPartBTotalSampleVarianceStatistic += tempPersonalizedMechanismPartBDetailStruct.getErrorDetails().getSampleErrorStruct().getSampleError();
                batchPartBTotalCountVar += tempPersonalizedMechanismPartBDetailStruct.getErrorDetails().getSampleErrorStruct().getCountVar();
                batchPartBTotalBiasSquare += tempPersonalizedMechanismPartBDetailStruct.getErrorDetails().getSampleErrorStruct().getBiasSquare();
            }

        }
//        batchTotalVarianceStatistic /= timeUpperBound;
        experimentResult.addPair(Constant.BRE, String.valueOf(batchTotalVarianceStatistic));
        experimentResult.addPair(Constant.BJSD, String.valueOf(batchTotalDivergence));
        experimentResult.addPair(Constant.BWD, String.valueOf(batchTotalWassersteinDistance));

        experimentResult.addPair(Constant.PartA_BDPVar, String.valueOf(batchPartATotalDPVarianceStatistic));
        experimentResult.addPair(Constant.PartB_BDPVar, String.valueOf(batchPartBTotalDPVarianceStatistic));
        experimentResult.addPair(Constant.PartA_BSampleVar, String.valueOf(batchPartATotalSampleVarianceStatistic));
        experimentResult.addPair(Constant.PartB_BSampleVar, String.valueOf(batchPartBTotalSampleVarianceStatistic));
        experimentResult.addPair(Constant.PartA_BCountVar, String.valueOf(batchPartATotalCountVar));
        experimentResult.addPair(Constant.PartB_BCountVar, String.valueOf(batchPartBTotalCountVar));
        experimentResult.addPair(Constant.PartA_BiasSquare, String.valueOf(batchPartATotalBiasSquare));
        experimentResult.addPair(Constant.PartB_BiasSquare, String.valueOf(batchPartBTotalBiasSquare));
        experimentResult.addPair(Constant.NonNullCount, String.valueOf(partBNonNullCount));
        return experimentResult;
    }
}
