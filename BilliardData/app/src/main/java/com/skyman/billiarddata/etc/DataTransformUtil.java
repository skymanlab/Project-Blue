package com.skyman.billiarddata.etc;

import com.skyman.billiarddata.developer.DeveloperManager;

import java.util.StringTokenizer;

public class DataTransformUtil {

    // constant
    public static final String CLASS_NAME = DataTransformUtil.class.getSimpleName();

    // constant
    public static final String DATE_DELIMITER = "년월일 ";

    /**
     * [method] [date] date 문자열을 StringTokenizer 로 구분한 뒤, 각 문자열을 int 로 parse 한 다음 배열에 담아 반환한다.
     *
     * @param date 날짜 문자열
     * @return 분할 된 날짜가 integer 로 변환되어 담긴 배열
     */
    public static int[] changeDateToIntArrayType(String date) {

        // [lv/i]dateTokenList : 매개변수 문자열이 분할되어 integer 로 parsing 된 다음 저장될 변수
        int[] dateTokenList = new int[3];

        // [lv/C]StringTokenizer : 매개변수 문자열을 '년월일 ' 로 나누기 / DATE_DELIMITER : '년월일 ' / delimiter : 구분자
        StringTokenizer tokenizer = new StringTokenizer(date, DATE_DELIMITER);

        // [cycle 1] : 구분된 값들이 있을 때까지 해당 값을 배열에 넣기
        for (int index = 0; tokenizer.hasMoreTokens(); index++) {

            // [lv/i]dateTokenList : 분할 된 토큰을 year, month, day 순으로 integer 로 parsing 한 값을 담는다.
            dateTokenList[index] = Integer.parseInt(tokenizer.nextToken());

        } // [cycle 1]

        return dateTokenList;

    } // End of method [changeDateToIntArrayType]


    /**
     * [method] [gameMode] 해당 gameMode 문자열로 gameMode spinner 의 몇 번째 item 과 같은지를 비교하여 selectionId 값을 반환한다.
     *
     * @param gameMode 게임 종목
     * @return spinner 의 selection id 값
     */
    public static int getSelectedIdOfBilliardGameModeSpinner(String gameMode) {

        final String METHOD_NAME = "[getSelectedIdOfBilliardGameModeSpinner] ";

        // [check 1] : "3구" 이면 0, "4구" 이면 1, "포켓볼" 이면 2  - java version 7 이후로 switch 문에서 String 을 지원한다.
        switch (gameMode) {
            case "3구":
                return 0;
            case "4구":
                return 1;
            case "포켓볼":
                return 2;
            default:
                DeveloperManager.displayLog(CLASS_NAME, METHOD_NAME + "현재 목록 중에 있는 값이 없네요.");
                return -1;
        }

    } // End of method [getSelectedIdOfBilliardGameModeSpinner]

}
