package ecnu.dll.run.c_dataset_run.utils;

import cn.edu.dll.struct.bean_structs.BeanInterface;

public class ResultBeanBefore implements BeanInterface<ResultBeanBefore> {
    private String name;
    private Integer batchID;
    private Long timeCost;
    private Double privacyBudget;
    private Integer windowSize;
    private Double bRE;




    public ResultBeanBefore(String name, Integer batchID, Long timeCost, Double privacyBudget, Integer windowSize, Double bRE) {
        this.name = name;
        this.batchID = batchID;
        this.timeCost = timeCost;
        this.privacyBudget = privacyBudget;
        this.windowSize = windowSize;
        this.bRE = bRE;
    }

    public String toCSVString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name).append(",");
        stringBuilder.append(this.batchID).append(",");
        stringBuilder.append(this.timeCost).append(",");
        stringBuilder.append(this.privacyBudget).append(",");
        stringBuilder.append(this.windowSize).append(",");
        stringBuilder.append(this.bRE);
        return stringBuilder.toString();
    }

    public ResultBeanBefore() {
    }

    public static ResultBeanBefore getInitializedBean(ResultBeanBefore modelBean) {
        return new ResultBeanBefore(modelBean.getName(), -1, 0L, modelBean.getPrivacyBudget(), modelBean.getWindowSize(), 0D);
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



    public static ResultBeanBefore toBean(String line) {
        String[] data = line.split(",");
        String name = data[0];
        Integer batchID = Integer.valueOf(data[1]);
        Long timeCost = Long.valueOf(data[2]);
        Double privacyBudget = Double.valueOf(data[3]);
        Integer windowSize = (int) Math.round(Double.valueOf(data[4]));
        Double bRE = Double.valueOf(data[5]);
        return new ResultBeanBefore(name, batchID, timeCost, privacyBudget, windowSize, bRE);
    }

    @Override
    public ResultBeanBefore toBean(String[] data) {
        String name = data[0];
        Integer batchID = Integer.valueOf(data[1]);
        Long timeCost = Long.valueOf(data[2]);
        Double privacyBudget = Double.valueOf(data[3]);
        Integer windowSize = Integer.valueOf(data[4]);
        Double bRE = Double.valueOf(data[5]);
        return new ResultBeanBefore(name, batchID, timeCost, privacyBudget, windowSize, bRE);
    }
}
