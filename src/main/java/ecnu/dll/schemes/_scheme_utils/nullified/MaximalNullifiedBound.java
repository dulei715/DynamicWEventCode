package ecnu.dll.schemes._scheme_utils.nullified;

import cn.edu.dll.collection.ListUtils;

import java.util.List;

public class MaximalNullifiedBound extends NullifiedBound {
    @Override
    public Double getNullifiedBound(List<Double> nullifiedTimestampList) {
        return ListUtils.getMaximalValue(nullifiedTimestampList, 0D);
    }
}
