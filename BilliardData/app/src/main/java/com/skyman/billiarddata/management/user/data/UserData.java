package com.skyman.billiarddata.management.user.data;

import java.io.Serializable;

public class UserData implements Serializable {

    // variable : desc
    private long id;                         // 0. id
    private String  name;                   // 1. name
    private int targetScore;                // 2. target score
    private String speciality;              // 3. speciality
    private int gameRecordWin;              // 4. game record win
    private int gameRecordLoss;             // 5. game record loss
    private long recentGameBilliardCount;   // 6. recent game player id
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
