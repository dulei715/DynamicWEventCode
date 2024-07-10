package ecnu.dll.run.b_parameter_run.basic.utils;

import ecnu.dll._config.Constant;
import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes.basic_scheme.NonPrivacyMechanism;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetAbsorption;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetDistribution;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedBudgetDistribution;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.DynamicPersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.DynamicPersonalizedBudgetDistribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterInitializeUtils {
    public static Map<String, Mechanism> getInitializedSchemeMap(List<String> dataType, Integer userSize,
                                                                 Double staticPrivacyBudget, Integer staticWindowSize,
                                                                 List<Double> personalizedPrivacyBudgetList, List<Integer> personalziedWindowSizeList) {
        Map<String, Mechanism> resultMap = new HashMap<>();

        NonPrivacyMechanism nonPrivacyMechanism = new NonPrivacyMechanism(dataType);
        resultMap.put(Constant.NonPrivacySchemeName, nonPrivacyMechanism);

        BudgetDistribution budgetDistributionMechanism = new BudgetDistribution(dataType, staticPrivacyBudget, staticWindowSize);
        BudgetAbsorption budgetAbsorptionMechanism = new BudgetAbsorption(dataType, staticPrivacyBudget, staticWindowSize);
        resultMap.put(Constant.BudgetDistributionSchemeName, budgetDistributionMechanism);
        resultMap.put(Constant.BudgetAbsorptionSchemeName, budgetAbsorptionMechanism);

        PersonalizedBudgetDistribution personalizedBudgetDistributionScheme = new PersonalizedBudgetDistribution(dataType, personalizedPrivacyBudgetList, personalziedWindowSizeList);
        PersonalizedBudgetAbsorption personalizedBudgetAbsorptionScheme = new PersonalizedBudgetAbsorption(dataType, personalizedPrivacyBudgetList, personalziedWindowSizeList);
        resultMap.put(Constant.PersonalizedBudgetDistributionSchemeName, personalizedBudgetDistributionScheme);
        resultMap.put(Constant.PersonalizedBudgetAbsorptionSchemeName, personalizedBudgetAbsorptionScheme);

        DynamicPersonalizedBudgetDistribution dynamicPersonalizedBudgetDistribution = new DynamicPersonalizedBudgetDistribution(dataType, userSize);
        DynamicPersonalizedBudgetAbsorption dynamicPersonalizedBudgetAbsorption = new DynamicPersonalizedBudgetAbsorption(dataType, userSize);
        resultMap.put(Constant.DynamicPersonalizedBudgetDistributionSchemeName, dynamicPersonalizedBudgetDistribution);
        resultMap.put(Constant.DynamicPersonalizedBudgetAbsorptionSchemeName, dynamicPersonalizedBudgetAbsorption);

        return resultMap;
    }
}
