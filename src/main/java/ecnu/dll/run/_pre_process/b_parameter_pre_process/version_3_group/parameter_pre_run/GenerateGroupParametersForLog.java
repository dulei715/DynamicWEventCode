package ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_generator.DiscreteParameterGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;

public class GenerateGroupParametersForLog {
    public static void generateParameters() {
        String privacyBudgetConfigVarianceName = "default";
        String windowSizeConfigVarianceName = "default";
        String userTypeIDFileName = "userTypeID.txt";
        String basicParameterGenerationDirectoryName = "group_generated_parameters";
        String userPrivacyFileNameForPersonalized = "typePrivacyBudgetFile.txt";
        String windowSizeFileNameForPersonalized = "typeWindowSizeFile.txt";
        DiscreteParameterGenerator.generateParametersForDataset(Constant.logFilePath, privacyBudgetConfigVarianceName, windowSizeConfigVarianceName, userTypeIDFileName, basicParameterGenerationDirectoryName, userPrivacyFileNameForPersonalized, windowSizeFileNameForPersonalized);

    }
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        // 1. 生成 userIDType.txt 以及 user_to_type.txt 到 basic_info中
        UserGroupGenerator.generateUserIDType(Constant.logFilePath);
        UserGroupGenerator.generateUserToType(Constant.logFilePath);
        // 2. 生成实验参数到 group_generated_parameters
        generateParameters();
    }
}
