package ecnu.dll.utils.paper;

import cn.edu.dll.constant_values.ConstantValues;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PaperUtils {

    public static String formatNumber(double number) {
        // 创建DecimalFormat对象，模式"#,##0.00"表示：
        // #,##0 - 整数部分每三位用逗号分隔
        // .00 - 保留两位小数，不足补0
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(number);
    }

    public static String capitalize(String str) {
        // 1. 处理 null 或空字符串的情况
        if (str == null || str.isEmpty()) {
            return str;
        }
        // 2. 截取首字母并转为大写，截取剩余部分并转为小写，然后拼接
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String getLatexTableCode(String datasetName, final String[] mechanismNameArray, List<List<Double>> resulList, boolean marked) {
        int mechanismSize = mechanismNameArray.length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\multirow{"+mechanismSize+"}{*}{\\textbf{"+datasetName+"}}");
        List<Double> tempResultList;

        int colSize = resulList.get(0).size();
        List<Integer> minimumResultIndex = new ArrayList<>(colSize);
        List<Integer> minimum2ResultIndex = new ArrayList<>(colSize);
        Integer currentMinimumResultIndex;
        Double currentMinimumResult, tempResult;
        for (int j = 0; j < colSize; j++) {
            currentMinimumResultIndex = -1;
            currentMinimumResult = Double.MAX_VALUE;
            for (int i = 0; i < resulList.size(); i++) {
                tempResult = resulList.get(i).get(j);
                if (tempResult < currentMinimumResult) {
                    currentMinimumResult = tempResult;
                    currentMinimumResultIndex = i;
                }
            }
            minimumResultIndex.add(currentMinimumResultIndex);
        }

        for (int j = 0; j < colSize; j++) {
            currentMinimumResultIndex = -1;
            currentMinimumResult = Double.MAX_VALUE;
            for (int i = 0; i < resulList.size(); i++) {
                tempResult = resulList.get(i).get(j);
                if (tempResult < currentMinimumResult && i != minimumResultIndex.get(j)) {
                    currentMinimumResult = tempResult;
                    currentMinimumResultIndex = i;
                }
            }
            minimum2ResultIndex.add(currentMinimumResultIndex);
        }

        for (int i = 0; i < mechanismSize; i++) {
            stringBuilder.append("&").append(mechanismNameArray[i]);
            tempResultList = resulList.get(i);
            for (int j = 0; j < tempResultList.size(); j++) {
                stringBuilder.append("\t\t").append("&");
                if (marked) {
                    stringBuilder.append("\\revision{");
                }
                if (i == minimumResultIndex.get(j) || i == minimum2ResultIndex.get(j)) {
                    stringBuilder.append("\\textbf{");
                }
                stringBuilder.append(formatNumber(tempResultList.get(j)));
                if (i == minimumResultIndex.get(j) || i == minimum2ResultIndex.get(j)) {
                    stringBuilder.append("}");
                }
                if (marked) {
                    stringBuilder.append("}");
                }
            }
            stringBuilder.append(" ").append("\\\\").append(ConstantValues.LINE_SPLIT);
        }
        stringBuilder.append("\\hline").append(ConstantValues.LINE_SPLIT);
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        double number = 1234567.8949;
        System.out.println(formatNumber(number));
    }
}
