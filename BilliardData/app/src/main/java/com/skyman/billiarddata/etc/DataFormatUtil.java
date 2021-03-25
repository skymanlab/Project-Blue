package com.skyman.billiarddata.etc;

import com.skyman.billiarddata.developer.DeveloperManager;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class DataFormatUtil {

    // constant
    public static final String CLASS_NAME = DataFormatUtil.class.getSimpleName();

    // constant
    public static final String DATE_FORMAT = "yyyy년 MM월 dd일";
    public static final String DATE_DASH_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_2 = "{0}년 {1}월 {2}일";


    // ===================================================== date =====================================================

    /**
     * [method] [Date] date 를 받아서 날짜 형태로 문자열 만들기
     * [format] ####년 ##월 ##일
     *
     * @param date 날짜
     * @return '####년 ##월 ##일' 형태로 반환된 문자열
     */
    public static String formatOfDashDate(Date date) {
        final String METHOD_NAME = "[formatOfDashDate] ";

        // [lv/C]SimpleDateFormat : 특정 날짜 형태로 만들 문자열을 가지고 객체 생성
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_DASH_FORMAT);
        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "결과 : " + formatter.format(date));

        return new SimpleDateFormat(DATE_DASH_FORMAT).format(date);

    } // End of method [formatOfDashDate]


    /**
     * [method] [Date] date 를 받아서 날짜 형태로 문자열 만들기
     * [format] ####년 ##월 ##일
     *
     * @param date 날짜
     * @return '####년 ##월 ##일' 형태로 반환된 문자열
     */
    public static String formatOfDate(Date date) {

        final String METHOD_NAME = "[getFormatOfDate] ";

        // [lv/C]SimpleDateFormat : 특정 날짜 형태로 만들 문자열을 가지고 객체 생성
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "결과 : " + formatter.format(date));

        return formatter.format(date);

    } // End of method [getFormatOfDate]


    /**
     * [method] [date] year, month, day 값을 받아서 MessageFormat 을 이용하여
     * '####년 ?#월 ##일'로 만들어 반환한다.
     * <p>
     * (주의) 해당 변환은 '2020년 8월 9일' 형태로 변환됨. 즉, 08월이 아니라, 8월로 09일이 아니라 9일로 변환된다.
     *
     * @param year
     * @param month
     * @param day
     * @return 해당 포맷으로 변환된 문자열
     */
    public static String formatOfDate(String year, String month, String day) {

        // [lv/C]String : year, month, day 를 배열로 만든다.
        String[] arguments = {year, month, day};

        return MessageFormat.format(DATE_FORMAT_2, arguments);

    } // End of method [setDate]


    // ===================================================== game record =====================================================

    /**
     * [method] [gameMode] gameRecordWin 과 gameRecordLoss 를 받아서 game record 형태로 문자열 만들기
     * [format] #전 #승 #패
     *
     * @param gameRecordWin  게임 승리 수 / int
     * @param gameRecordLoss 게임 패배 수 / int
     * @return '#전 #승 #패' 형태로 변환된 문자열
     */
    public static String formatOfGameRecord(int gameRecordWin, int gameRecordLoss) {
        final String METHOD_NAME = "[getFormatOfGameRecord] ";

        String temp = new StringBuilder()
                .append(gameRecordWin + gameRecordLoss)
                .append("전 ")
                .append(gameRecordWin)
                .append("승 ")
                .append(gameRecordLoss)
                .append("패")
                .toString();

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "Format/GameRecord 변환 결과 : " + temp);

        return temp;
    } // End of method [getFormatOfGameRecord]


    // ===================================================== score =====================================================

    /**
     * [method] [score] score_1 과 score_2 를 받아서 스코어 형태로 문자열 만들기
     * [format] # : #
     *
     * @param score_1 스코어 값 1 / int
     * @param score_2 스코어 값 2 / int
     * @return '# : #' 형태로 변환된 문자열
     */
    public static String formatOfScore(String score_1, String score_2) {
        final String METHOD_NAME = "[formatOfScore] ";

        String temp = new StringBuilder()
                .append(score_1)
                .append(" : ")
                .append(score_2)
                .toString();

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "Format/Score 변환 결과 : " + temp);

        return temp;
    } // End of method [formatOfScore]


    /**
     * [method] [score] ArrayList<String> 형태의 배열에 들어있는 score 값을 스코어 형태로 문자열 만들어 반환하기
     *
     * @param scores score 값이 들어있는 문자열 배열
     * @return score 의 length 에 맞는 형태로 만든 값
     */
    public static String formatOfScore(ArrayList<String> scores) {
        final String METHOD_NAME = "[formatOfScore] ";

        StringBuilder temp = new StringBuilder();

        temp.append(scores.get(0));

        for (int index = 1; index < scores.size(); index++) {
            temp.append(" : ");
            temp.append(scores.get(index));
        }

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "Format/Score 변환 결과 : " + temp);

        return temp.toString();
    } // End of method [formatOfScore]


    /**
     * [method] [score] ArrayList<String> 형태의 배열에 들어있는 score 값을 스코어 형태로 문자열 만들어 반환하기
     *
     * @param scores score 값이 들어있는 숫자 배열
     * @return score 의 length 에 맞는 형태로 만든 값
     */
    public static String formatOfScore(int[] scores) {
        final String METHOD_NAME = "[formatOfScore] ";

        StringBuilder temp = new StringBuilder();

        temp.append(scores[0]);

        for (int index = 1; index < scores.length; index++) {
            temp.append(" : ");
            temp.append(scores[index]);
        }

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "Format/Score 변환 결과 : " + temp);

        return temp.toString();
    } // End of method [formatOfScore]


    // ===================================================== play time =====================================================

    /**
     * [method] [score] playTime 을 받아서 시간 형태의 문자열을 만들기
     * [format] # 분
     *
     * @param playTime 게임 시간 / String
     * @return '# 분' 형태로 변환된 문자열 변환
     */
    public static String formatOfPlayTime(String playTime) {
        final String METHOD_NAME = "[formatOfPlayTime] ";

        String temp = new StringBuilder()
                .append(playTime)
                .append(" 분")
                .toString();

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "Format/PlayTime 변환 결과 : " + temp);

        return temp;
    } // End of method [formatOfPlayTime]


    /**
     * [method] [playTime] playTime 을 받아서 시간 형태의 문자열 만들기
     * [format] # 분
     *
     * @param playTime 게임 시간 / int
     * @return '# 분' 형태로 변환된 문자열 변환
     */
    public static String formatOfPlayTime(int playTime) {
        final String METHOD_NAME = "[formatOfPlayTime] ";

        String temp = new StringBuilder()
                .append(playTime)
                .append(" 분")
                .toString();

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "Format/PlayTime 변환 결과 : " + temp);

        return temp;
    } // End of method [formatOfPlayTime]


    // ===================================================== cost =====================================================

    /**
     * [method] [playTime] cost 를 받아서 가격 형태의 문자열 만들기
     * [format] ###,### 원
     *
     * @param cost 가격 / String
     * @return '###,### 원' 형태로 변환된 문자열
     */
    public static String formatOfCost(String cost) {
        final String METHOD_NAME = "[formatOfCost] ";

        DecimalFormat wonFormat = new DecimalFormat("###,###");

        String temp = new StringBuilder()
                .append(wonFormat.format(Long.parseLong(cost)))
                .append(" 원")
                .toString();

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "Format/Cost 변환 결과 : " + temp);

        return temp;
    } // End of method [formatOfCost]

    /**
     * [method] [cost] cost 를 받아서 가격 형태의 문자열 만들기
     * [format] ###,### 원
     *
     * @param cost 가격 / int
     * @return '###,### 원' 형태로 변환된 문자열
     */
    public static String formatOfCost(int cost) {

        final String METHOD_NAME = "[formatOfCost] ";

        // [lv/C]DecimalFormat : 숫자를 특정형태로 만들기 위한 객체 생성
        DecimalFormat wonFormat = new DecimalFormat("###,###");

        String temp = new StringBuilder()
                .append(wonFormat.format(cost))
                .append(" 원")
                .toString();

        DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "Format/Cost 변환 결과 : " + temp);

        return temp;
    } // End of method [formatOfCost]

}
