package com.skyman.billiarddata.management.player.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.database.BilliardTableSetting;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.projectblue.database.ProjectBlueDBManager;
import com.skyman.billiarddata.management.user.database.UserTableSetting;

import java.util.ArrayList;

public class PlayerDbManager extends ProjectBlueDBManager {

    // constant
    private final String CLASS_NAME_LOG = "[DbM]_PlayerDbManager";

    // constructor
    public PlayerDbManager(Context targetContext) {
        super(targetContext);
    }


    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


    /**
     * [method] [insert] ProjectBlueDBHelper 에서 writeableDatabase 를 가져와 player 테이블에 데이터를 저장한다.
     *
     * @return player 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(long billiardCount,
                            long playerId,
                            String playerName,
                            int targetScore,
                            int score) {

        final String METHOD_NAME = "[saveContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiardCount> <playerId> <playerName> <targetScore> <score> 을 저장한다.");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수의 형식이 맞는 지 검사한다.
            if ((billiardCount > 0)
                    && (playerId > 0)
                    && !playerName.equals("")
                    && (targetScore >= 0)
                    && (score >= 0)) {
                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기            ==> SQLiteDatabase 는 모든 조건이 만족 했을 때 가져와서
                SQLiteDatabase writeDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, billiardCount);
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerId);
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerName);
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, score);

                // [lv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.                == > 사용하고
                newRowId = writeDb.insert(PlayerTableSetting.Entry.TABLE_NAME, null, insertValues);

                // [lv/C]SQLiteDatabase : close             == > 닫은 다음 결과값을 반환하든 가공하든 하면 된다.
                writeDb.close();

                // [check 3] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {
                    // 데이터 insert 실패
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "입력 성공했습니다. " + newRowId + " 값을 리턴합니다.");
                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들의 형식이 맞지 않아요.");
                newRowId = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete");
        return newRowId;
    } // End of method [saveContent]


    /**
     * [method] [insert] ProjectBlueDBHelper 에서 writeableDatabase 를 가져와 player 테이블에 데이터를 저장한다.
     *
     * @param playerData 게임에 참가한 player 의 정보
     * @return player 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(PlayerData playerData) {

        final String METHOD_NAME = "[saveContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<playerData> 을 저장한다.");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수의 형식이 맞는 지 검사한다.
            if ((playerData.getBilliardCount() > 0)
                    && (playerData.getPlayerId() > 0)
                    && !playerData.getPlayerName().equals("")
                    && (playerData.getTargetScore() >= 0)
                    && (playerData.getScore() >= 0)) {
                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기            ==> SQLiteDatabase 는 모든 조건이 만족 했을 때 가져와서
                SQLiteDatabase writeDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, playerData.getBilliardCount());
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerData.getPlayerId());
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerData.getPlayerName());
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, playerData.getTargetScore());
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, playerData.getScore());

                // [lv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.                == > 사용하고
                newRowId = writeDb.insert(BilliardTableSetting.Entry.TABLE_NAME, null, insertValues);

                // [lv/C]SQLiteDatabase : close             == > 닫은 다음 결과값을 반환하든 가공하든 하면 된다.
                writeDb.close();

                // [check 3] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {
                    // 데이터 insert 실패
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "입력 성공했습니다. " + newRowId + " 값을 리턴합니다.");
                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들의 형식이 맞지 않아요.");
                newRowId = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete");
        return newRowId;
    } // End of method [saveContent]


    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


    /**
     * [method] [select] 해당 billiardCount 의 게임에 참가한 모든 player 를 가져온다.
     *
     * @param billiardCount billiard 테이블의 count 값
     * @return player 테이블에 저장된 모든 데이터
     */
    /* method : load, SQLite DB Helper 를 이용하여 해당 테이블의 정보를 가져온다. */
    public ArrayList<PlayerData> loadAllContentByBilliardCount(long billiardCount) {

        final String METHOD_NAME = "[loadAllContentByBilliardCount] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiardCount> 에 해당하는 데이터 가져오기");

        // [lv/C]ArrayList<PlayerData> : player 테이블에 저장된 모든 데이커가 담길 ArrayList
        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        // [check  1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2 : billiardCount 의 형식이 0 이상이다.
            if (billiardCount > 0) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기 / declaration & create
                SQLiteDatabase readDb = super.getDbOpenHelper().getReadableDatabase();

