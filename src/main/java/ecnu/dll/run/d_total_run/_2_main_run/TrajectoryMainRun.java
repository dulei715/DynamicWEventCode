package ecnu.dll.run.d_total_run._2_main_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForTrajectory;
import ecnu.dll.run.c_dataset_run.version_3.basic_run.TrajectoryDataSetRun;

public class TrajectoryMainRun {
    public static void main(String[] args) throws Exception {

        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.trajectoriesFilePath;
        String finalResultDirName = "1.trajectory_result";


        // 1. parameter 生成
        UserGroupGenerator.generateUserIDType(Constant.trajectoriesFilePath);
        UserGroupGenerator.generateUserToType(Constant.trajectoriesFilePath);
        GenerateGroupParametersForTrajectory.generateParameters();

        // 2. 执行
        TrajectoryDataSetRun.runTrajectory();

//        // 3. 后处理
//        String rawDataDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "group_output");
//        String extractResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "extract_result");
//        String finalResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, finalResultDirName);
//        PostProcessUtils.combineAndExtractCombineResult(rawDataDir, extractResultDir);
//        PostProcessUtils.furtherCombine(extractResultDir, finalResultDir);
    }
}
