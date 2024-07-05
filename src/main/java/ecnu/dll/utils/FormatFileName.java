package ecnu.dll.utils;


import cn.edu.dll.constant_values.ConstantValues;
import ecnu.dll._config.Constant;

import java.io.File;

public class FormatFileName {
    public static String formatFileName(String originalName, String leftSplit, String rightSplit, int digitNumber) {
        int leftIndex;
        if ("".equals(leftSplit)) {
            leftIndex = -1;
        } else {
            leftIndex = originalName.lastIndexOf(leftSplit);
        }
        int rightIndex = originalName.lastIndexOf(rightSplit);
        String numString = originalName.substring(leftIndex+1, rightIndex);
        Long number = Long.valueOf(numString);
        numString = String.format("%0"+digitNumber+"d", number);
        return originalName.substring(0, leftIndex+1).concat(numString).concat(originalName.substring(rightIndex));
    }


    public static void main(String[] args) {
        String directoryPath = args[0];
        String leftSplit = args[1];
        String rightSplit = args[2];
        File directoryFile = new File(directoryPath);
        File[] files = directoryFile.listFiles();
        // 假设从文件编号从0开始
        int digitNum = 0, fileQuantity = files.length - 1;
        for (; fileQuantity > 0; ++digitNum){
            fileQuantity /= 10;
        } // fileQuantity不会是0
        String tempOldName, tempNewName;
        File tempNewFile;
        for (File file : files) {
            tempOldName = file.getName();
            tempNewName = formatFileName(tempOldName, leftSplit, rightSplit, digitNum);
            tempNewFile = new File(file.getParentFile(), tempNewName);
            file.renameTo(tempNewFile);
        }
    }
}
