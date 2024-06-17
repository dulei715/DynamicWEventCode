package ecnu.dll.run.b_parameter_run.internal;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.pair.IdentityPurePair;
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

/**
 * 给定两类 privacy budget： e1, e2 且 e1 < e2, 计算 e1的占比对错误的影响
 */
public class ChangeUserRatio {
    public static List<ExperimentResult> twoBudgetRun(List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, IdentityPurePair<Double> privacyBudgetPair, Integer windowSize, List<Double> budgetGroupRatioList) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.run(dataType, dataList);
        List<StreamCountData> rawPublicationList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);

        Double groupRatio;

        int userSize = dataList.get(0).size();
        List<Double> totalBudgetList;


        // 执行各种机制
        int size = budgetGroupRatioList.size();
        List<Double> privacyBudgetList = privacyBudgetPair.toList();
        List<Integer> totalWindowSizeList = ParameterUtils.generateIntegerList(ListUtils.valueOf(windowSize), userSize, null);
        for (int i = 0; i < size; i++) {
            groupRatio = budgetGroupRatioList.get(i);

            totalBudgetList = ParameterUtils.generateDoubleList(privacyBudgetList, userSize, groupRatio);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetDistribution.class, dataType, dataList, rawPublicationList, totalBudgetList, totalWindowSizeList);
            experimentResultList.add(tempResult);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetAbsorption.class, dataType, dataList, rawPublicationList, totalBudgetList, totalWindowSizeList);
            experimentResultList.add(tempResult);
        }

        return experimentResultList;
    }
    public static List<ExperimentResult> twoWindowSizeRun(List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, Double privacyBudget, IdentityPurePair<Integer> windowSizePair, List<Double> budgetGroupRatioList) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.run(dataType, dataList);
        List<StreamCountData> rawPublicationList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);

        Double groupRatio;
        List<Integer> totalWindowSizeList;

        int userSize = dataList.get(0).size();


        // 执行各种机制
        int size = budgetGroupRatioList.size();
        List<Integer> windowSizeList = windowSizePair.toList();
        List<Double> totalBudgetList = ParameterUtils.generateDoubleList(ListUtils.valueOf(privacyBudget), userSize, null);
        for (int i = 0; i < size; i++) {
            groupRatio = budgetGroupRatioList.get(i);

            totalWindowSizeList = ParameterUtils.generateIntegerList(windowSizeList, userSize, groupRatio);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetDistribution.class, dataType, dataList, rawPublicationList, totalBudgetList, totalWindowSizeList);
            experimentResultList.add(tempResult);
            tempResult = _2_PersonalizedEventMechanismRun.run(PersonalizedBudgetAbsorption.class, dataType, dataList, rawPublicationList, totalBudgetList, totalWindowSizeList);
            experimentResultList.add(tempResult);
        }

        return experimentResultList;
    }
}
