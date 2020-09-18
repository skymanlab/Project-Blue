package com.skyman.billiarddata.database.billiard;

import java.text.DecimalFormat;

public class BilliardDataManager {

    /* 스코어 형태로 문자열 만들기 - #:# */
    public static String setFormatToScore(String score_1, String score_2) {
        StringBuilder scoreData = new StringBuilder();
        scoreData.append(score_1);
        scoreData.append(" : ");
        scoreData.append(score_2);
        return scoreData.toString();
    }

    /* 가격 형태로 문자열 만들기 - ###,### 원 */
    public static String setFormatToCost(String cost) {
        DecimalFormat wonFormat = new DecimalFormat("###,###");
        StringBuilder costData = new StringBuilder();
        long longCost = Long.parseLong(cost);


        costData.append(wonFormat.format(longCost));
        costData.append(" 원");
        return costData.toString();
    }

    /* 게임 시간 형태로 문자열 만들기 - ## 분 */
    public static String setFormatToPlayTime(String playTime) {
        StringBuilder playTimeData = new StringBuilder();
        playTimeData.append(playTime);
        playTimeData.append(" 분");
        return playTimeData.toString();
    }
}
