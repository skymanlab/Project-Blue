package com.skyman.billiarddata.management.projectblue.data;

import android.content.Intent;
import android.widget.ArrayAdapter;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.user.data.UserData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SessionManager {

    // constant
    private static final String CLASS_NAME = SessionManager.class.getSimpleName();

    // singleton : 해당 어플에서 공유해서 사용하기 위해서
    private static SessionManager INSTANCE = null;

    // constant :
    public static final String USER_DATA = "userData";
    public static final String BILLIARD_DATA = "billiardData";
    public static final String PARTICIPATED_FRIEND_LIST_IN_GAME = "participatedFriendListInGame";   // 게임에 참여한 친구 목록
    public static final String PLAYER_DATA_ARRAY_LIST = "playerDataArrayList";                      // 게임에 참여한 플레이어 ( 나 + 게임에_참여한_친구 ) 목록
    public static final String PAGE_NUMBER = "pageNumber";

    // constructor
    private SessionManager() {

    }

    // get instance
    public static SessionManager getInstance() {

        // 아직 생성되지 않았으면
        if (INSTANCE == null) {
            INSTANCE = new SessionManager();
        }

        // 생성되었으면
        return INSTANCE;
    }

    // ===================================================== userData =====================================================

    /**
     * userData : setter
     *
     * @param intent   해당 페이지에서 생성한 Intent
     * @param userData 유저 정보
     */
    public static void setUserDataFromIntent(Intent intent, UserData userData) {

        intent.putExtra(USER_DATA, userData);

    } // End of method [setIntentOfUserData]


    /**
     * userData : getter
     *
     * @param intent session 정보가 담긴 intent
     * @return Intent 에서 가져온 FriendData 값
     */
    public static UserData getUserDataFromIntent(Intent intent) {

        UserData userData = (UserData) intent.getSerializableExtra(USER_DATA);

        DeveloperManager.displayLog(
                CLASS_NAME,
                "=====>>> SessionManager <<<===="
        );
        DeveloperManager.displayToUserData(
                CLASS_NAME,
                userData
        );

        return userData;
    } // End of method [getUserDataInIntent]


    // ===================================================== billiardData =====================================================

    /**
     * billiardData : setter
     *
     * @param intent
     * @param billiardData
     */
    public static void setBilliardDataFromIntent(Intent intent, BilliardData billiardData) {

        intent.putExtra(BILLIARD_DATA, billiardData);

    } // End of method [setIntentOfBilliardData]


    /**
     * billiardData : getter
     *
     * @param intent
     * @return
     */
    public static BilliardData getBilliardDataFromIntent(Intent intent) {

        BilliardData billiardData = (BilliardData) intent.getSerializableExtra(BILLIARD_DATA);

        DeveloperManager.displayLog(
                CLASS_NAME,
                "=====>>> SessionManager <<<===="
        );
        DeveloperManager.displayToBilliardData(
                CLASS_NAME,
                billiardData
        );

        return billiardData;
    } // End of method [getBilliardDataInIntent]


    // ===================================================== participated friend list in game =====================================================

    /**
     * participatedFriendListInGame : setter
     *
     * @param intent
     * @param participatedFriendListInGame
     */
    public static void setParticipatedFriendListInGameFromIntent(Intent intent,
                                                                 ArrayList<FriendData> participatedFriendListInGame) {

        intent.putExtra(PARTICIPATED_FRIEND_LIST_IN_GAME, participatedFriendListInGame);

    } // End of method [setIntentOfParticipatedFriendListInGame]


    /**
     * participatedFriendListInGame : getter
     *
     * @param intent
     * @return
     */
    public static ArrayList<FriendData> getParticipatedFriendListInGameFromIntent(Intent intent) {

        ArrayList<FriendData> participatedFriendListInGame = (ArrayList<FriendData>) intent.getSerializableExtra(PARTICIPATED_FRIEND_LIST_IN_GAME);

        DeveloperManager.displayLog(
                CLASS_NAME,
                "=====>>> SessionManager <<<===="
        );
        DeveloperManager.displayToFriendData(
                CLASS_NAME,
                participatedFriendListInGame
        );

        return participatedFriendListInGame;
    } // End of method [setIntentOfParticipatedFriendListInGame]


    // ===================================================== playerDataArrayList =====================================================

    /**
     * playerDataArrayList : setter
     *
     * @param intent
     * @param playerDataArrayList
     */
    public static void setPlayerDataArrayListFromIntent(Intent intent, ArrayList<PlayerData> playerDataArrayList) {
        intent.putExtra(PLAYER_DATA_ARRAY_LIST, playerDataArrayList);
    }


    /**
     * playerDataArrayList : getter
     *
     * @param intent
     * @return
     */
    public static ArrayList<PlayerData> getPlayerDataArrayListFromIntent(Intent intent) {
        ArrayList<PlayerData> playerDataArrayList = (ArrayList<PlayerData>) intent.getSerializableExtra(PLAYER_DATA_ARRAY_LIST);

        DeveloperManager.displayLog(
                CLASS_NAME,
                "=====>>> SessionManager <<<===="
        );
        DeveloperManager.displayToPlayerData(
                CLASS_NAME,
                playerDataArrayList
        );

        return playerDataArrayList;
    }


    // ===================================================== pageNumber =====================================================

    /**
     * pageNumber : setter
     *
     * @param intent
     * @param pageNumber
     */
    public static void setPageNumberFromIntent(Intent intent, int pageNumber) {

        intent.putExtra(PAGE_NUMBER, pageNumber);

    } // End of method [setIntentOfPageNumber]


    /**
     * pageNumber : getter
     *
     * @param intent
     * @return
     */
    public static int getPageNumberFromIntent(Intent intent) {

        int pageNumber = intent.getIntExtra(PAGE_NUMBER, -1);

        DeveloperManager.displayLog(
                CLASS_NAME,
                "=====>>> SessionManager <<<===="
        );
        DeveloperManager.displayLog(
                CLASS_NAME,
                "pageNumber : " + pageNumber
        );

        return pageNumber;
    } // End of method [getPageNumberInIntent]


}
