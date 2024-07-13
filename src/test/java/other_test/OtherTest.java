package other_test;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import ecnu.dll._config.Constant;
import ecnu.dll.utils.filters.TxtFilter;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class OtherTest {
    @Test
    public void cutUserDataInFiles() {
        int size = 20;
        String dirPathInput = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "_raw_runInput");
        String dirPathOutput = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "runInput");
        File dirFile = new File(dirPathInput);
        File[] files = dirFile.listFiles(new TxtFilter());
        BasicRead basicRead = new BasicRead(",");
        BasicWrite basicWrite = new BasicWrite(",");
        List<String> tempData;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempData = basicRead.readGivenLineSize(size);
            basicRead.endReading();
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, dirPathOutput, file.getName()));
            basicWrite.writeStringListWithoutSize(tempData);
            basicWrite.endWriting();
        }
    }
}
