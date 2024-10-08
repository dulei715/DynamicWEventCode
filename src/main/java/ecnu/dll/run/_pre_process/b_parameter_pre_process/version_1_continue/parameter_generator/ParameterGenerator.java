package ecnu.dll.run._pre_process.b_parameter_pre_process.version_1_continue.parameter_generator;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll._config.ParameterUtils;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_handler.utils.PreprocessRunUtils;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_1_continue.parameter_generator.sub_thread.PrivacyBudgetWithinTimeRangeGenerator;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_1_continue.parameter_generator.sub_thread.WindowSizeWithinTimeRangeGenerator;
import ecnu.dll.utils.thread.ThreadUtils;

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

    public static void generateFixedPrivacyBudgetForAllUsers(String outputFileDir, String privacyBudgetFileNameForPersonalized, List<Integer> userIDList, Double lowerBound, Double upperBound) {
        List<String> fixedBudgetList = ParameterUtils.getRandomDoubleList(userIDList, lowerBound, upperBound);
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, privacyBudgetFileNameForPersonalized);
        BasicWrite basicWrite = new BasicWrite(",");
        basicWrite.startWriting(outputPath);
        basicWrite.writeStringListWithoutSize(fixedBudgetList);
        basicWrite.endWriting();
    }
    public static void generateFixedWindowSizeForAllUsers(String outputFileDir, String windowSizeFileNameForPersonalized, List<Integer> userIDList, Integer lowerBound, Integer upperBound) {
        List<String> fixedBudgetList = ParameterUtils.getRandomIntegerList(userIDList, lowerBound, upperBound);
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, windowSizeFileNameForPersonalized);
        BasicWrite basicWrite = new BasicWrite(",");
        basicWrite.startWriting(outputPath);
        basicWrite.writeStringListWithoutSize(fixedBudgetList);
        basicWrite.endWriting();
    }

    public static void generatePrivacyBudgetForAllUsersWithTimeStamps(String outputFileDir, String privacyBudgetFileNameForPersonalized, List<Integer> timeStampList, Double privacyUpperBound, Double remainBackwardPrivacyLowerBound, Double remainBackwardPrivacyUpperBound) {
        BasicRead basicRead = new BasicRead(",");
        basicRead.startReading(StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, privacyBudgetFileNameForPersonalized));
        List<String> userBudgetStringList = basicRead.readAllWithoutLineNumberRecordInFile();
        List<BasicPair<Integer, Double>> userBudgetList = new ArrayList<>(userBudgetStringList.size());
        String[] tempStringArray;
        for (String str : userBudgetStringList) {
            tempStringArray = basicRead.split(str);
            userBudgetList.add(new BasicPair<>(Integer.valueOf(tempStringArray[0]), Double.valueOf(tempStringArray[1])));
        }
//        generatePrivacyBudgetWithinGivenTimeRange(outputFileDir, timeStampList, upperBound, userBudgetList);
        int threadSize = 5;
        int timeStampListSize = timeStampList.size();
        List<Integer> threadStartIndexList = ThreadUtils.getThreadStartIndexList(threadSize, 0, timeStampListSize);
        Thread tempTread;
        Runnable tempRunnable;
        Integer startIndex, endIndex;
        threadSize = threadStartIndexList.size(); // 只是为了加强一下
        for (int i = 0; i < threadSize; i++) {
            startIndex = threadStartIndexList.get(i);
            if (i < threadSize - 1) {
                endIndex = threadStartIndexList.get(i+1) - 1;
            } else {
                endIndex = timeStampListSize - 1;
            }
//            MyPrint.showList(timeStampList, "; ");
//            tempRunnable = new PrivacyBudgetWithinTimeRangeGenerator(outputFileDir, timeStampList, privacyUpperBound, remainBackwardPrivacyLowerBound, remainBackwardPrivacyUpperBound, userBudgetList, startIndex, endIndex);
            tempRunnable = new PrivacyBudgetWithinTimeRangeGenerator(outputFileDir, timeStampList, privacyUpperBound, remainBackwardPrivacyLowerBound, remainBackwardPrivacyUpperBound, userBudgetList, startIndex, endIndex);
            tempTread = new Thread(tempRunnable);
            tempTread.start();
            System.out.println("Start privacy budget thread " + tempTread.getName() + " with id " + tempTread.getId());
        }
    }



    public static void generateWindowSizeForAllUsersWithTimeStamps(String outputFileDir, String windowSizeFileNameForPersonalized, List<Integer> timeStampList, Integer windowSizeLowerBound, Integer backwardWindowSizeLowerBound) {
        BasicRead basicRead = new BasicRead(",");
        basicRead.startReading(StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, windowSizeFileNameForPersonalized));
        List<String> userWindowSizeStringList = basicRead.readAllWithoutLineNumberRecordInFile();
        List<BasicPair<Integer, Integer>> userWSizeList = new ArrayList<>(userWindowSizeStringList.size());
        String[] tempStringArray;
        for (String str : userWindowSizeStringList) {
            tempStringArray = basicRead.split(str);
            userWSizeList.add(new BasicPair<>(Integer.valueOf(tempStringArray[0]), Integer.valueOf(tempStringArray[1])));
        }
