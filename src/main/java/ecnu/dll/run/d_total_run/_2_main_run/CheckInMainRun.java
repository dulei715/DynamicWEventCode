package ecnu.dll.run.d_total_run._2_main_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForCheckIn;
import ecnu.dll.run.c_dataset_run.version_3.basic_run.CheckInDataSetRun;

public class CheckInMainRun {
    public static void main(String[] args) throws Exception {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.checkInFilePath;
        String finalResultDirName = "2.checkIn_result";


        // 1. parameter 生成
        UserGroupGenerator.generateUserIDType(Constant.checkInFilePath);
        UserGroupGenerator.generateUserToType(Constant.checkInFilePath);
        GenerateGroupParametersForCheckIn.generateParameters();

        // 2. 执行
        CheckInDataSetRun.runCheckIn();

//        // 3. 后处理
//        String rawDataDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "group_output");
//        String extractResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "extract_result");
//        String finalResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, finalResultDirName);
//        PostProcessUtils.combineAndExtractCombineResult(rawDataDir, extractResultDir);
//        PostProcessUtils.furtherCombine(extractResultDir, finalResultDir);

    }
}
