package ecnu.dll.run.c_dataset_run.pre_process._parameter_generation;

import cn.edu.dll.basic.NumberUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll._config.ParameterUtils;
import ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils.TrajectoryPreprocessRunUtils;
import ecnu.dll.utils.FormatFileName;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ParameterGenerator {
    /**
     * 参数存放目录：
     *      1. privacy budget (内部是变化的 privacy budget)
     *                |
     *                | -- file: [value 1: index 1, value 2: index 2, ..., value 5: index 5]
     *                |
     *                | -- dir:  index 1 (budget 下限，内部是n个user，每个user)
     *                        |
     *                        | -- file: [user 1: budget A, user 2: budget B,...] (用于personalized static methods)
     *                        |
     *                        | -- dir: timestamp 1
     *                                  |
     *                                  | -- file: [user1: budget A1, user2: budget B1, ...]
     *                        | -- dir: timestamp 2
     *                | -- dir:  value 2
     *                        |
     *                        | -- ...
     *                | -- dir:  value 3
     *                |...
     *      2. window size --
     *
     */

    public static void generateFixedPrivacyBudgetForAllUsers(String outputFileDir, List<Integer> userIDList, Double lowerBound, Double upperBound) {
        List<String> fixedBudgetList = ParameterUtils.getRandomDoubleList(userIDList, lowerBound, upperBound);
        String fileName = "userPrivacyBudgetFile.txt";
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, fileName);
        BasicWrite basicWrite = new BasicWrite(",");
        basicWrite.startWriting(outputPath);
        basicWrite.writeStringListWithoutSize(fixedBudgetList);
        basicWrite.endWriting();
    }
    public static void generateFixedWindowSizeForAllUsers(String outputFileDir, List<Integer> userIDList, Integer lowerBound, Integer upperBound) {
        List<String> fixedBudgetList = ParameterUtils.getRandomIntegerList(userIDList, lowerBound, upperBound);
        String fileName = "userWindowSizeFile.txt";
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, fileName);
        BasicWrite basicWrite = new BasicWrite(",");
        basicWrite.startWriting(outputPath);
        basicWrite.writeStringListWithoutSize(fixedBudgetList);
        basicWrite.endWriting();
    }

    public static void generatePrivacyBudgetForAllUsersWithTimeStamps(String outputFileDir, List<Integer> timeStampList, Double upperBound) {
        BasicRead basicRead = new BasicRead(",");
        basicRead.startReading(StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, "userPrivacyBudgetFile.txt"));
        List<String> userBudgetStringList = basicRead.readAllWithoutLineNumberRecordInFile();
        List<BasicPair<Integer, Double>> userBudgetList = new ArrayList<>(userBudgetStringList.size());
        String[] tempStringArray;
        for (String str : userBudgetStringList) {
            tempStringArray = basicRead.split(str);
            userBudgetList.add(new BasicPair<>(Integer.valueOf(tempStringArray[0]), Double.valueOf(tempStringArray[1])));
        }
        String tempOutputFilePath;
        Integer timeStampListSize = timeStampList.size();
        String formatFileNameID;
        BasicWrite basicWrite = new BasicWrite(",");
        String tempString;
        for (Integer timeStampID : timeStampList) {
            formatFileNameID = FormatFileName.getFormatNumberString(timeStampID, 0, timeStampListSize);
            tempOutputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, "timestamp_" + formatFileNameID + ".txt");
            basicWrite.startWriting(tempOutputFilePath);
            for (BasicPair<Integer, Double> userBudgetLowerBoundPair : userBudgetList) {
                Double tempRandomDouble = RandomUtil.getRandomDouble(userBudgetLowerBoundPair.getValue(), upperBound);
                tempString = StringUtil.join(",", userBudgetLowerBoundPair.getKey(), NumberUtil.roundFormat(tempRandomDouble, 2));
                basicWrite.writeOneLine(tempString);
            }
            basicWrite.endWriting();
        }
    }

    public static void generateWindowSizeForAllUsersWithTimeStamps(String outputFileDir, List<Integer> timeStampList, Integer lowerBound) {
        BasicRead basicRead = new BasicRead(",");
        basicRead.startReading(StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, "userWindowSizeFile.txt"));
        List<String> userWindowSizeStringList = basicRead.readAllWithoutLineNumberRecordInFile();
        List<BasicPair<Integer, Integer>> userWSizeList = new ArrayList<>(userWindowSizeStringList.size());
        String[] tempStringArray;
        for (String str : userWindowSizeStringList) {
            tempStringArray = basicRead.split(str);
            userWSizeList.add(new BasicPair<>(Integer.valueOf(tempStringArray[0]), Integer.valueOf(tempStringArray[1])));
        }
        String tempOutputFilePath;
        Integer timeStampListSize = timeStampList.size();
        String formatFileNameID;
        BasicWrite basicWrite = new BasicWrite(",");
        String tempString;
        for (Integer timeStampID : timeStampList) {
            formatFileNameID = FormatFileName.getFormatNumberString(timeStampID, 0, timeStampListSize);
            tempOutputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, "timestamp_" + formatFileNameID + ".txt");
            basicWrite.startWriting(tempOutputFilePath);
            for (BasicPair<Integer, Integer> userWindowSizeUpperBoundPair : userWSizeList) {
                Integer tempRandomInteger = RandomUtil.getRandomInteger(lowerBound, userWindowSizeUpperBoundPair.getValue());
                tempString = StringUtil.join(",", userWindowSizeUpperBoundPair.getKey(), tempRandomInteger);
                basicWrite.writeOneLine(tempString);
            }
            basicWrite.endWriting();
        }
    }

    public static void generatePrivacyBudget(String dirPath, List<Integer> userIDList, List<Integer> timeStampList, List<Double> privacyBudgetList){
        Double upperBound = ConfigureUtils.getPrivacyBudgetUpperBound();
        File tempDir;
        String tempDirPath;
        for (Double budget : privacyBudgetList) {
            tempDir = new File(dirPath, "budget_"+String.valueOf(budget).replace(".","-"));
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            tempDirPath = tempDir.getAbsolutePath();
            generateFixedPrivacyBudgetForAllUsers(tempDirPath, userIDList, budget, upperBound);
            generatePrivacyBudgetForAllUsersWithTimeStamps(tempDirPath, timeStampList, upperBound);
        }
    }

    public static void generateWindowSize(String dirPath, List<Integer> userIDList, List<Integer> timeStampList, List<Integer> windowSizeList) {
        Integer lowerBound = ConfigureUtils.getWindowSizeLowerBound();
        File tempDir;
        String tempDirPath;
        for (Integer windowSize : windowSizeList) {
            tempDir = new File(dirPath, "w_size_"+String.valueOf(windowSize));
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            tempDirPath = tempDir.getAbsolutePath();
            generateFixedWindowSizeForAllUsers(tempDirPath, userIDList, lowerBound, windowSize);
            generateWindowSizeForAllUsersWithTimeStamps(tempDirPath, timeStampList, lowerBound);
        }
    }


    public void generateParameters(String datasetPath, Double[] privacyBudgetArray, Integer[] windowSizeArray) {
        String basicPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "generated_parameters");

    }

    public static void main(String[] args) {
        /**
         *
         */
        String basicParameterDirectoryName = "generated_parameters";
        String privacyBudgetDirName = "1.privacy_budget";
        String windowSizeDirName = "2.window_size";
        String checkInParameterBasicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, basicParameterDirectoryName);
        String trajectoryParameterBasicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, basicParameterDirectoryName);

        List<Double> privacyBudgetList = ConfigureUtils.getIndependentPrivacyBudgetList("default");
        List<Integer> trajectoryUserIDList = TrajectoryPreprocessRunUtils.getUserIDList();
        List<Integer> trajectoryTimeStampList = TrajectoryPreprocessRunUtils.getTimeStampList();
//        generatePrivacyBudget(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryParameterBasicPath, privacyBudgetDirName), trajectoryUserIDList, trajectoryTimeStampList, privacyBudgetList);
        generateWindowSize(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryParameterBasicPath, windowSizeDirName), trajectoryUserIDList, trajectoryTimeStampList, privacyBudgetList);
    }
}
