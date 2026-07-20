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
    protected Double partA_BDPVar;
    protected Double partB_BDPVar;
    protected Double partA_BSampleVar;
    protected Double partB_BSampleVar;
    protected Double partA_BCountVar;
    protected Double partB_BCountVar;
    protected Double partA_BiasSquare;
    protected Double partB_BiasSquare;
    protected Double partA_MDPVar;
    protected Double partB_MDPVar;
    protected Double partA_MSampleVar;
    protected Double partB_MSampleVar;
    protected Double partA_MCountVar;
    protected Double partB_MCountVar;
    protected Double partA_MBiasSquare;
    protected Double partB_MBiasSquare;
    protected Integer nonNullCount;
    protected Double nonNullMCount;

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
        Double partA_BDPVar = 0D, partB_BDPVar = 0D, partA_BSampleVar = 0D, partB_BSampleVar = 0D;
        Double partA_BCountVar = 0D, partB_BCountVar = 0D, partA_BiasSquare = 0D, partB_BiasSquare = 0D;
        Double partA_MDPVar = 0D, partB_MDPVar = 0D, partA_MSampleVar = 0D, partB_MSampleVar = 0D;
        Double partA_MCountVar = 0D, partB_MCountVar = 0D, partA_MBiasSquare = 0D, partB_MBiasSquare = 0D;
        Integer nonNullCount = 0;
        Double nonNullMCount = 0D;
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
        } else if (data.length <= 30) {
            bjsd = Double.valueOf(data[7]);
            bwd = Double.valueOf(data[8]);
            partA_BDPVar = Double.valueOf(data[9]);
            partB_BDPVar = Double.valueOf(data[10]);
            partA_BSampleVar = Double.valueOf(data[11]);
            partB_BSampleVar = Double.valueOf(data[12]);
            partA_BCountVar = Double.valueOf(data[13]);
            partB_BCountVar = Double.valueOf(data[14]);
            partA_BiasSquare = Double.valueOf(data[15]);
            partB_BiasSquare = Double.valueOf(data[16]);
            nonNullCount = Integer.valueOf(data[17]);

            mre = Double.valueOf(data[18]);
            mjsd = Double.valueOf(data[19]);
            mwd = Double.valueOf(data[20]);
            partA_MDPVar = Double.valueOf(data[21]);
            partB_MDPVar = Double.valueOf(data[22]);
            partA_MSampleVar = Double.valueOf(data[23]);
            partB_MSampleVar = Double.valueOf(data[24]);
            partA_MCountVar = Double.valueOf(data[25]);
            partB_MCountVar = Double.valueOf(data[26]);
            partA_MBiasSquare = Double.valueOf(data[27]);
            partB_MBiasSquare = Double.valueOf(data[28]);
            nonNullMCount = Double.valueOf(data[29]);
        }
        return new ResultBean(name, batchID, batchSize, timeCost, privacyBudget, windowSize,
                bre, bjsd, bwd, partA_BDPVar, partB_BDPVar, partA_BSampleVar, partB_BSampleVar,
                partA_BCountVar, partB_BCountVar, partA_BiasSquare, partB_BiasSquare, nonNullCount,
                mre, mjsd, mwd, partA_MDPVar, partB_MDPVar, partA_MSampleVar, partB_MSampleVar,
                partA_MCountVar, partB_MCountVar, partA_MBiasSquare, partB_MBiasSquare, nonNullMCount);
    }



    public ResultBean() {
    }

    public ResultBean(String name, Integer batchID, Integer batchSize, Long timeCost, Double privacyBudget, Integer windowSize, Double bre, Double bjsd, Double bwd,
                      Double mre, Double mjsd, Double mwd) {
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
    public ResultBean(String name, Integer batchID, Integer batchSize, Long timeCost, Double privacyBudget, Integer windowSize,
                      Double bre, Double bjsd, Double bwd,
                      Double partA_BDPVar, Double partB_BDPVar, Double partA_BSampleVar, Double partB_BSampleVar,
                      Double partA_BCountVar, Double partB_BCountVar, Double partA_BiasSquare, Double partB_BiasSquare,
                      Integer nonNullCount,
                      Double mre, Double mjsd, Double mwd,
                      Double partA_MDPVar, Double partB_MDPVar, Double PartA_MSampleVar, Double PartB_MSampleVar,
                      Double partA_MCountVar, Double partB_MCountVar, Double partA_MBiasSquare, Double partB_MBiasSquare,
                      Double nonNullMCount) {
        this.name = name;
        this.batchID = batchID;
        this.batchSize = batchSize;
        this.timeCost = timeCost;
        this.privacyBudget = privacyBudget;
        this.windowSize = windowSize;

        this.bre = bre;
        this.bjsd = bjsd;
        this.bwd = bwd;
        this.partA_BDPVar = partA_BDPVar;
        this.partB_BDPVar = partB_BDPVar;
        this.partA_BSampleVar = partA_BSampleVar;
        this.partB_BSampleVar = partB_BSampleVar;
        this.partA_BCountVar = partA_BCountVar;
        this.partB_BCountVar = partB_BCountVar;
        this.partA_BiasSquare = partA_BiasSquare;
        this.partB_BiasSquare = partB_BiasSquare;
        this.nonNullCount = nonNullCount;

        this.mre = mre;
        this.mjsd = mjsd;
        this.mwd = mwd;
        this.partA_MDPVar = partA_MDPVar;
        this.partB_MDPVar = partB_MDPVar;
        this.partA_MSampleVar = PartA_MSampleVar;
        this.partB_MSampleVar = PartB_MSampleVar;
        this.partA_MCountVar = partA_MCountVar;
        this.partB_MCountVar = partB_MCountVar;
        this.partA_MBiasSquare = partA_MBiasSquare;
        this.partB_MBiasSquare = partB_MBiasSquare;
        this.nonNullMCount = nonNullMCount;


    }

    public static ResultBean getInitializedBean(ResultBean modelBean) {
        return new ResultBean(modelBean.getName(), -1, 0, 0L, modelBean.getPrivacyBudget(), modelBean.getWindowSize(), 0D, 0D, 0D, 0D, 0D, 0D);
    }
    public static ResultBean getInitializedBeanContainingErrorDetails(ResultBean modelBean) {
        return new ResultBean(modelBean.getName(), -1, 0, 0L, modelBean.getPrivacyBudget(), modelBean.getWindowSize(),
                0D, 0D, 0D, 0D, 0D, 0D, 0D,
                0D, 0D, 0D, 0D, 0,
                0D, 0D, 0D, 0D, 0D, 0D, 0D,
                0D, 0D, 0D, 0D, 0D);
    }

    public static ResultBean getInitializedBean(String modelBeanName, Double privacyBudget, Integer windowSize) {
        return new ResultBean(modelBeanName, -1, 0, 0L, privacyBudget, windowSize, 0D, 0D, 0D, 0D, 0D, 0D);
    }

    public static ResultBean getInitializedBeanContainingErrorDetails(String modelBeanName, Double privacyBudget, Integer windowSize) {
        return new ResultBean(modelBeanName, -1, 0, 0L, privacyBudget, windowSize,
                0D, 0D, 0D, 0D, 0D, 0D, 0D,
                0D, 0D, 0D, 0D, 0,
                0D, 0D, 0D, 0D, 0D, 0D, 0D,
                0D, 0D, 0D, 0D, 0D);
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

    public Double getPartA_BDPVar() {
        return partA_BDPVar;
    }

    public void setPartA_BDPVar(Double partA_BDPVar) {
        this.partA_BDPVar = partA_BDPVar;
    }

    public Double getPartB_BDPVar() {
        return partB_BDPVar;
    }

    public void setPartB_BDPVar(Double partB_BDPVar) {
        this.partB_BDPVar = partB_BDPVar;
    }

    public Double getPartA_BSampleVar() {
        return partA_BSampleVar;
    }

    public void setPartA_BSampleVar(Double partA_BSampleVar) {
        this.partA_BSampleVar = partA_BSampleVar;
    }

    public Double getPartB_BSampleVar() {
        return partB_BSampleVar;
    }

    public void setPartB_BSampleVar(Double partB_BSampleVar) {
        this.partB_BSampleVar = partB_BSampleVar;
    }

    public Double getPartA_BCountVar() {
        return partA_BCountVar;
    }

    public void setPartA_BCountVar(Double partA_BCountVar) {
        this.partA_BCountVar = partA_BCountVar;
    }

    public Double getPartB_BCountVar() {
        return partB_BCountVar;
    }

    public void setPartB_BCountVar(Double partB_BCountVar) {
        this.partB_BCountVar = partB_BCountVar;
    }

    public Double getPartA_BiasSquare() {
        return partA_BiasSquare;
    }

    public void setPartA_BiasSquare(Double partA_BiasSquare) {
        this.partA_BiasSquare = partA_BiasSquare;
    }

    public Double getPartB_BiasSquare() {
        return partB_BiasSquare;
    }

    public void setPartB_BiasSquare(Double partB_BiasSquare) {
        this.partB_BiasSquare = partB_BiasSquare;
    }

    public Double getPartA_MDPVar() {
        return partA_MDPVar;
    }

    public void setPartA_MDPVar(Double partA_MDPVar) {
        this.partA_MDPVar = partA_MDPVar;
    }

    public Double getPartB_MDPVar() {
        return partB_MDPVar;
    }

    public void setPartB_MDPVar(Double partB_MDPVar) {
        this.partB_MDPVar = partB_MDPVar;
    }

    public Double getPartA_MSampleVar() {
        return partA_MSampleVar;
    }

    public void setPartA_MSampleVar(Double partA_MSampleVar) {
        this.partA_MSampleVar = partA_MSampleVar;
    }

    public Double getPartB_MSampleVar() {
        return partB_MSampleVar;
    }

    public void setPartB_MSampleVar(Double partB_MSampleVar) {
        this.partB_MSampleVar = partB_MSampleVar;
    }

    public Double getPartA_MCountVar() {
        return partA_MCountVar;
    }

    public void setPartA_MCountVar(Double partA_MCountVar) {
        this.partA_MCountVar = partA_MCountVar;
    }

    public Double getPartB_MCountVar() {
        return partB_MCountVar;
    }

    public void setPartB_MCountVar(Double partB_MCountVar) {
        this.partB_MCountVar = partB_MCountVar;
    }

    public Double getPartA_MBiasSquare() {
        return partA_MBiasSquare;
    }

    public void setPartA_MBiasSquare(Double partA_MBiasSquare) {
        this.partA_MBiasSquare = partA_MBiasSquare;
    }

    public Double getPartB_MBiasSquare() {
        return partB_MBiasSquare;
    }

    public void setPartB_MBiasSquare(Double partB_MBiasSquare) {
        this.partB_MBiasSquare = partB_MBiasSquare;
    }

    public Integer getNonNullCount() {
        return nonNullCount;
    }

    public void setNonNullCount(Integer nonNullCount) {
        this.nonNullCount = nonNullCount;
    }

    public Double getNonNullMCount() {
        return nonNullMCount;
    }

    public void setNonNullMCount(Double nonNullMCount) {
        this.nonNullMCount = nonNullMCount;
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
    public String toFormatErrorDetailsString() {
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
        stringBuilder.append(this.partA_BDPVar).append(",");
        stringBuilder.append(this.partB_BDPVar).append(",");
        stringBuilder.append(this.partA_BSampleVar).append(",");
        stringBuilder.append(this.partB_BSampleVar).append(",");
        stringBuilder.append(this.partA_BCountVar).append(",");
        stringBuilder.append(this.partB_BCountVar).append(",");
        stringBuilder.append(this.partA_BiasSquare).append(",");
        stringBuilder.append(this.partB_BiasSquare).append(",");
        stringBuilder.append(this.nonNullCount).append(",");
        stringBuilder.append(this.mre).append(",");
        stringBuilder.append(this.mjsd).append( ",");
        stringBuilder.append(this.mwd).append(",");
        stringBuilder.append(this.partA_MDPVar).append(",");
        stringBuilder.append(this.partB_MDPVar).append(",");
        stringBuilder.append(this.partA_MSampleVar).append(",");
        stringBuilder.append(this.partB_MSampleVar).append(",");
        stringBuilder.append(this.partA_MCountVar).append(",");
        stringBuilder.append(this.partB_MCountVar).append(",");
        stringBuilder.append(this.partA_MBiasSquare).append(",");
        stringBuilder.append(this.partB_MBiasSquare).append(",");
        stringBuilder.append(this.nonNullMCount);
        return stringBuilder.toString();
    }
}
