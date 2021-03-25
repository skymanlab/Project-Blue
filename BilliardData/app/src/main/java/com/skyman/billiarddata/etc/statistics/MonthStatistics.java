package com.skyman.billiarddata.etc.statistics;

import com.skyman.billiarddata.table.billiard.data.BilliardData;

import java.util.ArrayList;

/**
 * 월별 통계자료에 대한 데이터가 저장되는 DTO 클래스이다.
 */
public class MonthStatistics {

    // instance variable
    private int year;
    private int month;
    private int winCount;
    private int lossCount;
    private ArrayList<BilliardData> billiardDataArrayList;      // 같은 year, month 의 billiardData

    // constructor
    public MonthStatistics() {
        billiardDataArrayList = new ArrayList<>();
    }

    // getter, setter
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLossCount() {
        return lossCount;
    }

    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }

    public ArrayList<BilliardData> getBilliardDataArrayList() {
        return billiardDataArrayList;
    }

    public void setBilliardDataArrayList(ArrayList<BilliardData> billiardDataArrayList) {
        this.billiardDataArrayList.addAll(billiardDataArrayList);
    }
}
