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
    private ArrayList<UserData> userDataArrayList;
    private ArrayList<BilliardData> billiardDataArrayList;
    private ArrayList<PlayerData> playerDataArrayList;
    private ArrayList<FriendData> friendDataArrayList;

    // instance variable
    private boolean isCompletedParsing;      // true:json 형태로 변환 완료 , false:형식에 맞지 않아 데이터 변환 불가능

    // constructor
    public JsonParser(String content) {

        this.content = content;

        // ArrayList
        this.userDataArrayList = new ArrayList<>();
        this.billiardDataArrayList = new ArrayList<>();
        this.playerDataArrayList = new ArrayList<>();
        this.friendDataArrayList = new ArrayList<>();

        this.isCompletedParsing = false;
    }

    // getter
    public boolean isCompletedParsing() {
        return isCompletedParsing;
    }

    public ArrayList<UserData> getUserDataArrayList() {
        return userDataArrayList;
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
            JSONObject jsonObject = new JSONObject(content);

            DeveloperManager.displayLog(CLASS_NAME_LOG, "-------------------------------------------------->>>>>>>>>>>>>>>");
            DeveloperManager.displayLog(CLASS_NAME_LOG, "JSON : " +jsonObject.toString());

            // user
            JSONArray userDataArray = jsonObject.getJSONArray(UserData.CLASS_NAME);
            parseUserDataArray(userDataArray);

            // billiard
            JSONArray billiardDataArray = jsonObject.getJSONArray(BilliardData.CLASS_NAME);
            parseBilliardDataArray(billiardDataArray);

            // player
            JSONArray playerDataArray = jsonObject.getJSONArray(PlayerData.CLASS_NAME);
            parsePlayerDataArray(playerDataArray);

            // friend
            JSONArray friendDataArray = jsonObject.getJSONArray(FriendData.CLASS_NAME);
            parseFriendDataArray(friendDataArray);

            this.isCompletedParsing = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // ======================================================= userData =======================================================
    private void parseUserDataArray(JSONArray userDataArray) {

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

            for (int index = 0; index < userDataArray.length(); index++) {

                JSONObject jsonObject = userDataArray.getJSONObject(index);

                // jsonObject 의 내용으로 UserData 객체 생성
                UserData userData = new UserData();
                userData.setId(jsonObject.getLong(UserData.ID));
                userData.setName(jsonObject.getString(UserData.NAME));
                userData.setTargetScore(jsonObject.getInt(UserData.TARGET_SCORE));
                userData.setSpeciality(jsonObject.getString(UserData.SPECIALITY));
                userData.setGameRecordWin(jsonObject.getInt(UserData.GAME_RECORD_WIN));
                userData.setGameRecordLoss(jsonObject.getInt(UserData.GAME_RECORD_LOSS));
                userData.setRecentGameBilliardCount(jsonObject.getLong(UserData.RECENT_GAME_BILLIARD_COUNT));
                userData.setTotalPlayTime(jsonObject.getInt(UserData.TOTAL_PLAY_TIME));
                userData.setTotalCost(jsonObject.getInt(UserData.TOTAL_COST));

                //
                userDataArrayList.add(userData);
            }

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

        DeveloperManager.displayLog(CLASS_NAME_LOG, "===============================================================");
        DeveloperManager.displayLog(CLASS_NAME_LOG, "========================================= user =========================================");
        // user
        for (int index=0; index<userDataArrayList.size(); index++) {
            DeveloperManager.displayToUserData(CLASS_NAME_LOG, userDataArrayList.get(index));
        }

        // billiard
        DeveloperManager.displayLog(CLASS_NAME_LOG, "========================================= billiard =========================================");
        DeveloperManager.displayToBilliardData(CLASS_NAME_LOG, billiardDataArrayList);

        // player
        DeveloperManager.displayLog(CLASS_NAME_LOG, "========================================= player =========================================");
        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);

        // friend
        DeveloperManager.displayLog(CLASS_NAME_LOG, "========================================= friend =========================================");
        DeveloperManager.displayToFriendData(CLASS_NAME_LOG, friendDataArrayList);

    }

}
