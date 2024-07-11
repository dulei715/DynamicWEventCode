package ecnu.dll.run._pre_process.b_parameter_pre_process.version_2_decrete.parameter_generator.sun_thread;

import cn.edu.dll.basic.NumberUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.write.BasicWrite;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll.utils.FormatFileName;

import java.util.List;

public class DecretePrivacyBudgetWithinTimeRangeGenerator implements Runnable {
    private String outputFileDir;
    private List<Integer> timeStampList;
    private List<Double> privacyBudgetCandidateList;
    private List<Double> privacyBudgetRatioList;
    private Double remainBackwardPrivacyLowerBound;
    private Double remainBackwardPrivacyUpperBound;
    private List<BasicPair<Integer, Double>> userBudgetList;
    private Integer startIndex;
    private Integer endIndex;

    public DecretePrivacyBudgetWithinTimeRangeGenerator(String outputFileDir, List<Integer> timeStampList, List<Double> privacyBudgetCandidateList, List<Double> privacyBudgetRatioList, Double remainBackwardPrivacyLowerBound, Double remainBackwardPrivacyUpperBound, List<BasicPair<Integer, Double>> userBudgetList, Integer startIndex, Integer endIndex) {
        this.outputFileDir = outputFileDir;
        this.timeStampList = timeStampList;
        this.privacyBudgetCandidateList = privacyBudgetCandidateList;
        this.privacyBudgetRatioList = privacyBudgetRatioList;
        this.remainBackwardPrivacyLowerBound = remainBackwardPrivacyLowerBound;
        this.remainBackwardPrivacyUpperBound = remainBackwardPrivacyUpperBound;
        this.userBudgetList = userBudgetList;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    private void generatePrivacyBudgetWithinGivenTimeRange() {
        String tempOutputFilePath;
        Integer timeStampListSize = timeStampList.size();
        String formatFileNameID;
        BasicWrite basicWrite = new BasicWrite(",");
        String tempString;
        Integer timeStampID;
        for (int i = startIndex; i <= endIndex; ++i) {
            timeStampID = timeStampList.get(i);
            formatFileNameID = FormatFileName.getFormatNumberString(timeStampID, 0, timeStampListSize);
            tempOutputFilePath = StringUtil.join(ConstantValues.FILE_SPLIT, outputFileDir, "timestamp_" + formatFileNameID + ".txt");
            basicWrite.startWriting(tempOutputFilePath);
            for (BasicPair<Integer, Double> userBudgetLowerBoundPair : userBudgetList) {
                Double tempRandomDouble = RandomUtil.getRandomDouble(remainBackwardPrivacyLowerBound, remainBackwardPrivacyUpperBound);
//                Double tempRandomDouble2 = RandomUtil.getRandomDouble(userBudgetLowerBoundPair.getValue(), privacyUpperBound);
                Integer index = RandomUtil.getRandomIndexGivenStatisticPoint(this.privacyBudgetRatioList.toArray(new Double[0]));
                Double tempRandomDouble2 = privacyBudgetCandidateList.get(index);
                tempString = StringUtil.join(",", userBudgetLowerBoundPair.getKey(), NumberUtil.roundFormat(tempRandomDouble, 2), NumberUtil.roundFormat(tempRandomDouble2, 2));
                basicWrite.writeOneLine(tempString);
            }
            basicWrite.endWriting();
        }
    }
    @Override
    public void run() {
        generatePrivacyBudgetWithinGivenTimeRange();
    }
}
