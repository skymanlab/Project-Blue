package com.skyman.billiarddata.etc.statistics;

import android.util.Log;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.developer.Display;
import com.skyman.billiarddata.etc.database.AppDbManager;
import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.player.database.PlayerDbManager2;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;
import java.util.Arrays;

public class StatisticsAnalysis {


    private static final Display CLASS_LOG_SWITCH = Display.ON;
    private static final String CLASS_NAME = "StatisticsAnalysis";

    private AllStatistics allStatistics;
    private ArrayList<DateStatistics> yearStatsArrayList;
    private ArrayList<DateStatistics> monthStatsArrayList;

    public StatisticsAnalysis(AppDbManager appDbManager) {
        this.allStatistics = new AllStatistics();
        this.yearStatsArrayList = new ArrayList<>();
        this.monthStatsArrayList = new ArrayList<>();
    }

    /////////////////////////////////////////////////////
    // getter ///////////////////////////////////////////
    /////////////////////////////////////////////////////

    public ArrayList<DateStatistics> getYearStatsArrayList() {
        return yearStatsArrayList;
    }

    public ArrayList<DateStatistics> getMonthStatsArrayList() {
        return monthStatsArrayList;
    }

    /////////////////////////////////////////////////////
    // analyze method ///////////////////////////////////
    /////////////////////////////////////////////////////
    public void analyzeAllStats(ArrayList<SameDate2> sameDateArrayList, ArrayList<BilliardData> billiardDataArrayList) {
        if (sameDateArrayList.size() != 0 && billiardDataArrayList.size() != 0) {

            int recentRecordCounter = 5;
            for (int index = sameDateArrayList.size() - 1; index >= 0; index--) {
                addCounter(allStatistics.getCounter(), sameDateArrayList.get(index).getMyGameCounter());
                allStatistics.getTotalCost().addCost(sameDateArrayList.get(index).getTotalCost().getCost());

                if (recentRecordCounter > 0) {
                    recentRecordCounter = setRecord(allStatistics.getRecentRecordArrayList(), sameDateArrayList.get(index).getMyGameRecord(), recentRecordCounter);
                }
            }
        }
    }

    public void analyzeYearStats(ArrayList<SameDate2> sameDateArrayList, ArrayList<BilliardData> billiardDataArrayList) {
        if (sameDateArrayList.size() != 0 && billiardDataArrayList.size() != 0) {
            for (int index = 0; index < sameDateArrayList.size(); index++) {

            }
        }
    }

