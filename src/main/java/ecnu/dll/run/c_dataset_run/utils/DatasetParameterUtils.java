package ecnu.dll.run.c_dataset_run.utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;

import java.util.List;

public class DatasetParameterUtils {
//    private static Map<String, >
    public static List<String> getDataType(String basicPath, String dataTypeFileName) {
        String filePath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", dataTypeFileName);
        BasicRead basicRead = new BasicRead(",");
        basicRead.startReading(filePath);
        List<String> data = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        return data;
    }
}
