package basic_test;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.CSVRead;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetA.TrajectoryBean;
import org.junit.Test;

import java.util.List;

public class BeanTest {
    @Test
    public void fun1() {
        String basicPath = Constant.trajectoriesFilePath + ConstantValues.FILE_SPLIT + "taxi_log_2008_by_id";
        String filePath = basicPath.concat(ConstantValues.FILE_SPLIT + "1.txt");
        TrajectoryBean trajectoryBean = new TrajectoryBean();
        List<TrajectoryBean> resultList = CSVRead.readDataToBeanList(filePath, trajectoryBean);
        MyPrint.showList(resultList, ConstantValues.LINE_SPLIT);

    }
}
