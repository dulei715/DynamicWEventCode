package ecnu.dll.run.d_total_run._1_initialize_run;

import ecnu.dll.run._pre_process.a_dataset_pre_process.dataset_pre_run.CheckInDatasetPreprocessRun;
import ecnu.dll.utils.CatchSignal;

public class CheckInInitialize {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        System.out.println("Program is running... ...");

        // 1. 将数据分割成多个文件以方便分批读取到内存进行处理
        System.out.println("Start data split...");
        int unitSize = 204800;
        CheckInDatasetPreprocessRun.dataSplit(unitSize);

        // 2. 将数据与country文件链接，组合成 (userID,country,timestamp)的形式
        System.out.println("Start join...");
        CheckInDatasetPreprocessRun.dataJoin();


        // 3. 将数据按照时间，划分成多个文件
        System.out.println("Start shuffle...");
        CheckInDatasetPreprocessRun.shuffleJoinFilesByTimeSlot();

        // 4. 保留每个timestamp的用户状态
        System.out.println("Start merge...");
        CheckInDatasetPreprocessRun.mergeToExperimentRawData();

        // 5. 记录country.txt, user.txt, timestamp.txt三个基本文件到 basic_info/ 目录下
        System.out.println("Start record...");
        CheckInDatasetPreprocessRun.recordBasicInformation();
        System.out.println("Program finished !");

    }
}
