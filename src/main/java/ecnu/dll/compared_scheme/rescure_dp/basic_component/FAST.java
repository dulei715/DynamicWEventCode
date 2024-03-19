package ecnu.dll.compared_scheme.rescure_dp.basic_component;

import cn.edu.dll.differential_privacy.noise.LaplaceUtils;
@Deprecated
public class FAST {

    protected int sampleNumber;

    protected void initSampleNumber() {
        this.sampleNumber = 0;
    }

    protected boolean isSamplingPoint(int k) {
        // todo: realize
        return true;
    }

    protected void adaptiveSample() {

    }

//    public double[] execute(double[] rowData, double epsilon, int sampleNumberSize) {
//        double[] resultData = new double[rowData.length];
//        double prior, posterior;
//        double noiseData;
//        for (int k = 0; k < rowData.length; k++) {
//            prior = this.prediction.getEstimation();
//            if (isSamplingPoint(k) && this.sampleNumber < sampleNumberSize) {
//                noiseData = rowData[k] + LaplaceUtils.getLaplaceNoise(sampleNumberSize, epsilon, 1)[0];
//                ++this.sampleNumber;
//                posterior = this.correction.getEstimation();
//                resultData[k] = posterior;
//                adaptiveSample();
//            } else {
//                resultData[k] = prior;
//            }
//        }
//        return resultData;
//    }
    public static void main(String[] args) {
        System.out.println("Hello Dynamic WEvent!");
    }
}
