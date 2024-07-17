package ecnu.dll.run.d_total_run._2_main_run;

import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.TLNSDatasetPreprocessRun;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.UserGroupGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_pre_run.GenerateGroupParametersForTLNS;
import ecnu.dll.run.c_dataset_run.version_3.TLNSDataSetRun;
import ecnu.dll.utils.CatchSignal;

import java.lang.reflect.InvocationTargetException;

public class TLNSMainRun {
    public static void main(String[] args) throws Exception {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        // 1. dataset 生成
        TLNSDatasetPreprocessRun.generateDataset();

        // 2. parameter 生成
        UserGroupGenerator.generateUserIDType(Constant.tlnsFilePath);
        UserGroupGenerator.generateUserToType(Constant.tlnsFilePath);
        GenerateGroupParametersForTLNS.generateParameters();

        // 3. 执行
        TLNSDataSetRun.runTLNS();
    }
}
