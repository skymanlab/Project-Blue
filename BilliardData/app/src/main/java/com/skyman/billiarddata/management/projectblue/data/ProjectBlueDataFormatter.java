package com.skyman.billiarddata.management.projectblue.data;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectBlueDataFormatter {

    // class constant
    public static final String DATE_FORMAT = "yyyy년 MM월 dd일";

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

    /* method : formatter, 게임 시간 형태로 문자열 만들기 - ## 분 */
    public static String setFormatToPlayTime(int playTime) {
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

    /* method : formatter, 가격 형태로 문자열 만들기 - ###,### 원 */
    public static String setFormatToCost(int cost) {
        DecimalFormat wonFormat = new DecimalFormat("###,###");
        StringBuilder costData = new StringBuilder();

        costData.append(wonFormat.format(cost));
        costData.append(" 원");
        return costData.toString();
    }

    /* method : formatter, DATE_FORMAT 형식으로 Date 만들기 */
    public static String setFormatToDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return  formatter.format(date);
    }
}
