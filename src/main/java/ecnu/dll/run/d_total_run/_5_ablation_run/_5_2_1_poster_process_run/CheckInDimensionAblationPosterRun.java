package ecnu.dll.run.d_total_run._5_ablation_run._5_2_1_poster_process_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.signal.CatchSignal;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.utils.run.CombineForEachRound;
import ecnu.dll.utils.run.RepeatUtils;

public class CheckInDimensionAblationPosterRun {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.checkInFilePath;
        String finalResultDirName = "2.check_in_dimension_ablation_result";

        String basicOutputFileString = "../5.result_dimension_ablation";
        String roundPattern = "round_%d_dimension_ablation";
        String outputDir = "group_output_dimension_ablation";
        String extractDir = "extract_dimension_ablation_result";
        String basicOutputPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, basicOutputFileString);

//        int roundSize = 10;
//        int roundSize = 2;
//        int roundSize = 4;
        String configDatasetFileHandleName = "checkIn";
        int roundSize = Integer.parseInt(ConfigureUtils.getFileHandleInfo(configDatasetFileHandleName, "combineRound"));



        // 3. 后处理
        CombineForEachRound.combineAllRound(datasetPath, finalResultDirName, roundSize, roundPattern, outputDir, extractDir);
        // 4. 合并每轮
        RepeatUtils.combineMultipleDimensionAblationRound(datasetPath, basicOutputPath, roundSize);
    }
}