//        generateWindowSizeWithinTimeRange(outputFileDir, timeStampList, lowerBound, userWSizeList);
        int threadSize = 5;
        int timeStampListSize = timeStampList.size();
        List<Integer> threadStartIndexList = ThreadUtils.getThreadStartIndexList(threadSize, 0, timeStampListSize);
        Thread tempTread;
        Runnable tempRunnable;
        Integer startIndex, endIndex;
        threadSize = threadStartIndexList.size(); // 只是为了加强一下
        for (int i = 0; i < threadSize; i++) {
            startIndex = threadStartIndexList.get(i);
            if (i < threadSize - 1) {
                endIndex = threadStartIndexList.get(i+1) - 1;
            } else {
                endIndex = timeStampListSize - 1;
            }
            tempRunnable = new WindowSizeWithinTimeRangeGenerator(outputFileDir, timeStampList, windowSizeLowerBound, backwardWindowSizeLowerBound, userWSizeList, startIndex, endIndex);
            tempTread = new Thread(tempRunnable);
            tempTread.start();
            System.out.println("Start window size thread " + tempTread.getName() + " with id " + tempTread.getId());
        }
    }



    public static void generatePrivacyBudget(String dirPath, String privacyBudgetFileNameForPersonalized, final List<Integer> userTypeIDList, final List<Integer> timeStampList, final List<Double> privacyBudgetList){
        Double privacyUpperBound = ConfigureUtils.getPrivacyBudgetUpperBound();
        Double remainBackwardPrivacyUpperBound = ConfigureUtils.getBackwardPrivacyBudgetUpperBoundDifference();
        //todo: 这里把backward privacy设置为最大
//        Double remainBackwardPrivacyLowerBound = ConfigureUtils.getBackwardPrivacyBudgetLowerBoundDifference();
        Double remainBackwardPrivacyLowerBound = remainBackwardPrivacyUpperBound;
        File tempDir;
        String tempDirPath;
        for (Double budget : privacyBudgetList) {
            tempDir = new File(dirPath, "budget_"+String.valueOf(budget).replace(".","-"));
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            tempDirPath = tempDir.getAbsolutePath();
            generateFixedPrivacyBudgetForAllUsers(tempDirPath, privacyBudgetFileNameForPersonalized, userTypeIDList, budget, privacyUpperBound);
            generatePrivacyBudgetForAllUsersWithTimeStamps(tempDirPath, privacyBudgetFileNameForPersonalized, timeStampList, privacyUpperBound, remainBackwardPrivacyLowerBound, remainBackwardPrivacyUpperBound);
        }
    }

    public static void generateWindowSize(String dirPath, String windowSizeFileNameForPersonalized, final List<Integer> userIDList, final List<Integer> timeStampList, final List<Integer> windowSizeList) {
        Integer windowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        Integer backwardWindowSizeLowerBound = ConfigureUtils.getWindowSizeLowerBound();
        File tempDir;
        String tempDirPath;
        for (Integer windowSize : windowSizeList) {
            tempDir = new File(dirPath, "w_size_"+String.valueOf(windowSize));
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            tempDirPath = tempDir.getAbsolutePath();
            generateFixedWindowSizeForAllUsers(tempDirPath, windowSizeFileNameForPersonalized, userIDList, windowSizeLowerBound, windowSize);
            generateWindowSizeForAllUsersWithTimeStamps(tempDirPath, windowSizeFileNameForPersonalized, timeStampList, windowSizeLowerBound, backwardWindowSizeLowerBound);
        }
    }


    public void generateParameters(String datasetPath, Double[] privacyBudgetArray, Integer[] windowSizeArray) {
        String basicPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetPath, "generated_parameters");

    }

    public static void generateParametersForTrajectory(String privacyBudgetConfigVarianceName, String windowSizeConfigVarianceName, String userTypeIDFileName, String basicParameterGenerationDirectoryName, String privacyBudgetFileNameForPersonalized, String windowSizeFileNameForPersonalized) {
        /**
         *
         */
        String privacyBudgetDirName = "1.privacy_budget";
        String windowSizeDirName = "2.window_size";
        String trajectoryParameterBasicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, basicParameterGenerationDirectoryName);

        List<Double> privacyBudgetList = ConfigureUtils.getIndependentPrivacyBudgetList("default");
        List<Integer> windowSizeList = ConfigureUtils.getIndependentWindowSizeList("default");
