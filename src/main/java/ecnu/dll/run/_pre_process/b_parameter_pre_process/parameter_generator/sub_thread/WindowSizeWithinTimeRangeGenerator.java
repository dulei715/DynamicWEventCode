package ecnu.dll.run._pre_process.b_parameter_pre_process.parameter_generator.sub_thread;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll.utils.FormatFileName;

import java.util.List;

public class WindowSizeWithinTimeRangeGenerator implements Runnable {
    private String outputFileDir;
    private List<Integer> timeStampList;
    private Integer windowSizeLowerBound;
    private Integer backwardWindowSizeLowerBound;
    private List<BasicPair<Integer, Integer>> userWSizeList;
    private Integer startIndex;
    private Integer endIndex;

    public WindowSizeWithinTimeRangeGenerator(String outputFileDir, List<Integer> timeStampList, Integer windowSizeLowerBound, Integer backwardWindowSizeLowerBound, List<BasicPair<Integer, Integer>> userWSizeList, Integer startIndex, Integer endIndex) {
        this.outputFileDir = outputFileDir;
        this.timeStampList = timeStampList;
        this.windowSizeLowerBound = windowSizeLowerBound;
        this.backwardWindowSizeLowerBound = backwardWindowSizeLowerBound;
        this.userWSizeList = userWSizeList;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    private void generateWindowSizeWithinTimeRange() {
        String tempOutputFilePath;
        Integer timeStampListSize = timeStampList.size();
        String formatFileNameID;
        BasicWrite basicWrite = new BasicWrite(",");
        String tempString;
        Integer timeStampID;
//        for (Integer timeStampID : timeStampList) {
        for (int i = startIndex; i <= endIndex; ++i) {
            timeStampID = timeStampList.get(i);
            formatFileNameID = FormatFileName.getFormatNumberString(timeStampID, 0, timeStampListSize);
            tempOutputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, "timestamp_" + formatFileNameID + ".txt");
            basicWrite.startWriting(tempOutputFilePath);
            for (BasicPair<Integer, Integer> userWindowSizeUpperBoundPair : userWSizeList) {
                //todo: 这里把backward window size设置为最小
//                Integer tempRandomInteger = RandomUtil.getRandomInteger(backwardWindowSizeLowerBound, userWindowSizeUpperBoundPair.getValue());
                Integer tempRandomInteger = RandomUtil.getRandomInteger(backwardWindowSizeLowerBound, backwardWindowSizeLowerBound);
                Integer tempRandomInteger2 = RandomUtil.getRandomInteger(windowSizeLowerBound, userWindowSizeUpperBoundPair.getValue());
                tempString = StringUtil.join(",", userWindowSizeUpperBoundPair.getKey(), tempRandomInteger, tempRandomInteger2);
                basicWrite.writeOneLine(tempString);
            }
            basicWrite.endWriting();
        }
    }

    @Override
    public void run() {
        generateWindowSizeWithinTimeRange();
    }
}
