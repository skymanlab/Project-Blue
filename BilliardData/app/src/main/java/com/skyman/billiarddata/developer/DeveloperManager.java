package com.skyman.billiarddata.developer;

import android.util.Log;

import com.skyman.billiarddata.table.billiard.data.BilliardData;
import com.skyman.billiarddata.table.friend.data.FriendData;
import com.skyman.billiarddata.table.player.data.PlayerData;
import com.skyman.billiarddata.table.user.data.UserData;

import java.util.ArrayList;

/**
 * 개발과정에서 확인해야 하는 정보를 관리하기 위한 개발자 메니저 클래스이다.
 */
public final class DeveloperManager {

    public static final Display PROJECT_LOG_SWITCH = Display.ON;


//    public static void displayLog(String className, String logMessage) {
//        if (PROJECT_LOG_SWITCH == Display.ON)
//            Log.d(className, logMessage);
//    }

    public static void printLog(Display CLASS_LOG_SWITCH, String className, String log) {
        if (PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON)
                Log.d(className, log);
    }


    /////////////////////////////////////////////////////////
    // userData /////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    /**
     * userData 내용 Log 출력
     *
     * @param className
     * @param userData
     */
    public static void printLogUserData(Display CLASS_LOG_SWITCH, String className, UserData userData) {

        if (PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON) {
                if (userData != null) {

                    Log.d(className, "[userData 내용 확인]");
                    Log.d(className, "userData / 0. id : " + userData.getId());                                                 // 0. id
                    Log.d(className, "userData / 1. name : " + userData.getName());                                             // 1. name
                    Log.d(className, "userData / 2. targetScore : " + userData.getTargetScore());                               // 2. target score
                    Log.d(className, "userData / 3. speciality : " + userData.getSpeciality());                                 // 3. speciality
                    Log.d(className, "userData / 4. gameRecordWin : " + userData.getGameRecordWin());                           // 4. game record win
                    Log.d(className, "userData / 5. gameRecordLoss : " + userData.getGameRecordLoss());                         // 5. game record loss
                    Log.d(className, "userData / 6. recentGameBilliardCount : " + userData.getRecentGameBilliardCount());       // 6. recent game billiard count
                    Log.d(className, "userData / 7. totalPlayTime : " + userData.getTotalPlayTime());                           // 7. total play time
                    Log.d(className, "userData / 8. totalCost : " + userData.getTotalCost());                                   // 8. total cost

                } else {
                    Log.d(className, "userData 가 null 입니다.");
                }
            }
    }

    /**
     * userDataArrayList 내용 Log 출력
     *
     * @param className
     * @param userDataArrayList
     */
    public static void printLogUserData(Display CLASS_LOG_SWITCH, String className, ArrayList<UserData> userDataArrayList) {

        if (PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON)
                if (userDataArrayList.size() != 0) {

                    Log.d(className, "[userDataArrayList 내용 확인]");
                    for (int index = 0; index < userDataArrayList.size(); index++) {

                        Log.d(className, "---- " + index + "번째 ----");
                        Log.d(className, "userData / 0. id : " + userDataArrayList.get(index).getId());                                                 // 0. id
                        Log.d(className, "userData / 1. name : " + userDataArrayList.get(index).getName());                                             // 1. name
                        Log.d(className, "userData / 2. targetScore : " + userDataArrayList.get(index).getTargetScore());                               // 2. target score
                        Log.d(className, "userData / 3. speciality : " + userDataArrayList.get(index).getSpeciality());                                 // 3. speciality
                        Log.d(className, "userData / 4. gameRecordWin : " + userDataArrayList.get(index).getGameRecordWin());                           // 4. game record win
                        Log.d(className, "userData / 5. gameRecordLoss : " + userDataArrayList.get(index).getGameRecordLoss());                         // 5. game record loss
                        Log.d(className, "userData / 6. recentGameBilliardCount : " + userDataArrayList.get(index).getRecentGameBilliardCount());       // 6. recent game billiard count
                        Log.d(className, "userData / 7. totalPlayTime : " + userDataArrayList.get(index).getTotalPlayTime());                           // 7. total play time
                        Log.d(className, "userData / 8. totalCost : " + userDataArrayList.get(index).getTotalCost());                                   // 8. total cost

                    }

                } else {
                    Log.d(className, "userDataArrayList 의 size 가 0 입니다.");
                }

    }


