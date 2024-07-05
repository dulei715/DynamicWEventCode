package important_test;

import ecnu.dll._config.Constant;
import ecnu.dll._config.TestData;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll.dataset.real.datasetA.TrajectoryBean;
import ecnu.dll.dataset.real.datasetA.TrajectoryBeanUtils;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class DatasetTrajectoryTest {
    /**
     * 测试所有用户数据量的最大值和最小值
     */
    @Test
    public void testTrajectoryItemOfEachUser() {
        String basicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "taxi_log_2008_by_id");
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
        String basicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "taxi_log_2008_by_id_filter");
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
        String basicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "taxi_log_2008_by_id_filter");
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

    /**
     *
     */
    @Test
    public void testPositionRange() {

    }

}
