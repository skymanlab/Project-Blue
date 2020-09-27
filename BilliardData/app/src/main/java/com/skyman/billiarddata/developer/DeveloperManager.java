package com.skyman.billiarddata.developer;

import android.util.Log;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.friend.data.FriendData;
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

    /* method : billiardData 보기 */
    public static void displayToBilliardData(String className, BilliardData billiardData) {
        if(billiardData != null) {
            Log.d(className, "0. id : " + billiardData.getId());                                // 0. count
            Log.d(className, "1. date : " + billiardData.getDate());                            // 1. date
            Log.d(className, "2. target score : " + billiardData.getTargetScore());             // 2. target score
            Log.d(className, "3. speciality : " + billiardData.getSpeciality());                // 3. speciality
            Log.d(className, "4. play time : " + billiardData.getPlayTime());                   // 4. play time
            Log.d(className, "5. winner : " + billiardData.getWinner());                        // 5. winner
            Log.d(className, "6. score : " + billiardData.getScore());                          // 6. score
            Log.d(className, "7. cost : " + billiardData.getCost());                            // 7. cost
        } else {
            Log.d(className, "** The object of billiardData is null.");
        }
    }

    /* method : userData 보기*/
    public static void displayToUserData(String className, UserData userData){
        if(userData != null) {
            Log.d(className, "0. id : " + userData.getId());                                    // 0. id
            Log.d(className, "1. name : " + userData.getName());                                // 1. name
            Log.d(className, "2. target score : " + userData.getTargetScore());                 // 2. target score
            Log.d(className, "3. speciality : " + userData.getSpeciality());                    // 3. speciality
            Log.d(className, "4. game record win : " + userData.getGameRecordWin());            // 4. game record win
            Log.d(className, "5. game record loss : " + userData.getGameRecordLoss());          // 5. game record loss
            Log.d(className, "6. total play time : " + userData.getTotalPlayTime());            // 6. total play time
            Log.d(className, "7. total cost : " + userData.getTotalCost());                     // 7. total cost
        } else {
            Log.d(className, "** The object of userData is null.");
        }
    }

    /* method : friendData 보기 */
    public static void displayToFriendData(String className, FriendData friendData) {
        if(friendData != null) {
            Log.d(className, "0. id : " + friendData.getId());                                  // 0. id
            Log.d(className, "1. user id : " + friendData.getUserId());                         // 1. user id
            Log.d(className, "2. name : " + friendData.getName());                              // 2. name
            Log.d(className, "3. recent play date : " + friendData.getRecentPlayDate());        // 3. recent play date
            Log.d(className, "4. game record win : " + friendData.getGameRecordWin());          // 4. game record win
            Log.d(className, "5. game record loss : " + friendData.getGameRecordLoss());        // 5. game record loss
            Log.d(className, "6. total play time : " + friendData.getTotalPlayTime());          // 6. total play time
            Log.d(className, "7. total cost : " + friendData.getTotalCost());                   // 7. total cost
        }
    }
}
