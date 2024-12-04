package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

public class AbsolutePositionOneHotUtils {
    public static int toOneHotDataIndex(TwoDimensionalIntegerPoint point, int colSize) {
        return point.getXIndex() * colSize + point.getYIndex();
    }

    public static TwoDimensionalIntegerPoint toOriginalData(int index, int colSize) {
        int xIndex = index / colSize;
        int yIndex = index % colSize;
        return new TwoDimensionalIntegerPoint(xIndex, yIndex);
    }
}
