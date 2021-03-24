package com.skyman.billiarddata.management.file;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.user.data.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonGenerator {

    // instance variable
    private UserData userData;
    private ArrayList<BilliardData> billiardDataArrayList;
    private ArrayList<PlayerData> playerDataArrayList;
    private ArrayList<FriendData> friendDataArrayList;

    // instance variable
    private JSONObject jsonObject;


    // constructor
    private JsonGenerator(Builder builder) {
        // 데이터
        this.userData = builder.userData;
        this.billiardDataArrayList = builder.billiardDataArrayList;
        this.playerDataArrayList = builder.playerDataArrayList;
        this.friendDataArrayList = builder.friendDataArrayList;

        jsonObject = new JSONObject();
    }

    // getter
    public JSONObject getJsonObject() {
        return jsonObject;
    }


    // ============================================== 사용자 사용 메소드 ==============================================

    /**
     * 모든 데이터를 형식에 맞게 JSON Object 로 만들어서 반환한다.
     *
     * @throws Exception 오류 발생 시
     */
    public void createJsonObject() throws Exception {

        // dataArray
        JSONArray billiardDataArray = new JSONArray();
        JSONArray playerDataArray = new JSONArray();
        JSONArray friendDataArray = new JSONArray();

        // user
        jsonObject.put(UserData.class.getSimpleName(), createJsonObjectOfUserData(userData));

        // friend
        for (int index = 0; index < friendDataArrayList.size(); index++) {
            friendDataArray.put(
                    createJsonObjectOfFriendData(
                            friendDataArrayList.get(index)
                    )
            );
        }
        jsonObject.put(FriendData.class.getSimpleName(), friendDataArray);

        // billiard
        for (int index = 0; index < billiardDataArrayList.size(); index++) {
            billiardDataArray.put(
                    createJsonObjectOfBilliardData(
                            billiardDataArrayList.get(index)
                    )
            );
        }
        jsonObject.put(BilliardData.class.getSimpleName(), billiardDataArray);

        // player
        for (int index = 0; index < playerDataArrayList.size(); index++) {
            playerDataArray.put(
                    createJsonObjectOfPlayerData(
                            playerDataArrayList.get(index)
                    )
            );
        }
        jsonObject.put(PlayerData.class.getSimpleName(), playerDataArray);

    }


    // ======================================================= userData =======================================================
    private JSONObject createJsonObjectOfUserData(UserData userData) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        // 0. id
        // 1. name
        // 2. target score
        // 3. speciality
        // 4. game record win
        // 5. game record loss
        // 6. recent game billiard count
        // 7. total play time
        // 8. total cost

        jsonObject.put(UserData.ID, userData.getId());
        jsonObject.put(UserData.NAME, userData.getName());
        jsonObject.put(UserData.TARGET_SCORE, userData.getTargetScore());
        jsonObject.put(UserData.SPECIALITY, userData.getSpeciality());
        jsonObject.put(UserData.GAME_RECORD_WIN, userData.getGameRecordWin());
        jsonObject.put(UserData.GAME_RECORD_LOSS, userData.getGameRecordLoss());
        jsonObject.put(UserData.RECENT_GAME_BILLIARD_COUNT, userData.getRecentGameBilliardCount());
        jsonObject.put(UserData.TOTAL_PLAY_TIME, userData.getTotalPlayTime());
        jsonObject.put(UserData.TOTAL_COST, userData.getTotalCost());

        return jsonObject;
    }


    // ======================================================= billiardData =======================================================
    private JSONObject createJsonObjectOfBilliardData(BilliardData billiardData) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        // 0. count
        // 1. date
        // 2. game mode
        // 3. player count
        // 4. winner id
        // 5. winner name
        // 6. play time
        // 7. score
        // 8. cost
        jsonObject.put(BilliardData.COUNT, billiardData.getCount());
        jsonObject.put(BilliardData.DATE, billiardData.getDate());
        jsonObject.put(BilliardData.GAME_MODE, billiardData.getGameMode());
        jsonObject.put(BilliardData.PLAYER_COUNT, billiardData.getPlayerCount());
        jsonObject.put(BilliardData.WINNER_ID, billiardData.getWinnerId());
        jsonObject.put(BilliardData.WINNER_NAME, billiardData.getWinnerName());
        jsonObject.put(BilliardData.PLAY_TIME, billiardData.getPlayTime());
        jsonObject.put(BilliardData.SCORE, billiardData.getScore());
        jsonObject.put(BilliardData.COST, billiardData.getCost());

        return jsonObject;
    }


    // ======================================================= playerData =======================================================
    private JSONObject createJsonObjectOfPlayerData(PlayerData playerData) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        // 0. count
        // 1. billiard count
        // 2. player id
        // 3. player name
        // 4. target score
        // 5. score

        jsonObject.put(PlayerData.COUNT, playerData.getCount());
        jsonObject.put(PlayerData.BILLIARD_COUNT, playerData.getBilliardCount());
        jsonObject.put(PlayerData.PLAYER_ID, playerData.getPlayerId());
        jsonObject.put(PlayerData.PLAYER_NAME, playerData.getPlayerName());
        jsonObject.put(PlayerData.TARGET_SCORE, playerData.getTargetScore());
        jsonObject.put(PlayerData.SCORE, playerData.getScore());

        return jsonObject;
    }


    // ======================================================= friendData =======================================================
    private JSONObject createJsonObjectOfFriendData(FriendData friendData) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        // 0. id
        // 1. user id
        // 2. name
        // 3. game record win
        // 4. game record loss
        // 5. recent game billiard count
        // 6. total play time
        // 7. total cost

        jsonObject.put(FriendData.ID, friendData.getId());
        jsonObject.put(FriendData.USER_ID, friendData.getUserId());
        jsonObject.put(FriendData.NAME, friendData.getName());
        jsonObject.put(FriendData.GAME_RECORD_WIN, friendData.getGameRecordWin());
        jsonObject.put(FriendData.GAME_RECORD_LOSS, friendData.getGameRecordLoss());
        jsonObject.put(FriendData.RECENT_GAME_BILLIARD_COUNT, friendData.getRecentGameBilliardCount());
        jsonObject.put(FriendData.TOTAL_PLAY_TIME, friendData.getTotalPlayTime());
        jsonObject.put(FriendData.TOTAL_COST, friendData.getTotalCost());

        return jsonObject;
    }

    // ============================================== Builder ==============================================
    public static class Builder {

        // instance variable
        private UserData userData;
        private ArrayList<BilliardData> billiardDataArrayList;
        private ArrayList<PlayerData> playerDataArrayList;
        private ArrayList<FriendData> friendDataArrayList;

        // constructor
        public Builder() {

        }

        // setter
        public Builder setUserData(UserData userData) {
            this.userData = userData;
            return this;
        }

        public Builder setBilliardDataArrayList(ArrayList<BilliardData> billiardDataArrayList) {
            this.billiardDataArrayList = billiardDataArrayList;
            return this;
        }

        public Builder setPlayerDataArrayList(ArrayList<PlayerData> playerDataArrayList) {
            this.playerDataArrayList = playerDataArrayList;
            return this;
        }

        public Builder setFriendDataArrayList(ArrayList<FriendData> friendDataArrayList) {
            this.friendDataArrayList = friendDataArrayList;
            return this;
        }

        // create
        public JsonGenerator create() {
            return new JsonGenerator(this);
        }
    }
}
