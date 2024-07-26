package ecnu.dll.run.e_internal_run._3_poster_process_run.utils;

import ecnu.dll.utils.run.RepeatUtils;

public class CombineMultipleInternalRound {
    public static void main(String[] args) {
        String inputDir = args[0];
        String outputDir = args[1];
//        String inputDir = Constant.tlnsFilePath;
//        String outputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "1.result_internal");
//        combineMultipleMainRound(inputDir, outputDir);
        RepeatUtils.combineMultipleInternalRound(inputDir, outputDir);
    }
}
