package com.skyman.billiarddata.etc.game;

import androidx.annotation.NonNull;

public class Counter {
    private int value;          // 카운터 하는 값

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

    @NonNull
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
