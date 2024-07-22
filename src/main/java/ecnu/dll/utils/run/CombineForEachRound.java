package ecnu.dll.utils.run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll.run.c_dataset_run.utils.PostProcessUtils;

public class CombineForEachRound {
    public static void main(String[] args) {
        String datasetBasicPath = args[0];
        String finalResultDirName = args[1];
        for (int i = 1; i <= 10; i++) {
            String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetBasicPath, "round_"+i);
            String rawDataDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "group_output");
            String extractResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "extract_result");
            String finalResultDir = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, finalResultDirName);
            PostProcessUtils.combineAndExtractCombineResult(rawDataDir, extractResultDir);
            PostProcessUtils.furtherCombine(extractResultDir, finalResultDir);
        }

    }
}
