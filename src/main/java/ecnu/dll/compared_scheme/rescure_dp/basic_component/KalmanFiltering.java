package ecnu.dll.compared_scheme.rescure_dp.basic_component;

public class KalmanFiltering {
    /**
     * 根据前一时刻的发布以及当前的噪声，估计出当前的数据
     * @param rPri      前一时刻发布的数据
     * @param zNow      当前的噪声
     * @param pBefore   前一时刻的方差
     * @param noiseQ    过程噪声方差
     * @param noiseR    隐私噪声方差（用Gauss噪声近似Laplace噪声）
     * @return  [当前数据估计值，当前方差]
     */
    public static double[] estimate(double rPri, double zNow, double pBefore, double noiseQ, double noiseR) {
        double xPri, pPri, kalmanValue, xNow, pNow;
        double[] result = new double[2];
        // Prediction
        xPri = rPri;
        pPri = pBefore + noiseQ;

        // Correction
        kalmanValue = pPri / (pPri + noiseR);
        xNow = xPri + kalmanValue * (zNow - xPri);
        pNow = pPri * (1 - kalmanValue);
        result[0] = xNow;
        result[1] = pNow;
        return result;
    }





}
