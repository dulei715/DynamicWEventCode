package ecnu.dll.run.c_dataset_run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import ecnu.dll.dataset.real.datasetB.spetial_tools.CheckInBeanUtils;

import java.io.File;
import java.util.List;

public class RealDatasetPreprocessRun {

    private static final String basicPath = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\CheckIn_dataset_TIST2015";
    public static void dataSplit(int unitSize) {
        String dataPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "dataset_TIST2015_Checkins.txt");
        String outputSuperDirectory = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "split");
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
            basicWrite.endWriting();
            ++k;
        } while (recordList != null && !recordList.isEmpty() && recordList.size() >= unitSize);

    }

    public static void dataJoin() {
        String outputDataSuperPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "join");
        File outputDataSuperPathDirectory = new File(outputDataSuperPath);
        String splitDirectory = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "split");
        if (!outputDataSuperPathDirectory.exists()) {
            outputDataSuperPathDirectory.mkdirs();
        }
        CheckInBeanUtils.transformSplitFilesToCountry(basicPath, splitDirectory, outputDataSuperPath);
    }

    public static void main0(String[] args) {
        int unitSize = 204800;
        dataSplit(unitSize);
    }

    public static void main(String[] args) {
        dataJoin();
    }
}
