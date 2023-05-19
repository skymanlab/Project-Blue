package com.skyman.billiarddata.etc.game;

public class OneGameInfo {
    protected int numberOfGame;
    protected Cost cost;
    protected Time time;
    protected Record.Type recordType;
    protected Score score;

    public OneGameInfo() {
        this.cost = new Cost();
        this.time = new Time();
        this.recordType = null;
        this.score = new Score();
    }

    public Cost getCost() {
        return cost;
    }

    public Time getTime() {
        return time;
    }

    public Record.Type getRecordType() {
        return recordType;
    }

    public Score getScore() {
        return score;
    }

    public void update(Cost cost, Time time, Record.Type type, Score score){
        this.cost.setCost(cost.getCost());
        this.time.setMinute(time.getMinute());
        this.recordType = type;
        this.score.getPointList().addAll(score.getPointList());
    }

}
