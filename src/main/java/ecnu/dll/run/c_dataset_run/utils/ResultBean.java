package ecnu.dll.run.c_dataset_run.utils;

import cn.edu.dll.struct.bean_structs.BeanInterface;

public class ResultBean implements BeanInterface<ResultBean> {
    protected String name;
    protected Integer batchID;
    protected Integer batchSize;
    protected Long timeCost;
    protected Double privacyBudget;
    protected Integer windowSize;
    protected Double bre;
    protected Double mre;

    @Override
    public ResultBean toBean(String[] data) {
        String name = data[0];
        Integer batchID = Integer.valueOf(data[1]);
        Integer batchSize = Integer.valueOf(data[2]);
        Long timeCost = Long.valueOf(data[3]);
        Double privacyBudget = Double.valueOf(data[4]);
        Integer windowSize = Integer.valueOf(data[5]);
        Double bre = Double.valueOf(data[6]);
        Double mre = Double.valueOf(data[7]);
        return new ResultBean(name, batchID, batchSize, timeCost, privacyBudget, windowSize, bre, mre);

    }



    public ResultBean() {
    }

    public ResultBean(String name, Integer batchID, Integer batchSize, Long timeCost, Double privacyBudget, Integer windowSize, Double bre, Double mre) {
        this.name = name;
        this.batchID = batchID;
        this.batchSize = batchSize;
        this.timeCost = timeCost;
        this.privacyBudget = privacyBudget;
        this.windowSize = windowSize;
        this.bre = bre;
        this.mre = mre;
    }

    public static ResultBean getInitializedBean(ResultBean modelBean) {
        return new ResultBean(modelBean.getName(), -1, 0, 0L, modelBean.getPrivacyBudget(), modelBean.getWindowSize(), 0D, 0D);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBatchID() {
        return batchID;
    }

    public void setBatchID(Integer batchID) {
        this.batchID = batchID;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Long timeCost) {
        this.timeCost = timeCost;
    }

    public Double getPrivacyBudget() {
        return privacyBudget;
    }

    public void setPrivacyBudget(Double privacyBudget) {
        this.privacyBudget = privacyBudget;
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Integer windowSize) {
        this.windowSize = windowSize;
    }

    public Double getBre() {
        return bre;
    }

    public void setBre(Double bre) {
        this.bre = bre;
    }

    public Double getMre() {
        return mre;
    }

    public void setMre(Double mre) {
        this.mre = mre;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "name='" + name + '\'' +
                ", batchID=" + batchID +
                ", batchSize=" + batchSize +
                ", timeCost=" + timeCost +
                ", privacyBudget=" + privacyBudget +
                ", windowSize=" + windowSize +
                ", bre=" + bre +
                ", mre=" + mre +
                '}';
    }

    @Override
    public String toFormatString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name).append(",");
        stringBuilder.append(this.batchID).append(",");
        stringBuilder.append(this.batchSize).append(",");
        stringBuilder.append(this.timeCost).append(",");
        stringBuilder.append(this.privacyBudget).append(",");
        stringBuilder.append(this.windowSize).append(",");
        stringBuilder.append(this.bre).append(",");
        stringBuilder.append(this.mre);
        return stringBuilder.toString();
    }
}
