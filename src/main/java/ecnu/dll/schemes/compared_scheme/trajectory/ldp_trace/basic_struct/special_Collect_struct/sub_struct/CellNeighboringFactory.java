package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.special_Collect_struct.sub_struct;

public class CellNeighboringFactory {
    private int rowSize;
    private int colSize;
    public CellNeighboringFactory(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
    }
    public CellNeighboring generateCellNeighboringObject(int rowIndex, int colIndex, int[] directNeighboring) {
        return new CellNeighboring(this.rowSize, this.colSize, rowIndex, colIndex, directNeighboring);
    }
}
