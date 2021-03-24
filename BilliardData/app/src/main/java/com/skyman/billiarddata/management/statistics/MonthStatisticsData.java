package com.skyman.billiarddata.management.statistics;

import com.skyman.billiarddata.management.billiard.data.BilliardData;

import java.util.ArrayList;

public class MonthStatisticsData {

    // instance variable
    private int year;
    private int month;
    private int winCount;
    private int lossCount;
    private ArrayList<BilliardData> billiardDataArrayList;      // 같은 year, month 의 billiardData

    // constructor
    public MonthStatisticsData() {
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
