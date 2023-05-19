package com.skyman.billiarddata.etc.game;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public void plus(int cost) {
        this.cost += cost;
    }

    public static Cost totalCost(List<Cost> costList) {
        Cost totalCost = new Cost();
        costList.forEach(
                cost1 -> {
                    totalCost.plus(cost1.getCost());
                }
        );
        return totalCost;
    }

    public static Cost averageCost(List<Cost> costList) {
        int totalCost = 0;
        for (int index = 0; index < costList.size(); index++) {
            totalCost += costList.get(index).getCost();
        }

        return new Cost(totalCost / costList.size());
    }

    public static Cost maxCost(List<Cost> costList) {
        return Collections.max(costList, new Comparator<Cost>() {
            @Override
            public int compare(Cost cost, Cost t1) {
                if (cost.getCost() > t1.getCost())
                    return 1;
                else if (cost.getCost() < t1.getCost())
                    return -1;
                else
                    return 0;
            }
        });
    }

    public static Cost minCost(List<Cost> costList) {
        return Collections.min(costList, ((cost, t1) -> {
            if (cost.getCost() > t1.getCost())
                return 1;
            else if (cost.getCost() < t1.getCost())
                return -1;
            else
                return 0;
        }));
    }

    public String toString() {
        DecimalFormat formatter = new DecimalFormat(PATTERN);
        return formatter.format(cost) + "원";
    }

}
