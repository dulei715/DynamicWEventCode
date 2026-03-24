package ecnu.dll.run.d_total_run._5_ablation_run._5_1_1_dim_ablation_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForTrajectory;
import ecnu.dll.run.c_dataset_run.version_3.basic_run.TrajectoryDataSetRun;

public class TrajectoryDimensionAblationRun {
    public static void main(String[] args) throws Exception {

        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.trajectoriesFilePath;
        String finalResultDirName = "1.trajectory_dim_ablation_result";


        // 1. parameter 生成
        UserGroupGenerator.generateUserIDType(Constant.trajectoriesFilePath);
        UserGroupGenerator.generateUserToType(Constant.trajectoriesFilePath);
        GenerateGroupParametersForTrajectory.generateParameters();

        // 2. 执行
        TrajectoryDataSetRun.runTrajectoryContainingSimpleLDP();

    }
}
