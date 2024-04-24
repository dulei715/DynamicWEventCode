package ecnu.dll.struts;

import cn.edu.dll.basic.BasicArrayUtil;

import java.util.List;

public class Data {
    protected List<String> attributeList;
    protected List<Double> valueList;

    public Data(List<String> attributeList, List<Double> valueList) {
        this.attributeList = attributeList;
        this.valueList = valueList;
    }

    public Data(List<Double> valueList) {
        int size = valueList.size();
        this.attributeList = BasicArrayUtil.getIncreaseIntegerNumberListAsStringList(0, 1, size - 1);
        this.valueList = valueList;
    }

    public List<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<String> attributeList) {
        this.attributeList = attributeList;
    }

    public List<Double> getValueList() {
        return valueList;
    }

    public void setValueList(List<Double> valueList) {
        this.valueList = valueList;
    }
}
