package ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.utils;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils.CellNeighboringUtils;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct.UserTrajectoryOriginalStruct;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct.sub_struct.CellNeighboring;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryFOUtils {
    // 设置长度相关的 epsilon_1 占总 epsilon 的 0.1
    public static final double lengthBudgetRatio = 0.1;
    // 设置内部状态相关的 epsilon_2 占 (epsilon-epsilon_1) 的 0.9
    public static final double innerCellBudgetRatio = 0.9;
    public static double[] getSplitPrivacyBudget(Double epsilon) {
        double[] result = new double[3];
        result[0] = epsilon * lengthBudgetRatio;
        result[1] = (epsilon - result[0]) * innerCellBudgetRatio;
        result[2] = epsilon - result[0] - result[1];
        return result;
    }


    public static UserTrajectoryOriginalStruct extractOriginalTraceInformation(List<TwoDimensionalIntegerPoint> gridTrajectory, Integer gridRowLength, Integer gridColLength) {
        Integer trajectoryLength = gridTrajectory.size();
        CellNeighboring tempCellNeighboring;
        TwoDimensionalIntegerPoint tempPoint, tempNextPoint;
        int[] relativePosition;
        List<CellNeighboring> cellNeighboringList = new ArrayList<>(trajectoryLength - 1);
        for (int i = 0; i < trajectoryLength - 1; ++i) {
            tempPoint = gridTrajectory.get(i);
            tempNextPoint = gridTrajectory.get(i+1);
            relativePosition = CellNeighboringUtils.getDirectNeighboringInnerIndex(tempPoint, tempNextPoint);
            if (relativePosition == null) {
                throw new RuntimeException(tempPoint + " and " + tempNextPoint + " are not neighboring!");
            }
            tempCellNeighboring = new CellNeighboring(gridRowLength, gridColLength, tempPoint.getXIndex(), tempPoint.getYIndex(), relativePosition);
            cellNeighboringList.add(tempCellNeighboring);
        }
        return new UserTrajectoryOriginalStruct(trajectoryLength, cellNeighboringList, gridTrajectory.get(0), gridTrajectory.get(trajectoryLength - 1));
    }


}
