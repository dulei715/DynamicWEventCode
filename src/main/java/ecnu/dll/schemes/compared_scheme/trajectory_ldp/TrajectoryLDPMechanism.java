package ecnu.dll.schemes.compared_scheme.trajectory_ldp;

import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.schemes.compared_scheme.trajectory_ldp.basic_schemes.PrivateTrace;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;

public abstract class TrajectoryLDPMechanism<T extends PrivateTrace> extends Mechanism {



    public abstract Double getPrivacyBudget();
    public abstract Integer getWindowSize();
    public abstract StreamNoiseCountData getReleaseNoiseCountMap();
    public abstract void updateNextPublicationResult(List<StreamDataElement<Boolean>> batchData);
    public abstract String getSimpleName();
}
