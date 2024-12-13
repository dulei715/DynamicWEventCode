package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.ldp.impl;

import cn.edu.dll.differential_privacy.ldp.frequency_oracle.foImp.GeneralizedRandomizedResponse;
import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.ldp.PersonalizedLDPEventMechanism;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.*;

public class PersonalizedLDPBudgetUniform extends PersonalizedLDPEventMechanism {
    /**
     * Personalized LDP的一个最naive的实现
     * 针对每个用户，计算其单个窗口的平均budget
     * 取最小的budget作为LDP的预算（保证隐私）
     */
    protected int currentTime;
    protected List<String> dataTypeList;
    protected List<Double> privacyBudgetList;
    protected List<Integer> windowSizeList;

    protected Double minimalAveragePrivacyBudget;

    protected GeneralizedRandomizedResponse<String> generalizedRandomizedResponse;


    protected StreamNoiseCountData lastReleaseNoiseCountMap;


    public PersonalizedLDPBudgetUniform(List<String> dataTypeList, List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        this.currentTime = -1;
        this.dataTypeList = dataTypeList;
        this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, dataTypeList);
        this.privacyBudgetList = privacyBudgetList;
        this.windowSizeList = windowSizeList;
        this.minimalAveragePrivacyBudget = calculateMinimalAveragePrivacyBudget(this.privacyBudgetList, this.windowSizeList);
        this.generalizedRandomizedResponse = new GeneralizedRandomizedResponse<>(this.minimalAveragePrivacyBudget, dataTypeList.toArray(new String[0]));
    }


    public static Double calculateMinimalAveragePrivacyBudget(List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        Double minimalAveragePrivacyBudget = privacyBudgetList.get(0) / windowSizeList.get(0);
        int size = privacyBudgetList.size();
        for (int i = 1; i < size; ++i) {
            minimalAveragePrivacyBudget = Math.min(minimalAveragePrivacyBudget, privacyBudgetList.get(i) / windowSizeList.get(i));
        }
        return minimalAveragePrivacyBudget;
    }


    public void setParameters(List<Double> privacyBudgetList, List<Integer> windowSizeList) {
        this.privacyBudgetList = privacyBudgetList;
        this.windowSizeList = windowSizeList;
        this.minimalAveragePrivacyBudget = calculateMinimalAveragePrivacyBudget(this.privacyBudgetList, this.windowSizeList);
        this.generalizedRandomizedResponse = new GeneralizedRandomizedResponse<>(this.minimalAveragePrivacyBudget, this.dataTypeList.toArray(new String[0]));
    }

    public List<Double> getPrivacyBudgetList() {
        return privacyBudgetList;
    }

    public List<Integer> getWindowSizeList() {
        return windowSizeList;
    }



    public StreamNoiseCountData getReleaseNoiseCountMap() {
        return this.lastReleaseNoiseCountMap;
    }

    private static List<String> extractRealValue(List<StreamDataElement<Boolean>> data, Boolean targetValue) {
        int size = data.size();
        boolean status;
        List<String> result = new ArrayList<>(size);
        for (StreamDataElement<Boolean> element : data) {
            status = false;
            for (Map.Entry<String, Boolean> entry : element.getDataMap().entrySet()) {
                if (targetValue.equals(entry.getValue())) {
                    status = true;
                    result.add(entry.getKey());
                    break;
                }
            }
            if (status == false) {
                throw new RuntimeException("Not find target value!");
            }
        }
        return result;
    }

    private static TreeMap<String, Double> getCount(Set<String> basicElementSet, List<String> data) {
        TreeMap<String, Double> result = new TreeMap<>();
        for (String element : basicElementSet) {
            result.put(element, 0D);
        }
        for (String str : data) {
            Double tempCount = result.get(str);
            ++tempCount;
            result.put(str, tempCount);
        }
        return result;
    }

    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {

        ++this.currentTime;
        TreeSet<String> basicElementSet = new TreeSet<>(nextDataElementList.get(0).getKeyList());
        List<String> realDataList = extractRealValue(nextDataElementList, true);
        int size = realDataList.size();
        List<String> noiseDataList = new ArrayList<>(size);
        for (String realData : realDataList) {
            noiseDataList.add(this.generalizedRandomizedResponse.perturb(realData));
        }
        TreeMap<String, Double> noiseCount = getCount(basicElementSet, noiseDataList);
        this.lastReleaseNoiseCountMap = new StreamNoiseCountData(this.currentTime, noiseCount);
        return true;
    }
    public String getSimpleName() {
        return "PLBU";
    }
}
