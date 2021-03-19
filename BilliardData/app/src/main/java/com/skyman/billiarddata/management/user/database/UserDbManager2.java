package com.skyman.billiarddata.management.user.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager2;
import com.skyman.billiarddata.management.projectblue.database.AppDbLog;
import com.skyman.billiarddata.management.projectblue.database.AppDbSetting;
import com.skyman.billiarddata.management.projectblue.database.AppDbSetting2;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

/**
 * [class] project_blue.db 의 user 테이블을 관리하기 위한 클래스이다.
 *
 * <p>
 * ProjectBlueDBManger 를 상속 받아
 * ProjectBlueDBHelper 를 생성한다.
 * 이 openDBHelper 에서 readableDatabase 와 writeableDatabase 를 가져와서 query 문을 실행한다.
 */
public class UserDbManager2 {

    // constant
    private final String CLASS_NAME = UserDbManager2.class.getSimpleName();

    // instance variable
    private AppDbSetting2 appDbSetting2;

    // constructor
    public UserDbManager2(AppDbSetting2 appDbSetting2) {
        this.appDbSetting2 = appDbSetting2;
    }

    // 0. id
    // 1. name
    // 2. target score
    // 3. speciality
    // 4. game record win
    // 5. game record loss
    // 6. recent game billiard count
    // 7. total play time
    // 8. total cost


