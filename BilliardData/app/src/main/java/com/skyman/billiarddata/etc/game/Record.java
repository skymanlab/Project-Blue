package com.skyman.billiarddata.etc.game;

public class Record {

    private Counter winCounter;
    private Counter lossCounter;

    public Record() {
        winCounter = new Counter();
        lossCounter = new Counter();
    }

    public Counter getWinCounter() {
        return winCounter;
    }

    public void setWinCounter(Counter winCounter) {
        this.winCounter = winCounter;
    }

    public Counter getLossCounter() {
        return lossCounter;
    }

    public void setLossCounter(Counter lossCounter) {
        this.lossCounter = lossCounter;
    }

    public int getTotalNumberOfGame() {
        return winCounter.getValue() + lossCounter.getValue();
    }

    public String toString() {
        return getTotalNumberOfGame() + "전 " + winCounter.getValue() + "승 " + lossCounter.getValue() + "패 ";
    }

    public static enum Type {
        WIN, LOSS;

        public static final String WIN_KO = "승";
        public static final String LOSS_KO = "패";

        public static String toString(Type type) {
            switch (type) {
                case WIN:
                    return WIN_KO;
                case LOSS:
                    return LOSS_KO;
                default:
                    return null;
            }
        }
    }

}
