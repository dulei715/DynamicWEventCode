package ecnu.dll.run.b_parameter_run.basic;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.pair.PurePair;
import ecnu.dll._config.Constant;
import ecnu.dll.run.a_mechanism_run._0_NonPrivacyMechanismRun;
import ecnu.dll.run.a_mechanism_run._1_WEventMechanismRun;
import ecnu.dll.run.a_mechanism_run._2_PersonalizedEventMechanismRun;
import ecnu.dll.run.a_mechanism_run._3_PersonalizedDynamicEventMechanismRun;
import ecnu.dll.run.c_dataset_run.utils.DatasetParameterUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FixedParameterRun implements Runnable {
    private String basicPath;
    private String dataTypeFileName;
    private Map<String, ? extends Mechanism> mechanismMap;



    @Deprecated
    public static List<ExperimentResult> runBatchBefore(Map<String, ? extends Mechanism> mechanismMap, List<String> dataType, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList,
                                                  List<List<Double>> batchForwardPrivacyBudget, List<List<Integer>> batchForwardWindowSize,
                                                  List<List<Double>> batchRemainBackwardPrivacyBudget, List<List<Integer>> batchBackwardWindowSize) {
        NonPrivacyMechanism nonPrivacyScheme = new NonPrivacyMechanism(dataType);
        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.runBatch(nonPrivacyScheme, batchID, batchDataList);
        List<StreamCountData> rawPublicationBatchList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);



        // 执行各种机制
        tempResult = _1_WEventMechanismRun.runBatch((BudgetDistribution)mechanismMap.get(Constant.BudgetDistributionSchemeName), batchID, batchDataList, rawPublicationBatchList);
        experimentResultList.add(tempResult);
        // todo
        tempResult = _1_WEventMechanismRun.runBatch((BudgetAbsorption)mechanismMap.get(Constant.BudgetAbsorptionSchemeName), batchID, batchDataList, rawPublicationBatchList);
        experimentResultList.add(tempResult);

        tempResult = _2_PersonalizedEventMechanismRun.runBatch((PersonalizedBudgetDistribution)mechanismMap.get(Constant.PersonalizedBudgetDistributionSchemeName), batchID, batchDataList, rawPublicationBatchList);
        experimentResultList.add(tempResult);
        tempResult = _2_PersonalizedEventMechanismRun.runBatch((PersonalizedBudgetAbsorption)mechanismMap.get(Constant.PersonalizedBudgetAbsorptionSchemeName), batchID, batchDataList, rawPublicationBatchList);
        experimentResultList.add(tempResult);

        tempResult = _3_PersonalizedDynamicEventMechanismRun.runBatch((DynamicPersonalizedBudgetDistribution)mechanismMap.get(Constant.DynamicPersonalizedBudgetDistributionSchemeName), batchID, batchDataList, rawPublicationBatchList, batchRemainBackwardPrivacyBudget, batchBackwardWindowSize, batchForwardPrivacyBudget, batchForwardWindowSize);
        experimentResultList.add(tempResult);

        tempResult = _3_PersonalizedDynamicEventMechanismRun.runBatch((DynamicPersonalizedBudgetAbsorption)mechanismMap.get(Constant.DynamicPersonalizedBudgetAbsorptionSchemeName), batchID, batchDataList, rawPublicationBatchList, batchRemainBackwardPrivacyBudget, batchBackwardWindowSize, batchForwardPrivacyBudget, batchForwardWindowSize);
        experimentResultList.add(tempResult);

        return experimentResultList;
    }
    public List<ExperimentResult> runBatch(Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList,
                                                  List<List<Double>> batchForwardPrivacyBudget, List<List<Integer>> batchForwardWindowSize,
                                                  List<List<Double>> batchRemainBackwardPrivacyBudget, List<List<Integer>> batchBackwardWindowSize) {
        List<String> dataType = DatasetParameterUtils.getDataType(this.basicPath, this.dataTypeFileName);
        NonPrivacyMechanism nonPrivacyScheme = new NonPrivacyMechanism(dataType);

        PurePair<ExperimentResult, List<StreamCountData>> nonPrivacySchemeResultPair = _0_NonPrivacyMechanismRun.runBatch(nonPrivacyScheme, batchID, batchDataList);
        List<StreamCountData> rawPublicationBatchList = nonPrivacySchemeResultPair.getValue();
        List<ExperimentResult> experimentResultList = new ArrayList<>();
        ExperimentResult tempResult;

        tempResult = nonPrivacySchemeResultPair.getKey();
        experimentResultList.add(tempResult);



        // 执行各种机制
        tempResult = _1_WEventMechanismRun.runBatch((BudgetDistribution)mechanismMap.get(Constant.BudgetDistributionSchemeName), batchID, batchDataList, rawPublicationBatchList);
        experimentResultList.add(tempResult);
        // todo
        tempResult = _1_WEventMechanismRun.runBatch((BudgetAbsorption)mechanismMap.get(Constant.BudgetAbsorptionSchemeName), batchID, batchDataList, rawPublicationBatchList);
        experimentResultList.add(tempResult);

        tempResult = _2_PersonalizedEventMechanismRun.runBatch((PersonalizedBudgetDistribution)mechanismMap.get(Constant.PersonalizedBudgetDistributionSchemeName), batchID, batchDataList, rawPublicationBatchList);
        experimentResultList.add(tempResult);
        tempResult = _2_PersonalizedEventMechanismRun.runBatch((PersonalizedBudgetAbsorption)mechanismMap.get(Constant.PersonalizedBudgetAbsorptionSchemeName), batchID, batchDataList, rawPublicationBatchList);
        experimentResultList.add(tempResult);

        tempResult = _3_PersonalizedDynamicEventMechanismRun.runBatch((DynamicPersonalizedBudgetDistribution)mechanismMap.get(Constant.DynamicPersonalizedBudgetDistributionSchemeName), batchID, batchDataList, rawPublicationBatchList, batchRemainBackwardPrivacyBudget, batchBackwardWindowSize, batchForwardPrivacyBudget, batchForwardWindowSize);
        experimentResultList.add(tempResult);

        tempResult = _3_PersonalizedDynamicEventMechanismRun.runBatch((DynamicPersonalizedBudgetAbsorption)mechanismMap.get(Constant.DynamicPersonalizedBudgetAbsorptionSchemeName), batchID, batchDataList, rawPublicationBatchList, batchRemainBackwardPrivacyBudget, batchBackwardWindowSize, batchForwardPrivacyBudget, batchForwardWindowSize);
        experimentResultList.add(tempResult);

        return experimentResultList;
    }

    @Override
    public void run() {

    }
}
