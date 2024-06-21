package ecnu.dll._config;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.configure.XMLConfigure;
import cn.edu.dll.constant_values.ConstantValues;

import java.io.File;

public class Constant {
    public static String projectPath = System.getProperty("user.dir");
    public static String configPath;
    public static XMLConfigure xmlConfigure;
    public static String basicDatasetPath;
    public static String checkInFileName;
    public static String trajectoriesFileName;
    public static String checkInFilePath;
    public static String trajectoriesFilePath;

    static {
        configPath = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "config", "parameter_config.xml");
        File configFile = new File(configPath);
        if (!configFile.exists()) {
            configPath = StringUtil.join(ConstantValues.FILE_SPLIT, projectPath, "deployment", "config", "parameter_config.xml");
            String pathA = configFile.getAbsolutePath();
            configFile = new File(configPath);
            if (!configFile.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Not find the parameter_config.xml").append(ConstantValues.LINE_SPLIT);
                stringBuilder.append("Neither path '").append(pathA).append("' nor path '").append(configFile.getAbsolutePath()).append("' ");
                stringBuilder.append("exists!");
                throw new RuntimeException(stringBuilder.toString());
            }
        }
        xmlConfigure = new XMLConfigure(configPath);
        basicDatasetPath = ConfigureUtils.getDatasetBasicPath();
        checkInFileName = ConfigureUtils.getDatasetFileName("checkIn");
        trajectoriesFileName = ConfigureUtils.getDatasetFileName("trajectories");
        checkInFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, checkInFileName);
        trajectoriesFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, basicDatasetPath, trajectoriesFileName);
    }

    // constant for RescueDP
    public static Double TAO_1 = 50D;
    public static Double TAO_2 = 0.8D;
    public static Double TAO_3 = 20D;
    public static Integer SAMPLE_WINDOW_SIZE = 3;
    public static Double KP = 0.9;
    public static Double KI = 0.1;
    public static Double KD = 0D;
//    public static Integer THETA_SCALE = 10;
    public static Integer THETA_SCALE = 2;
    public static Integer PID_PI = 3;
    public static Double PHI_Scale = 0.5;
    //这里规定p_max为0.5
    public static Double P_MAX = 0.5;
    public static Double Q_VARIANCE = Math.pow(10, 5);
    public static Double R_VARIANCE = Math.pow(10, 6);


    // constant for experiment result title
    public static final String MechanismName = "Name";
    public static final String TimeCost = "Time Cost";
    public static final String PrivacyBudget = "Privacy Budget";
    public static final String WindowSize = "Window Size";
    public static final String MRE = "MRE";

    public static final String  nonPrivacyName = "Non-Privacy";
    public static final String  budgetDistributionName = "BD";
    public static final String  budgetAbsorptionName = "BA";
    public static final String  personalizedBudgetDistributionName = "PBD";
    public static final String  personalizedBudgetAbsorptionName = "PBA";
    public static final String  dynamicPersonalizedBudgetDistributionName = "DPBD";
    public static final String  dynamicPersonalizedBudgetAbsorptionName = "DPBA";







    // constant for Personalized Dynamic Budget Distribution (Absorption)
    public static final Integer MAX_BACKWARD_WINDOW_SIZE = 201;

    public static final Double PRIVACY_LOWER_BOUND = 0.1;
    public static final Double PRIVACY_UPPER_BOUND = 10D;
    public static final Integer WINDOW_LOWER_BOUND = 20;
    public static final Integer WINDOW_UPPER_BOUND = 100;



    public static Double MIN_UNION_PRIVACY_BUDGET = 0.1D;

    public static void main(String[] args) {
//        System.out.println(configPath);
        String datasetBasicPath = ConfigureUtils.getDatasetBasicPath();
        System.out.println(datasetBasicPath);
    }
















}