                // [lv/C]Cursor : select query 문의 실행 결과가 담길 Cursor / use
                Cursor readCursor = readDb.rawQuery(PlayerTableSetting.SQL_SELECT_WHERE_BILLIARD_COUNT + billiardCount, null);

                // [cycle 1] : cursor 의 객체의 moveToNext method 를 이용하여 가져온 데이터가 있을 때까지
                while (readCursor.moveToNext()) {

                    // [lv/C]PlayerData : player 테이블의 '한 행'의 정보를 담는다.
                    PlayerData playerData = new PlayerData();
                    playerData.setCount(readCursor.getLong(0));
                    playerData.setBilliardCount(readCursor.getLong(1));
                    playerData.setPlayerId(readCursor.getLong(2));
                    playerData.setPlayerName(readCursor.getString(3));
                    playerData.setTargetScore(readCursor.getInt(4));
                    playerData.setScore(readCursor.getInt(5));

                    // [lv/C]ArrayList<BilliardData> : 위 의 '한 행'의 내용을 배열 형태로 담는다.
                    playerDataArrayList.add(playerData);

                } // [cycle 1]

                // [lv/C]SQLiteDatabase : close / end
                readDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiardCount 의 형식이 맞지 않습니다.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");

        return playerDataArrayList;
    } // End of method [loadAllContentByBilliardCount]


    /**
     * [method] [select] 해당 playerId 가 참가한 게임 목록을 가져온다.
     *
     * @param playerId 게임에 참가한 player 의 id
     * @return player 테이블에 저장된 모든 데이터
     */
    /* method : load, SQLite DB Helper 를 이용하여 해당 테이블의 정보를 가져온다. */
    public ArrayList<PlayerData> loadAllContentByPlayerIdAndPlayerName(long playerId, String playerName) {

        final String METHOD_NAME = "[loadAllContentByPlayerIdAndPlayerName] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<playerId> <playerName> 에 해당하는 데이터를 가져온다.");

        // [lv/C]ArrayList<PlayerData> : player 테이블에 저장된 모든 데이커가 담길 ArrayList
        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        // [check  1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2 : billiardCount 의 형식이 0 이상이다.
            if ((playerId > 0)
                    && !playerName.equals("")) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기 / declaration & create
                SQLiteDatabase readDb = super.getDbOpenHelper().getReadableDatabase();

                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "QUERY =  " + makeSelectQueryOfPlayerIdAndPlayerName(playerId,playerName));

                // [lv/C]Cursor : select query 문의 실행 결과가 담길 Cursor / use
                Cursor readCursor = readDb.rawQuery(makeSelectQueryOfPlayerIdAndPlayerName(playerId, playerName), null);

                // [cycle 1] : cursor 의 객체의 moveToNext method 를 이용하여 가져온 데이터가 있을 때까지
                while (readCursor.moveToNext()) {

                    // [lv/C]PlayerData : player 테이블의 '한 행'의 정보를 담는다.
                    PlayerData playerData = new PlayerData();
                    playerData.setCount(readCursor.getLong(0));
                    playerData.setBilliardCount(readCursor.getLong(1));
                    playerData.setPlayerId(readCursor.getLong(2));
                    playerData.setPlayerName(readCursor.getString(3));
                    playerData.setTargetScore(readCursor.getInt(4));
                    playerData.setScore(readCursor.getInt(5));

                    // [lv/C]ArrayList<BilliardData> : 위 의 '한 행'의 내용을 배열 형태로 담는다.
                    playerDataArrayList.add(playerData);

                } // [cycle 1]

                // [lv/C]SQLiteDatabase : close / end
                readDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiardCount 의 형식이 맞지 않습니다.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");

        return playerDataArrayList;
    } // End of method [loadAllContentByPlayerIdAndPlayerName]


    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


    /**
     * [method] [update] playerId, playerName, targetScore, score 값을 받아서 해당 count 의 player 정보를 업데이트한다.
     *
     * @return update query 문을 실행한 결과
     */
    public int updateContentByCount(long count, long billiardCount, long playerId, String playerName, int targetScore, int score) {

        final String METHOD_NAME= "[updateContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<count> 에 해당하는 <billiardCount> <playerId> <playerName> <targetScore> <score> 을 갱신합니다.");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수들의 값이 형식에 맞다.
            if ((count > 0)                             // 0. count
                    && (billiardCount >= 0)             // 1. billiard count
                    && (playerId >= 0)                  // 2. player id
                    && !playerName.equals("")           // 3. player name
                    && (targetScore >= 0)               // 4. target score
                    && (score >= 0)) {                  // 5. score

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase updateDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : 매개변수 값을 담을 객체 생성과 초기화
                ContentValues updateValue = new ContentValues();
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, billiardCount);
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerId);
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerName);
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, score);

                // [lv/i]methodResult : update query 문을 실행한 결과
                methodResult = updateDb.update(UserTableSetting.Entry.TABLE_NAME, updateValue, PlayerTableSetting.Entry._COUNT + "=" + count, null);

                // [lv/C]SQLiteDatabase : close
                updateDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들이 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");

        return methodResult;
    } // End of method [updateContent]


    /**
     * [method] [update] playerId, playerName, targetScore, score 값을 받아서 해당 count 의 player 정보를 업데이트한다.
     *
     * @return update query 문을 실행한 결과
     */
    public int updateContentByCount(PlayerData playerData) {

        final String METHOD_NAME= "[updateContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<playerData> 를 갱신합니다.");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수들의 값이 형식에 맞다.
            if ((playerData.getCount() > 0)                             // 0. count
                    && (playerData.getBilliardCount() >= 0)             // 1. billiard count
                    && (playerData.getPlayerId() >= 0)                  // 2. player id
                    && !playerData.getPlayerName().equals("")           // 3. player name
                    && (playerData.getTargetScore() >= 0)               // 4. target score
                    && (playerData.getScore() >= 0)) {                  // 5. score

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase updateDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : 매개변수 값을 담을 객체 생성과 초기화
                ContentValues updateValue = new ContentValues();
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, playerData.getBilliardCount());
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerData.getPlayerId());
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerData.getPlayerName());
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, playerData.getTargetScore());
                updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, playerData.getScore());

                // [lv/i]methodResult : update query 문을 실행한 결과
                methodResult = updateDb.update(PlayerTableSetting.Entry.TABLE_NAME, updateValue, PlayerTableSetting.Entry._COUNT + "=" + playerData.getCount(), null);

                // [lv/C]SQLiteDatabase : close
                updateDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들이 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");

        return methodResult;
    } // End of method [updateContent]


    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


    /**
     * [method] [delete] 해당 billiardCount 값이 모두 같은 레코드는 모두 삭제한다.
     *
     * @param billiardCount billiard 테이블의 count
     * @return user 테이블의 데이터를 삭제한 개수 or 실패
     */
    public int deleteContentByBilliardCount(long billiardCount) {

        final String METHOD_NAME= "[deleteContentByBilliardCount] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiardCount> 에 해당하는 모든 데이터를 삭제합니다.");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : id 는 0 보다 크다
            if (billiardCount > 0) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase deleteDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/i]methodResult : delete query 문이 실행 된 결과를 받는다.
                methodResult = deleteDb.delete(PlayerTableSetting.Entry.TABLE_NAME, PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT + "="+ billiardCount, null);

                // [lv/C]SQLiteDatabase : close
                deleteDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수 billiardCount 는 0 보다 작은 값입니다.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return methodResult;

    } // End of method [deleteContentByBilliardCount]


    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


    /**
     * [method] playerId 와 playerName 으로 SQL query 문을 만든다.
     *
     * <P>
     * select * from player where playerId='' and playerName=''
     * </P>
     *
     */
    private String makeSelectQueryOfPlayerIdAndPlayerName(long playerId, String playerName){

        StringBuilder query = new StringBuilder();

        query.append(PlayerTableSetting.SQL_SELECT_WHERE);
        query.append("playerId=");
        query.append("'");
        query.append(playerId);
        query.append("'");
        query.append(" AND ");
        query.append("playerName=");
        query.append("'");
        query.append(playerName);
        query.append("'");

        return query.toString();
    }
}
