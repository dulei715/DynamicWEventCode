package ecnu.dll.utils.filters;


import ecnu.dll.utils.FormatFileName;

import java.io.File;
import java.io.FileFilter;

public class NumberTxtFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        String fileName = file.getName();
        if (!fileName.endsWith(".txt")) {
            return false;
        }
        int left = fileName.lastIndexOf("_");
        int right = fileName.lastIndexOf(".");
        String middle = fileName.substring(left+1, right);
        try {
            Integer.valueOf(middle);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
