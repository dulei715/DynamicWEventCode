package ecnu.dll.run.c_dataset_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetB.spetial_tools.CheckInBeanUtils;

import java.io.File;
import java.util.List;

public class RealDataSetPreprocessRun {

    public static void dataSplit(int unitSize) {
        String dataPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "dataset_TIST2015_Checkins.txt");
        String outputSuperDirectory = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "split");
//        System.out.println(dataPath);
//        System.out.println(outputSuperDirectory);

        File outputDataSuperPathDirectory = new File(outputSuperDirectory);
        if (!outputDataSuperPathDirectory.exists()) {
            outputDataSuperPathDirectory.mkdirs();
        }
        int k = 1;
        BasicRead basicRead = new BasicRead();
        BasicWrite basicWrite = new BasicWrite();
        List<String> recordList;

        basicRead.startReading(dataPath);
        do {
            recordList = basicRead.readGivenLineSize(unitSize);
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, outputDataSuperPathDirectory, k+".txt"));
            basicWrite.writeStringListWithoutSize(recordList);
            System.out.println("Finish " + k + ".txt");
            basicWrite.endWriting();
            ++k;
        } while (recordList != null && !recordList.isEmpty() && recordList.size() >= unitSize);

    }

    public static void dataJoin() {
        String outputDataSuperPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "join");
        File outputDataSuperPathDirectory = new File(outputDataSuperPath);
        String splitDirectory = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "split");
        if (!outputDataSuperPathDirectory.exists()) {
            outputDataSuperPathDirectory.mkdirs();
        }
        CheckInBeanUtils.transformSplitFilesToCountry(Constant.checkInFilePath, splitDirectory, outputDataSuperPath);
    }



    public static void main1(String[] args) {
        int unitSize = 204800;
        dataSplit(unitSize);
//        System.out.println("Hello RealDataSetPreprocessRun");
    }

    public static void main(String[] args) {
        dataJoin();
    }
}
