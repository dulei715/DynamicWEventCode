package ecnu.dll.schemes.compared_scheme.w_event;

public abstract class AbstractScheme {
    protected Integer regionSize;
    protected Integer timeUpperBound;
    protected Double privacyBudget;

    protected Double[][] regionStatisticMatrix;
    protected Double[][] noiseStatisticMatrix;

    protected abstract Double getDissimilarity();
    protected abstract Double getError();

}
