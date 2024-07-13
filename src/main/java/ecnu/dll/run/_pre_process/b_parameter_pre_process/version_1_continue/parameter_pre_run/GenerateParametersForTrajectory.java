package ecnu.dll.run._pre_process.b_parameter_pre_process.version_1_continue.parameter_pre_run;

import ecnu.dll.run._pre_process.b_parameter_pre_process.version_1_continue.parameter_generator.ParameterGenerator;
import ecnu.dll.utils.CatchSignal;

public class GenerateParametersForTrajectory {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        String privacyBudgetConfigVarianceName = "default";
        String windowSizeConfigVarianceName = "default";
        String userTypeIDFileName = "user.txt";  // 这里不对user进行分类
        String basicParameterGenerationDirectoryName = "generated_parameters";
        String privacyBudgetFileNameForPersonalized = "userPrivacyBudgetFile.txt";
        String windowSizeFileNameForPersonalized = "userWindowSizeFile.txt";
        ParameterGenerator.generateParametersForTrajectory(privacyBudgetConfigVarianceName, windowSizeConfigVarianceName, userTypeIDFileName, basicParameterGenerationDirectoryName, privacyBudgetFileNameForPersonalized, windowSizeFileNameForPersonalized);
    }
}
