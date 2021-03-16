package com.skyman.billiarddata.management.statistics;

import com.skyman.billiarddata.developer.DeveloperManager;

import java.io.Serializable;
import java.util.ArrayList;

public class SameDateChecker implements Serializable {

    // constant
    private static final String CLASS_NAME = SameDateChecker.class.getSimpleName();

    // instance variable
    private boolean[] isCheckedDate;                                // 해당 date 가 검사되었는지
    private boolean[] isStandardDate;                               // 해당 date 가 기준 날짜인지
    private int[] winCount;                                         // 기준 날짜일 때만 나의 승리 횟수를 카운트 한다.
    private int[] lossCount;                                        // 기준 날짜일 때만 나의 패배 횟수를 카운트 한다.
    private int[] year;                                             // 기준 날짜일 때만 기준 날짜의 year 를 저장한다.
    private int[] month;                                            // 기준 날짜일 때만 기준 날짜의 month 를 저장한다.
    private ArrayList<SameDateItem> sameDateItemArrayList[];        // 기준 날짜일 때만 이 날짜와 같은 billiardDataArrayList 의 'count' 와 '나의 승리 여부'를 배열 형태로 저장한다.
    private int arraySize;                                          // 해당 SameDateChecker 는 검사하는 billiardDataArrayList 의 size 만큼 생상된다.

    // constructor
    public SameDateChecker(int arraySize) {

        // 모든 필드를 arraySize 만큼 배열을 생성
        this.isCheckedDate = new boolean[arraySize];
        this.isStandardDate = new boolean[arraySize];
        this.winCount = new int[arraySize];
        this.lossCount = new int[arraySize];
        this.year = new int[arraySize];
        this.month = new int[arraySize];
        this.sameDateItemArrayList = new ArrayList[arraySize];

        // [iv/i]arraySize : billiardData 가 담긴 ArrayList 의 크기
        this.arraySize = arraySize;

    }


    /**
     * [method] 배열 필드를 초기값을 설정한다.
     */
    public void initArray() {

        // [cycle 1] : size 크기 만큼
        for (int index = 0; index < arraySize; index++) {

            // [iv/b]isCheckedDate, isStandardDate : 초기값을 false 로 설정하기
            this.isCheckedDate[index] = false;
            this.isStandardDate[index] = false;

            // [iv/i]winCount, lossCount : 초기값을 0 으로 설정한다. /
            this.winCount[index] = 0;
            this.lossCount[index] = 0;

            // [iv/i]year : 초기값을 0 으로 설정한다.
            this.year[index] = 0;

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
     * 검사한 날짜가 같은 날짜일 때, 내가 승리한 경기이면 기준 날짜의 winCount 값을 +1 하는 것이다.
     * </p>
     *
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
     * 검사한 날짜가 같은 날짜일 때, 내가 패배한 경기이면 기준 날짜의 lossCount 값을 +1 하는 것이다.
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


    // ========================================== year ==========================================
    /**
     * [method] year 의 index 번째에 값을 넣는다.
     * billiardData 의 date 에서 year 값만 저장한다.
     */
    public void setYearToIndex(int index, int year) {
        this.year[index] = year;
    }

    /**
     * [method] year 의 index 번째의 값을 가져온다.
     */
    public int getYearToIndex(int index) {
        return year[index];
    }



    // ========================================== month ==========================================

    /**
     * [method] month 의 index 번째에 값을 넣는다.
     * billiardData 의 date 에서 month 값만 저장한다.
     */
    public void setMonthToIndex(int index, int month) {
        this.month[index] = month;
    }

    /**
     * [method] month 의 index 번째의 값을 가져온다.
     */
    public int getMonthToIndex(int index) {
        return month[index];
    }

    // ========================================== SameDateItem ==========================================

    /**
     * [method] 같은 날짜일 때, 기준 날짜인 index 번째에 추가한다.
     * billiard 테이블의 count 를 추가하고 count 의 승리 패배 여부를 넣은 SameDateItem 객체를 sameDateItemArrayList 에 추가한다.
     *
     * <p>
     * 같은 날짜가 여러개 일때, 기준 날짜의 index 번째에 추가한다.
     * 기준 날짜와 비교 날짜의 sameDateItem 을 만들어 추가한다.
     * </p>
     *
     * @param index        배열의 index
     * @param sameDateItem 그 날짜의 billiard 테이블의 count 값과 승패 여부가 담긴 객체
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
     */
    public int getArraySize() {
        return arraySize;
    }


    // ========================================== toString ==========================================
    public void printData() {

        DeveloperManager.displayLog(CLASS_NAME, "=================================== SameDateChecker ===================================");

        for (int index = 0; index < getArraySize(); index++) {

            DeveloperManager.displayLog(CLASS_NAME, "=================================== " + index + " ===================================");

            // standard date
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "standard date : " + getStandardDateToIndex(index) + ""
            );

            // year
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "month : " + getYearToIndex(index) + ""
            );

            // month
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "month : " + getMonthToIndex(index) + ""
            );

            // win
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "win : " + getWinCountToIndex(index) + ""
            );

            // loss
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "loss : " + getLossCountToIndex(index) + ""
            );

            // checked Date
            DeveloperManager.displayLog(
                    CLASS_NAME,
                    "checked date :" + getCheckedDateToIndex(index) + ""
            );


            // same Date item
            for (int sdiIndex = 0; sdiIndex < getSameDateItemToIndex(index).size(); sdiIndex++) {

                // is winner
                DeveloperManager.displayLog(
                        CLASS_NAME,
                        "==> same date / isWinner : " + getSameDateItemToIndex(index).get(sdiIndex).isWinner() + ""
                );

                // billiard count
                DeveloperManager.displayLog(
                        CLASS_NAME,
                        "==> same date / billiardCount : " + getSameDateItemToIndex(index).get(sdiIndex).getBilliardCount() + ""
                );
            } // End of [for]
        } // End of [for]

        DeveloperManager.displayLog(
                CLASS_NAME,
                "============================================================================"
        );

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    // [inner class]
    public static class SameDateItem {

        // instance variable
        private long billiardCount;
        private boolean isWinner;
        private int index;      // count 가 차례대로 증가하는 값이 아닐 수 도 있으므로 이 같은 날짜인 billiard 는 배열에서 몇 번째인지를 알고 있어야 한다.

        // constructor
        public SameDateItem(long billiardCount, boolean isWinner, int index) {
            this.billiardCount = billiardCount;
            this.isWinner = isWinner;
            this.index = index;
        }

        // method : getter
        public boolean isWinner() {
            return isWinner;
        }

        public long getBilliardCount() {
            return billiardCount;
        }

        public int getIndex() {
            return index;
        }
    } // End of Class [SameDateList]
}
