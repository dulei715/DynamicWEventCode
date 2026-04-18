package ecnu.dll.struts.stream_data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StreamCountData {
    protected Integer timeSlot;
    protected TreeMap<String, Integer> dataMap;

    public StreamCountData(Integer timeSlot, TreeMap<String, Integer> dataMap) {
        this.timeSlot = timeSlot;
        this.dataMap = dataMap;
    }

    public StreamCountData(Integer timeSlot, List<String> dataType) {
        this.timeSlot = timeSlot;
        this.dataMap = new TreeMap<>();
        for (String keyName : dataType) {
            this.dataMap.put(keyName, 0);
        }
    }

    public StreamNoiseCountData convertToNoise() {
        TreeMap<String, Double> resultMap = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue()*1.0);
        }
        return new StreamNoiseCountData(this.timeSlot, resultMap);
    }


    public Integer getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Integer timeSlot) {
        this.timeSlot = timeSlot;
    }

    public TreeMap<String, Integer> getDataMap() {
        return dataMap;
    }

    public void setDataMap(TreeMap<String, Integer> dataMap) {
        this.dataMap = dataMap;
    }

    public void addEntry(String key, Integer value) {
        this.dataMap.put(key, value);
    }
}
