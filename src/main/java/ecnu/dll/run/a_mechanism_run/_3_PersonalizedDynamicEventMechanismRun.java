package ecnu.dll.run.a_mechanism_run;

import cn.edu.dll.basic.MatrixArray;
import cn.edu.dll.result.ExperimentResult;
import cn.edu.dll.statistic.StatisticTool;
import ecnu.dll._config.Constant;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.PersonalizedEventMechanism;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.PersonalizedDynamicWindowSizeMechanism;
import ecnu.dll.struts.stream_data.StreamCountData;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class _3_PersonalizedDynamicEventMechanismRun {
    public static ExperimentResult run(Class clazz, List<String> dataType, List<List<StreamDataElement<Boolean>>> dataList, List<StreamCountData> rawPublicationList, List<List<Double>> backwardPrivacyBudgetListList, List<List<Integer>> backwardWindowSizeListList, List<List<Double>> forwardPrivacyBudgetListList, List<List<Integer>> forwardWindowSizeListList) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor constructor = clazz.getDeclaredConstructor(List.class, Integer.class);
//        WEventMechanism scheme = new BudgetDistribution(dataType, privacyBudget, windowSize);
        int userSize = dataList.get(0).size();
        PersonalizedDynamicWindowSizeMechanism scheme = (PersonalizedDynamicWindowSizeMechanism) constructor.newInstance(dataType, userSize);

        ExperimentResult experimentResult = new ExperimentResult();
        int timeUpperBound = dataList.size();
        StreamCountData rawPublicationData;
        StreamNoiseCountData publicationData;
        long startTime, endTime, timeCost;
        double varianceStatistic = 0;
        List<StreamNoiseCountData> publicationList = new ArrayList<>(timeUpperBound);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < timeUpperBound; i++) {
            scheme.updateNextPublicationResult(dataList.get(i), backwardPrivacyBudgetListList.get(i), backwardWindowSizeListList.get(i), forwardPrivacyBudgetListList.get(i), forwardWindowSizeListList.get(i));
            publicationList.add(scheme.getReleaseNoiseCountMap());
        }
        endTime = System.currentTimeMillis();
        timeCost = endTime - startTime;
        experimentResult.addPair(Constant.MechanismName, scheme.getSimpleName());
        experimentResult.addPair(Constant.TimeCost, String.valueOf(timeCost));
        experimentResult.addPair(Constant.PrivacyBudget, String.valueOf(Math.min(MatrixArray.getMinimalValue(backwardPrivacyBudgetListList), MatrixArray.getMinimalValue(forwardPrivacyBudgetListList))));
        experimentResult.addPair(Constant.WindowSize, String.valueOf(Math.max(MatrixArray.getMaximalValue(backwardWindowSizeListList,1), MatrixArray.getMaximalValue(forwardWindowSizeListList, 1))));
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
