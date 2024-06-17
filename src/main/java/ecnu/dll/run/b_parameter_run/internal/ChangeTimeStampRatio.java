package ecnu.dll.run.b_parameter_run.internal;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.pair.IdentityPurePair;
import cn.edu.dll.struct.pair.PurePair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.ParameterUtils;
import ecnu.dll.run.a_mechanism_run._0_NonPrivacyMechanismRun;
import ecnu.dll.run.a_mechanism_run._3_PersonalizedDynamicEventMechanismRun;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.DynamicPersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.DynamicPersonalizedBudgetDistribution;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ChangeTimeStampRatio {
    public static List<ExperimentResult> twoBudgetRun(List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, IdentityPurePair<Double> privacyBudgetPair, Integer windowSize, List<Double> budgetTimeStampRatioList) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.run(dataType, dataList);
        List<StreamCountData> rawPublicationList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);

        Double firstBudgetTimeStampLengthRatio;
        Double backwardDifferenceBudget = ConfigureUtils.getBackwardPrivacyBudgetUpperBoundDifference();
        Integer windowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        Integer windowSizeUpperBound = windowSize;

        int timeStampLength = dataList.size();
        int userSize = dataList.get(0).size();
        int typeSize = 1;
        int groupElementSize = userSize;
        List<Integer> randomWindowSizeList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, windowSizeUpperBound, typeSize, groupElementSize, userSize);
        List<List<Integer>> forwardWindowSizeListList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, randomWindowSizeList, groupElementSize, timeStampLength);
        List<List<Integer>> backwardWindowSizeListList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, randomWindowSizeList, groupElementSize, timeStampLength);
        List<List<Double>> forwardPrivacyBudgetListList;
        List<List<Double>> remainBackwardPrivacyBudgetListList = ParameterUtils.generateRandomDifferenceDoubleList(backwardDifferenceBudget, backwardDifferenceBudget, typeSize, groupElementSize, userSize, timeStampLength);


        // 执行各种机制
        int size = budgetTimeStampRatioList.size();
        for (int i = 0; i < size; i++) {
            firstBudgetTimeStampLengthRatio = budgetTimeStampRatioList.get(i);
            forwardPrivacyBudgetListList = ParameterUtils.generateBudgetListListWithIdentityUsersAndTwoTypeBudgetsInRandomTimestamps(privacyBudgetPair, userSize, timeStampLength, firstBudgetTimeStampLengthRatio);
            tempResult = _3_PersonalizedDynamicEventMechanismRun.run(DynamicPersonalizedBudgetDistribution.class, dataType, dataList, rawPublicationList, remainBackwardPrivacyBudgetListList, backwardWindowSizeListList, forwardPrivacyBudgetListList, forwardWindowSizeListList);
            experimentResultList.add(tempResult);

            tempResult = _3_PersonalizedDynamicEventMechanismRun.run(DynamicPersonalizedBudgetAbsorption.class, dataType, dataList, rawPublicationList, remainBackwardPrivacyBudgetListList, backwardWindowSizeListList, forwardPrivacyBudgetListList, forwardWindowSizeListList);
            experimentResultList.add(tempResult);
        }

        return experimentResultList;
    }
}
