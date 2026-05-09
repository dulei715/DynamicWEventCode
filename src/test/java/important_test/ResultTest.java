package important_test;

import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.basic.NumberUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.collection.ListUtils;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.filter.file_filter.DirectoryFileFilter;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.bean_structs.BeanInterface;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import ecnu.dll.run.c_dataset_run.utils.ResultBean;
import ecnu.dll.utils.io.ListReadUtils;
import ecnu.dll.utils.run.ParameterUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResultTest {

    public static List<ResultBean> searchBeanByName(List<ResultBean> data, String name) {
        List<ResultBean> resultList = new ArrayList<>();
        for (ResultBean bean : data) {
            if (bean.getName().equals(name)) {
                resultList.add(bean);
            }
        }
        return resultList;
    }

    public static List[] getAverageImprovementForBudgetChange(File[] fileDirFile, String furtherImproveMethodName, String improveMethodName, String originalMethodName, boolean whetherLog) {
        BeanInterface<ResultBean> bean = new ResultBean();
        String dirName;
        int dataLength = fileDirFile.length;
        List<Double> epsilonList = new ArrayList<>(dataLength);
        List<Double> improveRatioList = new ArrayList<>(dataLength);
        List<Double> furtherImproveRatioList = new ArrayList<>(dataLength);
        List<ResultBean> tempResult;
        ResultBean improveBean, furtherImproveBean, originalBean;
        File resultFile;
        Double originalValue, improveValue, furtherImproveValue;
        for (File dirFile : fileDirFile) {
            dirName = dirFile.getName();
            epsilonList.add(ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(dirName).getKey());
            resultFile = new File(dirFile, "result.txt");
            tempResult = CSVReadEnhanced.readDataToBeanList(resultFile.getAbsolutePath(), bean);
            improveBean = searchBeanByName(tempResult, improveMethodName).get(0);
            originalBean = searchBeanByName(tempResult, originalMethodName).get(0);
            furtherImproveBean = searchBeanByName(tempResult, furtherImproveMethodName).get(0);
            originalValue = originalBean.getMre();
            improveValue = improveBean.getMre();
            furtherImproveValue = furtherImproveBean.getMre();
            if (whetherLog) {
                improveRatioList.add((Math.log(originalValue) - Math.log(improveValue)) / Math.log(originalValue));
                furtherImproveRatioList.add((Math.log(originalValue) - Math.log(furtherImproveValue)) / Math.log(originalValue));
            } else {
                improveRatioList.add((originalValue - improveValue) / originalValue);
                furtherImproveRatioList.add((originalValue - furtherImproveValue) / originalValue);
            }
        }
        List[] resultList = new List[] {
                epsilonList,
                improveRatioList,
                furtherImproveRatioList
        };
        return resultList;
    }
    public static List[] getAverageImprovementForWindowSizeChange(File[] fileDirFile, String furtherImproveMethodName, String improveMethodName, String originalMethodName, boolean whetherLog) {
        BeanInterface<ResultBean> bean = new ResultBean();
        String dirName;
        int dataLength = fileDirFile.length;
        List<Integer> windowSizeList = new ArrayList<>(dataLength);
        List<Double> improveRatioList = new ArrayList<>(dataLength);
        List<Double> furtherImproveRatioList = new ArrayList<>(dataLength);
        List<ResultBean> tempResult;
        ResultBean improveBean, furtherImproveBean, originalBean;
        File resultFile;
        Double originalValue, improveValue, furtherImproveValue;
        for (File dirFile : fileDirFile) {
            dirName = dirFile.getName();
            windowSizeList.add(ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(dirName).getValue());
            resultFile = new File(dirFile, "result.txt");
            tempResult = CSVReadEnhanced.readDataToBeanList(resultFile.getAbsolutePath(), bean);
            improveBean = searchBeanByName(tempResult, improveMethodName).get(0);
            furtherImproveBean = searchBeanByName(tempResult, furtherImproveMethodName).get(0);
            originalBean = searchBeanByName(tempResult, originalMethodName).get(0);
            originalValue = originalBean.getMre();
            improveValue = improveBean.getMre();
            furtherImproveValue = furtherImproveBean.getMre();
            if (whetherLog) {
                improveRatioList.add((Math.log(originalValue) - Math.log(improveValue)) / Math.log(originalValue));
                furtherImproveRatioList.add((Math.log(originalValue) - Math.log(furtherImproveValue)) / Math.log(originalValue));
            } else {
                improveRatioList.add((originalValue - improveValue) / originalValue);
                furtherImproveRatioList.add((originalValue - furtherImproveValue) / originalValue);
            }
        }
        List[] resultList = new List[] {
                windowSizeList,
                improveRatioList,
                furtherImproveRatioList
        };
        return resultList;
    }

    @Test
    public void testBudgetChangeImprove() {
//        String datasetOrderName = "1.trajectory_containing_ldp_result";
        String datasetOrderName = "2.check_in_containing_ldp_result";
//        String datasetOrderName = "5.log_containing_ldp_result";
        String originalMethodName = "BD";
        String improveMethodName = "PBD";
        String furtherImproveMethodName = "PDBD";
//        String originalMethodName = "BA";
//        String improveMethodName = "PBA";
//        String furtherImproveMethodName = "PDBA";
        Integer defaultWindowSize = 120;
//        boolean whetherLog = false;
        boolean whetherLog = true;
//        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "1.result", datasetOrderName);
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "4.result_containing_ldp", datasetOrderName);
        File file = new File(datasetPath);
        File[] totalDirFileArray = file.listFiles(new DirectoryFileFilter());
        List<File> dirFileList;
        TreeMap<Double, File> budgetFileMap = new TreeMap<>();
        String innerDirName;
        BasicPair<Double, Integer> tempPair;
        Double tempBudget;
        Integer tempWindowSize;
        for (File innerDir : totalDirFileArray) {
            innerDirName = innerDir.getName();
            tempPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(innerDirName);
            tempBudget = tempPair.getKey();
            tempWindowSize = tempPair.getValue();
            if (tempWindowSize.equals(defaultWindowSize)) {
//                dirFileList.add(innerDir);
                budgetFileMap.put(tempBudget, innerDir);
            }
        }
        dirFileList = new ArrayList<>();
        dirFileList.addAll(budgetFileMap.values());
        File[] dirFileArray = dirFileList.toArray(new File[0]);
        List[] result = getAverageImprovementForBudgetChange(dirFileArray, furtherImproveMethodName, improveMethodName, originalMethodName, whetherLog);
        MyPrint.showList(result[0]);
        MyPrint.showList(result[1]);
        double sum = ListUtils.sum(result[1]);
        System.out.println(sum / result[1].size());
        double sum2 = ListUtils.sum(result[2]);
        MyPrint.showList(result[2]);
        System.out.println(sum2 / result[2].size());
    }

    @Test
    public void testWindowSizeImprove() {
//        String datasetOrderName = "1.trajectory_result";
//        String datasetOrderName = "2.check_in_result";
//        String datasetOrderName = "3.tlns_result";
//        String datasetOrderName = "4.sin_result";
        String datasetOrderName = "5.log_result";
//        String originalMethodName = "BD";
//        String improveMethodName = "PBD";
//        String furtherImproveMethodName = "PDBD";
        String originalMethodName = "BA";
        String improveMethodName = "PBA";
        String furtherImproveMethodName = "PDBA";
        Double defaultEpsilon = 0.6;
//        boolean whetherLog = false;
        boolean whetherLog = true;
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "1.result", datasetOrderName);
        File file = new File(datasetPath);
        File[] totalDirFileArray = file.listFiles(new DirectoryFileFilter());
        List<File> dirFileList;
        TreeMap<Integer, File> windowSizeFileMap = new TreeMap<>();
        String innerDirName;
        BasicPair<Double, Integer> tempPair;
        Double tempBudget;
        Integer tempWindowSize;
        for (File innerDir : totalDirFileArray) {
            innerDirName = innerDir.getName();
            tempPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(innerDirName);
            tempBudget = tempPair.getKey();
            tempWindowSize = tempPair.getValue();
            if (tempBudget.equals(defaultEpsilon)) {
//                dirFileList.add(innerDir);
                windowSizeFileMap.put(tempWindowSize, innerDir);
            }
        }
        dirFileList = new ArrayList<>();
        dirFileList.addAll(windowSizeFileMap.values());
        File[] dirFileArray = dirFileList.toArray(new File[0]);
        List[] result = getAverageImprovementForWindowSizeChange(dirFileArray, furtherImproveMethodName, improveMethodName, originalMethodName, whetherLog);
        MyPrint.showList(result[0]);
        MyPrint.showList(result[1]);
        double sum = ListUtils.sum(result[1]);
        System.out.println(sum / result[1].size());
        double sum2 = ListUtils.sum(result[2]);
        MyPrint.showList(result[2]);
        System.out.println(sum2 / result[2].size());
    }

    public static List<String> getFirstLastTwoRoundValue(List<String> dataListWithFirstTitle, String elementSplit) {
        List<String> result = new ArrayList<>();
        int size = dataListWithFirstTitle.size();
        String[] tempArray = dataListWithFirstTitle.get(0).split(elementSplit);

        result.add(StringUtil.join(elementSplit, tempArray[0], tempArray[tempArray.length - 2], tempArray[tempArray.length - 1]));
        Double mre, mjsd;
        for (int i = 1; i < size; i++) {
            tempArray = dataListWithFirstTitle.get(i).split(elementSplit);
            mre = Double.parseDouble(tempArray[tempArray.length - 2]);
            mjsd = Double.parseDouble(tempArray[tempArray.length - 1]);
            result.add(StringUtil.join(elementSplit, tempArray[0], NumberUtil.roundFormat(mre, 2), NumberUtil.roundFormat(mjsd, 2)));
        }
        return result;
    }

    @Test
    public void showContainingLDPBudgetChangeResult() {
//        String datasetOrderName = "1.trajectory_containing_ldp_result";
//        String datasetOrderName = "2.check_in_containing_ldp_result";
        String datasetOrderName = "3.tlns_containing_ldp_result";
//        String datasetOrderName = "4.sin_containing_ldp_result";
//        String datasetOrderName = "5.log_containing_ldp_result";
        Integer defaultWindowSize = 120;
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "4.result_containing_ldp", datasetOrderName);
        File file = new File(datasetPath);
        File[] totalDirFileArray = file.listFiles(new DirectoryFileFilter());
        List<File> dirFileList;
        TreeMap<Double, File> budgetFileMap = new TreeMap<>();
        String innerDirName;
        BasicPair<Double, Integer> tempPair;
        Double tempBudget;
        Integer tempWindowSize;
        for (File innerDir : totalDirFileArray) {
            innerDirName = innerDir.getName();
            tempPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(innerDirName);
            tempBudget = tempPair.getKey();
            tempWindowSize = tempPair.getValue();
            if (tempWindowSize.equals(defaultWindowSize)) {
                budgetFileMap.put(tempBudget, innerDir);
            }
        }
