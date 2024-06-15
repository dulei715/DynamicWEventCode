package scheme_test;

import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.SchemeUtils;
import ecnu.dll.schemes._scheme_utils.nullified.NullifiedBound;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.DynamicPersonalizedBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.DynamicPersonalizedBudgetDistribution;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.special_tools.ForwardImpactStreamTools;
import ecnu.dll.struts.direct_stream.*;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.test.BackwardForwardData;
import ecnu.dll.utils.TestTools;
import org.junit.Test;

import java.util.*;

public class DynamicMechanismTest {
    private List<StreamDataElement<Boolean>> dataList;
    private Random random = new Random(1);

    private void initializeHistoricalBudgetListList(ArrayList<ArrayList<Double>> data, int size) {
        for (int i = 0; i < size; i++) {
            data.add(new ArrayList<Double>());
        }
    }
    private void addCurrentElementListToHistoricalBudgetListList(ArrayList<ArrayList<Double>> data, List<Double> colDataList) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).add(colDataList.get(i));
        }
    }

    @Test
    public void personalizeDynamicPrivacyBudgetDistributionTest() {
        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        int timeUpperBound = 100;

        List<StreamDataElement<Boolean>> dataElementList;
        TreeMap<String, Integer> tempRealMapResult;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
        DynamicPersonalizedBudgetDistribution pdbd = new DynamicPersonalizedBudgetDistribution(dataElementList.get(0).getKeyList(), userSize);
        List<Double> tempBackwardBudgetList, tempForwardBudgetList;
        List<Integer> tempBackwardWindowSizeList, tempForwardWindowSizeList;
        List<Double> historicalBudgetSumList = new ArrayList<>();
        for (int i = 0; i < timeUpperBound; i++) {
            System.out.printf("Time slot %d\n", i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
            tempRealMapResult = BooleanStreamDataElementUtils.getCountByGivenElementType(true, dataElementList);
            tempBackwardWindowSizeList = TestTools.generateWindowSizeList(this.random, userSize, windowSizeUpperBound);
            tempBackwardBudgetList = TestTools.generateLegalBackwardPrivacyBudgetByWindowSize(this.random, pdbd.getBackwardHistoricalStreamList(), tempBackwardWindowSizeList);
            tempForwardBudgetList = TestTools.generateEpsilonList(this.random, userSize);
            tempForwardWindowSizeList = TestTools.generateWindowSizeList(this.random, userSize, windowSizeUpperBound);
            boolean isPublication = pdbd.updateNextPublicationResult(dataElementList, tempBackwardBudgetList, tempBackwardWindowSizeList, tempForwardBudgetList, tempForwardWindowSizeList);
            System.out.println(isPublication);
            MyPrint.showMap(pdbd.getReleaseNoiseCountMap().getDataMap());
            MyPrint.showSplitLine("*", 50);
            MyPrint.showMap(tempRealMapResult);
            MyPrint.showSplitLine("*", 150);
        }
    }

    @Test
    public void personalizeDynamicPrivacyBudgetAbsorptionTest() {
        int userSize = 100;
        int typeSize = 5;
        int windowSizeUpperBound = 6;

        int timeUpperBound = 100;

        int nullifiedType = NullifiedBound.AverageType;

        List<StreamDataElement<Boolean>> dataElementList;
        TreeMap<String, Integer> tempRealMapResult;

        dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
        DynamicPersonalizedBudgetAbsorption pdba = new DynamicPersonalizedBudgetAbsorption(dataElementList.get(0).getKeyList(), userSize, nullifiedType);
        List<Double> tempBackwardBudgetList, tempForwardBudgetList;
        List<Integer> tempBackwardWindowSizeList, tempForwardWindowSizeList;
        List<Double> historicalBudgetSumList = new ArrayList<>();
        for (int i = 0; i < timeUpperBound; i++) {
            System.out.printf("Time slot %d\n", i);
            dataElementList = TestTools.generateStreamDataElementList(this.random, userSize, typeSize);
            tempRealMapResult = BooleanStreamDataElementUtils.getCountByGivenElementType(true, dataElementList);
            tempBackwardWindowSizeList = TestTools.generateWindowSizeList(this.random, userSize, windowSizeUpperBound);
            tempBackwardBudgetList = TestTools.generateLegalBackwardPrivacyBudgetByWindowSize(this.random, pdba.getBackwardHistoricalStreamList(), tempBackwardWindowSizeList);
            tempForwardBudgetList = TestTools.generateEpsilonList(this.random, userSize);
            tempForwardWindowSizeList = TestTools.generateWindowSizeList(this.random, userSize, windowSizeUpperBound);
            boolean isPublication = pdba.updateNextPublicationResult(dataElementList, tempBackwardBudgetList, tempBackwardWindowSizeList, tempForwardBudgetList, tempForwardWindowSizeList);
            System.out.println(isPublication);
            MyPrint.showMap(pdba.getReleaseNoiseCountMap().getDataMap());
            MyPrint.showSplitLine("*", 50);
            MyPrint.showMap(tempRealMapResult);
            MyPrint.showSplitLine("*", 150);
        }
    }

    public static BasicPair<Double, Double>[][] getAverageBudgets(BackwardForwardData[][] data) {
        int rowSize = data.length;
        int colSize = data[0].length;
        BasicPair<Double, Double>[][] result = new BasicPair[rowSize][colSize];
        BackwardForwardData tempData;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                tempData = data[i][j];
                result[i][j] = new BasicPair<>(tempData.getBackwardBudget()/(2*tempData.getBackwardWindowSize()), tempData.getForwardBudget()/(2*tempData.getForwardWindowSize()));
            }
        }
        return result;
    }

    public void updateForwardImpactStreamList(List<ForwardImpactStream> forwardImpactStreamList, List<Double> forwardBudgetList,
                                                 List<Integer> forwardWindowSizeList) {
        ForwardImpactStream tempForwardStream;
        Double tempForwardBudget;
        Integer tempForwardWindowSize;
        int userSize = forwardWindowSizeList.size();
        for (int userID = 0; userID < userSize; ++userID) {
            tempForwardBudget = forwardBudgetList.get(userID);
            tempForwardWindowSize = forwardWindowSizeList.get(userID);
            tempForwardStream = forwardImpactStreamList.get(userID);
            tempForwardStream.addElement(tempForwardBudget, tempForwardWindowSize);
        }
    }

    protected void updateBackwardHistoricalStreamList(List<Double> calculationPrivacyBudgetList, List<Double> publicationPrivacyBudgetList, List<BackwardHistoricalStream> backwardHistoricalStreamList) {
        Double tempCalculationBudget, tempPublicationPrivacyBudget;
        int userSize = calculationPrivacyBudgetList.size();
        for (int userID = 0; userID < userSize; ++userID) {
            tempCalculationBudget = calculationPrivacyBudgetList.get(userID);
            tempPublicationPrivacyBudget = publicationPrivacyBudgetList.get(userID);
            backwardHistoricalStreamList.get(userID).addElement(tempCalculationBudget, tempPublicationPrivacyBudget);
        }
    }

    protected List<BasicPair<Double, Double>> setCalculationPrivacyBudgetList(List<Double> calculationPrivacyBudgetList, List<Double> backwardBudgetList,
                                                   List<Integer> backwardWindowSizeList, List<ForwardImpactStream> forwardImpactStreamList, List<BackwardHistoricalStream> backwardHistoricalStreamList){
        Double tempBackwardBudget, tempForwardAverageBudget, tempBackwardBudgetRemain, tempCalculationBudget;
        Integer tempBackwardWindowSize;
        ForwardImpactStream tempForwardStream;
        BackwardHistoricalStream tempBackwardStream;
        List<BasicPair<Double, Double>> result = new ArrayList<>();
        BasicPair<Double, Double> tempPair;

        // todo: alter it to subclass
        int userSize = backwardBudgetList.size();
        for (int userID = 0; userID < userSize; ++userID) {
            tempForwardStream = forwardImpactStreamList.get(userID);
            tempBackwardStream = backwardHistoricalStreamList.get(userID);
            tempBackwardWindowSize = backwardWindowSizeList.get(userID);
            tempBackwardBudget = backwardBudgetList.get(userID);

            tempForwardAverageBudget = ForwardImpactStreamTools.getMinimalHalfAverageBudgetInWindow(tempForwardStream);
            tempBackwardBudgetRemain = tempBackwardBudget / 2 - tempBackwardStream.getHistoricalCalculationBudgetSum(tempBackwardWindowSize-1);
            tempCalculationBudget = Math.min(tempForwardAverageBudget, Math.max(tempBackwardBudgetRemain, 0));
            result.add(new BasicPair<>(tempBackwardBudgetRemain, tempForwardAverageBudget));
            calculationPrivacyBudgetList.add(tempCalculationBudget);
        }
        return result;
    }

    protected List<BasicPair<Double, Double>> setPublicationPrivacyBudgetListForPDBD(int size, List<Double> publicationPrivacyBudgetList) {
        List<BasicPair<Double,Double>> result = new ArrayList<>();
        publicationPrivacyBudgetList.clear();
        for (int i = 0; i < size; i++) {
            result.add(new BasicPair<>(0D, 0D));
            publicationPrivacyBudgetList.add(0D);
        }
        return result;
    }
    protected List<BasicPair<Double, Double>> setPublicationPrivacyBudgetListForPDBD(List<Double> backwardBudgetList,
                                                                                     List<Integer> backwardWindowSizeList, List<Double> publicationPrivacyBudgetList, List<ForwardImpactStream> forwardImpactStreamList, List<BackwardHistoricalStream> backwardHistoricalStreamList) {
        Double tempBackwardBudget, tempMinimalForwardBudgetRemain, tempMinimalBackwardBudgetRemain;
        Integer tempBackwardWindowSize;
        ForwardImpactStream tempForwardStream;
        BackwardHistoricalStream tempBackwardStream;
        List<BasicPair<Double, Double>> result = new ArrayList<>();

//        publicationPrivacyBudgetList = new ArrayList<>();
        int userSize = backwardBudgetList.size();
        for (int userID = 0; userID < userSize; ++userID) {
            tempForwardStream = forwardImpactStreamList.get(userID);
            tempBackwardStream = backwardHistoricalStreamList.get(userID);
            tempBackwardBudget = backwardBudgetList.get(userID);
            tempBackwardWindowSize = backwardWindowSizeList.get(userID);

            tempMinimalForwardBudgetRemain = 0.5*ForwardImpactStreamTools.getMinimalForwardRemainPublicationBudget(tempForwardStream, tempBackwardStream);
            tempMinimalBackwardBudgetRemain = (tempBackwardBudget / 2 - tempBackwardStream.getHistoricalPublicationBudgetSum(tempBackwardWindowSize-1));

            result.add(new BasicPair(tempMinimalBackwardBudgetRemain, tempMinimalForwardBudgetRemain));

            publicationPrivacyBudgetList.add(Math.min(tempMinimalForwardBudgetRemain, tempMinimalBackwardBudgetRemain));
        }
        return result;
    }


    @Test
    public void middleStepForPDBD() {
        int basicUserSize = 3;
        int timeStampSize = 5;
        BackwardForwardData[][] data = new BackwardForwardData[][] {
                new BackwardForwardData[] {new BackwardForwardData(1.0, 1, 2.4, 4), new BackwardForwardData(2.4, 2, 3.2, 4), new BackwardForwardData(2.8, 2, 4.2, 3), new BackwardForwardData(2.4, 2, 2.4, 3), new BackwardForwardData(3.0, 5, 0.8, 2)},
                new BackwardForwardData[] {new BackwardForwardData(0.6, 1, 1.6, 2), new BackwardForwardData(1.6, 2, 2.4, 2), new BackwardForwardData(3.2, 2, 2.8, 2), new BackwardForwardData(4.2, 3, 2.8, 2), new BackwardForwardData(3.6, 3, 2.0, 2)},
                new BackwardForwardData[] {new BackwardForwardData(2.0, 1, 1.2, 3), new BackwardForwardData(1.2, 2, 3.0, 3), new BackwardForwardData(1.8, 3, 1.2, 2), new BackwardForwardData(3.2, 2, 0.6, 3), new BackwardForwardData(2.4, 4, 1.8, 3)},
        };
        int[] groupSizeArray = new int[]{33, 33, 33};
        BasicPair<Double, Double>[][] averageBudgetPairMatrix = getAverageBudgets(data);
//        MyPrint.show2DimensionArray(averageBudgetPairMatrix, ", ");
        List<ForwardImpactStream> forwardImpactStreamList = new ArrayList<>();
        List<BackwardHistoricalStream> backwardHistoricalStreamList = new ArrayList<>();
        for (int i = 0; i < basicUserSize; i++) {
            forwardImpactStreamList.add(new ForwardImpactStream());
            backwardHistoricalStreamList.add(new BackwardHistoricalStream());
        }
        List<Double> tempForwardBudgetList;
        List<Integer> tempForwardWindowSizeList;
        List<Double> tempBackwardBudgetList;
        List<Integer> tempBackwardWindowSizeList;
        List<Double> tempCalculationPrivacyBudgetList, tempPublicationPrivacyBudgetList;
        List<BasicPair<Double, Double>> tempRemainBudgetListPair, publicationListPair;
        boolean[] isPublic = new boolean[]{
                true, false, true, false, true
        };

        for (int t = 0; t < timeStampSize; t++) {
            tempBackwardBudgetList = new ArrayList<>();
            tempBackwardWindowSizeList = new ArrayList<>();
            tempForwardBudgetList = new ArrayList<>();
            tempForwardWindowSizeList = new ArrayList<>();
            for (int i = 0; i < basicUserSize; i++) {
                tempBackwardBudgetList.add(data[i][t].getBackwardBudget());
                tempBackwardWindowSizeList.add(data[i][t].getBackwardWindowSize());
                tempForwardBudgetList.add(data[i][t].getForwardBudget());
                tempForwardWindowSizeList.add(data[i][t].getForwardWindowSize());
            }
            updateForwardImpactStreamList(forwardImpactStreamList, tempForwardBudgetList, tempForwardWindowSizeList);

            tempCalculationPrivacyBudgetList = new ArrayList<>();

            // M_1
            tempRemainBudgetListPair = setCalculationPrivacyBudgetList(tempCalculationPrivacyBudgetList, tempBackwardBudgetList, tempBackwardWindowSizeList, forwardImpactStreamList, backwardHistoricalStreamList);
//            MyPrint.showList(tempCalculationPrivacyBudgetList, "; ");
//            MyPrint.showSplitLine("*", 50);
            MyPrint.showList(tempRemainBudgetListPair, "; ");

            Double[] minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(tempCalculationPrivacyBudgetList);
//            System.out.println(minimalEpsilonAndError[0]);
//            MyPrint.showList(tempCalculationPrivacyBudgetList, "! ");




            // M_2
            tempPublicationPrivacyBudgetList = new ArrayList<>();
            publicationListPair = setPublicationPrivacyBudgetListForPDBD(tempBackwardBudgetList, tempBackwardWindowSizeList, tempPublicationPrivacyBudgetList, forwardImpactStreamList, backwardHistoricalStreamList);
            minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(tempPublicationPrivacyBudgetList);
            MyPrint.showList(publicationListPair, "; ");
            if (!isPublic[t]) {
                publicationListPair = setPublicationPrivacyBudgetListForPDBD(tempBackwardBudgetList.size(), tempPublicationPrivacyBudgetList);
            }
//            System.out.println(minimalEpsilonAndError[0]);
//            MyPrint.showList(tempPublicationPrivacyBudgetList, "! ");


            updateBackwardHistoricalStreamList(tempCalculationPrivacyBudgetList, tempPublicationPrivacyBudgetList, backwardHistoricalStreamList);

            MyPrint.showSplitLine("*", 50);

        }
    }

    protected List<BasicPair<Double, Double>> setPublicationPrivacyBudgetListForPDBA(int size, List<Double> publicationPrivacyBudgetList) {
        List<BasicPair<Double,Double>> result = new ArrayList<>();
        publicationPrivacyBudgetList.clear();
        for (int i = 0; i < size; i++) {
            result.add(new BasicPair<>(0D, 0D));
            publicationPrivacyBudgetList.add(0D);
        }
        return result;
    }

    protected List<BasicPair<Double, Double>> setPublicationPrivacyBudgetListForPDBA(int currentTime, List<Double> backwardBudgetList, List<Integer> backwardWindowSizeList, List<Double> publicationPrivacyBudgetList, List<ForwardImpactStream> forwardImpactStreamList, List<BackwardHistoricalStream> backwardHistoricalStreamList) {
        Double tempMaximalForwardBudget, tempValue, tempMinimalBorder, tempHalfTotalBudget, tempForwardBudgetRemain, tempBackwardBudgetRemain, tempBackwardBudget;
        Integer tempBackwardWindowSize;
        ForwardImpactStream tempImpactStream;
        BackwardHistoricalStream tempBackwardStream;
        ImpactElementAbsorption tempImpactElement;
        Iterator<ImpactElement> tempIterator;
        List<BasicPair<Double, Double>> result = new ArrayList<>();
        Integer userSize = backwardBudgetList.size();
        for (int i = 0; i < userSize; i++) {
            tempImpactStream = forwardImpactStreamList.get(i);
            tempIterator = tempImpactStream.forwardImpactElementIterator();
            tempMaximalForwardBudget = 0D;
            tempMinimalBorder = Double.MAX_VALUE;
            while (tempIterator.hasNext()) {
                tempImpactElement = (ImpactElementAbsorption) tempIterator.next();
                tempHalfTotalBudget = tempImpactElement.getTotalPrivacyBudget() / 2;
                tempMinimalBorder = Math.min(tempMinimalBorder, tempHalfTotalBudget);
                tempValue = (currentTime - tempImpactElement.getPublicationRightBorder()) * tempHalfTotalBudget / tempImpactElement.getWindowSize();
                tempMaximalForwardBudget = Math.max(tempMaximalForwardBudget, tempValue);
            }
            tempForwardBudgetRemain = Math.min(tempMaximalForwardBudget, tempMinimalBorder);
            tempBackwardBudget = backwardBudgetList.get(i);
            tempBackwardStream = backwardHistoricalStreamList.get(i);
            tempBackwardWindowSize = backwardWindowSizeList.get(i);
            tempBackwardBudgetRemain = tempBackwardBudget / 2 - tempBackwardStream.getHistoricalPublicationBudgetSum(tempBackwardWindowSize-1);
            result.add(new BasicPair<>(tempBackwardBudgetRemain, tempForwardBudgetRemain));
            publicationPrivacyBudgetList.add(Math.min(tempForwardBudgetRemain, tempBackwardBudgetRemain));
        }
        return result;
    }

    protected void setMaxPublicationBudgetUsageSumToNullifiedList(List<Double> nullifiedTimeStampList, List<ForwardImpactStream> forwardImpactStreamList) {

        Double tempNullified, tempMaxNullified;
        ImpactElementAbsorption element;
        ForwardImpactStream forwardImpactStream;
        Iterator<ImpactElement> impactElementIterator;
        int userSize = forwardImpactStreamList.size();
        for (int i = 0; i < userSize; i++) {
            forwardImpactStream = forwardImpactStreamList.get(i);
            impactElementIterator = forwardImpactStream.forwardImpactElementIterator();
            tempMaxNullified = -1D;
            while (impactElementIterator.hasNext()) {
                element = (ImpactElementAbsorption) impactElementIterator.next();
                tempNullified = element.getPublicationBudgetUsage() / (element.getTotalPrivacyBudget() / (2 * element.getWindowSize())) - 1 + element.getTimeSlot();
                tempMaxNullified = Math.max(tempMaxNullified, tempNullified);
            }
            nullifiedTimeStampList.add(tempMaxNullified);
        }
    }

    protected void updateImpactStreamUsageList(List<ForwardImpactStream> forwardImpactStreamList, List<Double> publicationPrivacyBudgetList) {
        int userSize = forwardImpactStreamList.size();
        for (int i = 0; i < userSize; i++) {
            ((ForwardImpactStreamAbsorption)forwardImpactStreamList.get(i)).updateStreamUsageAndRightBorder(publicationPrivacyBudgetList.get(i));
        }
    }

    @Test
    public void middleStepForPDBA() {
        int basicUserSize = 3;
        int timeStampSize = 5;
        BackwardForwardData[][] data = new BackwardForwardData[][] {
                new BackwardForwardData[] {new BackwardForwardData(1.0, 1, 2.4, 4), new BackwardForwardData(2.4, 2, 3.2, 4), new BackwardForwardData(2.8, 2, 4.2, 3), new BackwardForwardData(2.4, 2, 2.4, 3), new BackwardForwardData(3.0, 5, 0.8, 2)},
                new BackwardForwardData[] {new BackwardForwardData(0.6, 1, 1.6, 2), new BackwardForwardData(1.6, 2, 2.4, 2), new BackwardForwardData(3.2, 2, 2.8, 2), new BackwardForwardData(4.2, 3, 2.8, 2), new BackwardForwardData(3.6, 3, 2.0, 2)},
                new BackwardForwardData[] {new BackwardForwardData(2.0, 1, 1.2, 3), new BackwardForwardData(1.2, 2, 3.0, 3), new BackwardForwardData(1.8, 3, 1.2, 2), new BackwardForwardData(3.2, 2, 0.6, 3), new BackwardForwardData(2.4, 4, 1.8, 3)},
        };
        int[] groupSizeArray = new int[]{33, 33, 33};
        BasicPair<Double, Double>[][] averageBudgetPairMatrix = getAverageBudgets(data);
        List<ForwardImpactStream> forwardImpactStreamList = new ArrayList<>();
        List<BackwardHistoricalStream> backwardHistoricalStreamList = new ArrayList<>();
        for (int i = 0; i < basicUserSize; i++) {
            forwardImpactStreamList.add(new ForwardImpactStreamAbsorption());
            backwardHistoricalStreamList.add(new BackwardHistoricalStream());
        }
        List<Double> tempForwardBudgetList;
        List<Integer> tempForwardWindowSizeList;
        List<Double> tempBackwardBudgetList;
        List<Integer> tempBackwardWindowSizeList;
        List<Double> tempCalculationPrivacyBudgetList, tempPublicationPrivacyBudgetList;
        List<BasicPair<Double, Double>> tempRemainBudgetListPair, publicationListPair;
        List<Double> nullifiedTimeStampList;
        boolean[] isPublic = new boolean[]{
                true, false, true, false, true
        };

        for (int t = 0; t < timeStampSize; t++) {
            tempBackwardBudgetList = new ArrayList<>();
            tempBackwardWindowSizeList = new ArrayList<>();
            tempForwardBudgetList = new ArrayList<>();
            tempForwardWindowSizeList = new ArrayList<>();
            for (int i = 0; i < basicUserSize; i++) {
                tempBackwardBudgetList.add(data[i][t].getBackwardBudget());
                tempBackwardWindowSizeList.add(data[i][t].getBackwardWindowSize());
                tempForwardBudgetList.add(data[i][t].getForwardBudget());
                tempForwardWindowSizeList.add(data[i][t].getForwardWindowSize());
            }
            updateForwardImpactStreamList(forwardImpactStreamList, tempForwardBudgetList, tempForwardWindowSizeList);

            tempCalculationPrivacyBudgetList = new ArrayList<>();

            // M_1
            tempRemainBudgetListPair = setCalculationPrivacyBudgetList(tempCalculationPrivacyBudgetList, tempBackwardBudgetList, tempBackwardWindowSizeList, forwardImpactStreamList, backwardHistoricalStreamList);
            MyPrint.showList(tempRemainBudgetListPair, "; ");

            Double[] minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(tempCalculationPrivacyBudgetList);




            // M_2
            nullifiedTimeStampList = new ArrayList<>();
            setMaxPublicationBudgetUsageSumToNullifiedList(nullifiedTimeStampList, forwardImpactStreamList);

            tempPublicationPrivacyBudgetList = new ArrayList<>();
            publicationListPair = setPublicationPrivacyBudgetListForPDBA(t, tempBackwardBudgetList, tempBackwardWindowSizeList, tempPublicationPrivacyBudgetList, forwardImpactStreamList, backwardHistoricalStreamList);
            minimalEpsilonAndError = SchemeUtils.selectOptimalBudget(tempPublicationPrivacyBudgetList);
            MyPrint.showList(publicationListPair, "; ");
            // 此处timestamp从0开始，而论文中例子从1开始，因此这个现实结果对应论文中要+1
            MyPrint.showList(nullifiedTimeStampList, " # ");
            if (!isPublic[t]) {
                publicationListPair = setPublicationPrivacyBudgetListForPDBA(tempBackwardBudgetList.size(), tempPublicationPrivacyBudgetList);
            }
//            System.out.println(minimalEpsilonAndError[0]);
//            MyPrint.showList(tempPublicationPrivacyBudgetList, "! ");


            updateBackwardHistoricalStreamList(tempCalculationPrivacyBudgetList, tempPublicationPrivacyBudgetList, backwardHistoricalStreamList);
            updateImpactStreamUsageList(forwardImpactStreamList, tempPublicationPrivacyBudgetList);

            MyPrint.showSplitLine("*", 50);

        }
    }
}
