package ecnu.dll.run;

import cn.edu.dll.result.ExperimentResult;

public class Main {
    /**
     * 自变量指标
     *      1. window 种类数
     *      2. privacy budget 种类数
     *
     * 因变量指标：
     *      1. Runing time
     *      2. MRE, MAE
     *
     * 对比方案
     *      1. 基本方案
     *          (1) BD (统一为最小的 privacy buget 和最大的 window size)
     *          (2) BA (统一为最小的 privacy buget 和最大的 window size)
     *      2. 额外方案
     *          (1) 针对 PBD 和 PBA
     *          (2) 针对 PDBD 和 PDBA
     *      3. 本方案
     *          (1) PBD (每个用户各自统一为最小的 privacy budget 和最大的 window size)
     *          (2) PBA (每个用户各自统一为最小的 privacy budget 和最大的 window size)
     *          (3) PDBD
     *          (4) PDBA
     */
    public static void main(String[] args) {
        ExperimentResult experimentResult = new ExperimentResult();
        experimentResult.addPair("aaa", "1");
        experimentResult.addPair("bbb", "2");
        System.out.println(experimentResult);
    }
}
