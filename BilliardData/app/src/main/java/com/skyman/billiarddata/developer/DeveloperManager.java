package com.skyman.billiarddata.developer;

import android.util.Log;

/**
 * ===================================================================================
 * 순전히 개발자의 입장을 표명하기 위한 클래스
 * ===================================================================================
 * */
public class DeveloperManager {

    public static final Display DISPLAY_POWER = Display.ON;

    public static void displayLog(String className, String logMessage){
        Log.d(className, logMessage);
    }
}
