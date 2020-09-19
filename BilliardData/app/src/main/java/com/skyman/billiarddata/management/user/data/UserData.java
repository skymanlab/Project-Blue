package com.skyman.billiarddata.management.user.data;

public class UserData {

    // value : desc
    private String userName;                // 0.
    private int targetScore;                // 1.
    private String speciality;              // 2.
    private int gameRecordWin;              // 3.
    private int gameRecordLoss;             // 4.
    private int totalPlayTime;              // 5.
    private int totalCost;                  // 6.

    // value : getter, setter


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
