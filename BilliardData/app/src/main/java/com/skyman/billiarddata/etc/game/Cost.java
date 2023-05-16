package com.skyman.billiarddata.etc.game;

import java.text.DecimalFormat;

public class Cost {

    private static final String PATTERN = "###,###";

    private int cost;

    public Cost() {
        cost = 0;
    }

    public Cost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void addCost(int cost) {
        this.cost += cost;
    }

    public String toString() {
        DecimalFormat formatter = new DecimalFormat(PATTERN);
        return formatter.format(cost) + "원";
    }

}
