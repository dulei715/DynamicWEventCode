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
    protected Double bjsd;
    protected Double mjsd;
    protected Double bwd;
    protected Double mwd;

    @Override
    public ResultBean toBean(String[] data) {
        String name = data[0];
        Integer batchID = Integer.valueOf(data[1]);
        Integer batchSize = Integer.valueOf(data[2]);
        Long timeCost = Long.valueOf(data[3]);
        Double privacyBudget = Double.valueOf(data[4]);
        Integer windowSize = Integer.valueOf(data[5]);
        Double bre = Double.valueOf(data[6]);
        Double mre = 0D, bjsd = 0D, mjsd = 0D, bwd = 0D, mwd = 0D;
        if (data.length <= 8) {
            mre = Double.valueOf(data[7]);
        } else if (data.length <= 10) {
            bjsd = Double.valueOf(data[7]);
            mre = Double.valueOf(data[8]);
            mjsd = Double.valueOf(data[9]);
        } else if (data.length <= 12) {
            bjsd = Double.valueOf(data[7]);
            bwd = Double.valueOf(data[8]);
            mre = Double.valueOf(data[9]);
            mjsd = Double.valueOf(data[10]);
            mwd = Double.valueOf(data[11]);
        }
        return new ResultBean(name, batchID, batchSize, timeCost, privacyBudget, windowSize, bre, bjsd, bwd, mre, mjsd, mwd);
    }



    public ResultBean() {
    }

    public ResultBean(String name, Integer batchID, Integer batchSize, Long timeCost, Double privacyBudget, Integer windowSize, Double bre, Double bjsd, Double bwd, Double mre, Double mjsd, Double mwd) {
        this.name = name;
        this.batchID = batchID;
        this.batchSize = batchSize;
        this.timeCost = timeCost;
        this.privacyBudget = privacyBudget;
        this.windowSize = windowSize;
        this.bre = bre;
        this.bjsd = bjsd;
        this.bwd = bwd;
        this.mre = mre;
        this.mjsd = mjsd;
        this.mwd = mwd;
    }

    public static ResultBean getInitializedBean(ResultBean modelBean) {
        return new ResultBean(modelBean.getName(), -1, 0, 0L, modelBean.getPrivacyBudget(), modelBean.getWindowSize(), 0D, 0D, 0D, 0D, 0D, 0D);
    }

    public static ResultBean getInitializedBean(String modelBeanName, Double privacyBudget, Integer windowSize) {
        return new ResultBean(modelBeanName, -1, 0, 0L, privacyBudget, windowSize, 0D, 0D, 0D, 0D, 0D, 0D);
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

    public Double getBjsd() {
        return bjsd;
    }

    public void setBjsd(Double bjsd) {
        this.bjsd = bjsd;
    }

    public Double getMjsd() {
        return mjsd;
    }

    public void setMjsd(Double mjsd) {
        this.mjsd = mjsd;
    }

    public Double getBwd() {
        return bwd;
    }

    public void setBwd(Double bwd) {
        this.bwd = bwd;
    }

    public Double getMwd() {
        return mwd;
    }

    public void setMwd(Double mwd) {
        this.mwd = mwd;
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
                ", bjsd=" + bjsd +
                ", bwd=" + bwd +
                ", mre=" + mre +
                ", mjsd=" + mjsd +
                ", mwd=" + mwd +
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
        stringBuilder.append(this.bjsd).append(",");
        stringBuilder.append(this.bwd).append(",");
        stringBuilder.append(this.mre).append(",");
        stringBuilder.append(this.mjsd).append( ",");
        stringBuilder.append(this.mwd);
        return stringBuilder.toString();
    }
}
