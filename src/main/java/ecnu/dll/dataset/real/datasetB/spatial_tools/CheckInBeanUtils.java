package ecnu.dll.dataset.real.datasetB.spatial_tools;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.read.BasicRead;
import cn.edu.dll.io.write.BasicWrite;
import ecnu.dll.dataset.real.datasetB.basic_struct.CheckInBean;
import ecnu.dll.dataset.real.datasetB.basic_struct.CityBean;
import ecnu.dll.dataset.real.datasetB.basic_struct.POIBean;
import ecnu.dll.dataset.real.datasetB.spatial_tools.CheckInStringTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckInBeanUtils {
    public static void transformToCountry(String inputSuperFilePath, String outputSuperFilePath, String outputFileName) {
        String checkInPath = StringUtil.join(ConstantValues.FILE_SPLIT, inputSuperFilePath, "dataset_TIST2015_Checkins.txt");
        String poiPath = StringUtil.join(ConstantValues.FILE_SPLIT, inputSuperFilePath, "dataset_TIST2015_POIs.txt");
        String cityPath = StringUtil.join(ConstantValues.FILE_SPLIT, inputSuperFilePath, "dataset_TIST2015_Cities.txt");
        Map<String, String> countryCodeNameMap = new HashMap<>();
        BasicRead basicRead = new BasicRead();
        CityBean tempCityBean;
        POIBean tempPOIBean;
        CheckInBean tempCheckInBean;
        String tempCountryName, tempVenueID, tempCountryCode;

        basicRead.startReading(cityPath);
        List<String> cityData = basicRead.readAllWithoutLineNumberRecordInFile();
        for (String cityDatum : cityData) {
            tempCityBean = CityBean.toBean(CheckInStringTool.recordSplit(cityDatum));
            countryCodeNameMap.put(tempCityBean.getCountryCode(), tempCityBean.getCountryName());
        }
        basicRead.endReading();

        Map<String, String> venueIDCountryMap = new HashMap<>();
        basicRead.startReading(poiPath);
        List<String> poiData = basicRead.readAllWithoutLineNumberRecordInFile();
        for (String poiDatum : poiData) {
            tempPOIBean = POIBean.toBean(CheckInStringTool.recordSplit(poiDatum));
            tempVenueID = tempPOIBean.getVenueID();
            tempCountryCode = tempPOIBean.getCountryCode();
            venueIDCountryMap.put(tempVenueID, countryCodeNameMap.get(tempCountryCode));
        }
        basicRead.endReading();

        List<String> result = new ArrayList<>();
        basicRead.startReading(checkInPath);
        List<String> checkInRecordList = basicRead.readAllWithoutLineNumberRecordInFile();
        basicRead.endReading();
        String tempString;
        for (String record : checkInRecordList) {
            tempCheckInBean = CheckInBean.toBean(CheckInStringTool.recordSplit(record));
            tempCountryName = venueIDCountryMap.get(tempCheckInBean.getVenueID());
            tempString = StringUtil.join(",", tempCheckInBean.getUserID(), tempCountryName, tempCheckInBean.getUtcTime());
            result.add(tempString);
        }

        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, outputSuperFilePath, outputFileName);
        BasicWrite basicWrite = new BasicWrite();
        basicWrite.startWriting(outputPath);
        basicWrite.writeStringListWithoutSize(result);
        basicWrite.endWriting();

    }

    public static void main(String[] args) {

    }
}