    /////////////////////////////////////////////////////////
    // friendData ///////////////////////////////////////////
    /////////////////////////////////////////////////////////

    /**
     * friendData 내용 Log 출력
     *
     * @param className
     * @param friendData
     */
    public static void printLogFriendData(Display CLASS_LOG_SWITCH, String className, FriendData friendData) {

        if (PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON)
                if (friendData != null) {

                    Log.d(className, "[friendData 내용 확인]");
                    Log.d(className, "friendData / 0. id : " + friendData.getId());                                             // 0. id
                    Log.d(className, "friendData / 1. userId : " + friendData.getUserId());                                     // 1. user id
                    Log.d(className, "friendData / 2. name : " + friendData.getName());                                         // 2. name
                    Log.d(className, "friendData / 3. gameRecordWin : " + friendData.getGameRecordWin());                       // 3. game record win
                    Log.d(className, "friendData / 4. gameRecordLoss : " + friendData.getGameRecordLoss());                     // 4. game record loss
                    Log.d(className, "friendData / 5. recentGameBilliardCount : " + friendData.getRecentGameBilliardCount());   // 5. recent game billiard count
                    Log.d(className, "friendData / 6. totalPlayTime : " + friendData.getTotalPlayTime());                       // 6. total play time
                    Log.d(className, "friendData / 7. totalCost : " + friendData.getTotalCost());                               // 7. total cost

                } else {
                    Log.d(className, "friendData 가 null 입니다.");
                }

    }

    /**
     * friendDataArrayList 내용 Log 출력
     *
     * @param className
     * @param friendDataArrayList
     */
    public static void printLogFriendData(Display CLASS_LOG_SWITCH, String className, ArrayList<FriendData> friendDataArrayList) {

        if (PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON)
                if (friendDataArrayList.size() != 0) {

                    Log.d(className, "[friendDataArrayList 내용 확인]");
                    for (int index = 0; index < friendDataArrayList.size(); index++) {

                        Log.d(className, "---- " + index + "번째 ----");
                        Log.d(className, "friendData / 0. id : " + friendDataArrayList.get(index).getId());                                              // 0. id
                        Log.d(className, "friendData / 1. userId : " + friendDataArrayList.get(index).getUserId());                                      // 1. user id
                        Log.d(className, "friendData / 2. name : " + friendDataArrayList.get(index).getName());                                          // 2. name
                        Log.d(className, "friendData / 3. gameRecordWin : " + friendDataArrayList.get(index).getGameRecordWin());                        // 3. game record win
                        Log.d(className, "friendData / 4. gameRecordLoss : " + friendDataArrayList.get(index).getGameRecordLoss());                      // 4. game record loss
                        Log.d(className, "friendData / 5. recentGameBilliardCount : " + friendDataArrayList.get(index).getRecentGameBilliardCount());    // 5. recent game billiard count
                        Log.d(className, "friendData / 6. totalPlayTime : " + friendDataArrayList.get(index).getTotalPlayTime());                        // 6. total play time
                        Log.d(className, "friendData / 7. totalCost : " + friendDataArrayList.get(index).getTotalCost());                                // 7. total cost
                    }

                } else {
                    Log.d(className, "friendDataArrayList 의 size 가 0 입니다.");
                }

    }


    /////////////////////////////////////////////////////////
    // billiardData /////////////////////////////////////////
    /////////////////////////////////////////////////////////

