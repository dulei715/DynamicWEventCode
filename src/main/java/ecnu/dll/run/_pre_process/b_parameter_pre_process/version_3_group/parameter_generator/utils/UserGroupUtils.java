package ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.utils;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.print.MyPrint;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll._config.Constant;
import ecnu.dll.utils.io.ListReadUtils;
import ecnu.dll.utils.io.ListWriteUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserGroupUtils {
    public static List<String> getUserIDType(Integer userTypeSize) {
        List<String> userTypeStringList = new ArrayList<>();
        for (int i = 0; i < userTypeSize; i++) {
            userTypeStringList.add(String.valueOf(i));
        }
        return userTypeStringList;
    }


    public static List<String> getUserToTypeInAverage(String userIDInputPath, String userTypeIDInputPath) {

        String tempOutputString;
        List<String> userIDDataList = ListReadUtils.readAllDataList(userIDInputPath, ",");
        List<String> userTypeIDDataList = ListReadUtils.readAllDataList(userTypeIDInputPath, ",");
        Iterator<String> userIDIterator = userIDDataList.iterator();
        Integer groupElementSize = (int)Math.ceil(userIDDataList.size() * 1.0 / userTypeIDDataList.size());
        Integer groupID = 0;
        String tempUserID;
        List<String> userToTypeData = new ArrayList<>();
        while (userIDIterator.hasNext()) {
            for (int i = 0; i < groupElementSize && userIDIterator.hasNext(); i++) {
                tempUserID = userIDIterator.next();
                tempOutputString = StringUtil.join(",", tempUserID, groupID);
                userToTypeData.add(tempOutputString);
            }
            ++groupID;
        }
        return userToTypeData;
    }
    public static List<String> getUserToTypeByRatio(String userIDInputPath, String userTypeIDRatioInputPath) {

        String tempOutputString;
        List<String> userIDDataList = ListReadUtils.readAllDataList(userIDInputPath, ",");
        List<String> userTypeIDRatioDataList = ListReadUtils.readAllDataList(userTypeIDRatioInputPath, ",");
        Iterator<String> userIDIterator = userIDDataList.iterator();
        Integer tempGroupElementSize, userSize = userIDDataList.size();
        String tempUserID, tempTypeID;
        List<String> userToTypeData = new ArrayList<>();
        Double tempRatio;
        String[] tempSplitArray;
        double ratioSum = 0;
        for (String userTypeIDRatio : userTypeIDRatioDataList) {
            ratioSum += Double.parseDouble(userTypeIDRatio.split(",")[1]);
        }
        for (String userTypeIDRatio : userTypeIDRatioDataList) {
            tempSplitArray = userTypeIDRatio.split(",");
            tempTypeID = tempSplitArray[0];
            tempRatio = Double.valueOf(tempSplitArray[1]);
            tempGroupElementSize = (int) Math.round(userSize / ratioSum * tempRatio);
            for (int i = 0; i < tempGroupElementSize && userIDIterator.hasNext(); i++) {
                tempUserID = userIDIterator.next();
                tempOutputString = StringUtil.join(",", tempUserID, Integer.valueOf(tempTypeID));
                userToTypeData.add(tempOutputString);
            }
        }
        return userToTypeData;
    }

    public static void main(String[] args) {
        String userIDInputPath = StringUtil.join(ConstantValues.FILE_SPLIT,
                Constant.basicDatasetPath, "..", "test", "user.txt");
        String userTypeIDRatioInputPath = StringUtil.join(ConstantValues.FILE_SPLIT,
                Constant.basicDatasetPath, "..", "test", "userTypeIDRatio.txt");
        List<String> result = getUserToTypeByRatio(userIDInputPath, userTypeIDRatioInputPath);
        MyPrint.showList(result, ConstantValues.LINE_SPLIT);
    }

}
