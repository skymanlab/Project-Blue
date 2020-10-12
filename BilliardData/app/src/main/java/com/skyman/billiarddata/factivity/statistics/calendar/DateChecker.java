package com.skyman.billiarddata.factivity.statistics.calendar;

import java.util.ArrayList;

public class DateChecker {

    // instance variable
    private boolean[] checkedDate;
    private boolean[] standardDate;
    private int[] winCount;
    private WinCounter[] winCounters;
    private int[] lossCount;
    private LossCounter[] lossCounters;
    private int arraySize;

    private ArrayList<Long> billiardCount[];

    private ArrayList<BilliardCounter> winLosses[];

    // constructor
    public DateChecker(int billiardDataArrayListSize) {
        this.checkedDate = new boolean[billiardDataArrayListSize];
        this.standardDate = new boolean[billiardDataArrayListSize];
        this.winCount = new int[billiardDataArrayListSize];
        this.lossCount = new int[billiardDataArrayListSize];
        this.arraySize = billiardDataArrayListSize;
        this.winCounters = new WinCounter[billiardDataArrayListSize];
        this.lossCounters = new LossCounter[billiardDataArrayListSize];
        this.billiardCount = new ArrayList[billiardDataArrayListSize];
        this.winLosses = new ArrayList[billiardDataArrayListSize];

        for(int index=0; index <billiardDataArrayListSize; index++){
            this.winCounters[index] = new WinCounter();
            this.lossCounters[index] = new LossCounter();
            this.billiardCount[index] = new ArrayList<>();
            this.winLosses[index] = new ArrayList<>();
        }
    }

    // method : getter, setter
    public boolean[] getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(boolean[] checkedDate) {
        this.checkedDate = checkedDate;
    }

    public boolean[] getStandardDate() {
        return standardDate;
    }

    public void setStandardDate(boolean[] standardDate) {
        this.standardDate = standardDate;
    }

    public int[] getWinCount() {
        return winCount;
    }

    public void setWinCount(int[] winCount) {
        this.winCount = winCount;
    }

    public int[] getLossCount() {
        return lossCount;
    }

    public void setLossCount(int[] lossCount) {
        this.lossCount = lossCount;
    }

    /* method : array init */
    public void initArray() {
        // cycle : checkedDate, winCount, lossCount
        for (int position = 0; position < checkedDate.length; position++) {
            this.checkedDate[position] = false;
            this.standardDate[position] = false;
            this.winCount[position] = 0;
            this.lossCount[position] = 0;
        }

    } // End of method

    // ========================================== checkedDate ==========================================

    /* method : checkedDate - 해당 index 에 true 로 셋팅하기 */
    public void setTrueToCheckedDate(int index) {
        this.checkedDate[index] = true;
    }

    /* method : checkedDate - 해당 index 의 값을 가져오기 */
    public boolean getCheckedDateToIndex(int index) {
        return this.checkedDate[index];
    }

    // ========================================== standardDate ==========================================

    /* method : standardDate - 해당 index 에 true 로 셋팅하기 */
    public void setTrueToStandardDate(int index) {
        this.standardDate[index] = true;
    }

    /* method : Standard - 해당 index 의 값을 가져오기 */
    public boolean getStandardDateToIndex(int index) {
        return this.standardDate[index];
    }

    // ========================================== winCount ==========================================

    /* method : winCount - 해당 index 에 1씩 증가하기 */
    public void addOneToWinCount(int index) {
        this.winCount[index] += 1;
    }

    /* method : winCount - 해당 index 에 승리 횟 수를 가져오기 */
    public  int getWinCountToIndex(int index) {
        return winCount[index];
    }

    // ========================================== lossCount ==========================================

    /* method : lossCount - 해당 index 에 1씩 증가하기 */
    public void addOneToLossCount(int index) {
        this.lossCount[index] += 1;
    }

    /* method : lossCount - 해당 index 에 승리 횟 수를 가져오기 */
    public  int getLossCountToIndex(int index) {
        return lossCount[index];
    }

    // ========================================== WinCounter ==========================================

