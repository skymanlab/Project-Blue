package com.skyman.billiarddata.etc.statistics;

import androidx.annotation.NonNull;

public class Cost {
    private int cost;

    public Cost() {
        cost = 0;
    }

    public int getCost() {
        return cost;
    }

    public void addCost(int cost) {
        this.cost += cost;
    }

    @NonNull
    @Override
    public String toString() {
        return cost + "원";
    }
}
