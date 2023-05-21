package com.skyman.billiarddata.etc.statistics;

import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.etc.game.Cost;
import com.skyman.billiarddata.etc.game.Record;
import com.skyman.billiarddata.etc.game.Score;
import com.skyman.billiarddata.etc.game.Time;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    protected Record record;                        // 전적
    protected List<Cost> costList;                  // 비용 리스트
    protected List<Time> timeList;                  // 시간 리스트
    protected List<Record.Type> recordTypeList;     // 승패 유형 리스트
    protected List<Score> scoreList;                // 스코어 리스트

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

    /**
     * 기존 리스트 객체에 매개변수로 받은 객체를 추가하는 메소드
     *
     * @param cost       비용
     * @param time       시간
     * @param recordType 승패 유형
     * @param score      스코어
     */
    public void add(Cost cost, Time time, Record.Type recordType, Score score) {
        this.costList.add(cost);
        this.timeList.add(time);
        this.recordTypeList.add(recordType);
        this.scoreList.add(score);
    }

    public void printLog(LogSwitch CLASS_LOG_SWITCH, String CLASS_NAME) {

    }
}
