package ecnu.dll.run._pre_process.b_parameter_pre_process.version_3_group.parameter_generator;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;

public class UserTypeMapGenerator {
    public void generateUserToType(String basicPath) {
        String outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, basicPath, "basic_info", "user_to_type");
    }
}
