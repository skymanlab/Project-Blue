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

    /* method : userData 보기 1 */
    public static void displayToUserData(String className, UserData userData){
        if(userData != null) {
            Log.d(className, "UserData / 0. id : " + userData.getId());                                    // 0. id
            Log.d(className, "UserData / 1. name : " + userData.getName());                                // 1. name
            Log.d(className, "UserData / 2. target score : " + userData.getTargetScore());                 // 2. target score
            Log.d(className, "UserData / 3. speciality : " + userData.getSpeciality());                    // 3. speciality
            Log.d(className, "UserData / 4. game record win : " + userData.getGameRecordWin());            // 4. game record win
            Log.d(className, "UserData / 5. game record loss : " + userData.getGameRecordLoss());          // 5. game record loss
            Log.d(className, "UserData / 5. game record loss : " + userData.getRecentGamePlayerId());      // 6. recent game player id
            Log.d(className, "UserData / 5. game record loss : " + userData.getRecentPlayDate());          // 7. recent play date
            Log.d(className, "UserData / 6. total play time : " + userData.getTotalPlayTime());            // 8. total play time
            Log.d(className, "UserData / 7. total cost : " + userData.getTotalCost());                     // 9. total cost
        } else {
            Log.d(className, "== The object of userData is null. ==");
        }
    }

    /* method : userData 보기 2 */
    public static void displayToUserData(String className, long id, int gameRecordWin, int gameRecordLoss, long recentGamePlayerId, String recentPlayDate, int totalPlayTime, int totalCost ){
        DeveloperManager.displayLog(className, "userData / 0. id : " + id);                                        // 0. id
        DeveloperManager.displayLog(className, "userData / 4. gameRecordWin : " + gameRecordWin);                  // 4. game record win
        DeveloperManager.displayLog(className, "userData / 5. gameRecordLoss : " + gameRecordLoss);                // 5. game record loss
        DeveloperManager.displayLog(className, "userData / 6. recentGamePlayerId : " + recentGamePlayerId);        // 6. recent game player id
        DeveloperManager.displayLog(className, "userData / 7. recentPlayDate : " + recentPlayDate);                // 7. recent play date
        DeveloperManager.displayLog(className, "userData / 8. totalPlayTime : " + totalPlayTime);                  // 8. total play time
        DeveloperManager.displayLog(className, "userData / 9. totalCost : " + totalCost);                          // 9. total cost
    }

    /* method : friendData 보기 1 */
    public static void displayToFriendData(String className, FriendData friendData) {
        if(friendData != null) {
            Log.d(className, "FriendData / 0. id : " + friendData.getId());                                  // 0. id
            Log.d(className, "FriendData / 1. user id : " + friendData.getUserId());                         // 1. user id
            Log.d(className, "FriendData / 2. name : " + friendData.getName());                              // 2. name
            Log.d(className, "FriendData / 3. game record win : " + friendData.getGameRecordWin());          // 3. game record win
            Log.d(className, "FriendData / 4. game record loss : " + friendData.getGameRecordLoss());        // 4. game record loss
            Log.d(className, "FriendData / 5. recent play date : " + friendData.getRecentPlayDate());        // 5. recent play date
            Log.d(className, "FriendData / 6. total play time : " + friendData.getTotalPlayTime());          // 6. total play time
            Log.d(className, "FriendData / 7. total cost : " + friendData.getTotalCost());                   // 7. total cost
        } else {
            Log.d(className, "== The object of friendData is null. ==");
        }
    }

    /* method : friendData 보기 2 */
    public static void displayToFriendData(String className, long id, int gameRecordWin, int gameRecordLoss, String recentPlayDate, int totalPlayTime, int totalCost) {
        DeveloperManager.displayLog(className, "friendData / 0. id : " + id);                                      // 0. id
        DeveloperManager.displayLog(className, "friendData / 3. gameRecordWin : " + gameRecordWin);                // 3. game record win
        DeveloperManager.displayLog(className, "friendData / 4. gameRecordLoss : " + gameRecordLoss);              // 4. game record loss
        DeveloperManager.displayLog(className, "friendData / 5. recentPlayDate : " + recentPlayDate);              // 5. recent play date
        DeveloperManager.displayLog(className, "friendData / 6. totalPlayTime : " + totalPlayTime);                // 6. total play time
        DeveloperManager.displayLog(className, "friendData / 7. totalCost : " + totalCost);                        // 7. total cost
    }

}
