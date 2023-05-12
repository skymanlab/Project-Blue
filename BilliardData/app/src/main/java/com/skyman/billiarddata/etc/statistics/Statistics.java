package com.skyman.billiarddata.etc.statistics;

import java.util.ArrayList;

public class Statistics {

    private Counter counter;                                        // 승패
    private ArrayList<RelativeRecord> relativeRecordList;           // 상대 전적을 표시
    private Cost totalCost;                                    // 총 비용에 대한

    public Statistics() {
        counter = new Counter();
        totalCost = new Cost();
        relativeRecordList = new ArrayList<>();
    }

    public Counter getCounter() {
        return counter;
    }

    public ArrayList<RelativeRecord> getRelativeRecordList() {
        return relativeRecordList;
    }

    public Cost getTotalCost() {
        return totalCost;
    }
}
