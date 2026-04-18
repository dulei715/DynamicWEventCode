package ecnu.dll.schemes._basic_struct;

import ecnu.dll.struts.stream_data.StreamNoiseCountData;

public abstract class Mechanism {
    public abstract String getSimpleName();
    public abstract StreamNoiseCountData getReleaseNoiseCountData();
}
