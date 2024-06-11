package ecnu.dll.run.a_mechanism_run;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes.compared_scheme.w_event.BudgetDistribution;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.ArrayList;
import java.util.List;

public class BDRun {
    public static ExperimentResult run(List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, List<StreamCountData> rawPublicationList, Double privacyBudget, Integer windowSize) {
        BudgetDistribution scheme = new BudgetDistribution(dataType, privacyBudget, windowSize);
        ExperimentResult experimentResult = new ExperimentResult();
        int timeUpperBound = dataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double varianceStatistic = 0;
        List<StreamNoiseCountData> publicationList = new ArrayList<>(timeUpperBound);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeUpperBound; i++) {
            scheme.updateNextPublicationResult(dataList.get(i));
            publicationList.add(scheme.getReleaseNoiseCountMap());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, Constant.budgetDistributionName);
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        for (int i = 0; i < timeUpperBound; i++) {
            rawPublicationData = rawPublicationList.get(i);
            publicationData = publicationList.get(i);
            varianceStatistic += StatisticTool.getVariance(rawPublicationData.getDataMap(), publicationData.getDataMap());
        }
        varianceStatistic /= timeUpperBound;
        experimentResult.addPair(Constant.MRE, String.valueOf(varianceStatistic));
        return experimentResult;
    }
}
