package ecnu.dll.schemes.compared_scheme.w_event_dp.struct;

import java.util.ArrayDeque;

public class HistoricalStructure {
    protected Integer size;
    protected ArrayDeque<Double> historicalDataQueue;
    protected Double sum;

    public HistoricalStructure(Integer size) {
        this.size = size;
        this.historicalDataQueue = new ArrayDeque<>(size);
        this.sum = 0D;
    }

    public void add(Double data) {
        if (this.historicalDataQueue.size() >= this.size) {
            Double outElement = this.historicalDataQueue.poll();
            this.sum -= outElement;
        }
        this.historicalDataQueue.offer(data);
        this.sum += data;
    }

    public Double getSum() {
        return this.sum;
    }

    public void addZero() {
        this.add(0D);
    }


}
