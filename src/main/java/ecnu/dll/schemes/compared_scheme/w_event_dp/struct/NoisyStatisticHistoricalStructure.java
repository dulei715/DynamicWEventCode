package ecnu.dll.schemes.compared_scheme.w_event_dp.struct;

import java.util.*;

public class NoisyStatisticHistoricalStructure extends GeneralizedFixedHistoryStructure<TreeMap<String, Double>>{
    protected ArrayDeque<Boolean> historicalStatusQueue;
    public NoisyStatisticHistoricalStructure(Integer size) {
        super(size);
        this.historicalStatusQueue = new ArrayDeque<>(size);
    }

    @Deprecated
    @Override
    public void add(TreeMap<String, Double> data) {
        throw new RuntimeException("Please apply add(TreeMap<String, Double> data, Boolean status)");
    }

    public void add(TreeMap<String, Double> data, Boolean status) {
        if (this.historicalDataQueue.size() >= this.size) {
            this.historicalDataQueue.poll();
            this.historicalStatusQueue.poll();
        }
        this.historicalDataQueue.offer(data);
        this.historicalStatusQueue.offer(status);
    }

    public List<TreeMap<String, Double>> getEffectiveNoisyStatisticList() {
        Iterator<Boolean> statusIterator = this.historicalStatusQueue.iterator();
        Iterator<TreeMap<String, Double>> statisticIterator = this.historicalDataQueue.iterator();
        List<TreeMap<String, Double>> resultList = new ArrayList<>();
        Boolean currentStatus;
        TreeMap<String, Double> currentTreeMap;
        while (statisticIterator.hasNext()) {
            currentStatus = statusIterator.next();
            currentTreeMap = statisticIterator.next();
            if (currentStatus) {
                resultList.add(currentTreeMap);
            }
        }
        return resultList;
    }
}
