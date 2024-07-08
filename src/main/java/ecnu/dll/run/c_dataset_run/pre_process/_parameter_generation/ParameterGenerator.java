package ecnu.dll.run.c_dataset_run.pre_process._parameter_generation;

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
        List<String> userBudgetStringList = basicRead.readAll();
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
            tempOutputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, "time_"+formatFileNameID);
            basicWrite.startWriting(tempOutputFilePath);
            for (BasicPair<Integer, Double> userBudgetLowerBoundPair : userBudgetList) {
                tempString = StringUtil.join(ConstantValues.FILE_SPLIT, userBudgetLowerBoundPair.getKey(), RandomUtil.getRandomDouble(userBudgetLowerBoundPair.getValue(), upperBound));
                basicWrite.writeOneLine(tempString);
            }
            basicWrite.endWriting();
        }
    }

    public static void generatePrivacyBudget(String dirPath, List<Integer> userIDList, String timeStampDirPath, List<Double> privacyBudgetList){
        File timeStampDir = new File(timeStampDirPath);
        String[] timeStampFileNameArray = timeStampDir.list();
        List<Integer> timeStampList = new ArrayList<>(timeStampFileNameArray.length);
        for (String fileName : timeStampFileNameArray) {
            timeStampList.add(Integer.valueOf(FormatFileName.extractNumString(fileName, "_", ".")));
        }
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



    public void generateParameters(String datasetPath, Double[] privacyBudgetArray, Integer[] windowSizeArray) {
        String basicPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "generated_parameters");

    }

    public static void main(String[] args) {
        /**
         *
         */
        String basicParameterDirectoryName = "generated_parameters";
        String privacyBudgetDirName = "1.privacy_budget";
        String checkInParameterBasicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, basicParameterDirectoryName);
        String trajectoryParameterBasicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, basicParameterDirectoryName);

        List<Double> privacyBudgetList = ConfigureUtils.getIndependentPrivacyBudgetList("default");
        // todo: 修改userID获取方式，trajectory截取了一部分，可能导致一些user不存在
        String trajectoryUserIDDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "taxi_log_2008_by_id_filter");
        List<Integer> userIDList = TrajectoryPreprocessRunUtils.getUserIDList(trajectoryUserIDDir);
        String trajectoryTimeStampDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "")
        generatePrivacyBudget(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryParameterBasicPath, privacyBudgetDirName), userIDList, );
    }
}
