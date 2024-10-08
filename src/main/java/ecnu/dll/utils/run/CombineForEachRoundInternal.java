package ecnu.dll.utils.run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.utils.PostProcessUtils;

public class CombineForEachRoundInternal {
    public static void combineAllRoundInternal(String datasetBasicPath, String finalResultDirName) {
//        String datasetBasicPath = Constant.tlnsFilePath;
//        String finalResultDirName = "3.tlns_internal_result";

        String roundPattern = "round_%d_internal";
        String outputDir = "group_output_internal";
        String extractDir = "extract_internal_result";
        for (int i = 1; i <= 10; i++) {
            String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetBasicPath, String.format(roundPattern, i));
            String rawDataDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, outputDir);
            String extractResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, extractDir);
            String finalResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, finalResultDirName);
//            if (i == 10) {
//                System.out.println("haha");
//            }
            PostProcessUtils.combineAndExtractCombineResult(rawDataDir, extractResultDir);
            PostProcessUtils.furtherCombine(extractResultDir, finalResultDir);
        }

    }
}
