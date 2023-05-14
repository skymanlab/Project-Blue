package com.skyman.billiarddata.table.friend.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.LogSwitch;
import com.skyman.billiarddata.table.friend.data.FriendData;
import com.skyman.billiarddata.etc.database.AppDbLog;
import com.skyman.billiarddata.etc.database.AppDbSetting2;

import java.util.ArrayList;


/**
 * [class] project_blue.db 의 friend 테이블을 관리하기 위한 클래스이다.
 *
 * <p>
 * ProjectBlueDBManger 를 상속 받아
 * ProjectBlueDBHelper 를 생성한다.
 * 이 openDBHelper 에서 readableDatabase 와 writeableDatabase 를 가져와서 query 문을 실행한다.
 */
public class FriendDbManager2 {

    // constant
    private static final LogSwitch CLASS_LOG_SWITCH = LogSwitch.OFF;
    private static final String CLASS_NAME = "FriendDbManager2";

    // instance variable
    private AppDbSetting2 appDbSetting2;

    // constructor
    public FriendDbManager2(AppDbSetting2 appDbSetting2) {
        this.appDbSetting2 = appDbSetting2;
    }

    // 0. id (primary key/autoincrement)
    // 1. user id
    // 2. name
    // 3. game record win
    // 4. game record loss
    // 5. recent game billiard count
    // 6. total play time
    // 7. total cost

