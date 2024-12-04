package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.sub_struct.CellNeighboring;

public class CellNeighboringOneHotUtils {
    private static final TwoDimensionalIntegerPoint nullPoint = new TwoDimensionalIntegerPoint(-1, -1);
    public static TwoDimensionalIntegerPoint getNullNeighboringPoint() {
        return nullPoint;
    }
    public static boolean isNullPoint(TwoDimensionalIntegerPoint point) {
        if (point == null || (point.getXIndex() == -1 && point.getYIndex() == -1)) {
            return true;
        }
        return false;
    }
    /*
        当 a = b = n 时
        CellNeighboring 可以分为三类：
            1. 四个角(4个元素)：邻居各占3个单元
            2. 四条边(4n-8个元素)：邻居各占5个单元
            3. 内部节点(n^2-4n+4个元素)：邻居各占8个单元
         因此总共需要 12+20n-40+8n^2-32n+32 = 8n^2-12n+4个单元
         OneHot设定为：
            1. 12个角单元
            2. 20n-40个边单元
            3. 8n^2-32n+32个内部单元

         当 a不等于b时
         CellNeighboring 可以分为三类：
            1. 四个角(4个元素)：邻居各占3个单元
            2. 四条边(2a+2b-8个元素)：邻居各占5个单元
            3. 内部节点(a*b-2a-2b+4个元素)：邻居各占8个单元
         因此总共需要 12+10a+10b-40+8ab-16a-16b+32 = 8ab-6a-6b+4个单元
         OneHot设定为：
            1. 12个角单元
            2. 10a+10b-40个边单元
            3. 8ab-16a-16b+32个内部单元

     */

    /**
     * 这里的xIndex为rowIndex，x周向下，y轴向右
     * @param cellNeighboring
     * @return
     */
    public static int[] toOneHotDataIndexRange(CellNeighboring cellNeighboring) {
        int type = cellNeighboring.getType();
        TwoDimensionalIntegerPoint cellValue = cellNeighboring.getCellIndex();
        int[] oneHotIndex = new int[2];
        int rowIndex = cellValue.getXIndex();
        int colIndex = cellValue.getYIndex();
        int rowSize = cellNeighboring.getRowSize();
        int colSize = cellNeighboring.getColSize();
        int bias;
        switch (type) {
            case CellNeighboring.Angle:
                oneHotIndex[0] = (rowIndex == 0 ? 0 : 1) * 6 + (colIndex == 0 ? 0 : 1) * 3;
                oneHotIndex[1] = oneHotIndex[0] + 2;
                break;
            case CellNeighboring.Edge:
                bias = 12;
                if (rowIndex == 0) {
                    oneHotIndex[0] = bias + (colIndex - 1) * 5;
                } else if (colIndex == 0) {
                    oneHotIndex[0] = bias + (colSize-2) * 5 + (rowIndex - 1) * 5;
                } else if (colIndex > 0) {
                    oneHotIndex[0] = bias + (colSize + rowSize - 4) * 5 + (rowIndex - 1) * 5;
                } else {
                    oneHotIndex[0] = bias + (colSize + rowSize * 2 - 6) * 5 + (colIndex - 1) * 5;
                }
                oneHotIndex[1] = oneHotIndex[0] + 4;
                break;
            case CellNeighboring.Inner:
                bias = (rowSize + colSize) * 10 - 28;
                oneHotIndex[0] = bias + (rowIndex - 1) * (colSize - 2) * 8 + (colIndex - 1) * 8;
                oneHotIndex[1] = oneHotIndex[0] + 7;
                break;
        }
        return oneHotIndex;
    }
    public static int[] toOneHotDataIndexRange(TwoDimensionalIntegerPoint cellValue, int rowSize, int colSize) {
        int type = CellNeighboringUtils.getType(rowSize, colSize, cellValue.getXIndex(), cellValue.getYIndex());
        int[] oneHotIndex = new int[2];
        int rowIndex = cellValue.getXIndex();
        int colIndex = cellValue.getYIndex();
        int bias;
        switch (type) {
            case CellNeighboring.Angle:
                oneHotIndex[0] = (rowIndex == 0 ? 0 : 1) * 6 + (colIndex == 0 ? 0 : 1) * 3;
                oneHotIndex[1] = oneHotIndex[0] + 2;
                break;
            case CellNeighboring.Edge:
                bias = 12;
                if (rowIndex == 0) {
                    oneHotIndex[0] = bias + (colIndex - 1) * 5;
                } else if (colIndex == 0) {
                    oneHotIndex[0] = bias + (colSize-2) * 5 + (rowIndex - 1) * 5;
                } else if (colIndex > 0) {
                    oneHotIndex[0] = bias + (colSize + rowSize - 4) * 5 + (rowIndex - 1) * 5;
                } else {
                    oneHotIndex[0] = bias + (colSize + rowSize * 2 - 6) * 5 + (colIndex - 1) * 5;
                }
                oneHotIndex[1] = oneHotIndex[0] + 4;
                break;
            case CellNeighboring.Inner:
                bias = (rowSize + colSize) * 10 - 28;
                oneHotIndex[0] = bias + (rowIndex - 1) * (colSize - 2) * 8 + (colIndex - 1) * 8;
                oneHotIndex[1] = oneHotIndex[0] + 7;
                break;
        }
        return oneHotIndex;
    }

