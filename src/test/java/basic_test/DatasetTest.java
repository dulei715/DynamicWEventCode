package basic_test;

import _test_data.TestData;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import ecnu.dll._config.ConfigureUtils;
import org.junit.Test;

import java.util.List;

public class DatasetTest {


    @Test
    public void fun1() {
        String checkInFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, TestData.basicDatasetPath, TestData.checkInFileName);
        String dataPath = StringUtil.join(ConstantValues.FILE_SPLIT, checkInFilePath, "dataset_TIST2015_Cities.txt");
        BasicRead basicRead = new BasicRead();
        basicRead.startReading(dataPath);
        List<String> recordList = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        System.out.println(recordList.size());
    }
    @Test
    public void fun2() {
        String checkInFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, TestData.basicDatasetPath, TestData.checkInFileName);
        String dataPath = StringUtil.join(ConstantValues.FILE_SPLIT, checkInFilePath, "dataset_TIST2015_Cities.txt");
        BasicRead basicRead = new BasicRead();
        basicRead.startReading(dataPath);
//        List<String> recordList = basicRead.readAllWithoutLineNumberRecordInFile();
        List<String> recordList;
        do {
            recordList = basicRead.readGivenLineSize(10);
            MyPrint.showList(recordList, ConstantValues.LINE_SPLIT);
            MyPrint.showSplitLine("*", 150);
        } while (recordList != null && !recordList.isEmpty() && recordList.size() >= 10);

        basicRead.endReading();
//        System.out.println(recordList.size());
    }
}
