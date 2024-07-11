package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function.DataGenerationFunction;

public class SyntheticGenerationUtils {
    public static void generateProbability(String outputDir, String outputFileName, DataGenerationFunction function, Integer probabilitySize) {
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputDir, outputFileName);

    }
}
