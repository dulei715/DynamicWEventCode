package ecnu.dll.run.d_total_run._4_added_run._2_6_containing_error_details_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.LogDatasetPreprocessRun;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForLog;
import ecnu.dll.run.c_dataset_run.version_3.basic_run.LogDataSetRun;

public class LogContainingErrorDetailsRun {
    public static void main(String[] args) throws Exception {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();


        // 1. dataset 生成
        LogDatasetPreprocessRun.generateDataset();

        // 2. parameter 生成
        UserGroupGenerator.generateUserIDType(Constant.logFilePath);
        UserGroupGenerator.generateUserToType(Constant.logFilePath);
        GenerateGroupParametersForLog.generateParameters();

        // 3. 执行
        LogDataSetRun.runLogContainingErrorDetails();


    }
}
