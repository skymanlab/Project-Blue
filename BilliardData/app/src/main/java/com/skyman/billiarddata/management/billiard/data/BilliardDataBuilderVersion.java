package com.skyman.billiarddata.management.billiard.data;

public class BilliardDataBuilderVersion {

    // variable : table desc
    private long id;            // 0. id
    private String date;        // 1. date
    private int targetScore;    // 2. target score
    private String speciality;  // 3. speciality
    private int playTime;       // 4. play time
    private String winner;      // 5. winner
    private String score;       // 6. score
    private int cost;           // 7. cost

    // constructor
    public BilliardDataBuilderVersion(DataBuilder dataBuilder){
        this.id = dataBuilder.id;
        this.date = dataBuilder.date;
        this.targetScore = dataBuilder.targetScore;
        this.speciality = dataBuilder.speciality;
        this.playTime = dataBuilder.playTime;
        this.winner = dataBuilder.winner;
        this.score = dataBuilder.score;
        this.cost = dataBuilder.cost;
    }

    // method : getter
    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public String getSpeciality() {
        return speciality;
    }

    public int getPlayTime() {
        return playTime;
    }

    public String getWinner() {
        return winner;
    }

    public String getScore() {
        return score;
    }

    public int getCost() {
        return cost;
    }


    // Builder class
    public static class DataBuilder{

        private long id;            // 0. id
        private String date;        // 1. date
        private int targetScore;    // 2. target score
        private String speciality;  // 3. speciality
        private int playTime;       // 4. play time
        private String winner;      // 5. winner
        private String score;       // 6. score
        private int cost;           // 7. cost

        public DataBuilder(){

        }

        public DataBuilder setId(long id){
            this.id = id;
            return this;
        }

        public DataBuilder setDate(String date){
            this.date = date;
            return this;
        }

        public DataBuilder setTargetScore(int targetScore){
            this.targetScore = targetScore;
            return this;
        }

        public DataBuilder setSpeciality(String speciality){
            this.speciality = speciality;
            return this;
        }
        public DataBuilder setPlayTime(int playTime){
            this.playTime = playTime;
            return this;
        }
        public DataBuilder setWinner(String winner) {
            this.winner = winner;
            return this;
        }

        public DataBuilder setScore(String score){
            this.score = score;
            return this;
        }

        public DataBuilder setCost(int cost) {
            this.cost = cost;
            return this;
        }

        public BilliardDataBuilderVersion builder(){
            return new BilliardDataBuilderVersion(this);
        }
    }
}
