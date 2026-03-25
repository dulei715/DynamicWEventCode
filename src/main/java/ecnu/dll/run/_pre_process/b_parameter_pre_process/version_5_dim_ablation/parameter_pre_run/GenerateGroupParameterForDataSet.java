package ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation.parameter_pre_run;

import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_generator.DiscreteParameterGenerator;

public class GenerateGroupParameterForDataSet {

    /**
     * 将 ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run 下的针对各个数据集的独有方法升级为传入数据集路径的通用方法
     * @param datasetPath
     */
    public static void generateParameters(String datasetPath) {
        String privacyBudgetConfigVarianceName = "default";
        String windowSizeConfigVarianceName = "default";
        String userTypeIDFileName = "userTypeID.txt";
        String basicParameterGenerationDirectoryName = "group_generated_parameters";
        String privacyBudgetFileNameForPersonalized = "typePrivacyBudgetFile.txt";
        String windowSizeFileNameForPersonalized = "typeWindowSizeFile.txt";
        DiscreteParameterGenerator.generateParametersForDataset(datasetPath, privacyBudgetConfigVarianceName, windowSizeConfigVarianceName,
                userTypeIDFileName, basicParameterGenerationDirectoryName, privacyBudgetFileNameForPersonalized, windowSizeFileNameForPersonalized);
    }


}
