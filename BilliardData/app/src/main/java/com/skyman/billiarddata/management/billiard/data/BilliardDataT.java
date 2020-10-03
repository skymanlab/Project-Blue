package com.skyman.billiarddata.management.billiard.data;

import java.io.Serializable;

/**
 * [class] project_blue.db 의 billiard 테이블의 '한 행'을 담기위한 DTO 클래스이다.
 * */
public class BilliardDataT implements Serializable {

    // instance variable : desc
    private long count;                 // 0. count
    private String date;                // 1. date
    private int targetScore;            // 2. target score
    private String speciality;          // 3. speciality
    private int playTime;               // 4. play time
    private String winner;              // 5. winner
    private String score;               // 6. score
    private int cost;                   // 7. cost

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

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
