package com.skyman.billiarddata.etc.calendar;

import com.skyman.billiarddata.developer.DeveloperLog;
import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.game.Date;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Reference;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.user.data.UserData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class SameDateGameAnalysis {

    SameDateGame allGame;                       // 년, 월, 일에 상관없이 모든 게임의 리스트
    Map<String, SameDateGame> sameYearGameList;     // 같은 년도의 게임 리스트 : <년도, 같은 년도에 한 게임>
    Map<String, SameDateGame> sameMonthGameList;    // 같은 년, 월의 게임 리스트 : <년도, 같은 달에 한 게임>
    Map<String, SameDateGame> sameDateGameList;     // 같은 년, 월, 일의 게임 리스트 : <년도, 같은 날짜에 한 게임>
    ArrayList<BilliardData> sortedBilliardDataList; // 정렬된 billiardDataList

    public SameDateGameAnalysis() {
        allGame = new SameDateGame();
        sameYearGameList = new LinkedHashMap<>();
        sameMonthGameList = new LinkedHashMap<>();
        sameDateGameList = new LinkedHashMap<>();
        sortedBilliardDataList = new ArrayList<>();
    }

    public SameDateGame getAllGame() {
        return allGame;
    }

    public Map<String, SameDateGame> getSameYearGameList() {
        return sameYearGameList;
    }

    public Map<String, SameDateGame> getSameMonthGameList() {
        return sameMonthGameList;
    }

    public Map<String, SameDateGame> getSameDateGameList() {
        return sameDateGameList;
    }

    public ArrayList<BilliardData> getSortedBilliardDataList() {
        return sortedBilliardDataList;
    }

    /**
     * 매개변수로 받은 UserData 객체와 ArrayList<BilliardData> 객체를 받고, ArrayList<BilliardaData> 의 모든 BilliardData 의 날짜를 검사하여 같은 날짜의 게임은 sameDataGame 객체에 필요한 정보를 저장하고, 이를 sameDateGameList 에 추가하는 메소드
     *
     * @param userData
     * @param billiardDataArrayList 분석할 BilliardData 객체가 담긴 ArrayList
     */
    public void analyzeSameDateGame(UserData userData, ArrayList<BilliardData> billiardDataArrayList) {

        if (!billiardDataArrayList.isEmpty()) {

            // billiardData 와 1:1 대응되어서 해당 데이터가 분석된 것인지 판단한다!
            boolean[] isAnalyzed = fillFalse(billiardDataArrayList.size());

            for (int index = 0; index < billiardDataArrayList.size(); index++) {

                // if 1 : index 번째의 billiardData 가 분석된 것인가?
                if (!isAnalyzed[index]) {       // --> 분석되지 않았을 때(isAnalyzed=true 이면)

                    // 분석 시작 시점에 분석된 내용이 담길 SameDataGame 객체 생성
                    SameDateGame sameDateGame = new SameDateGame();

                    // 분석된(할) 데이터이다!
                    isAnalyzed[index] = true;

                    // 날짜
                    sameDateGame.getDate().setDateByParsing(billiardDataArrayList.get(index).getDate());

                    // 정보 : record, cost, billiardDataArrayList
                    setInfo(sameDateGame, userData, billiardDataArrayList.get(index), index);

                    for (int nextIndex = index + 1; nextIndex < billiardDataArrayList.size(); nextIndex++) {

                        // if 2 : nextIndex 번째의 billiardData 가 분석된 것인가?
                        if (!isAnalyzed[nextIndex]) {       // --> 분석되지 않았을 때만(isAnalyzed=true 이면)

                            // if 2 : sameDateGame 의 날짜와 nextIndex 번째의 billiardData 의 날짜가 같은 날짜인가?
                            if (sameDateGame.getDate().equals(billiardDataArrayList.get(nextIndex).getDate())) {         // --> 같은 날짜 일때
                                // 분석된(할) 데이터이다!!
                                isAnalyzed[nextIndex] = true;                                                // isAnalyzed : 기준 날짜와 같으면 기준 날짜에 해당하는 sameDate2 객체에 추가하므로 이때 ture로 변경

                                setInfo(sameDateGame, userData, billiardDataArrayList.get(nextIndex), nextIndex);
                            }
                        }
                    }
                    // 같은 날짜의 billiardData 를 분석하였을 때 put()
                    sameDateGameList.put(billiardDataArrayList.get(index).getDate(), sameDateGame);
                }
            }
        }
    }

    /**
     * 매개변수로 받은 모든 BilliardData 객체의 날짜를 분석하여 년별(sameYearGame), 월별(sameMonthGame), 날짜별(sameDateGame)로 저장하는 메소드
     *
     * @param userData
     * @param billiardDataList 분석할 BilliardData 객체가 담긴 ArrayList
     */
    public void analyze(UserData userData, ArrayList<BilliardData> billiardDataList) {

        if (!billiardDataList.isEmpty()) {

            // billiardDataList 복사
            this.sortedBilliardDataList.addAll(billiardDataList);
            // 날짜 순으로 정렬
            sort(this.sortedBilliardDataList);

            // billiardData 와 1:1 대응되어서 해당 데이터가 분석된 것인지 판단한다!
            boolean[] isAnalyzed = fillFalse(this.sortedBilliardDataList.size());

            for (int index = 0; index < this.sortedBilliardDataList.size(); index++) {

                // if : index 번째의 billiardData 가 분석된 것인가?
                if (!isAnalyzed[index]) {       // --> 분석되지 않았을 때(isAnalyzed=true 이면)

                    // 분석된(할) 데이터이다!
                    isAnalyzed[index] = true;

                    Date date = Date.newInstanceByParsing(this.sortedBilliardDataList.get(index).getDate());

                    // all game
                    setInfo(allGame, userData, this.sortedBilliardDataList.get(index), index);

                    // same date game
                    SameDateGame sameDateGame = new SameDateGame();
                    sameDateGame.getDate().setDateByParsing(this.sortedBilliardDataList.get(index).getDate());
                    setInfo(sameDateGame, userData, this.sortedBilliardDataList.get(index), index);

                    // same Year game
                    SameDateGame sameYearGame = new SameDateGame();
                    sameYearGame.getDate().setYear(date.getYear());
                    setInfo(sameYearGame, userData, this.sortedBilliardDataList.get(index), index);

                    // same Month game
                    SameDateGame sameMonthGame = new SameDateGame();
                    sameMonthGame.getDate().setYear(date.getYear());
                    sameMonthGame.getDate().setMonth(date.getMonth());
                    setInfo(sameMonthGame, userData, this.sortedBilliardDataList.get(index), index);


                    for (int nextIndex = index + 1; nextIndex < this.sortedBilliardDataList.size(); nextIndex++) {

                        // if : nextIndex 번째의 billiardData 가 분석된 것인가?
                        if (!isAnalyzed[nextIndex]) {       // --> 분석되지 않았을 때만(isAnalyzed=true 이면)

                            Date comparedDate = Date.newInstanceByParsing(this.sortedBilliardDataList.get(nextIndex).getDate());

                            // if : year 가 같은가?
                            if (sameDateGame.getDate().equalYear(comparedDate.getYear())) {

                                setInfo(sameYearGame, userData, this.sortedBilliardDataList.get(nextIndex), nextIndex);

                                // if : month 가 같은가?
                                if (sameDateGame.getDate().equalMonth(comparedDate.getMonth())) {

                                    setInfo(sameMonthGame, userData, this.sortedBilliardDataList.get(nextIndex), nextIndex);

                                    // if : dayOfMonth 가 같은가?
                                    if (sameDateGame.getDate().equalDayOfMonth(comparedDate.getDayOfMonth())) {

                                        // year, month, dayOfMonth 가 모두 같을 때를 기준으로 isAnalyzed 를 true 로 변경
                                        isAnalyzed[nextIndex] = true;
                                        setInfo(sameDateGame, userData, this.sortedBilliardDataList.get(nextIndex), nextIndex);

                                        // all game
                                        setInfo(allGame, userData, this.sortedBilliardDataList.get(nextIndex), nextIndex);

                                    }
                                }
                            }
                        }
                    }

                    // date
                    // 같은 날짜의 billiardData 를 분석하였을 때 put()
                    sameDateGameList.put(sameDateGame.getDate().toString(), sameDateGame);

                    // if : year / sameYearGameList 에 포함된 SameDateGame 객체인가?
                    if (!sameYearGameList.containsKey(new Date(date.getYear(), 0, 0).toString())) {
                        sameYearGameList.put(sameYearGame.getDate().toString(), sameYearGame);
                    }

                    // if : month / sameMonthGameList 에 포함된 SameDateGame 객체인가?
                    if (!sameMonthGameList.containsKey(new Date(date.getYear(), date.getMonth(), 0).toString())) {
                        sameMonthGameList.put(sameMonthGame.getDate().toString(), sameMonthGame);
                    }
                }
            }
        }
//        printLog();
    }

    /**
     * 매개변수로 받은 size 값으로 boolean[] 배열을 생성하고, 모든 값을 false 로 채워 이를 반환하는 메소드
     *
     * @param size
     * @return
     */
    public boolean[] fillFalse(int size) {
        boolean[] values = new boolean[size];
        Arrays.fill(values, false);
        return values;
    }

    /**
     * 매개변수로 받은 ArrayList<BilliardData> 객체의 sort()를 사용하여 billiardData 의 날짜를 비교하여 정렬된 ArrayList<BilliardData> 객체로 반환하는 메소드
     *
     * @param billiardDataArrayList
     */
    public void sort(List<BilliardData> billiardDataArrayList) {

        billiardDataArrayList.sort(new Comparator<BilliardData>() {
            @Override
            public int compare(BilliardData billiardData, BilliardData t1) {
                LocalDate localDate = LocalDate.parse(billiardData.getDate(), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                LocalDate localDate1 = LocalDate.parse(t1.getDate(), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                return localDate.compareTo(localDate1);
            }
        });
    }

    /**
     * 매개변수로 받은 SameDateGame 객체의 record, recordTypeArrayList, totalCost, referenceArrayList 에 확인된 정보를 저장하는 메소드
     *
     * @param sameDateGame
     * @param userData
     * @param billiardData
     * @param index
     */
    private void setInfo(SameDateGame sameDateGame, UserData userData, BilliardData billiardData, int index) {
        // 전적, 승/패
        checkMyWin(sameDateGame, userData, billiardData);
        // 총 비용
        sameDateGame.getTotalCost().plus(billiardData.getCost());
        // billiardData 추가
        sameDateGame.getReferenceList().add(new Reference((int) billiardData.getCount(), index));

    }

    /**
     * 매개변수로 받은 UserData 객체와 BilliardData 객체의 데이터를 이용하여 내가 이긴 경기지인 패배한 경기인지를 판단하고, 이 결과를 매개변수로 받은 SameDataGame 객체의 record와 recordTypeArrayList 변수에 반영하는 메소드
     *
     * @param sameDateGame
     * @param userData
     * @param billiardData
     */
    private void checkMyWin(SameDateGame sameDateGame, UserData userData, BilliardData billiardData) {

        if ((userData.getId() == billiardData.getWinnerId()) &&
                userData.getName().equals(billiardData.getWinnerName())) {
            sameDateGame.getRecord().getWinCounter().plusOne();         // winCounter +1
            sameDateGame.getRecordTypeList().add(Record.Type.WIN);      // record type WIN 추가
        } else {
            sameDateGame.getRecord().getLossCounter().plusOne();        // lossCounter +1
            sameDateGame.getRecordTypeList().add(Record.Type.LOSS);     // record type LOSS 추가
        }
    }


    /**
     * Map<Sting, SameDateGame> 형태의 객체를 ArrayList<SameDateGame> 형태의 객체로 변환하여 반환하는 메소드
     * @param sameDateGameList
     * @return
     */
    public static ArrayList<SameDateGame> convertToArrayList(Map<String, SameDateGame> sameDateGameList) {
        ArrayList<String> keySet = new ArrayList<>(sameDateGameList.keySet());
        ListIterator<String> iterator = keySet.listIterator();

        ArrayList<SameDateGame> arrayList = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            arrayList.add(sameDateGameList.get(key));
        }
        return arrayList;
    }

    public void printLog(LogSwitch CLASS_LOG_SWITCH, String CLASS_NAME) {
        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, ">>>>>>>>>>>>>> allGame <<<<<<<<<<<<<<<<");
        allGame.printLog(CLASS_LOG_SWITCH, CLASS_NAME);
        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, ">>>>>>>>>>>>>> sameYearGame <<<<<<<<<<<<<<<<");
        sameYearGameList.forEach(
                (s, sameDateGame) -> sameDateGame.printLog(CLASS_LOG_SWITCH, CLASS_NAME)
        );
        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, ">>>>>>>>>>>>>> sameMonthGame <<<<<<<<<<<<<<<<");
        sameMonthGameList.forEach(
                (s, sameDateGame) -> sameDateGame.printLog(CLASS_LOG_SWITCH, CLASS_NAME)
        );
        DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, ">>>>>>>>>>>>>> sameDateGame <<<<<<<<<<<<<<<<");
        sameDateGameList.forEach(
                (s, sameDateGame) -> sameDateGame.printLog(CLASS_LOG_SWITCH, CLASS_NAME)
        );
    }


    ////////////////////////////////////
    // 중요한 점
    // 1. HashMap, HashTable : 입력 순서 보장 않음
    // 2. LinkedHashMap : 입력 순서 보장
    // 3. TreeMap : 입려순서 보장 않으나, 정렬된 순서로 저장되어 출력
    // 4. ArrayList<> 정렬 Comparator 의 return - 1: 큼, -1: 작음, 0 : 같음
    // 5. 날짜 비교 : LocalData 의 compareTo() 사용 결과가 Comparator 의 return 값과 일치

}
