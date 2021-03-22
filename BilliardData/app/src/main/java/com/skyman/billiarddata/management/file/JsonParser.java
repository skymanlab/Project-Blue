package com.skyman.billiarddata.management.file;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.user.data.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    // constant
    private static final String CLASS_NAME_LOG = JsonParser.class.getSimpleName();

    // instance variable
    private String content;

    // instance variable
    private UserData userData;
    private ArrayList<FriendData> friendDataArrayList;
    private ArrayList<BilliardData> billiardDataArrayList;
    private ArrayList<PlayerData> playerDataArrayList;

    // instance variable
    private boolean isCompletedParsing;      // true:json 형태로 변환 완료 , false:형식에 맞지 않아 데이터 변환 불가능

    // constructor
    public JsonParser(String content) {

        this.content = content;

        // ArrayList
        this.userData = new UserData();
        this.billiardDataArrayList = new ArrayList<>();
        this.playerDataArrayList = new ArrayList<>();
        this.friendDataArrayList = new ArrayList<>();

        this.isCompletedParsing = false;
    }

    // getter
    public boolean isCompletedParsing() {
        return isCompletedParsing;
    }

    public UserData getUserData() {
        return userData;
    }

    public ArrayList<BilliardData> getBilliardDataArrayList() {
        return billiardDataArrayList;
    }

    public ArrayList<PlayerData> getPlayerDataArrayList() {
        return playerDataArrayList;
    }

    public ArrayList<FriendData> getFriendDataArrayList() {
        return friendDataArrayList;
    }


    //
    public void parseContent() {

        try {

            // content : String -> JSONObject
            JSONObject jsonData = new JSONObject(content);

            DeveloperManager.displayLog(CLASS_NAME_LOG, "-------------------------------------------------->>>>>>>>>>>>>>>");
            DeveloperManager.displayLog(CLASS_NAME_LOG, "JSON : " + jsonData.toString());

            // user
            JSONObject userDataObject = jsonData.getJSONObject(UserData.class.getSimpleName());
            parseUserDataObject(userDataObject);

            // friend
            JSONArray friendDataArray = jsonData.getJSONArray(FriendData.class.getSimpleName());
            parseFriendDataArray(friendDataArray);

            // billiard
            JSONArray billiardDataArray = jsonData.getJSONArray(BilliardData.class.getSimpleName());
            parseBilliardDataArray(billiardDataArray);

            // player
            JSONArray playerDataArray = jsonData.getJSONArray(PlayerData.class.getSimpleName());
            parsePlayerDataArray(playerDataArray);

            this.isCompletedParsing = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ======================================================= userData =======================================================
    private void parseUserDataObject(JSONObject userDataObject) {

        // 0. id
        // 1. name
        // 2. target score
        // 3. speciality
        // 4. game record win
        // 5. game record loss
        // 6. recent game billiard count
        // 7. total play time
        // 8. total cost

        try {

            // jsonObject 의 내용으로 UserData 객체 생성
            userData.setId(userDataObject.getLong(UserData.ID));
            userData.setName(userDataObject.getString(UserData.NAME));
            userData.setTargetScore(userDataObject.getInt(UserData.TARGET_SCORE));
            userData.setSpeciality(userDataObject.getString(UserData.SPECIALITY));
            userData.setGameRecordWin(userDataObject.getInt(UserData.GAME_RECORD_WIN));
            userData.setGameRecordLoss(userDataObject.getInt(UserData.GAME_RECORD_LOSS));
            userData.setRecentGameBilliardCount(userDataObject.getLong(UserData.RECENT_GAME_BILLIARD_COUNT));
            userData.setTotalPlayTime(userDataObject.getInt(UserData.TOTAL_PLAY_TIME));
            userData.setTotalCost(userDataObject.getInt(UserData.TOTAL_COST));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ======================================================= billiardData =======================================================
    private void parseBilliardDataArray(JSONArray billiardDataArray) {

        // 0. count
        // 1. date
        // 2. game mode
        // 3. player count
        // 4. winner id
        // 5. winner name
        // 6. play time
        // 7. score
        // 8. cost

        try {
            for (int index = 0; index < billiardDataArray.length(); index++) {

                JSONObject jsonObject = billiardDataArray.getJSONObject(index);

                //
                BilliardData billiardData = new BilliardData();
                billiardData.setCount(jsonObject.getLong(BilliardData.COUNT));
                billiardData.setDate(jsonObject.getString(BilliardData.DATE));
                billiardData.setGameMode(jsonObject.getString(BilliardData.GAME_MODE));
                billiardData.setPlayerCount(jsonObject.getInt(BilliardData.PLAYER_COUNT));
                billiardData.setWinnerId(jsonObject.getLong(BilliardData.WINNER_ID));
                billiardData.setWinnerName(jsonObject.getString(BilliardData.WINNER_NAME));
                billiardData.setPlayTime(jsonObject.getInt(BilliardData.PLAY_TIME));
                billiardData.setScore(jsonObject.getString(BilliardData.SCORE));
                billiardData.setCost(jsonObject.getInt(BilliardData.COST));

                //
                billiardDataArrayList.add(billiardData);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ======================================================= playerData =======================================================
    private void parsePlayerDataArray(JSONArray playerDataArray) {

        // 0. count
        // 1. billiard count
        // 2. player id
        // 3. player name
        // 4. target score
        // 5. score

        try {

            for (int index = 0; index < playerDataArray.length(); index++) {

                JSONObject jsonObject = playerDataArray.getJSONObject(index);

                //
                PlayerData playerData = new PlayerData();
                playerData.setCount(jsonObject.getLong(PlayerData.COUNT));
                playerData.setBilliardCount(jsonObject.getLong(PlayerData.BILLIARD_COUNT));
                playerData.setPlayerId(jsonObject.getLong(PlayerData.PLAYER_ID));
                playerData.setPlayerName(jsonObject.getString(PlayerData.PLAYER_NAME));
                playerData.setTargetScore(jsonObject.getInt(PlayerData.TARGET_SCORE));
                playerData.setScore(jsonObject.getInt(PlayerData.SCORE));

                //
                playerDataArrayList.add(playerData);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ======================================================= friendData =======================================================
    private void parseFriendDataArray(JSONArray friendDataArray) {

        // 0. id
        // 1. user id
        // 2. name
        // 3. game record win
        // 4. game record loss
        // 5. recent game billiard count
        // 6. total play time
        // 7. total cost

        try {

            for (int index = 0; index < friendDataArray.length(); index++) {

                JSONObject jsonObject = friendDataArray.getJSONObject(index);

                //
                FriendData friendData = new FriendData();
                friendData.setId(jsonObject.getLong(FriendData.ID));
                friendData.setUserId(jsonObject.getLong(FriendData.USER_ID));
                friendData.setName(jsonObject.getString(FriendData.NAME));
                friendData.setGameRecordWin(jsonObject.getInt(FriendData.GAME_RECORD_WIN));
                friendData.setGameRecordLoss(jsonObject.getInt(FriendData.GAME_RECORD_LOSS));
                friendData.setRecentGameBilliardCount(jsonObject.getLong(FriendData.RECENT_GAME_BILLIARD_COUNT));
                friendData.setTotalPlayTime(jsonObject.getInt(FriendData.TOTAL_PLAY_TIME));
                friendData.setTotalCost(jsonObject.getInt(FriendData.TOTAL_COST));

                //
                friendDataArrayList.add(friendData);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void print() {

        DeveloperManager.displayLog(CLASS_NAME_LOG, "======> JsonParser 로 파싱한 데이터 확인");
        DeveloperManager.displayToUserData(
                CLASS_NAME_LOG,
                userData
        );

        // billiard
        DeveloperManager.displayToBilliardData(
                CLASS_NAME_LOG,
                billiardDataArrayList
        );

        // player
        DeveloperManager.displayToPlayerData(
                CLASS_NAME_LOG,
                playerDataArrayList
        );

        // friend
        DeveloperManager.displayToFriendData(
                CLASS_NAME_LOG,
                friendDataArrayList
        );

    }

}
