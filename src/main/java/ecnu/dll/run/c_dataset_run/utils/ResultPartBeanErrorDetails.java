package ecnu.dll.run.c_dataset_run.utils;

import cn.edu.dll.struct.bean_structs.BeanInterface;

public class ResultPartBeanErrorDetails extends ResultPartBean {
    protected Double partA_BDPVar;
    protected Double partB_BDPVar;
    protected Double partA_BSampleVar;
    protected Double partB_BSampleVar;
    protected Double partA_BCountVar;
    protected Double partB_BCountVar;
    protected Double partA_BiasSquare;
    protected Double partB_BiasSquare;
    protected Integer nonNullCount;

    protected Double partA_MDPVar = 0D;
    protected Double partB_MDPVar = 0D;
    protected Double partA_MSampleVar = 0D;
    protected Double partB_MSampleVar = 0D;
    protected Double partA_MCountVar = 0D;
    protected Double partB_MCountVar = 0D;
    protected Double partA_MBiasSquare = 0D;
    protected Double partB_MBiasSquare = 0D;
    protected Double nonNullMCount = 0D;

    public ResultPartBeanErrorDetails() {
    }

    public ResultPartBeanErrorDetails(String name, Integer batchID, Integer batchSize, Long timeCost, Double privacyBudget, Integer windowSize,
                                      Double bRE, Double bJSD, Double bWD, Double partA_BDPVar, Double partB_BDPVar,
                                      Double partA_BSampleVar, Double partB_BSampleVar, Double partA_BCountVar,
                                      Double partB_BCountVar, Double partA_BiasSquare, Double partB_BiasSquare,
                                      Integer nonNullCount) {
        super(name, batchID, batchSize, timeCost, privacyBudget, windowSize, bRE, bJSD, bWD);
        this.partA_BDPVar = partA_BDPVar;
        this.partB_BDPVar = partB_BDPVar;
        this.partA_BSampleVar = partA_BSampleVar;
        this.partB_BSampleVar = partB_BSampleVar;
        this.partA_BCountVar = partA_BCountVar;
        this.partB_BCountVar = partB_BCountVar;
        this.partA_BiasSquare = partA_BiasSquare;
        this.partB_BiasSquare = partB_BiasSquare;
        this.nonNullCount = nonNullCount;
    }

