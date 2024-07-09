package ecnu.dll.run.a_mechanism_run;

import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.struct.pair.PurePair;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes.basic_scheme.NonPrivacyMechanism;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;

import java.util.ArrayList;
import java.util.List;

public class _0_NonPrivacyMechanismRun {
    @Deprecated
    public static PurePair<ExperimentResult, List<StreamCountData>> run(List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList) {
        NonPrivacyMechanism scheme = new NonPrivacyMechanism(dataType);
        ExperimentResult experimentResult = new ExperimentResult();
        int timeUpperBound = dataList.size();
        long startTime, endTime, timeCost;
        List<StreamCountData> publicationList = new ArrayList<>(timeUpperBound);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeUpperBound; i++) {
            scheme.updateNextPublicationResult(dataList.get(i));
            publicationList.add(scheme.getReleaseCountMap());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, Constant.nonPrivacyName);
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(0.0));
        experimentResult.addPair(Constant.WindowSize, String.valueOf(0));
        experimentResult.addPair(Constant.MRE, String.valueOf(0));
        return new PurePair<>(experimentResult, publicationList);
    }

    public static PurePair<ExperimentResult, List<StreamCountData>> runBatch(NonPrivacyMechanism scheme, Integer batchID, List<List<StreamDataElement<Boolean>>> batchDataList) {
//        NonPrivacyMechanism scheme = new NonPrivacyMechanism(dataType);
        ExperimentResult experimentResult = new ExperimentResult();
        int timeBatchSize = batchDataList.size();
        long startTime, endTime, timeCost;
        List<StreamCountData> publicationList = new ArrayList<>(timeBatchSize);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeBatchSize; i++) {
            scheme.updateNextPublicationResult(batchDataList.get(i));
            publicationList.add(scheme.getReleaseCountMap());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, Constant.nonPrivacyName);
        experimentResult.addPair(Constant.BatchName, String.valueOf(batchID));
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(0.0));
        experimentResult.addPair(Constant.WindowSize, String.valueOf(0));
        experimentResult.addPair(Constant.BRE, String.valueOf(0));
        return new PurePair<>(experimentResult, publicationList);
    }

}
