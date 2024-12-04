package ecnu.dll.run._2_trajectory_run._0_pre_handle;

import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction.extend_tools.trajectory_io.TrajectoryRead;

import java.util.List;

public class TrajectoryRunUtils {
    public static List<List<TwoDimensionalDoublePoint>> loadDataSet(String dataSetPath) {
        TrajectoryRead trajectoryRead = new TrajectoryRead();
        trajectoryRead.startReading(dataSetPath);
        List<List<TwoDimensionalDoublePoint>> result = trajectoryRead.readAllTrajectoryAndSize();
        trajectoryRead.endReading();
        return result;
    }

}
