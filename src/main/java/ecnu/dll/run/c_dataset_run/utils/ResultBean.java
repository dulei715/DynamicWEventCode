package ecnu.dll.run.c_dataset_run.utils;

import cn.edu.dll.struct.bean_structs.BeanInterface;

public class ResultBean implements BeanInterface<ResultBean> {
    private String name;
    private Integer batchID;
    private Integer batchSize;
    private Long timeCost;
    private Double privacyBudget;
    private Integer windowSize;
    private Double bRE;

    private Double mRE = 0D;






    public ResultBean(String name, Integer batchID, Integer batchSize, Long timeCost, Double privacyBudget, Integer windowSize, Double bRE) {
        this.name = name;
        this.batchID = batchID;
        this.batchSize = batchSize;
        this.timeCost = timeCost;
        this.privacyBudget = privacyBudget;
        this.windowSize = windowSize;
        this.bRE = bRE;
    }

    public String toCSVString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name).append(",");
        stringBuilder.append(this.batchID).append(",");
        stringBuilder.append(this.batchSize).append(",");
        stringBuilder.append(this.timeCost).append(",");
        stringBuilder.append(this.privacyBudget).append(",");
        stringBuilder.append(this.windowSize).append(",");
        stringBuilder.append(this.bRE).append(",");
        stringBuilder.append(this.mRE);
        return stringBuilder.toString();
    }

    public ResultBean() {
    }

    public static ResultBean getInitializedBean(ResultBean modelBean) {
        return new ResultBean(modelBean.getName(), -1, 0, 0L, modelBean.getPrivacyBudget(), modelBean.getWindowSize(), 0D);
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

    public Double getbRE() {
        return bRE;
    }

    public void setbRE(Double bRE) {
        this.bRE = bRE;
    }

    public Double getmRE() {
        return mRE;
    }

    public void setmRE(Double mRE) {
        this.mRE = mRE;
    }

    public static ResultBean toBean(String line) {
        String[] data = line.split(",");
        String name = data[0];
        Integer batchID = Integer.valueOf(data[1]);
        Integer batchSize = Integer.valueOf(data[2]);
        Long timeCost = Long.valueOf(data[3]);
        Double privacyBudget = Double.valueOf(data[4]);
        Double tempDouble = Double.valueOf(data[5]);
        Integer windowSize = (int)Math.round(tempDouble);
        Double bRE = Double.valueOf(data[6]);
        return new ResultBean(name, batchID, batchSize, timeCost, privacyBudget, windowSize, bRE);
    }

    @Override
    public ResultBean toBean(String[] data) {
        String name = data[0];
        Integer batchID = Integer.valueOf(data[1]);
        Integer batchSize = Integer.valueOf(data[2]);
        Long timeCost = Long.valueOf(data[3]);
        Double privacyBudget = Double.valueOf(data[4]);
        Integer windowSize = Integer.valueOf(data[5]);
        Double bRE = Double.valueOf(data[6]);
        return new ResultBean(name, batchID, batchSize, timeCost, privacyBudget, windowSize, bRE);
    }
}
