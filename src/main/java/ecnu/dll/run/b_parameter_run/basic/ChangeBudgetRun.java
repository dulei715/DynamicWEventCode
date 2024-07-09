package ecnu.dll.run.b_parameter_run.basic;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.pair.PurePair;
import ecnu.dll.run.a_mechanism_run._1_WEventMechanismRun;
import ecnu.dll.run.a_mechanism_run._0_NonPrivacyMechanismRun;
import ecnu.dll.run.a_mechanism_run._2_PersonalizedEventMechanismRun;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.ParameterUtils;
import ecnu.dll.run.a_mechanism_run._3_PersonalizedDynamicEventMechanismRun;
import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes.basic_scheme.NonPrivacyMechanism;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetAbsorption;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetDistribution;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetDistribution;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.DynamicPersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.DynamicPersonalizedBudgetDistribution;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangeBudgetRun {
    @Deprecated
    public static List<ExperimentResult> run(List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, List<Double> privacyBudgetList, Integer windowSize) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.run(dataType, dataList);
        List<StreamCountData> rawPublicationList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);

        Double budgetLowerBound;
        Double budgetUpperBound = ConfigureUtils.getPrivacyBudgetUpperBound();
        Double backwardDifferenceBudgetLowerBound = ConfigureUtils.getBackwardPrivacyBudgetLowerBoundDifference();
        Double backwardDifferenceBudgetUpperBound = ConfigureUtils.getBackwardPrivacyBudgetUpperBoundDifference();
        Integer windowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        Integer windowSizeUpperBound = windowSize;

        int timeStampLength = dataList.size();
        int userSize = dataList.get(0).size();
        int typeSize = ConfigureUtils.getDefaultTypeSize();
        int groupElementSize = (int)Math.ceil(userSize*1.0/typeSize);
        List<Integer> randomWindowSizeList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, windowSizeUpperBound, typeSize, groupElementSize, userSize);
        List<Double> randomBudgetList;
        List<List<Integer>> forwardWindowSizeListList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, randomWindowSizeList, groupElementSize, timeStampLength);
        List<List<Integer>> backwardWindowSizeListList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, randomWindowSizeList, groupElementSize, timeStampLength);
        List<List<Double>> forwardPrivacyBudgetListList;
        List<List<Double>> remainBackwardPrivacyBudgetListList = ParameterUtils.generateRandomDifferenceDoubleList(backwardDifferenceBudgetLowerBound, backwardDifferenceBudgetUpperBound, typeSize, groupElementSize, userSize, timeStampLength);


        // 执行各种机制
        int size = privacyBudgetList.size();
        for (int i = 0; i < size; i++) {
            budgetLowerBound = privacyBudgetList.get(i);
            tempResult = _1_WEventMechanismRun.run(BudgetDistribution.class, dataType, dataList, rawPublicationList, budgetLowerBound, windowSizeUpperBound);
            experimentResultList.add(tempResult);
            tempResult = _1_WEventMechanismRun.run(BudgetAbsorption.class, dataType, dataList, rawPublicationList, budgetLowerBound, windowSizeUpperBound);
            experimentResultList.add(tempResult);

            randomBudgetList = ParameterUtils.generateRandomDoubleList(budgetLowerBound, budgetUpperBound, typeSize, groupElementSize, userSize);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetDistribution.class, dataType, dataList, rawPublicationList, randomBudgetList, randomWindowSizeList);
            experimentResultList.add(tempResult);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetAbsorption.class, dataType, dataList, rawPublicationList, randomBudgetList, randomWindowSizeList);
            experimentResultList.add(tempResult);

            forwardPrivacyBudgetListList = ParameterUtils.generateRandomDoubleList(randomBudgetList, budgetUpperBound, groupElementSize, timeStampLength);
            tempResult = _3_PersonalizedDynamicEventMechanismRun.run(DynamicPersonalizedBudgetDistribution.class, dataType, dataList, rawPublicationList, remainBackwardPrivacyBudgetListList, backwardWindowSizeListList, forwardPrivacyBudgetListList, forwardWindowSizeListList);
            experimentResultList.add(tempResult);

            tempResult = _3_PersonalizedDynamicEventMechanismRun.run(DynamicPersonalizedBudgetAbsorption.class, dataType, dataList, rawPublicationList, remainBackwardPrivacyBudgetListList, backwardWindowSizeListList, forwardPrivacyBudgetListList, forwardWindowSizeListList);
            experimentResultList.add(tempResult);
        }

        return experimentResultList;
    }

    public static List<ExperimentResult> runBatch(Map<String, ? extends Mechanism> mechanismMap, List<String> dataType, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList, List<Double> privacyBudgetList, Integer windowSize, List<Integer> windowSizeList, List<Double>[] batchBudgetListArray,
                                                  List<List<Integer>> forwardWindowSizeListBatchList, List<List<Integer>> backwardWindowSizeListBatchList, List<List<Double>>[] forwardPrivacyBudgetListBatchListArray,
                                                  List<List<Double>> remainBackwardPrivacyBudgetListBatchList) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        NonPrivacyMechanism nonPrivacyScheme = new NonPrivacyMechanism(dataType);
        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.runBatch(nonPrivacyScheme, batchID, batchDataList);
        List<StreamCountData> rawPublicationBatchList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);

        Double budgetLowerBound;
        Double budgetUpperBound = ConfigureUtils.getPrivacyBudgetUpperBound();
        Double backwardDifferenceBudgetLowerBound = ConfigureUtils.getBackwardPrivacyBudgetLowerBoundDifference();
        Double backwardDifferenceBudgetUpperBound = ConfigureUtils.getBackwardPrivacyBudgetUpperBoundDifference();
        Integer windowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        Integer windowSizeUpperBound = windowSize;

        int timeBatchSize = batchDataList.size();
        int userSize = batchDataList.get(0).size();
        int typeSize = ConfigureUtils.getDefaultTypeSize();
