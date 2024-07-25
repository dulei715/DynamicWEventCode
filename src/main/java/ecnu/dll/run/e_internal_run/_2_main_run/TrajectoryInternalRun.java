package ecnu.dll.run.e_internal_run._2_main_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForTrajectory;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_4_two_group.parameter_generator.TwoGroupParameterGenerator;
import ecnu.dll.run.c_dataset_run.utils.PostProcessUtils;
import ecnu.dll.run.c_dataset_run.version_3.basic_run.TrajectoryDataSetRun;
import ecnu.dll.run.c_dataset_run.version_3.internal_run.TrajectoryInternalDataSetRun;
import ecnu.dll.utils.CatchSignal;

public class TrajectoryInternalRun {
    public static void main(String[] args) throws Exception {

        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.trajectoriesFilePath;
        String finalResultDirName = "1.trajectory_internal_result";
        String rawDirName = "group_output_internal";
        String extractDirName = "extract_internal_result";


        // 1. parameter 生成
        TwoGroupParameterGenerator.generateUserIDTypeRatio(datasetPath);
        TwoGroupParameterGenerator.generateUserToType(datasetPath);
        TwoGroupParameterGenerator.generateInternalParameters(datasetPath);

        // 2. 执行
        TrajectoryInternalDataSetRun.runInternalTrajectory();

        // 3. 后处理
        String rawDataDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, rawDirName);
        String extractResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, extractDirName);
        String finalResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, finalResultDirName);
        PostProcessUtils.combineAndExtractCombineResult(rawDataDir, extractResultDir);
        PostProcessUtils.furtherCombine(extractResultDir, finalResultDir);
    }
}
