package com.skyman.billiarddata.management.calendar;

import java.util.ArrayList;

public class SameDateChecker {

    // instance variable
    private boolean[] isCheckedDate;
    private boolean[] isStandardDate;
    private int[] winCount;
    private int[] lossCount;
    private int[] month;
    private ArrayList<SameDateItem> sameDateItemArrayList[];
    private int arraySize;

    // constructor
    public SameDateChecker(int arraySize) {

        // 모든 필드를 arraySize 만큼 배열을 생성
        this.isCheckedDate = new boolean[arraySize];
        this.isStandardDate = new boolean[arraySize];
        this.winCount = new int[arraySize];
        this.lossCount = new int[arraySize];
        this.month = new int[arraySize];
        this.sameDateItemArrayList = new ArrayList[arraySize];

        // [iv/i]arraySize : billiardData 가 담긴 ArrayList 의 크기
        this.arraySize = arraySize;

    }



    /**
     * [method] 배열 필드를 초기값을 설정한다.
     *
     */
    public void initArray() {

        // [cycle 1] : size 크기 만큼
        for (int index = 0; index < arraySize ; index++) {

            // [iv/b]isCheckedDate, isStandardDate : 초기값을 false 로 설정하기
            this.isCheckedDate[index] = false;
            this.isStandardDate[index] = false;

            // [iv/i]winCount, lossCount : 초기값을 0 으로 설정한다. /
            this.winCount[index] = 0;
            this.lossCount[index] = 0;

            // [iv/i]month : 초기값을 0 으로 설정한다.
            this.month[index] = 0;

            // [iv/C]ArrayList<SameDateItem> : 위에서 생성된 sameDateItemArrayList 배열의 각각을 ArrayList<SameDateItem> 을 생성하기
            this.sameDateItemArrayList[index] = new ArrayList<>();

        } // [cycle 1]

    } // End of method[initArray]


    // ========================================== isCheckedDate ==========================================
    /**
     * [method] isCheckedDate 의 index 번째의 값을 true 로 설정
     *
     * @param index 배열의 index
     */
    public void setCheckedDateToTrue(int index) {

        // [iv/b]isCheckedDate : billiardDataArrayList 에서 index 번째 billiardData 의 date 값을 가져오고, 이 date 를 가지고 같은 날짜가 있는지 검사를 했을 때 true 로 설정한다.
        this.isCheckedDate[index] = true;

    } // End of method [setCheckedDateToTrue]

    /**
     * [method] isCheckedDate 의 index 번째의 값을 가져오기
     *
     * @param index 배열의 index
     * @return isCheckedDate[index] 의 값
     */
    public boolean getCheckedDateToIndex(int index) {
        return this.isCheckedDate[index];
    } // End of method [getCheckedDateToIndex]


    // ========================================== isStandardDate ==========================================
    /**
     * [method] isStandardDate 의 index 번째의 값을 true 로 설정
     *
     * @param index 배열의 index
     */
    public void setStandardDateToTrue(int index) {

        // [iv/b]isStandardDate : billiardDataArrayList 에서 index 번째 billiardData 의 date 값을 가져오고, 이 date 를 가지고 같은 날짜가 있는지 검사를 했는데 기준 날짜일 때 true 로 설정한다.
        this.isStandardDate[index] = true;

    } // End of method [setStandardDateToTrue]

    /**
     * [method] isStandardDate 의 index 번째의 값을 가져온다.
     *
     * @param index 배열의 index
     * @return isStandardDate[index] 의 값
     */
    public boolean getStandardDateToIndex(int index) {
        return this.isStandardDate[index];
    } // End of method [getStandardDateToIndex]


