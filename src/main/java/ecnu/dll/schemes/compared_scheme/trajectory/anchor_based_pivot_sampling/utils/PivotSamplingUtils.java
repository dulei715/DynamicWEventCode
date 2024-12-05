package ecnu.dll.schemes.compared_scheme.trajectory.anchor_based_pivot_sampling.utils;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.geometry.Line;
import cn.edu.dll.geometry.LineUtils;
import cn.edu.dll.struct.pair.IdentityPair;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.schemes.compared_scheme.trajectory.anchor_based_pivot_sampling.basic_struct.SectorAreas;

import java.util.*;

public class PivotSamplingUtils {

    /**
     * 给定枢轴点，目标点和一个角度，返回以枢轴点与目标点所在直线为角平分线的该角度的两个边所在直线
     * @param pivotPoint
     * @param targetPoint
     * @param unitAngle
     * @return
     */
    protected static Line[] getBorderLine(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, double unitAngle) {
        double pivotPointX = pivotPoint.getXIndex(), pivotPointY = pivotPoint.getYIndex();
        double targetPointX = targetPoint.getXIndex(), targetPointY = targetPoint.getYIndex();
        Line directionLine = new Line(pivotPointX, pivotPointY, targetPointX, targetPointY);
        Line lowBoundLine = LineUtils.getRoll(directionLine, pivotPointX, pivotPointY, -unitAngle / 2);
        Line highBoundLine = LineUtils.getRoll(directionLine, pivotPointX, pivotPointY, unitAngle / 2);
        return new Line[]{lowBoundLine, highBoundLine};
    }


    @Deprecated
    public static Set<TwoDimensionalDoublePoint> getPointSet_before(List<TwoDimensionalDoublePoint> totalPointSet, TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, int sectorSize, int perturbedAreaIndex) {
        SectorAreas sectorAreas = new SectorAreas(pivotPoint, targetPoint, sectorSize);
//        int targetPointAreaIndex = sectorAreas.getTargetPointExistingAreaIndex();
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        for (TwoDimensionalDoublePoint point : totalPointSet) {
            if (SectorAreasUtils.isInArea(sectorAreas, perturbedAreaIndex, point)) {
                resultSet.add(point);
            }
        }
        return resultSet;
    }

    public static Set<TwoDimensionalDoublePoint> getPointSet(List<TwoDimensionalDoublePoint> totalPointSet, SectorAreas sectorAreas, int perturbedAreaIndex) {
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        for (TwoDimensionalDoublePoint point : totalPointSet) {
            if (SectorAreasUtils.isInArea(sectorAreas, perturbedAreaIndex, point)) {
                resultSet.add(point);
            }
        }
        return resultSet;
    }


    @Deprecated
    public static Set<TwoDimensionalDoublePoint> getPointIntersectionSet(List<TwoDimensionalDoublePoint> totalPointSet, TwoDimensionalDoublePoint pivotPointA, TwoDimensionalDoublePoint pivotPointB, TwoDimensionalDoublePoint targetPoint, int sectorSize, int perturbedAreaIndexA, int perturbedAreaIndexB) {
        SectorAreas sectorAreasA = new SectorAreas(pivotPointA, targetPoint, sectorSize);
        SectorAreas sectorAreasB = new SectorAreas(pivotPointB, targetPoint, sectorSize);
//        int targetPointAreaIndex = sectorAreas.getTargetPointExistingAreaIndex();
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        for (TwoDimensionalDoublePoint point : totalPointSet) {
            if (SectorAreasUtils.isInArea(sectorAreasA, perturbedAreaIndexA, point) && SectorAreasUtils.isInArea(sectorAreasB, perturbedAreaIndexB, point)) {
                resultSet.add(point);
            }
        }
        return resultSet;
    }

    public static Set<TwoDimensionalDoublePoint> getPointIntersectionSet(List<TwoDimensionalDoublePoint> totalPointSet, SectorAreas sectorAreasA, SectorAreas sectorAreasB, int perturbedAreaIndexA, int perturbedAreaIndexB) {
        Set<TwoDimensionalDoublePoint> resultSet = new HashSet<>();
        for (TwoDimensionalDoublePoint point : totalPointSet) {
            if (SectorAreasUtils.isInArea(sectorAreasA, perturbedAreaIndexA, point) && SectorAreasUtils.isInArea(sectorAreasB, perturbedAreaIndexB, point)) {
                resultSet.add(point);
            }
        }
        return resultSet;
    }

