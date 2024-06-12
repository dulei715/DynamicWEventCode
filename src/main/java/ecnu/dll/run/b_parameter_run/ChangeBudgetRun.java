package ecnu.dll.run.b_parameter_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.pair.PurePair;
import ecnu.dll.run.a_mechanism_run._1_WEventMechanismRun;
import ecnu.dll.run.a_mechanism_run._0_NonPrivacyMechanismRun;
import ecnu.dll.run.a_mechanism_run._2_PersonalizedEventMechanismRun;
import ecnu.dll.schemes._scheme_utils.ConfigureUtils;
import ecnu.dll.schemes._scheme_utils.ParameterUtils;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetAbsorption;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetDistribution;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetDistribution;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ChangeBudgetRun {
    public static List<ExperimentResult> run(List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, List<Double> privacyBudgetList, Integer windowSize) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.run(dataType, dataList);
        List<StreamCountData> rawPublicationList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);

        Double budgetLowerBound;
        Double budgetUpperBound = ConfigureUtils.getPrivacyBudgetUpperBound();
        Integer windowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        Integer windowSizeUpperBound = windowSize;

        int userSize = dataList.get(0).size();
        int typeSize = ConfigureUtils.getDefaultTypeSize();
        int groupElementSize = (int)Math.ceil(userSize*1.0/typeSize);
        List<Integer> randomWindowSizeList = ParameterUtils.generateRandomIntegerList(windowSizeLowerBound, windowSizeUpperBound, typeSize, groupElementSize, userSize);
        List<Double> randomBudgetList;

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

        }

        return experimentResultList;
    }
}
