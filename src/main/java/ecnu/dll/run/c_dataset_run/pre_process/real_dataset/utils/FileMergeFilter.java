package ecnu.dll.run.c_dataset_run.pre_process.real_dataset.utils;


import java.io.File;
import java.io.FileFilter;

public class FileMergeFilter implements FileFilter {
    private Long fileNumber = null;

    public FileMergeFilter(Long fileNumber) {
        this.fileNumber = fileNumber;
    }

    @Override
    public boolean accept(File file) {
        String fileName = file.getName();
        if (!fileName.endsWith(".txt")) {
            return false;
        }
        String fileNumberString = fileName.split("_")[1].split("\\.")[0];
        Long fileNumber = Long.valueOf(fileNumberString);
        if (fileNumber < this.fileNumber) {
            return false;
        }
        return true;
    }
}
