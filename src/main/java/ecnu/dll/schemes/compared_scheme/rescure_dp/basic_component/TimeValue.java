package ecnu.dll.schemes.compared_scheme.rescure_dp.basic_component;

public class TimeValue {
    private Integer timePoint;
    private Double statisticValue;

    public TimeValue(Integer timePoint, Double statisticValue) {
        this.timePoint = timePoint;
        this.statisticValue = statisticValue;
    }

    public Integer getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Integer timePoint) {
        this.timePoint = timePoint;
    }

    public Double getStatisticValue() {
        return statisticValue;
    }

    public void setStatisticValue(Double statisticValue) {
        this.statisticValue = statisticValue;
    }

    @Override
    public String toString() {
        return "TimeValue{" +
                "timePoint=" + timePoint +
                ", statisticValue=" + statisticValue +
                '}';
    }
}
