package ecnu.dll.utils.run;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.filter.file_filter.DirectoryFileFilter;
import cn.edu.dll.io.write.CSVWrite;
import cn.edu.dll.struct.bean_structs.BeanInterface;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import ecnu.dll.run.c_dataset_run.utils.ResultBean;
import ecnu.dll.utils.filters.RoundDirectoryFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class RepeatUtils {

    private static final String[] nameStringArray = new String[]{
            "NP", "BD", "BA", "PBD", "PBA"
//            , "PDBD", "PDBA"
    };

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
//            paramsPair = ParameterUtils.extractBudgetWindowSizeParametersAccordingFileDirName(parameterFileDir);
            title = CSVReadEnhanced.readDataTitle(inputMethodDirFileList.get(0).listFiles(directoryFileFilter)[0].getAbsolutePath()+ConstantValues.FILE_SPLIT+"result.txt");
//            System.out.println(title);
            combineBeanList = new ArrayList<>();
            for (String beanName : nameStringArray) {
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

    private static void update(List<ResultBean> combineBeanList, List<ResultBean> updateBeanList) {
        ResultBean combineBean, updateBean;
        for (int i = 0; i < combineBeanList.size(); i++) {
            combineBean = combineBeanList.get(i);
            updateBean = updateBeanList.get(i);
            combineBean.setMre(combineBean.getMre()+ updateBean.getMre());
        }
    }

    private static void average(List<ResultBean> combineBeanList, int size) {
        for (ResultBean bean : combineBeanList) {
            bean.setMre(bean.getMre()*1.0/size);
        }
    }

    private static File fillRoundAndParameterInfoAndGetOutputMethodDirFile(String outputDir, File inputDirFile, FileFilter roundDirectoryFileFilter, File outputDirFile, Set<String> outputParamsFileNameSet, List<File> datasetRoundList) {
        File outputMethodDirFile;
        File[] roundDirs = inputDirFile.listFiles(roundDirectoryFileFilter);
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

        for (File roundDir : roundDirs) {
            File methodDirFile =  OtherUtils.getSubDatasetNameFile(roundDir);roundDir.listFiles(directoryFilter);
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
    public static void combineMultipleMainRound(String inputDir, String outputDir) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList);

        combineMainProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }
    public static void combineMultipleInternalRound(String inputDir, String outputDir) {
        FileFilter roundDirectoryFileFilter = new RoundDirectoryFilter();
        File inputDirFile = new File(inputDir);
        File outputDirFile = new File(outputDir);
        File outputMethodDirFile;
        List<File> datasetRoundList = new ArrayList<>();
        Set<String> outputParamsFileNameSet = new HashSet<>();
        outputMethodDirFile = fillRoundAndParameterInfoAndGetOutputMethodDirFile(outputDir, inputDirFile, roundDirectoryFileFilter, outputDirFile, outputParamsFileNameSet, datasetRoundList);

        combineInternalProcess(outputMethodDirFile, datasetRoundList, outputParamsFileNameSet);
    }

    public static void main(String[] args) {
//        String inputDir = args[0];
//        String outputDir = args[1];
        String inputDir = Constant.tlnsFilePath;
        String outputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "1.result_internal");
//        combineMultipleMainRound(inputDir, outputDir);
        combineMultipleInternalRound(inputDir, outputDir);
    }
}
