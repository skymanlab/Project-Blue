package com.skyman.billiarddata.management.user.data;

import java.io.Serializable;

public class UserData implements Serializable {

    // 0. id (primary key/autoincrement)
    // 1. name
    // 2. target score
    // 3. speciality
    // 4. game record win
    // 5. game record loss
    // 6. recent game billiard count
    // 7. total play time
    // 8. total cost

    // constructor
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TARGET_SCORE = "targetScore";
    public static final String SPECIALITY = "speciality";
    public static final String GAME_RECORD_WIN = "gameRecordWin";
    public static final String GAME_RECORD_LOSS = "gameRecordLoss";
    public static final String RECENT_GAME_BILLIARD_COUNT = "recentGameBilliardCount";
    public static final String TOTAL_PLAY_TIME = "totalPlayTime";
    public static final String TOTAL_COST = "totalCost";

    // variable : desc
    private long id;                         // 0. id
    private String name;                   // 1. name
    private int targetScore;                // 2. target score
    private String speciality;              // 3. speciality
    private int gameRecordWin;              // 4. game record win
    private int gameRecordLoss;             // 5. game record loss
    private long recentGameBilliardCount;   // 6. recent game billiard count
    private int totalPlayTime;              // 7. total play time
    private int totalCost;                  // 8. total cost

    // method : getter, setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getGameRecordWin() {
        return gameRecordWin;
    }

    public void setGameRecordWin(int gameRecordWin) {
        this.gameRecordWin = gameRecordWin;
    }

    public int getGameRecordLoss() {
        return gameRecordLoss;
    }

    public void setGameRecordLoss(int gameRecordLoss) {
        this.gameRecordLoss = gameRecordLoss;
    }

    public long getRecentGameBilliardCount() {
        return recentGameBilliardCount;
    }

    public void setRecentGameBilliardCount(long recentGameBilliardCount) {
        this.recentGameBilliardCount = recentGameBilliardCount;
    }

    public int getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setTotalPlayTime(int totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
