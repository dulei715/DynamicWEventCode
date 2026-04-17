package ecnu.dll.schemes.compared_scheme.w_event_dp.struct;

import java.util.ArrayDeque;

public abstract class GeneralizedFixedHistoryStructure<T> {
    protected Integer size;
    protected ArrayDeque<T> historicalDataQueue;
    public GeneralizedFixedHistoryStructure(Integer size) {
        this.size = size;
        this.historicalDataQueue = new ArrayDeque<>(size);
    }

    public void add(T data) {
        if (this.historicalDataQueue.size() >= this.size) {
            T outElement = this.historicalDataQueue.poll();
        }
        this.historicalDataQueue.offer(data);
    }


}