//        int groupElementSize = (int)Math.ceil(userSize*1.0/typeSize);
//        List<Integer> windowSizeList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, windowSizeUpperBound, typeSize, groupElementSize, userSize);
        List<Double> batchBudgetList;
//        List<List<Integer>> forwardWindowSizeListBatchList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, windowSizeList, groupElementSize, timeBatchSize);
//        List<List<Integer>> backwardWindowSizeListBatchList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, windowSizeList, groupElementSize, timeBatchSize);
        List<List<Double>> forwardPrivacyBudgetListBatchList;
//        List<List<Double>> remainBackwardPrivacyBudgetListBatchList = ParameterUtils.generateRandomDifferenceDoubleList(backwardDifferenceBudgetLowerBound, backwardDifferenceBudgetUpperBound, typeSize, groupElementSize, userSize, timeBatchSize);


        // 执行各种机制
        int size = privacyBudgetList.size();
        for (int i = 0; i < size; i++) {
            budgetLowerBound = privacyBudgetList.get(i);
            tempResult = _1_WEventMechanismRun.runBatch((BudgetDistribution)mechanismMap.get("budgetDistribution"), batchID, batchDataList, rawPublicationBatchList, budgetLowerBound, windowSizeUpperBound);
            experimentResultList.add(tempResult);
            // todo
            tempResult = _1_WEventMechanismRun.run(BudgetAbsorption.class, dataType, batchDataList, rawPublicationBatchList, budgetLowerBound, windowSizeUpperBound);
            experimentResultList.add(tempResult);

//            batchBudgetList = ParameterUtils.generateRandomDoubleList(budgetLowerBound, budgetUpperBound, typeSize, groupElementSize, userSize);
            batchBudgetList = batchBudgetListArray[i];
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetDistribution.class, dataType, batchDataList, rawPublicationBatchList, batchBudgetList, windowSizeList);
            experimentResultList.add(tempResult);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetAbsorption.class, dataType, batchDataList, rawPublicationBatchList, batchBudgetList, windowSizeList);
            experimentResultList.add(tempResult);

//            forwardPrivacyBudgetListBatchList = ParameterUtils.generateRandomDoubleList(randomBudgetList, budgetUpperBound, groupElementSize, timeBatchSize);
            forwardPrivacyBudgetListBatchList = forwardPrivacyBudgetListBatchListArray[i];
            tempResult = _3_PersonalizedDynamicEventMechanismRun.run(DynamicPersonalizedBudgetDistribution.class, dataType, batchDataList, rawPublicationBatchList, remainBackwardPrivacyBudgetListBatchList, backwardWindowSizeListBatchList, forwardPrivacyBudgetListBatchList, forwardWindowSizeListBatchList);
            experimentResultList.add(tempResult);

            tempResult = _3_PersonalizedDynamicEventMechanismRun.run(DynamicPersonalizedBudgetAbsorption.class, dataType, batchDataList, rawPublicationBatchList, remainBackwardPrivacyBudgetListBatchList, backwardWindowSizeListBatchList, forwardPrivacyBudgetListBatchList, forwardWindowSizeListBatchList);
            experimentResultList.add(tempResult);
        }

        return experimentResultList;
    }
}
