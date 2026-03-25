package ecnu.dll.run.d_total_run._5_ablation_run._5_1_1_dim_ablation_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation.PositionGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation.parameter_pre_run.GenerateGroupParameterForDataSet;

public class TrajectoryDimensionAblationRun {
    public static void main(String[] args) throws Exception {

        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.trajectoriesFilePath;
        String finalResultDirName = "1.trajectory_dim_ablation_result";
        String positionFileName = "cell.txt";


        // 1. parameter 生成
        UserGroupGenerator.generateUserIDType(datasetPath);
        UserGroupGenerator.generateUserToType(datasetPath);
        GenerateGroupParameterForDataSet.generateParameters(datasetPath);

        // 1-2. 将数据集转换成不同维度并生成
        PositionGroupGenerator.generatePositionToGroup(datasetPath, positionFileName);

        // 2. 执行
//        TrajectoryDataSetRun.runTrajectoryContainingSimpleLDP();

    }
}
