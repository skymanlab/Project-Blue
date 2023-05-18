package com.skyman.billiarddata.etc.game;

public enum GameMode {
    THREE, FOUR, POCKET_BALL;

    public static final String THREE_KO = "3구";
    public static final String FOUR_KO = "4구";
    public static final String POCKET_BALL_KO = "포켓볼";

    public static GameMode of(String gameMode) {
        switch (gameMode) {
            case THREE_KO:
                return GameMode.THREE;
            case FOUR_KO:
                return GameMode.FOUR;
            case POCKET_BALL_KO:
                return GameMode.POCKET_BALL;
            default:
                return null;
        }
    }

    public String toString(GameMode gameMode) {
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
