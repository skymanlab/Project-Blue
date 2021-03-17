package com.skyman.billiarddata.management.billiard.data;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * [class] project_blue.db 의 billiard 테이블의 '한 행(레코드)'을 담기위한 DTO 클래스이다.
 */
public class BilliardData implements Serializable {

    // 0. count
    // 1. date
    // 2. game mode
    // 3. player count
    // 4. winner id
    // 5. winner name
    // 6. play time
    // 7. score
    // 8. cost

    // constructor
    public static final String CLASS_NAME = "billiardData";
    public static final String COUNT = "count";
    public static final String DATE = "date";
    public static final String GAME_MODE = "gameMode";
    public static final String PLAYER_COUNT = "playerCount";
    public static final String WINNER_ID = "winnerId";
    public static final String WINNER_NAME = "winnerName";
    public static final String PLAY_TIME = "playTime";
    public static final String SCORE = "score";
    public static final String COST = "cost";

    // instance variable : desc
    private long count;                 // 0. count
    private String date;                // 1. date
    private String gameMode;            // 2. game mode
    private int playerCount;            // 3. player count
    private long winnerId;              // 4. winner id
    private String winnerName;          // 5. winner name
    private int playTime;               // 6. play time
    private String score;               // 7. score
    private int cost;                   // 8. cost

    // method : getter, setter
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(long winnerId) {
        this.winnerId = winnerId;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