    // ==================================================== Insert Query ====================================================
    public long saveContent(UserData userData) {

        final long rowNumber[] = {0};
        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfUserData(userData, false);
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(UserTableSetting.Entry._ID, userData.getId());                                                             // 0. id (primary key)
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_NAME, userData.getName());                                              // 1. name
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, userData.getTargetScore());                               // 2. target score
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, userData.getSpeciality());                                  // 3. speciality
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, userData.getGameRecordWin());                          // 4. game record win
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, userData.getGameRecordLoss());                        // 5. game record loss
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, userData.getRecentGameBilliardCount());     // 6. recent game billiard count
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, userData.getTotalPlayTime());                          // 7. total play time
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, userData.getTotalCost());                                   // 8. total cost

                        return writable.insert(
                                UserTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, UserDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, UserData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContent]


    public long saveContentExceptPrimaryKey(UserData userData) {

        final long rowNumber[] = {0};
        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfUserData(userData, true);
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_NAME, userData.getName());                                              // 1. name
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, userData.getTargetScore());                               // 2. target score
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, userData.getSpeciality());                                  // 3. speciality
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, userData.getGameRecordWin());                          // 4. game record win
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, userData.getGameRecordLoss());                        // 5. game record loss
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, userData.getRecentGameBilliardCount());     // 6. recent game billiard count
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, userData.getTotalPlayTime());                          // 7. total play time
                        insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, userData.getTotalCost());                                   // 8. total cost

                        return writable.insert(
                                UserTableSetting.Entry.TABLE_NAME,
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
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, UserDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, UserData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContent]


    // ==================================================== Select Query ====================================================
    public UserData loadContent(long idOfUser) {

        final UserData[] userData = {null};

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < idOfUser) ? true : false;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {
                        return readable.rawQuery(
                                UserTableSetting.SQL_SELECT_WHERE_ID + idOfUser,
                                null
                        );
                    }

                    @Override
                    public void successRequest(Cursor result) {

                        if (result.moveToFirst()) {

                            userData[0] = new UserData();
                            userData[0].setId(result.getLong(0));                          // 0. id
                            userData[0].setName(result.getString(1));                      // 1. name
                            userData[0].setTargetScore(result.getInt(2));                  // 2. target score
                            userData[0].setSpeciality(result.getString(3));                // 3. speciality
                            userData[0].setGameRecordWin(result.getInt(4));                // 4. game record win
                            userData[0].setGameRecordLoss(result.getInt(5));               // 5. game record loss
                            userData[0].setRecentGameBilliardCount(result.getLong(6));     // 6. recent game billiard count
                            userData[0].setTotalPlayTime(result.getInt(7));                // 8. total play time
                            userData[0].setTotalCost(result.getInt(8));                    // 9. total cost

                        }
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, UserData.class.getSimpleName());
                    }
                }
        );


        return userData[0];
    } // End of method [loadContent]


    public ArrayList<UserData> loadAllContent() {

        ArrayList<UserData> userDataArrayList = new ArrayList<>();

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return false;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {

                        return readable.rawQuery(
                                UserTableSetting.SQL_SELECT_ALL_CONTENT,
                                null
                        );
                    }

                    @Override
                    public void successRequest(Cursor result) {

                        while (result.moveToNext()) {

                            // [lv/C]UserData : user 테이블의 '한 행'의 정보를 담는다.
                            UserData userData = new UserData();
                            userData.setId(result.getLong(0));                          // 0. id
                            userData.setName(result.getString(1));                      // 1. name
                            userData.setTargetScore(result.getInt(2));                  // 2. target score
                            userData.setSpeciality(result.getString(3));                // 3. speciality
                            userData.setGameRecordWin(result.getInt(4));                // 4. game record win
                            userData.setGameRecordLoss(result.getInt(5));               // 5. game record loss
                            userData.setRecentGameBilliardCount(result.getLong(6));     // 6. recent game billiard count
                            userData.setTotalPlayTime(result.getInt(7));                // 7. total play time
                            userData.setTotalCost(result.getInt(8));                    // 8. total cost

                            // [lv/C]ArrayList<UserData> : 위 의 '한 행'의 내용을 배열 형태로 담는다.
                            userDataArrayList.add(userData);

                        } // [cycle 1]
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, UserData.class.getSimpleName());
                    }
                }
        );

        return userDataArrayList;
    } // End of method [loadAllContent]


    // ==================================================== Update Query ====================================================
    public int updateContent(long id, int targetScore, String speciality) {

        final int numberOfUpdatedRows[] = {0};

        appDbSetting2.requestUpdateQuery(
                new AppDbSetting2.UpdateQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((0 < id) && (0 <= targetScore) && !speciality.endsWith("")) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        ContentValues updateValue = new ContentValues();
                        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, speciality);

                        String selection = UserTableSetting.Entry._ID + " LIKE ?";
                        String[] selectionArgs = {id + ""};

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
                        AppDbLog.printFormatErrorLog(CLASS_NAME, UserData.class.getSimpleName());
                    }
                }
        );

        return numberOfUpdatedRows[0];
    } // End of method [updateContent]


    /**
     * [method] [update] name, targetScore, speciality 값을 받아서 해당 id 의 user 의 정보를 갱신한다.
     *
     * @param id          [0] user 의 id
     * @param name        [1] user 의 이름
     * @param targetScore [2] user 의 수지
     * @param speciality  [3] user 의 종목
     * @return update query 문을 실행한 결과
     */
    public int updateContent(long id, String name, int targetScore, String speciality) {


        final int numberOfUpdatedRows[] = {0};

        appDbSetting2.requestUpdateQuery(
                new AppDbSetting2.UpdateQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return ((0 < id) && !name.equals("") && (0 <= targetScore) && !speciality.equals("")) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        ContentValues updateValue = new ContentValues();
                        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_NAME, name);
                        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, speciality);

                        String selection = UserTableSetting.Entry._ID + " LIKE ?";
                        String[] selectionArgs = {id + ""};

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
                        AppDbLog.printFormatErrorLog(CLASS_NAME, UserData.class.getSimpleName());
                    }
                }
        );

        return numberOfUpdatedRows[0];
    } // End of method [updateContent]


    /**
     * [method] [update] gameRecordWin, gameRecordLoss, recentGamePlayerId, recentPlayDate, totalPlayTime, totalCost 값을 받아서
     * 해당 id 의 user 의 정보를 갱신한다.
     *
     * @param id                      [0] user 의 id
     * @param gameRecordWin           [4] user 의 게임 승리 수
     * @param gameRecordLoss          [5] user 의 게임 패배 수
     * @param recentGameBilliardCount [6] user 의 최근 게임의 billiard count
     * @param totalPlayTime           [7] user 의 총 게임 시간
     * @param totalCost               [8] user 의 총 비용
     * @return update query 문을 실행한 결과
     */
    public int updateContent(long id, int gameRecordWin, int gameRecordLoss, long recentGameBilliardCount, int totalPlayTime, int totalCost) {

        final String METHOD_NAME = "[updateContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<id> 에 해당하는 <gameRecordWin> <gameRecordLoss> <recentGameBilliardCount> <totalPlayTime> <totalCost> 를 갱신합니다.");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : id, gameRecordWin, gameRecordLoss, recentGamePlayerId, recentPlayDate, totalPlayTime, totalCost 형식 확인
            if ((id > 0)                                    // 0. id
                    && (gameRecordWin >= 0)                 // 4. game record win
                    && (gameRecordLoss >= 0)                // 5. game record loss
                    && (recentGameBilliardCount >= 0)        // 6. recent game player id
                    && (totalPlayTime >= 0)                 // 7. total play time
                    && (totalCost >= 0)) {                  // 8. total cost

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase updateDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : 매개변수 값을 담을 객체 생성과 초기화
                ContentValues updateValue = new ContentValues();
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, recentGameBilliardCount);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);

                // [lv/i]methodResult : update query 문을 실행한 결과
                methodResult = updateDb.update(UserTableSetting.Entry.TABLE_NAME, updateValue, UserTableSetting.Entry._ID + "=" + id, null);

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


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    /**
     * [method] [delete] ProjectBlueDBHelper 로 writeableDatabase 를 가져와서 user 테이블의 모든 내용을 삭제한다.
     *
     * <p>
     * project_blue.db 의 user 테이블에 데이터를 delete query 문을 실행한다.
     *
     * <p>
     * 반환 값
     * '-1' : 데이터베이스 문재 발생
     * 0 이상 : 데이터베이스 delete query 실행 성공과 삭제한 데이터의 개수
     *
     * @return user 테이블의 데이터를 삭제한 개수 or 실패
     */
    public int deleteAllContent() {

        final String METHOD_NAME = "[deleteAllContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 데이터를 삭제합니다.");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
            SQLiteDatabase deleteDb = this.getDbOpenHelper().getWritableDatabase();

            // [lv/i]methodResult : delete query 문이 실행 된 결과를 받는다.
            methodResult = deleteDb.delete(UserTableSetting.Entry.TABLE_NAME, null, null);

            // [lv/C]SQLiteDatabase : close
            deleteDb.close();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return methodResult;

    } // End of method [deleteAllContent]


    /**
     * [method] [delete] ProjectBlueDBHelper 로 writeableDatabase 를 가져와서 user 테이블의 모든 내용을 삭제한다.
     *
     * <p>
     * project_blue.db 의 user 테이블에 데이터를 delete query 문을 실행한다.
     *
     * <p>
     * 반환 값
     * '-1' : 데이터베이스 문재 발생
     * 0 이상 : 데이터베이스 delete query 실행 성공과 삭제한 데이터의 개수
     *
     * @param id user 의 id
     * @return user 테이블의 데이터를 삭제한 개수 or 실패
     */
    public int deleteContent(long id) {

        final String METHOD_NAME = "[deleteContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<id> 에 해당하는 유저의 데이터를 삭제합니다.");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : id 는 0 보다 크다
            if (id > 0) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase deleteDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/i]methodResult : delete query 문이 실행 된 결과를 받는다.
                methodResult = deleteDb.delete(UserTableSetting.Entry.TABLE_NAME, UserTableSetting.Entry._ID + "=" + id, null);

                // [lv/C]SQLiteDatabase : close
                deleteDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수 id 가 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return methodResult;

    } // End of method [deleteContent]


    // ==================================================== Check Format ====================================================
    private boolean checkFormatOfUserData(UserData userData, boolean exceptsPrimaryKey) {

        // 0. id
        // 1. name
        // 2. target score
        // 3. speciality
        // 4. game record win
        // 5. game record loss
        // 6. recent game billiard count
        // 7. total play time
        // 8. total cost
        // 형식이 일치하는가?

        boolean matchesFormat = false;

        if (!exceptsPrimaryKey) {

            // primary key 포함하여 형식 검사
            if ((userData.getId() > 0) &&                               // 0. id (primary key)
                    !userData.getName().equals("") &&                   // 1. name                          -- 내용이 있어야 함
                    (userData.getTargetScore() >= 0) &&                 // 2. target score                  -- 0 보다 크거나 같다.
                    !userData.getSpeciality().equals("") &&             // 3. speciality                    -- not null
                    (userData.getGameRecordWin() >= 0) &&               // 4. game record win               -- 0 보다 크거나 같다.
                    (userData.getGameRecordLoss() >= 0) &&              // 5. game record loss              -- 0 보다 크거나 같다.
                    (userData.getRecentGameBilliardCount() >= 0) &&     // 6. recent game billiard count    -- 0 보다 크거나 같다.
                    (userData.getTotalPlayTime() >= 0) &&               // 7. total play time               -- 0 보다 크거나 같다.
                    (userData.getTotalCost() >= 0)) {                   // 8. total cost                    -- 0 보다 크거나 같다.
                matchesFormat = true;
            } else {
                matchesFormat = false;
            }

        } else {

            // primary key 제외하고 형식 검사
            if (!userData.getName().equals("") &&                       // 1. name                          -- 내용이 있어야 함
                    (userData.getTargetScore() >= 0) &&                 // 2. target score                  -- 0 보다 크거나 같다.
                    !userData.getSpeciality().equals("") &&             // 3. speciality                    -- not null
                    (userData.getGameRecordWin() >= 0) &&               // 4. game record win               -- 0 보다 크거나 같다.
                    (userData.getGameRecordLoss() >= 0) &&              // 5. game record loss              -- 0 보다 크거나 같다.
                    (userData.getRecentGameBilliardCount() >= 0) &&     // 6. recent game billiard count    -- 0 보다 크거나 같다.
                    (userData.getTotalPlayTime() >= 0) &&               // 7. total play time               -- 0 보다 크거나 같다.
                    (userData.getTotalCost() >= 0)) {                   // 8. total cost                    -- 0 보다 크거나 같다.
                matchesFormat = true;
            } else {
                matchesFormat = false;
            }

        }

        return matchesFormat;
    }
}
