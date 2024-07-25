package ecnu.dll.run.e_internal_run._1_initialize_run;

import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_4_two_group.parameter_generator.TwoGroupParameterGenerator;

public class TrajectoryInternalInitialize {
    public static void main(String[] args) {
        String basicPath = Constant.checkInFilePath;
        TwoGroupParameterGenerator.generateUserIDTypeRatio(basicPath);
        TwoGroupParameterGenerator.generateUserToType(basicPath);
        TwoGroupParameterGenerator.generateInternalParameters(basicPath);
    }
}
