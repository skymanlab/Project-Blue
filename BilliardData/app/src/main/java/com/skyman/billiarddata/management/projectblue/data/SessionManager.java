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

    /**
     * [method] [user] userData 를 Intent 에 포함하기 / userData
     *
     * @param intent   해당 페이지에서 생성한 Intent
     * @param userData 유저 정보
     */
    public static void setIntentOfUserData(Intent intent, UserData userData) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("userData", userData);

    } // End of method [setIntentOfUserData]


    /**
     * [method] [user] userData 의 id 를 Intent 에 포함하기 / userId
     *
     * @param intent   해당 페이지에서 생성한 Intent
     * @param userData
     */
    public static void setIntentOfUserId(Intent intent, UserData userData) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("userId", userData.getId());

    } // End of method [setIntentOfUserData]


    /**
     * [method] [friend] FriendData 를 Intent 에 포함하기 / friendPlayer
     *
     * @param intent 해당 페이지에서 생성한 Intent
     * @param friendPlayer 선택한 player 중 friend 에 해당하는 데이터
     */
    public static void setIntentOfFriendPlayer(Intent intent, FriendData friendPlayer) {

        // [lv/C]Intent : userData 를 셋팅하기
        intent.putExtra("friendPlayer", friendPlayer);

    } // End of method [setIntentOfFriendPlayer]


    /**
     * [method] [friend] ArrayList<FriendData> 를 Intent 에 포함하기 / friendPlayerList
     *
     * @param intent     해당 페이지에서 생성한 Intent
     * @param friendPlayerList 선택된 player 중 friend 에 해당하는 데이터를 모두 담은 배열
     */
    public static void setIntentOfFriendPlayerList(Intent intent, ArrayList<FriendData> friendPlayerList) {

        // [lv/C]Intent : playerList 를 intent 에 추가하기
        intent.putExtra("friendPlayerList", friendPlayerList);

    } // End of method [setIntentOfFriendPlayerList]


    /**
     * [method] [friend] ArrayList<FriendData> 에서 선택된 FriendData 의 id 를 Intent 에 포함하기 / selectedFriendPlayerIndex
     *
     * @param intent              해당 페이지에서 생성한 Intent
     * @param selectedFriendPlayerIndex 선택된 player 중 friend 의 데이터가 들어있는 FriendDataArrayList 에서 특정 friend 의 index 번호
     */
    public static void setIntentOfSelectedPlayerIndex(Intent intent, int selectedFriendPlayerIndex) {

        // [lv/C]Intent : selectedPlayerIndex 를 intent 에 추가하기
        intent.putExtra("selectedFriendPlayerIndex", selectedFriendPlayerIndex);

    } // End of method [setIntentOfUserData]


    /**
     * [method] pageNumber 를 Intent 에 포함히기 / pageNumber
     *
     * @param intent     해당 페이지에서 생성한 Intent
     * @param pageNumber UserManagerActivity 의 Fragment 페이지 번호
     */
    public static void setIntentOfPageNumber(Intent intent, int pageNumber) {

        // [lv/C]Intent : UserManagerActivity 의 어떤 Fragment 를 띄울지 그 페이지 를 넣어 보낸다.
        intent.putExtra("pageNumber", pageNumber);

    } // End of method [setIntentOfPageNumber]


    /**
     * [method] [billiard] billiardData 를 Intent 에 포함하기 / billiardData
     *
     * @param intent
     * @param billiardData 선택된 게임 데이터가 담긴 billiardData
     */
    public static void setIntentOfBilliardData(Intent intent, BilliardData billiardData) {

        // [lv/C]Intent : billiardData 를 intent 에 추가하기
        intent.putExtra("billiardData", billiardData);

    } // End of method [setIntentOfBilliardData]


    /**
     * [method] [player] 게임에 참가한 player 가 등록되어있는 playerDataArrayList 를 Intent 에 포함하기 / playerList
     *
     */
    public static void setIntentOfPlayerList (Intent intent, ArrayList<PlayerData> playerDataArrayList) {

        // [lv/C]Intent : 게임한 참가한 player 가 등록되어있는 playerDateArrayList 를 Intent 에 추가하기
        intent.putExtra("playerList", playerDataArrayList);

    } // End of method []


    // ============================================================================================================================================


    /**
     * [method] [user] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "userData" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static UserData getUserDataInIntent(Intent intent) {

        final String METHOD_NAME = "[getUserDataInIntent] ";

        // [lv/C]UserData : Intent 에서 "user" 로 UserData 가져오기
        UserData userData = (UserData) intent.getSerializableExtra("userData");

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : userData 확인 **********************************");
        DeveloperManager.displayToUserData(CLASS_NAME_LOG, userData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******************************************************************");

        return userData;

    } // End of method [getUserDataInIntent]


    /**
     * [method] [user] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "userId" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static long getUserIdInIntent(Intent intent) {

        final String METHOD_NAME = "[getUserIdInIntent] ";

        // [lv/l]userId : Intent 에서 "userId" 로 UserData 가져오기
        long userId = intent.getIntExtra("userId", -1);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : userId 확인 **********************************");
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userId = " + userId);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "****************************************************************");

        return userId;

    } // End of method [getUserIdInIntent]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "friendPlayer" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static FriendData getFriendPlayerInIntent(Intent intent) {

        final String METHOD_NAME = "[getFriendPlayerInIntent] ";

        // [lv/C]FriendData : Intent 에서 "friendPlayer" 로 FriendData 가져오기
        FriendData friendPlayerData = (FriendData) intent.getSerializableExtra("friendPlayer");

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : friendPlayer 확인 **********************************");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendPlayerData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "**********************************************************************");

        return friendPlayerData;

    } // End of method [getFriendPlayerInIntent]


    /**
     * [method] [friend] 전 단계에서 player 중 선택한 친구의 데이터가 담긴 FriendData 배열 를 Intent 를 통해 "friendPlayerList" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static ArrayList<FriendData> getFriendPlayerListInIntent(Intent intent) {

        final String METHOD_NAME = "[getFriendPlayerListInIntent] ";

        // [lv/C]ArrayList<FriendData> : Intent 에서 "friendPlayerList" 로 모든 FriendData 가져오기
        ArrayList<FriendData> friendPlayerList = (ArrayList<FriendData>) intent.getSerializableExtra("friendPlayerList");

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : friendPlayerList 확인 **********************************");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendPlayerList);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "**************************************************************************");

        return friendPlayerList;

    } // End of method [getFriendPlayerListInIntent]


    /**
     * [method] [friend] 전 단계에서 선택한 친구의 데이터가 담긴 FriendData 를 Intent 를 통해 "selectedFriendPlayerIndex" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static int getSelectedFriendPlayerIndexInIntent(Intent intent) {

        final String METHOD_NAME = "[getSelectedFriendPlayerIndexInIntent] ";

        // [lv/i]selectedPosition : Intent 에서 "selectedFriendPlayerIndex" 로 friendPlayerList 중 선택된 friend 에 해당하는 index
        int selectedFriendPlayerIndex = intent.getIntExtra("selectedFriendPlayerIndex", -1);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : selectedFriendPlayerIndex 확인 **********************************");
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "선택한 친구의 index = " + selectedFriendPlayerIndex);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "***********************************************************************************");

        return selectedFriendPlayerIndex;

    } // End of method [getSelectedFriendPlayerIndexInIntent]


    /**
     * [method] [pageNumber] 전 단계에서 선택한 UserManagerActivity 의 Fragment 의 페이지 번호를 Intent 를 통해 "pageNumber" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static int getPageNumberInIntent(Intent intent) {

        final String METHOD_NAME = "[getPageNumberInIntent] ";

        // [lv/i]selectedPosition : Intent 에서 "pageNumber" 로 pageNumber 가져오기
        int pageNumber = intent.getIntExtra("pageNumber", -1);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : pageNumber 확인 **********************************");
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "움직여야 하는 페이지의 번호 = " + pageNumber);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "********************************************************************");

        return pageNumber;

    } // End of method [getPageNumberInIntent]


    /**
     * [method] [billiard] 전 단계에서 선택한 게임 데이터가 담긴  billiardData 를 Intent 를 통해 "billiardData" 값으로 가져오기
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 BilliardData 값
     */
    public static BilliardData getBilliardDataInIntent(Intent intent) {

        final String METHOD_NAME = "[getBilliardDataInIntent] ";

        // [lv/C]BilliardData : Intent 에서 "billiardData" 로 billiardData 가져오기
        BilliardData billiardData = (BilliardData) intent.getSerializableExtra("billiardData");

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : billiardData 확인 **********************************");
        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, billiardData);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "**********************************************************************");

        return billiardData;
    } // End of method [getBilliardDataInIntent]


    /**
     * [method] [player] 전 단계에서 등록된 player 데이터가 담긴 PlayerData 배열을 Intent 를 통해 "playerList" 값으로 가져오기
     *
     */
    public static ArrayList<PlayerData> getPlayerListInIntent(Intent intent) {

        final String METHOD_NAME = "[getPlayerListInIntent] ";

        // [lv/C]ArrayList<PlayerData> : Intent 에서 "playerList" 로 playerDataArrayList 가져오기
        ArrayList<PlayerData> playerDataArrayList = (ArrayList<PlayerData>) intent.getSerializableExtra("playerList");

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "******* Session : playerList 확인 **********************************");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "********************************************************************");

        return  playerDataArrayList;
    } // End of method [getPlayerListInIntent]
}
