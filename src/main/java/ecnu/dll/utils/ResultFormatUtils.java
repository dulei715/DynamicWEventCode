package ecnu.dll.utils;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.filter.file_filter.DirectoryFileFilter;
import ecnu.dll.utils.io.ListReadUtils;
import ecnu.dll.utils.io.ListWriteUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultFormatUtils {
    public static void insertAttribute(String inputFilePath, String outputFilePath, String elementSplit, final List<Integer> sortedColumnInsertIndexList, final List<String> insertAttributeListSortedByIndex, final List<String> insertDefaultValueListSortedByIndexList) {
        List<String> rawDataList = ListReadUtils.readAllDataList(inputFilePath, elementSplit);
        int size = rawDataList.size(), insertSize = sortedColumnInsertIndexList.size();
        List<String> tempList = Arrays.asList(rawDataList.get(0).split(elementSplit)), newTempList;
        List<String> resultDataList = new ArrayList<>(size);

        newTempList = new ArrayList<>(tempList);
        for (int i = insertSize - 1; i >= 0 ; i--) {
            newTempList.add(sortedColumnInsertIndexList.get(i), insertAttributeListSortedByIndex.get(i));
        }
        resultDataList.add(StringUtil.join(elementSplit, newTempList));
        for (int j = 1; j < size; j++) {
            tempList = Arrays.asList(rawDataList.get(j).split(elementSplit));
            newTempList = new ArrayList<>(tempList);
            for (int i = insertSize - 1; i >= 0 ; i--) {
                newTempList.add(sortedColumnInsertIndexList.get(i), insertDefaultValueListSortedByIndexList.get(i));
            }
            resultDataList.add(StringUtil.join(elementSplit, newTempList));
        }
        ListWriteUtils.writeList(outputFilePath, resultDataList, elementSplit);
    }

    public static void main(String[] args) {
        String basicPath = "/Users/mac/MainFiles/1.Research/dataset/3_stream_dp";
        String inputDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "2.result_internal_before");
        String outputDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "2.result_internal");
        File inputDirAExperimentFile = new File(inputDir), outputDirAExperimentFile = new File(outputDir);
        File outputDirBDatasetFile, outputDirCParameterFile;
        String outputDirBDatasetName, outputDirCParameterName;
        String dataFileName = "result.txt";
        DirectoryFileFilter directoryFileFilter = new DirectoryFileFilter();
        String inputDataPath, outputDataPath;
        String elementSplit = ",";

        if (!outputDirAExperimentFile.exists()) {
            outputDirAExperimentFile.mkdirs();
        }
        for (File inputDirBDatasetFile : inputDirAExperimentFile.listFiles(directoryFileFilter)) {
            outputDirBDatasetName = inputDirBDatasetFile.getName();
            outputDirBDatasetFile = new File(outputDirAExperimentFile, outputDirBDatasetName);
            if (!outputDirBDatasetFile.exists()) {
                outputDirBDatasetFile.mkdirs();
            }
            for (File inputDirCParameterFile : inputDirBDatasetFile.listFiles(directoryFileFilter)) {
                outputDirCParameterName = inputDirCParameterFile.getName();
                outputDirCParameterFile = new File(outputDirBDatasetFile, outputDirCParameterName);
                if (!outputDirCParameterFile.exists()) {
                    outputDirCParameterFile.mkdirs();
                }
                inputDataPath = StringUtil.join(ConstantValues.FILE_SPLIT, inputDirCParameterFile.getAbsolutePath(), dataFileName);
                outputDataPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputDirCParameterFile.getAbsolutePath(), dataFileName);
                insertAttribute(inputDataPath, outputDataPath, elementSplit, Arrays.asList(7, 8), Arrays.asList("BJSD", "MJSD"), Arrays.asList("0.0", "0.0"));
            }
        }
    }
}
