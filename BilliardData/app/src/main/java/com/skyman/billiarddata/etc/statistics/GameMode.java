package com.skyman.billiarddata.etc.statistics;

public enum GameMode {
    THREE, FOUR, POCKET_BALL;

    public static final String THREE_KO = "3구";
    public static final String FOUR_KO = "4구";
    public static final String POCKET_BALL_KO = "포켓볼";

    static public String toString(GameMode gameMode) {
        switch (gameMode) {
            case THREE:
                return THREE_KO;
            case FOUR:
                return FOUR_KO;
            case POCKET_BALL:
                return POCKET_BALL_KO;
            default:
                return null;
        }
    }
}
