package basic_test;

import cn.edu.dll.basic.BasicArrayUtil;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.io.print.MyPrint;
import ecnu.dll.schemes._scheme_utils.MechanismErrorUtils;
import org.junit.Test;

import java.util.TreeMap;

public class MechanismErrorTest {

    @Test
    public void fun1() {
        double[] epsilonArray = BasicArrayUtil.getIncreasedoubleNumberArray(0.1, 0.01, 10, 2);
        MyPrint.showArray(epsilonArray, "; ");
        int size = epsilonArray.length;
        Double[] randomDoubleArray = RandomUtil.getRandomDoubleArray(0D, 1D, size);
//        MyPrint.showArray(randomDoubleArray, "; ");
        Double sum = BasicArrayUtil.getSum(randomDoubleArray);
//        System.out.println(sum);
        BasicArrayUtil.linearTransform(randomDoubleArray, 1.0/sum, 0D);
//        MyPrint.showArray(randomDoubleArray, "; ");

        TreeMap<Double, Double> map = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            map.put(epsilonArray[i], randomDoubleArray[i]);
        }

        Double[] errorArray = new Double[size], sampleErrorArray = new Double[size], dpErrorArray = new Double[size];
        Double[] countError = new Double[size], biasError = new Double[size];
        for (int i = 0; i < size; i++) {
            countError[i] = MechanismErrorUtils.getCountError(1000, map, epsilonArray[i]);
            biasError[i] = MechanismErrorUtils.getBiasError(1000, map, epsilonArray[i]);
            sampleErrorArray[i] = MechanismErrorUtils.getSampleError(1000, map, epsilonArray[i]);
            dpErrorArray[i] = MechanismErrorUtils.getDPError(epsilonArray[i]);
            errorArray[i] = sampleErrorArray[i] + dpErrorArray[i];
        }
        MyPrint.showArray(countError, "; ");
        MyPrint.showArray(biasError, "; ");
        MyPrint.showArray(sampleErrorArray, "; ");
        MyPrint.showArray(dpErrorArray, "; ");
        MyPrint.showArray(errorArray, "; ");

        double minError = Double.MAX_VALUE;
        double epsilon = -1, ratio = -1;
        for (int i = 0; i < errorArray.length; i++) {
//            if (errorArray[i] < minError) {
//                minError = errorArray[i];
//            if (sampleErrorArray[i] < minError) {
//                minError = sampleErrorArray[i];
            if (countError[i] < minError) {
                minError = countError[i];
                epsilon = epsilonArray[i];
                ratio = randomDoubleArray[i];
            }
        }
        System.out.printf("epsilon: %f, ration: %f, error: %f\n", epsilon, ratio, minError);
    }

    @Test
    public void fun2() {
        double[] epsilonArray = new double[]{0.1, 0.2, 0.3, 3, 3.1, 10, 10.5};
        MyPrint.showArray(epsilonArray, "; ");
        int size = epsilonArray.length;
        Double[] randomDoubleArray = RandomUtil.getRandomDoubleArray(0D, 1D, size);
//        MyPrint.showArray(randomDoubleArray, "; ");
        Double sum = BasicArrayUtil.getSum(randomDoubleArray);
//        System.out.println(sum);
        BasicArrayUtil.linearTransform(randomDoubleArray, 1.0/sum, 0D);
//        MyPrint.showArray(randomDoubleArray, "; ");

        TreeMap<Double, Double> map = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            map.put(epsilonArray[i], randomDoubleArray[i]);
        }

        Double[] errorArray = new Double[size], sampleErrorArray = new Double[size], dpErrorArray = new Double[size];
        Double[] countError = new Double[size], biasError = new Double[size];
        for (int i = 0; i < size; i++) {
            countError[i] = MechanismErrorUtils.getCountError(1000, map, epsilonArray[i]);
            biasError[i] = MechanismErrorUtils.getBiasError(1000, map, epsilonArray[i]);
            sampleErrorArray[i] = MechanismErrorUtils.getSampleError(1000, map, epsilonArray[i]);
            dpErrorArray[i] = MechanismErrorUtils.getDPError(epsilonArray[i]);
            errorArray[i] = sampleErrorArray[i] + dpErrorArray[i];
        }
        MyPrint.showArray(countError, "; ");
        MyPrint.showArray(biasError, "; ");
        MyPrint.showArray(sampleErrorArray, "; ");
        MyPrint.showArray(dpErrorArray, "; ");
        MyPrint.showArray(errorArray, "; ");

        double minError = Double.MAX_VALUE;
        double epsilon = -1, ratio = -1;
        for (int i = 0; i < errorArray.length; i++) {
//            if (errorArray[i] < minError) {
//                minError = errorArray[i];
//            if (sampleErrorArray[i] < minError) {
//                minError = sampleErrorArray[i];
            if (countError[i] < minError) {
                minError = countError[i];
                epsilon = epsilonArray[i];
                ratio = randomDoubleArray[i];
            }
        }
        System.out.printf("epsilon: %f, ration: %f, error: %f\n", epsilon, ratio, minError);
    }
    @Test
    public void fun3() {
        double[] epsilonArray = new double[]{0,0.3};
        MyPrint.showArray(epsilonArray, "; ");
        int size = epsilonArray.length;
        Double[] randomDoubleArray = RandomUtil.getRandomDoubleArray(0D, 1D, size);
//        MyPrint.showArray(randomDoubleArray, "; ");
        Double sum = BasicArrayUtil.getSum(randomDoubleArray);
//        System.out.println(sum);
        BasicArrayUtil.linearTransform(randomDoubleArray, 1.0/sum, 0D);
//        MyPrint.showArray(randomDoubleArray, "; ");

        TreeMap<Double, Double> map = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            map.put(epsilonArray[i], randomDoubleArray[i]);
        }

        Double[] errorArray = new Double[size], sampleErrorArray = new Double[size], dpErrorArray = new Double[size];
        Double[] countError = new Double[size], biasError = new Double[size];
        for (int i = 0; i < size; i++) {
            countError[i] = MechanismErrorUtils.getCountError(1000, map, epsilonArray[i]);
            biasError[i] = MechanismErrorUtils.getBiasError(1000, map, epsilonArray[i]);
            sampleErrorArray[i] = MechanismErrorUtils.getSampleError(1000, map, epsilonArray[i]);
            dpErrorArray[i] = MechanismErrorUtils.getDPError(epsilonArray[i]);
            errorArray[i] = sampleErrorArray[i] + dpErrorArray[i];
        }
        MyPrint.showArray(countError, "; ");
        MyPrint.showArray(biasError, "; ");
        MyPrint.showArray(sampleErrorArray, "; ");
        MyPrint.showArray(dpErrorArray, "; ");
        MyPrint.showArray(errorArray, "; ");

        double minError = Double.MAX_VALUE;
        double epsilon = -1, ratio = -1;
        for (int i = 0; i < errorArray.length; i++) {
            if (errorArray[i] < minError) {
                minError = errorArray[i];
//            if (sampleErrorArray[i] < minError) {
//                minError = sampleErrorArray[i];
//            if (countError[i] < minError) {
//                minError = countError[i];
                epsilon = epsilonArray[i];
                ratio = randomDoubleArray[i];
            }
        }
        System.out.printf("epsilon: %f, ration: %f, error: %f\n", epsilon, ratio, minError);
    }
}
