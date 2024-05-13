package ecnu.dll.struts;

import ecnu.dll._config.Constant;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class BackwardHistoricalStream {
    public static final Integer Max_Backward_Window_Size = Constant.MAX_BACKWARD_WINDOW_SIZE;
    private Integer currentTime;
    private Deque<Double> historicalCalculationBudgetQueue;
    private Deque<Double> historicalPublicationBudgetQueue;

    public BackwardHistoricalStream() {
        this.currentTime = -1;
        this.historicalCalculationBudgetQueue = new LinkedList<>();
        this.historicalPublicationBudgetQueue = new LinkedList<>();
    }

    public void resetStream() {
        this.currentTime = -1;
        this.historicalCalculationBudgetQueue.clear();
        this.historicalPublicationBudgetQueue.clear();
    }

    private void checkAndDrop() {
        while (this.historicalCalculationBudgetQueue.size() > Max_Backward_Window_Size) {
            this.historicalCalculationBudgetQueue.poll();
            this.historicalPublicationBudgetQueue.poll();
        }
    }

    public void addElement(Double calculationPrivacyBudget, Double predictionPrivacyBudget) {
        this.historicalCalculationBudgetQueue.addLast(calculationPrivacyBudget);
        this.historicalPublicationBudgetQueue.addLast(predictionPrivacyBudget);
        this.checkAndDrop();
    }

    public Double getHistoricalCalculationBudgetSum(int beforeLength) {
        Double result = 0D;
        Iterator<Double> iterator = this.historicalCalculationBudgetQueue.descendingIterator();
        for (int i = 0; i < beforeLength && iterator.hasNext(); i++) {
            result += iterator.next();
        }
        return result;
    }
    public Double getHistoricalPublicationBudgetSum(int beforeLength) {
        Double result = 0D;
        Iterator<Double> iterator = this.historicalPublicationBudgetQueue.descendingIterator();
        for (int i = 0; i < beforeLength && iterator.hasNext(); i++) {
            result += iterator.next();
        }
        return result;
    }

    public Double getHistoricalBudgetSum(int beforeLength) {
        return this.getHistoricalCalculationBudgetSum(beforeLength) + this.getHistoricalPublicationBudgetSum(beforeLength);
    }

}
