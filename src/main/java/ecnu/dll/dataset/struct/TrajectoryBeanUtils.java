package ecnu.dll.dataset.struct;

import cn.edu.dll.map.MapUtils;
import cn.edu.dll.struct.pair.IdentityPair;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TrajectoryBeanUtils {
    public static Map<String, String> getInfo(List<TrajectoryBean> beanList) {
        TreeMap<IdentityPair<Double>, Integer> treeMap = new TreeMap<>();
        IdentityPair<Double> tempKey;
        Double[] tempPosition;
        Integer tempSize;
        Double minLongitude = Double.MAX_VALUE, maxLongitude = -1D, minLatitude = Double.MAX_VALUE, maxLatitude = -1D;
        for (TrajectoryBean trajectoryBean : beanList) {
            tempPosition = trajectoryBean.getPosition();
            minLongitude = Math.min(minLongitude, tempPosition[0]);
            maxLongitude = Math.max(maxLongitude, tempPosition[0]);
            minLatitude = Math.min(minLatitude, tempPosition[1]);
            maxLatitude = Math.max(maxLatitude, tempPosition[1]);

//            tempKey = new IdentityPair<>(tempPosition[0], tempPosition[1]);
//            tempSize = treeMap.getOrDefault(tempKey, 0);
//            ++tempSize;
//            treeMap.put(tempKey, tempSize);
        }
//        System.out.println(treeMap.size());
        System.out.println("minLongitude: " + minLongitude);
        System.out.println("maxLongitude: " + maxLongitude);
        System.out.println("minLatitude: " + minLatitude);
        System.out.println("maxLatitude: " + maxLatitude);
        return null;
    }

}
