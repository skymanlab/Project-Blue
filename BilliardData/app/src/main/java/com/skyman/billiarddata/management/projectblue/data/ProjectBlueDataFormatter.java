package com.skyman.billiarddata.management.projectblue.data;

import java.text.DecimalFormat;

public class ProjectBlueDataFormatter {

    /* method : formatter, 스코어 형태로 문자열 만들기 - #:# */
    public static String setFormatToScore(String score_1, String score_2) {
        StringBuilder scoreData = new StringBuilder();
        scoreData.append(score_1);
        scoreData.append(" : ");
        scoreData.append(score_2);
        return scoreData.toString();
    }

    /* method : gameRecordWin 과 gameRecordLoss 를 가지고 'x전 x승 x패' 형태로 변환 */
    public static String setFormatToGameRecord(int gameRecordWin, int gameRecordLoss){
        StringBuilder temp = new StringBuilder();
        temp.append(gameRecordWin+gameRecordLoss);
        temp.append("전 ");
        temp.append(gameRecordWin);
        temp.append("승 ");
        temp.append(gameRecordLoss);
        temp.append("패");
        return temp.toString();
    }

    /* method : formatter, 게임 시간 형태로 문자열 만들기 - ## 분 */
    public static String setFormatToPlayTime(String playTime) {
        StringBuilder playTimeData = new StringBuilder();
        playTimeData.append(playTime);
        playTimeData.append(" 분");
        return playTimeData.toString();
    }

    /* method : formatter, 가격 형태로 문자열 만들기 - ###,### 원 */
    public static String setFormatToCost(String cost) {
        DecimalFormat wonFormat = new DecimalFormat("###,###");
        StringBuilder costData = new StringBuilder();
        long longCost = Long.parseLong(cost);

        costData.append(wonFormat.format(longCost));
        costData.append(" 원");
        return costData.toString();
    }
}
