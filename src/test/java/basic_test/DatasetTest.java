package basic_test;

import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.dataset.DataSetHandler;
import ecnu.dll.dataset.struct.TrajectoryBean;
import ecnu.dll.dataset.struct.TrajectoryBeanUtils;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import org.junit.Test;

import java.util.List;

public class DatasetTest {
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
}
