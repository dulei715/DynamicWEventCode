package important_test;

import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll._config.Constant;
import ecnu.dll._config.TestData;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.io.read.BasicRead;
import ecnu.dll.dataset.real.datasetB.basic_struct.CityBean;
import ecnu.dll.dataset.real.datasetB.handled_struct.CheckInSimplifiedBean;
import ecnu.dll.dataset.real.datasetB.spetial_tools.CheckInStringTool;
import org.junit.Test;

import java.io.File;
import java.util.*;

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

    // 测试各个Country在check in 数据中各自关联多少个数据
    @Test
    public void testCountryStatistic() {
        String filterFileDirectory = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.checkInFilePath, "join");
        File fileDirectoryFile = new File(filterFileDirectory);
        File[] fileArray = fileDirectoryFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> dataList;
        String[] dataSplitArray;
        Integer tempCount;
        Map<String, Integer> countryStatisticMap = new HashMap<>();
        for (File file : fileArray) {
            basicRead.startReading(file.getAbsolutePath());
            dataList = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String data : dataList) {
                dataSplitArray = data.split(basicRead.INPUT_SPLIT_SYMBOL);
                tempCount = countryStatisticMap.getOrDefault(dataSplitArray[1],0);
                ++tempCount;
                countryStatisticMap.put(dataSplitArray[1],tempCount);
            }
        }
        System.out.println(countryStatisticMap.size());
        MyPrint.showMap(countryStatisticMap);

    }

    @Test
    public void testTime() {
        String directoryPath = Constant.checkInFilePath;
        String handledDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, directoryPath, "join");
        File directoryFile = new File(handledDirectoryPath);
        File[] files = directoryFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> lineDataList;
        CheckInSimplifiedBean tempBean;
        Long maxTimeStamp = 0L, minTimeStamp = Long.MAX_VALUE, tempTimeStamp;
        int k = 0;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            lineDataList = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String line : lineDataList) {
                ++k;
                tempBean = CheckInSimplifiedBean.toBean(basicRead.split(line));
                tempTimeStamp = tempBean.getCheckInTimeStamp();
                minTimeStamp = Math.min(minTimeStamp, tempTimeStamp);
                maxTimeStamp = Math.max(maxTimeStamp, tempTimeStamp);
            }
            basicRead.endReading();
        }
        System.out.println(k);
        Date minDate = new Date(minTimeStamp);
        Date maxDate = new Date(maxTimeStamp);
        System.out.println(minDate);
        System.out.println(maxDate);
        System.out.println(minTimeStamp);
        System.out.println(maxTimeStamp);
        System.out.println((maxTimeStamp-minTimeStamp)/1000.0/60/10);
    }

    @Test
    public void testTimeStampDifference() {
        String directoryPath = Constant.checkInFilePath;
        String handledDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, directoryPath, "join");
        File directoryFile = new File(handledDirectoryPath);
        File[] files = directoryFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> lineDataList;
        CheckInSimplifiedBean tempBean;
        TreeMap<Long, Integer> differenceMap = new TreeMap<>();
        Long tempDifference, oldTimeStamp, newTimeStamp = 0L;
        int k = 0;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            lineDataList = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String line : lineDataList) {
                ++k;
                oldTimeStamp = newTimeStamp;
                tempBean = CheckInSimplifiedBean.toBean(basicRead.split(line));
                newTimeStamp = tempBean.getCheckInTimeStamp();
                tempDifference = newTimeStamp - oldTimeStamp;
                StatisticTool.addElement(differenceMap, tempDifference);
            }
            basicRead.endReading();
        }
        System.out.println(k);
        MyPrint.showMap(differenceMap);
    }


    @Test
    public void testEachUserCheckInInterval() {
        String directoryPath = Constant.checkInFilePath;
        String handledDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, directoryPath, "join");
        File directoryFile = new File(handledDirectoryPath);
        File[] files = directoryFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> lineDataList;
        CheckInSimplifiedBean tempBean;
        TreeMap<Long, Integer> differenceMap = new TreeMap<>();
        Long tempDifference, oldTimeStamp, newTimeStamp;
        Map<Integer, Long> currentMap = new TreeMap<>();
        Map<Integer, Map<Integer, Integer>> differMap = new TreeMap<>();
        Integer tempUserID;
        int k = 0;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            lineDataList = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String line : lineDataList) {
                ++k;
                tempBean = CheckInSimplifiedBean.toBean(basicRead.split(line));
                tempUserID = tempBean.getUserID();
                newTimeStamp = tempBean.getCheckInTimeStamp();
                oldTimeStamp = currentMap.getOrDefault(tempUserID, 0L);
                StatisticTool.addElement(differMap, tempUserID, newTimeStamp.compareTo(oldTimeStamp));
                currentMap.put(tempUserID, newTimeStamp);
            }
            basicRead.endReading();
        }
        System.out.println(k);
//        MyPrint.showMap(differMap);
        for (Map.Entry<Integer, Map<Integer, Integer>> entry : differMap.entrySet()) {
            Map<Integer, Integer> tempValue = entry.getValue();
            if (tempValue.size() > 1) {
                System.out.println(entry.getKey() + "; " + tempValue);
            }
        }

    }

    @Test
    public void testEachUserInterval() {
        String directoryPath = Constant.checkInFilePath;
        String handledDirectoryPath = StringUtil.join(ConstantValues.FILE_SPLIT, directoryPath, "join");
        File directoryFile = new File(handledDirectoryPath);
        File[] files = directoryFile.listFiles();
        BasicRead basicRead = new BasicRead(",");
        List<String> lineDataList;
        CheckInSimplifiedBean tempBean;
        TreeMap<Long, Integer> differenceMap = new TreeMap<>();
        Long tempDifference, oldTimeStamp, newTimeStamp;
        Map<Integer, Long> currentMap = new TreeMap<>();
        Map<Integer, Map<Long, Integer>> differMap = new TreeMap<>();
        Integer tempUserID;
        int k = 0;
        for (File file : files) {
            basicRead.startReading(file.getAbsolutePath());
            lineDataList = basicRead.readAllWithoutLineNumberRecordInFile();
            for (String line : lineDataList) {
                ++k;
                tempBean = CheckInSimplifiedBean.toBean(basicRead.split(line));
                tempUserID = tempBean.getUserID();
                newTimeStamp = tempBean.getCheckInTimeStamp();
                oldTimeStamp = currentMap.getOrDefault(tempUserID, 0L);
                StatisticTool.addElement(differMap, tempUserID, newTimeStamp - oldTimeStamp);
                currentMap.put(tempUserID, newTimeStamp);
            }
            basicRead.endReading();
        }
        System.out.println(k);
//        MyPrint.showMap(differMap);
        for (Map.Entry<Integer, Map<Long, Integer>> entry : differMap.entrySet()) {
            Map<Long, Integer> tempValue = entry.getValue();
            if (tempValue.size() > 1) {
                System.out.println(entry.getKey() + "; " + tempValue);
            }
        }

    }







}