//        List<Integer> trajectoryUserIDList = TrajectoryPreprocessRunUtils.getUserIDList();
        List<Integer> trajectoryUserIDList = PreprocessRunUtils.getUserTypeIDList(Constant.trajectoriesFilePath, userTypeIDFileName);
        List<Integer> trajectoryTimeStampList = PreprocessRunUtils.getTimeStampList(Constant.trajectoriesFilePath);
        generatePrivacyBudget(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryParameterBasicPath, privacyBudgetDirName), privacyBudgetConfigVarianceName, trajectoryUserIDList, trajectoryTimeStampList, privacyBudgetList);
        generateWindowSize(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryParameterBasicPath, windowSizeDirName), windowSizeConfigVarianceName, trajectoryUserIDList, trajectoryTimeStampList, windowSizeList);
    }

    public static void generateParametersForCheckIn(String privacyBudgetConfigVarianceName, String windowSizeConfigVarianceName, String userTypeIDFileName, String basicParameterGenerationDirectoryName, String privacyBudgetFileNameForPersonalized, String windowSizeFileNameForPersonalized) {
        String privacyBudgetDirName = "1.privacy_budget";
        String windowSizeDirName = "2.window_size";
        String checkInParameterBasicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, basicParameterGenerationDirectoryName);
        List<Double> privacyBudgetList = ConfigureUtils.getIndependentPrivacyBudgetList("default");
        List<Integer> windowSizeList = ConfigureUtils.getIndependentWindowSizeList("default");
//        List<Integer> checkInUserIDList = CheckInPreprocessRunUtils.getUserIDList();
        List<Integer> checkInUserIDList = PreprocessRunUtils.getUserTypeIDList(Constant.checkInFilePath, userTypeIDFileName);
        List<Integer> checkInTimeStampList = PreprocessRunUtils.getTimeStampList(Constant.checkInFilePath);
//        MyPrint.showList(checkInTimeStampList, "; ");
        generatePrivacyBudget(StringUtil.join(ConstantValues.FILE_SPLIT, checkInParameterBasicPath, privacyBudgetDirName), privacyBudgetConfigVarianceName, checkInUserIDList, checkInTimeStampList, privacyBudgetList);
        generateWindowSize(StringUtil.join(ConstantValues.FILE_SPLIT, checkInParameterBasicPath, windowSizeDirName), windowSizeConfigVarianceName, checkInUserIDList, checkInTimeStampList, windowSizeList);
    }

    public static void main(String[] args) {
        String dirPath = args[0];
        System.out.println(dirPath);
        File file = new File(dirPath);
//        File[] files = file.listFiles(new TxtFilter());
        File[] files = file.listFiles();
        System.out.println(files.length);
        MyPrint.showSplitLine("*", 50);
        for (File file1 : files) {
            System.out.println(file1.getName());
        }
    }

}
