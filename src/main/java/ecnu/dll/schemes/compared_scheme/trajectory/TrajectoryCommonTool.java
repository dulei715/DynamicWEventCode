package ecnu.dll.schemes.compared_scheme.trajectory;

import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

import java.util.*;

public class TrajectoryCommonTool {


//    public static TreeMap<TwoDimensionalIntegerPoint, Integer> getCount(List<TwoDimensionalIntegerPoint> valueList, Integer rowSize, Integer colSize) {
//        TreeMap<TwoDimensionalIntegerPoint, Integer> result = new TreeMap<>();
//        for (int i = 0; i < rowSize; ++i) {
//            for (int j = 0; j < colSize; ++j) {
//                result.put(new TwoDimensionalIntegerPoint(i, j), 0);
//            }
//        }
//        for (TwoDimensionalIntegerPoint point : valueList) {
//            Integer size = result.get(point);
//            size += 1;
//            result.put(point, size);
//        }
//        return result;
//    }

    public static List<TwoDimensionalIntegerPoint> fromIntegerTrajectoryListToIntegerPointList(List<List<TwoDimensionalIntegerPoint>> trajectoryList) {
        List<TwoDimensionalIntegerPoint> result = new ArrayList<>();
        for (List<TwoDimensionalIntegerPoint> trajectory : trajectoryList) {
            for (TwoDimensionalIntegerPoint point : trajectory) {
                result.add(point);
            }
        }
        return result;
    }
    public static List<TwoDimensionalDoublePoint> fromDoubleTrajectoryListToDoublePointList(List<List<TwoDimensionalDoublePoint>> trajectoryList) {
        List<TwoDimensionalDoublePoint> result = new ArrayList<>();
        for (List<TwoDimensionalDoublePoint> trajectory : trajectoryList) {
            for (TwoDimensionalDoublePoint point : trajectory) {
                result.add(point);
            }
        }
        return result;
    }

    public static List<TwoDimensionalIntegerPoint> generateBasicGridPointList(int rowSize, int colSize) {
        List<TwoDimensionalIntegerPoint> result = new ArrayList<>();
        for (int i = 0; i < rowSize; ++i) {
            for (int j = 0; j < colSize; ++j) {
                result.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }
        return result;
    }

    public static TreeMap<TwoDimensionalIntegerPoint, Integer> countTrajectoryPoint(List<List<TwoDimensionalIntegerPoint>> trajectoryList, Integer rowSize, Integer colSize) {
        TreeMap<TwoDimensionalIntegerPoint, Integer> result = new TreeMap<>();
        for (int i = 0; i < rowSize; ++i) {
            for (int j = 0; j < colSize; ++j) {
                result.put(new TwoDimensionalIntegerPoint(i, j), 0);
            }
        }
        for (List<TwoDimensionalIntegerPoint> pointList : trajectoryList) {
            for (TwoDimensionalIntegerPoint point : pointList) {
                Integer size = result.get(point);
                size += 1;
                result.put(point, size);
            }
        }
        return result;
    }

    public static TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<List<TwoDimensionalIntegerPoint>> trajectoryList, Integer rowSize, Integer colSize) {
        TreeMap<TwoDimensionalIntegerPoint, Integer> countMap = new TreeMap<>();
        TreeMap<TwoDimensionalIntegerPoint, Double> resultMap = new TreeMap<>();
        for (int i = 0; i < rowSize; ++i) {
            for (int j = 0; j < colSize; ++j) {
                countMap.put(new TwoDimensionalIntegerPoint(i, j), 0);
            }
        }
        int totalSize = 0;
        for (List<TwoDimensionalIntegerPoint> pointList : trajectoryList) {
            for (TwoDimensionalIntegerPoint point : pointList) {
                Integer size = countMap.get(point);
                size += 1;
                countMap.put(point, size);
                ++totalSize;
            }
        }

        for (Map.Entry<TwoDimensionalIntegerPoint, Integer> entry : countMap.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue() * 1.0 / totalSize);
        }
        return resultMap;
    }
    public static TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<List<TwoDimensionalIntegerPoint>> trajectoryList, Collection<TwoDimensionalIntegerPoint> basicPointCollection) {
        TreeMap<TwoDimensionalIntegerPoint, Integer> countMap = new TreeMap<>();
        TreeMap<TwoDimensionalIntegerPoint, Double> resultMap = new TreeMap<>();
//        for (int i = 0; i < rowSize; ++i) {
//            for (int j = 0; j < colSize; ++j) {
//                countMap.put(new TwoDimensionalIntegerPoint(i, j), 0);
//            }
//        }
        for (TwoDimensionalIntegerPoint basicPoint : basicPointCollection) {
            countMap.put(basicPoint, 0);
        }
        int totalSize = 0;
        for (List<TwoDimensionalIntegerPoint> pointList : trajectoryList) {
            for (TwoDimensionalIntegerPoint point : pointList) {
                Integer size = countMap.get(point);
                size += 1;
                countMap.put(point, size);
                ++totalSize;
            }
        }

        for (Map.Entry<TwoDimensionalIntegerPoint, Integer> entry : countMap.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue() * 1.0 / totalSize);
        }
        return resultMap;
    }



}