//        MyPrint.showMap(budgetFileMap);
        for (Map.Entry<Double, File> entry : budgetFileMap.entrySet()) {
            System.out.println("Epsilon: " + entry.getKey());
            String filePath = entry.getValue().getAbsolutePath().concat(File.separator).concat("result.txt");
            List<String> data = ListReadUtils.readAllDataList(filePath, ",");
            data = getFirstLastTwoRoundValue(data, ",");
            MyPrint.showList(data, System.lineSeparator());
            MyPrint.showSplitLine("*", 150);
        }
    }

    @Test
    public void showContainingLDPWindowSizeChangeResult() {
//        String datasetOrderName = "1.trajectory_containing_ldp_result";
//        String datasetOrderName = "2.check_in_containing_ldp_result";
        String datasetOrderName = "3.tlns_containing_ldp_result";
//        String datasetOrderName = "4.sin_containing_ldp_result";
//        String datasetOrderName = "5.log_containing_ldp_result";
        Double defaultEpsilon = 0.6;
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "4.result_containing_ldp", datasetOrderName);
        File file = new File(datasetPath);
        File[] totalDirFileArray = file.listFiles(new DirectoryFileFilter());
        List<File> dirFileList;
        TreeMap<Integer, File> windowSizeFileMap = new TreeMap<>();
        String innerDirName;
        BasicPair<Double, Integer> tempPair;
        Double tempBudget;
        Integer tempWindowSize;
        for (File innerDir : totalDirFileArray) {
            innerDirName = innerDir.getName();
            tempPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(innerDirName);
            tempBudget = tempPair.getKey();
            tempWindowSize = tempPair.getValue();
            if (tempBudget.equals(defaultEpsilon)) {
//                dirFileList.add(innerDir);
                windowSizeFileMap.put(tempWindowSize, innerDir);
            }
        }
