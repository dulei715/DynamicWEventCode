package ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation.utils.PositionGroupUtils;
import ecnu.dll.utils.filters.NumberTxtFilter;

import java.io.File;
import java.util.*;

public class PositionGroupGenerator {
    // 仅适用于真实数据集位置类别比较多的情况。分组的类别要小于位置类别
    public static void generatePositionToGroup(String basicPath, String positionFileName) {
        String outputPositionPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "dim_ablation_run", "exchange_info_ablation", "groupPosition.txt");
        String currentOutputRunInputPath;
        List<Integer> positionSizeList = ConfigureUtils.getDefaultPositionSizeList();
        String positionFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", positionFileName);
        BasicRead basicRead = new BasicRead();
        basicRead.startReading(positionFilePath);
        Set<String> totalPositionSet = new HashSet<>(basicRead.readAllWithoutLineNumberRecordInFile());
        basicRead.endReading();
        Map<Integer, Map<String, String>> positionSizeMapToGroupMap = new HashMap<>();
        for (Integer positionSize : positionSizeList) {
            positionSizeMapToGroupMap.put(positionSize, PositionGroupUtils.toGroup(totalPositionSet, positionSize));
        }



        File runInputFileDir = new File(StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "runInput"));
        File[] originalDataFile = runInputFileDir.listFiles(new NumberTxtFilter());

        String splitTag = ",";
        basicRead = new BasicRead(splitTag);
        List<String> currentOriginalDataList;
        BasicWrite basicWrite = new BasicWrite(splitTag);
        List<String> outputGroupDataList;
        for (File file : originalDataFile) {
            basicRead.startReading(file.getAbsolutePath());
            currentOriginalDataList = basicRead.readAllWithoutLineNumberRecordInFile();
            for (Integer positionSize : positionSizeList) {
                currentOutputRunInputPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "runInput_dim_ablation", "positionSize_"+positionSize, file.getName());
                Map<String, String> currentGroupMap = positionSizeMapToGroupMap.get(positionSize);
                outputGroupDataList = new ArrayList<>();

                for (String originalData : currentOriginalDataList) {
                    String[] splitDataArray = basicRead.split(originalData);
                    splitDataArray[1] = currentGroupMap.get(splitDataArray[1]);
                    String groupData = StringUtil.join(splitTag, splitDataArray);
                    outputGroupDataList.add(groupData);
                }
                basicWrite.startWriting(currentOutputRunInputPath);
                basicWrite.writeStringListWithoutSize(outputGroupDataList);
            }

        }
        basicWrite.endWriting();
        basicRead.endReading();
    }
}
