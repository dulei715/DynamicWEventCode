package ecnu.dll.schemes.basic_scheme.personalized_dp;

import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.differential_privacy.noise.LaplaceUtils;

import java.util.*;
@Deprecated
public class PersonalizedDP<T> {
    protected List<T> elementList = null;
    protected List<Double> privacyBudgetList = null;
    protected List<Double> probabilityList = null;
    protected Double budgetThreshold = null;


    public PersonalizedDP(List<T> elementList, List<Double> privacyBudgetList, Double budgetThreshold) {
        this.elementList = elementList;
        this.privacyBudgetList = privacyBudgetList;
        this.budgetThreshold = budgetThreshold;
        updateProbabilityList();
    }

    public void setPrivacyBudgetList(List<Double> privacyBudgetList) {
        this.privacyBudgetList = privacyBudgetList;
        updateProbabilityList();
    }

    public void setBudgetThreshold(Double budgetThreshold) {
        this.budgetThreshold = budgetThreshold;
        updateProbabilityList();
    }

    protected void updateProbabilityList() {
        if (probabilityList == null) {
            this.probabilityList = new ArrayList<Double>();
        } else {
            this.probabilityList.clear();
        }
        for (Double budget : this.privacyBudgetList) {
            if (budget < this.budgetThreshold) {
                this.probabilityList.add((Math.exp(budget)-1)/(Math.exp(this.budgetThreshold)-1));
            } else {
                this.probabilityList.add(1D);
            }
        }
    }

    public List<Double> getSampleProbability() {
        return this.probabilityList;
    }

    protected boolean isChosen(Integer index) {
        Double probability = this.probabilityList.get(index);
        return RandomUtil.isChosen(probability);
    }

    public List<T> sampleElement() {
        List<T> result = new ArrayList<T>();
        for (int index = 0; index < this.elementList.size(); index++) {
            if (isChosen(index)) {
                result.add(this.elementList.get(index));
            }
        }
        return result;
    }

    public List<Integer> sampleIndex() {
        List<Integer> result = new ArrayList<Integer>();
        for (int index = 0; index < this.elementList.size(); index++) {
            if (isChosen(index)) {
                result.add(index);
            }
        }
        return result;
    }

    public TreeMap<Double, Integer> getRealStatisticByPrivacyBudget() {
        TreeMap<Double, Integer> result = new TreeMap<Double, Integer>();
        Double tempBudget;
        Integer tempCount;
        for (int i = 0; i < this.privacyBudgetList.size(); i++) {
            tempBudget = this.privacyBudgetList.get(i);
            tempCount = result.get(tempBudget);
            if (tempBudget == null) {
                tempCount = 1;
            } else {
                ++tempCount;
            }
            result.put(tempBudget, tempCount);
        }
        return result;
    }

    public TreeMap<Double, Integer> getSampleStatisticByPrivacyBudget() {
        TreeMap<Double, Integer> result = new TreeMap<Double, Integer>();
        Double tempBudget;
        Integer tempCount;
        List<Integer> indexList = this.sampleIndex();
        for (Integer index : indexList) {
            tempBudget = this.privacyBudgetList.get(index);
            tempCount = result.get(tempBudget);
            if (tempBudget == null) {
                tempCount = 1;
            } else {
                ++tempCount;
            }
            result.put(tempBudget, tempCount);
        }
        return result;
    }

    public static TreeMap<Double, Double> getNoiseCount(TreeMap<Double, Integer> data) {
        Double tempBudget;
        Integer tempCount;
        Double tempNoiseCount;
        TreeMap<Double, Double> result = new TreeMap<Double, Double>();
        for (Map.Entry<Double, Integer> entry : data.entrySet()) {
            tempBudget = entry.getKey();
            tempCount = entry.getValue();
            tempNoiseCount = tempCount + LaplaceUtils.getLaplaceNoise(1, tempBudget);
            result.put(tempBudget, tempNoiseCount);
        }
        return result;
    }


}
