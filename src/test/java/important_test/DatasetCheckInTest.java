package important_test;

import ecnu.dll._config.Constant;
import ecnu.dll._config.TestData;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import ecnu.dll.dataset.real.datasetB.basic_struct.CityBean;
import ecnu.dll.dataset.real.datasetB.spetial_tools.CheckInStringTool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DatasetCheckInTest {
    @Test
    public void testCity() {
        String cityFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "dataset_TIST2015_Cities.txt");
        BasicRead basicRead = new BasicRead();
        basicRead.startReading(cityFilePath);
        List<String> recordList = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        List<CityBean> cityBeanList = new ArrayList<>();
        CityBean cityBean;
        for (String record : recordList) {
            cityBean = CityBean.toBean(CheckInStringTool.recordSplit(record));
            cityBeanList.add(cityBean);
        }
        MyPrint.showList(cityBeanList, ConstantValues.LINE_SPLIT);
    }
    @Test
    public void testCityType() {
        String cityFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "dataset_TIST2015_Cities.txt");
        BasicRead basicRead = new BasicRead();
        basicRead.startReading(cityFilePath);
        List<String> recordList = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        Map<String, Integer> cityBeanMap = new TreeMap<>();
        CityBean cityBean;
        String tempColumn;
        Integer tempCount;
        for (String record : recordList) {
            cityBean = CityBean.toBean(CheckInStringTool.recordSplit(record));
//            tempColumn = cityBean.getCityName();
//            tempColumn = cityBean.getCityType();
//            tempColumn = cityBean.getCountryName();
            tempColumn = cityBean.getCountryCode();
            tempCount = cityBeanMap.getOrDefault(tempColumn, 0);
            ++tempCount;
            cityBeanMap.put(tempColumn, tempCount);
        }
        MyPrint.showMap(cityBeanMap);
        System.out.println(cityBeanMap.size());
    }
}
