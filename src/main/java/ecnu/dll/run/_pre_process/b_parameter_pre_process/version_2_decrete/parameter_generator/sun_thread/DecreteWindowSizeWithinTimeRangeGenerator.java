package ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_generator.sun_thread;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll.utils.FormatFileName;

import java.util.List;

public class DecreteWindowSizeWithinTimeRangeGenerator implements Runnable {
    private String outputFileDir;
    private List<Integer> timeStampList;
    private List<Integer> windowSizeCandidateList;
    private List<Double> windowSizeRatioList;
    private Integer backwardWindowSizeLowerBound;
    private List<BasicPair<Integer, Integer>> userWSizeList;
    private Integer startIndex;
    private Integer endIndex;

    public DecreteWindowSizeWithinTimeRangeGenerator(String outputFileDir, List<Integer> timeStampList, List<Integer> windowSizeCandidateList, List<Double> windowSizeRatioList, Integer backwardWindowSizeLowerBound, List<BasicPair<Integer, Integer>> userWSizeList, Integer startIndex, Integer endIndex) {
        this.outputFileDir = outputFileDir;
        this.timeStampList = timeStampList;
        this.windowSizeCandidateList = windowSizeCandidateList;
        this.windowSizeRatioList = windowSizeRatioList;
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
//                Integer tempRandomInteger2 = RandomUtil.getRandomInteger(windowSizeCandidateList, userWindowSizeUpperBoundPair.getValue());
                Integer index = RandomUtil.getRandomIndexGivenStatisticPoint(windowSizeRatioList.toArray(new Double[0]));
                Integer tempRandomInteger2 = windowSizeCandidateList.get(index);
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
