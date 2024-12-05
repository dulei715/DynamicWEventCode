package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.special_Collect_struct.sub_struct;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils.CellNeighboringOneHotUtils;
import ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils.CellNeighboringUtils;

public class CellNeighboring {
    public static final int Angle = 0;
    public static final int Edge = 1;
    public static final int Inner = 2;
    public static final int[] LeftTop = new int[]{0, 0};
    public static final int[] Top = new int[]{0, 1};
    public static final int[] RightTop = new int[]{0, 2};
    public static final int[] Left = new int[]{1, 0};
    public static final int[] Right = new int[]{1, 2};
    public static final int[] LeftBottom = new int[]{2, 0};
    public static final int[] Bottom = new int[]{2, 1};
    public static final int[] RightBottom = new int[]{2, 2};

    private int type;
    private int rowSize;
    private int colSize;
    private TwoDimensionalIntegerPoint[][] cellNeighboringIndex;

    // 记录方向的偏离位置，x，y坐标取值分别为0到2，
    private int[] directNeighboringInnerIndex;

    private CellNeighboring() {}

    private static final CellNeighboring nullCell;
    static {
        nullCell = new CellNeighboring();
        nullCell.rowSize = -1;
        nullCell.colSize = -1;
        nullCell.type = -1;
        nullCell.cellNeighboringIndex = null;
        nullCell.directNeighboringInnerIndex = null;
    }

    private CellNeighboring(int rowSize, int colSize, int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= rowSize || colIndex < 0 || colIndex >= colSize) {
            throw new RuntimeException("Initializing error: out of bound!");
        }
        this.rowSize = rowSize;
        this.colSize = colSize;

        this.type = CellNeighboringUtils.getType(rowSize, colSize, rowIndex, colIndex);

        this.cellNeighboringIndex = CellNeighboringUtils.getCellNeighboringIndex(rowSize, colSize, rowIndex, colIndex);

    }




    public CellNeighboring(int rowSize, int colSize, int rowIndex, int colIndex, int[] directNeighboringInnerIndex) {
        this(rowSize, colSize, rowIndex, colIndex);
        this.directNeighboringInnerIndex = directNeighboringInnerIndex;
        if (this.cellNeighboringIndex[directNeighboringInnerIndex[0]][directNeighboringInnerIndex[1]] == null) {
            throw new RuntimeException("The given neighboring is error!");
        }
    }



    public CellNeighboring(int rowSize, int colSize, int rowIndex, int colIndex, int bias) {
        this(rowSize, colSize, rowIndex, colIndex);
        this.directNeighboringInnerIndex = CellNeighboringOneHotUtils.getTwoDimensionalIndexByBias(this, bias);
        if (this.cellNeighboringIndex[directNeighboringInnerIndex[0]][directNeighboringInnerIndex[1]] == null) {
            throw new RuntimeException("The given neighboring is error!");
        }
    }

    public static CellNeighboring getNullCell() {
        return nullCell;
    }

    public static boolean isNullCell(CellNeighboring cellNeighboring) {
        /*
            nullCell = new CellNeighboring();
            nullCell.rowSize = -1;
            nullCell.colSize = -1;
            nullCell.type = -1;
            nullCell.cellNeighboringIndex = null;
            nullCell.directNeighboringInnerIndex = null;
         */
        if (cellNeighboring == nullCell || (cellNeighboring.rowSize == -1 && cellNeighboring.colSize == -1 && cellNeighboring.type == -1)) {
            return true;
        }
        return false;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public TwoDimensionalIntegerPoint getCellIndex() {
        return cellNeighboringIndex[1][1];
    }

    public TwoDimensionalIntegerPoint[][] getCellNeighboringIndex() {
        return this.cellNeighboringIndex;
    }

    public int getType() {
        return this.type;
    }

    public int[] getDirectNeighboringInnerIndex() {
        return directNeighboringInnerIndex;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, j; i < cellNeighboringIndex.length; i++) {
            for (j = 0; j < cellNeighboringIndex[0].length - 1; j++) {
                if (cellNeighboringIndex[i][j] != null) {
                    stringBuilder.append("(").append(cellNeighboringIndex[i][j].getXIndex()).append(", ").append(cellNeighboringIndex[i][j].getYIndex()).append("), ");
                } else {
                    stringBuilder.append("null, ");
                }
            }
            if (cellNeighboringIndex[i][j] != null) {
                stringBuilder.append("(").append(cellNeighboringIndex[i][j].getXIndex()).append(", ").append(cellNeighboringIndex[i][j].getYIndex()).append(")").append(ConstantValues.LINE_SPLIT);
            } else {
                stringBuilder.append("null").append(ConstantValues.LINE_SPLIT);
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        int[] neighboringDirect = LeftBottom;
        CellNeighboring cellNeighboring = new CellNeighboring(5, 5, 0, 1, neighboringDirect);
        TwoDimensionalIntegerPoint value = cellNeighboring.getCellIndex();
        System.out.println(value);
//        value.setXIndex(7);
//        System.out.println(value);
        System.out.println(cellNeighboring);

    }
}
