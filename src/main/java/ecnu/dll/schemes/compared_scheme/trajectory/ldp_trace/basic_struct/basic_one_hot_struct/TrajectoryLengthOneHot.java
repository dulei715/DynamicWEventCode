package ecnu.dll.schemes.compared_scheme.trajectory.ldp_trace.basic_struct.basic_one_hot_struct;

import cn.edu.dll.struct.one_hot.OneHot;

public class TrajectoryLengthOneHot extends OneHot<Integer> {
//    protected int maxLength;
    public TrajectoryLengthOneHot(int maxLength) {
        super(maxLength);
    }
    @Override
    public void setElement(Integer integer) {
        int index = toOneHotDataIndex(integer);
        this.data[index] = ONE;
    }

    public int getMaxLength() {
        return super.areaSize;
    }

    protected TrajectoryLengthOneHot(boolean... booleans) {
        super(booleans);
    }

    @Override
    public OneHot<Integer> getInstance(boolean... booleans) {
        return new TrajectoryLengthOneHot(booleans);
    }


    protected int toOneHotDataIndex(Integer integer) {
        return integer - 1;
    }
}