    public void addOneToWinCountOfWinCounter (int index) {
        this.winCounters[index].addOneToWinCount();
    }

    public void addBilliardCountOfWinCounter (int index, long count) {
        this.winCounters[index].addBilliardCount(count);
    }

    public WinCounter getWinCounterToIndex(int index) {

        return this.winCounters[index];
    }

    // ========================================== LossCounter ==========================================

    public void addOneToLossCountOfLossCounter(int index) {
        this.lossCounters[index].addOneLossCount();
    }

    public void addBilliardCountOfLossCounter(int index, long count) {
        this.lossCounters[index].addBilliardCount(count);
    }

    public LossCounter getLossCounterToIndex(int index) {
        return this.lossCounters[index];
    }

    // ========================================== billiardCount ==========================================
    public void addBilliardCountToIndex(int index, long count) {
        this.billiardCount[index].add(new Long(count));
    }

    public ArrayList<Long> getBilliardCountToIndex(int index) {
        return this.billiardCount[index];
    }


    // ========================================== WinLoss ==========================================

    public void addBilliardCountToIndex(int index, BilliardCounter winLoss){
        this.winLosses[index].add(winLoss);
    }

    public ArrayList<BilliardCounter> getWinLossToIndex(int index) {
        return this.winLosses[index];
    }


    // =================================================================================================


    /* method : 체크하기 위해 생성된 배열의 사이즈 출력 */
    public int getArraySize(){
        return arraySize;
    }


    // =================================================================================================
    // =================================================================================================


    public class WinCounter {
        // instance variable
        private int winCount;
        private ArrayList<Long> billiardCount;

        public WinCounter() {
            this.winCount = 0;
            this.billiardCount = new ArrayList<>();
        }

        // =================================================================================================

        // method : getter, setter
        public int getWinCount() {
            return winCount;
        }

        public void setWinCount(int winCount) {
            this.winCount = winCount;
        }

        public void addOneToWinCount() {
            this.winCount += 1;
        }

        // =================================================================================================

        public ArrayList<Long> getBilliardCount() {
            return billiardCount;
        }

        public void setBilliardCount(ArrayList<Long> billiardCount) {
            this.billiardCount = billiardCount;
        }

        public long getBilliardDataToIndex(int index) {
            return billiardCount.get(index);
        }

        public void addBilliardCount(long count) {
            this.billiardCount.add(new Long(count));
        }
    }

    // =================================================================================================
    // =================================================================================================

    public class LossCounter {

        // instance variable
        private int lossCount;
        private ArrayList<Long> billiardCount;

        // constructor
        public LossCounter() {
            this.lossCount = 0;
            this.billiardCount = new ArrayList<>();
        }

        // =================================================================================================

        // method :getter, setter
        public int getLossCount() {
            return lossCount;
        }

        public void setLossCount(int lossCount) {
            this.lossCount = lossCount;
        }

        public void addOneLossCount(){
            this.lossCount += 1;
        }

        // =================================================================================================

        public ArrayList<Long> getBilliardCount() {
            return billiardCount;
        }

        public void setBilliardCount(ArrayList<Long> billiardCount) {
            this.billiardCount = billiardCount;
        }

        public long getBilliardCountToIndex(int index) {
            return billiardCount.get(index);
        }

        public void addBilliardCount(long count) {
            billiardCount.add(new Long(count));
        }
    }

    public static class BilliardCounter {

        // instance variable
        private char winOrLoss;
        private long billiardCount;

        // constructor
        public BilliardCounter(char winOrLoss, long billiardCount) {
            this.winOrLoss = winOrLoss;
            this.billiardCount = billiardCount;
        }

        // method : getter, setter
        public char getWinOrLoss() {
            return winOrLoss;
        }

        public void setWinOrLoss(char winOrLoss) {
            this.winOrLoss = winOrLoss;
        }

        public long getBilliardCount() {
            return billiardCount;
        }

        public void setBilliardCount(long billiardCount) {
            this.billiardCount = billiardCount;
        }
    }
}
