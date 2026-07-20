package ecnu.dll.run.c_dataset_run.utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.execute.CopyUtils;
import cn.edu.dll.filter.file_filter.DirectoryFileFilter;
import cn.edu.dll.filter.file_filter.TxtFilter;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.io.write.CSVWrite;
import cn.edu.dll.signal.CatchSignal;
import cn.edu.dll.struct.bean_structs.BeanInterface;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import ecnu.dll.utils.run.ParameterUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class PostProcessUtils {
    public static void combineResult(String dirPath) {
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles(new TxtFilter());
        List<List<ResultPartBean>> dataList = new ArrayList<>();
        List<ResultPartBean> tempList;
        BeanInterface<ResultPartBean> beanBeanInterface = new ResultPartBean();
        String title = CSVReadEnhanced.readDataTitle(files[0].getAbsolutePath());
        List<String> dataStringList;
        for (File file : files) {
            // here
            dataStringList = CSVReadEnhanced.readDataLinesWithoutTitle(file.getAbsolutePath());
            if (dataStringList.isEmpty()) {
                continue;
            }
            tempList = new ArrayList<>();
            for (String str : dataStringList) {
                tempList.add(ResultPartBean.toBean(str));
            }
            dataList.add(tempList);
        }
        List<ResultPartBean> resultList = new ArrayList<>();
        ResultPartBean resultPartBean, tempBean;
        int innerSize = dataList.get(0).size();
        for (int i = 0; i < innerSize; i++) {
            resultPartBean = ResultPartBean.getInitializedBean(dataList.get(0).get(i));
            for (List<ResultPartBean> innerList : dataList) {
                tempBean = innerList.get(i);
                resultPartBean.setBatchSize(resultPartBean.getBatchSize()+ tempBean.getBatchSize());
                resultPartBean.setbRE(resultPartBean.getbRE() + tempBean.getbRE());
                resultPartBean.setTimeCost(resultPartBean.getTimeCost() + tempBean.getTimeCost());
                resultPartBean.setbJSD(resultPartBean.getbJSD() + tempBean.getbJSD());
                resultPartBean.setbWD(resultPartBean.getbWD() + tempBean.getbWD());
            }
            resultPartBean.setmRE(resultPartBean.getbRE() / resultPartBean.getBatchSize());
            resultPartBean.setmJSD(resultPartBean.getbJSD() / resultPartBean.getBatchSize());
            resultPartBean.setmWD(resultPartBean.getbWD() / resultPartBean.getBatchSize());
            resultList.add(resultPartBean);
        }
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, dirPath, "combine", "combine.txt");
        BasicWrite basicWrite = new BasicWrite(",");
        basicWrite.startWriting(outputPath);
        basicWrite.writeOneLine(title + ",MRE" + ",MJSD" + ",MWD");
        for (ResultPartBean bean : resultList) {
            basicWrite.writeOneLine(bean.toCSVString());
        }
        basicWrite.endWriting();
    }
    public static void combineResultErrorDetails(String dirPath) {
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles(new TxtFilter());
        List<List<ResultPartBeanErrorDetails>> dataList = new ArrayList<>();
        List<ResultPartBeanErrorDetails> tempList;
        BeanInterface<ResultPartBean> beanBeanInterface = new ResultPartBeanErrorDetails();
        String title = CSVReadEnhanced.readDataTitle(files[0].getAbsolutePath());
        List<String> dataStringList;
        for (File file : files) {
            // here
            dataStringList = CSVReadEnhanced.readDataLinesWithoutTitle(file.getAbsolutePath());
            if (dataStringList.isEmpty()) {
                continue;
            }
            tempList = new ArrayList<>();
            for (String str : dataStringList) {
                tempList.add(ResultPartBeanErrorDetails.toBean(str));
            }
            dataList.add(tempList);
        }
        List<ResultPartBeanErrorDetails> resultList = new ArrayList<>();
        ResultPartBeanErrorDetails resultPartBeanErrorDetails, tempBean;
        int innerSize = dataList.get(0).size();
        for (int i = 0; i < innerSize; i++) {
            resultPartBeanErrorDetails = ResultPartBeanErrorDetails.getInitializedBean(dataList.get(0).get(i));
            for (List<ResultPartBeanErrorDetails> innerList : dataList) {
                tempBean = innerList.get(i);
                resultPartBeanErrorDetails.setBatchSize(resultPartBeanErrorDetails.getBatchSize()+ tempBean.getBatchSize());
                resultPartBeanErrorDetails.setTimeCost(resultPartBeanErrorDetails.getTimeCost() + tempBean.getTimeCost());
                resultPartBeanErrorDetails.setbRE(resultPartBeanErrorDetails.getbRE() + tempBean.getbRE());
                resultPartBeanErrorDetails.setbJSD(resultPartBeanErrorDetails.getbJSD() + tempBean.getbJSD());
                resultPartBeanErrorDetails.setbWD(resultPartBeanErrorDetails.getbWD() + tempBean.getbWD());
                resultPartBeanErrorDetails.setPartA_BDPVar(resultPartBeanErrorDetails.getPartA_BDPVar() + tempBean.getPartA_BDPVar());
                resultPartBeanErrorDetails.setPartB_BDPVar(resultPartBeanErrorDetails.getPartB_BDPVar() + tempBean.getPartB_BDPVar());
                resultPartBeanErrorDetails.setPartA_BSampleVar(resultPartBeanErrorDetails.getPartA_BSampleVar() + tempBean.getPartA_BSampleVar());
                resultPartBeanErrorDetails.setPartB_BSampleVar(resultPartBeanErrorDetails.getPartB_BSampleVar() + tempBean.getPartB_BSampleVar());
                resultPartBeanErrorDetails.setPartA_BCountVar(resultPartBeanErrorDetails.getPartA_BCountVar() + tempBean.getPartA_BCountVar());
                resultPartBeanErrorDetails.setPartB_BCountVar(resultPartBeanErrorDetails.getPartB_BCountVar() + tempBean.getPartB_BCountVar());
                resultPartBeanErrorDetails.setPartA_BiasSquare(resultPartBeanErrorDetails.getPartA_BiasSquare() + tempBean.getPartA_BiasSquare());
                resultPartBeanErrorDetails.setPartB_BiasSquare(resultPartBeanErrorDetails.getPartB_BiasSquare() + tempBean.getPartB_BiasSquare());
                resultPartBeanErrorDetails.setNonNullCount(resultPartBeanErrorDetails.getNonNullCount() + tempBean.getNonNullCount());

            }
            resultPartBeanErrorDetails.setmRE(resultPartBeanErrorDetails.getbRE() / resultPartBeanErrorDetails.getBatchSize());
            resultPartBeanErrorDetails.setmJSD(resultPartBeanErrorDetails.getbJSD() / resultPartBeanErrorDetails.getBatchSize());
            resultPartBeanErrorDetails.setmWD(resultPartBeanErrorDetails.getbWD() / resultPartBeanErrorDetails.getBatchSize());
            resultPartBeanErrorDetails.setPartA_MDPVar(resultPartBeanErrorDetails.getPartA_BDPVar() / resultPartBeanErrorDetails.getBatchSize());
            resultPartBeanErrorDetails.setPartB_MDPVar(resultPartBeanErrorDetails.getPartB_BDPVar() / resultPartBeanErrorDetails.getNonNullCount());
            resultPartBeanErrorDetails.setPartA_MSampleVar(resultPartBeanErrorDetails.getPartA_BSampleVar() / resultPartBeanErrorDetails.getBatchSize());
            resultPartBeanErrorDetails.setPartB_MSampleVar(resultPartBeanErrorDetails.getPartB_BSampleVar() / resultPartBeanErrorDetails.getNonNullCount());
            resultPartBeanErrorDetails.setPartA_MCountVar(resultPartBeanErrorDetails.getPartA_BCountVar() / resultPartBeanErrorDetails.getBatchSize());
            resultPartBeanErrorDetails.setPartB_MCountVar(resultPartBeanErrorDetails.getPartB_BCountVar() / resultPartBeanErrorDetails.getNonNullCount());
            resultPartBeanErrorDetails.setPartA_MBiasSquare(resultPartBeanErrorDetails.getPartA_BiasSquare() / resultPartBeanErrorDetails.getBatchSize());
            resultPartBeanErrorDetails.setPartB_MBiasSquare(resultPartBeanErrorDetails.getPartB_BiasSquare() / resultPartBeanErrorDetails.getNonNullCount());
            resultPartBeanErrorDetails.setNonNullMCount(resultPartBeanErrorDetails.getNonNullCount().doubleValue() / resultPartBeanErrorDetails.getBatchSize());
            resultList.add(resultPartBeanErrorDetails);
        }
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, dirPath, "combine", "combine.txt");
        BasicWrite basicWrite = new BasicWrite(",");
        basicWrite.startWriting(outputPath);
        basicWrite.writeOneLine(title + ",MRE" + ",MJSD" + ",MWD"
                + ",PartA_MDPVar" + ",PartB_MDPVar" + ",PartA_MSampleVar" + ",PartB_MSampleVar"
                + ",PartA_MCountVar" + ",PartB_MCountVar" + ",PartA_MBiasSquare" + ",PartB_MBiasSquare"
                + ",NonNullMCount");
        for (ResultPartBeanErrorDetails bean : resultList) {
            basicWrite.writeOneLine(bean.toCSVString());
        }
        basicWrite.endWriting();
    }

    public static void combineAndExtractCombineResult(String inputDir, String outputDir) {
        File dirInputFile = new File(inputDir);
        File dirOutputFile = new File(outputDir);
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        if (!dirOutputFile.exists()) {
            dirOutputFile.mkdirs();
        }
        File tempParametersChangeOutputFile, tempOutputFile, tempInputFile;
        String tempSegmentOutputName;
        File[] dirFiles = dirInputFile.listFiles(directoryFileFilter);
        File[] segmentInputDirs;
        for (File parametersChangeInputFile : dirFiles) {
            tempParametersChangeOutputFile = new File(dirOutputFile, parametersChangeInputFile.getName());
            if (!tempParametersChangeOutputFile.exists()) {
                tempParametersChangeOutputFile.mkdirs();
            }
            segmentInputDirs = parametersChangeInputFile.listFiles(directoryFileFilter);
            for (File segmentInputDir : segmentInputDirs) {
                // here
                combineResult(segmentInputDir.getAbsolutePath());
                tempSegmentOutputName = segmentInputDir.getName();
                tempOutputFile = new File(tempParametersChangeOutputFile, tempSegmentOutputName+".txt");
                tempInputFile = new File(segmentInputDir, StringUtil.join(ConstantValues.FILE_SPLIT, "combine", "combine.txt"));
                CopyUtils.fileCopyWithTransferTo(tempInputFile.getAbsolutePath(), tempOutputFile.getAbsolutePath());
            }
        }
    }
    public static void combineAndExtractCombineResultErrorDetails(String inputDir, String outputDir) {
        File dirInputFile = new File(inputDir);
        File dirOutputFile = new File(outputDir);
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        if (!dirOutputFile.exists()) {
            dirOutputFile.mkdirs();
        }
        File tempParametersChangeOutputFile, tempOutputFile, tempInputFile;
        String tempSegmentOutputName;
        File[] dirFiles = dirInputFile.listFiles(directoryFileFilter);
        File[] segmentInputDirs;
        for (File parametersChangeInputFile : dirFiles) {
            tempParametersChangeOutputFile = new File(dirOutputFile, parametersChangeInputFile.getName());
            if (!tempParametersChangeOutputFile.exists()) {
                tempParametersChangeOutputFile.mkdirs();
            }
            segmentInputDirs = parametersChangeInputFile.listFiles(directoryFileFilter);
            for (File segmentInputDir : segmentInputDirs) {
                // here
                combineResultErrorDetails(segmentInputDir.getAbsolutePath());
                tempSegmentOutputName = segmentInputDir.getName();
                tempOutputFile = new File(tempParametersChangeOutputFile, tempSegmentOutputName+".txt");
                tempInputFile = new File(segmentInputDir, StringUtil.join(ConstantValues.FILE_SPLIT, "combine", "combine.txt"));
                CopyUtils.fileCopyWithTransferTo(tempInputFile.getAbsolutePath(), tempOutputFile.getAbsolutePath());
            }
        }
    }

    private static void furtherCombineFiles(File tempOutputFile, File[] segmentInputFiles) {
        File segmentInputFile;
        BeanInterface<ResultBean> beanBeanInterface = new ResultBean();
        List<List<ResultBean>> dataList = new ArrayList<>();
        List<ResultBean> tempBeanList, resultList;
        String title = CSVReadEnhanced.readDataTitle(segmentInputFiles[0].getAbsolutePath());
        for (int i = 0; i < segmentInputFiles.length; i++) {
            segmentInputFile = segmentInputFiles[i];
            tempBeanList = CSVReadEnhanced.readDataToBeanList(segmentInputFile.getAbsolutePath(), beanBeanInterface);
            dataList.add(tempBeanList);
        }
        ResultBean resultBean, tempBean;
        int innerSize = dataList.get(0).size();
        resultList = new ArrayList<>();
        // 从输出文件路径中提取 positionSize（仅当路径包含 _d_ 标识时）
//        Integer positionSize = ParameterUtils.extractBudgetWindowSizeDimensionParametersAccordingFileDirName(tempOutputFile.getParentFile().getName()).getTag();
//        boolean isDimensionAblation = positionSize != null && positionSize > 0;
        String parentDirName = tempOutputFile.getParentFile().getName();
        Integer positionSize = null;
        boolean isDimensionAblation = false;

        if (parentDirName.startsWith("u_")) {
            positionSize = ParameterUtils.extractUserRatioAndOtherParametersAccordingFileDirName(parentDirName).getTag();
            isDimensionAblation = positionSize != null && positionSize > 0;
        } else if (parentDirName.startsWith("p_")) {
            positionSize = ParameterUtils.extractBudgetWindowSizeDimensionParametersAccordingFileDirName(parentDirName).getTag();
            isDimensionAblation = positionSize != null && positionSize > 0;
        }

        for (int i = 0; i < innerSize; i++) {
            resultBean = ResultBean.getInitializedBean(dataList.get(0).get(i));
            for (List<ResultBean> innerList : dataList) {
                tempBean = innerList.get(i);
                resultBean.setBatchSize(resultBean.getBatchSize()+ tempBean.getBatchSize());
                resultBean.setTimeCost(resultBean.getTimeCost() + tempBean.getTimeCost());
                resultBean.setBre(resultBean.getBre() + tempBean.getBre());
                resultBean.setMre(resultBean.getMre() + tempBean.getMre()*tempBean.getBatchSize());
                resultBean.setBjsd(resultBean.getBjsd() + tempBean.getBjsd());
                resultBean.setMjsd(resultBean.getMjsd() + tempBean.getMjsd() * tempBean.getBatchSize());
                resultBean.setBwd(resultBean.getBwd() + tempBean.getBwd());
                resultBean.setMwd(resultBean.getMwd() + tempBean.getMwd() * tempBean.getBatchSize());
            }
            resultBean.setMre(resultBean.getMre() / resultBean.getBatchSize());
            resultBean.setMjsd(resultBean.getMjsd() / resultBean.getBatchSize());
            resultBean.setMwd(resultBean.getMwd() / resultBean.getBatchSize());
            resultList.add(resultBean);
        }
        String outputPath = tempOutputFile.getAbsolutePath();
        CSVWrite csvWrite = new CSVWrite();
        csvWrite.startWriting(outputPath);
        // 根据是否为维度消融实验决定是否添加 PositionSize 列
        if (isDimensionAblation) {
            csvWrite.writeOneLine(title + ",PositionSize");
            for (ResultBean bean : resultList) {
                csvWrite.writeOneLine(bean.toFormatString() + "," + positionSize);
            }
        } else {
            csvWrite.writeOneLine(title);
            csvWrite.writeBeanList(resultList);
        }
        csvWrite.endWriting();
    }
    private static void furtherCombineFilesErrorDetails(File tempOutputFile, File[] segmentInputFiles) {
        File segmentInputFile;
        BeanInterface<ResultBean> beanBeanInterface = new ResultBean();
        List<List<ResultBean>> dataList = new ArrayList<>();
        List<ResultBean> tempBeanList, resultList;
        String title = CSVReadEnhanced.readDataTitle(segmentInputFiles[0].getAbsolutePath());
        for (int i = 0; i < segmentInputFiles.length; i++) {
            segmentInputFile = segmentInputFiles[i];
            tempBeanList = CSVReadEnhanced.readDataToBeanList(segmentInputFile.getAbsolutePath(), beanBeanInterface);
            dataList.add(tempBeanList);
        }
        ResultBean resultBean, tempBean;
        int innerSize = dataList.get(0).size();
        resultList = new ArrayList<>();
        // 从输出文件路径中提取 positionSize（仅当路径包含 _d_ 标识时）
//        Integer positionSize = ParameterUtils.extractBudgetWindowSizeDimensionParametersAccordingFileDirName(tempOutputFile.getParentFile().getName()).getTag();
//        boolean isDimensionAblation = positionSize != null && positionSize > 0;
        String parentDirName = tempOutputFile.getParentFile().getName();
        Integer positionSize = null;
        boolean isDimensionAblation = false;

        if (parentDirName.startsWith("u_")) {
            positionSize = ParameterUtils.extractUserRatioAndOtherParametersAccordingFileDirName(parentDirName).getTag();
            isDimensionAblation = positionSize != null && positionSize > 0;
        } else if (parentDirName.startsWith("p_")) {
            positionSize = ParameterUtils.extractBudgetWindowSizeDimensionParametersAccordingFileDirName(parentDirName).getTag();
            isDimensionAblation = positionSize != null && positionSize > 0;
        }

        for (int i = 0; i < innerSize; i++) {
            resultBean = ResultBean.getInitializedBeanContainingErrorDetails(dataList.get(0).get(i));
            for (List<ResultBean> innerList : dataList) {
                tempBean = innerList.get(i);
                resultBean.setBatchSize(resultBean.getBatchSize()+ tempBean.getBatchSize());
                resultBean.setTimeCost(resultBean.getTimeCost() + tempBean.getTimeCost());
                resultBean.setBre(resultBean.getBre() + tempBean.getBre());
                resultBean.setBjsd(resultBean.getBjsd() + tempBean.getBjsd());
                resultBean.setBwd(resultBean.getBwd() + tempBean.getBwd());

                resultBean.setPartA_BDPVar(resultBean.getPartA_BDPVar() + tempBean.getPartA_BDPVar());
                resultBean.setPartB_BDPVar(resultBean.getPartB_BDPVar() + tempBean.getPartB_BDPVar());
                resultBean.setPartA_BSampleVar(resultBean.getPartA_BSampleVar() + tempBean.getPartA_BSampleVar());
                resultBean.setPartB_BSampleVar(resultBean.getPartB_BSampleVar() + tempBean.getPartB_BSampleVar());
                resultBean.setPartA_BCountVar(resultBean.getPartA_BCountVar() + tempBean.getPartA_BCountVar());
                resultBean.setPartB_BCountVar(resultBean.getPartB_BCountVar() + tempBean.getPartB_BCountVar());
                resultBean.setPartA_BiasSquare(resultBean.getPartA_BiasSquare() + tempBean.getPartA_BiasSquare());
                resultBean.setPartB_BiasSquare(resultBean.getPartB_BiasSquare() + tempBean.getPartB_BiasSquare());
                resultBean.setNonNullCount(resultBean.getNonNullCount() + tempBean.getNonNullCount());

            }
            resultBean.setMre(resultBean.getBre() / resultBean.getBatchSize());
            resultBean.setMjsd(resultBean.getBjsd() / resultBean.getBatchSize());
            resultBean.setMwd(resultBean.getBwd() / resultBean.getBatchSize());
            resultBean.setPartA_MDPVar(resultBean.getPartA_BDPVar() / resultBean.getBatchSize());
            resultBean.setPartB_MDPVar(resultBean.getPartB_BDPVar() / resultBean.getNonNullCount());
            resultBean.setPartA_MSampleVar(resultBean.getPartA_BSampleVar() / resultBean.getBatchSize());
            resultBean.setPartB_MSampleVar(resultBean.getPartB_BSampleVar() / resultBean.getNonNullCount());
            resultBean.setPartA_MCountVar(resultBean.getPartA_BCountVar() / resultBean.getBatchSize());
            resultBean.setPartB_MCountVar(resultBean.getPartB_BCountVar() / resultBean.getNonNullCount());
            resultBean.setPartA_MBiasSquare(resultBean.getPartA_BiasSquare() / resultBean.getBatchSize());
            resultBean.setPartB_MBiasSquare(resultBean.getPartB_BiasSquare() / resultBean.getNonNullCount());
            resultBean.setNonNullMCount(resultBean.getNonNullCount().doubleValue() / resultBean.getBatchSize());
            resultList.add(resultBean);
        }
        String outputPath = tempOutputFile.getAbsolutePath();
        CSVWrite csvWrite = new CSVWrite();
        csvWrite.startWriting(outputPath);
        // 根据是否为维度消融实验决定是否添加 PositionSize 列
        if (isDimensionAblation) {
            csvWrite.writeOneLine(title + ",PositionSize");
            for (ResultBean bean : resultList) {
                csvWrite.writeOneLine(bean.toFormatErrorDetailsString() + "," + positionSize);
            }
        } else {
            csvWrite.writeOneLine(title);
            for (ResultBean bean : resultList) {
                csvWrite.writeOneLine(bean.toFormatErrorDetailsString());
            }
        }
        csvWrite.endWriting();
    }

    public static void furtherCombine(String inputDir, String outputDir) {
        File dirInputFile = new File(inputDir);
        File dirOutputFile = new File(outputDir);
        File dirInnerOutputFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        FileFilter txtFilter = new TxtFilter();

        if (!dirOutputFile.exists()) {
            dirOutputFile.mkdirs();
        }


        File tempParametersChangeOutputFile, tempOutputFile, tempInputFile;
        File[] datasetDirFile = dirInputFile.listFiles(directoryFileFilter);
        File[] segmentInputFiles;
        for (File datasetFile : datasetDirFile) {
            dirInnerOutputFile = new File(dirOutputFile, datasetFile.getName());
            if (!dirInnerOutputFile.exists()) {
                dirInnerOutputFile.mkdirs();
            }
//            dirFiles = datasetFile.listFiles(directoryFileFilter);
            segmentInputFiles = datasetFile.listFiles(txtFilter);
            tempOutputFile = new File(dirInnerOutputFile, "result.txt");
            furtherCombineFiles(tempOutputFile, segmentInputFiles);
        }

    }
    public static void furtherCombineErrorDetails(String inputDir, String outputDir) {
        File dirInputFile = new File(inputDir);
        File dirOutputFile = new File(outputDir);
        File dirInnerOutputFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        FileFilter txtFilter = new TxtFilter();

        if (!dirOutputFile.exists()) {
            dirOutputFile.mkdirs();
        }


        File tempParametersChangeOutputFile, tempOutputFile, tempInputFile;
        File[] datasetDirFile = dirInputFile.listFiles(directoryFileFilter);
        File[] segmentInputFiles;
        for (File datasetFile : datasetDirFile) {
            dirInnerOutputFile = new File(dirOutputFile, datasetFile.getName());
            if (!dirInnerOutputFile.exists()) {
                dirInnerOutputFile.mkdirs();
            }
//            dirFiles = datasetFile.listFiles(directoryFileFilter);
            segmentInputFiles = datasetFile.listFiles(txtFilter);
            tempOutputFile = new File(dirInnerOutputFile, "result.txt");
            furtherCombineFilesErrorDetails(tempOutputFile, segmentInputFiles);
        }

    }






    public static void main1(String[] args) {
//        String inputPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "test");
//        combineResultBefore(inputPath);
    }

//    public static void main0(String[] args) {
//        CatchSignal catchSignal = new CatchSignal();
//        catchSignal.startCatch();
////        String inputDirPath = StringUtil.join(combineDir);
////        String inputDirPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "output");
//        String inputDirPath = args[0];
//        System.out.println(inputDirPath);
//        File dirFile = new File(inputDirPath);
//        File[] files = dirFile.listFiles();
//        for (File file : files) {
//            if (!file.isDirectory()) {
//                continue;
//            }
//            combineResult(file.getAbsolutePath());
//        }
//    }

    public static void main(String[] args) {
        String inputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "result");
        String outputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "..", "result_combine");
//        combineResult(inputDir);
//        furtherCombine(inputDir, outputDir);
    }
}
