package ecnu.dll.run._pre_process.b_parameter_pre_process.version_5_dim_ablation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PositionGroupUtils {
    public static Map<String, String> toGroup(Set<String> positionSet, Integer shareSize) {
        int positionSize = positionSet.size();
        if (positionSize < shareSize) {
            throw new RuntimeException("The size of positionSet is less than shareSize!");
        }
        Map<String, String> resultMap = new HashMap<>(positionSize);
        String[] positionArray = positionSet.toArray(new String[0]);
        for (int i = 0; i < positionSize; i++) {
            resultMap.put(positionArray[i], positionArray[i % shareSize]);
        }
        return resultMap;
    }

}
