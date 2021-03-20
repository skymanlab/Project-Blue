package com.skyman.billiarddata.management.player.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager2;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.projectblue.database.AppDbLog;
import com.skyman.billiarddata.management.projectblue.database.AppDbSetting;
import com.skyman.billiarddata.management.projectblue.database.AppDbSetting2;
import com.skyman.billiarddata.management.user.database.UserTableSetting;

import java.util.ArrayList;

public class PlayerDbManager2 {

    // constant
    private final String CLASS_NAME = PlayerDbManager2.class.getSimpleName();

    // instance variable
    private AppDbSetting2 appDbSetting2;

    // constructor
    public PlayerDbManager2(AppDbSetting2 appDbSetting2) {
        this.appDbSetting2 = appDbSetting2;
    }

    // 0. count (primary key/autoincrement)
    // 1. billiard count
    // 2. player id
    // 3. player name
    // 4. target score
    // 5. score


    // ==================================================== Insert Query ====================================================
    public long saveContent(long billiardCount,
                            long playerId,
                            String playerName,
                            int targetScore,
                            int score) {

        final long rowNumber[] = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((billiardCount > 0) &&
                                (playerId > 0) &&
                                !playerName.equals("") &&
                                (targetScore >= 0) &&
                                (score >= 0)) ? true : false;
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, billiardCount);
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerId);
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerName);
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, score);

                        return writable.insert(
                                PlayerTableSetting.Entry.TABLE_NAME,
                                null,
                                insertValues
                        );
                    }

                    @Override
                    public void successRequest(long result) {
                        rowNumber[0] = result;
                    }

                    @Override
                    public void errorDatabase() {
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, PlayerDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContent]

    public long saveContentExceptPrimaryKey(PlayerData playerData) {

        final long rowNumber[] = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfPlayerData(playerData, true);
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, playerData.getBilliardCount());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerData.getPlayerId());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerData.getPlayerName());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, playerData.getTargetScore());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, playerData.getScore());

                        return writable.insert(
                                PlayerTableSetting.Entry.TABLE_NAME,
                                null,
                                insertValues
                        );
                    }

                    @Override
                    public void successRequest(long result) {
                        rowNumber[0] = result;
                    }

                    @Override
                    public void errorDatabase() {
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, PlayerDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContentExceptPrimaryKey]


    public long saveContent(PlayerData playerData) {

        final long rowNumber[] = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfPlayerData(playerData, false);
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(PlayerTableSetting.Entry._COUNT, playerData.getCount());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, playerData.getBilliardCount());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerData.getPlayerId());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerData.getPlayerName());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, playerData.getTargetScore());
                        insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, playerData.getScore());

                        return writable.insert(
                                PlayerTableSetting.Entry.TABLE_NAME,
                                null,
                                insertValues
                        );
                    }

                    @Override
                    public void successRequest(long result) {
                        rowNumber[0] = result;
                    }

                    @Override
                    public void errorDatabase() {
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, PlayerDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContent]


    // ==================================================== Select Query ====================================================
    public ArrayList<PlayerData> loadAllContent() {

        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return true;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {
                        return readable.rawQuery(
                                PlayerTableSetting.SQL_SELECT_TABLE_ALL_ITEM,
                                null
                        );
                    }

                    @Override
                    public void successRequest(Cursor result) {

                        while (result.moveToNext()) {

                            PlayerData playerData = new PlayerData();
                            playerData.setCount(result.getLong(0));
                            playerData.setBilliardCount(result.getLong(1));
                            playerData.setPlayerId(result.getLong(2));
                            playerData.setPlayerName(result.getString(3));
                            playerData.setTargetScore(result.getInt(4));
                            playerData.setScore(result.getInt(5));

                            playerDataArrayList.add(playerData);

                        }
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return playerDataArrayList;
    } // End of method [loadAllContent]


    public ArrayList<PlayerData> loadAllContentByBilliardCount(long billiardCount) {

        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < billiardCount) ? true : false;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {
                        return readable.rawQuery(
                                PlayerTableSetting.SQL_SELECT_WHERE_BILLIARD_COUNT + billiardCount,
                                null
                        );
                    }

                    @Override
                    public void successRequest(Cursor result) {

                        while (result.moveToNext()) {

                            PlayerData playerData = new PlayerData();
                            playerData.setCount(result.getLong(0));
                            playerData.setBilliardCount(result.getLong(1));
                            playerData.setPlayerId(result.getLong(2));
                            playerData.setPlayerName(result.getString(3));
                            playerData.setTargetScore(result.getInt(4));
                            playerData.setScore(result.getInt(5));

                            playerDataArrayList.add(playerData);

                        }
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return playerDataArrayList;
    } // End of method [loadAllContentByBilliardCount]


    public ArrayList<PlayerData> loadAllContentByPlayerIdAndPlayerName(long playerId, String playerName) {

        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((playerId > 0) && !playerName.equals("")) ? true : false;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {

                        String query = new StringBuilder()
                                .append(PlayerTableSetting.SQL_SELECT_WHERE)
                                .append("playerId=")
                                .append("'")
                                .append(playerId)
                                .append("'")
                                .append(" AND ")
                                .append("playerName=")
                                .append("'")
                                .append(playerName)
                                .append("'")
                                .toString();

                        return readable.rawQuery(
                                query,
                                null
                        );
                    }

                    @Override
                    public void successRequest(Cursor result) {

                        while (result.moveToNext()) {

                            PlayerData playerData = new PlayerData();
                            playerData.setCount(result.getLong(0));
                            playerData.setBilliardCount(result.getLong(1));
                            playerData.setPlayerId(result.getLong(2));
                            playerData.setPlayerName(result.getString(3));
                            playerData.setTargetScore(result.getInt(4));
                            playerData.setScore(result.getInt(5));

                            playerDataArrayList.add(playerData);

                        }

                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return playerDataArrayList;
    } // End of method [loadAllContentByPlayerIdAndPlayerName]


    // ==================================================== Update Query ====================================================
    public int updateContentByCount(long count, long billiardCount, long playerId, String playerName, int targetScore, int score) {

        final int numberOfUpdatedRows[] = {0};

        appDbSetting2.requestUpdateQuery(
                new AppDbSetting2.UpdateQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((count > 0) &&
                                (billiardCount >= 0) &&
                                (playerId >= 0) &&
                                !playerName.equals("") &&
                                (targetScore >= 0) &&
                                (score >= 0)) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        ContentValues updateValue = new ContentValues();
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, billiardCount);
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerId);
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerName);
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, score);

                        String selection = PlayerTableSetting.Entry._COUNT + " LIKE ?";
                        String[] selectionArgs = {count + ""};

                        return writable.update(
                                UserTableSetting.Entry.TABLE_NAME,
                                updateValue,
                                selection,
                                selectionArgs
                        );
                    }

                    @Override
                    public void successRequest(int result) {
                        numberOfUpdatedRows[0] = result;
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return numberOfUpdatedRows[0];
    } // End of method [updateContent]


    public int updateContentByCount(PlayerData playerData) {

        final int numberOfUpdatedRows[] = {0};

        appDbSetting2.requestUpdateQuery(
                new AppDbSetting2.UpdateQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfPlayerData(playerData, false);
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        ContentValues updateValue = new ContentValues();
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, playerData.getBilliardCount());
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerData.getPlayerId());
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerData.getPlayerName());
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, playerData.getTargetScore());
                        updateValue.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, playerData.getScore());

                        String selection = PlayerTableSetting.Entry._COUNT + " LIKE ?";
                        String[] selectionArgs = {playerData.getCount() + ""};

                        return writable.update(
                                PlayerTableSetting.Entry.TABLE_NAME,
                                updateValue,
                                selection,
                                selectionArgs
                        );

                    }

                    @Override
                    public void successRequest(int result) {
                        numberOfUpdatedRows[0] = result;
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return numberOfUpdatedRows[0];
    } // End of method [updateContent]


    // ==================================================== Delete Query ====================================================
    public int deleteContentByBilliardCount(long billiardCount) {

        final int numberOfDeletedRows[] = {0};

        appDbSetting2.requestDeleteQuery(
                new AppDbSetting2.DeleteQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < billiardCount) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        String selection = PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT + " LIKE ?";
                        String[] selectionArgs = {billiardCount + ""};

                        return writable.delete(
                                PlayerTableSetting.Entry.TABLE_NAME,
                                selection,
                                selectionArgs
                        );
                    }

                    @Override
                    public void successRequest(int result) {
                        numberOfDeletedRows[0] = result;
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, PlayerData.class.getSimpleName());
                    }
                }
        );

        return numberOfDeletedRows[0];
    } // End of method [deleteContentByBilliardCount]


    // ==================================================== Check Format ====================================================
    private boolean checkFormatOfPlayerData(PlayerData playerData, boolean exceptsPrimaryKey) {

        // 0. count (primary key/autoincrement)
        // 1. billiard count
        // 2. player id
        // 3. player name
        // 4. target score
        // 5. score


        boolean matchesFormat = false;

        if (!exceptsPrimaryKey) {

            // primary key 포함하여 형식 검사
            if ((playerData.getCount() > 0) &&                  // 0. count
                    (playerData.getBilliardCount() > 0) &&      // 1. billiard count
                    (playerData.getPlayerId() > 0) &&           // 2. player id
                    !playerData.getPlayerName().equals("") &&   // 3. player name
                    (playerData.getTargetScore() >= 0) &&       // 4. target score
                    (playerData.getScore() >= 0)) {             // 5. score
                matchesFormat = true;
            } else {
                matchesFormat = false;
            }

        } else {

            // primary key 제외하고 형식 검사
            if ((playerData.getBilliardCount() > 0) &&          // 1. billiard count
                    (playerData.getPlayerId() > 0) &&           // 2. player id
                    !playerData.getPlayerName().equals("") &&   // 3. player name
                    (playerData.getTargetScore() >= 0) &&       // 4. target score
                    (playerData.getScore() >= 0)) {             // 5. score
                matchesFormat = true;
            } else {
                matchesFormat = false;
            }

        }

        return matchesFormat;
    } // End of method [checkFormatOfPlayerData]

}
