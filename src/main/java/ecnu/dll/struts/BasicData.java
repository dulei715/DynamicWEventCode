package ecnu.dll.struts;

import cn.edu.dll.struct.point.DoublePoint;

public class BasicData extends DoublePoint {
    public BasicData(Double[] valueArray) {
        super(valueArray);
    }

    public BasicData(double... values) {
        super(values);
    }

    public BasicData(Integer dimensionalSize) {
        super(dimensionalSize);
    }
}
