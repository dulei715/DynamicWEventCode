package ecnu.dll.run.e_internal_run._3_poster_process_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.utils.PostProcessUtils;
import ecnu.dll.utils.CatchSignal;

public class CheckInInternalPosterRun {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetPath = Constant.checkInFilePath;
        String finalResultDirName = "2.checkIn_internal_result";
        String rawDirName = "group_output_internal";
        String extractDirName = "extract_internal_result";


        // 3. 后处理
        String rawDataDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, rawDirName);
        String extractResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, extractDirName);
        String finalResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, finalResultDirName);
        PostProcessUtils.combineAndExtractCombineResult(rawDataDir, extractResultDir);
        PostProcessUtils.furtherCombine(extractResultDir, finalResultDir);
    }
}
