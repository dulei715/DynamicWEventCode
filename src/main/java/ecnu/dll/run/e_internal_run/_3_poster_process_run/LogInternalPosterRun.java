package ecnu.dll.run.e_internal_run._3_poster_process_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.utils.PostProcessUtils;
import ecnu.dll.utils.CatchSignal;
import ecnu.dll.utils.run.CombineForEachRoundInternal;
import ecnu.dll.utils.run.RepeatUtils;

public class LogInternalPosterRun {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.logFilePath;
        String finalResultDirName = "5.log_internal_result";
        String rawDirName = "group_output_internal";
        String extractDirName = "extract_internal_result";
        String basicOutputFileString = "../1.result_internal";
        String basicOutputPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, basicOutputFileString);
        String roundFormat = "round_%d_internal";

        for (int i = 1; i <= 10; i++) {
            // 4. 后处理
            String rawDataDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, String.format(roundFormat, i), rawDirName);
            String extractResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, String.format(roundFormat, i), extractDirName);
            String finalResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, String.format(roundFormat, i), finalResultDirName);
            PostProcessUtils.combineAndExtractCombineResult(rawDataDir, extractResultDir);
            PostProcessUtils.furtherCombine(extractResultDir, finalResultDir);
        }

        // 5. 合并
        CombineForEachRoundInternal.combineAllRoundInternal(datasetPath, finalResultDirName);
        RepeatUtils.combineMultipleInternalRound(datasetPath, basicOutputPath);
    }
}
