package ecnu.dll.run.c_dataset_run.utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import ecnu.dll._config.Constant;
import ecnu.dll.run.b_parameter_run.basic.utils.InputDataStruct;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DatasetParameterUtils {
//    private static Map<String, >
    public static List<String> getDataType(String basicPath, String dataTypeFileName) {
        String filePath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", dataTypeFileName);
        BasicRead basicRead = new BasicRead(",");
        basicRead.startReading(filePath);
        List<String> data = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        return data;
    }

    public static List<StreamDataElement<Boolean>> getData(String dataPath, List<String> dataType) {
        BasicRead basicRead = new BasicRead(",");
        basicRead.startReading(dataPath);
        List<String> strDataList = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        InputDataStruct bean;
        String location;
        TreeMap<String, Boolean> tempMap;
        List<StreamDataElement<Boolean>> resultList = new ArrayList<>();
        StreamDataElement<Boolean> tempElement;
        for (String str : strDataList) {
            bean = InputDataStruct.toBean(basicRead.split(str));
            location = bean.getLocation();
            tempMap = new TreeMap<>();
            for (String loc : dataType) {
                if (loc.equals(location)) {
                    tempMap.put(loc, true);
                } else {
                    tempMap.put(loc, false);
                }
            }
            tempElement = new StreamDataElement(tempMap);
            resultList.add(tempElement);
        }
        return resultList;
    }

    public static void main(String[] args) {
        String basicDataPath = Constant.checkInFilePath;
        String dataTypeFileName = "country.txt";
        List<String> dataType = getDataType(basicDataPath, dataTypeFileName);
        String path = StringUtil.join(ConstantValues.FILE_SPLIT, basicDataPath, "runInput", "timestamp_0000.txt");
        List<StreamDataElement<Boolean>> data = getData(path, dataType);
        MyPrint.showList(data);
    }
}
