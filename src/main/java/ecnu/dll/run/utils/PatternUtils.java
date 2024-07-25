package ecnu.dll.run.utils;

public class PatternUtils {
    public static String toRatioFileNamePattern(Double ratio, String fileSuffix) {
        String formatNum = String.valueOf(ratio).replace(".", "-");
        return String.format("ratio_%s%s", formatNum, fileSuffix);
    }
//    public static String toPrivacyRatioFileNamePattern(Double ratio, String fileSuffix) {
//        String formatNum = String.valueOf(ratio).replace(".", "-");
//        return String.format("p_ratio_%s%s", formatNum, fileSuffix);
//    }
//    public static String toWindowRatioFileNamePattern(Double ratio, String fileSuffix) {
//        String formatNum = String.valueOf(ratio).replace(".", "-");
//        return String.format("w_ratio_%s%s", formatNum, fileSuffix);
//    }
    public static Double getRatioFromNamePattern(String namePattern) {
        String ratioStr = namePattern.split("\\.")[0].split("_")[1].replace("-", ".");
        return Double.valueOf(ratioStr);
    }
    public static void main(String[] args) {
        Double ratio = 0.2;
        String fileSuffix = ".txt";
        String result = toRatioFileNamePattern(ratio, fileSuffix);
        System.out.println(result);
    }
}
