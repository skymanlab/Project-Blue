package com.skyman.billiarddata.etc.game;

public class Counter {
    private int value;

    public Counter() {
        value = 0;
    }

    public Counter(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void plusOne() {
        this.value += 1;
    }

    public void plus(int value) {
        this.value += value;
    }
}
