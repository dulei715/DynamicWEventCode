package ecnu.dll.schemes.compared_scheme.w_event_dp.struct;

public class EpsilonHistoricalStructure extends GeneralizedFixedHistoryStructure<Double> {
    protected Double sum;

    public EpsilonHistoricalStructure(Integer size) {
        super(size);
        this.sum = 0D;
    }

    @Override
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
