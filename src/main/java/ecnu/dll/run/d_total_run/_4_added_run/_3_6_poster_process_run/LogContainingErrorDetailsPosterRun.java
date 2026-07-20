package ecnu.dll.run.d_total_run._4_added_run._3_6_poster_process_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.utils.run.CombineForEachRound;
import ecnu.dll.utils.run.RepeatUtils;

public class LogContainingErrorDetailsPosterRun {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.logFilePath;
        String finalResultDirName = "5.log_containing_error_details_result";

        String basicOutputFileString = "../8.result_containing_error_details";
        String roundPattern = "round_%d_containing_error_details";
        String outputDir = "group_output_containing_error_details";
        String extractDir = "extract_containing_error_details_result";
        String basicOutputPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, basicOutputFileString);

//        int roundSize = 10;
//        int roundSize = 4;
        String configDatasetFileHandleName = "log";
        int roundSize = Integer.parseInt(ConfigureUtils.getFileHandleInfo(configDatasetFileHandleName, "combineRound"));


        // 3. 后处理
        CombineForEachRound.combineAllRoundErrorDetails(datasetPath, finalResultDirName, roundSize, roundPattern, outputDir, extractDir);
        // 4. 合并每轮
        RepeatUtils.combineMultipleContainingErrorsRound(datasetPath, basicOutputPath, roundSize);
    }
}
