package ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll._config.ConfigureUtils;
import ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator.utils.UserGroupUtils;
import ecnu.dll.utils.io.ListReadUtils;
import ecnu.dll.utils.io.ListWriteUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserGroupGenerator {
    public static void generateUserIDType(String basicPath) {
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", "userTypeID.txt");
        Integer userTypeSize = ConfigureUtils.getDefaultUserTypeSize();
//        List<String> userTypeStringList = new ArrayList<>();
        List<String> userTypeStringList = UserGroupUtils.getUserIDType(userTypeSize);
//        for (int i = 0; i < userTypeSize; i++) {
//            userTypeStringList.add(String.valueOf(i));
//        }
        ListWriteUtils.writeList(outputPath, userTypeStringList, ",");
    }
    public static void generateUserToType(String basicPath) {
        String userIDInputPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", "user.txt");
        String userTypeIDInputPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", "userTypeID.txt");
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", "user_to_type.txt");

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
        ListWriteUtils.writeList(outputPath, userToTypeData, ",");
    }
}
