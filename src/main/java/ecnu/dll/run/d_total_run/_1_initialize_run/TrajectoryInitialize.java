package ecnu.dll.run.d_total_run._1_initialize_run;

import cn.edu.dll.signal.CatchSignal;
import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.TrajectoryDatasetPreprocessRun;

public class TrajectoryInitialize {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        // 1. 抽取数据在经度[116,116.8]和纬度[39.5,40.3]之间的数据（和user_guide.pdf图像展示保持一致）
        TrajectoryDatasetPreprocessRun.extract();

        // 2. 将用户数据按照time stamp分类
        TrajectoryDatasetPreprocessRun.splitByTimeMultiThread();

        // 3. 统一time_stamp文件名格式
        TrajectoryDatasetPreprocessRun.formatFileName("shuffle_by_time_slot");

        // 4. 只保留每个time stamp中的最新的用户数据，并记录道runInput中
        TrajectoryDatasetPreprocessRun.mergeToExperimentRawData();

        // 5. 记录每个基本数据
        TrajectoryDatasetPreprocessRun.recordBasicInformation();
    }
}
