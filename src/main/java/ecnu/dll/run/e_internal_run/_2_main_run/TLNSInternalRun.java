package ecnu.dll.run.e_internal_run._2_main_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.TLNSDatasetPreprocessRun;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_4_two_group.parameter_generator.TwoGroupParameterGenerator;
import ecnu.dll.run.c_dataset_run.version_3.internal_run.TLNSInternalDataSetRun;

public class TLNSInternalRun {
    public static void main(String[] args) throws Exception {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.tlnsFilePath;
        String finalResultDirName = "3.tlns_internal_result";
        String rawDirName = "group_output_internal";
        String extractDirName = "extract_internal_result";

        // 1. dataset 生成
        TLNSDatasetPreprocessRun.generateDataset();

        // 2. parameter 生成
        TwoGroupParameterGenerator.generateUserIDTypeRatio(datasetPath);
        TwoGroupParameterGenerator.generateUserToType(datasetPath);
        TwoGroupParameterGenerator.generateInternalParameters(datasetPath);

        // 3. 执行
        TLNSInternalDataSetRun.runInternalTLNS();

    }
}
