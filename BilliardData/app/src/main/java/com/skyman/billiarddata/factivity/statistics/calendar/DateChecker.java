package com.skyman.billiarddata.factivity.statistics.calendar;

public class DateChecker {

    // variable
    private boolean[] checkedDate;
    private boolean[] standardDate;
    private int[] winCount;
    private int[] lossCount;
    private int arraySize;

    // constructor
    public DateChecker(int billiardDataArrayListSize) {
        this.checkedDate = new boolean[billiardDataArrayListSize];
        this.standardDate = new boolean[billiardDataArrayListSize];
        this.winCount = new int[billiardDataArrayListSize];
        this.lossCount = new int[billiardDataArrayListSize];
        this.arraySize = billiardDataArrayListSize;
    }

    /* method : getter, setter */
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

    /* method : checkedDate - 해당 index 에 true 로 셋팅하기 */
    public void setTrueToCheckedDate(int index) {
        this.checkedDate[index] = true;
    }

    /* method : checkedDate - 해당 index 의 값을 가져오기 */
    public boolean getCheckedDateToIndex(int index) {
        return this.checkedDate[index];
    }

    /* method : standardDate - 해당 index 에 true 로 셋팅하기 */
    public void setTrueToStandardDate(int index) {
        this.standardDate[index] = true;
    }

    /* method : Standard - 해당 index 의 값을 가져오기 */
    public boolean getStandardDateToIndex(int index) {
        return this.standardDate[index];
    }


    /* method : winCount - 해당 index 에 1씩 증가하기 */
    public void addOneToWinCount(int index) {
        this.winCount[index] += 1;
    }

    /* method : winCount - 해당 index 에 승리 횟 수를 가져오기 */
    public  int getWinCountToIndex(int index) {
        return winCount[index];
    }

    /* method : lossCount - 해당 index 에 1씩 증가하기 */
    public void addOneToLossCount(int index) {
        this.lossCount[index] += 1;
    }

    /* method : lossCount - 해당 index 에 승리 횟 수를 가져오기 */
    public  int getLossCountToIndex(int index) {
        return lossCount[index];
    }

    /* method : 체크하기 위해 생성된 배열의 사이즈 출력 */
    public int getArraySize(){
        return arraySize;
    }
}
