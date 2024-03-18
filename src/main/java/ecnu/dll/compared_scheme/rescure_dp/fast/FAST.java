package ecnu.dll.compared_scheme.rescure_dp.fast;

import ecnu.dll.compared_scheme.rescure_dp.fast.basic_component.Prediction;

public class FAST {

    protected int sampleNumber;
    protected void initSampleNumber() {
        this.sampleNumber = 0;
    }

    public double[] execute(double[] rowData, double epsilon, int sampleNumberSize) {
        double[] resultData = new double[rowData.length];
        double prior, posterior;
        for (int k = 0; k < rowData.length; k++) {
            prior = Prediction.getEstimation();
        }
        return null;
    }
    public static void main(String[] args) {
        System.out.println("Hello Dynamic WEvent!");
    }
}
