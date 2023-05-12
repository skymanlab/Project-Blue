package com.skyman.billiarddata.etc.statistics;

import java.util.ArrayList;

public class AllStatistics extends Statistics {

    private ArrayList<Record> recentRecordArrayList;

    public AllStatistics() {
        super();
        recentRecordArrayList = new ArrayList<>();
    }

    public ArrayList<Record> getRecentRecordArrayList() {
        return recentRecordArrayList;
    }
}
