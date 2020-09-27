package com.skyman.billiarddata.management.user.data;

import java.io.Serializable;

public class UserData implements Serializable {

    // value : desc
    private long id;                         // 0. id
    private String  name;                   // 1. name
    private int targetScore;                // 2. target score
    private String speciality;              // 3. speciality
    private int gameRecordWin;              // 4. game record win
    private int gameRecordLoss;             // 5. game record loss
    private long recentGamePlayerId;         // 6. recent game player id
    private String recentPlayDate;          // 7. recent play date
    private int totalPlayTime;              // 8. total play time
    private int totalCost;                  // 9. total cost

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

    public long getRecentGamePlayerId() {
        return recentGamePlayerId;
    }

    public void setRecentGamePlayerId(long recentGamePlayerId) {
        this.recentGamePlayerId = recentGamePlayerId;
    }

    public String getRecentPlayDate() {
        return recentPlayDate;
    }

    public void setRecentPlayDate(String recentPlayDate) {
        this.recentPlayDate = recentPlayDate;
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
