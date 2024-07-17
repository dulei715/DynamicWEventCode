package ecnu.dll.run;

public class TotalRun {
    /**
     *
     * 自变量指标
     *      1. window 种类数
     *      2. privacy budget 种类数
     *
     * 因变量指标：
     *      1. Runing time
     *      2. MRE, MAE
     *
     *
     *
     * 实验一：
     * 对比方案
     *      1. 基本方案
     *          (1) BD (统一为最小的 privacy budget 和最大的 window size)
     *          (2) BA (统一为最小的 privacy budget 和最大的 window size)
     *      2. 额外方案
     *          (1) 针对 PBD 和 PBA
     *          (2) 针对 PDBD 和 PDBA
     *      3. 本方案
     *          (1) PBD (每个用户各自统一为最小的 privacy budget 和最大的 window size)
     *          (2) PBA (每个用户各自统一为最小的 privacy budget 和最大的 window size)
     *          (3) PDBD
     *          (4) PDBA
     *
     *  实验二：
     *      1. 控制 window size 不变，变换 privacy budget
     *      2. 控制 privacy budget 不变，变换 window size
     *
     *  实验三(针对PBD和PBA --- BD和BA)：
     *      1. 在假设只有两种 privacy 的前提下调整 user 的最小privacy budget占比
     *      2. 在假设只有两种 window size 的前提下调整 user的最大 window size 占比
     *
     *  实验四(针对PDBD和PDBA --- BD和BA)
     *      1. 在假设每个用户 backward privacy 足够大backward window size足够小，且只有两种 forward privacy 的前提下调整最小 forward privacy 占比
     *      2. 在假设每个用户 backward privacy 足够大backward window size足够小，且 forward privacy 统一的前提下调整最大 forward window size 的占比
     *
     */
    public static void main(String[] args) {

    }
}
