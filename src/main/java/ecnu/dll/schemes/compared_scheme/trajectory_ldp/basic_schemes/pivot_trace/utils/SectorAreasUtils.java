package ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.pivot_trace.utils;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.geometry.Line;
import cn.edu.dll.geometry.LineUtils;
import cn.edu.dll.struct.pair.BasicPair;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.pivot_trace.basic_struct.SectorAreas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectorAreasUtils {

    public static List<Line> getSeparateSortedSectorList(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, int sectorSize) {
        Line[] resultLineArray = new Line[sectorSize / 2];
        double pivotPointX = pivotPoint.getXIndex(), pivotPointY = pivotPoint.getYIndex();
        double targetPointX = targetPoint.getXIndex(), targetPointY = targetPoint.getYIndex();
        Line basicLine = new Line(pivotPointX, pivotPointY, targetPointX, targetPointY);
        double angle = 2 * Math.PI / sectorSize;
        Line tempLine = LineUtils.getRoll(basicLine, pivotPointX, pivotPointY, angle / 2);
        resultLineArray[0] = tempLine;
        for (int i = 1; i < sectorSize / 2; ++i) {
            tempLine = LineUtils.getRoll(tempLine, pivotPointX, pivotPointY, angle);
            resultLineArray[i] = tempLine;
        }
        Arrays.sort(resultLineArray);
        return Arrays.asList(resultLineArray);
    }

    /**
     * 针对排序好的直线组，返回其排序区域的直线一般式的值
     * @param lineSize
     * @return
     */
    public static List<BasicPair<Integer,Integer>> getSortedSeparateAreaStatusList(int lineSize) {
        List<BasicPair<Integer, Integer>> resultList = new ArrayList<>(lineSize * 2);
        // 处理第四象限到第一象限的区域
        // 处理一二象限
        resultList.add(new BasicPair<>(1, 1));
        for (int i = 1; i < lineSize; ++i) {
            resultList.add(new BasicPair<>(-1, 1));
        }
        // 处理第二象限到第三象限
        resultList.add(new BasicPair<>(-1, -1));
        for (int i = 1; i < lineSize; ++i) {
            resultList.add(new BasicPair<>(1, -1));
        }
        return resultList;
    }

    /**
     * 判断所给的点point是否在sectorAreas的第areaIndex区域内
     * @param sectorAreas
     * @param areaIndex
     * @param point
     * @return
     */
    public static boolean isInArea(SectorAreas sectorAreas, int areaIndex, TwoDimensionalDoublePoint point) {
        TwoDimensionalDoublePoint pivotPoint = sectorAreas.getPivotPoint();
        double pointX = point.getXIndex();
        double pointY = point.getYIndex();
        List<Line> sortedBorderLineList = sectorAreas.getSectorBorderSortedLineList();

        if (areaIndex >= sectorAreas.getAreaList().size()) {
            // 增强
            return false;
        }

        BasicPair<Integer, Integer> areaStatus = sectorAreas.getAreaList().get(areaIndex);
        int[] lineIndexes = SectorAreasUtils.fromAreaIndexToLineIndexes(areaIndex, sortedBorderLineList.size());
        Line lineLeft = sortedBorderLineList.get(lineIndexes[0]);
        Line lineRight = sortedBorderLineList.get(lineIndexes[1]);

        double leftLineValue = lineLeft.getLineValue(pointX, pointY);
        double rightLineValue = lineRight.getLineValue(pointX, pointY);
        if (leftLineValue * areaStatus.getKey() >= 0 && rightLineValue * areaStatus.getValue() > 0) {
            /*
                保证line value 和 对应的区间状态同号
                两直线所夹区域规定含左线不含右线
                中心点始终返回false（pivot和target重合或point和pivot重合）
             */
            return true;
        }
        return false;
    }

    public static int[] fromAreaIndexToLineIndexes(int areaIndex, int lineSize) {
        int[] indexes = new int[2];
        indexes[0] = (areaIndex + lineSize - 1) % lineSize;
        indexes[1] = areaIndex % lineSize;
        return indexes;
    }

    public static int getSearchAreaIndex(SectorAreas sectorAreas, TwoDimensionalDoublePoint point) {
        int areaSize = sectorAreas.getAreaList().size();
        for (int i = 0; i < areaSize; ++i) {
            if (isInArea(sectorAreas, i, point)) {
                return i;
            }
        }
        /**
         * 接下来这种情况下target point和pivot point重合致使直线表达式为(0,0)或point和pivot point重合，导致不在任何区域
         * 这种情况下随机返回
         */
//        throw new RuntimeException("Not found area index! It's impossible!");
        return RandomUtil.getRandomInteger(0, areaSize - 1);
    }


//    public static BasicPair<Integer, Integer> getAreaStatus(Line leftLine, Line rightLine) {
////        if ()
//    }
}
