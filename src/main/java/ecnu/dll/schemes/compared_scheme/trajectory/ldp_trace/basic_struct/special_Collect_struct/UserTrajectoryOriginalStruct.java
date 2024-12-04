package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.special_Collect_struct;

import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_Collect_struct.sub_struct.CellNeighboring;

import java.util.List;

public class UserTrajectoryOriginalStruct {
    public Integer trajectoryLength;
    public List<CellNeighboring> cellNeighboringList;
    public TwoDimensionalIntegerPoint startIndex;
    public TwoDimensionalIntegerPoint endIndex;


    public UserTrajectoryOriginalStruct(Integer trajectoryLength, List<CellNeighboring> cellNeighboringList, TwoDimensionalIntegerPoint startIndex, TwoDimensionalIntegerPoint endIndex) {
        this.trajectoryLength = trajectoryLength;
        this.cellNeighboringList = cellNeighboringList;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public Integer getTrajectoryLength() {
        return trajectoryLength;
    }

    public void setTrajectoryLength(Integer trajectoryLength) {
        this.trajectoryLength = trajectoryLength;
    }


    public TwoDimensionalIntegerPoint getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(TwoDimensionalIntegerPoint startIndex) {
        this.startIndex = startIndex;
    }

    public TwoDimensionalIntegerPoint getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(TwoDimensionalIntegerPoint endIndex) {
        this.endIndex = endIndex;
    }

    public List<CellNeighboring> getCellNeighboringList() {
        return cellNeighboringList;
    }

    public void setCellNeighboringList(List<CellNeighboring> cellNeighboringList) {
        this.cellNeighboringList = cellNeighboringList;
    }
}
