package ecnu.dll.run.d_total_run._2_main_run;

import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.CheckInDatasetPreprocessRun;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForCheckIn;
import ecnu.dll.run.c_dataset_run.version_3.CheckInDataSetRun;
import ecnu.dll.utils.CatchSignal;

import java.lang.reflect.InvocationTargetException;

public class CheckInMainRun {
    public static void main(String[] args) throws Exception {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        // 1. dataset 抽取
        CheckInDatasetPreprocessRun.extractUser();  // 抽取 5% 的 user 记录在 user.txt
        CheckInDatasetPreprocessRun.extractUserData(); // 根据新的 user.txt 抽取 runInput 中的数据

        // 2. parameter 生成
        UserGroupGenerator.generateUserIDType(Constant.checkInFilePath);
        UserGroupGenerator.generateUserToType(Constant.checkInFilePath);
        GenerateGroupParametersForCheckIn.generateParameters();

        // 3. 执行
        CheckInDataSetRun.runCheckIn();

    }
}
