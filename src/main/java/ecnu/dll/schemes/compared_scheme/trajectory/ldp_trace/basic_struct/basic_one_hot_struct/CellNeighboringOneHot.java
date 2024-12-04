package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.basic_one_hot_struct;

import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.one_hot.OneHot;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils.CellNeighboringOneHotUtils;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.sub_struct.CellNeighboring;

public class CellNeighboringOneHot extends OneHot<CellNeighboring> {

    protected int rowSize;
    protected int colSize;

    public CellNeighboringOneHot(int rowSize, int colSize) {
        // 8ab-6a-6b+4个单元
        super(8*rowSize*colSize-6*(rowSize+colSize)+4);
        this.rowSize = rowSize;
        this.colSize = colSize;
    }

    protected CellNeighboringOneHot(boolean... booleans) {
        super(booleans);
    }

    @Override
    public OneHot<CellNeighboring> getInstance(boolean... booleans) {
        CellNeighboringOneHot newInstance =  new CellNeighboringOneHot(booleans);
        newInstance.rowSize = this.rowSize;
        newInstance.colSize = this.colSize;
        return newInstance;
    }





    @Override
    public void setElement(CellNeighboring cellNeighboring) {
        int index = CellNeighboringOneHotUtils.toOneHotIndex(cellNeighboring);
        if (index < 0) {
            // 过滤掉非法设置
            return;
        }
        this.data[index] = ONE;
    }

    public String toClassifiedString() {
        StringBuilder stringBuilder = new StringBuilder();

        int k = 0;
        for (int i = 0; i < 4; i++) {
            stringBuilder.append("(");
            for (int j = 0; j < 3; j++) {
                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 2 ? ", " : ")");
            }
            stringBuilder.append(", ");
            if (i == 3) {
                stringBuilder.append(ConstantValues.LINE_SPLIT);
            }
        }
        int tempCellSize = 2 * (this.rowSize + this.colSize) - 8;
        stringBuilder.append("[");
        for (int i = 0; i < this.colSize-2; ++i) {
            stringBuilder.append("(");
            for (int j = 0; j < 5; ++j) {
                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 4 ? ", " : ")");
            }
            if (i < this.colSize-3) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("], ");

        for (int i = 0; i < this.rowSize * 2 - 4; ++i) {
            if (i % (this.rowSize - 2) == 0) {
                stringBuilder.append("[");
            }
            stringBuilder.append("(");
            for (int j = 0; j < 5; ++j) {
                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 4 ? ", " : ")");
            }

            if ((i+1)%(this.rowSize-2)==0) {
                stringBuilder.append("], ");
            } else {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("[");
        for (int i = 0; i < this.colSize-2; ++i) {
            stringBuilder.append("(");
            for (int j = 0; j < 5; ++j) {
                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 4 ? ", " : ")");
            }
            if (i < this.colSize-3) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("], ");
        stringBuilder.append(ConstantValues.LINE_SPLIT);

//        for (int i = 0; i < tempCellSize; i++) {
//            if (i % (this.colSize - 2) == 0) {
//                stringBuilder.append("[");
//            }
//            stringBuilder.append("(");
//            for (int j = 0; j < 5; j++) {
//                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 4 ? ", " : ")");
//            }
//            if (i % (this.colSize - 2) == this.colSize - 3) {
//                stringBuilder.append("]");
//            }
//            stringBuilder.append(", ");
//            if (i == tempCellSize - 1) {
//                stringBuilder.append(ConstantValues.LINE_SPLIT);
//            }
//        }
        tempCellSize = (this.rowSize - 2) * (this.colSize - 2);
        for (int i = 0; i < tempCellSize; i++) {
            if (i % (this.colSize - 2) == 0) {
                stringBuilder.append("[");
            }
            stringBuilder.append("(");
            for (int j = 0; j < 8; j++) {
                stringBuilder.append(this.data[k++] ? "1" : "0").append(j < 7 ? ", " : ")");
            }
            if (i % (this.colSize - 2) == this.colSize - 3) {
                stringBuilder.append("]");
            }
            if (i < tempCellSize - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        int rowSize = 5;
        int colSize = 5;
        CellNeighboringOneHot cellNeighboringOneHot = new CellNeighboringOneHot(rowSize, colSize);
        System.out.println(cellNeighboringOneHot);
        MyPrint.showSplitLine("*", 150);

        int[] directNeighboring = CellNeighboring.Bottom;
        CellNeighboring cellNeighboring = new CellNeighboring(rowSize, colSize, 1, 1, directNeighboring);
        cellNeighboringOneHot.setElement(cellNeighboring);
        System.out.println(cellNeighboringOneHot.toClassifiedString());
    }
}
