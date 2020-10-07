package com.skyman.billiarddata.developer;

import android.util.Log;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

/**
 * 개발과정에서 확인해야 하는 정보를 관리하기 위한 개발자 메니저 클래스이다.
 */
public final class DeveloperManager {


    public static final Display DISPLAY_POWER = Display.ON;

    /**
     * [method] 해당 log message 를 Log 로 출력한다.
     */
    public static void displayLog(String className, String logMessage) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON)
            Log.d(className, logMessage);

    } // End of method [displayLog]


    /**
     * [method] BilliardData 의 정보를 받아서, 모든 내용을 Log 로 출력한다.
     */
    public static void displayToBilliardData(String className, BilliardData billiardData) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            // [check 2] : BilliardData 가 있다.
            if (billiardData != null) {
                Log.d(className, "0. id : " + billiardData.getCount());                             // 0. count
                Log.d(className, "1. date : " + billiardData.getDate());                            // 1. date
                Log.d(className, "2. target score : " + billiardData.getTargetScore());             // 2. target score
                Log.d(className, "3. speciality : " + billiardData.getSpeciality());                // 3. speciality
                Log.d(className, "4. play time : " + billiardData.getPlayTime());                   // 4. play time
                Log.d(className, "5. winner : " + billiardData.getWinner());                        // 5. winner
                Log.d(className, "6. score : " + billiardData.getScore());                          // 6. score
                Log.d(className, "7. cost : " + billiardData.getCost());                            // 7. cost
            } else {
                Log.d(className, "** The object of billiardData is null.");
            } // [check 2]

        } // [check 1]
    }


    /**
     * [method] UserData 의 정보를 받아서, 모든 내용을 Log 로 출력한다.
     */
    public static void displayToUserData(String className, UserData userData) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            // [check 2] : UserData 가 있다.
            if (userData != null) {
                Log.d(className, "UserData / 0. id : " + userData.getId());                                    // 0. id
                Log.d(className, "UserData / 1. name : " + userData.getName());                                // 1. name
                Log.d(className, "UserData / 2. target score : " + userData.getTargetScore());                 // 2. target score
                Log.d(className, "UserData / 3. speciality : " + userData.getSpeciality());                    // 3. speciality
                Log.d(className, "UserData / 4. game record win : " + userData.getGameRecordWin());            // 4. game record win
                Log.d(className, "UserData / 5. game record loss : " + userData.getGameRecordLoss());          // 5. game record loss
                Log.d(className, "UserData / 5. recent game player id : " + userData.getRecentGamePlayerId());      // 6. recent game player id
                Log.d(className, "UserData / 5. recent play date : " + userData.getRecentPlayDate());          // 7. recent play date
                Log.d(className, "UserData / 6. total play time : " + userData.getTotalPlayTime());            // 8. total play time
                Log.d(className, "UserData / 7. total cost : " + userData.getTotalCost());                     // 9. total cost
            } else {
                Log.d(className, "== The object of userData is null. ==");
            } // [check 2]

        } // [check 1]

    } // End of method [displayToUserData]


    /**
     * [method] UserData 의 id, gameRecordWin, gameRecordLoss, recentPlayerId, recentPlayDate, totalPlayTime, totalCost 의 값을 Log 로 출력한다.
     */
    public static void displayToUserData(String className, long id, int gameRecordWin, int gameRecordLoss, long recentGamePlayerId, String recentPlayDate, int totalPlayTime, int totalCost) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            Log.d(className, "userData / 0. id : " + id);                                   // 0. id
            Log.d(className, "userData / 4. gameRecordWin : " + gameRecordWin );            // 4. game record win
            Log.d(className, "userData / 5. gameRecordLoss : " + gameRecordLoss);           // 5. game record loss
            Log.d(className, "userData / 6. recentGamePlayerId : " + recentGamePlayerId);   // 6. recent game player id
            Log.d(className, "userData / 7. recentPlayDate : " + recentPlayDate);           // 7. recent play date
            Log.d(className, "userData / 8. totalPlayTime : " + totalPlayTime);             // 8. total play time
            Log.d(className, "userData / 9. totalCost : " + totalCost);                     // 9. total cost

        } // [check 1]

    } // End of method [displayToUserData]


    /**
     * [method] FriendData 의 정보를 받아서, 모든 내용을 Log 로 출력한다.
     */
    public static void displayToFriendData(String className, FriendData friendData) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            // [check 2] : FriendData 가 있다.
            if (friendData != null) {

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
            } // [check 2]

        } // [check 1]

    } // End of method [displayToFriendData]


    /**
     * [method] ArrayList<FriendData> 의 정보를 받아서, 모든 내용을 Log 로 출력한다.
     */
    public static void displayToFriendData(String className, ArrayList<FriendData> friendDataArrayList) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            // [check 2] : FriendDataArrayList 의 데이터가 있다.
            if (friendDataArrayList != null) {

                // [cycle 1] : friendDataArrayList 의 size 만큼 모든 내용을 확인한다.
                for (int position = 0; position < friendDataArrayList.size(); position++) {
                    Log.d(className, "FriendData / 0. id : " + friendDataArrayList.get(position).getId());                                  // 0. id
                    Log.d(className, "FriendData / 1. user id : " + friendDataArrayList.get(position).getUserId());                         // 1. user id
                    Log.d(className, "FriendData / 2. name : " + friendDataArrayList.get(position).getName());                              // 2. name
                    Log.d(className, "FriendData / 3. game record win : " + friendDataArrayList.get(position).getGameRecordWin());          // 3. game record win
                    Log.d(className, "FriendData / 4. game record loss : " + friendDataArrayList.get(position).getGameRecordLoss());        // 4. game record loss
                    Log.d(className, "FriendData / 5. recent play date : " + friendDataArrayList.get(position).getRecentPlayDate());        // 5. recent play date
                    Log.d(className, "FriendData / 6. total play time : " + friendDataArrayList.get(position).getTotalPlayTime());          // 6. total play time
                    Log.d(className, "FriendData / 7. total cost : " + friendDataArrayList.get(position).getTotalCost());                   // 7. total cost

                } // [cycle 1]

            } // [check 2]

        } // [check 1]

    } // End of method [displayToFriendData]


    /**
     * [method] FriendData 의 id, gameRecordWin, gameRecordLoss, recentPlayDate, totalPlayTime, totalCost 의 값을 Log 로 출력한다.
     */
    public static void displayToFriendData(String className, long id, int gameRecordWin, int gameRecordLoss, String recentPlayDate, int totalPlayTime, int totalCost) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            Log.d(className, "friendData / 0. id : " + id);                                 // 0. id
            Log.d(className, "friendData / 3. gameRecordWin : " + gameRecordWin);           // 3. game record win
            Log.d(className, "friendData / 4. gameRecordLoss : " + gameRecordLoss);         // 4. game record loss
            Log.d(className, "friendData / 5. recentPlayDate : " + recentPlayDate);         // 5. recent play date
            Log.d(className, "friendData / 6. totalPlayTime : " + totalPlayTime);           // 6. total play time
            Log.d(className, "friendData / 7. totalCost : " + totalCost);                   // 7. total cost


        } // [check 1]

    } // End of method [displayToFriendData]

}
