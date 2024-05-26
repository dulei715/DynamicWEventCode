package ecnu.dll.schemes._scheme_utils.nullified;

import cn.edu.dll.collection.ListUtils;

import java.util.List;

public class AverageNullifiedBound extends NullifiedBound {
    @Override
    public Double getNullifiedBound(List<Double> nullifiedTimestampList) {
        Double sum = ListUtils.sum(nullifiedTimestampList);
        return sum / nullifiedTimestampList.size();
    }
}
