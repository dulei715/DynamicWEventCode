package ecnu.dll.struts;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.statistic.StatisticTool;

import java.util.*;

public class StreamDataElement<T extends Comparable<T>> {

    protected TreeMap<String, T> dataMap;

    public StreamDataElement(TreeMap<String, T> dataMap) {
        this.dataMap = dataMap;
    }

    public StreamDataElement(List<T> valueList) {
        this.dataMap = new TreeMap<>();
        for (int i = 0; i < valueList.size(); i++) {
            this.dataMap.put("key_"+i, valueList.get(i));
        }
    }

    public TreeMap<String, T> getDataMap() {
        return dataMap;
    }

    public T getValue(String key) {
        return this.dataMap.get(key);
    }

    public List<String> getKeyList() {
        return new ArrayList<>(this.dataMap.keySet());
    }

    public List<T> getValueList() {
        return new ArrayList<>(this.dataMap.values());
    }

    protected TreeMap<String, List<T>> extractSubData(List<StreamDataElement<T>> dataList) {
        TreeMap<String, List<T>> treeMapData = new TreeMap<>();
        List<String> keyList = dataList.get(0).getKeyList();
        List<T> tempValueList;
        for (int j = 0; j < keyList.size(); j++) {
            treeMapData.put(keyList.get(j), new ArrayList<T>());
        }
        StreamDataElement<T> tempElement;
        TreeMap<String, T> tempDataMap;
        for (int i = 0; i < dataList.size(); i++) {
            tempElement = dataList.get(i);
            tempDataMap = tempElement.getDataMap();
            for (Map.Entry<String, T> entry : tempDataMap.entrySet()) {
                tempValueList = treeMapData.get(entry.getKey());
                tempValueList.add(entry.getValue());
            }
        }
        return treeMapData;
    }

    public TreeMap<String, Integer> getCountByGivenElementType(T elementValue, List<StreamDataElement<T>> dataList, List<Integer> indexList) {
        TreeMap<String, List<T>> typeDataTreeMap = extractSubData(dataList);
        Map<T, Integer> tempMap;
        String tempKey;
        TreeMap<String, Integer> result = new TreeMap<>();
        for (Map.Entry<String, List<T>> entry : typeDataTreeMap.entrySet()) {
            tempKey = entry.getKey();
            tempMap = StatisticTool.countHistogramNumberByGivenElementType(elementValue, entry.getValue(), indexList);
            result.put(tempKey, tempMap.get(elementValue));
        }
        return result;
    }

}
