package ecnu.dll.run._2_trajectory_run._0_pre_handle;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.TwoDimensionalPointRead;
import cn.edu.dll.io.write.PointWrite;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.dataset.trajectory.TrajectorySampleTool;
import ecnu.dll.construction.extend_tools.trajectory_io.TrajectoryRead;
import ecnu.dll.construction.extend_tools.trajectory_io.TrajectoryWrite;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.TrajectoryCommonTool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SampleAndWriteTrajectory {

    private static final Integer bufferSize = 10;

    private static void sampleAndWriteTrajectoryWithGivenBuffer() {
        DataSetAreaInfo datasetInfo = Constant.nycDataSet;

        Integer trajectorySamplingLengthLowerBound = Constant.TrajectorySamplingLengthLowerBound;
        Integer trajectorySamplingLengthUpperBound = Constant.TrajectorySamplingLengthUpperBound;
        // 这里的值为10000
        Integer trajectorySamplingSize = Constant.TrajectorySamplingSize;
//        Integer trajectorySamplingSize = sampleSize;
        // 这里的值为50
        Integer sampleGridSideLength = Constant.SampleTrajectoryGridSideLength;
//        Integer sampleGridSideLength = 100;

        String dataSetPath = datasetInfo.getDataSetPath();
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, datasetInfo.getParentPath(), "nyc_trajectory.txt");
        System.out.println(dataSetPath);

        TrajectorySampleTool trajectorySampleTool = new TrajectorySampleTool(sampleGridSideLength);
        TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalDoublePoint> pointList = pointRead.getPointList();


        TrajectoryWrite trajectoryWrite = new TrajectoryWrite();
        trajectoryWrite.startWriting(outputPath);

        trajectoryWrite.writeDataSize(trajectorySamplingSize);

        List<List<TwoDimensionalDoublePoint>> trajectoryList;

//        int k = 0;

        for (int i = trajectorySamplingSize; i > 0; i -= bufferSize) {
            if (i > bufferSize) {
                trajectoryList= trajectorySampleTool.sampleTrajectory(pointList, trajectorySamplingLengthLowerBound, trajectorySamplingLengthUpperBound, bufferSize);
            } else {
                trajectoryList= trajectorySampleTool.sampleTrajectory(pointList, trajectorySamplingLengthLowerBound, trajectorySamplingLengthUpperBound, i);
            }
            trajectoryWrite.writeTrajectoryListWithoutWritingDataSize(trajectoryList);
//            System.out.println("Finish batch " + (k++) + " with size " + bufferSize);

//            trajectoryList = null;
//            System.gc();
        }

        trajectoryWrite.endWriting();
    }

    public static void generatePoiForPivotTrace(String originalTrajectoryPath, String outputPath) {
        // 这里用原始轨迹的点作为poi点
        TrajectoryRead trajectoryRead = new TrajectoryRead();
        trajectoryRead.startReading(originalTrajectoryPath);
        List<List<TwoDimensionalDoublePoint>> trajectoryList = trajectoryRead.readAllTrajectoryAndSize();
        trajectoryRead.endReading();
        List<TwoDimensionalDoublePoint> poi = TrajectoryCommonTool.fromDoubleTrajectoryListToDoublePointList(trajectoryList);
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        resultSet.addAll(poi);
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(poi);
        pointWrite.endWriting();
    }

    public static void main(String[] args) {
        // 这两步用来生成Poi
        String dataSetPath = Constant.nycTrajectoryDataSet.getDataSetPath();
        generatePoiForPivotTrace(dataSetPath, Constant.poiPath);

//        sampleAndWriteTrajectoryWithGivenBuffer();

    }
}
