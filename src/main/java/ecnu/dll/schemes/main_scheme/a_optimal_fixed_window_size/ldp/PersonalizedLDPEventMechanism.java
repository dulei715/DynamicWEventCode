package ecnu.dll.schemes.main_scheme.a_optimal_fixed_window_size.ldp;

import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;

public abstract class PersonalizedLDPEventMechanism extends Mechanism {
    public abstract List<Double> getPrivacyBudgetList();
    public abstract List<Integer> getWindowSizeList();
    public abstract StreamNoiseCountData getReleaseNoiseCountMap();
    public abstract boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> batchData);
    public abstract String getSimpleName();
}
