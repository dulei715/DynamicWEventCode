package ecnu.dll.run.d_total_run._2_main_run;

import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.TrajectoryDatasetPreprocessRun;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForTrajectory;
import ecnu.dll.run.c_dataset_run.version_3.TrajectoryDataSetRun;

import java.lang.reflect.InvocationTargetException;

public class TrajectoryMainRun {
    public static void main(String[] args) throws Exception {
        // 1. 数据记录
        TrajectoryDatasetPreprocessRun.recordBasicInformation();

        // 2. parameter 生成
        UserGroupGenerator.generateUserIDType(Constant.trajectoriesFilePath);
        UserGroupGenerator.generateUserToType(Constant.trajectoriesFilePath);
        GenerateGroupParametersForTrajectory.generateParameters();

        // 3. 执行
        TrajectoryDataSetRun.runTrajectory();
    }
}
