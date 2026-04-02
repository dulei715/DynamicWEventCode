package ecnu.dll.run.d_total_run._5_ablation_run._5_1_1_dim_ablation_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation.PositionGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation.parameter_pre_run.GenerateGroupParameterForDataSet;
import ecnu.dll.run.c_dataset_run.version_3.basic_run.CheckInDataSetRun;
import ecnu.dll.run.c_dataset_run.version_3.basic_run.TrajectoryDataSetRun;

public class CheckInDimensionAblationRun {
    public static void main(String[] args) throws Exception {

        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.checkInFilePath;
        String positionFileName = "country.txt";


        // 1. parameter 生成
        UserGroupGenerator.generateUserIDType(datasetPath);
        UserGroupGenerator.generateUserToType(datasetPath);
        GenerateGroupParameterForDataSet.generateParameters(datasetPath);

        // 1-2. 将数据集转换成不同维度并生成
        PositionGroupGenerator.generatePositionToGroup(datasetPath, positionFileName);

        // 2. 执行
//        TrajectoryDataSetRun.runTrajectoryContainingDatasetAblation();
        CheckInDataSetRun.runCheckInContainingDatasetAblation();

    }
}
