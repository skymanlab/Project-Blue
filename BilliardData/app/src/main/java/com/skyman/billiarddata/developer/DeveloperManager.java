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

    public static final Display DISPLAY_POWER = Display.OFF;

    /**
     * [method] 해당 log message 를 Log 로 출력한다.
     */
    public static void displayLog(String className, String logMessage) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            Log.d(className, logMessage);

        } // [check 1]

    } // End of method [displayLog]


    // ================================================== UserData ==================================================

    /**
     * [method] UserData 의 정보를 받아서, 모든 내용을 Log 로 출력한다.
     */
    public static void displayToUserData(String className, UserData userData) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            // [check 2] : UserData 가 있다.
            if (userData != null) {

                Log.d(className, "UserData / 0. id : " + userData.getId());                                                 // 0. id
                Log.d(className, "UserData / 1. name : " + userData.getName());                                             // 1. name
                Log.d(className, "UserData / 2. targetScore : " + userData.getTargetScore());                               // 2. target score
                Log.d(className, "UserData / 3. speciality : " + userData.getSpeciality());                                 // 3. speciality
                Log.d(className, "UserData / 4. gameRecordWin : " + userData.getGameRecordWin());                           // 4. game record win
                Log.d(className, "UserData / 5. gameRecordLoss : " + userData.getGameRecordLoss());                         // 5. game record loss
                Log.d(className, "UserData / 6. recentGameBilliardCount : " + userData.getRecentGameBilliardCount());       // 6. recent game billiard count
                Log.d(className, "UserData / 7. totalPlayTime : " + userData.getTotalPlayTime());                           // 7. total play time
                Log.d(className, "UserData / 8. totalCost : " + userData.getTotalCost());                                   // 8. total cost

            } else {
                Log.d(className, "UserData 데이터가 없습니다.");
            } // [check 2]

        } // [check 1]

    } // End of method [displayToUserData]


    /**
     * [method] UserData 의 정보를 받아서, 모든 내용을 Log 로 출력한다.
     */
    public static void displayToUserData(String className, ArrayList<UserData> userDataArrayList) {

        if (DISPLAY_POWER == Display.ON) {

            Log.d(className, "=================================== userDataArrayList : START ===================================");
            for (int index = 0; index < userDataArrayList.size(); index++) {

                Log.d(className, "------>> " + index + " <<------");
                Log.d(className, "UserData / 0. id : " + userDataArrayList.get(index).getId());                                                 // 0. id
                Log.d(className, "UserData / 1. name : " + userDataArrayList.get(index).getName());                                             // 1. name
                Log.d(className, "UserData / 2. targetScore : " + userDataArrayList.get(index).getTargetScore());                               // 2. target score
                Log.d(className, "UserData / 3. speciality : " + userDataArrayList.get(index).getSpeciality());                                 // 3. speciality
                Log.d(className, "UserData / 4. gameRecordWin : " + userDataArrayList.get(index).getGameRecordWin());                           // 4. game record win
                Log.d(className, "UserData / 5. gameRecordLoss : " + userDataArrayList.get(index).getGameRecordLoss());                         // 5. game record loss
                Log.d(className, "UserData / 6. recentGameBilliardCount : " + userDataArrayList.get(index).getRecentGameBilliardCount());       // 6. recent game billiard count
                Log.d(className, "UserData / 7. totalPlayTime : " + userDataArrayList.get(index).getTotalPlayTime());                           // 7. total play time
                Log.d(className, "UserData / 8. totalCost : " + userDataArrayList.get(index).getTotalCost());                                   // 8. total cost

            }
            Log.d(className, "=================================== userDataArrayList : END ===================================");

        }

    } // End of method [displayToUserData]


    // ================================================== FriendData ==================================================

    /**
     * [method] FriendData 의 정보를 받아서, 모든 내용을 Log 로 출력한다.
     */
    public static void displayToFriendData(String className, FriendData friendData) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            // [check 2] : FriendData 가 있다.
            if (friendData != null) {

                Log.d(className, "FriendData / 0. id : " + friendData.getId());                                             // 0. id
                Log.d(className, "FriendData / 1. userId : " + friendData.getUserId());                                     // 1. user id
                Log.d(className, "FriendData / 2. name : " + friendData.getName());                                         // 2. name
                Log.d(className, "FriendData / 3. gameRecordWin : " + friendData.getGameRecordWin());                       // 3. game record win
                Log.d(className, "FriendData / 4. gameRecordLoss : " + friendData.getGameRecordLoss());                     // 4. game record loss
                Log.d(className, "FriendData / 5. recentGameBilliardCount : " + friendData.getRecentGameBilliardCount());   // 5. recent game billiard count
                Log.d(className, "FriendData / 6. totalPlayTime : " + friendData.getTotalPlayTime());                       // 6. total play time
                Log.d(className, "FriendData / 7. totalCost : " + friendData.getTotalCost());                               // 7. total cost

            } else {
                Log.d(className, "FriendData 데이터가 없습니다.");
            } // [check 2]

        } // [check 1]

    } // End of method [displayToFriendData]


    /**
     * friendDataArrayList
     *
     * @param className
     * @param friendDataArrayList
     */
    public static void displayToFriendData(String className, ArrayList<FriendData> friendDataArrayList) {

        if (DISPLAY_POWER == Display.ON) {

            Log.d(className, "=================================== friendDataArrayList : START ===================================");
            for (int index = 0; index < friendDataArrayList.size(); index++) {

                Log.d(className, "------>> " + index + " <<------");
                Log.d(className, "FriendData / 0. id : " + friendDataArrayList.get(index).getId());                                              // 0. id
                Log.d(className, "FriendData / 1. userId : " + friendDataArrayList.get(index).getUserId());                                      // 1. user id
                Log.d(className, "FriendData / 2. name : " + friendDataArrayList.get(index).getName());                                          // 2. name
                Log.d(className, "FriendData / 3. gameRecordWin : " + friendDataArrayList.get(index).getGameRecordWin());                        // 3. game record win
                Log.d(className, "FriendData / 4. gameRecordLoss : " + friendDataArrayList.get(index).getGameRecordLoss());                      // 4. game record loss
                Log.d(className, "FriendData / 5. recentGameBilliardCount : " + friendDataArrayList.get(index).getRecentGameBilliardCount());    // 5. recent game billiard count
                Log.d(className, "FriendData / 6. totalPlayTime : " + friendDataArrayList.get(index).getTotalPlayTime());                        // 6. total play time
                Log.d(className, "FriendData / 7. totalCost : " + friendDataArrayList.get(index).getTotalCost());                                // 7. total cost
            }
            Log.d(className, "=================================== friendDataArrayList : END ===================================");
        }
    } // End of method [displayToFriendData]


    // ================================================== BilliardData ==================================================

    /**
     * billiardData
     *
     * @param className
     * @param billiardData
     */
    public static void displayToBilliardData(String className, BilliardData billiardData) {

        // [check 1] : Log 를 출력해도 된다.
        if (DISPLAY_POWER == Display.ON) {

            // [check 2] : BilliardData 가 있다.
            if (billiardData != null) {

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
                Log.d(className, "billiardData 데이터가 없습니다.");
            } // [check 2]

        } // [check 1]

    } // End of method [displayToBilliardData]


    /**
     * billiardDataArrayList
     *
     * @param className
     * @param billiardDataArrayList
     */
    public static void displayToBilliardData(String className, ArrayList<BilliardData> billiardDataArrayList) {

        if (DISPLAY_POWER == Display.ON) {

            Log.d(className, "=================================== billiardDataArrayList : START ===================================");
            for (int index = 0; index < billiardDataArrayList.size(); index++) {

                Log.d(className, "------>> " + index + " <<------");
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
            Log.d(className, "=================================== billiardDataArrayList : END ===================================");

        }
    }


    // ================================================== PlayerData ==================================================

    /**
     * playerDataArrayList
     *
     * @param className
     * @param playerDataArrayList
     */
    public static void displayToPlayerData(String className, ArrayList<PlayerData> playerDataArrayList) {

        if (DISPLAY_POWER == Display.ON) {

            Log.d(className, "=================================== playerDataArrayList : START ===================================");
            for (int index = 0; index < playerDataArrayList.size(); index++) {

                Log.d(className, "------>> " + index + " <<------");

                Log.d(className, "PlayerData / 0. count : " + playerDataArrayList.get(index).getCount());                       // 0. id
                Log.d(className, "PlayerData / 1. billiardCount : " + playerDataArrayList.get(index).getBilliardCount());       // 1. billiard count
                Log.d(className, "PlayerData / 2. playerId : " + playerDataArrayList.get(index).getPlayerId());                 // 2. player id
                Log.d(className, "PlayerData / 3. playerName : " + playerDataArrayList.get(index).getPlayerName());             // 3. player name
                Log.d(className, "PlayerData / 4. targetScore : " + playerDataArrayList.get(index).getTargetScore());           // 4. target score
                Log.d(className, "PlayerData / 5. score : " + playerDataArrayList.get(index).getScore());                       // 5. score

            }
            Log.d(className, "=================================== playerDataArrayList : END ===================================");
        }

    } // End of method [displayToPlayerData]

}
