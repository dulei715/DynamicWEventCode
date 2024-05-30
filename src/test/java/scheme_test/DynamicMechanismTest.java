package scheme_test;

import cn.edu.dll.io.print.MyPrint;
import cn.edu.dll.struct.pair.BasicPair;
import ecnu.dll.schemes._scheme_utils.BooleanStreamDataElementUtils;
import ecnu.dll.schemes._scheme_utils.nullified.NullifiedBound;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.PersonalizedDynamicBudgetAbsorption;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.PersonalizedDynamicBudgetDistribution;
import ecnu.dll.schemes.main_scheme.b_dynamic_windown_size.special_tools.ForwardImpactStreamTools;
import ecnu.dll.struts.direct_stream.BackwardHistoricalStream;
import ecnu.dll.struts.direct_stream.ForwardImpactStream;
import ecnu.dll.struts.stream_data.StreamDataElement;
import ecnu.dll.struts.test.BackwardForwardData;
import ecnu.dll.utils.TestTools;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

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
        PersonalizedDynamicBudgetDistribution pdbd = new PersonalizedDynamicBudgetDistribution(dataElementList.get(0).getKeyList(), userSize);
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
        PersonalizedDynamicBudgetAbsorption pdba = new PersonalizedDynamicBudgetAbsorption(dataElementList.get(0).getKeyList(), userSize, nullifiedType);
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

    protected void updateBackwardHistoricalStreamList(int userSize, List<Double> calculationPrivacyBudgetList, List<Double> publicationPrivacyBudgetList, List<BackwardHistoricalStream> backwardHistoricalStreamList) {
        Double tempCalculationBudget, tempPublicationPrivacyBudget;
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


    @Test
    public void middleStep() {
        int basicUserSize = 3;
        int timeStampSize = 5;
        BackwardForwardData[][] data = new BackwardForwardData[][] {
                new BackwardForwardData[] {new BackwardForwardData(1.0, 1, 2.4, 4), new BackwardForwardData(2.4, 2, 3.2, 4), new BackwardForwardData(2.8, 2, 4.2, 3), new BackwardForwardData(1.2, 2, 2.4, 3), new BackwardForwardData(1.0, 5, 0.8, 2)},
                new BackwardForwardData[] {new BackwardForwardData(0.6, 1, 1.6, 2), new BackwardForwardData(1.6, 2, 2.4, 2), new BackwardForwardData(0.0, 0, 0.0, 0), new BackwardForwardData(0.0, 0, 0.0, 0), new BackwardForwardData(0.0, 0, 0.0, 0)},
                new BackwardForwardData[] {new BackwardForwardData(2.0, 1, 1.2, 3), new BackwardForwardData(1.2, 2, 3.0, 3), new BackwardForwardData(0.0, 0, 0.0, 0), new BackwardForwardData(0.0, 0, 0.0, 0), new BackwardForwardData(0.0, 0, 0.0, 0)},
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
        List<Double> tempCalculationPrivacyBudgetList;
        List<BasicPair<Double, Double>> tempRemainBudgetListPair;
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
            MyPrint.showSplitLine("*", 50);


            // M_2





        }
    }

    @Test
    public void testPersonalizedDynamicBudgetDistribution() {

    }
}
