package ecnu.dll.run._2_trajectory_run.main_repeat_process;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.result.ExperimentResult;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run._2_trajectory_run.main_process.d_total_run.TotalTrajectoryRun;

import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class RepeatTrajectoryRun {
    public static void main(String[] args) throws IllegalAccessException, CloneNotSupportedException, InstantiationException {

        DataSetAreaInfo nycDataSet = Constant.nycTrajectoryDataSet;

        String nycOutputDir;

        int repeatTimes = 10;
        for (int i = 0; i < repeatTimes; i++) {
            nycOutputDir = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.trajectoryOutputNYCDir, String.valueOf(i));
//            normalOutputDir = Constant.outputNormalDir;

            System.out.println(nycOutputDir);

            Map<String, Map<String, Map<String, List<ExperimentResult>>>> resultMap = TotalTrajectoryRun.runAndWrite(nycDataSet, nycOutputDir);
            System.out.println("Finish extended round " + i);
        }

    }
}
