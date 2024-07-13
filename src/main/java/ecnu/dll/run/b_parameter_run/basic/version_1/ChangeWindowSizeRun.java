package ecnu.dll.run.b_parameter_run.basic.version_1;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.pair.PurePair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.ParameterUtils;
import ecnu.dll.run.a_mechanism_run._0_NonPrivacyMechanismRun;
import ecnu.dll.run.a_mechanism_run._1_WEventMechanismRun;
import ecnu.dll.run.a_mechanism_run._2_PersonalizedEventMechanismRun;
import ecnu.dll.run.a_mechanism_run._3_PersonalizedDynamicEventMechanismRun;
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

public class ChangeWindowSizeRun {
    @Deprecated
    public static List<ExperimentResult> run(List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, Double privacyBudget, List<Integer> windowSizeList) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.run(dataType, dataList);
        List<StreamCountData> rawPublicationList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);

        Double budgetLowerBound = privacyBudget;
        Double budgetUpperBound = ConfigureUtils.getPrivacyBudgetUpperBound();
        Double backwardDifferenceBudgetLowerBound = ConfigureUtils.getBackwardPrivacyBudgetLowerBoundDifference();
        Double backwardDifferenceBudgetUpperBound = ConfigureUtils.getBackwardPrivacyBudgetUpperBoundDifference();
        Integer windowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        Integer windowSizeUpperBound;

        int timeStampLength = dataList.size();
        int userSize = dataList.get(0).size();
        int typeSize = ConfigureUtils.getDefaultUserTypeSize();
        int groupElementSize = (int)Math.ceil(userSize*1.0/typeSize);
        List<Integer> randomWindowSizeList;
        List<Double> randomBudgetList = ParameterUtils.generateRandomDoubleList(budgetLowerBound, budgetUpperBound, typeSize, groupElementSize, userSize);
        List<List<Integer>> forwardWindowSizeListList;
        List<List<Integer>> backwardWindowSizeListList;
        List<List<Double>> forwardPrivacyBudgetListList = ParameterUtils.generateRandomDoubleList(randomBudgetList, budgetUpperBound, groupElementSize, timeStampLength);
        List<List<Double>> remainBackwardPrivacyBudgetListList = ParameterUtils.generateRandomDifferenceDoubleList(backwardDifferenceBudgetLowerBound, backwardDifferenceBudgetUpperBound, typeSize, groupElementSize, userSize, timeStampLength);


        // 执行各种机制
        int size = windowSizeList.size();
        for (int i = 0; i < size; i++) {
            windowSizeUpperBound = windowSizeList.get(i);
            tempResult = _1_WEventMechanismRun.run(BudgetDistribution.class, dataType, dataList, rawPublicationList, budgetLowerBound, windowSizeUpperBound);
            experimentResultList.add(tempResult);
            tempResult = _1_WEventMechanismRun.run(BudgetAbsorption.class, dataType, dataList, rawPublicationList, budgetLowerBound, windowSizeUpperBound);
            experimentResultList.add(tempResult);

            randomWindowSizeList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, windowSizeUpperBound, typeSize, groupElementSize, userSize);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetDistribution.class, dataType, dataList, rawPublicationList, randomBudgetList, randomWindowSizeList);
            experimentResultList.add(tempResult);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetAbsorption.class, dataType, dataList, rawPublicationList, randomBudgetList, randomWindowSizeList);
            experimentResultList.add(tempResult);

            forwardWindowSizeListList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, randomWindowSizeList, groupElementSize, timeStampLength);
            backwardWindowSizeListList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, randomWindowSizeList, groupElementSize, timeStampLength);
            tempResult = _3_PersonalizedDynamicEventMechanismRun.run(DynamicPersonalizedBudgetDistribution.class, dataType, dataList, rawPublicationList, remainBackwardPrivacyBudgetListList, backwardWindowSizeListList, forwardPrivacyBudgetListList, forwardWindowSizeListList);
            experimentResultList.add(tempResult);

            tempResult = _3_PersonalizedDynamicEventMechanismRun.run(DynamicPersonalizedBudgetAbsorption.class, dataType, dataList, rawPublicationList, remainBackwardPrivacyBudgetListList, backwardWindowSizeListList, forwardPrivacyBudgetListList, forwardWindowSizeListList);
            experimentResultList.add(tempResult);
        }

        return experimentResultList;
    }
