package ecnu.dll.schemes.compared_scheme.trajectory_ldp.trajectory_ldp_impl;

import ecnu.dll.schemes.compared_scheme.trajectory_ldp.TrajectoryLDPMechanism;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.ldp_trace.LDPTrace;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;

public class LDPTraceCountStream extends TrajectoryLDPMechanism<LDPTrace> {

    protected Integer userSize = null;
    protected List<LDPTrace> userTraceList;

    @Override
    public Double getPrivacyBudget() {
        return null;
    }

    @Override
    public Integer getWindowSize() {
        return null;
    }

    @Override
    public StreamNoiseCountData getReleaseNoiseCountMap() {
        return null;
    }

    @Override
    public String getSimpleName() {
        return null;
    }

    @Override
    public void updateNextPublicationResult(List batchData) {

    }
}