    // ==================================================== Insert Query ====================================================
    public long saveContent(long userId,
                            String name,
                            int gameRecordWin,
                            int gameRecordLoss,
                            long recentGameBilliardCount,
                            int totalPlayTime,
                            int totalCost) {


        final long rowNumber[] = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((userId > 0) &&
                                !name.equals("") &&
                                (gameRecordWin >= 0) &&
                                (gameRecordLoss >= 0) &&
                                (recentGameBilliardCount >= 0) &&
                                (totalPlayTime >= 0) &&
                                (totalCost >= 0)) ? true : false;
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_USER_ID, userId);                                         // 1. user id
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_NAME, name);                                              // 2. name
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);                          // 3. game record win
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);                        // 4. game record loss
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, recentGameBilliardCount);     // 5. recent game billiard count
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);                          // 6. total play time
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);                                   // 7. total cost

                        return writable.insert(
                                FriendTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, FriendDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContent]


    public long saveContent(FriendData friendData) {

        // 0. id (primary key/autoincrement)
        // 1. user id
        // 2. name
        // 3. game record win
        // 4. game record loss
        // 5. recent game billiard count
        // 6. total play time
        // 7. total cost

        final long rowNumber[] = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfFriendData(friendData, false);
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(FriendTableSetting.Entry._ID, friendData.getId());                                                             // 0. id
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_USER_ID, friendData.getUserId());                                         // 1. user id
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_NAME, friendData.getName());                                              // 2. name
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, friendData.getGameRecordWin());                          // 3. game record win
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, friendData.getGameRecordLoss());                        // 4. game record loss
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, friendData.getRecentGameBilliardCount());     // 5. recent game billiard count
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, friendData.getTotalPlayTime());                          // 6. total play time
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_COST, friendData.getTotalCost());                                   // 7. total cost

                        return writable.insert(
                                FriendTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, FriendDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContent]


    public long saveContentExceptPrimaryKey(FriendData friendData) {

        // 0. id (primary key/autoincrement)
        // 1. user id
        // 2. name
        // 3. game record win
        // 4. game record loss
        // 5. recent game billiard count
        // 6. total play time
        // 7. total cost

        final long rowNumber[] = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfFriendData(friendData, true);
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_USER_ID, friendData.getUserId());                                         // 1. user id
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_NAME, friendData.getName());                                              // 2. name
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, friendData.getGameRecordWin());                          // 3. game record win
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, friendData.getGameRecordLoss());                        // 4. game record loss
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, friendData.getRecentGameBilliardCount());     // 5. recent game billiard count
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, friendData.getTotalPlayTime());                          // 6. total play time
                        insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_COST, friendData.getTotalCost());                                   // 7. total cost

                        return writable.insert(
                                FriendTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, FriendDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContentExceptPrimaryKey]


    // ==================================================== Select Query ====================================================
    public FriendData loadContentById(long id) {

        final FriendData[] friendData = {null};

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < id) ? true : false;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {

                        return readable.rawQuery(
                                FriendTableSetting.SQL_SELECT_WHERE_ID + id,
                                null
                        );

                    }

                    @Override
                    public void successRequest(Cursor result) {

                        if (result.moveToFirst()) {

                            friendData[0] = new FriendData();
                            friendData[0].setId(result.getLong(0));
                            friendData[0].setUserId(result.getLong(1));
                            friendData[0].setName(result.getString(2));
                            friendData[0].setGameRecordWin(result.getInt(3));
                            friendData[0].setGameRecordLoss(result.getInt(4));
                            friendData[0].setRecentGameBilliardCount(result.getLong(5));
                            friendData[0].setTotalPlayTime(result.getInt(6));
                            friendData[0].setTotalCost(result.getInt(7));

                        }
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return friendData[0];
    } // End of method [loadContentById]


    public FriendData loadContentByIdAndUserId(long id, long userId) {

        final FriendData[] friendData = {null};

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((0 < id) && (0 < userId)) ? true : false;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {

                        String query = new StringBuilder()
                                .append("SELECT * FROM WHERE ")
                                .append(FriendTableSetting.Entry._ID)
                                .append("=")
                                .append(id)
                                .append(" AND ")
                                .append(FriendTableSetting.Entry.COLUMN_NAME_USER_ID)
                                .append("=")
                                .append(userId)
                                .toString();

                        return readable.rawQuery(
                                query,
                                null
                        );

                    }

                    @Override
                    public void successRequest(Cursor result) {

                        if (result.moveToFirst()) {

                            friendData[0].setId(result.getLong(0));
                            friendData[0].setUserId(result.getLong(1));
                            friendData[0].setName(result.getString(2));
                            friendData[0].setGameRecordWin(result.getInt(3));
                            friendData[0].setGameRecordLoss(result.getInt(4));
                            friendData[0].setRecentGameBilliardCount(result.getLong(5));
                            friendData[0].setTotalPlayTime(result.getInt(6));
                            friendData[0].setTotalCost(result.getInt(7));

                        }
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return friendData[0];
    } // End of method [loadContentByIdAndUserId]


    public ArrayList<FriendData> loadAllContent() {

        ArrayList<FriendData> friendDataArrayList = new ArrayList<>();

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return true;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {
                        return readable.rawQuery(
                                FriendTableSetting.SQL_SELECT_TABLE_ALL_ITEM,
                                null
                        );
                    }

                    @Override
                    public void successRequest(Cursor result) {

                        while (result.moveToNext()) {

                            FriendData friendData = new FriendData();
                            friendData.setId(result.getLong(0));
                            friendData.setUserId(result.getLong(1));
                            friendData.setName(result.getString(2));
                            friendData.setGameRecordWin(result.getInt(3));
                            friendData.setGameRecordLoss(result.getInt(4));
                            friendData.setRecentGameBilliardCount(result.getLong(5));
                            friendData.setTotalPlayTime(result.getInt(6));
                            friendData.setTotalCost(result.getInt(7));

                            friendDataArrayList.add(friendData);
                        }

                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return friendDataArrayList;
    } // End of method [loadAllContent]


    public ArrayList<FriendData> loadAllContentByUserId(long userId) {

        ArrayList<FriendData> friendDataArrayList = new ArrayList<>();

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < userId) ? true : false;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {

                        return readable.rawQuery(
                                FriendTableSetting.SQL_SELECT_WHERE_USER_ID + userId,
                                null
                        );

                    }

                    @Override
                    public void successRequest(Cursor result) {

                        while (result.moveToNext()) {

                            FriendData friendData = new FriendData();
                            friendData.setId(result.getLong(0));
                            friendData.setUserId(result.getLong(1));
                            friendData.setName(result.getString(2));
                            friendData.setGameRecordWin(result.getInt(3));
                            friendData.setGameRecordLoss(result.getInt(4));
                            friendData.setRecentGameBilliardCount(result.getLong(5));
                            friendData.setTotalPlayTime(result.getInt(6));
                            friendData.setTotalCost(result.getInt(7));

                            friendDataArrayList.add(friendData);

                        }
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return friendDataArrayList;
    } // End of method [loadAllContentByUserId]


    // ==================================================== Update Query ====================================================
    public int updateContentById(long id,
                                 int gameRecordWin,
                                 int gameRecordLoss,
                                 long recentGameBilliardCount,
                                 int totalPlayTime,
                                 int totalCost) {


        final int numberOfUpdatedRows[] = {0};

        appDbSetting2.requestUpdateQuery(
                new AppDbSetting2.UpdateQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((id > 0) &&                         // 0. id
                                (gameRecordWin >= 0) &&             // 3. game record win
                                (gameRecordLoss >= 0) &&            // 4. game record loss
                                (recentGameBilliardCount >= 0) &&   // 5. recent game billiard count
                                (totalPlayTime >= 0) &&             // 6. total play time
                                (totalCost >= 0)) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        ContentValues updateValue = new ContentValues();
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, recentGameBilliardCount);
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);

                        String selection = FriendTableSetting.Entry._ID + " LIKE ?";
                        String[] selectionArgs = {id + ""};

                        return writable.update(
                                FriendTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return numberOfUpdatedRows[0];
    } // End of method [updateContentById]


    public int updateContentByUserId(long userId,
                                     int gameRecordWin,
                                     int gameRecordLoss,
                                     long recentGameBilliardCount,
                                     int totalPlayTime,
                                     int totalCost) {


        final int numberOfUpdatedRows[] = {0};

        appDbSetting2.requestUpdateQuery(
                new AppDbSetting2.UpdateQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((userId > 0) &&                         // 0. id
                                (gameRecordWin >= 0) &&             // 3. game record win
                                (gameRecordLoss >= 0) &&            // 4. game record loss
                                (recentGameBilliardCount >= 0) &&   // 5. recent game billiard count
                                (totalPlayTime >= 0) &&             // 6. total play time
                                (totalCost >= 0)) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        ContentValues updateValue = new ContentValues();
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);                           // 3. game record win
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);                         // 4. game record loss
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, recentGameBilliardCount);      // 5. recent game billiard count
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);                           // 6. total play time
                        updateValue.put(FriendTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);

                        String selection = FriendTableSetting.Entry.COLUMN_NAME_USER_ID + " LIKE ?";
                        String[] selectionArgs = {userId + ""};

                        return writable.update(
                                FriendTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return numberOfUpdatedRows[0];
    } // End of method [updateContentByUserId]


    // ==================================================== Delete Query ====================================================
    public int deleteAllContent() {

        final int numberOfDeletedRows[] = {0};

        appDbSetting2.requestDeleteQuery(
                new AppDbSetting2.DeleteQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return true;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {
                        return writable.delete(
                                FriendTableSetting.Entry.TABLE_NAME,
                                null,
                                null
                        );
                    }

                    @Override
                    public void successRequest(int result) {
                        numberOfDeletedRows[0] = result;
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return numberOfDeletedRows[0];
    } // End of method [deleteContent]


    public int deleteContentById(long id) {

        final int numberOfDeletedRows[] = {0};

        appDbSetting2.requestDeleteQuery(
                new AppDbSetting2.DeleteQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < id) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        String selection = FriendTableSetting.Entry._ID + " LIKE ?";
                        String[] selectionArgs = {id + ""};

                        return writable.delete(
                                FriendTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return numberOfDeletedRows[0];
    } // End of method [deleteContentById]


    public int deleteContentByUserId(long userId) {

        final int numberOfDeletedRows[] = {0};

        appDbSetting2.requestDeleteQuery(
                new AppDbSetting2.DeleteQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < userId) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        String selection = FriendTableSetting.Entry.COLUMN_NAME_USER_ID + " LIKE ?";
                        String[] selectionArgs = {userId + ""};

                        return writable.delete(
                                FriendTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printFormatErrorLog(CLASS_NAME, FriendData.class.getSimpleName());
                    }
                }
        );

        return numberOfDeletedRows[0];
    } // End of method [deleteContentByUserId]


    // ==================================================== Check Format ====================================================
    private boolean checkFormatOfFriendData(FriendData friendData, boolean exceptsPrimaryKey) {

        // 0. id
        // 1. user id
        // 2. name
        // 3. game record win
        // 4. game record loss
        // 5. recent game billiard count
        // 6. total play time
        // 7. total cost

        boolean matchesFormat = false;

        if (!exceptsPrimaryKey) {

            // primary key 포함하여 형식 검사
            if ((friendData.getId() > 0) &&                             // 0. id
                    (friendData.getUserId() > 0) &&                     // 1. user id
                    !friendData.getName().equals("") &&                 // 2. name
                    (friendData.getGameRecordWin() >= 0) &&             // 3. game record win
                    (friendData.getGameRecordLoss() >= 0) &&            // 4. game record loss
                    (friendData.getRecentGameBilliardCount() >= 0) &&   // 5. recent game billiard count
                    (friendData.getTotalPlayTime() >= 0) &&             // 6. total play time
                    (friendData.getTotalCost() >= 0)) {                 // 7. total cost
                matchesFormat = true;
            } else {
                matchesFormat = false;
            }

        } else {

            // primary key 제외하고 형식 검사
            if ((friendData.getUserId() > 0) &&                         // 1. user id
                    !friendData.getName().equals("") &&                 // 2. name
                    (friendData.getGameRecordWin() >= 0) &&             // 3. game record win
                    (friendData.getGameRecordLoss() >= 0) &&            // 4. game record loss
                    (friendData.getRecentGameBilliardCount() >= 0) &&   // 5. recent game billiard count
                    (friendData.getTotalPlayTime() >= 0) &&             // 6. total play time
                    (friendData.getTotalCost() >= 0)) {                 // 7. total cost
                matchesFormat = true;
            } else {
                matchesFormat = false;
            }

        }

        return matchesFormat;
    }
}