//        MyPrint.showMap(budgetFileMap);
        for (Map.Entry<Integer, File> entry : windowSizeFileMap.entrySet()) {
            System.out.println("Window_Size: " + entry.getKey());
            String filePath = entry.getValue().getAbsolutePath().concat(File.separator).concat("result.txt");
            List<String> data = ListReadUtils.readAllDataList(filePath, ",");
            data = getFirstLastTwoRoundValue(data, ",");
            MyPrint.showList(data, System.lineSeparator());
            MyPrint.showSplitLine("*", 150);
        }
    }

    @Test
    public void testContainingLDPBudgetChangeImprove() {
        String datasetOrderName = "1.trajectory_containing_ldp_result";
//        String datasetOrderName = "2.check_in_containing_ldp_result";
//        String datasetOrderName = "3.tlns_containing_ldp_result";
//        String datasetOrderName = "4.sin_containing_ldp_result";
//        String datasetOrderName = "5.log_containing_ldp_result";
        String originalMethodName = "BD";
        String improveMethodName = "PBD";
        String furtherImproveMethodName = "PDBD";
//        String originalMethodName = "BA";
//        String improveMethodName = "PBA";
//        String furtherImproveMethodName = "PDBA";
        Integer defaultWindowSize = 120;
        boolean whetherLog = false;
//        boolean whetherLog = true;
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "4.result_containing_ldp", datasetOrderName);
        File file = new File(datasetPath);
        File[] totalDirFileArray = file.listFiles(new DirectoryFileFilter());
        List<File> dirFileList;
        TreeMap<Double, File> budgetFileMap = new TreeMap<>();
        String innerDirName;
        BasicPair<Double, Integer> tempPair;
        Double tempBudget;
        Integer tempWindowSize;
        for (File innerDir : totalDirFileArray) {
            innerDirName = innerDir.getName();
            tempPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(innerDirName);
            tempBudget = tempPair.getKey();
            tempWindowSize = tempPair.getValue();
            if (tempWindowSize.equals(defaultWindowSize)) {
                budgetFileMap.put(tempBudget, innerDir);
            }
        }
        dirFileList = new ArrayList<>();
        dirFileList.addAll(budgetFileMap.values());
        File[] dirFileArray = dirFileList.toArray(new File[0]);
        List[] result = getAverageImprovementForBudgetChange(dirFileArray, furtherImproveMethodName, improveMethodName, originalMethodName, whetherLog);
        MyPrint.showList(result[0]);
        MyPrint.showList(result[1]);
        double sum = ListUtils.sum(result[1]);
        System.out.println(sum / result[1].size());
        double sum2 = ListUtils.sum(result[2]);
        MyPrint.showList(result[2]);
        System.out.println(sum2 / result[2].size());
    }

    @Test
    public void testContainingLDPWindowSizeImprove() {
//        String datasetOrderName = "1.trajectory_containing_ldp_result";
//        String datasetOrderName = "2.check_in_containing_ldp_result";
//        String datasetOrderName = "3.tlns_containing_ldp_result";
//        String datasetOrderName = "4.sin_containing_ldp_result";
        String datasetOrderName = "5.log_containing_ldp_result";
//        String originalMethodName = "BD";
//        String improveMethodName = "PBD";
//        String furtherImproveMethodName = "PDBD";
        String originalMethodName = "BA";
        String improveMethodName = "PBA";
        String furtherImproveMethodName = "PDBA";
        Double defaultEpsilon = 0.6;
//        boolean whetherLog = false;
        boolean whetherLog = true;
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "4.result_containing_ldp", datasetOrderName);
        File file = new File(datasetPath);
        File[] totalDirFileArray = file.listFiles(new DirectoryFileFilter());
        List<File> dirFileList;
        TreeMap<Integer, File> windowSizeFileMap = new TreeMap<>();
        String innerDirName;
        BasicPair<Double, Integer> tempPair;
        Double tempBudget;
        Integer tempWindowSize;
        for (File innerDir : totalDirFileArray) {
            innerDirName = innerDir.getName();
            tempPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(innerDirName);
            tempBudget = tempPair.getKey();
            tempWindowSize = tempPair.getValue();
            if (tempBudget.equals(defaultEpsilon)) {
//                dirFileList.add(innerDir);
                windowSizeFileMap.put(tempWindowSize, innerDir);
            }
        }
        dirFileList = new ArrayList<>();
        dirFileList.addAll(windowSizeFileMap.values());
        File[] dirFileArray = dirFileList.toArray(new File[0]);
        List[] result = getAverageImprovementForWindowSizeChange(dirFileArray, furtherImproveMethodName, improveMethodName, originalMethodName, whetherLog);
        MyPrint.showList(result[0]);
        MyPrint.showList(result[1]);
        double sum = ListUtils.sum(result[1]);
        System.out.println(sum / result[1].size());
        double sum2 = ListUtils.sum(result[2]);
        MyPrint.showList(result[2]);
        System.out.println(sum2 / result[2].size());
    }

    @Test
    public void testContainingTotalBudgetChangeImprove() {
//        String datasetOrderName = "1.trajectory_containing_total_result";
//        String datasetOrderName = "2.check_in_containing_total_result";
//        String datasetOrderName = "3.tlns_containing_total_result";
//        String datasetOrderName = "4.sin_containing_total_result";
        String datasetOrderName = "5.log_containing_total_result";
//        String originalMethodName = "BD";
//        String improveMethodName = "PBD";
//        String furtherImproveMethodName = "PDBD";
        String originalMethodName = "BA";
        String improveMethodName = "PBA";
        String furtherImproveMethodName = "PDBA";
        Integer defaultWindowSize = 120;
        boolean whetherLog = false;
//        boolean whetherLog = true;
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "..", "3-3_dynamic_stream_pdp", "7.result_containing_total", datasetOrderName);
        File file = new File(datasetPath);
        File[] totalDirFileArray = file.listFiles(new DirectoryFileFilter());
        List<File> dirFileList;
        TreeMap<Double, File> budgetFileMap = new TreeMap<>();
        String innerDirName;
        BasicPair<Double, Integer> tempPair;
        Double tempBudget;
        Integer tempWindowSize;
        for (File innerDir : totalDirFileArray) {
            innerDirName = innerDir.getName();
            tempPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(innerDirName);
            tempBudget = tempPair.getKey();
            tempWindowSize = tempPair.getValue();
            if (tempWindowSize.equals(defaultWindowSize)) {
                budgetFileMap.put(tempBudget, innerDir);
            }
        }
        dirFileList = new ArrayList<>();
        dirFileList.addAll(budgetFileMap.values());
        File[] dirFileArray = dirFileList.toArray(new File[0]);
        List[] result = getAverageImprovementForBudgetChange(dirFileArray, furtherImproveMethodName, improveMethodName, originalMethodName, whetherLog);
        MyPrint.showList(result[0]);
        MyPrint.showList(result[1]);
        double sum = ListUtils.sum(result[1]);
        System.out.println(sum / result[1].size());
        double sum2 = ListUtils.sum(result[2]);
        MyPrint.showList(result[2]);
        System.out.println(sum2 / result[2].size());
    }

    @Test
    public void testContainingTotalWindowSizeImprove() {
//        String datasetOrderName = "1.trajectory_containing_total_result";
//        String datasetOrderName = "2.check_in_containing_total_result";
//        String datasetOrderName = "3.tlns_containing_total_result";
//        String datasetOrderName = "4.sin_containing_total_result";
        String datasetOrderName = "5.log_containing_total_result";
//        String originalMethodName = "BD";
//        String improveMethodName = "PBD";
//        String furtherImproveMethodName = "PDBD";
        String originalMethodName = "BA";
        String improveMethodName = "PBA";
        String furtherImproveMethodName = "PDBA";
        Double defaultEpsilon = 0.6;
        boolean whetherLog = false;
//        boolean whetherLog = true;
        String datasetPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "..", "3-3_dynamic_stream_pdp", "7.result_containing_total", datasetOrderName);
        File file = new File(datasetPath);
        File[] totalDirFileArray = file.listFiles(new DirectoryFileFilter());
        List<File> dirFileList;
        TreeMap<Integer, File> windowSizeFileMap = new TreeMap<>();
        String innerDirName;
        BasicPair<Double, Integer> tempPair;
        Double tempBudget;
        Integer tempWindowSize;
        for (File innerDir : totalDirFileArray) {
            innerDirName = innerDir.getName();
            tempPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(innerDirName);
            tempBudget = tempPair.getKey();
            tempWindowSize = tempPair.getValue();
            if (tempBudget.equals(defaultEpsilon)) {
//                dirFileList.add(innerDir);
                windowSizeFileMap.put(tempWindowSize, innerDir);
            }
        }
        dirFileList = new ArrayList<>();
        dirFileList.addAll(windowSizeFileMap.values());
        File[] dirFileArray = dirFileList.toArray(new File[0]);
        List[] result = getAverageImprovementForWindowSizeChange(dirFileArray, furtherImproveMethodName, improveMethodName, originalMethodName, whetherLog);
        MyPrint.showList(result[0]);
        MyPrint.showList(result[1]);
        double sum = ListUtils.sum(result[1]);
        System.out.println(sum / result[1].size());
        double sum2 = ListUtils.sum(result[2]);
        MyPrint.showList(result[2]);
        System.out.println(sum2 / result[2].size());
    }


}
