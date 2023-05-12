package com.skyman.billiarddata.etc.statistics;

public class Reference {

    private int count;                  // billiardData의 count 맴버변수(billiard 테이블의 count 항목)
    private int index;                  // billiardDataArrayList의 index(같은 날짜의 billiardData가 billiardDataArrayList에 있는 위치)

    public Reference(int count, int index) {
        this.count = count;
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public int getIndex() {
        return index;
    }
}
