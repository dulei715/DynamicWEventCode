package others;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import ecnu.dll._config.Constant;

import java.util.List;

public class EqualTest {
    public static boolean isEqual(List<String> dataListA, List<String> dataListB) {
        int dataASize = dataListA.size();
        int dataBSize = dataListB.size();
        if (dataASize != dataBSize) {
            return false;
        }
        for (String tempDataA : dataListA) {
            if (!dataListB.contains(tempDataA)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isEqual(String filePathA, String filePathB) {
        List<String> dataA, dataB;
        BasicRead basicRead = new BasicRead(",");
        basicRead.startReading(filePathA);
        dataA = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        basicRead.startReading(filePathB);
        dataB = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        return isEqual(dataA, dataB);
    }

    public static void main(String[] args) {
        String basicPath = Constant.basicDatasetPath;
        String filePathA = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test", "timestamp_0000.txt");
        String filePathB = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test", "timestamp_0001.txt");
//        String filePathA = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test", "timestamp_17_aaai01.txt");
//        String filePathB = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test", "timestamp_17_vldb05.txt");
//        String filePathA = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test", "timestamp_0_aaai01.txt");
//        String filePathB = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "test", "timestamp_0_vldb05.txt");
        boolean result = isEqual(filePathA, filePathB);
        System.out.println(result);
    }
}
