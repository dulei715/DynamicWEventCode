package important_test;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll.dataset.real.datasetA.TrajectoryBean;
import ecnu.dll.dataset.real.datasetA.TrajectoryBeanUtils;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class DatasetTrajectoryTest {
    @Test
    public void testTrajectoryItemOfEachUser() {
        String basicPath = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\T-drive Taxi Trajectories\\taxi_log_2008_by_id";
        File file = new File(basicPath);
        String[] fileNameArray = file.list();
        String tempPath;
        List<TrajectoryBean> tempList;
        int tempSize;
        int minSize = Integer.MAX_VALUE;
        int maxSize = Integer.MIN_VALUE;
        for (String fileName : fileNameArray) {
            tempPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, fileName);
            tempList = CSVReadEnhanced.readDataToBeanList(tempPath, new TrajectoryBean());
            tempSize = tempList.size();
            minSize = Math.min(minSize, tempSize);
            maxSize = Math.max(maxSize, tempSize);
        }
        System.out.println("minSize: " + minSize);
        System.out.println("maxSize: " + maxSize);
    }


    @Test
    public void testFilterTotalRecord() {
        String basicPath = "E:\\1.学习\\4.数据集\\3.dataset_for_dynamic_w_event\\0_dataset\\T-drive Taxi Trajectories\\taxi_log_2008_by_id_filter";
        File file = new File(basicPath);
        String[] fileNameArray = file.list();
        String tempPath;
        List<TrajectoryBean> tempList;
        int tempSize;
        int totalSize = 0;
        for (String fileName : fileNameArray) {
            tempPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, fileName);
            tempList = CSVReadEnhanced.readDataToBeanList(tempPath, new TrajectoryBean());
            tempSize = tempList.size();
            totalSize += tempSize;
        }
        System.out.println("totalSize: " + totalSize);
    }

    @Test
    public void longitudeLatitudeRatio() {
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
}
