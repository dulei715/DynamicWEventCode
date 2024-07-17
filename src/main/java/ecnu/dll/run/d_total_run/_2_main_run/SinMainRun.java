package ecnu.dll.run.d_total_run._2_main_run;

import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.SinDatasetPreprocessRun;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForSin;
import ecnu.dll.run.c_dataset_run.version_3.SinDataSetRun;
import ecnu.dll.utils.CatchSignal;


public class SinMainRun {
    public static void main(String[] args) throws Exception {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        // 1. dataset 生成
        SinDatasetPreprocessRun.generateDataset();

        // 2. parameter 生成
        UserGroupGenerator.generateUserIDType(Constant.sinFilePath);
        UserGroupGenerator.generateUserToType(Constant.sinFilePath);
        GenerateGroupParametersForSin.generateParameters();

        // 3. 执行
        SinDataSetRun.runSin();
    }
}
