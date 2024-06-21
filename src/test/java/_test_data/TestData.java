package _test_data;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll._config.ConfigureUtils;

public class TestData {
    public static final String basicDatasetPath = ConfigureUtils.getDatasetBasicPath();
    public static final String checkInFileName = ConfigureUtils.getDatasetFileName("checkIn");
    public static final String trajectoriesFileName = ConfigureUtils.getDatasetFileName("trajectories");

    public static final String checkInFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, checkInFileName);
    public static final String trajectoriesFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, trajectoriesFileName);
}