    @Override
    public String toCSVString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name).append(",");
        stringBuilder.append(this.batchID).append(",");
        stringBuilder.append(this.batchSize).append(",");
        stringBuilder.append(this.timeCost).append(",");
        stringBuilder.append(this.privacyBudget).append(",");
        stringBuilder.append(this.windowSize).append(",");
        stringBuilder.append(this.bRE).append(",");
        stringBuilder.append(this.bJSD).append(",");
        stringBuilder.append(this.bWD).append(",");
        stringBuilder.append(this.partA_BDPVar).append(",");
        stringBuilder.append(this.partB_BDPVar).append(",");
        stringBuilder.append(this.partA_BSampleVar).append(",");
        stringBuilder.append(this.partB_BSampleVar).append(",");
        stringBuilder.append(this.partA_BCountVar).append(",");
        stringBuilder.append(this.partB_BCountVar).append(",");
        stringBuilder.append(this.partA_BiasSquare).append(",");
        stringBuilder.append(this.partB_BiasSquare).append(",");
        stringBuilder.append(this.nonNullCount).append(",");
        stringBuilder.append(this.mRE).append(",");
        stringBuilder.append(this.mJSD).append(",");
        stringBuilder.append(this.mWD).append(",");
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

    public static ResultPartBeanErrorDetails getInitializedBean(ResultPartBeanErrorDetails modelBean) {
        return new ResultPartBeanErrorDetails(modelBean.getName(), -1, 0,
                0L, modelBean.getPrivacyBudget(), modelBean.getWindowSize(),
                0D, 0D, 0D, 0D,
                0D, 0D, 0D,
                0D, 0D, 0D,
                0D, 0);
    }

    public Double getPartA_BDPVar() {
        return partA_BDPVar;
    }

    public Double getPartB_BDPVar() {
        return partB_BDPVar;
    }

    public Double getPartA_BSampleVar() {
        return partA_BSampleVar;
    }

    public Double getPartB_BSampleVar() {
        return partB_BSampleVar;
    }

    public Double getPartA_BCountVar() {
        return partA_BCountVar;
    }

    public Double getPartB_BCountVar() {
        return partB_BCountVar;
    }

    public Double getPartA_BiasSquare() {
        return partA_BiasSquare;
    }

    public Double getPartB_BiasSquare() {
        return partB_BiasSquare;
    }

    public Integer getNonNullCount() {
        return nonNullCount;
    }

    public Double getPartA_MDPVar() {
        return partA_MDPVar;
    }

    public Double getPartB_MDPVar() {
        return partB_MDPVar;
    }

    public Double getPartA_MSampleVar() {
        return partA_MSampleVar;
    }

    public Double getPartB_MSampleVar() {
        return partB_MSampleVar;
    }

    public Double getPartA_MCountVar() {
        return partA_MCountVar;
    }

    public Double getPartB_MCountVar() {
        return partB_MCountVar;
    }

    public Double getPartA_MBiasSquare() {
        return partA_MBiasSquare;
    }

    public Double getPartB_MBiasSquare() {
        return partB_MBiasSquare;
    }

    public Double getNonNullMCount() {
        return nonNullMCount;
    }

    public void setPartA_BDPVar(Double partA_BDPVar) {
        this.partA_BDPVar = partA_BDPVar;
    }

    public void setPartB_BDPVar(Double partB_BDPVar) {
        this.partB_BDPVar = partB_BDPVar;
    }

    public void setPartA_BSampleVar(Double partA_BSampleVar) {
        this.partA_BSampleVar = partA_BSampleVar;
    }

    public void setPartB_BSampleVar(Double partB_BSampleVar) {
        this.partB_BSampleVar = partB_BSampleVar;
    }

    public void setPartA_BCountVar(Double partA_BCountVar) {
        this.partA_BCountVar = partA_BCountVar;
    }

    public void setPartB_BCountVar(Double partB_BCountVar) {
        this.partB_BCountVar = partB_BCountVar;
    }

    public void setPartA_BiasSquare(Double partA_BiasSquare) {
        this.partA_BiasSquare = partA_BiasSquare;
    }

    public void setPartB_BiasSquare(Double partB_BiasSquare) {
        this.partB_BiasSquare = partB_BiasSquare;
    }

    public void setNonNullCount(Integer nonNullCount) {
        this.nonNullCount = nonNullCount;
    }

    public void setPartA_MDPVar(Double partA_MDPVar) {
        this.partA_MDPVar = partA_MDPVar;
    }

    public void setPartB_MDPVar(Double partB_MDPVar) {
        this.partB_MDPVar = partB_MDPVar;
    }

    public void setPartA_MSampleVar(Double partA_MSampleVar) {
        this.partA_MSampleVar = partA_MSampleVar;
    }

    public void setPartB_MSampleVar(Double partB_MSampleVar) {
        this.partB_MSampleVar = partB_MSampleVar;
    }

    public void setPartA_MCountVar(Double partA_MCountVar) {
        this.partA_MCountVar = partA_MCountVar;
    }

    public void setPartB_MCountVar(Double partB_MCountVar) {
        this.partB_MCountVar = partB_MCountVar;
    }

    public void setPartA_MBiasSquare(Double partA_MBiasSquare) {
        this.partA_MBiasSquare = partA_MBiasSquare;
    }

    public void setPartB_MBiasSquare(Double partB_MBiasSquare) {
        this.partB_MBiasSquare = partB_MBiasSquare;
    }

    public void setNonNullMCount(Double nonNullMCount) {
        this.nonNullMCount = nonNullMCount;
    }

    public static ResultPartBeanErrorDetails toBean(String line) {
        String[] data = line.split(",");
        String name = data[0];
        Integer batchID = Integer.valueOf(data[1]);
        Integer batchSize = Integer.valueOf(data[2]);
        Long timeCost = Long.valueOf(data[3]);
        Double privacyBudget = Double.valueOf(data[4]);
        Integer windowSize = Integer.valueOf(data[5]);
        Double bRE = Double.valueOf(data[6]);
        Double bJSD = Double.valueOf(data[7]);
        Double bWD = Double.valueOf(data[8]);
        Double partA_BDPVar = Double.valueOf(data[9]);
        Double partB_BDPVar = Double.valueOf(data[10]);
        Double partA_BSampleVar = Double.valueOf(data[11]);
        Double partB_BSampleVar = Double.valueOf(data[12]);
        Double partA_BCountVar = Double.valueOf(data[13]);
        Double partB_BCountVar = Double.valueOf(data[14]);
        Double partA_BiasSquare = Double.valueOf(data[15]);
        Double partB_BiasSquare = Double.valueOf(data[16]);
        Integer nonNullCount = Integer.valueOf(data[17]);
        return new ResultPartBeanErrorDetails(name, batchID, batchSize, timeCost, privacyBudget, windowSize,
                bRE, bJSD, bWD, partA_BDPVar, partB_BDPVar,
                partA_BSampleVar, partB_BSampleVar, partA_BCountVar,
                partB_BCountVar, partA_BiasSquare, partB_BiasSquare,
                nonNullCount);
    }

    @Override
    public String toString() {
        return toCSVString();
    }
}
