package ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.utils;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.write.BasicWrite;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.synthetic_dataset.function.DataGenerationFunction;
import ecnu.dll.utils.io.ListWriteUtils;

import java.util.List;

public class SyntheticGenerationUtils {
    public static void generateProbability(DataGenerationFunction<Double> function, Integer probabilitySize, Boolean containInitialValue) {
        String basicFileName  = ConfigureUtils.getDatasetFileName(function.getName());
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, basicFileName, "basic_info", function.getName()+".txt");
        List<Double> probabilityList = function.nextProbability(probabilitySize);
        BasicWrite basicWrite = new BasicWrite();
        basicWrite.startWriting(outputPath);
        basicWrite.writeOneLine("0,".concat(String.valueOf(function.getInitializedValue())));
        int i = 0;
        for (; i < probabilitySize - 1; i++) {
//            System.out.println(probabilityList.get(i));
            basicWrite.writeOneLine(String.valueOf(i+1).concat(",").concat(String.valueOf(probabilityList.get(i))));
        }
        if (!containInitialValue) {
            basicWrite.writeOneLine(String.valueOf(i+1).concat(",").concat(String.valueOf(probabilityList.get(i))));
        }
        basicWrite.endWriting();
    }

    public static void generateUserID(String datasetPath, int size) {
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "basic_info", "user.txt");
        List<Integer> userList = BasicArrayUtil.getIncreaseIntegerNumberList(0, 1, size-1);
        ListWriteUtils.writeList(outputPath, userList, ",");
    }
    public static void generateUserType(String datasetPath, int size) {
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "basic_info", "userTypeID.txt");
        List<Integer> userList = BasicArrayUtil.getIncreaseIntegerNumberList(0, 1, size-1);
        ListWriteUtils.writeList(outputPath, userList, ",");
    }


}
