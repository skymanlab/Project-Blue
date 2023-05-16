package com.skyman.billiarddata.etc.game;

public class Reference {

    private int count;  // billiardData 의 count
    private int index;  // billiardDataArrayList 에서의 위치

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