    /**
     * billiardData 내용 Log 출력
     *
     * @param className
     * @param billiardData
     */
    public static void printLogBilliardData(Display CLASS_LOG_SWITCH, String className, BilliardData billiardData) {

        if (PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON)
                if (billiardData != null) {

                    Log.d(className, "[billiardData 내용 확인]");
                    Log.d(className, "billiardData / 0. count : " + billiardData.getCount());                      // 0. count
                    Log.d(className, "billiardData / 1. date : " + billiardData.getDate());                        // 1. date
                    Log.d(className, "billiardData / 2. gameMode : " + billiardData.getGameMode());                // 2. target score
                    Log.d(className, "billiardData / 3. playerCount : " + billiardData.getPlayerCount());          // 3. speciality
                    Log.d(className, "billiardData / 4. winnerId : " + billiardData.getWinnerId());                // 4. winner id
                    Log.d(className, "billiardData / 5. winnerName : " + billiardData.getWinnerName());            // 5. winner name
                    Log.d(className, "billiardData / 6. playTime : " + billiardData.getPlayTime());                // 6. play time
                    Log.d(className, "billiardData / 7. score : " + billiardData.getScore());                      // 7. score
                    Log.d(className, "billiardData / 8. cost : " + billiardData.getCost());                        // 8. cost

                } else {
                    Log.d(className, "billiardData 가 null 입니다.");
                }

    }

    /**
     * billiardDataArrayList 내용 Log 출력
     *
     * @param className
     * @param billiardDataArrayList
     */
    public static void printLogBilliardData(Display CLASS_LOG_SWITCH, String className, ArrayList<BilliardData> billiardDataArrayList) {

        if (PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON)
                if (billiardDataArrayList.size() != 0) {

                    Log.d(className, "[billiardDataArrayList 내용 확인]");
                    for (int index = 0; index < billiardDataArrayList.size(); index++) {

                        Log.d(className, "---- " + index + "번째 ----");
                        Log.d(className, "billiardData / 0. count : " + billiardDataArrayList.get(index).getCount());                      // 0. count
                        Log.d(className, "billiardData / 1. date : " + billiardDataArrayList.get(index).getDate());                        // 1. date
                        Log.d(className, "billiardData / 2. gameMode : " + billiardDataArrayList.get(index).getGameMode());                // 2 target score
                        Log.d(className, "billiardData / 3. playerCount : " + billiardDataArrayList.get(index).getPlayerCount());          // 3. speciality
                        Log.d(className, "billiardData / 4. winnerId : " + billiardDataArrayList.get(index).getWinnerId());                // 4. winner id
                        Log.d(className, "billiardData / 5. winnerName : " + billiardDataArrayList.get(index).getWinnerName());            // 5. winner name
                        Log.d(className, "billiardData / 6. playTime : " + billiardDataArrayList.get(index).getPlayTime());                // 6. play time
                        Log.d(className, "billiardData / 7. score : " + billiardDataArrayList.get(index).getScore());                      // 7. score
                        Log.d(className, "billiardData / 8. cost : " + billiardDataArrayList.get(index).getCost());                        // 8. cost

                    }

                } else {
                    Log.d(className, "billiardDataArrayList 의 size 가 0 입니다.");
                }

    }


    /////////////////////////////////////////////////////////
    // PlayerData ///////////////////////////////////////////
    /////////////////////////////////////////////////////////

    /**
     * playerDataArrayList
     *
     * @param className
     * @param playerDataArrayList
     */
    public static void printLogPlayerData(Display CLASS_LOG_SWITCH, String className, ArrayList<PlayerData> playerDataArrayList) {

        if (PROJECT_LOG_SWITCH == Display.ON)
            if (CLASS_LOG_SWITCH == Display.ON)
                if (playerDataArrayList.size() != 0) {

                    Log.d(className, "[playerDataArrayList 내용 확인]");
                    for (int index = 0; index < playerDataArrayList.size(); index++) {

                        Log.d(className, "---- " + index + "번째 ----");
                        Log.d(className, "PlayerData / 0. count : " + playerDataArrayList.get(index).getCount());                       // 0. id
                        Log.d(className, "PlayerData / 1. billiardCount : " + playerDataArrayList.get(index).getBilliardCount());       // 1. billiard count
                        Log.d(className, "PlayerData / 2. playerId : " + playerDataArrayList.get(index).getPlayerId());                 // 2. player id
                        Log.d(className, "PlayerData / 3. playerName : " + playerDataArrayList.get(index).getPlayerName());             // 3. player name
                        Log.d(className, "PlayerData / 4. targetScore : " + playerDataArrayList.get(index).getTargetScore());           // 4. target score
                        Log.d(className, "PlayerData / 5. score : " + playerDataArrayList.get(index).getScore());                       // 5. score

                    }

                } else {
                    Log.d(className, "playerDataArrayList 의 size 가 0 입니다.");
                }

    }

}
