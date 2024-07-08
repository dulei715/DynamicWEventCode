package ecnu.dll.run.c_dataset_run.pre_process.real_dataset.sub_thread.trajectory;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetA.handled_struct.TrajectorySimplifiedBean;
import ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils.FileMergeEnhancedFilter;
import ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils.PreprocessRunUtils;
import ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils.TrajectoryPreprocessRunUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TrajectoryMergeThread implements Runnable{
    private Long fileNumberStart;
    private Long fileNumberEnd;
    private String inputDirectoryPath;
    private String outputDirectoryName;

    public TrajectoryMergeThread(Long fileNumberStart, Long fileNumberEnd, String inputDirectoryPath, String outputDirectoryName) {
        this.fileNumberStart = fileNumberStart;
        this.fileNumberEnd = fileNumberEnd;
        this.inputDirectoryPath = inputDirectoryPath;
        this.outputDirectoryName = outputDirectoryName;
    }

    public void mergeToRawData() {
//        String path = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "shuffle_by_time_slot");
        File directoryFile = new File(inputDirectoryPath);
        File[] files = directoryFile.listFiles(new FileMergeEnhancedFilter(fileNumberStart, fileNumberEnd));
        BasicRead basicRead = new BasicRead(",");
        BasicWrite basicWrite = new BasicWrite(",");
        List<String> tempDataList;
        TrajectorySimplifiedBean tempBean;
        Map<Integer, BasicPair<Long, String>> userTimeSlotLocationMap = TrajectoryPreprocessRunUtils.getInitialUserTimeSlotLocationMap(StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "extract_data"));
        String outputDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, outputDirectoryName);

        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempDataList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            for (String lineString : tempDataList) {
                tempBean = TrajectorySimplifiedBean.toBean(basicRead.split(lineString));
                PreprocessRunUtils.updateLatestTimeSlotData(userTimeSlotLocationMap, tempBean.getUserID(), tempBean.getTimestamp(), tempBean.getAreaIndex()+"");
            }
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, outputDirectoryPath, file.getName()));
            for (Map.Entry<Integer, BasicPair<Long, String>> entry : userTimeSlotLocationMap.entrySet()) {
                basicWrite.writeOneLine(TrajectoryPreprocessRunUtils.toSimpleTrajectoryString(entry));
            }
            basicWrite.endWriting();
        }
    }
    @Override
    public void run() {
        mergeToRawData();
        System.out.println("end thread...");
    }
}
