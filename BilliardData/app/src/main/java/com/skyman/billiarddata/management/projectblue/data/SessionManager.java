package com.skyman.billiarddata.management.projectblue.data;

import android.content.Intent;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

public class SessionManager {

    /**
     * [method] userData 를 Intent 에 포함하기 / userData
     *
     */
    public static void setIntentOfUserData (Intent intent, UserData userData) {
        
        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("userData", userData);
        
    } // End of method [setIntentOfUserData]


    /**
     * [method] userData 의 id 를 Intent 에 포함하기 / userId
     *
     */
    public static void setIntentOfUserId (Intent intent, UserData userData) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("userId", userData.getId());

    } // End of method [setIntentOfUserData]


    /**
     * [method] FriendData 를 Intent 에 포함하기 / player
     *
     */
    public static void setIntentOfPlayer (Intent intent, FriendData friendData) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("player", friendData);

    } // End of method [setIntentOfUserData]


    /**
     * [method] ArrayList<FriendData> 를 Intent 에 포함하기 / playerList
     *
     */
    public static void setIntentOfPlayerList (Intent intent, ArrayList<FriendData> friendDataArrayList) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("playerList", friendDataArrayList);

    } // End of method [setIntentOfPlayerList]


    /**
     * [method] ArrayList<FriendData> 에서 선택된 FriendData 의 id 를 Intent 에 포함하기 / selectedPlayerPosition
     *
     */
    public static void setIntentOfSelectedPlayerPosition (Intent intent, int position) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("selectedPlayerPosition", position);

    } // End of method [setIntentOfUserData]




    // ============================================================================================================================================


    /**
     * [method] [user] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "userData" 값으로 가져오기
     *
     * @return Intent 에서 가져온 FriendData 값
     */
    public static UserData getUserDataInIntent(Intent user) {

        // [lv/C]UserData : Intent 에서 "user" 로 UserData 가져오기
        UserData userData = (UserData) user.getSerializableExtra("userData");
        DeveloperManager.displayToUserData("[Se]_SessionManager", userData);

        return userData;

    } // End of method [getUserDataListInIntent]


    /**
     * [method] [user] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "userId" 값으로 가져오기
     *
     * @return Intent 에서 가져온 FriendData 값
     */
    public static long getUserIdInIntent(Intent user) {

        // [lv/l]userId : Intent 에서 "userId" 로 UserData 가져오기
        long userId = user.getIntExtra("userId", -1);

        return userId;

    } // End of method [getUserDataListInIntent]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "player" 값으로 가져오기
     *
     * @return Intent 에서 가져온 FriendData 값
     */
    public static FriendData getFriendDataInIntent(Intent player) {

        // [lv/C]FriendData : Intent 에서 "player" 로 FriendData 가져오기
        FriendData friendData = (FriendData) player.getSerializableExtra("player");
        DeveloperManager.displayToFriendData("[Se]_SessionManager", friendData);

        return friendData;

    } // End of method [getFriendDataToIntent]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "playerList" 값으로 가져오기
     *
     * @return Intent 에서 가져온 FriendData 값
     */
    public static
    ArrayList<FriendData> getFriendDataArrayListInIntent(Intent player) {

        // [lv/C]ArrayList<FriendData> : Intent 에서 "playerList" 로 모든 FriendData 가져오기
        ArrayList<FriendData> friendDataArrayList = (ArrayList<FriendData>) player.getSerializableExtra("playerList");
        DeveloperManager.displayToFriendData("[Se]_SessionManager", friendDataArrayList);

        return friendDataArrayList;

    } // End of method [getFriendDataToIntent]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "selectedPlayerPosition" 값으로 가져오기
     *
     * @return Intent 에서 가져온 FriendData 값
     */
    public static int getSelectedPositionInIntent(Intent player) {

        // [lv/i]selectedPosition : Intent 에서 "selectedPosition" 로 UserData 가져오기
        int selectedPosition = player.getIntExtra("selectedPlayerPosition", -1);

        return selectedPosition;

    } // End of method [getUserDataListInIntent]


}
