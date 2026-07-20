package ecnu.dll.utils.run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.filter.file_filter.DirectoryFileFilter;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.write.CSVWrite;
import cn.edu.dll.struct.bean_structs.BeanInterface;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.pair.PureTriple;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import ecnu.dll.run.c_dataset_run.utils.ResultBean;
import ecnu.dll.utils.filters.RoundDirectoryFilter;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.util.*;

public class RepeatUtils {

    private static final String[] nameStringArray = new String[]{
            "NP", "BD", "BA", "PBD", "PBA"
            , "PDBD", "PDBA"
            , "PLBU"
    };
    private static final String[] nameStringArrayOnlyForTimeCost = new String[]{
            "NP", "BD", "BA", "PBD", "PBA"
            , "PDBD", "PDBA"
    };
    private static final String[] nameStringArrayOnlyForDimensionAblation = new String[]{
            "NP", "BD", "BA", "PBD", "PBA"
            , "PDBD", "PDBA"
    };

    private static final String[] nameStringArrayOnlyForSPAS = new String[]{
            "NP", "BD", "BA", "PBD", "PBA"
            , "PDBD", "PDBA"
            , "SPAS"
    };
    private static final String[] nameStringArrayOnlyForErrorDetails = new String[]{
            "NP", "BA", "PBA"
    };

    private static final String[] nameStringArrayForInternal = new String[]{
            "NP", "BD", "BA", "PBD", "PBA"
    };
    private static final String[] nameStringArrayTotal = new String[]{
            "NP", "BD", "BA", "PBD", "PBA"
            , "PDBD", "PDBA"
            , "PLBU"
            , "SPAS"
    };

