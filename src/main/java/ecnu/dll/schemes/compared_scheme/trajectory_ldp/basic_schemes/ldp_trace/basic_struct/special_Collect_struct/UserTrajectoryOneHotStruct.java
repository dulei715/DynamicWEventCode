package ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct;

import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.special_Collect_struct.sub_struct.CellNeighboring;

import java.util.List;

public class UserTrajectoryOneHotStruct {
    public OneHot<Integer> trajectoryLength;
    public List<OneHot<CellNeighboring>> cellNeighboringList;
    public OneHot<TwoDimensionalIntegerPoint> startIndex;
    public OneHot<TwoDimensionalIntegerPoint> endIndex;

    public UserTrajectoryOneHotStruct() {
    }

    public UserTrajectoryOneHotStruct(OneHot<Integer> trajectoryLength, List<OneHot<CellNeighboring>> cellNeighboringList, OneHot<TwoDimensionalIntegerPoint> startIndex, OneHot<TwoDimensionalIntegerPoint> endIndex) {
        this.trajectoryLength = trajectoryLength;
        this.cellNeighboringList = cellNeighboringList;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public OneHot<Integer> getTrajectoryLength() {
        return trajectoryLength;
    }

    public void setTrajectoryLength(OneHot<Integer> trajectoryLength) {
        this.trajectoryLength = trajectoryLength;
    }



    public OneHot<TwoDimensionalIntegerPoint> getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(OneHot<TwoDimensionalIntegerPoint> startIndex) {
        this.startIndex = startIndex;
    }

    public OneHot<TwoDimensionalIntegerPoint> getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(OneHot<TwoDimensionalIntegerPoint> endIndex) {
        this.endIndex = endIndex;
    }

    public List<OneHot<CellNeighboring>> getCellNeighboringList() {
        return cellNeighboringList;
    }

    public void setCellNeighboringList(List<OneHot<CellNeighboring>> cellNeighboringList) {
        this.cellNeighboringList = cellNeighboringList;
    }
}
