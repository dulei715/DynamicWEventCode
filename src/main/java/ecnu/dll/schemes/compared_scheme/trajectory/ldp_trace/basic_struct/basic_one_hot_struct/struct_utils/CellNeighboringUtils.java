package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.sub_struct.CellNeighboring;

public class CellNeighboringUtils {
    public static TwoDimensionalIntegerPoint[][] getCellNeighboringIndex(int rowSize, int colSize, int rowIndex, int colIndex) {
        TwoDimensionalIntegerPoint[][] cellNeighboringIndex = new TwoDimensionalIntegerPoint[3][3];
        cellNeighboringIndex[1][1] = new TwoDimensionalIntegerPoint(rowIndex, colIndex);

        if (rowIndex - 1 >= 0) {
            cellNeighboringIndex[0][1] = new TwoDimensionalIntegerPoint(rowIndex -1, colIndex);
            if (colIndex - 1 >= 0) {
                cellNeighboringIndex[0][0] = new TwoDimensionalIntegerPoint(rowIndex -1, colIndex -1);
            }
            if (colIndex + 1 < colSize) {
                cellNeighboringIndex[0][2] = new TwoDimensionalIntegerPoint(rowIndex -1, colIndex +1);
            }
        }
        if (rowIndex + 1 < rowSize) {
            cellNeighboringIndex[2][1] = new TwoDimensionalIntegerPoint(rowIndex +1, colIndex);
            if (colIndex - 1 >= 0) {
                cellNeighboringIndex[2][0] = new TwoDimensionalIntegerPoint(rowIndex +1, colIndex -1);
            }
            if (colIndex + 1 < colSize) {
                cellNeighboringIndex[2][2] = new TwoDimensionalIntegerPoint(rowIndex +1, colIndex +1);
            }
        }

        if (colIndex - 1 >= 0) {
            cellNeighboringIndex[1][0] = new TwoDimensionalIntegerPoint(rowIndex, colIndex -1);
        }
        if (colIndex + 1 < colSize) {
            cellNeighboringIndex[1][2] = new TwoDimensionalIntegerPoint(rowIndex, colIndex +1);
        }

        return cellNeighboringIndex;
    }

    public static int getType(int rowSize, int colSize, int rowIndex, int colIndex) {
        int type;
        if ((rowIndex == 0 || rowIndex == rowSize -1) && (colIndex == 0 || colIndex == colSize -1)) {
            type = CellNeighboring.Angle;
        } else if (rowIndex == 0 || rowIndex == rowSize -1 || colIndex == 0 || colIndex == colSize -1) {
            type = CellNeighboring.Edge;
        } else {
            type = CellNeighboring.Inner;
        }
        return type;
    }

    /**
     * 返回nextPoint在originalPoint邻域矩阵的坐标
     * @param nextPoint
     * @return
     */
    public static int[] getDirectNeighboringInnerIndex(TwoDimensionalIntegerPoint originalPoint, TwoDimensionalIntegerPoint nextPoint) {
        Integer xIndexDiffer = nextPoint.getXIndex() - originalPoint.getXIndex();
        if (Math.abs(xIndexDiffer) > 1) {
            return null;
        }
        Integer yIndexDiffer = nextPoint.getYIndex() - originalPoint.getYIndex();
        if (Math.abs(yIndexDiffer) > 1) {
            return null;
        }
        // 这个结果可能返回(1,1)，代表重合
        return new int[]{1 + xIndexDiffer, 1 + yIndexDiffer};
    }
}
