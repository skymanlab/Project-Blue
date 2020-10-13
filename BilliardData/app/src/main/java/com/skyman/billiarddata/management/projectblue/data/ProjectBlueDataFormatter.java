package com.skyman.billiarddata.management.projectblue.data;

import com.skyman.billiarddata.developer.DeveloperManager;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class ProjectBlueDataFormatter {

    // constant
    private final String CLASS_NAME_LOG = "";
    public static final String DATE_FORMAT = "yyyy년 MM월 dd일";
    public static final String DATE_DASH_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_2 = "{0}년 {1}월 {2}일";


    /**
     * [method] [Date] date 를 받아서 날짜 형태로 문자열 만들기
     *          [format] ####년 ##월 ##일
     *
     * @param date      날짜
     * @return '####년 ##월 ##일' 형태로 반환된 문자열
     */
    public static String getDashFormatOfDate(Date date){

        // [lv/C]SimpleDateFormat : 특정 날짜 형태로 만들 문자열을 가지고 객체 생성
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_DASH_FORMAT);
        DeveloperManager.displayLog("[DF]_ProjectBlueDataFormatter", "[getFormatOfDate] 결과 : " + formatter.format(date));

        return  formatter.format(date);

    } // End of method [getFormatOfDate]


    /**
     * [method] [Date] date 를 받아서 날짜 형태로 문자열 만들기
     *          [format] ####년 ##월 ##일
     *
     * @param date      날짜
     * @return '####년 ##월 ##일' 형태로 반환된 문자열
     */
    public static String getFormatOfDate(Date date){

        // [lv/C]SimpleDateFormat : 특정 날짜 형태로 만들 문자열을 가지고 객체 생성
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        DeveloperManager.displayLog("[DF]_ProjectBlueDataFormatter", "[getFormatOfDate] 결과 : " + formatter.format(date));

        return  formatter.format(date);

    } // End of method [getFormatOfDate]



    /**
     * [method] year, month, day 값을 받아서 MessageFormat 을 이용하여
     *          '####년 ?#월 ##일'로 만들어 반환한다.
     *
     * (주의) 해당 변환은 '2020년 8월 9일' 형태로 변환됨. 즉, 08월이 아니라, 8월로 09일이 아니라 9일로 변환된다.
     *
     * @param year
     * @param month
     * @param day
     * @return 해당 포맷으로 변환된 문자열
     */
    public static String getFormatOfDate(String year, String month, String day) {

        // [lv/C]String : year, month, day 를 배열로 만든다.
        String[] arguments = {year, month, day};

        return MessageFormat.format(DATE_FORMAT_2, arguments);

    } // End of method [setDate]



    /**
     * [method]  year, month, day 값을 받아서 MessageFormat 을 이용하여
     *          '####년 ##월 ##일'로 만들어 반환한다.
     * <p>
     *     위 의 method 와는 다르게 '2020년 08월 09일'의 형태로 변환된다.
     * @param year
     * @param month
     * @param day
     * @return 해당 포맷으로 변환된 문자열
     */
    public static String getFormatOfDate(int year, int month, int day) {

        // [lv/C]Calendar : Calendar 객체 가져오기
        Calendar calendar = Calendar.getInstance();

        // [lv/C]Calendar : 위 의 객체에 매개변수로 세팅하여 날짜 만들기
        calendar.set(year, month-1, day);
        DeveloperManager.displayLog("DateModify", "[setDate] 변환 값은 : " + ProjectBlueDataFormatter.getFormatOfDate(calendar.getTime()));

        return  ProjectBlueDataFormatter.getFormatOfDate(calendar.getTime());

    } // End of method [setDate]



    /**
     * [method] [String 타입] score_1 과 score_2 를 받아서 스코어 형태로 문자열 만들기
     *          [format] # : #
     *
     * @param score_1   스코어 값 1 / int
     * @param score_2   스코어 값 2 / int
     * @return '# : #' 형태로 변환된 문자열
     */
    public static String getFormatOfScore(String score_1, String score_2) {

        // [lv/C]StringBuilder : 문자열을 더하기 위한 객체 생성
        StringBuilder scoreData = new StringBuilder();

        scoreData.append(score_1);
        scoreData.append(" : ");
        scoreData.append(score_2);

        DeveloperManager.displayLog("[DF]_ProjectBlueDataFormatter", "[getFormatOfScore] 결과 : " + scoreData.toString());
        return scoreData.toString();

    } // End of method [getFormatOfScore]



    /**
     * [method] [int 타입] gameRecordWin 과 gameRecordLoss 를 받아서 game record 형태로 문자열 만들기
     *          [format] #전 #승 #패
     *
     * @param gameRecordWin     게임 승리 수 / int
     * @param gameRecordLoss    게임 패배 수 / int
     * @return '#전 #승 #패' 형태로 변환된 문자열
     */
    public static String getFormatOfGameRecord(int gameRecordWin, int gameRecordLoss){

        // [lv/C]StringBuilder : 문자열을 더하기 위한 객체 생성
        StringBuilder temp = new StringBuilder();

        // 1. #전
        temp.append(gameRecordWin+gameRecordLoss);
        temp.append("전 ");

        // 2. #승
        temp.append(gameRecordWin);
        temp.append("승 ");

        // 3. #패
        temp.append(gameRecordLoss);
        temp.append("패");

        DeveloperManager.displayLog("[DF]_ProjectBlueDataFormatter", "[getFormatOfGameRecord] 결과 : " + temp.toString());
        return temp.toString();

    } // End of method [getFormatOfGameRecord]



    /**
     * [method] [String 타입] playTime 을 받아서 시간 형태의 문자열을 만들기
     *          [format] # 분
     *
     * @param playTime      게임 시간 / String
     * @return '# 분' 형태로 변환된 문자열 변환
     */
    public static String getFormatOfPlayTime(String playTime) {

        // [lv/C]StringBuilder : 문자열을 더하기 위한 객체 생성
        StringBuilder temp = new StringBuilder();

        // 1. # 분
        temp.append(playTime);
        temp.append(" 분");

        DeveloperManager.displayLog("[DF]_ProjectBlueDataFormatter", "[getFormatOfPlayTime] 결과 : " + temp.toString());
        return temp.toString();

    } // End of method [getFormatOfPlayTime]



    /**
     * [method] [int 타입] playTime 을 받아서 시간 형태의 문자열 만들기
     *          [format] # 분
     *
     * @param playTime      게임 시간 / int
     * @return '# 분' 형태로 변환된 문자열 변환
     */
    public static String getFormatOfPlayTime(int playTime) {

        // [lv/C]StringBuilder : 문자열을 더하기 위한 객체 생성
        StringBuilder temp = new StringBuilder();

        // 1. # 분
        temp.append(playTime);
        temp.append(" 분");

        DeveloperManager.displayLog("[DF]_ProjectBlueDataFormatter", "[getFormatOfPlayTime] 결과 : " + temp.toString());
        return temp.toString();

    } // End of method [getFormatOfPlayTime]



    /**
     * [method] [String 타입] cost 를 받아서 가격 형태의 문자열 만들기
     *          [format] ###,### 원
     *
     * @param cost      가격 / String
     * @return '###,### 원' 형태로 변환된 문자열
     */
    public static String getFormatOfCost(String cost) {

        // [lv/C]DecimalFormat : 숫자를 특정형태로 만들기 위한 객체 생성
        DecimalFormat wonFormat = new DecimalFormat("###,###");

        // [lv/C]StringBuilder : 문자열을 더하기 위한 객체 생성
        StringBuilder costData = new StringBuilder();

        costData.append(wonFormat.format(Long.parseLong(cost)));
        costData.append(" 원");

        DeveloperManager.displayLog("[DF]_ProjectBlueDataFormatter", "[getFormatOfCost] 결과 : " + costData.toString());
        return costData.toString();
    } // End of method [getFormatOfCost]



    /**
     * [method] [int 타입] cost 를 받아서 가격 형태의 문자열 만들기
     *          [format] ###,### 원
     *
     * @param cost      가격 / int
     * @return '###,### 원' 형태로 변환된 문자열
     */
    public static String getFormatOfCost(int cost) {

        // [lv/C]DecimalFormat : 숫자를 특정형태로 만들기 위한 객체 생성
        DecimalFormat wonFormat = new DecimalFormat("###,###");

        // [lv/C]StringBuilder : 문자열을 더하기 위한 객체 생성
        StringBuilder costData = new StringBuilder();

        costData.append(wonFormat.format(cost));
        costData.append(" 원");

        DeveloperManager.displayLog("[DF]_ProjectBlueDataFormatter", "[getFormatOfCost] 결과 : " + costData.toString());
        return costData.toString();
    } // End of method [getFormatOfCost]


}