//    public static List<ExperimentResult> runBatch(Map<String, ? extends Mechanism> schemeMap, List<String> dataType, Integer batchID, List<List<StreamDataElement<Boolean>>> dataList,
//                                                  Double privacyBudget, List<Integer> windowSizeList,
//                                                  List<Double> batchPersonalizedPrivacyBudget, List<List<Integer>> batchPersonalizedWindowSizeList,
//                                                  List<List<Double>> batchForwardPrivacyBudget, List<List<List<Integer>>> batchForwardWindowSizeList,
//                                                  List<List<Double>> batchRemainBackwardPrivacyBudget, List<List<List<Integer>>> batchBackwardWindowSizeList) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.run(dataType, dataList);
//        List<StreamCountData> rawPublicationList = nonPrivacySchemeResultPair.getValue();
//        List<ExperimentResult> experimentResultList = new ArrayList<>();
//        ExperimentResult tempResult;
//
//        tempResult = nonPrivacySchemeResultPair.getKey();
//        experimentResultList.add(tempResult);
//
//        Double budgetLowerBound = privacyBudget;
//        Integer windowSizeUpperBound;
//
//        List<Integer> batchPersonalizedWindowSize;
//        List<List<Integer>> batchForwardWindowSize;
//        List<List<Integer>> batchBackwardWindowSize;
//
//
//        // 执行各种机制
//        int size = windowSizeList.size();
//        for (int i = 0; i < size; i++) {
//            windowSizeUpperBound = windowSizeList.get(i);
//            tempResult = _1_WEventMechanismRun.runBatch((BudgetDistribution)schemeMap.get(Constant.BudgetDistributionSchemeName), batchID, dataList, rawPublicationList, budgetLowerBound, windowSizeUpperBound);
//            experimentResultList.add(tempResult);
//            tempResult = _1_WEventMechanismRun.runBatch((BudgetAbsorption)schemeMap.get(Constant.BudgetAbsorptionSchemeName), batchID, dataList, rawPublicationList, budgetLowerBound, windowSizeUpperBound);
//            experimentResultList.add(tempResult);
//
//            batchPersonalizedWindowSize = batchPersonalizedWindowSizeList.get(i);
//            tempResult = _2_PersonalizedEventMechanismRun.runBatch((PersonalizedBudgetDistribution)schemeMap.get(Constant.PersonalizedBudgetDistributionSchemeName), batchID, dataList, rawPublicationList, batchPersonalizedPrivacyBudget, batchPersonalizedWindowSize);
//            experimentResultList.add(tempResult);
//            tempResult = _2_PersonalizedEventMechanismRun.runBatch((PersonalizedBudgetAbsorption)schemeMap.get(Constant.PersonalizedBudgetAbsorptionSchemeName), batchID, dataList, rawPublicationList, batchPersonalizedPrivacyBudget, batchPersonalizedWindowSize);
//            experimentResultList.add(tempResult);
//
//            batchForwardWindowSize = batchForwardWindowSizeList.get(i);
//            batchBackwardWindowSize = batchBackwardWindowSizeList.get(i);
//            tempResult = _3_PersonalizedDynamicEventMechanismRun.runBatch((DynamicPersonalizedBudgetDistribution)schemeMap.get(Constant.DynamicPersonalizedBudgetDistributionSchemeName), batchID, dataList, rawPublicationList, batchRemainBackwardPrivacyBudget, batchBackwardWindowSize, batchForwardPrivacyBudget, batchForwardWindowSize);
//            experimentResultList.add(tempResult);
//
//            tempResult = _3_PersonalizedDynamicEventMechanismRun.runBatch((DynamicPersonalizedBudgetAbsorption)schemeMap.get(Constant.DynamicPersonalizedBudgetAbsorptionSchemeName), batchID, dataList, rawPublicationList, batchRemainBackwardPrivacyBudget, batchBackwardWindowSize, batchForwardPrivacyBudget, batchForwardWindowSize);
//            experimentResultList.add(tempResult);
//        }
//
//        return experimentResultList;
//    }
}
