package com.skyman.billiarddata.listview;

public class DataListItem {
    private long id;
    private String date;
    private String victoree;
    private String score;
    private String cost;
    private String paly_time;

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

    public String getPaly_time() {
        return paly_time;
    }

    public void setPaly_time(String paly_time) {
        this.paly_time = paly_time;
    }
}
