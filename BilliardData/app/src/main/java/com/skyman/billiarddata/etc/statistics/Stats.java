package com.skyman.billiarddata.etc.statistics;

import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Score;
import com.skyman.billiarddata.etc.game.Time;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    protected Record record;
    protected List<Cost> costList;
    protected List<Time> timeList;
    protected List<Record.Type> recordTypeList;
    protected List<Score> scoreList;

    public Stats() {
        this.record = new Record();
        this.costList = new ArrayList<>();
        this.timeList = new ArrayList<>();
        this.recordTypeList = new ArrayList<>();
        this.scoreList = new ArrayList<>();
    }

    public Record getRecord() {
        return record;
    }

    public List<Cost> getCostList() {
        return costList;
    }

    public List<Time> getTimeList() {
        return timeList;
    }

    public List<Record.Type> getRecordTypeList() {
        return recordTypeList;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void add(Cost cost, Time time, Record.Type recordType, Score score){
        this.costList.add(cost);
        this.timeList.add(time);
        this.recordTypeList.add(recordType);
        this.scoreList.add(score);
    }

    public void printLog() {

    }
}
