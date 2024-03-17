package com.skyman.billiarddata.etc.game;

public class Record {

    private Counter winCounter;         // 승리 카운터
    private Counter lossCounter;        // 패배 카운터

    public Record() {
        winCounter = new Counter();
        lossCounter = new Counter();
    }

    public Counter getWinCounter() {
        return winCounter;
    }

    public Counter getLossCounter() {
        return lossCounter;
    }

    public Counter totalNumberOfGame() {
        return new Counter(winCounter.getValue() + lossCounter.getValue());
    }

    public void plusByType(Record record) {
        winCounter.plus(record.getWinCounter().getValue());
        lossCounter.plus(record.getLossCounter().getValue());
    }

    public String winningPercentage() {
        return (winCounter.getValue() / totalNumberOfGame().getValue()) + "%";
    }

    public String toString() {
        return totalNumberOfGame().getValue() + "전 " + winCounter.getValue() + "승 " + lossCounter.getValue() + "패 ";
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
