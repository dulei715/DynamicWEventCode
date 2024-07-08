package ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils;


import java.io.File;
import java.io.FileFilter;

public class TxtFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        String fileName = file.getName();
        if (!fileName.endsWith(".txt")) {
            return false;
        }
        return true;
    }
}
