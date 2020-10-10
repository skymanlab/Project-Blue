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
     * @param intent    해당 페이지에서 생성한 Intent
     * @param userData  유저 정보
     */
    public static void setIntentOfUserData (Intent intent, UserData userData) {
        
        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("userData", userData);
        
    } // End of method [setIntentOfUserData]


    /**
     * [method] userData 의 id 를 Intent 에 포함하기 / userId
     *
     * @param intent        해당 페이지에서 생성한 Intent
     * @param userData
     */
    public static void setIntentOfUserId (Intent intent, UserData userData) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("userId", userData.getId());

    } // End of method [setIntentOfUserData]


    /**
     * [method] FriendData 를 Intent 에 포함하기 / player
     *
     * @param intent    해당 페이지에서 생성한 Intent
     * @param player    선택한 player 의 데이터
     */
    public static void setIntentOfPlayer (Intent intent, FriendData player) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("player", player);

    } // End of method [setIntentOfUserData]


    /**
     * [method] ArrayList<FriendData> 를 Intent 에 포함하기 / playerList
     *
     * @param intent        해당 페이지에서 생성한 Intent
     * @param playerList    선택된 모든 player 를 담은 ArrayList
     */
    public static void setIntentOfPlayerList (Intent intent, ArrayList<FriendData> playerList) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("playerList", playerList);

    } // End of method [setIntentOfPlayerList]


    /**
     * [method] ArrayList<FriendData> 에서 선택된 FriendData 의 id 를 Intent 에 포함하기 / selectedPlayerIndex
     *
     * @param intent        해당 페이지에서 생성한 Intent
     * @param index      선택된 모든 player 의 데이터가 들어있는 FriendDataArrayList 의 index 번호
     */
    public static void setIntentOfSelectedPlayerIndex (Intent intent, int index) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("selectedPlayerIndex", index);

    } // End of method [setIntentOfUserData]


    /**
     * [method] pageNumber 를 Intent 에 포함히기 / pageNumber
     *
     * @param intent        해당 페이지에서 생성한 Intent
     * @param pageNumber    UserManagerActivity 의 Fragment 페이지 번호
     */
    public static void setIntentOfPageNumber(Intent intent, int pageNumber) {

        // [lv/C]Intent : UserManagerActivity 의 어떤 Fragment 를 띄울지 그 페이지 를 넣어 보낸다.
        intent.putExtra("pageNumber", pageNumber);

    } // End of method [setIntentOfPageNumber]


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
    public static FriendData getPlayerInIntent(Intent player) {

        // [lv/C]FriendData : Intent 에서 "player" 로 FriendData 가져오기
        FriendData friendData = (FriendData) player.getSerializableExtra("player");
        DeveloperManager.displayToFriendData("[Se]_SessionManager", friendData);

        return friendData;

    } // End of method [getPlayerInIntent]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "playerList" 값으로 가져오기
     *
     * @return Intent 에서 가져온 FriendData 값
     */
    public static
    ArrayList<FriendData> getPlayerListInIntent(Intent player) {

        // [lv/C]ArrayList<FriendData> : Intent 에서 "playerList" 로 모든 FriendData 가져오기
        ArrayList<FriendData> playerList = (ArrayList<FriendData>) player.getSerializableExtra("playerList");
        DeveloperManager.displayToFriendData("[Se]_SessionManager", playerList);

        return playerList;

    } // End of method [getPlayerListInIntent]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "selectedPlayerIndex" 값으로 가져오기
     *
     * @return Intent 에서 가져온 FriendData 값
     */
    public static int getSelectedPlayerIndexInIntent(Intent player) {

        // [lv/i]selectedPosition : Intent 에서 "selectedPosition" 로 UserData 가져오기
        int selectedPlayerIndex = player.getIntExtra("selectedPlayerIndex", -1);

        return selectedPlayerIndex;

    } // End of method [getSelectedPlayerIndexInIntent]


    /**
     * [method] [pageNumber] 전 단계에서 선택한 UserManagerActivity 의 Fragment 의 페이지 번호를 Intent 를 통해 "pageNumber" 값으로 가져오기
     *
     * @return Intent 에서 가져온 FriendData 값
     */
    public static int getPageNumberInIntent(Intent player) {

        // [lv/i]selectedPosition : Intent 에서 "pageNumber" 로 pageNumber 가져오기
        int pageNumber = player.getIntExtra("pageNumber", -1);

        return pageNumber;

    } // End of method [getPageNumberInIntent]

}