    private static Double getPhiValue(Integer dI, Double thetaJ, Integer g) {
        double unitAngle = Math.PI / g;
        double[] dInterval = new double[]{(2 * dI - 1) * unitAngle, (2 * dI + 1) * unitAngle};
        double[] thetaJInterval = new double[]{-thetaJ, thetaJ};
        double[] intervalIntersection = BasicCalculation.getIntervalIntersection(dInterval, thetaJInterval);
        if (intervalIntersection == null) {
            return 0D;
        }
        return Math.abs(intervalIntersection[1] - intervalIntersection[0]) / (2 * Math.PI / g);
    }

    private static Double getLambdaValue(Integer dI, Integer d, Double epsilon, Integer g) {
        double tempValue = Math.exp(epsilon);
        if (dI.equals(d)) {
            return tempValue / (g - 1 + tempValue);
        }
        return 1.0 / (g - 1 + tempValue);
    }

    /**
     *
     * @param candidateSectorSizeList
     * @param privacyBudget
     * @param realDirectIndexList 其中每个元素对应candidateSectorSizeList中相应分区大小下的真实方向索引
     * @return
     */
    @Deprecated
    public static Integer getSingleDirectionOptimalSectorSize(List<Integer> candidateSectorSizeList, Double privacyBudget, List<Integer> realDirectIndexList) {
        Double goalValue = -1D, tempGoalValue;
        Integer resultG = null, tempG, tempDirectIndex;
        Collection<Double> bigTheta = new HashSet<>();
        for (Integer g : candidateSectorSizeList) {
            bigTheta.add(Math.PI / g);
        }
        for (int i = 0; i < candidateSectorSizeList.size(); ++i) {
            tempG = candidateSectorSizeList.get(i);
            tempDirectIndex = realDirectIndexList.get(i);
            tempGoalValue = getDirectionKeepValue(privacyBudget, tempG, tempDirectIndex, bigTheta);
            if (tempGoalValue > goalValue) {
                resultG = tempG;
                goalValue = tempGoalValue;
            }
        }
        return resultG;
    }

    private static Double getDirectionKeepValue(Double privacyBudget, Integer sectorSize, Integer realDirectIndex, Collection<Double> bigTheta) {
        Double tempGoalValue;
        Integer[] tempDIArray;
        Integer bigThetaSize = bigTheta.size();
        Double result = -1D, goalValue = -1D;
        tempDIArray = BasicArrayUtil.getIncreaseIntegerNumberArray(0, 1, sectorSize - 1);
        tempGoalValue = 0D;
        for (Double thetaJ : bigTheta) {
            for (Integer dI : tempDIArray) {
                tempGoalValue += getPhiValue(dI, thetaJ, sectorSize) * getLambdaValue(dI, realDirectIndex, privacyBudget, sectorSize) / bigThetaSize;
            }
        }
        return tempGoalValue;
    }

    public static Integer getOptimalSectorSize(List<Integer> candidateSectorSizeList, Double privacyBudget, List<TwoDimensionalDoublePoint> trajectory) {
        // 获取每个力度下的轨迹中每个方向的真实方向下标
        int candidateSize = candidateSectorSizeList.size();
        int directSize = trajectory.size() - 1, effectiveSize;
        Double tempTrajectoryDirectionKeepValue, goalValue = -1D;
        SectorAreas tempSectorAreas;
        Integer tempSectorSize, resultSectorSize = null, tempRealDirectIndex;
        Collection<Double> bigTheta = new HashSet<>();
        TwoDimensionalDoublePoint currentPoint, nextPoint;
        for (Integer g : candidateSectorSizeList) {
            bigTheta.add(Math.PI / g);
        }
        for (int i = 0; i < candidateSize; ++i) {
            tempSectorSize  = candidateSectorSizeList.get(i);
            /**
             * 这里做了优化，过滤掉了那些没方向的两点（即两点重合）
             */
            tempTrajectoryDirectionKeepValue = 0D;
            effectiveSize = 0;
            for (int j = 0; j < directSize; ++j) {
                currentPoint = trajectory.get(j);
                nextPoint = trajectory.get(j+1);
                if (currentPoint.equals(nextPoint)) {
                    continue;
                }
                ++effectiveSize;
                tempSectorAreas = new SectorAreas(trajectory.get(j), trajectory.get(j+1), tempSectorSize);
                tempRealDirectIndex = tempSectorAreas.getTargetPointExistingAreaIndex();
                tempTrajectoryDirectionKeepValue += getDirectionKeepValue(privacyBudget, tempSectorSize, tempRealDirectIndex, bigTheta);
            }
            // 这里取轨迹的所有方向的平均值为最终方向保持值
            if (effectiveSize > 0) {
                tempTrajectoryDirectionKeepValue /= effectiveSize;
            } else {
                // 如果所有的点都是同一个点，这里顶一个它的value为无效且最小值-1
                tempTrajectoryDirectionKeepValue = -1D;
            }

            if (tempTrajectoryDirectionKeepValue > goalValue) {
                goalValue = tempTrajectoryDirectionKeepValue;
                resultSectorSize = tempSectorSize;
            }
        }
        if (goalValue < 0) {
            // 所有的点都是孤立点，没构成轨迹。此时方向就随机选取
            return candidateSectorSizeList.get(RandomUtil.getRandomInteger(0, candidateSectorSizeList.size() - 1));
        }
        return resultSectorSize;
    }