    /**
     * 将每轮最终结果合并取平均值
     * @param outputMethodDirFile
     * @param inputMethodDirFileList
     * @param parameterSet
     */
    private static void combineMainProcess(File outputMethodDirFile, List<File> inputMethodDirFileList, Set<String> parameterSet) {
        List<ResultBean> combineBeanList = null, updateBeanList;
        ResultBean tempBean;
        BeanInterface<ResultBean> modelBean = new ResultBean();
        BasicPair<Double, Integer> paramsPair;
        String inputFilePath, outputFilePath, title;
        CSVWrite csvWrite = new CSVWrite();
        File parentFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        for (String parameterFileDir : parameterSet) {
            paramsPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(parameterFileDir);
            title = CSVReadEnhanced.readDataTitle(inputMethodDirFileList.get(0).listFiles(directoryFileFilter)[0].getAbsolutePath()+ConstantValues.FILE_SPLIT+"result.txt");
//            System.out.println(title);
            combineBeanList = new ArrayList<>();
            for (String beanName : nameStringArray) {
                tempBean = ResultBean.getInitializedBean(beanName, paramsPair.getKey(), paramsPair.getValue());
                combineBeanList.add(tempBean);
            }
            for (File inputMethodDir : inputMethodDirFileList) {
                inputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, inputMethodDir, parameterFileDir, "result.txt");
                updateBeanList = CSVReadEnhanced.readDataToBeanList(inputFilePath, modelBean);
                update(combineBeanList, updateBeanList);
            }
            average(combineBeanList, inputMethodDirFileList.size());
            parentFile = new File(outputMethodDirFile, parameterFileDir);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, parentFile.getAbsolutePath(), "result.txt");
            csvWrite.startWriting(outputFilePath);
            csvWrite.writeOneLine(title);
            csvWrite.writeBeanList(combineBeanList);
            csvWrite.endWriting();
        }

    }
    private static void combineInternalProcess(File outputMethodDirFile, List<File> inputMethodDirFileList, Set<String> parameterSet) {
        List<ResultBean> combineBeanList = null, updateBeanList;
        ResultBean tempBean;
        BeanInterface<ResultBean> modelBean = new ResultBean();
        BasicPair<Double, Integer> paramsPair;
        String inputFilePath, outputFilePath, title;
        CSVWrite csvWrite = new CSVWrite();
        File parentFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        Double[] twoFixedPrivacyBudget = ConfigureUtils.getTwoFixedPrivacyBudget();
        Integer[] twoFixedWindowSize = ConfigureUtils.getTwoFixedWindowSize();
        for (String parameterFileDir : parameterSet) {
            title = CSVReadEnhanced.readDataTitle(inputMethodDirFileList.get(0).listFiles(directoryFileFilter)[0].getAbsolutePath()+ConstantValues.FILE_SPLIT+"result.txt");
            combineBeanList = new ArrayList<>();
            for (String beanName : nameStringArrayForInternal) {
                tempBean = ResultBean.getInitializedBean(beanName, twoFixedPrivacyBudget[1], twoFixedWindowSize[1]);
                combineBeanList.add(tempBean);
            }
            for (File inputMethodDir : inputMethodDirFileList) {
                inputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, inputMethodDir, parameterFileDir, "result.txt");
                updateBeanList = CSVReadEnhanced.readDataToBeanList(inputFilePath, modelBean);
                update(combineBeanList, updateBeanList);
            }
            average(combineBeanList, inputMethodDirFileList.size());
            parentFile = new File(outputMethodDirFile, parameterFileDir);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, parentFile.getAbsolutePath(), "result.txt");
            csvWrite.startWriting(outputFilePath);
            csvWrite.writeOneLine(title);
            csvWrite.writeBeanList(combineBeanList);
            csvWrite.endWriting();
        }

    }

    private static void combineSerialProcess(File outputMethodDirFile, List<File> inputMethodDirFileList, Set<String> parameterSet) {
        // todo: 还未修改成关于串行的运行结果的合并(目前和combineMainProcess除了nameStringArrayOnlyForTimeCost外完全相同)
        List<ResultBean> combineBeanList = null, updateBeanList;
        ResultBean tempBean;
        BeanInterface<ResultBean> modelBean = new ResultBean();
        BasicPair<Double, Integer> paramsPair;
        String inputFilePath, outputFilePath, title;
        CSVWrite csvWrite = new CSVWrite();
        File parentFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        for (String parameterFileDir : parameterSet) {
            paramsPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(parameterFileDir);
            title = CSVReadEnhanced.readDataTitle(inputMethodDirFileList.get(0).listFiles(directoryFileFilter)[0].getAbsolutePath()+ConstantValues.FILE_SPLIT+"result.txt");
//            System.out.println(title);
            combineBeanList = new ArrayList<>();
            for (String beanName : nameStringArrayOnlyForTimeCost) {
                tempBean = ResultBean.getInitializedBean(beanName, paramsPair.getKey(), paramsPair.getValue());
                combineBeanList.add(tempBean);
            }
            for (File inputMethodDir : inputMethodDirFileList) {
                inputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, inputMethodDir, parameterFileDir, "result.txt");
                updateBeanList = CSVReadEnhanced.readDataToBeanList(inputFilePath, modelBean);
                update(combineBeanList, updateBeanList);
            }
            average(combineBeanList, inputMethodDirFileList.size());
            parentFile = new File(outputMethodDirFile, parameterFileDir);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, parentFile.getAbsolutePath(), "result.txt");
            csvWrite.startWriting(outputFilePath);
            csvWrite.writeOneLine(title);
            csvWrite.writeBeanList(combineBeanList);
            csvWrite.endWriting();
        }

    }

    /**
     *  新增：专门用于维度消融实验的合并方法
     */
    private static void combineDimensionAblationProcess(File outputMethodDirFile, List<File> inputMethodDirFileList, Set<String> parameterSet) {
        List<ResultBean> combineBeanList = null, updateBeanList;
        ResultBean tempBean;
        BeanInterface<ResultBean> modelBean = new ResultBean();
        PureTriple<Double, Integer, Integer> paramsTriple;
        String inputFilePath, outputFilePath, title;
        CSVWrite csvWrite = new CSVWrite();
        File parentFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        for (String parameterFileDir : parameterSet) {
            paramsTriple = ParameterUtils.extractBudgetWindowSizeDimensionParametersAccordingFileDirName(parameterFileDir);
            title = CSVReadEnhanced.readDataTitle(inputMethodDirFileList.get(0).listFiles(directoryFileFilter)[0].getAbsolutePath()+ConstantValues.FILE_SPLIT+"result.txt");
            combineBeanList = new ArrayList<>();
            // 使用维度消融专用的机制列表（7种）
            for (String beanName : nameStringArrayOnlyForDimensionAblation) {
                tempBean = ResultBean.getInitializedBean(beanName, paramsTriple.getKey(), paramsTriple.getValue());
                combineBeanList.add(tempBean);
            }
            for (File inputMethodDir : inputMethodDirFileList) {
                inputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, inputMethodDir, parameterFileDir, "result.txt");
                updateBeanList = CSVReadEnhanced.readDataToBeanList(inputFilePath, modelBean);
                update(combineBeanList, updateBeanList);
            }
            average(combineBeanList, inputMethodDirFileList.size());
            parentFile = new File(outputMethodDirFile, parameterFileDir);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, parentFile.getAbsolutePath(), "result.txt");
            csvWrite.startWriting(outputFilePath);

            // 从参数目录名中提取 positionSize（格式：p_x-w_y-d_z）
            Integer positionSize = paramsTriple.getTag();
            boolean hasPositionSize = positionSize != null && title.contains("PositionSize");

            if (hasPositionSize) {
                csvWrite.writeOneLine(title);
                for (ResultBean bean : combineBeanList) {
                    csvWrite.writeOneLine(bean.toFormatString() + "," + positionSize);
                }
            } else {
                csvWrite.writeOneLine(title);
                csvWrite.writeBeanList(combineBeanList);
            }

            csvWrite.endWriting();
        }
    }

    private static void combineContainingSPASProcess(File outputMethodDirFile, List<File> inputMethodDirFileList, Set<String> parameterSet) {
        List<ResultBean> combineBeanList = null, updateBeanList;
        ResultBean tempBean;
        BeanInterface<ResultBean> modelBean = new ResultBean();
        BasicPair<Double, Integer> paramsPair;
        String inputFilePath, outputFilePath, title;
        CSVWrite csvWrite = new CSVWrite();
        File parentFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        for (String parameterFileDir : parameterSet) {
            paramsPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(parameterFileDir);
            title = CSVReadEnhanced.readDataTitle(inputMethodDirFileList.get(0).listFiles(directoryFileFilter)[0].getAbsolutePath()+ConstantValues.FILE_SPLIT+"result.txt");
//            System.out.println(title);
            combineBeanList = new ArrayList<>();
            for (String beanName : nameStringArrayOnlyForSPAS) {
                tempBean = ResultBean.getInitializedBean(beanName, paramsPair.getKey(), paramsPair.getValue());
                combineBeanList.add(tempBean);
            }
            for (File inputMethodDir : inputMethodDirFileList) {
                inputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, inputMethodDir, parameterFileDir, "result.txt");
                updateBeanList = CSVReadEnhanced.readDataToBeanList(inputFilePath, modelBean);
                update(combineBeanList, updateBeanList);
            }
            average(combineBeanList, inputMethodDirFileList.size());
            parentFile = new File(outputMethodDirFile, parameterFileDir);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, parentFile.getAbsolutePath(), "result.txt");
            csvWrite.startWriting(outputFilePath);
            csvWrite.writeOneLine(title);
            csvWrite.writeBeanList(combineBeanList);
            csvWrite.endWriting();
        }

    }
    private static void combineContainingErrorDetailsProcess(File outputMethodDirFile, List<File> inputMethodDirFileList, Set<String> parameterSet) {
        List<ResultBean> combineBeanList = null, updateBeanList;
        ResultBean tempBean;
        BeanInterface<ResultBean> modelBean = new ResultBean();
        BasicPair<Double, Integer> paramsPair;
        String inputFilePath, outputFilePath, title;
        CSVWrite csvWrite = new CSVWrite();
        File parentFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        Method formatStringMethod = null;
        try {
            formatStringMethod = ResultBean.class.getMethod("toFormatErrorDetailsString");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (String parameterFileDir : parameterSet) {
            paramsPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(parameterFileDir);
            title = CSVReadEnhanced.readDataTitle(inputMethodDirFileList.get(0).listFiles(directoryFileFilter)[0].getAbsolutePath()+ConstantValues.FILE_SPLIT+"result.txt");
//            System.out.println(title);
            combineBeanList = new ArrayList<>();
            for (String beanName : nameStringArrayOnlyForErrorDetails) {
                tempBean = ResultBean.getInitializedBeanContainingErrorDetails(beanName, paramsPair.getKey(), paramsPair.getValue());
                combineBeanList.add(tempBean);
            }
            for (File inputMethodDir : inputMethodDirFileList) {
                inputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, inputMethodDir, parameterFileDir, "result.txt");
                updateBeanList = CSVReadEnhanced.readDataToBeanList(inputFilePath, modelBean);
                updateErrorDetails(combineBeanList, updateBeanList);
            }
            averageErrorDetails(combineBeanList, inputMethodDirFileList.size());
            parentFile = new File(outputMethodDirFile, parameterFileDir);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, parentFile.getAbsolutePath(), "result.txt");
            csvWrite.startWriting(outputFilePath);
            csvWrite.writeOneLine(title);
            csvWrite.writeBeanListWithMethod(combineBeanList, formatStringMethod);
            csvWrite.endWriting();
        }

    }
    private static void combineContainingTotalProcess(File outputMethodDirFile, List<File> inputMethodDirFileList, Set<String> parameterSet) {
        List<ResultBean> combineBeanList = null, updateBeanList;
        ResultBean tempBean;
        BeanInterface<ResultBean> modelBean = new ResultBean();
        BasicPair<Double, Integer> paramsPair;
        String inputFilePath, outputFilePath, title;
        CSVWrite csvWrite = new CSVWrite();
        File parentFile;
        FileFilter directoryFileFilter = new DirectoryFileFilter();
        for (String parameterFileDir : parameterSet) {
            paramsPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(parameterFileDir);
            title = CSVReadEnhanced.readDataTitle(inputMethodDirFileList.get(0).listFiles(directoryFileFilter)[0].getAbsolutePath()+ConstantValues.FILE_SPLIT+"result.txt");
//            System.out.println(title);
            combineBeanList = new ArrayList<>();
            for (String beanName : nameStringArrayTotal) {
                tempBean = ResultBean.getInitializedBean(beanName, paramsPair.getKey(), paramsPair.getValue());
                combineBeanList.add(tempBean);
            }
            for (File inputMethodDir : inputMethodDirFileList) {
                inputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, inputMethodDir, parameterFileDir, "result.txt");
                updateBeanList = CSVReadEnhanced.readDataToBeanList(inputFilePath, modelBean);
                update(combineBeanList, updateBeanList);
            }
            average(combineBeanList, inputMethodDirFileList.size());
            parentFile = new File(outputMethodDirFile, parameterFileDir);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            outputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, parentFile.getAbsolutePath(), "result.txt");
            csvWrite.startWriting(outputFilePath);
            csvWrite.writeOneLine(title);
            csvWrite.writeBeanList(combineBeanList);
            csvWrite.endWriting();
        }

    }



    private static void update(List<ResultBean> combineBeanList, List<ResultBean> updateBeanList) {
        ResultBean combineBean, updateBean;
        for (int i = 0; i < combineBeanList.size(); i++) {
            combineBean = combineBeanList.get(i);
            updateBean = updateBeanList.get(i);
            combineBean.setBatchSize(combineBean.getBatchSize() + updateBean.getBatchSize());
            combineBean.setTimeCost(combineBean.getTimeCost() + updateBean.getTimeCost());
            combineBean.setBre(combineBean.getBre() + updateBean.getBre());
            combineBean.setBjsd(combineBean.getBjsd() + updateBean.getBjsd());
            combineBean.setBwd(combineBean.getBwd() + updateBean.getBwd());
            combineBean.setMre(combineBean.getMre()+ updateBean.getMre());
            combineBean.setMjsd(combineBean.getMjsd() + updateBean.getMjsd());
            combineBean.setMwd(combineBean.getMwd() + updateBean.getMwd());
        }
    }
    private static void updateErrorDetails(List<ResultBean> combineBeanList, List<ResultBean> updateBeanList) {
        ResultBean combineBean, updateBean;
        for (int i = 0; i < combineBeanList.size(); i++) {
            combineBean = combineBeanList.get(i);
            updateBean = updateBeanList.get(i);
            combineBean.setBatchSize(combineBean.getBatchSize() + updateBean.getBatchSize());
            combineBean.setTimeCost(combineBean.getTimeCost() + updateBean.getTimeCost());
            combineBean.setBre(combineBean.getBre() + updateBean.getBre());
            combineBean.setBjsd(combineBean.getBjsd() + updateBean.getBjsd());
            combineBean.setBwd(combineBean.getBwd() + updateBean.getBwd());
            combineBean.setPartA_BDPVar(combineBean.getPartA_BDPVar() + updateBean.getPartA_BDPVar());
            combineBean.setPartB_BDPVar(combineBean.getPartB_BDPVar() + updateBean.getPartB_BDPVar());
            combineBean.setPartA_BSampleVar(combineBean.getPartA_BSampleVar() + updateBean.getPartA_BSampleVar());
            combineBean.setPartB_BSampleVar(combineBean.getPartB_BSampleVar() + updateBean.getPartB_BSampleVar());
            combineBean.setPartA_BCountVar(combineBean.getPartA_BCountVar() + updateBean.getPartA_BCountVar());
            combineBean.setPartB_BCountVar(combineBean.getPartB_BCountVar() + updateBean.getPartB_BCountVar());
            combineBean.setPartA_BiasSquare(combineBean.getPartA_BiasSquare() + updateBean.getPartA_BiasSquare());
            combineBean.setPartB_BiasSquare(combineBean.getPartB_BiasSquare() + updateBean.getPartB_BiasSquare());
            combineBean.setNonNullCount(combineBean.getNonNullCount() + updateBean.getNonNullCount());

            combineBean.setMre(combineBean.getMre()+ updateBean.getMre());
            combineBean.setMjsd(combineBean.getMjsd() + updateBean.getMjsd());
            combineBean.setMwd(combineBean.getMwd() + updateBean.getMwd());
            combineBean.setPartA_MDPVar(combineBean.getPartA_MDPVar() + updateBean.getPartA_MDPVar());
            combineBean.setPartB_MDPVar(combineBean.getPartB_MDPVar() + updateBean.getPartB_MDPVar());
            combineBean.setPartA_MSampleVar(combineBean.getPartA_MSampleVar() + updateBean.getPartA_MSampleVar());
            combineBean.setPartB_MSampleVar(combineBean.getPartB_MSampleVar() + updateBean.getPartB_MSampleVar());
            combineBean.setPartA_MCountVar(combineBean.getPartA_MCountVar() + updateBean.getPartA_MCountVar());
            combineBean.setPartB_MCountVar(combineBean.getPartB_MCountVar() + updateBean.getPartB_MCountVar());
            combineBean.setPartA_MBiasSquare(combineBean.getPartA_MBiasSquare() + updateBean.getPartA_MBiasSquare());
            combineBean.setPartB_MBiasSquare(combineBean.getPartB_MBiasSquare() + updateBean.getPartB_MBiasSquare());
            combineBean.setNonNullMCount(combineBean.getNonNullMCount() + updateBean.getNonNullMCount());

        }
    }

    private static void average(List<ResultBean> combineBeanList, int size) {
        for (ResultBean bean : combineBeanList) {
            bean.setBatchSize(bean.getBatchSize()/size);
            bean.setTimeCost(bean.getTimeCost()/size);
            bean.setBre(bean.getBre()/size);
            bean.setBjsd(bean.getBjsd()/size);
            bean.setBwd(bean.getBwd()/size);
            bean.setMre(bean.getMre()/size);
            bean.setMjsd(bean.getMjsd()/size);
            bean.setMwd(bean.getMwd()/size);
        }
    }
    private static void averageErrorDetails(List<ResultBean> combineBeanList, int size) {
        for (ResultBean bean : combineBeanList) {
            bean.setBatchSize(bean.getBatchSize()/size);
            bean.setTimeCost(bean.getTimeCost()/size);
            bean.setBre(bean.getBre()/size);
            bean.setBjsd(bean.getBjsd()/size);
            bean.setBwd(bean.getBwd()/size);

            bean.setPartA_BDPVar(bean.getPartA_BDPVar()/size);
            bean.setPartB_BDPVar(bean.getPartB_BDPVar()/size);
            bean.setPartA_BSampleVar(bean.getPartA_BSampleVar()/size);
            bean.setPartB_BSampleVar(bean.getPartB_BSampleVar()/size);
            bean.setPartA_BCountVar(bean.getPartA_BCountVar()/size);
            bean.setPartB_BCountVar(bean.getPartB_BCountVar()/size);
            bean.setPartA_BiasSquare(bean.getPartA_BiasSquare()/size);
            bean.setPartB_BiasSquare(bean.getPartB_BiasSquare()/size);
            bean.setNonNullCount(bean.getNonNullCount()/size);

            bean.setMre(bean.getMre()/size);
            bean.setMjsd(bean.getMjsd()/size);
            bean.setMwd(bean.getMwd()/size);

            bean.setPartA_MDPVar(bean.getPartA_MDPVar()/size);
            bean.setPartB_MDPVar(bean.getPartB_MDPVar()/size);
            bean.setPartA_MSampleVar(bean.getPartA_MSampleVar()/size);
            bean.setPartB_MSampleVar(bean.getPartB_MSampleVar()/size);
            bean.setPartA_MCountVar(bean.getPartA_MCountVar()/size);
            bean.setPartB_MCountVar(bean.getPartB_MCountVar()/size);
            bean.setPartA_MBiasSquare(bean.getPartA_MBiasSquare()/size);
            bean.setPartB_MBiasSquare(bean.getPartB_MBiasSquare()/size);
            bean.setNonNullMCount(bean.getNonNullMCount()/size);
        }
    }

    private static File fillRoundAndParameterInfoAndGetOutputMethodDirFile(String outputDir, File inputDirFile, FileFilter roundDirectoryFileFilter, File outputDirFile, Set<String> outputParamsFileNameSet, List<File> datasetRoundList, int roundSize) {
        File outputMethodDirFile;
        File[] roundDirs = inputDirFile.listFiles(roundDirectoryFileFilter);

//        MyPrint.showSplitLine("-", 50);
//        MyPrint.showArray(roundDirs);
//        MyPrint.showSplitLine("-", 50);

        File tempInputMethodDirFile = OtherUtils.getSubDatasetNameFile(roundDirs[0]);
        String methodName = tempInputMethodDirFile.getName();
        outputMethodDirFile = new File(outputDir, methodName);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdirs();
        }
        FileFilter directoryFilter = new DirectoryFileFilter();

        if (!outputMethodDirFile.exists()) {
            outputMethodDirFile.mkdirs();
        }
        File[] paramDirFileArray = tempInputMethodDirFile.listFiles(directoryFilter);
        for (File paramFile : paramDirFileArray) {
            outputParamsFileNameSet.add(paramFile.getName());
        }
