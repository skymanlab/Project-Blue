package com.skyman.billiarddata.etc.calendar;

import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;

import java.io.Serializable;
import java.util.ArrayList;

public class SameDate implements Serializable {

    // constant

    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "SameDate";

    // instance variable
    private boolean[] isCheckedDate;                                // 해당 billiardDate 객체의 date 가 검사되었는지 (for 구문을 돌면서 검사한 데이터는 넘어가기 위해서)
    private boolean[] isStandardDate;                               // 해당 billiardDate 객체의 date 가 기준 날짜인지 (기준 날짜 :
    private int[] winCount;                                         // 기준 날짜일 때만 나의 승리 횟수를 카운트 한다.
    private int[] lossCount;                                        // 기준 날짜일 때만 나의 패배 횟수를 카운트 한다.
    private int[] year;                                             // 기준 날짜일 때만 기준 날짜의 year 를 저장한다.
    private int[] month;                                            // 기준 날짜일 때만 기준 날짜의 month 를 저장한다.
    private ArrayList<SameDateItem> sameDateItemArrayList[];        // 기준 날짜일 때만 이 날짜와 같은 billiardDataArrayList 의 'count' 와 '나의 승리 여부'를 배열 형태로 저장한다.
    private int arraySize;                                          // 해당 SameDateChecker 는 검사하는 billiardDataArrayList 의 size 만큼 생상된다.

    // constructor
    public SameDate(int arraySize) {

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


    ///////////////////////////////////////////////////////
    // method /////////////////////////////////////////////
    ///////////////////////////////////////////////////////

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

    } //


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
    public void printLog(SameDate sameDate) {

        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {

                Log.d(CLASS_NAME, "[sameDate 내용 확인]");

                for (int index = 0; index < getArraySize(); index++) {

                    Log.d(CLASS_NAME, "---- " + index + "번째 ----");
                    Log.d(CLASS_NAME, "isCheckedDate :" + getCheckedDateToIndex(index));
                    Log.d(CLASS_NAME, "isStandardDate : " + getStandardDateToIndex(index));
                    Log.d(CLASS_NAME, "year : " + getYearToIndex(index));
                    Log.d(CLASS_NAME, "month : " + getMonthToIndex(index));
                    Log.d(CLASS_NAME, "winCount : " + getWinCountToIndex(index));
                    Log.d(CLASS_NAME, "lossCount : " + getLossCountToIndex(index));

                    // for 2: sameDateItemArrayList
                    for (int sdiIndex = 0; sdiIndex < getSameDateItemToIndex(index).size(); sdiIndex++) {

                        Log.d(CLASS_NAME, "sameDateItem / billiardCount : " + getSameDateItemToIndex(index).get(sdiIndex).getBilliardCount());
                        Log.d(CLASS_NAME,  "sameDateItem / isWinner : " + getSameDateItemToIndex(index).get(sdiIndex).isWinner());
                        Log.d(CLASS_NAME, "sameDateItem / index : " + getSameDateItemToIndex(index).get(sdiIndex).getIndex());

                    } //

                } //
            }
    }


    ///////////////////////////////////////////////////////
    // inner class ////////////////////////////////////////
    ///////////////////////////////////////////////////////

    /**
     * SameDate 의 isStandardDate 가 true 이면
     * 같은 날짜에 경기 데이터가 담긴 billiardData 를 참조하기 위한 billiardDataArrayList 의 순번(index)과
     * 경기수(
     * 나의 승리여부(나를 기준으로 정리한 데이터 이고, GameRecordDialog에서 필요한 데이터)가 담겨져 있다.
     */
    public static class SameDateItem {

        // instance variable
        private long billiardCount;                 // 데이터베이스에 저장된 billiardData 의 count(순번)
        private boolean isWinner;                   // 나의 승리 여부
        private int index;                          // sameDataChecker 와 1:1 대응되는 billiardDataArrayList 의 index

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
    } //

}
