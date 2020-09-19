package com.skyman.billiarddata.listview.billiard;

public class BilliardDataItem {
    private long id;
    private String date;
    private String target_score;
    private String speciality;
    private String paly_time;
    private String victoree;
    private String score;
    private String cost;

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

    public String getTarget_score() {
        return target_score;
    }

    public void setTarget_score(String target_score) {
        this.target_score = target_score;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPaly_time() {
        return paly_time;
    }

    public void setPaly_time(String paly_time) {
        this.paly_time = paly_time;
    }

    public String getVictoree() {
        return victoree;
    }

    public void setVictoree(String victoree) {
        this.victoree = victoree;
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
