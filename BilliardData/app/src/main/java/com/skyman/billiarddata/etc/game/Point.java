package com.skyman.billiarddata.etc.game;

import androidx.annotation.NonNull;

public class Point {
    private String playerName;      // 참가자 이름
    private int targetPoint;        // 목표 점수
    private int earnedPoints;       // 획득 점수

    public Point(String playerName, int targetPoint, int earnedPoints) {
        this.playerName = playerName;
        this.targetPoint = targetPoint;
        this.earnedPoints = earnedPoints;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(int targetPoint) {
        this.targetPoint = targetPoint;
    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(int earnedPoint) {
        this.earnedPoints = earnedPoint;
    }

    @NonNull
    @Override
    public String toString() {
        return "참가자 이름 : " +playerName +", 목표 점수 : " + targetPoint + ", 획득 점수 : " + earnedPoints;
    }

}
