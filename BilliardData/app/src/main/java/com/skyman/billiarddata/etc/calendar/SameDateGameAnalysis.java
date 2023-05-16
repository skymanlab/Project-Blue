package com.skyman.billiarddata.etc.calendar;

import android.util.Log;

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
import java.util.Map;

public class SameDateGameAnalysis {

    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.ON;
    private static final String CLASS_NAME = "SameDateGameAnalysis";

    Map<String, SameDateGame> sameYearGameList;
    Map<String, SameDateGame> sameMonthGameList;
    Map<String, SameDateGame> sameDateGameList;                         // key : 날짜(날짜순으로 정렬하기 위해서 key로 사용)

    ArrayList<BilliardData> sortedBilliardDataArrayList;

    public SameDateGameAnalysis() {
        sameYearGameList = new LinkedHashMap<>();
        sameMonthGameList = new LinkedHashMap<>();
        sameDateGameList = new LinkedHashMap<>();
        sortedBilliardDataArrayList = new ArrayList<>();
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

    public ArrayList<BilliardData> getSortedBilliardDataArrayList() {
        return sortedBilliardDataArrayList;
    }

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
                            if (sameDateGame.getDate().equal(billiardDataArrayList.get(nextIndex).getDate())) {         // --> 같은 날짜 일때
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

    public void analyze(UserData userData, ArrayList<BilliardData> billiardDataArrayList) {

        if (!billiardDataArrayList.isEmpty()) {

            this.sortedBilliardDataArrayList.addAll(billiardDataArrayList);
            sort(this.sortedBilliardDataArrayList);
//            DeveloperLog.printLogBilliardData(CLASS_LOG_SWITCH, CLASS_NAME, sortedBilliardDataArrayList);

            // billiardData 와 1:1 대응되어서 해당 데이터가 분석된 것인지 판단한다!
            boolean[] isAnalyzed = fillFalse(this.sortedBilliardDataArrayList.size());

            for (int index = 0; index < this.sortedBilliardDataArrayList.size(); index++) {

                // if : index 번째의 billiardData 가 분석된 것인가?
                if (!isAnalyzed[index]) {       // --> 분석되지 않았을 때(isAnalyzed=true 이면)

                    // 분석된(할) 데이터이다!
                    isAnalyzed[index] = true;

                    Date date = Date.createDateByParsing(this.sortedBilliardDataArrayList.get(index).getDate());

                    // date
                    SameDateGame sameDateGame = new SameDateGame();
                    sameDateGame.getDate().setDateByParsing(this.sortedBilliardDataArrayList.get(index).getDate());
                    setInfo(sameDateGame, userData, this.sortedBilliardDataArrayList.get(index), index);

                    // Year
                    SameDateGame sameYearGame = new SameDateGame();
                    sameYearGame.getDate().setYear(date.getYear());
                    setInfo(sameYearGame, userData, this.sortedBilliardDataArrayList.get(index), index);

                    // Month
                    SameDateGame sameMonthGame = new SameDateGame();
                    sameMonthGame.getDate().setYear(date.getYear());
                    sameMonthGame.getDate().setMonth(date.getMonth());
                    setInfo(sameMonthGame, userData, this.sortedBilliardDataArrayList.get(index), index);


                    for (int nextIndex = index + 1; nextIndex < this.sortedBilliardDataArrayList.size(); nextIndex++) {

                        // if : nextIndex 번째의 billiardData 가 분석된 것인가?
                        if (!isAnalyzed[nextIndex]) {       // --> 분석되지 않았을 때만(isAnalyzed=true 이면)

                            Date comparedDate = Date.createDateByParsing(this.sortedBilliardDataArrayList.get(nextIndex).getDate());

                            // if : year 가 같은가?
                            if (sameDateGame.getDate().equalYear(comparedDate.getYear())) {

                                setInfo(sameYearGame, userData, this.sortedBilliardDataArrayList.get(nextIndex), nextIndex);

                                // if : month 가 같은가?
                                if (sameDateGame.getDate().equalMonth(comparedDate.getMonth())) {

                                    setInfo(sameMonthGame, userData, this.sortedBilliardDataArrayList.get(nextIndex), nextIndex);

                                    // if : dayOfMonth 가 같은가?
                                    if (sameDateGame.getDate().equalDayOfMonth(comparedDate.getDayOfMonth())) {

                                        // year, month, dayOfMonth 가 모두 같을 때를 기준으로 isAnalyzed 를 true 로 변경
                                        isAnalyzed[nextIndex] = true;
                                        setInfo(sameDateGame, userData, this.sortedBilliardDataArrayList.get(nextIndex), nextIndex);

                                    }
                                }
                            }
                        }
                    }

                    // date
                    // 같은 날짜의 billiardData 를 분석하였을 때 put()
//                    printLog(sameDateGame);
                    sameDateGameList.put(sameDateGame.getDate().toString(), sameDateGame);

                    // if : year / sameYearGameList 에 포함된 SameDateGame 객체인가?
                    if (!sameYearGameList.containsKey(new Date(date.getYear(), 0, 0).toString())) {
//                        printLog(sameYearGame);
                        sameYearGameList.put(sameYearGame.getDate().toString(), sameYearGame);
                    }

                    // if : month / sameMonthGameList 에 포함된 SameDateGame 객체인가?
                    if (!sameMonthGameList.containsKey(new Date(date.getYear(), date.getMonth(), 0).toString())) {
//                        printLog(sameMonthGame);
                        sameMonthGameList.put(sameMonthGame.getDate().toString(), sameMonthGame);
                    }
                }
            }
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "============== year ==============");
            printLog(sameYearGameList);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "============== month ==============");
            printLog(sameMonthGameList);
            DeveloperLog.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "============== date ==============");
            printLog(sameDateGameList);
        }
    }

    public boolean[] fillFalse(int size) {
        boolean[] values = new boolean[size];
        Arrays.fill(values, false);
        return values;
    }

    public void sort(ArrayList<BilliardData> billiardDataArrayList) {

        billiardDataArrayList.sort(new Comparator<BilliardData>() {
            @Override
            public int compare(BilliardData billiardData, BilliardData t1) {
                LocalDate localDate = LocalDate.parse(billiardData.getDate(), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                LocalDate localDate1 = LocalDate.parse(t1.getDate(), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                return localDate.compareTo(localDate1);
            }
        });
    }


    private void setInfo(SameDateGame sameDateGame, UserData userData, BilliardData billiardData, int index) {
        // 전적, 승/패
        checkMyWin(sameDateGame, userData, billiardData);
        // 총 비용
        sameDateGame.getTotalCost().addCost(billiardData.getCost());
        // billiardData 추가
        sameDateGame.getReferenceArrayList().add(new Reference((int) billiardData.getCount(), index));

    }

    private void checkMyWin(SameDateGame sameDateGame, UserData userData, BilliardData billiardData) {

        if ((userData.getId() == billiardData.getWinnerId()) &&
                userData.getName().equals(billiardData.getWinnerName())) {
            sameDateGame.getRecord().getWinCounter().plusOne();                  // winCounter +1
            sameDateGame.getRecordTypeArrayList().add(Record.Type.WIN);         // record type WIN 추가
        } else {
            sameDateGame.getRecord().getLossCounter().plusOne();                 // lossCounter +1
            sameDateGame.getRecordTypeArrayList().add(Record.Type.LOSS);        // record type LOSS 추가
        }
    }


    public void printLog(Map<String, SameDateGame> sameDateGameList) {

        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {

                Log.d(CLASS_NAME, "[HashMap<String, SameDateGame> 내용 확인]");

                sameDateGameList.forEach(
                        (s, sameDateGame) -> {
                            Log.d(CLASS_NAME, "---- " + s + " -----");
                            Log.d(CLASS_NAME, "Date / year : " + sameDateGame.getDate().getYear());
                            Log.d(CLASS_NAME, "Date / month : " + sameDateGame.getDate().getMonth());
                            Log.d(CLASS_NAME, "Date / day : " + sameDateGame.getDate().getDayOfMonth());
                            Log.d(CLASS_NAME, "Record / winCounter : " + sameDateGame.getRecord().getWinCounter().getValue());
                            Log.d(CLASS_NAME, "Record / lossCounter : " + sameDateGame.getRecord().getLossCounter().getValue());
                            Log.d(CLASS_NAME, "Record / totalNumberOfGame : " + sameDateGame.getRecord().getTotalNumberOfGame());
                            Log.d(CLASS_NAME, "Cost / cost  : " + sameDateGame.getTotalCost().getCost());

                            StringBuilder recordType = new StringBuilder();
                            recordType.append("Record.Type : ");
                            StringBuilder countOfBilliardData = new StringBuilder();
                            countOfBilliardData.append("BilliardData / counts : ");
                            StringBuilder indexOfArrayList = new StringBuilder();
                            indexOfArrayList.append("ArrayList<BilliardData> / index : ");

                            sameDateGame.getRecordTypeArrayList().forEach(
                                    (r) -> {
                                        recordType.append("[" + r + "]");
                                    }
                            );

                            sameDateGame.getReferenceArrayList().forEach(
                                    (r) -> {
                                        countOfBilliardData.append("[" + r.getCount() + "]");
                                        indexOfArrayList.append("[" + r.getIndex() + "]");
                                    }
                            );
                            Log.d(CLASS_NAME, recordType.toString());
                            Log.d(CLASS_NAME, countOfBilliardData.toString());
                            Log.d(CLASS_NAME, indexOfArrayList.toString());
                        }
                );

            }

    } //

    public void printLog(SameDateGame sameDateGame) {

        if (DeveloperLog.PROJECT_LOG_SWITCH.equals(LogSwitch.ON))
            if (CLASS_LOG_SWITCH.equals(LogSwitch.ON)) {

                Log.d(CLASS_NAME, "[sameDateGame 내용 확인]");
                Log.d(CLASS_NAME, "Date / year : " + sameDateGame.getDate().getYear());
                Log.d(CLASS_NAME, "Date / month : " + sameDateGame.getDate().getMonth());
                Log.d(CLASS_NAME, "Date / day : " + sameDateGame.getDate().getDayOfMonth());
                Log.d(CLASS_NAME, "Record / winCounter : " + sameDateGame.getRecord().getWinCounter().getValue());
                Log.d(CLASS_NAME, "Record / lossCounter : " + sameDateGame.getRecord().getLossCounter().getValue());
                Log.d(CLASS_NAME, "Record / totalNumberOfGame : " + sameDateGame.getRecord().getTotalNumberOfGame());
                Log.d(CLASS_NAME, "Cost / cost  : " + sameDateGame.getTotalCost().getCost());

                StringBuilder recordType = new StringBuilder();
                recordType.append("Record.Type : ");
                StringBuilder countOfBilliardData = new StringBuilder();
                countOfBilliardData.append("BilliardData / counts : ");
                StringBuilder indexOfArrayList = new StringBuilder();
                indexOfArrayList.append("ArrayList<BilliardData> / index : ");

                sameDateGame.getRecordTypeArrayList().forEach(
                        (r) -> {
                            recordType.append("[" + r + "]");
                        }
                );

                sameDateGame.getReferenceArrayList().forEach(
                        (r) -> {
                            countOfBilliardData.append("[" + r.getCount() + "]");
                            indexOfArrayList.append("[" + r.getIndex() + "]");
                        }
                );
                Log.d(CLASS_NAME, recordType.toString());
                Log.d(CLASS_NAME, countOfBilliardData.toString());
                Log.d(CLASS_NAME, indexOfArrayList.toString());
            }
    }


    ////////////////////////////////////
    // 중요한 점
    // 1. HashMap, HashTable : 입력 순서 보장 않음
    // 2. LinkedHashMap : 입력 순서 보장
    // 3. TreeMap : 입려순서 보장 않으나, 정렬된 순서로 저장되어 출력
    // 4. ArrayList<> 정렬 Comparator 의 return - 1: 큼, -1: 작음, 0 : 같음
    // 5. 날짜 비교 : LocalData 의 compareTo() 사용 결과가 Comparator 의 return 값과 일치

}
