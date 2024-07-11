package ecnu.dll.run.c_dataset_run;

import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run.b_parameter_run.basic.FixedParameterRun;
import ecnu.dll.run.c_dataset_run.utils.DatasetParameterUtils;
import ecnu.dll.run.c_dataset_run.utils.DatasetRunUtils;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.utils.CatchSignal;

import java.util.List;

public class CheckInDataSetRun {


    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();
        String basicPath = Constant.checkInFilePath;
        String dataTypeFileName = "country.txt";
        Integer singleBatchSize = 2;
        DatasetRunUtils.basicDatasetRun(basicPath, dataTypeFileName, singleBatchSize);
    }
}
