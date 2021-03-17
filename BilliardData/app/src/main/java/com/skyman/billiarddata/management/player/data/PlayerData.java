package com.skyman.billiarddata.management.player.data;

import java.io.Serializable;

public class PlayerData implements Serializable {

    // 0. count
    // 1. billiard count
    // 2. player id
    // 3. player name
    // 4. target score
    // 5. score

    // constructor
    public static final String CLASS_NAME = "playerData";
    public static final String COUNT = "count";
    public static final String BILLIARD_COUNT= "billiardCount";
    public static final String PLAYER_ID= "playerId";
    public static final String PLAYER_NAME = "playerName";
    public static final String TARGET_SCORE = "targetScore";
    public static final String SCORE = "score";

    // instance variable : desc
    private long count;                 // 0. count
    private long billiardCount;         // 1. billiard count
    private long playerId;              // 2. player id
    private String playerName;          // 3. player name
    private int targetScore;            // 4. target score
    private int score;                  // 5. score

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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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
