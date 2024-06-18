package ecnu.dll.run.c_dataset_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll.dataset.real.datasetB.spatial_tools.CheckInBeanUtils;

import java.io.File;

public class RealDatasetPreprocessRun {
    public static void main(String[] args) {
        String inputDataSuperPath = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\CheckIn_dataset_TIST2015";
        String outputDataSuperPath = StringUtil.join(ConstantValues.FILE_SPLIT, inputDataSuperPath, "join");
        File outputDataSuperPathDirectory = new File(outputDataSuperPath);
        if (!outputDataSuperPathDirectory.exists()) {
            outputDataSuperPathDirectory.mkdirs();
        }
        String outputFileName = "check_ins.csv";
        CheckInBeanUtils.transformToCountry(inputDataSuperPath, outputDataSuperPath, outputFileName);
    }
}
