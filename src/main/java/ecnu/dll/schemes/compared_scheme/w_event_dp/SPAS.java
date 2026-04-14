package ecnu.dll.schemes.compared_scheme.w_event_dp;

import ecnu.dll.schemes._basic_struct.Mechanism;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.stream_data.StreamNoiseCountData;

import java.util.List;

public class SPAS extends Mechanism {

    protected int currentTime;
    protected Double privacyBudget;
    protected StreamNoiseCountData lastReleaseNoiseCountMap;

    protected Integer sampleCount;

    public boolean updateNextPublicationResult(List<StreamDataElement<Boolean>> nextDataElementList) {
        ++this.currentTime;

    }

}
