package ecnu.dll.schemes.compared_scheme.trajectory_ldp.trajectory_ldp_impl;

import ecnu.dll.schemes.compared_scheme.trajectory_ldp.TraceLDPMechanism;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.LDPTrace;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.basic_struct.SampleLDPTraceStatus;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;

public class LDPTraceCountStream extends TraceLDPMechanism {

    protected Integer userSize = null;
    protected LDPTrace ldpTrace = null;
    protected Double privacyBudget = null;
    protected Integer windowSize = null;
    // 每个用户持有一个sampleLDPTraceStatus
    protected List<SampleLDPTraceStatus> ldpTraceStatusList;

    /*

        1. 构建原始轨迹 n 条 (n=10000)，每个轨迹最多10000个点(时间点)
        2. 利用生成的原始轨迹构建LDPTrace
        3. 随机抽取 n 条
        4. 计算对应轨迹之间的误差指标
     */

    @Override
    public Double getPrivacyBudget() {
        return this.privacyBudget;
    }

    @Override
    public Integer getWindowSize() {
        return this.windowSize;
    }

    @Override
    public StreamNoiseCountData getReleaseNoiseCountData() {
        return null;
    }

    @Override
    public String getSimpleName() {
        return "LDPTrace";
    }

    @Override
    public void updateNextPublicationResult(List<StreamDataElement<Boolean>> batchData) {
        for (int i = 0; i < this.ldpTraceStatusList.size(); ++i) {
            this.ldpTraceStatusList.set(i, LDPTrace.trajectorySynthesisSampleNextPoint(this.ldpTraceStatusList.get(i)));
        }
    }
}
