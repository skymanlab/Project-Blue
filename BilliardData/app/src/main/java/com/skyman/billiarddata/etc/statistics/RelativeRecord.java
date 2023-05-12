package com.skyman.billiarddata.etc.statistics;

/**
 * 상대 전적을 (상대 이름, 전적, 비용)을 저장
 */
public class RelativeRecord {

    private String name;           // 상대방 이름
    private Counter counter;        // 전적을 표시하기 위한 win, loss 저장하는 Counter 객체
    private Cost totalCost;    // 상대방과 경기한 비용을 저장하기 위한 객체

    public RelativeRecord() {
        name = null;
        counter = new Counter();
        totalCost = new Cost();
    }

    public RelativeRecord(String name, Counter counter, Cost totalCost) {
        this.name = name;
        this.counter = counter;
        this.totalCost = totalCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Counter getCounter() {
        return counter;
    }

    public Cost getTotalCost() {
        return totalCost;
    }

}
