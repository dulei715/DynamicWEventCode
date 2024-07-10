package ecnu.dll.run.c_dataset_run.utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.io.write.CSVWrite;
import cn.edu.dll.struct.bean_structs.BeanInterface;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import ecnu.dll.utils.filters.TxtFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PostProcessUtils {
    public static void combineResultBefore(String dirPath) {
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles(new TxtFilter());
        List<List<ResultBeanBefore>> dataList = new ArrayList<>();
        List<ResultBeanBefore> tempList;
        BeanInterface<ResultBeanBefore> beanBeanInterface = new ResultBeanBefore();
        String title = CSVReadEnhanced.readDataTitle(files[0].getAbsolutePath());
        List<String> dataStringList;
        for (File file : files) {
            dataStringList = CSVReadEnhanced.readDataLinesWithoutTitle(file.getAbsolutePath());
            tempList = new ArrayList<>();
            for (String str : dataStringList) {
                tempList.add(ResultBeanBefore.toBean(str));
            }
            dataList.add(tempList);
        }
        List<ResultBeanBefore> resultList = new ArrayList<>();
        ResultBeanBefore resultBean, tempBean;
        int innerSize = dataList.get(0).size();
        for (int i = 0; i < innerSize; i++) {
            resultBean = ResultBeanBefore.getInitializedBean(dataList.get(0).get(i));
            for (List<ResultBeanBefore> innerList : dataList) {
                tempBean = innerList.get(i);
                resultBean.setbRE(resultBean.getbRE() + tempBean.getbRE());
            }
            resultList.add(resultBean);
        }
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, dirPath, "combine", "combine.txt");
        BasicWrite basicWrite = new BasicWrite(",");
        basicWrite.startWriting(outputPath);
        basicWrite.writeOneLine(title);
        for (ResultBeanBefore bean : resultList) {
            basicWrite.writeOneLine(bean.toCSVString());
        }
        basicWrite.endWriting();
    }
    public static void combineResult(String dirPath) {
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles(new TxtFilter());
        List<List<ResultBean>> dataList = new ArrayList<>();
        List<ResultBean> tempList;
        BeanInterface<ResultBean> beanBeanInterface = new ResultBean();
        String title = CSVReadEnhanced.readDataTitle(files[0].getAbsolutePath());
        List<String> dataStringList;
        for (File file : files) {
            dataStringList = CSVReadEnhanced.readDataLinesWithoutTitle(file.getAbsolutePath());
            tempList = new ArrayList<>();
            for (String str : dataStringList) {
                tempList.add(ResultBean.toBean(str));
            }
            dataList.add(tempList);
        }
        List<ResultBean> resultList = new ArrayList<>();
        ResultBean resultBean, tempBean;
        int innerSize = dataList.get(0).size();
        for (int i = 0; i < innerSize; i++) {
            resultBean = ResultBean.getInitializedBean(dataList.get(0).get(i));
            for (List<ResultBean> innerList : dataList) {
                tempBean = innerList.get(i);
                resultBean.setBatchSize(resultBean.getBatchSize()+ tempBean.getBatchSize());
                resultBean.setbRE(resultBean.getbRE() + tempBean.getbRE());
            }
            resultBean.setmRE(resultBean.getbRE() / resultBean.getBatchSize());
            resultList.add(resultBean);
        }
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, dirPath, "combine", "combine.txt");
        BasicWrite basicWrite = new BasicWrite(",");
        basicWrite.startWriting(outputPath);
        basicWrite.writeOneLine(title + ",MRE");
        for (ResultBean bean : resultList) {
            basicWrite.writeOneLine(bean.toCSVString());
        }
        basicWrite.endWriting();
    }

    public static void main(String[] args) {
        String inputPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "test");
        combineResultBefore(inputPath);
    }
}
