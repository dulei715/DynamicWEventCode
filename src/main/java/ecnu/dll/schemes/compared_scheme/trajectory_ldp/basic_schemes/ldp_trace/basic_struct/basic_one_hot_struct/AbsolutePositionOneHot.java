package ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.basic_one_hot_struct;

import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.basic_one_hot_struct.struct_utils.AbsolutePositionOneHotUtils;

/**
 * 用来记录起始点或终止点的 one hot 编码
 */
public class AbsolutePositionOneHot extends OneHot<TwoDimensionalIntegerPoint> {
    protected int rowSize;
    protected int colSize;
    public AbsolutePositionOneHot(int rowSize, int colSize) {
        super(rowSize * colSize);
        this.rowSize = rowSize;
        this.colSize = colSize;

    }
    protected AbsolutePositionOneHot(boolean... booleans) {
        super(booleans);
    }
    @Override
    public void setElement(TwoDimensionalIntegerPoint point) {
        int index = AbsolutePositionOneHotUtils.toOneHotDataIndex(point, this.colSize);
        this.data[index] = ONE;
    }

    @Override
    public OneHot<TwoDimensionalIntegerPoint> getInstance(boolean... booleans) {
        AbsolutePositionOneHot newInstance = new AbsolutePositionOneHot(booleans);
        newInstance.rowSize = this.rowSize;
        newInstance.colSize = this.colSize;
        return newInstance;
    }


}
