package basic_test;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import ecnu.dll.dataset.DataSetHandler;
import ecnu.dll.dataset.real.datasetA.TrajectoryBean;
import ecnu.dll.dataset.real.datasetA.TrajectoryBeanUtils;
import ecnu.dll.dataset.real.datasetB.spetial_tools.CheckInStringTool;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class BasicDatasetTest {
    @Test
    public void fun1() {
        Double p0 = 0.05;
        Double standardVariance = 0.0025;
        int size = 100;
        Double[] result = DataSetHandler.getLinearNormalSequence(p0, standardVariance, size);
        MyPrint.showArray(result);
    }

    @Test
    public void fun2() {
        Double amplitude = 0.05;
        Double angularVelocity = 0.01;
        Double initialY = 0.075;
        int size = 100;
        Double[] result = DataSetHandler.getSinSequence(amplitude, angularVelocity, initialY, size);
        MyPrint.showArray(result);
    }

    @Test
    public void fun3() {
        Double valueA = 0.25;
        Double valueB = 0.01;
        int size = 100;
        Double[] result = DataSetHandler.getLogSequence(valueA, valueB, size);
        MyPrint.showArray(result);
    }

    @Test
    public void fun4() {
        String path = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\T-drive Taxi Trajectories\\combineData.csv";
//        List<String> result = CSVReadEnhanced.readStringData(path);
        List<TrajectoryBean> result = CSVReadEnhanced.readDataToBeanList(path, new TrajectoryBean());
        System.out.println(result.size());
        TrajectoryBeanUtils.getInfo(result);
    }

    @Test
    public void fun5() {
        String basicPath = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\T-drive Taxi Trajectories\\taxi_log_2008_by_id_filter";
        File file = new File(basicPath);
        String[] fileNameArray = file.list();
        String tempPath;
        List<TrajectoryBean> tempList;
        for (String fileName : fileNameArray) {
            tempPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, fileName);
            tempList = CSVReadEnhanced.readDataToBeanList(tempPath, new TrajectoryBean());
            TrajectoryBeanUtils.getInfo(tempList);
        }
    }

    @Test
    public void fun6() {
        String basicPath = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\CheckIn_dataset_TIST2015";
        String checkInPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "dataset_TIST2015_Checkins.txt");
//        String checkInPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "dataset_TIST2015_POIs.txt");
        BasicRead basicRead = new BasicRead();
        basicRead.startReading(checkInPath);
        String record = basicRead.readOneLine();
        basicRead.endReading();
        System.out.println(record);
        String[] result = CheckInStringTool.recordSplit(record);
        MyPrint.showArray(result);
    }
    @Test
    public void fun7() {
        String basicPath = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\CheckIn_dataset_TIST2015";
        String checkInPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "dataset_TIST2015_Checkins.txt");
        BasicRead basicRead = new BasicRead();
        basicRead.startReading(checkInPath);
        String record = basicRead.readOneLine();
        basicRead.endReading();
        System.out.println(record);
    }
}
