package com.skyman.billiarddata.management.projectblue.data;

import android.content.Intent;
import android.widget.ArrayAdapter;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

public class SessionManager {

    // constant
    private static final String CLASS_NAME_LOG = "[Se]_SessionManager";

    public static final String USER_DATA = "userData";
    public static final String USER_ID = "userId";
    public static final String FRIEND_PLAYER = "friendPlayer";
    public static final String FRIEND_PLAYER_LIST = "friendPlayerList";
    public static final String SELECTED_FRIEND_PLAYER_INDEX = "selectedFriendPlayerIndex";
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String BILLIARD_DATA = "billiardData";
    public static final String PLAYER_LIST = "playerList";


    // ===================================================== userData =====================================================

    /**
     * [method] [user] userData 를 Intent 에 포함하기 / userData
     *
     * @param intent   해당 페이지에서 생성한 Intent
     * @param userData 유저 정보
     */
    public static void setIntentOfUserData(Intent intent, UserData userData) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra(USER_DATA, userData);

    } // End of method [setIntentOfUserData]


    /**
     * [method] [user] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "userData" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static UserData getUserDataInIntent(Intent intent) {

        final String METHOD_NAME = "[getUserDataInIntent] ";

        // [lv/C]UserData : Intent 에서 "user" 로 UserData 가져오기
        UserData userData = (UserData) intent.getSerializableExtra(USER_DATA);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : userData 확인 **********************************");
        DeveloperManager.displayToUserData(CLASS_NAME_LOG, userData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******************************************************************");

        return userData;

    } // End of method [getUserDataInIntent]


    // ===================================================== userId =====================================================

    /**
     * [method] [user] userData 의 id 를 Intent 에 포함하기 / userId
     *
     * @param intent   해당 페이지에서 생성한 Intent
     * @param userData
     */
    public static void setIntentOfUserId(Intent intent, UserData userData) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra(USER_ID, userData.getId());

    } // End of method [setIntentOfUserData]


    /**
     * [method] [user] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "userId" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static long getUserIdInIntent(Intent intent) {

        final String METHOD_NAME = "[getUserIdInIntent] ";

        // [lv/l]userId : Intent 에서 "userId" 로 UserData 가져오기
        long userId = intent.getIntExtra(USER_ID, -1);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : userId 확인 **********************************");
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userId = " + userId);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "****************************************************************");

        return userId;

    } // End of method [getUserIdInIntent]

    // ===================================================== friendPlayer =====================================================

    /**
     * [method] [friend] FriendData 를 Intent 에 포함하기 / friendPlayer
     *
     * @param intent       해당 페이지에서 생성한 Intent
     * @param friendPlayer 선택한 player 중 friend 에 해당하는 데이터
     */
    public static void setIntentOfFriendPlayer(Intent intent, FriendData friendPlayer) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra(FRIEND_PLAYER, friendPlayer);

    } // End of method [setIntentOfFriendPlayer]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "friendPlayer" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static FriendData getFriendPlayerInIntent(Intent intent) {

        final String METHOD_NAME = "[getFriendPlayerInIntent] ";

        // [lv/C]FriendData : Intent 에서 "friendPlayer" 로 FriendData 가져오기
        FriendData friendPlayerData = (FriendData) intent.getSerializableExtra(FRIEND_PLAYER);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : friendPlayer 확인 **********************************");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendPlayerData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "**********************************************************************");

        return friendPlayerData;

    } // End of method [getFriendPlayerInIntent]


    // ===================================================== friendPlayerList =====================================================

    /**
     * [method] [friend] ArrayList<FriendData> 를 Intent 에 포함하기 / friendPlayerList
     *
     * @param intent           해당 페이지에서 생성한 Intent
     * @param friendPlayerList 선택된 player 중 friend 에 해당하는 데이터를 모두 담은 배열
     */
    public static void setIntentOfFriendPlayerList(Intent intent, ArrayList<FriendData> friendPlayerList) {

        // [lv/C]Intent : playerList 를 intent 에 추가하기
        intent.putExtra(FRIEND_PLAYER_LIST, friendPlayerList);

    } // End of method [setIntentOfFriendPlayerList]


    /**
     * [method] [friend] 전 단계에서 player 중 선택한 친구의 데이터가 담긴 FriendData 배열 를 Intent 를 통해 "friendPlayerList" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static ArrayList<FriendData> getFriendPlayerListInIntent(Intent intent) {

        final String METHOD_NAME = "[getFriendPlayerListInIntent] ";

        // [lv/C]ArrayList<FriendData> : Intent 에서 "friendPlayerList" 로 모든 FriendData 가져오기
        ArrayList<FriendData> friendPlayerList = (ArrayList<FriendData>) intent.getSerializableExtra(FRIEND_PLAYER_LIST);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : friendPlayerList 확인 **********************************");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendPlayerList);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "**************************************************************************");

        return friendPlayerList;

    } // End of method [getFriendPlayerListInIntent]


    // ===================================================== selectedFriendPlayerIndex =====================================================

    /**
     * [method] [friend] ArrayList<FriendData> 에서 선택된 FriendData 의 id 를 Intent 에 포함하기 / selectedFriendPlayerIndex
     *
     * @param intent                    해당 페이지에서 생성한 Intent
     * @param selectedFriendPlayerIndex 선택된 player 중 friend 의 데이터가 들어있는 FriendDataArrayList 에서 특정 friend 의 index 번호
     */
    public static void setIntentOfSelectedPlayerIndex(Intent intent, int selectedFriendPlayerIndex) {

        // [lv/C]Intent : selectedPlayerIndex 를 intent 에 추가하기
        intent.putExtra(SELECTED_FRIEND_PLAYER_INDEX, selectedFriendPlayerIndex);

    } // End of method [setIntentOfUserData]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "selectedFriendPlayerIndex" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static int getSelectedFriendPlayerIndexInIntent(Intent intent) {

        final String METHOD_NAME = "[getSelectedFriendPlayerIndexInIntent] ";

        // [lv/i]selectedPosition : Intent 에서 "selectedFriendPlayerIndex" 로 friendPlayerList 중 선택된 friend 에 해당하는 index
        int selectedFriendPlayerIndex = intent.getIntExtra(SELECTED_FRIEND_PLAYER_INDEX, -1);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : selectedFriendPlayerIndex 확인 **********************************");
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "선택한 친구의 index = " + selectedFriendPlayerIndex);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "***********************************************************************************");

        return selectedFriendPlayerIndex;

    } // End of method [getSelectedFriendPlayerIndexInIntent]


    // ===================================================== pageNumber =====================================================

    /**
     * [method] pageNumber 를 Intent 에 포함히기 / pageNumber
     *
     * @param intent     해당 페이지에서 생성한 Intent
     * @param pageNumber UserManagerActivity 의 Fragment 페이지 번호
     */
    public static void setIntentOfPageNumber(Intent intent, int pageNumber) {

        // [lv/C]Intent : UserManagerActivity 의 어떤 Fragment 를 띄울지 그 페이지 를 넣어 보낸다.
        intent.putExtra(PAGE_NUMBER, pageNumber);

    } // End of method [setIntentOfPageNumber]


    /**
     * [method] [pageNumber] 전 단계에서 선택한 UserManagerActivity 의 Fragment 의 페이지 번호를 Intent 를 통해 "pageNumber" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static int getPageNumberInIntent(Intent intent) {

        final String METHOD_NAME = "[getPageNumberInIntent] ";

        // [lv/i]selectedPosition : Intent 에서 "pageNumber" 로 pageNumber 가져오기
        int pageNumber = intent.getIntExtra(PAGE_NUMBER, -1);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : pageNumber 확인 **********************************");
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "움직여야 하는 페이지의 번호 = " + pageNumber);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "********************************************************************");

        return pageNumber;

    } // End of method [getPageNumberInIntent]


    // ===================================================== billiardData =====================================================

    /**
     * [method] [billiard] billiardData 를 Intent 에 포함하기 / billiardData
     *
     * @param intent
     * @param billiardData 선택된 게임 데이터가 담긴 billiardData
     */
    public static void setIntentOfBilliardData(Intent intent, BilliardData billiardData) {

        // [lv/C]Intent : billiardData 를 intent 에 추가하기
        intent.putExtra(BILLIARD_DATA, billiardData);

    } // End of method [setIntentOfBilliardData]


    /**
     * [method] [billiard] 전 단계에서 선택한 게임 데이터가 담긴  billiardData 를 Intent 를 통해 "billiardData" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 BilliardData 값
     */
    public static BilliardData getBilliardDataInIntent(Intent intent) {

        final String METHOD_NAME = "[getBilliardDataInIntent] ";

        // [lv/C]BilliardData : Intent 에서 "billiardData" 로 billiardData 가져오기
        BilliardData billiardData = (BilliardData) intent.getSerializableExtra(BILLIARD_DATA);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : billiardData 확인 **********************************");
        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, billiardData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "**********************************************************************");

        return billiardData;
    } // End of method [getBilliardDataInIntent]


    // ===================================================== playerList =====================================================

    /**
     * [method] [player] 게임에 참가한 player 가 등록되어있는 playerDataArrayList 를 Intent 에 포함하기 / playerList
     */
    public static void setIntentOfPlayerList(Intent intent, ArrayList<PlayerData> playerDataArrayList) {

        // [lv/C]Intent : 게임한 참가한 player 가 등록되어있는 playerDateArrayList 를 Intent 에 추가하기
        intent.putExtra(PLAYER_LIST, playerDataArrayList);

    } // End of method [setIntentOfPlayerList]


    /**
     * [method] [player] 전 단계에서 등록된 player 데이터가 담긴 PlayerData 배열을 Intent 를 통해 "playerList" 값으로 가져오기
     */
    public static ArrayList<PlayerData> getPlayerListInIntent(Intent intent) {

        final String METHOD_NAME = "[getPlayerListInIntent] ";

        // [lv/C]ArrayList<PlayerData> : Intent 에서 "playerList" 로 playerDataArrayList 가져오기
        ArrayList<PlayerData> playerDataArrayList = (ArrayList<PlayerData>) intent.getSerializableExtra(PLAYER_LIST);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : playerList 확인 **********************************");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "********************************************************************");

        return playerDataArrayList;
    } // End of method [getPlayerListInIntent]
}
