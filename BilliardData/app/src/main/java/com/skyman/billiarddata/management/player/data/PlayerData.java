package com.skyman.billiarddata.management.player.data;

public class PlayerData {

    // instance variable : desc
    private long count;                 // 0. count
    private long billiardCount;         // 1. billiard count
    private long playerId;              // 2. playerId
    private int targetScore;            // 3. target score
    private int score;                  // 4. score

    // method : getter. setter

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getBilliardCount() {
        return billiardCount;
    }

    public void setBilliardCount(long billiardCount) {
        this.billiardCount = billiardCount;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