    /**
     * 给定一个轨迹上的所有有效点，返回所有点的到其邻节点的SectorArea
     * @param trajectory
     * @param optimalSectorSize
     * @return
     */
    @Deprecated
    public static List<List<SectorAreas>> getNeighboringList_before(List<TwoDimensionalDoublePoint> trajectory, Integer optimalSectorSize) {
        TwoDimensionalDoublePoint currentPoint;
        List<SectorAreas> tempNeighboringList;
        int trajectorySize = trajectory.size();
        // 外层List每个位置对应trajectory上的一个点，内层List要么是一个元素要么是两个元素
        List<List<SectorAreas>> neighboringList = new ArrayList<>();
        for (int i = 0; i < trajectorySize; ++i) {
            currentPoint = trajectory.get(i);
            if (i == 0) {
                tempNeighboringList = new ArrayList<>(1);
                tempNeighboringList.add(new SectorAreas(currentPoint, trajectory.get(i + 1), optimalSectorSize));

            } else if (i == trajectorySize - 1) {
                tempNeighboringList = new ArrayList<>(1);
                tempNeighboringList.add(new SectorAreas(currentPoint, trajectory.get(i - 1), optimalSectorSize));
            } else {
                tempNeighboringList = new ArrayList<>(2);
                tempNeighboringList.add(new SectorAreas(currentPoint, trajectory.get(i - 1), optimalSectorSize));
                tempNeighboringList.add(new SectorAreas(currentPoint, trajectory.get(i + 1), optimalSectorSize));
            }
            neighboringList.add(tempNeighboringList);
        }
        return neighboringList;
    }
    /**
     * 给定一个轨迹上的所有有效点，返回所有点的到其邻节点的SectorArea
     * 这里用双重映射方便存取
     * IdentityPair<Integer>记录两个键key_1和key_2
     * 外层key_1代表以trajectory中第key个位置点为pivot点
     * key_2代表以trajectory中第key_2个点作为target点
     * currentPoint代表从pivot点到target点的sectorAreas
     * @param trajectory
     * @param optimalSectorSize
     * @return
     */
    public static Map<IdentityPair<Integer>, SectorAreas> getNeighboringList(List<TwoDimensionalDoublePoint> trajectory, Integer optimalSectorSize) {
        TwoDimensionalDoublePoint currentPoint;
        // 外层Map每个位置对应trajectory上的一个点，内层Map要么是一个元素要么是两个元素
        Map<IdentityPair<Integer>, SectorAreas> neighboringMap = new TreeMap<>();
        int trajectorySize = trajectory.size();
        for (int i = 0; i < trajectorySize; ++i) {
            currentPoint = trajectory.get(i);
            if (i > 0) {
                neighboringMap.put(new IdentityPair<>(i ,i - 1), new SectorAreas(currentPoint, trajectory.get(i - 1), optimalSectorSize));
            }
            if (i < trajectorySize - 1) {
                neighboringMap.put(new IdentityPair<>(i, i + 1), new SectorAreas(currentPoint, trajectory.get(i + 1), optimalSectorSize));
            }
        }
        return neighboringMap;
    }

    public static SectorAreas generateSectorAreas(TwoDimensionalDoublePoint pivotPoint, TwoDimensionalDoublePoint targetPoint, Integer optimalSectorSize) {
        return new SectorAreas(pivotPoint, targetPoint, optimalSectorSize);
    }

}