    // ========================================== winCount ==========================================
    /**
     * [method] winCount 의 index 번째의 값을 +1 한다.
     *
     * <p>
     *     검사한 날짜가 같은 날짜일 때, 내가 승리한 경기이면 기준 날짜의 winCount 값을 +1 하는 것이다.
     * </p>
     * @param index 배열의 index
     */
    public void addOneToWinCount(int index) {

        // [iv/i]winCount : 같은 날짜의 BilliardData 의 정보로 내가 승리한 경기 일 때, 승리 횟수를 +1 한다.
        this.winCount[index] += 1;

    } // End of method [addOneToWinCount]

    /**
     * [method] winCount 의 index 번째의 값을 가져온다.
     *
     * @param index 배열의 index
     * @return winCount[index] 의 값
     */
    public int getWinCountToIndex(int index) {
        return winCount[index];
    } // End of method [getWinCountToIndex]


    // ========================================== lossCount ==========================================
    /**
     * [method] lossCount 의 index 번째의 값을 +1 한다.
     *
     * <p>
     *     검사한 날짜가 같은 날짜일 때, 내가 패배한 경기이면 기준 날짜의 lossCount 값을 +1 하는 것이다.
     * </p>
     *
     * @param index 배열의 index
     */
    public void addOneToLossCount(int index) {

        // [iv/i]lossCount : 같은 날짜의 BilliardData 의 정보로 내가 패배한 경기 일 때, 패배 횟수를 +1 한다.
        this.lossCount[index] += 1;

    } // End of method [addOneToLossCount]


    /**
     * [method] lossCount 의 index 번째의 값을 가져온다.
     *
     * @param index 배열의 index
     * @return lossCount[index] 의 값
     */
    public int getLossCountToIndex(int index) {
        return lossCount[index];
    } // End of method [getLossCountToIndex]

    // ========================================== month ==========================================
    /**
     * [method] month 의 index 번째에 값을 넣는다.
     *          billiardData 의 date 에서 month 값만 저장한다.
     *
     *
     */
    public void setMonthToIndex (int index, int month) {
        this.month[index] = month;
    }

    /**
     * [method] month 의 index 번째의 값을 가져온다.
     *
     */
    public int getMonthToIndex(int index){
        return month[index];
    }

    // ========================================== SameDateItem ==========================================
    /**
     * [method] 같은 날짜일 때, 기준 날짜인 index 번째에 추가한다.
     *          billiard 테이블의 count 를 추가하고 count 의 승리 패배 여부를 넣은 SameDateItem 객체를 sameDateItemArrayList 에 추가한다.
     *
     * <p>
     *     같은 날짜가 여러개 일때, 기준 날짜의 index 번째에 추가한다.
     *     기준 날짜와 비교 날짜의 sameDateItem 을 만들어 추가한다.
     * </p>
     *
     * @param index 배열의 index
     * @param sameDateItem 그 날짜의 billiard 테이블의 count 값과 승패 여부가 담긴 객체
     *
     */
    public void addSameDateItemToIndex(int index, SameDateItem sameDateItem) {
        this.sameDateItemArrayList[index].add(sameDateItem);
    } // End of method []

    /**
     * [method] 기준 날짜인 index 번째에서 같은 날짜의 값이 담겨져 있는 배열을 가져온다.
     *
     * @param index 배열의 index
     */
    public ArrayList<SameDateItem> getSameDateItemToIndex(int index) {
        return this.sameDateItemArrayList[index];
    } // End of method [getSameDateItemToIndex]


    // ========================================== arraySize ==========================================
    /**
     * [method] 필드의 배열의 사이즈
     *
     */
    public int getArraySize() {
        return arraySize;
    }


    // [inner class]
    public static class SameDateItem {

        // instance variable
        private long billiardCountNumber;
        private boolean isWinner;

        // constructor
        public SameDateItem(long billiardCountNumber, boolean isWinner) {
            this.billiardCountNumber = billiardCountNumber;
            this.isWinner = isWinner;
        }

        // method : getter
        public boolean isWinner() {
            return isWinner;
        }

        public long getBilliardCountNumber() {
            return billiardCountNumber;
        }

    } // End of Class [SameDateList]
}
