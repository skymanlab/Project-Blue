package com.skyman.billiarddata.management.billiard.data;

import java.io.Serializable;

public class BilliardData implements Serializable {

    // value : desc
    private long id;                    // 0. id
    private String date;                // 1. date
    private String targetScore;         // 2. target score
    private String speciality;          // 3. speciality
    private String playTime;            // 4. play time
    private String winner;              // 5. winner
    private String score;               // 6. score
    private String cost;                // 7. cost

    // method : getter, setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(String targetScore) {
        this.targetScore = targetScore;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