//        for (File roundDir : roundDirs) {
//            File methodDirFile =  OtherUtils.getSubDatasetNameFile(roundDir);
//            roundDir.listFiles(directoryFilter);
//            datasetRoundList.add(methodDirFile);
//        }
        for (int i = 0; i < roundDirs.length; ++i) {
            if (i >= roundSize) {
                break;
            }
            File roundDir = roundDirs[i];
            File methodDirFile =  OtherUtils.getSubDatasetNameFile(roundDir);
            roundDir.listFiles(directoryFilter);
            datasetRoundList.add(methodDirFile);
        }
        return outputMethodDirFile;
    }

    /**
     * input dir:
     *      ${input_dir}/round_i/k.${dataset_name}/param_dir/result.txt
     * output dir:
     *      ${output_dir}/k.${dataset_name}/param_dir/result.txt
     * @param inputDir
     * @param outputDir
     */
    public static void combineMultipleMainRound(String inputDir, String outputDir, int roundSize) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList, roundSize);

//        MyPrint.showSplitLine("*", 150);
//        System.out.println(inputDir);
//        System.out.println(outputDir);
//        MyPrint.showList(datasetRoundList);
//        MyPrint.showSplitLine("+", 150);

        combineMainProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }
    public static void combineMultipleInternalRound(String inputDir, String outputDir, int roundSize) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList, roundSize);

        combineInternalProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }

    public static void combineMultipleSerialRound(String inputDir, String outputDir, int roundSize) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList, roundSize);

        combineSerialProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }

    /**
     * 新增：维度消融实验的多轮合并入口
     */
    public static void combineMultipleDimensionAblationRound(String inputDir, String outputDir, int roundSize) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList, roundSize);
        combineDimensionAblationProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }

    public static void combineMultipleContainingSPASRound(String inputDir, String outputDir, int roundSize) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList, roundSize);

        combineContainingSPASProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }
    public static void combineMultipleContainingErrorsRound(String inputDir, String outputDir, int roundSize) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList, roundSize);

        combineContainingErrorDetailsProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }
    public static void combineMultipleContainingTotalRound(String inputDir, String outputDir, int roundSize) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList, roundSize);

        combineContainingTotalProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }

    public static void main(String[] args) {
//        String inputDir = args[0];
//        String outputDir = args[1];
        String inputDir = Constant.trajectoriesFilePath;
        String outputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "1.result");
        String roundSizeStr = ConfigureUtils.getFileHandleInfo("trajectories", "combineRound");
        combineMultipleMainRound(inputDir, outputDir, Integer.parseInt(roundSizeStr));
//        String inputDir = Constant.tlnsFilePath;
//        String outputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "1.result_internal");
//        combineMultipleInternalRound(inputDir, outputDir);
    }
}
