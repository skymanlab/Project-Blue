package com.skyman.billiarddata.etc.calendar;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class SameDateCheckerUtil {

    // constant
    private final String CLASS_NAME = SameDateCheckerUtil.class.getSimpleName();
    private final String DATE_DELIMITER = "년월일 ";

    /**
     * [method] billiardDataArrayList 로 DateChecker 를 만들기
     */
    public SameDate createSameDate(UserData userData, ArrayList<BilliardData> billiardDataArrayList) {

        final String METHOD_NAME = "[createSameDate] ";

        // DateChecker : 각 date 에 win 과 loss 가 몇 개인지 구분
        SameDate sameDate = new SameDate(billiardDataArrayList.size());
        sameDate.initArray();

        // cycle 1 : billiardDataArrayList 의 Date 를 가져와 중복 date 를 찾아 체크하고, 그 date 의 win 과 loss 의 수를 구한다.
        for (int index = 0; index < billiardDataArrayList.size(); index++) {

            // [lv/i]dateDivision : 날짜를 년, 월, 일 을 구분하여 int 배열로 만든다.
            int[] dateDivision = changeDateToIntArrayType(billiardDataArrayList.get(index).getDate());

            // [lv/C]SameDateChecker : index 번째의 year 에 해당월의 넣는다. / 기준 날짜(index)에만 해당한다.
            sameDate.setYearToIndex(index, dateDivision[0]);

            // [lv/C]SameDateChecker : index 번째의 month 에 해당월의 넣는다. / 기준 날짜(index)에만 해당한다.
            sameDate.setMonthToIndex(index, dateDivision[1]);

            // check 1 : checkedDate[index] 의 값이 false 일 때 - 체크 안 된 date
            if (!sameDate.getCheckedDateToIndex(index)) {

                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> ======================= 기준 날짜(index) " + index + " 번째 date : " + billiardDataArrayList.get(index).getDate() + " =======================");

                // DateChecker : 위 의 check 1 을 수행 하였으므로 기준 날짜(index 번째)를 true 로 - checkedDate
                sameDate.setCheckedDateToTrue(index);

                // DateChecker : 위 의 check 1 을 수행 하였으므로 기준 날짜(index 번째)를 true 로 - checkedDate
                sameDate.setStandardDateToTrue(index);


                // [check 2] : 나의 이름과 index 번째의 winner 의 이름과 같다. 즉, 내가 승리한 게임이다. / method : compareUserNameWithWinner
                if (compareUserNameWithWinner(userData.getId(), userData.getName(), billiardDataArrayList.get(index).getWinnerId(), billiardDataArrayList.get(index).getWinnerName())) {

                    // [lv/C]SameDateChecker : 기준 날짜(index)가 승리했다. 따라서 기준 날짜(index)의 winCount 값을 +1 한다.
                    sameDate.addOneToWinCount(index);
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째>  기준 날짜(index)는 내가 승리한 게임입니다. 기준 날짜(index)의 winCount 를 +1 을 수행하였습니다.");

                    // [lv/C]SameDateChecker : 기준 날짜(index)가 승리했다. 따라서 기준 날짜(index)에 SameDateItem 항목을 만들어서 추가한다.
                    sameDate.addSameDateItemToIndex(index, new SameDate.SameDateItem(billiardDataArrayList.get(index).getCount(), true, index));
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> 기준 날짜(index)의 billiard count 와 승리 여부(true)를 담은 SameDateItem 을 기준 날짜(index)에 추가하였습니다.");

                } else {

                    // [lv/C]SameDateChecker : 기준 날짜(index)가 패배했다. 따라서 기준 날짜(index)의 lossCount 값을 +1 한다.
                    sameDate.addOneToLossCount(index);
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> 기준 날짜(index)는 내가 패배한 게임입니다. 기준 날짜의 lossCount 를 +1 을 수행하였습니다.");

                    // [lv/C]SameDateChecker : 기준 날짜(index)가 승리했다. 따라서 기준 날짜(index)에 SameDateItem 항목을 만들어서 추가한다.
                    sameDate.addSameDateItemToIndex(index, new SameDate.SameDateItem(billiardDataArrayList.get(index).getCount(), false, index));
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> 기준 날짜(index)의 billiard count 와 승리 여부(false)를 담은 SameDateItem 을 기준 날짜(index)에 추가하였습니다.");

                } // [check 2]

                // cycle 2 : [index+1] 인 배열부터 배열 끝 까지 date 가 같은지 비교
                for (int nextIndex = index + 1; nextIndex < billiardDataArrayList.size(); nextIndex++) {

                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> >>>>>>>>>>>>>>>> 비교 날짜(nextIndex)는 " + nextIndex + " 입니다. >>>>>>>>>>>>>>>> ");
                    compareIndexWithNextIndex(userData, billiardDataArrayList, sameDate, index, nextIndex);

                } // cycle 2

            } else {
                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> =======================  기준 날짜(index) " + index + " 번째는 이미 검사하였습니다.  ======================= ");
            } // check 1

        } // cycle 1

        return sameDate;

    } // End of method [createSameDate]

    /**
     * [method] billiardDataArrayList 의 index 와 nextIndex 번째의 date 가 같은지 비교하여 win, loss 구분한다.
     *
     * @param userData              유저의 데이터
     * @param billiardDataArrayList 비교하는 데이터가 담긴 ArrayList
     * @param sameDate
     * @param index                 기준 날짜의 index
     */
    private void compareIndexWithNextIndex(UserData userData, ArrayList<BilliardData> billiardDataArrayList, SameDate sameDate, int index, int nextIndex) {

        final String METHOD_NAME = "[compareIndexWithNextIndex] ";

        //  [check 1] : sameDateChecker / 비교 날짜(nextIndex)의 isCheckedDate 가 false 이다. 즉, 비교가 끝날 날짜이다.
        if (!sameDate.getCheckedDateToIndex(nextIndex)) {

            // [check 2] : billiardDataArrayList / 기준 날짜(index)와 비교 날짜(nextIndex) 의 date 가 같다. / method : compareDateOfBilliardData
            if (compareDateOfBilliardData(billiardDataArrayList, index, nextIndex)) {

                // [lv/C]SameDateChecker : 비교 날짜의 'isCheckedDate' 를 true 로 바꾼다. 즉, 기준 날짜와 같은 날짜이므로 체크한 날짜로 바꾸는 것이다.
                sameDate.setCheckedDateToTrue(nextIndex);

                // [check 3] : 비교 날짜(nextIndex)의 winner 와 userName 이 같다. 즉, 내가 승리한 게임이다. / method : compareUserNameWithWinner
                if (compareUserNameWithWinner(userData.getId(), userData.getName(), billiardDataArrayList.get(nextIndex).getWinnerId(), billiardDataArrayList.get(nextIndex).getWinnerName())) {

                    // [lv/C]SameDateChecker : 비교 날짜(nextIndex)가 승리했다. 따라서 기준 날짜(index)의 winCount 값을 +1 한다. / 비교 날짜(nextIndex)의 winCount 값은 0 그대로이다.
                    sameDate.addOneToWinCount(index);
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째>  비교 날짜(nextIndex)는 내가 승리한 게임입니다. 기준 날짜(index)의 winCount 를 +1 을 수행하였습니다.");

                    // [lv/C]SameDateChecker : 비교 날짜(nextIndex)가 승리했다. 따라서 기준 날짜(index)에 SameDateItem 항목을 만들어서 추가한다.
                    sameDate.addSameDateItemToIndex(index, new SameDate.SameDateItem(billiardDataArrayList.get(nextIndex).getCount(), true, nextIndex));
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> 비교 날짜(nextIndex)의 billiard count 와 승리 여부(true)를 담은 SameDateItem 을 기준 날짜(index)에 추가하였습니다.");

                } else {

                    // [lv/C]SameDateChecker : 비교 날짜(index)가 패배했다. 따라서 기준 날짜(index)의 lossCount 값을 +1 한다. / 비교 날짜(nextIndex)의 lossCount 값은 0 그대로이다.
                    sameDate.addOneToLossCount(index);
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째>  비교 날짜(nextIndex)는 내가 패배한 게임입니다. 기준 날짜(index)의 lossCount 를 +1 을 수행하였습니다.");

                    // [lv/C]SameDateChecker : 비교 날짜(nextIndex)가 패배했다. 따라서 기준 날짜(index)에 SameDateItem 항목을 만들어서 추가한다.
                    sameDate.addSameDateItemToIndex(index, new SameDate.SameDateItem(billiardDataArrayList.get(nextIndex).getCount(), false, nextIndex));
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> 비교 날짜(nextIndex)의 billiard count 와 승리 여부(false)를 담은 SameDateItem 을 기준 날짜(index)에 추가하였습니다.");

                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> 비교 날짜(nextIndex)의 date 는 기준 날짜(index)의 date 와 다릅니다. 다음 비교 날짜(nextIndex+1)를 비교합니다.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "index<" + index + "번째> 비교 날짜(nextIndex)의 checkedDate 는 true 이므로 기준 날짜(index)의 date 와 비교하지 않아도 됩니다.");
        } // [check 1]

    } // End of method [compareIndexWithNextIndex]


    /**
     * [method] index 과 nextIndex 의 date 를 비교하여 같으면 nextIndex 를 검사한 날짜로 체크하기
     *
     * @param billiardDataArrayList
     * @param index
     * @param nextIndex
     * @return billiardDataArrayList 를 index 번재와 nextIndex 번째의 date 를 비교한 결과를 반환한다.
     */
    private boolean compareDateOfBilliardData(ArrayList<BilliardData> billiardDataArrayList, int index, int nextIndex) {

        final String METHOD_NAME = "[compareDateOfBilliardData] ";

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "<" + index + "> index 번째 date : " + billiardDataArrayList.get(index).getDate() + " / nextIndex 번째 date : " + billiardDataArrayList.get(nextIndex).getDate());
        // [lv/b]isSameDate : 두 개의 날짜가 같은 날짜인지 구분
        boolean isSameDate;

        // [check 1] : index 번째와 nextIndex 번째의 date 가 같다.
        if (billiardDataArrayList.get(index).getDate().equals(billiardDataArrayList.get(nextIndex).getDate())) {

            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "<" + index + "> index 와 nextIndex 번째는 같은 날짜입니다.");
            isSameDate = true;

        } else {
            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "<" + index + "> index 와 nextIndex 번째는 다른 날짜입니다.");
            isSameDate = false;
        } // [check 1]

        return isSameDate;

    } // End of method [compareDateOfBilliardData]


    /**
     * [method] userName 과 winner 가 같은 때, true 를 반환한다.
     *
     * @param winnerId   userData 의 id
     * @param userName   userData 의 name
     * @param winnerId   billiardData 의 winner 의 id
     * @param winnerName billiardData 의 winner 의 name
     * @return userName 과 winner 를 비교한 결과
     */
    private boolean compareUserNameWithWinner(long userId, String userName, long winnerId, String winnerName) {

        final String METHOD_NAME = "[compareUserNameWithWinner] ";

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "user 의 id = " + userId + ", name = " + userName + " / winner 의 id = " + winnerId + ", name = " + winnerName);

        // [lv/b]isSameName : userName 과 winner 를 비교한 결과
        boolean isSameName;

        // [check 1] : userName 이 승리했다.
        if ((userId == winnerId) && userName.equals(winnerName)) {
            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "userName : " + userName + " 은 승리했습니다.");
            isSameName = true;
        } else {
            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "userName : " + userName + " 은 패배했습니다.");
            isSameName = false;
        } // [check 1]

        return isSameName;

    } // End of method [compareUserNameWithWinner]


    /**
     * [method] date 문자열을 StringTokenizer 로 구분한 뒤, 각 문자열을 int 로 parse 한 다음 배열에 담아 반환한다.
     *
     * @param date 날짜 문자열
     * @return 분할 된 날짜가 integer 로 변환되어 담긴 배열 [0]:year, [1]:month, [2]:dayOfMonth
     */
    private int[] changeDateToIntArrayType(String date) {

        // [lv/i]dateTokenList : 매개변수 문자열이 분할되어 integer 로 parsing 된 다음 저장될 변수
        int[] dateTokenList = new int[3];

        // [lv/C]StringTokenizer : 매개변수 문자열을 '년월일 ' 로 나누기 / DATE_DELIMITER : '년월일 ' / delimiter : 구분자
        StringTokenizer tokenizer = new StringTokenizer(date, DATE_DELIMITER);

        // [cycle 1] : 구분된 값들이 있을 때까지 해당 값을 배열에 넣기
        for (int index = 0; tokenizer.hasMoreTokens(); index++) {

            // [lv/i]dateTokenList : 분할 된 토큰을 year, month, day 순으로 integer 로 parsing 한 값을 담는다.
            dateTokenList[index] = Integer.parseInt(tokenizer.nextToken());

        } // [cycle 1]

        return dateTokenList;

    } // End of method [changeDateToIntArrayType]


    /**
     * [method] billiardDataArrayList 를 가지고 각 날짜의 win 과 loss 카운트하여 DateChecker 를 만든다.
     * 그리고 이 DateChecker 를 통해 기준 날짜의 EventDay 를 만들어 eventDayArrayList 에 추가한다.
     * 마지막으로 이 eventDayArrayList 를 calendarView 에 설정한다.
     */
    private void printLog(SameDate sameDate) {

        final String METHOD_NAME = "[printLog] ";

        // [check 1] : SameDateChecker 가 있다.
        if (sameDate.getArraySize() != 0) {

            // [cycle 1] : SameDateChecker 의 size 만큼
            for (int index = 0; index < sameDate.getArraySize(); index++) {
                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "기준 날짜인 " + index + " 번째 / isCheckedDate : " + sameDate.getStandardDateToIndex(index));
                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "기준 날짜인 " + index + " 번째 / isStandardDate : " + sameDate.getStandardDateToIndex(index));
                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "기준 날짜인 " + index + " 번째 / winCount : " + sameDate.getWinCountToIndex(index));
                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "기준 날짜인 " + index + " 번째 / LossCount : " + sameDate.getLossCountToIndex(index));
                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "기준 날짜인 " + index + " 번째 / month : " + sameDate.getMonthToIndex(index));

                // [lv/C]ArrayList<Long> : dateChecker 의 BilliardCounter 에서 같은 날짜의 billiard 테이블의 count 값이 담긴
                ArrayList<SameDate.SameDateItem> sameDateItemArrayList = sameDate.getSameDateItemToIndex(index);

                // [cycle 2] : index 번째에 추가되어있는 같은 날짜의 데이터가 담긴 sameDateItemArrayList 의 size
                for (int checkerIndex = 0; checkerIndex < sameDateItemArrayList.size(); checkerIndex++) {

                    // index 번째에 추가되어있는 같은 날짜의 데이터가 담긴 sameDateItemArrayList 에서 billiard count 의 번호를 출력한다.
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "기준 날짜인 " + index + " 번째 / sameDateItemArrayList 의 " + checkerIndex + "  번재 / billiard 의 count 번호 => " + sameDateItemArrayList.get(checkerIndex).getBilliardCount());
                    DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "기준 날짜인 " + index + " 번째 / sameDateItemArrayList 의 " + checkerIndex + "  번재 / billiard 의 count 번호의 isWinner => " + sameDateItemArrayList.get(checkerIndex).isWinner());

                } // [cycle 2]

            } // [cycle 1]

        } else {
            DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "sameDateChecker 가 없습니다. 즉, billiardDataArrayList 가 없어서 만들어지지 않았습니다.  ");
        } // [check 1]

    } // End of method [printLog]


}
