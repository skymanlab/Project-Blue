package com.skyman.billiarddata.etc.calendar;

import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.developer.Display;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class SameDateChecker2 {

    private static final Display CLASS_LOG_SWITCH = Display.ON;
    private static final String CLASS_NAME = "SameDateChecker2";

    // constant
    private static final String DATE_DELIMITER = "년월일 ";

    // instance variable
    boolean isCheckedDate[];    // 검사 완료 여부
//    boolean isBaseDate[];       // billiardData 의 date 가 기준 날짜(SameDate 에 들어가는 날짜) 인가? ==> 필요없어짐


    /**
     * billiardDataArrayList 의 billiarData 객체의 date 를 이용하여 같은 날짜를 검사하여
     * sameDate2 객체를 만들고 이를 ArrayList 에 담아서 반환한다.
     *
     * @param userData              나의 승리 여부를 판별하기 위한
     * @param billiardDataArrayList 모든 billiardData 를 담은 객체( 검사의 주체 date 정보를 담은 )
     * @return sameDate2 객체가 담긴 ArrayList
     */
    public ArrayList<SameDate2> checkSameDate(UserData userData, ArrayList<BilliardData> billiardDataArrayList) {

        if (!billiardDataArrayList.isEmpty()) {

            ArrayList<SameDate2> sameDateArrayList = new ArrayList<>();                     // SameDate2 객체가 담길 ArrayList - return 객체
            List<String> dateList = new ArrayList<>();                                      // 날짜를 String 값으로 저장하기 위한 변수 : 정렬하기 위해서

            // initData() : 검사를 진행하기 위해 필요한 맴버 변수 초기화
            initData(billiardDataArrayList.size());

            for (int index = 0; index < billiardDataArrayList.size(); index++) {

                if (!isCheckedDate[index]) {                                                // if : 아직 검사되지 않은 날짜인지 확인하기
                    // 진행 조건( isCheckedData=false ) : 검사되지 않은 날짜 일때

                    isCheckedDate[index] = true;                                            // isCheckedData : 검사 진행 중이므로 true로 변경
                    SameDate2 sameDate = new SameDate2();                                   // sameDate : 검사 하고 있고 기준이 되는 날짜 일 때, sameDate2 객체 생성

                    // separateDate() : SameDate2.Date 항목 추가
                    separateDate(sameDate, billiardDataArrayList.get(index).getDate());

                    dateList.add(
                            billiardDataArrayList.get(index).getDate()
                    );                                                                      // dateList : 정렬을 위한 date 문자열 저장

                    // checkMyWin() : 승리 여부 체크 후 해당 항목(승리, 패배) 업데이트
                    checkMyWin(sameDate, userData, billiardDataArrayList.get(index));

                    // addReference() : sameDate2.Reference 항목 추가
                    addReference(sameDate, billiardDataArrayList.get(index), index);

                    for (int nextIndex = index + 1; nextIndex < billiardDataArrayList.size(); nextIndex++) {

                        // if 1 : 아직 검사되지 않은 날짜인지 확인하기
                        if (!isCheckedDate[nextIndex]) {
                            // 진행 조건( isCheckedDate=false ) : 검사되지 않은 날짜 일때

                            // if 2 : 기준 날짜와 같은지 확인하기
                            if (equalBaseDate(billiardDataArrayList, index, nextIndex)) {
                                // 진행 조건( equalBaseDate()=true ) : index번째와 nextIndex번째의 billiardData 의 date 가 같으면

                                isCheckedDate[nextIndex] = true;                                                // isCheckedDate : 기준 날짜와 같으면 기준 날짜에 해당하는 sameDate2 객체에 추가하므로 이때 ture로 변경

                                // checkMyWin() : 승리 여부 체크 후 해당 항목(승리, 패배) 업데이트
                                checkMyWin(sameDate, userData, billiardDataArrayList.get(nextIndex));
                                // addReference() : sameDate2.Reference 항목 추가
                                addReference(sameDate, billiardDataArrayList.get(nextIndex), nextIndex);

                            }
                        }
                    }

                    sameDateArrayList.add(sameDate);                                            // sameDateArrayList : 모든 검사가 완료된 sameDate2 객체를 arrayList 에 추가
                }
            }
            return sortDate(sameDateArrayList, dateList);

        } else {
            return null;
        }

    } //


    /**
     * billiardDataArrayList의 1:1 대응되어 검사 여부를 확인하기 위한
     * isCheckedDate 배열을 생서하고 모두 false로 초기화한다.
     *
     * @param size billiardDataArrayList의 size
     */
    private void initData(int size) {

        if (size != 0) {
            isCheckedDate = new boolean[size];

            for (int index = 0; index < size; index++) {
                isCheckedDate[index] = false;
            }
        }

    } //


    /**
     * billiardData 의 date 를 년, 월, 일 단위로 분리하여
     * SameDate2.Date 객체에 추가 하기
     *
     * @param sameDate 분리된 년,월,일 단위가 담길 SameDate 객체
     * @param date     billiardData 의 date
     */
    private void separateDate(SameDate2 sameDate, String date) {

        // tokenizer: "yyyy년 MM월 dd일" 형태의 date 문자열을 "년월일"로 나누고, int 타입으로 변환 / delimiter(구분자) -> DATE_DELIMITER = "년월일"
        StringTokenizer tokenizer = new StringTokenizer(date, DATE_DELIMITER);

        for (int index = 0; tokenizer.hasMoreTokens(); index++) {

            // switch : 분할 된 토큰을 year, month, day 순으로 integer 로 parsing 한 값 sameDate2 객체에 담는다.
            switch (index) {
                case 0:
                    sameDate.getGameDate().setYear(Integer.parseInt(tokenizer.nextToken()));
                    break;
                case 1:
                    sameDate.getGameDate().setMonth(Integer.parseInt(tokenizer.nextToken()));
                    break;
                case 2:
                    sameDate.getGameDate().setDay(Integer.parseInt(tokenizer.nextToken()));
            }

        }
        sameDate.getGameDate().setDate(date);

    }


    /**
     * userData 의 'id', 'name'과
     * billiardData 의 'winnerId', 'winnerName'을 비교하여
     * sameDate 의 Counter 객체에 나의 승리이면 winCounter를 +1, 나의 패배이면 lossCounter 를 +1 한다.
     * 그리고 이 객체를 반환한다.
     *
     * @return 나의 승리 여부 (true : 나의 승리, false : 나의 패배)
     */
    private void checkMyWin(SameDate2 sameDate, UserData userData, BilliardData billiardData) {

        if (
                (userData.getId() == billiardData.getWinnerId()) &&
                        userData.getName().equals(billiardData.getWinnerName())) {
            sameDate.getMyGameCounter().plusOneWinCounter();
            sameDate.getMyGameRecord().add(Record.WIN);
        } else {
            sameDate.getMyGameCounter().plusOneLossCounter();
            sameDate.getMyGameRecord().add(Record.LOSS);
        }
    }

    /**
     * sameDate2 객체에 reference 객체를 추가한다.
     *
     * @param sameDate       reference 객체가 추가될
     * @param billiardData   count 값을 가져올 billiardData 객체
     * @param arrayListIndex 검사 진행중인 객체의 arrayList 의 index
     */
    private void addReference(SameDate2 sameDate, BilliardData billiardData, int arrayListIndex) {
        sameDate.getReferenceArrayList().add(new SameDate2.Reference((int) billiardData.getCount(), arrayListIndex));
    }

    /**
     * 기준 날짜와 비교되는 날짜가 같은지를 비교하여 그 결과를 반환한다.
     *
     * @param billiardDataArrayList
     * @param baseDateIndex
     * @param comparedDateIndex
     * @return 같은 날짜이면 true, 다른 날짜 이면 false
     */
    private boolean equalBaseDate(ArrayList<BilliardData> billiardDataArrayList, int baseDateIndex, int comparedDateIndex) {

        if (billiardDataArrayList.get(baseDateIndex).getDate().equals(
                billiardDataArrayList.get(comparedDateIndex).getDate())
        ) {
            return true;
        } else
            return false;
    }


    /**
     * 날짜를 정렬하고
     * 정렬 된 순서로 sameDate2 객체를 ArrayList 에 추가하여 반환한다.
     *
     * @param sameDateArrayList 날짜 순으로 정렬되지 않은 sameDate2 객체가 담긴 ArrayList
     * @param dateList          정렬되지 않은 date 가 담긴 List
     * @return 정렬된 순서로 SameDate2 객체가 단긴 ArrayList
     */
    private ArrayList<SameDate2> sortDate(ArrayList<SameDate2> sameDateArrayList, List<String> dateList) {

        // 날짜순으로 정렬
        Collections.sort(dateList);

        // 정렬된 날짜로 sameDateArrayList 만들기
        ArrayList<SameDate2> sortedArrayList = new ArrayList<>();

        for (int index = 0; index < dateList.size(); index++) {

            for (int sIndex = 0; sIndex < sameDateArrayList.size(); sIndex++) {
                if (dateList.get(index).equals(
                        sameDateArrayList.get(sIndex).getGameDate().getDate()
                )) {

                    sortedArrayList.add(sameDateArrayList.get(sIndex));
                }
            }

        }
        printLog(sortedArrayList);
        return sortedArrayList;

    } //


    /**
     * 디버그 로그 보기 : dataList 내용 확인
     *
     * @param dateList
     */
    public void printLog(List<String> dateList) {

        if (DeveloperManager.PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON) {

                Log.d(CLASS_NAME, "dateList 내용 확인");

                for (int index = 0; index < dateList.size(); index++) {
                    Log.d(CLASS_NAME, index + "번째 날짜 : " + dateList.get(index));
                }

            }

    } //

    /**
     * 디버그 로그 보기 : sameDateArrayList 내용 확인
     *
     * @param sameDate2ArrayList
     */
    public void printLog(ArrayList<SameDate2> sameDate2ArrayList) {

        if (DeveloperManager.PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON) {

                Log.d(CLASS_NAME, "[sameDataArrayList 내용 확인]");
                for (int index = 0; index < sameDate2ArrayList.size(); index++) {
                    Log.d(CLASS_NAME, "---- " + index + "번째 ----");

                    Log.d(CLASS_NAME, "Date / year : " + sameDate2ArrayList.get(index).getGameDate().getYear());
                    Log.d(CLASS_NAME, "Date / month : " + sameDate2ArrayList.get(index).getGameDate().getMonth());
                    Log.d(CLASS_NAME, "Date / day : " + sameDate2ArrayList.get(index).getGameDate().getDay());
                    Log.d(CLASS_NAME, "Counter / winCounter : " + sameDate2ArrayList.get(index).getMyGameCounter().getWinCounter());
                    Log.d(CLASS_NAME, "Counter / lossCounter : " + sameDate2ArrayList.get(index).getMyGameCounter().getLossCounter());
                    Log.d(CLASS_NAME, "Counter / totalGameCounter: " + sameDate2ArrayList.get(index).getMyGameCounter().getTotalGameCounter());

                    StringBuilder record = new StringBuilder();
                    record.append("Record : ");
                    StringBuilder referenceCount = new StringBuilder();
                    referenceCount.append("Reference / count : ");
                    StringBuilder referenceIndex = new StringBuilder();
                    referenceIndex.append("Reference / index : ");
                    for (int rIndex = 0; rIndex < sameDate2ArrayList.get(index).getReferenceArrayList().size(); rIndex++) {

                        record.append("[" + sameDate2ArrayList.get(index).getMyGameRecord().get(rIndex) + "]");
                        referenceCount.append("[" + sameDate2ArrayList.get(index).getReferenceArrayList().get(rIndex).getCount() + "]");
                        referenceIndex.append("[" + sameDate2ArrayList.get(index).getReferenceArrayList().get(rIndex).getIndex() + "]");
                    }
                    Log.d(CLASS_NAME, record.toString());
                    Log.d(CLASS_NAME, referenceCount.toString());
                    Log.d(CLASS_NAME, referenceIndex.toString());
                }
            }

    } //


}
