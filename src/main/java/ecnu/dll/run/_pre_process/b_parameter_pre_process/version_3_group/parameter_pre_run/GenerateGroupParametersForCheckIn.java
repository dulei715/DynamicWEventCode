package ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run;

import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_generator.DiscreteParameterGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.utils.CatchSignal;

public class GenerateGroupParametersForCheckIn {
    public static void generateParameters() {
        String privacyBudgetConfigVarianceName = "default";
        String windowSizeConfigVarianceName = "default";
        String userTypeIDFileName = "userTypeID.txt";  // 这里不对user进行分类
        String basicParameterGenerationDirectoryName = "group_generated_parameters";
        String userPrivacyFileNameForPersonalized = "typePrivacyBudgetFile.txt";
        String windowSizeFileNameForPersonalized = "typeWindowSizeFile.txt";
        DiscreteParameterGenerator.generateParametersForCheckIn(privacyBudgetConfigVarianceName, windowSizeConfigVarianceName, userTypeIDFileName, basicParameterGenerationDirectoryName, userPrivacyFileNameForPersonalized, windowSizeFileNameForPersonalized);

    }
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        UserGroupGenerator.generateUserIDType(Constant.checkInFilePath);
        UserGroupGenerator.generateUserToType(Constant.checkInFilePath);
        generateParameters();
    }
}
