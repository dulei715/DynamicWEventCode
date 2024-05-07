package ecnu.dll.struts;

import ecnu.dll._config.Constant;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class BackwardHistoricalStream {
    public static final Integer Max_Backward_Window_Size = Constant.MAX_BACKWARD_WINDOW_SIZE;
    private Integer currentTime;
    private Deque<Double> historicalCalculationBudgetQueue;
    private Deque<Double> historicalPredictionBudgetQueue;

    public BackwardHistoricalStream() {
        this.currentTime = -1;
        this.historicalCalculationBudgetQueue = new LinkedList<>();
        this.historicalPredictionBudgetQueue = new LinkedList<>();
    }

    public void resetStream() {
        this.currentTime = -1;
        this.historicalCalculationBudgetQueue.clear();
        this.historicalPredictionBudgetQueue.clear();
    }

    private void checkAndDrop() {
        while (this.historicalCalculationBudgetQueue.size() > Max_Backward_Window_Size) {
            this.historicalCalculationBudgetQueue.poll();
            this.historicalPredictionBudgetQueue.poll();
        }
    }

    public void addElement(Double calculationPrivacyBudget, Double predictionPrivacyBudget) {
        this.historicalCalculationBudgetQueue.addLast(calculationPrivacyBudget);
        this.historicalPredictionBudgetQueue.addLast(predictionPrivacyBudget);
        this.checkAndDrop();
    }

    public Double getHistoricalCalculationBudgetSum(int beforeLength) {
        Double result = 0D;
        Iterator<Double> iterator = this.historicalCalculationBudgetQueue.descendingIterator();
        for (int i = 0; i < beforeLength; i++) {
            result += iterator.next();
        }
        return result;
    }
    public Double getHistoricalPredictionBudgetSum(int beforeLength) {
        Double result = 0D;
        Iterator<Double> iterator = this.historicalPredictionBudgetQueue.descendingIterator();
        for (int i = 0; i < beforeLength; i++) {
            result += iterator.next();
        }
        return result;
    }

}
