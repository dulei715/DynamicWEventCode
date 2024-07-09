package ecnu.dll.run.c_dataset_run;

import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.run.c_dataset_run.utils.DatasetParameterUtils;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.List;

public class RealDataSetRun {

    public static void CheckInDatasetRun() {
        String datasetPath = Constant.checkInFilePath;
        String dataTypeFileName = "country.txt";
        List<String> dataType = DatasetParameterUtils.getDataType(datasetPath, dataTypeFileName);
        List<Double> privacyBudgetList = ConfigureUtils.getIndependentPrivacyBudgetList("default");
        List<Integer> windowSizeList = ConfigureUtils.getIndependentWindowSizeList("default");
        List<List<StreamDataElement<Boolean>>> dataList;
    }

    public static void main(String[] args) {
        List<String> dataType;
        List<List<StreamDataElement<Boolean>>> dataList;
        List<Double> privacyBudgetList;
        Integer windowSize;
    }
}
