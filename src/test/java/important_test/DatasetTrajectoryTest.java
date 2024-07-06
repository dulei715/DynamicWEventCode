package important_test;

import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import ecnu.dll._config.Constant;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll.dataset.real.datasetA.basic_struct.TrajectoryBean;
import ecnu.dll.dataset.real.datasetA.basic_struct.TrajectoryBeanUtils;
import ecnu.dll.dataset.utils.CSVReadEnhanced;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    public void testLongitudeLatitudeRange() {
        String dirPath = Constant.trajectoriesFilePath;
//        System.out.println(dirPath);
        File filterDataDirFile = new File(dirPath, "taxi_log_2008_by_id_filter");
//        System.out.println(filterDataDirFile.getAbsolutePath());
//        System.out.println(filterDataDirFile.list().length);
        // 前提假设：所有数据的经纬度都不低于0
        Double minLongitude = Double.MAX_VALUE, maxLongitude = 0D, minLatitude = Double.MAX_VALUE, maxLatitude = 0D;
        File[] files = filterDataDirFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempList;
        TrajectoryBean tempBean;
        Double tempLongitude, tempLatitude;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            for (String lineString : tempList) {
                tempBean = TrajectoryBean.toTrajectoryBeanWithFormatTime(basicRead.split(lineString));
                tempLongitude = tempBean.getLongitude();
                tempLatitude = tempBean.getLatitude();
                minLongitude = Math.min(minLongitude, tempLongitude);
                maxLongitude = Math.max(maxLongitude, tempLongitude);
                minLatitude = Math.min(minLatitude, tempLatitude);
                maxLatitude = Math.max(maxLatitude, tempLatitude);
            }
        }
        System.out.printf("最小longitude: %f, 最大longitude: %f\n", minLongitude, maxLongitude);
        System.out.printf("最小latitude: %f, 最大latitude: %f\n", minLatitude, maxLatitude);

    }
    private static int getIndex(double value, double unitValue) {
        double share = value / unitValue;
        return (int)Math.floor(share);
    }
    private void addElementToMap(Map<Integer, Integer> map, Integer element) {
        Integer count = map.getOrDefault(element, 0);
        ++count;
        map.put(element, count);
    }

    /**
     * 0. 测试经纬度的最小值和最大值（用于分割地理位置）(不用了……)
     *  (1) 测试调用test.important_test.DatasetTrajectoryTest:longitudeLatitudeRatio()
     *      最小longitude: 0.000000, 最大longitude: 255.300000
     *      最小latitude: 0.000000, 最大latitude: 96.067670
     */
    @Test
    public void testAreaDistribution() {
        String dirPath = Constant.trajectoriesFilePath;
        File filterDataDirFile = new File(dirPath, "taxi_log_2008_by_id_filter");
        double maxLongitude = 255.3;
        double maxLatitude = 96.06767;
        double longitudeUnitSize = 25.5;
        double latitudeUnitSize = 9.6;
        int longitudeIndexSize = (int)Math.ceil(maxLongitude / longitudeUnitSize);
        int latitudeIndexSize = (int)Math.ceil(maxLatitude / latitudeUnitSize);
        File[] files = filterDataDirFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempList;
        TrajectoryBean tempBean;
        Double tempLongitude, tempLatitude;
        Map<Integer, String> longitudeIndexToRange = new TreeMap<>();
        Map<Integer, Integer> longitudeIndexToCount = new TreeMap<>();
        Map<Integer, String> latitudeIndexToRange = new TreeMap<>();
        Map<Integer, Integer> latitudeIndexToCount = new TreeMap<>();
        String tempString;
        Integer tempLongitudeIndex, tempLatitudeIndex;

        for (int i = 0; i < longitudeIndexSize; i++) {
            tempString = String.format("%f--%f", longitudeUnitSize * i, longitudeUnitSize * (i+1));
            longitudeIndexToRange.put(i, tempString);
        }
        for (int i = 0; i < latitudeIndexSize; i++) {
            tempString = String.format("%f--%f", latitudeUnitSize * i, latitudeUnitSize * (i+1));
            latitudeIndexToRange.put(i, tempString);
        }

        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            for (String lineString : tempList) {
                tempBean = TrajectoryBean.toTrajectoryBeanWithFormatTime(basicRead.split(lineString));
                tempLongitude = tempBean.getLongitude();
                tempLatitude = tempBean.getLatitude();
                tempLongitudeIndex = getIndex(tempLongitude, longitudeUnitSize);
                tempLatitudeIndex = getIndex(tempLatitude, latitudeUnitSize);
                addElementToMap(longitudeIndexToCount, tempLongitudeIndex);
                addElementToMap(latitudeIndexToCount, tempLatitudeIndex);
            }
        }
        Integer tempIndex, tempCount;
        String tempRange;
        System.out.println("longitude:");
        for (Map.Entry<Integer, String> rangeEntry : longitudeIndexToRange.entrySet()) {
            tempIndex = rangeEntry.getKey();
            tempRange = rangeEntry.getValue();
            tempCount = longitudeIndexToCount.get(tempIndex);
            System.out.printf("%s的个数: %d\n", tempRange, tempCount);
        }
        MyPrint.showSplitLine("*", 150);
        System.out.println("latitude:");
        for (Map.Entry<Integer, String> rangeEntry : latitudeIndexToRange.entrySet()) {
            tempIndex = rangeEntry.getKey();
            tempRange = rangeEntry.getValue();
            tempCount = latitudeIndexToCount.get(tempIndex);
            System.out.printf("%s的个数: %d\n", tempRange, tempCount);
        }

    }

    @Test
    public void testTimeSlotRange() {
        String dirPath = Constant.trajectoriesFilePath;
        String subDir = "extract_data";
        File subDirFile = new File(dirPath, subDir);
        File[] files = subDirFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempList;
        TrajectoryBean tempBean;
        Long minTimeSlot = Long.MAX_VALUE;
        Long maxTimeSlot = 0L;
        Long tempTimeSlot;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
//            System.out.println("Start file " + file.getName());
            tempList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            for (String str : tempList) {
                tempBean = TrajectoryBean.toTrajectoryBean(basicRead.split(str));
                tempTimeSlot = tempBean.getTimestamp();
                minTimeSlot = Math.min(minTimeSlot, tempTimeSlot);
                maxTimeSlot = Math.max(maxTimeSlot, tempTimeSlot);
            }
        }
        System.out.printf("最小时间戳：%d; 最大时间戳：%d\n", minTimeSlot, maxTimeSlot);
    }

    @Test
    public void testFilterTotalRecord2() {
        String basicPath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoriesFilePath, "extract_data");
        File basicFile = new File(basicPath);
        File[] fileArray = basicFile.listFiles();
        String tempPath;
        List<String> tempList;
        int tempSize;
        int totalSize = 0;
        BasicRead basicRead = new BasicRead(",");
        for (File file : fileArray) {
            basicRead.startReading(file.getAbsolutePath());
            tempList =  basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            tempSize = tempList.size();
            totalSize += tempSize;
        }
        System.out.println("totalSize: " + totalSize);
    }

}
