package ecnu.dll.run._pre_process.b_parameter_pre_process.version_4_two_group.parameter_generator;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.filter.file_filter.TxtFilter;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_generator.DiscreteParameterGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.utils.UserGroupUtils;
import ecnu.dll.run.utils.PatternUtils;
import ecnu.dll.utils.io.ListReadUtils;
import ecnu.dll.utils.io.ListWriteUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoGroupParameterGenerator {
    public static void generateUserIDTypeRatio(String basicPath) {
        String outputBasicDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "group_generated_parameters", "3.user_ratio", "1.userTypeIDRatio");
        String ratioFileName;
        String outputPath;
        PureTriple<String, Double, List<Double>> ratioTriple;
        try {
            ratioTriple = ConfigureUtils.getIndependentData("TwoFixedUserRatio", "default", "default");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        List<String> userIDTypeRatio = UserGroupUtils.getUserIDTypeRatio(ratioTriple.getTag());
//        ListWriteUtils.writeList(outputPath, userIDTypeRatio, ",");
        List<Double> ratioList = ratioTriple.getTag(), typeRatioList = new ArrayList<>(2);
        List<String> userIDTypeRatio;
        for (Double ratio : ratioList) {
            typeRatioList.clear();
            typeRatioList.add(ratio);
            typeRatioList.add(1-ratio);
            userIDTypeRatio = UserGroupUtils.getUserIDTypeRatio(typeRatioList);
            ratioFileName = PatternUtils.toRatioFileNamePattern(ratio, ".txt");
            outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputBasicDir, ratioFileName);
            ListWriteUtils.writeList(outputPath, userIDTypeRatio, ",");
        }
    }
    public static void generateUserToType(String basicPath) {
        FileFilter txtFilter = new TxtFilter();
        String userIDInputPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", "user.txt");
        String userTypeIDRatioInputPathDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "group_generated_parameters", "3.user_ratio", "1.userTypeIDRatio");
        String outputPathDir = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "group_generated_parameters", "3.user_ratio", "2.user_to_type_ratio");
        PureTriple<String, Double, List<Double>> ratioTriple;
        BasicRead basicRead = new BasicRead(",");
        BasicWrite basicWrite = new BasicWrite(",");
        String fileName, outputFilePath;
        List<String> userIDList = ListReadUtils.readAllDataList(userIDInputPath, ",");
        List<String> userTypeIDRatioList, userToTypeRatioList;
        File userTypeIDRatioInputDir = new File(userTypeIDRatioInputPathDir);
        File[] userTypeIDRatioFiles = userTypeIDRatioInputDir.listFiles(txtFilter);
        for (File typeRatioFile : userTypeIDRatioFiles) {
            fileName = typeRatioFile.getName();
            basicRead.startReading(typeRatioFile.getAbsolutePath());
            userTypeIDRatioList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            userToTypeRatioList = UserGroupUtils.getUserToTypeByRatio(userIDList, userTypeIDRatioList);
            outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, outputPathDir, fileName);
            basicWrite.startWriting(outputFilePath);
            basicWrite.writeStringListWithoutSize(userToTypeRatioList);
            basicWrite.endWriting();
        }
    }

    public static void generatePrivacyBudget(String datasetPath){
        String basicDirPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath,
                "group_generated_parameters", "3.user_ratio");
        String typePrivacyBudgetDirPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDirPath, "3.typePrivacyBudget");
        Double[] twoFixedPrivacyBudget = ConfigureUtils.getTwoFixedPrivacyBudget();
        String fileName, outputFilePath;
        fileName = "typePrivacyBudgetFile.txt";
        outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, typePrivacyBudgetDirPath, fileName);
        List<String> outputDataList = new ArrayList<>();
        outputDataList.add(String.format("0,%.2f", twoFixedPrivacyBudget[0]));
        outputDataList.add(String.format("1,%.2f", twoFixedPrivacyBudget[1]));
        ListWriteUtils.writeList(outputFilePath, outputDataList, ",");
    }
    public static void generateWindowSize(String datasetPath){
        String basicDirPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath,
                "group_generated_parameters", "3.user_ratio");
        String typePrivacyBudgetDirPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDirPath, "4.typeWindowSize");
        Integer[] twoFixedWindowSize = ConfigureUtils.getTwoFixedWindowSize();
        String fileName, outputFilePath;
        fileName = "typeWindowSizeFile.txt";
        outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, typePrivacyBudgetDirPath, fileName);
        List<String> outputDataList = new ArrayList<>();
        outputDataList.add(String.format("0,%d", twoFixedWindowSize[0]));
        outputDataList.add(String.format("1,%d", twoFixedWindowSize[1]));
        ListWriteUtils.writeList(outputFilePath, outputDataList, ",");
    }

    public static void generateInternalParameters(String basicPath) {
        generatePrivacyBudget(basicPath);
        generateWindowSize(basicPath);
    }



    public static void main(String[] args) {
//        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT,
//                Constant.basicDatasetPath, "..", "test", "two_budget_message.txt");
//        Double[] privacyBudgetArray = new Double[]{0.2, 0.6};
//        List<Integer> typeIDList = BasicArrayUtil.getIncreaseIntegerNumberList(1, 1, 5);
        String basicPath = Constant.trajectoriesFilePath;
//        generateUserIDTypeRatio(basicPath);
//        generateUserToType(basicPath);
        generatePrivacyBudget(basicPath);
        generateWindowSize(basicPath);
    }
}
