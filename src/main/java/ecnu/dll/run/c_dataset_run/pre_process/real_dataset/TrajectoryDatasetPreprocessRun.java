package ecnu.dll.run.c_dataset_run.pre_process.real_dataset;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.dataset.real.datasetA.basic_struct.TrajectoryBean;

import java.io.File;
import java.util.List;

public class TrajectoryDatasetPreprocessRun {
//    public static void main(String[] args) {
//        System.out.println("hello");
//    }

    /**
     * 1. 抽取数据在经度[116,116.8]和纬度[39.5,40.3]之间的数据（和user_guide.pdf图像展示保持一致）
     */
    public static void extract() {

        String trajectoryDirectoryPath = Constant.trajectoriesFilePath;
        Double longitudeLeft = 116.0, longitudeRight = 116.8;
        Double latitudeLeft = 39.5, latitudeRight = 40.3;
        Integer longitudeShareSize = ConfigureUtils.getTrajectoryLongitudeSize();
        Integer latitudeShareSize = ConfigureUtils.getTrajectoryLatitudeSize();
        File filterFile = new File(trajectoryDirectoryPath, "taxi_log_2008_by_id_filter");
        File[] files = filterFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempList;
        TrajectoryBean tempBean;
        String outputDirectory = "extract_data";
        Double tempLongitude, tempLatitude;
        BasicWrite basicWrite = new BasicWrite(",");
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            if (tempList.isEmpty()) {
                continue;
            }
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryDirectoryPath, outputDirectory, file.getName()));
            for (String stringLine : tempList) {
                tempBean = TrajectoryBean.toTrajectoryBeanWithFormatTime(basicRead.split(stringLine));
                tempLongitude = tempBean.getLongitude();
                tempLatitude = tempBean.getLatitude();
                if (tempLongitude >= longitudeLeft && tempLongitude <= longitudeRight && tempLatitude >= latitudeLeft && tempLatitude <= latitudeRight) {
                    basicWrite.writeOneLine(StringUtil.join(",", tempBean.getUserID(), tempBean.getTimestamp(), tempLongitude, tempLatitude, tempBean.getRangeIndex(longitudeLeft, longitudeRight, longitudeShareSize, latitudeLeft, latitudeRight, latitudeShareSize)));
                }
            }
            basicWrite.endWriting();
        }
    }

    /**
     * 2. 将时间转成时间戳，同时将趋于划分为longitudeSplitSize*latitudeSplitSize，并用划分后的区域编号表示它们
     */
    private static int getIndex(double value, double unitValue) {
        double share = value / unitValue;
        return (int)Math.floor(share);
    }
    @Deprecated
    public static void transformToSimpleData() {
        Integer longitudeSplitSize = ConfigureUtils.getTrajectoryLongitudeSize();
        Integer latitudeSplitSize = ConfigureUtils.getTrajectoryLatitudeSize();
        double longitudeUnit = 255.3 / longitudeSplitSize;
        double latitudeUnit = 96.06767 / latitudeSplitSize;
        String trajectoryDirectoryPath = Constant.trajectoriesFilePath;
        File filterFile = new File(trajectoryDirectoryPath, "taxi_log_2008_by_id_filter");
        File[] files = filterFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> tempList;
        TrajectoryBean tempBean;
        String outputDirectory = "data_format";
        Double tempLongitude, tempLatitude;
        Integer tempLongitudeIndex, tempLatitudeIndex, tempAreaIndex;
        BasicWrite basicWrite = new BasicWrite(",");
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            tempList = basicRead.readAllWithoutLineNumberRecordInFile();
            basicRead.endReading();
            basicWrite.startWriting(StringUtil.join(ConstantValues.FILE_SPLIT, trajectoryDirectoryPath, outputDirectory, file.getName()));
            for (String lineString : tempList) {
                tempBean = TrajectoryBean.toTrajectoryBeanWithFormatTime(basicRead.split(lineString));
                tempLongitude = tempBean.getLongitude();
                tempLatitude = tempBean.getLatitude();
                tempLongitudeIndex = getIndex(tempLongitude, longitudeUnit);
                tempLatitudeIndex = getIndex(tempLatitude, latitudeUnit);
                tempAreaIndex = tempLongitudeIndex * latitudeSplitSize + tempLatitudeIndex;
                basicWrite.writeOneLine(StringUtil.join(",", tempBean.getUserID(), tempBean.getTimestamp(), tempAreaIndex));
            }
            basicWrite.endWriting();
        }
    }

    /**
     * 计算出最小和最大时间戳（用于方便时间分割），见test.important_test:testTimeSlotRange
     * 最小时间戳：1201930244000; 最大时间戳：1202463559000
     */


    /**
     * 3. 按照时间段重新划分各个文件，使得每个文件表示一个时间段，文件中记录该时间段用户的位置
     */

    /**
     * 4. 初始化用户最原始的位置，依次按照时间段文件更新用户状态，并记录每个时间段更新结束的状态
     */

    public static void splitByTime() {

    }

    public static void main(String[] args) {
        extract();
//        transformToSimpleData();
    }
}
