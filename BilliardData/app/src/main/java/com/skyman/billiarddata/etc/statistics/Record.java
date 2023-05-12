package com.skyman.billiarddata.etc.statistics;

public enum Record {

    WIN, LOSS;
    public static final String WIN_KO = "승";
    public static final String LOSS_KO = "패";

    static public String toString(Record record) {
        switch (record) {
            case WIN:
                return WIN_KO;
            case LOSS:
                return LOSS_KO;
            default:
                return null;
        }
    }
}
