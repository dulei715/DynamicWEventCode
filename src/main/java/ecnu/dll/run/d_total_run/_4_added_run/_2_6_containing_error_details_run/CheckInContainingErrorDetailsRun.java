package ecnu.dll.run.d_total_run._4_added_run._2_6_containing_error_details_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForCheckIn;
import ecnu.dll.run.c_dataset_run.version_3.basic_run.CheckInDataSetRun;

public class CheckInContainingErrorDetailsRun {
    public static void main(String[] args) throws Exception {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.checkInFilePath;


        // 1. parameter 生成
        UserGroupGenerator.generateUserIDType(datasetPath);
        UserGroupGenerator.generateUserToType(datasetPath);
        GenerateGroupParametersForCheckIn.generateParameters();

        // 2. 执行
        CheckInDataSetRun.runCheckInContainingErrorDetails();


    }
}
