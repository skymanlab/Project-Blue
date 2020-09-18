package com.skyman.billiarddata.database.billiard;

import android.widget.ListView;

import java.text.DecimalFormat;

public class BilliardDataManager {

    /*// value
    private BilliardHelper;
    private ListView targetListView;

    // constructor
    public BilliardDataManager(ListView targetListView) {
        this.targetListView = targetListView;
    }*/

    /* method : 스코어 형태로 문자열 만들기 - #:# */
    public static String setFormatToScore(String score_1, String score_2) {
        StringBuilder scoreData = new StringBuilder();
        scoreData.append(score_1);
        scoreData.append(" : ");
        scoreData.append(score_2);
        return scoreData.toString();
    }

    /* method : 가격 형태로 문자열 만들기 - ###,### 원 */
    public static String setFormatToCost(String cost) {
        DecimalFormat wonFormat = new DecimalFormat("###,###");
        StringBuilder costData = new StringBuilder();
        long longCost = Long.parseLong(cost);


        costData.append(wonFormat.format(longCost));
        costData.append(" 원");
        return costData.toString();
    }

    /* method : 게임 시간 형태로 문자열 만들기 - ## 분 */
    public static String setFormatToPlayTime(String playTime) {
        StringBuilder playTimeData = new StringBuilder();
        playTimeData.append(playTime);
        playTimeData.append(" 분");
        return playTimeData.toString();
    }
}
