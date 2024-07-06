package basic_test;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.CSVRead;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetA.basic_struct.TrajectoryBean;
import org.junit.Test;

import java.util.Date;
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

    @Test
    public void fun2() {
        String dataString = "4,2008-02-02 15:15:04,116.47002,39.90666";
        TrajectoryBean bean = TrajectoryBean.toTrajectoryBeanWithFormatTime(dataString.split(","));
        System.out.println(bean);
        Date date = new Date(bean.getTimestamp());
        System.out.println(date);
    }
}
