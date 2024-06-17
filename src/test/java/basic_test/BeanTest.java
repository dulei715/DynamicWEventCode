package basic_test;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.CSVRead;
import ecnu.dll.dataset.struct.TrajectoryBean;
import org.junit.Test;

import java.util.List;

public class BeanTest {
    @Test
    public void fun1() {
        String basicPath = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\T-drive Taxi Trajectories\\taxi_log_2008_by_id";
        String filePath = basicPath.concat("\\1.txt");
        TrajectoryBean trajectoryBean = new TrajectoryBean();
        List<TrajectoryBean> resultList = CSVRead.readDataToBeanList(filePath, trajectoryBean);
        MyPrint.showList(resultList, ConstantValues.LINE_SPLIT);

    }
}
