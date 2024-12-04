package ecnu.dll.schemes.compared_scheme.trajectory.anchor_based_pivot_sampling.basic_struct;

import cn.edu.dll.geometry.Line;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction.schemes.compared_schemes.trajectory.anchor_based_pivot_sampling.utils.SectorAreasUtils;

import java.util.List;

public class SectorAreas {
    private TwoDimensionalDoublePoint pivotPoint;
    private TwoDimensionalDoublePoint targetPoint;
    private Integer sectorSize;
    private List<Line> sectorBorderSortedLineList;
    private List<BasicPair<Integer, Integer>> areaList;
    private Integer targetPointExistingAreaIndex;

    public SectorAreas(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, Integer sectorSize) {
        this.pivotPoint = pivotPoint;
        this.targetPoint = targetPoint;
        this.sectorSize = sectorSize;
        // 要求line从targetPoint所在上边界开始按照方位角大小排序，到最大后下一个回到最小
        this.sectorBorderSortedLineList = SectorAreasUtils.getSeparateSortedSectorList(pivotPoint, targetPoint, sectorSize);
        this.areaList = SectorAreasUtils.getSortedSeparateAreaStatusList(sectorSize / 2);
        this.targetPointExistingAreaIndex = SectorAreasUtils.getSearchAreaIndex(this, this.targetPoint);
    }



    public TwoDimensionalDoublePoint getPivotPoint() {
        return pivotPoint;
    }

    public TwoDimensionalDoublePoint getTargetPoint() {
        return targetPoint;
    }

    public Integer getSectorSize() {
        return sectorSize;
    }

    public List<Line> getSectorBorderSortedLineList() {
        return sectorBorderSortedLineList;
    }

    public List<BasicPair<Integer, Integer>> getAreaList() {
        return areaList;
    }

    public Integer getTargetPointExistingAreaIndex() {
        return targetPointExistingAreaIndex;
    }
}
