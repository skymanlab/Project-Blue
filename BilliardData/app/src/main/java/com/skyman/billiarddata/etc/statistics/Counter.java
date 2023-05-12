package com.skyman.billiarddata.etc.statistics;

import androidx.annotation.NonNull;

public class Counter {

    private int winCounter;
    private int lossCounter;

    public Counter() {
        winCounter = 0;
        lossCounter = 0;
    }

    public Counter(int winCounter, int lossCounter) {
        this.winCounter = winCounter;
        this.lossCounter = lossCounter;
    }

    public int getWinCounter() {
        return winCounter;
    }

    public int getLossCounter() {
        return lossCounter;
    }

    public void addOneWinCounter() {
        winCounter += 1;
    }
    public void addWinCounter(int winCounter) {
        this.winCounter += winCounter;
    }

    public void addOneLossCounter() {
        lossCounter += 1;
    }
    public void addLossCounter(int lossCounter) {
        this.lossCounter += lossCounter;
    }

    public int getTotalCounter() {
        return winCounter + lossCounter;
    }

    @NonNull
    @Override
    public String toString() {
        return getTotalCounter() + "전" + winCounter + "승 " + lossCounter + "패";
    }
}
