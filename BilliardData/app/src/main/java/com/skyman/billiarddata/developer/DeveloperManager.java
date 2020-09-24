package com.skyman.billiarddata.developer;

import android.util.Log;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.user.data.UserData;

/**
 * ===================================================================================
 * 순전히 개발자의 입장을 표명하기 위한 클래스
 * ===================================================================================
 * */
public class DeveloperManager {

    public static final Display DISPLAY_POWER = Display.ON;

    public static void displayLog(String className, String logMessage){
        if (DISPLAY_POWER == Display.ON)
            Log.d(className, logMessage);
    }

    public static void displayToUserData(String className, UserData userData){
        if(userData != null) {
            Log.d(className, "0. id : " + userData.getId());
            Log.d(className, "1. name : " + userData.getName());
            Log.d(className, "2. target score : " + userData.getTargetScore());
            Log.d(className, "3. speciality : " + userData.getSpeciality());
            Log.d(className, "4. game record win : " + userData.getGameRecordWin());
            Log.d(className, "5. game record loss : " + userData.getGameRecordLoss());
            Log.d(className, "6. total play time : " + userData.getTotalPlayTime());
            Log.d(className, "7. total cost : " + userData.getTotalCost());
        } else {
            Log.d(className, "** The object of userData is null.");
        }
    }

    public static void displayToBilliardData(String className, BilliardData billiardData) {
        if(billiardData != null) {
            Log.d(className, "0. id : " + billiardData.getId());
            Log.d(className, "1. date : " + billiardData.getDate());
            Log.d(className, "2. target score : " + billiardData.getTargetScore());
            Log.d(className, "3. speciality : " + billiardData.getSpeciality());
            Log.d(className, "4. play time : " + billiardData.getPlayTime());
            Log.d(className, "5. winner : " + billiardData.getWinner());
            Log.d(className, "6. score : " + billiardData.getScore());
            Log.d(className, "7. cost : " + billiardData.getCost());
        } else {
            Log.d(className, "** The object of billiardData is null.");
        }
    }
}
