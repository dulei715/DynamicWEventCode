package ecnu.dll._config;

public class Constant {
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




}