    public void analyzeMonthTotalStats(ArrayList<SameDate2> sameDateArrayList, ArrayList<BilliardData> billiardDataArrayList) {
        if (sameDateArrayList.size() != 0 && billiardDataArrayList.size() != 0) {

            DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "[analyzeMonthTotalStats() 실행 중]");

            // sameDate 를 검사하였는지 체크할 변수 선언 및 모두 false 로 초기화
            boolean isChecked[] = fillOfBooleanArray(sameDateArrayList.size());

            for (int index = sameDateArrayList.size() - 1; index >= 0; index--) {

                // if 2 : 검사하지 않은 sameDate 객체만
                if (isChecked[index] == false) {
                    isChecked[index] = true;                            // 검사를 진행해고 있으므로 true 변경

                    DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "---- " + index + "번째 ----");

                    // 해당 월에 대한 통계 자룔를 저장하기 위한 객체 선언
                    DateStatistics monthStats = new DateStatistics();

                    // monthStats 의 Date
                    setDateOfMonthStats(monthStats, sameDateArrayList.get(index).getGameDate());
                    // monthStats 의 Counter, Cost 등등
                    setDateStats(monthStats, sameDateArrayList.get(index));

                    for (int nextIndex = index - 1; nextIndex >= 0; nextIndex--) {

                        if (isChecked[nextIndex] == false) {
                            if (equalMonth(sameDateArrayList.get(index).getGameDate(), sameDateArrayList.get(nextIndex).getGameDate())) {

                                isChecked[nextIndex] = true;

                                // monthStats 의 Counter, Cost 등등
                                setDateStats(monthStats, sameDateArrayList.get(nextIndex));

                            }
                        }
                    }
                    // monthStata 를 ArrayList 에 담기
                    monthStatsArrayList.add(monthStats);
                }
            }
            printLog(monthStatsArrayList);
        }
    }

    private boolean[] fillOfBooleanArray(int size) {
        boolean[] array = new boolean[size];
        Arrays.fill(array, false);
        return array;
    }

    private void setDateStats(DateStatistics monthStats, SameDate2 sameDate2) {

        // counter
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "sameDate / Counter : " + sameDate2.getMyGameCounter().toString());
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "monthStats / Counter / 전 : " + monthStats.getCounter().toString());
        addCounter(monthStats.getCounter(), sameDate2.getMyGameCounter());
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, "monthStats / Counter / 후 : " + monthStats.getCounter().toString());

        // cost
        monthStats.getTotalCost().addCost(sameDate2.getTotalCost().getCost());

    }

    private void setDateOfMonthStats(DateStatistics monthStats, Date date) {
        monthStats.getDate().setYear(date.getYear());
        monthStats.getDate().setMonth(date.getMonth());
    }

    private int setRecord(ArrayList<Record> recentRecordList, ArrayList<Record> sameDateRecordList, int counter) {
        for (int index = sameDateRecordList.size() - 1; index >= 0; index--) {
            if (counter > 0) {
                recentRecordList.add(sameDateRecordList.get(index));
                counter--;
            }
        }
        return counter;
    }


    /**
     * 기존 Counter 의 winCounter, lossCounter 에 추가할 Counter 의 winCounter, lossCounter 를 더하기
     *
     * @param baseCounter  기존 Counter
     * @param addedCounter 추가할 Counter
     */
    private void addCounter(Counter baseCounter, Counter addedCounter) {
        baseCounter.addWinCounter(addedCounter.getWinCounter());
        baseCounter.addLossCounter(addedCounter.getLossCounter());
    }

    /**
     * year, month 비교한 결과를 반환한다.
     *
     * @param baseDate     기존 Date
     * @param comparedDate 비교할 Date
     * @return 같은 날짜(year, month 같은) 면 true, 다른 날짜이면 false
     */
    private boolean equalMonth(Date baseDate, Date comparedDate) {

        if (baseDate.getYear() == comparedDate.getYear()) {
            if (baseDate.getMonth() == comparedDate.getMonth()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 같은 년도 인지 비교한 결과를 반환한다.
     *
     * @param baseDate     기존 Date
     * @param comparedDate 비교할 Date
     * @return 같은 년도이면 true, 다른 년도이면 false
     */
    private boolean equalYear(Date baseDate, Date comparedDate) {
        if (baseDate.getYear() == comparedDate.getYear())
            return true;
        else
            return false;
    }


    private void printLog(ArrayList<DateStatistics> monthStatsArrayList) {
        if (DeveloperManager.PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON) {
                Log.d(CLASS_NAME, "[monthStatsArrayList 내용 확인");

                for (int index = 0; index < monthStatsArrayList.size(); index++) {

                    Log.d(CLASS_NAME, "---- " + index + "번째 ----");
                    Log.d(CLASS_NAME, "Date : " + monthStatsArrayList.get(index).getDate().toString());
                    Log.d(CLASS_NAME, "Counter / 전적 : " + monthStatsArrayList.get(index).getCounter().toString());
                    Log.d(CLASS_NAME, "TotalCost : " + monthStatsArrayList.get(index).getTotalCost().toString());

                    StringBuilder recentRecord = new StringBuilder();
                    recentRecord.append("recent Record list : ");

                    Log.d(CLASS_NAME, recentRecord.toString());

                }
            }
    }
}