    /**
     * 返回邻居在数组中的位置，如果没找(邻居为自己，或者邻居越界)到返回-1()
     * @param cellNeighboring
     * @return
     */
    protected static int getBias(CellNeighboring cellNeighboring) {
        TwoDimensionalIntegerPoint[][] cellNeighboringIndex = cellNeighboring.getCellNeighboringIndex();
        int[] directNeighboringInnerIndex = cellNeighboring.getDirectNeighboringInnerIndex();
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1 || cellNeighboringIndex[i][j] == null) {
                    continue;
                }
                if (1 + directNeighboringInnerIndex[0] == i && 1 + directNeighboringInnerIndex[1] == j) {
                    return k;
                }
                ++k;
            }
        }
        return -1;
    }

    public static int toOneHotIndex(CellNeighboring cellNeighboring) {
        int[] oneHotDataIndex = toOneHotDataIndexRange(cellNeighboring);
        int bias = getBias(cellNeighboring);
        if (bias < 0) {
            // 非法的便宜（没在neighboring记录，比如：自身节点）
            return -1;
        }
        return oneHotDataIndex[0] + bias;
    }

    public static int[] getTwoDimensionalIndexByBias(CellNeighboring cellNeighboring, int bias) {
        TwoDimensionalIntegerPoint[][] cellNeighboringIndex = cellNeighboring.getCellNeighboringIndex();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (i == 1 && j == 1 || cellNeighboringIndex[i][j] == null) {
                    continue;
                }
                if (bias == 0) {
                    return new int[]{i, j};
                }
                --bias;
            }
        }
        throw new RuntimeException("Not found right bias!");
    }
    public static int[] getTwoDimensionalIndexByBias(int rowSize, int colSize, int rowIndex, int colIndex, int bias) {
        TwoDimensionalIntegerPoint[][] cellNeighboringIndex = CellNeighboringUtils.getCellNeighboringIndex(rowSize, colSize, rowIndex, colIndex);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (i == 1 && j == 1 || cellNeighboringIndex[i][j] == null) {
                    continue;
                }
                if (bias == 0) {
                    return new int[]{i, j};
                }
                --bias;
            }
        }
        throw new RuntimeException("Not found right bias!");
    }



    public static CellNeighboring toOriginalData(int index, int rowSize, int colSize) {
        int valueXIndex, valueYIndex, bias, typeBias;
        if (index < 12) {
            typeBias = index;
            valueXIndex = typeBias / 6 == 0 ? 0 : rowSize - 1;
            valueYIndex = typeBias % 6 < 3 ? 0 : colSize - 1;
            bias = typeBias % 3;
        } else if (index < 10*rowSize+10*colSize-28) {
            typeBias = index - 12;
            if (typeBias < (colSize - 2) * 5) {
                valueXIndex = 0;
                valueYIndex = 1 + typeBias / 5;
            } else if (typeBias < (colSize + rowSize - 4) * 5) {
                valueXIndex = 1 + typeBias / 5 - (colSize - 2);
                valueYIndex = 0;
            } else if (typeBias < (colSize + 2*rowSize - 6) * 5) {
                valueXIndex = 1 + typeBias / 5 - (colSize + rowSize - 4);
                valueYIndex = colSize - 1;
            } else {
                valueXIndex = rowSize - 1;
                valueYIndex = 1 + typeBias / 5 - (colSize + 2*rowSize - 6);
            }
            bias = typeBias % 5;
        } else {
            typeBias = index - 10*rowSize-10*colSize+28;
            valueXIndex = 1 + typeBias / 8 / colSize;
            valueYIndex = 1 + typeBias / 8 % colSize;
            bias = typeBias % 8;
        }
        return new CellNeighboring(rowSize, colSize, valueXIndex, valueYIndex, bias);
    }
    public static TwoDimensionalIntegerPoint toOriginalNeighboringData(int index, int rowSize, int colSize) {
        int valueXIndex, valueYIndex, bias, typeBias;
        if (index < 12) {
            typeBias = index;
            valueXIndex = typeBias / 6 == 0 ? 0 : rowSize - 1;
            valueYIndex = typeBias % 6 < 3 ? 0 : colSize - 1;
            bias = typeBias % 3;
        } else if (index < 10*rowSize+10*colSize-28) {
            typeBias = index - 12;
            if (typeBias < (colSize - 2) * 5) {
                valueXIndex = 0;
                valueYIndex = 1 + typeBias / 5;
            } else if (typeBias < (colSize + rowSize - 4) * 5) {
                valueXIndex = 1 + typeBias / 5 - (colSize - 2);
                valueYIndex = 0;
            } else if (typeBias < (colSize + 2*rowSize - 6) * 5) {
                valueXIndex = 1 + typeBias / 5 - (colSize + rowSize - 4);
                valueYIndex = colSize - 1;
            } else {
                valueXIndex = rowSize - 1;
                valueYIndex = 1 + typeBias / 5 - (colSize + 2*rowSize - 6);
            }
            bias = typeBias % 5;
        } else {
            typeBias = index - 10*rowSize-10*colSize+28;
            valueXIndex = 1 + typeBias / 8 / (colSize - 2);
            valueYIndex = 1 + typeBias / 8 % (colSize - 2);
            bias = typeBias % 8;
        }
//        if (valueYIndex == colSize || valueXIndex == rowSize) {
//            System.out.println("haha");
//        }
        int[] directInnerIndex = getTwoDimensionalIndexByBias(rowSize, colSize, valueXIndex, valueYIndex, bias);
        return new TwoDimensionalIntegerPoint(valueXIndex + directInnerIndex[0] - 1, valueYIndex + directInnerIndex[1] - 1);
    }

    public static void main(String[] args) {

    }
}
