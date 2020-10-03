package com.skyman.billiarddata.management.user.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

public class UserDbManager {

    // variable : SQLite DB open helper 객체 선언
    private UserDbHelper userDbHelper;

    // variable : 생성 요청하는 activity 관련 된 객체 선언
    private Context targetContext;

    // variable : init_db 실행 여부 확인
    private boolean initDb;

    // constructor
    public UserDbManager(Context targetContext) {
        this.userDbHelper = null;                       // 직관성을 위해, 객체 생성은 init_db 에서 한다.
        this.targetContext = targetContext;
        this.initDb = false;
    }


    /*                                      public method
     * =============================================================================================
     * =============================================================================================
     * */

    /* method : getter, setter */
    public boolean isInitDb() {
        return initDb;
    }

    /* method : init, SQLite DB open helper 를 이용하여 초기화 */
    public void init_db() {
        /*
         * =========================================================================================
         * project_blue.db 에 billiard, user, friend 테이블의 존재 여부 확인한다.
         * 없으면 project_blue.db 생성 후 billiard, user, friend 테이블을 생성하고,
         * project_blue.db 를 open 한다.
         * =========================================================================================
         * */
        userDbHelper = new UserDbHelper(targetContext);
        initDb = true;
        DeveloperManager.displayLog("[DbM] userDbManager", "[init_db] The method is complete. - initDb : " + initDb);
    }

    /* method : insert, SQLite DB Helper를 이용하여 해당 테이블에 정보를 insert 한다. */
    public long save_content(String name, int targetScore, String speciality, int gameRecordWin, int gameRecordLoss, int recentGamePlayerId, String recentPlayDate, int totalPlayTime, int totalCost) {
        /*
         * =========================================================================================
         * user table insert query
         * -    데이터를 매개변수로 받아서 모든 값을 입력 받은지 확인한다.
         *      그리고 ContentValues 의 객체에 내용 setting 을 한다.
         *      userDbHelper 를 통해 writeable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 user 테이블에 해당 내용을 insert 한다.
         * - 입력 순서
         *      1. name
         *      2. target score
         *      3. speciality
         *      4. game record win
         *      5. game record loss
         *      6. recent game player id
         *      7. recent play date
         *      8: total play time
         *      9: total cost
         * - return
         *      1. '-2' : 입력 조건이 만족하지 않음
         *      2. '-1' : 데이터베이스 insert 실패
         *      3. '0' : 기본 값
         *      4. '0' 보다 큰 수 : insert 된 곳의 행 번호
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM] userDbManager", "[save_content] The method is executing ............");

        // SQLiteDatabase : write database - userDbHelper 를 통해 write 용으로 가져오기
        SQLiteDatabase writeDb = userDbHelper.getWritableDatabase();

        // long : 이 메소드의 결과를 저장하기 위한 변수 선언, 행 번호
        long newRowId = 0;

        // check : 매개변수의 값이 특정 조건에 맞냐?
        if (!name.equals("") &&                                 // 1. name                      -- 내용이 있어야 함
                (targetScore >= 0) &&                           // 2. target score              -- 0 보다 크거나 같다.
                !speciality.equals("") &&                       // 3. speciality                -- not null
                (gameRecordWin >= 0) &&                         // 4. game record win           -- 0 보다 크거나 같다.
                (gameRecordLoss >= 0) &&                        // 5. game record loss          -- 0 보다 크거나 같다.
                (recentGamePlayerId >= 0) &&                     // 6. recent game player id     -- 0 보다 크거나 같다.
                !recentPlayDate.equals("") &&                   // 7. recent play date          -- 내용이 있어야 함
                (totalPlayTime >= 0) &&                         // 8. total play time           -- 0 보다 크거나 같다.
                (totalCost >= 0)                                // 9. total cost                -- 0 보다 크거나 같다.
        ) {
            DeveloperManager.displayLog("[DbM] userDbManager", "[save_content] 모든 조건이 통과되었습니다.");
            // ContentValues : 매개변수의 내용을 insertValues 에 셋팅하기
            ContentValues insertValues = new ContentValues();
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_NAME, name);                                    // 1. name
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);                     // 2. target score
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, speciality);                        // 3. speciality
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);                // 4. game record win
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);              // 5. game record loss
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_GAME_PLAYER_ID, recentGamePlayerId);     // 6. recent game player id
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_PLAY_DATE, recentPlayDate);              // 7. recent play date
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);                // 8. total play time
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);                         // 9. total cost

            /*  해당 user 테이블에 내용 insert
                nullColumnHack 이 'null'로 지정 되었다면?       values 객체의 어떤 열에 값이 없으면 지금 내용을 insert 안함.
                               이 '열 이름'이 지정 되었다면?    해당 열에 값이 없으면 null 값을 넣는다.*/

            // newRowId는 데이터베이스에 insert 가 실패하면 '-1'을 반환하고, 성공하면 해당 '행 번호'를 반환한다.
            newRowId = writeDb.insert(UserTableSetting.Entry.TABLE_NAME, null, insertValues);

            // check : 데이터베이스 입력이 실패, 성공 했는지 구분하여
            if (newRowId == -1) {
                // 데이터 insert 실패
                DeveloperManager.displayLog("[DbM] userDbManager", "[save_load] DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                return newRowId;
            } else {
                // 데이터 insert 성공
                DeveloperManager.displayLog("[DbM] userDbManager", "[save_load] " + newRowId + " 번째 입력이 성공하였습니다.");
            }
        } else {
            DeveloperManager.displayLog("[DbM] userDbManager", "[save_content] 입력 된 값이 조건에 만족하지 않습니다. : " + newRowId + " 값을 리턴합니다.");
            return -2;
        }

        // SQLiteDatabase : DB close
        writeDb.close();

        DeveloperManager.displayLog("[DbM] userDbManager", "[save_content] The method is complete!");
        return newRowId;
    }

    /* method : load, SQLite DB Helper 를 이용하여 해당 테이블에 모든 정보를 select 한다. */
    public ArrayList<UserData> load_contents() {
        /*
         * =========================================================================================
         * user table select query
         * -    userDbHelper 를 통해 readable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 user 테이블의 모든 내용을 읽어온다.
         *
         * - return : 읽어온 내용이 담기 ArrayList<UserData>
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM] UserDbManager", "[load_contents] The method is executing ............");

        // SQLiteDatabase : read database - userDbHelper 를 통해 read 용으로 가져오기
        SQLiteDatabase readDb = userDbHelper.getReadableDatabase();

        // Cursor : 해당 쿼리문으로 읽어온 테이블 내용을 readCursor 에 담는다.
        Cursor readCursor = readDb.rawQuery(UserTableSetting.SQL_SELECT_TABLE_ALL_ITEM, null);

        // ArrayList<UserData> : userDataArrayList declaration - readCursor 에 담긴 내용을 한 행씩 읽어 배열형태로 저장하기 위한 객체 생성
        ArrayList<UserData> userDataArrayList = new ArrayList<>();

        // cycle : Cursor 객체를 통해 하나씩 읽어온다.
        while (readCursor.moveToNext()) {
            // UserData : userData declaration -  readCursor 에 담긴 내용을 한 행씩 읽어 userData 에 저장
            UserData userData = new UserData();
            userData.setId(readCursor.getLong(0));                  // 0. id
            userData.setName(readCursor.getString(1));              // 1. name
            userData.setTargetScore(readCursor.getInt(2));          // 2. target score
            userData.setSpeciality(readCursor.getString(3));        // 3. speciality
            userData.setGameRecordWin(readCursor.getInt(4));        // 4. game record win
            userData.setGameRecordLoss(readCursor.getInt(5));       // 5. game record loss
            userData.setRecentGamePlayerId(readCursor.getLong(6));  // 6. recent game player id
            userData.setRecentPlayDate(readCursor.getString(7));    // 7. recent play time
            userData.setTotalPlayTime(readCursor.getInt(8));        // 8. total play time
            userData.setTotalCost(readCursor.getInt(9));            // 9. total cost

            // ArrayList<UserData> : 위의 내용을 가지는 배열 userDataArrayList 에 추가하여 저장
            userDataArrayList.add(userData);
        }

        // SQLiteDatabase : DB close
        readDb.close();

        DeveloperManager.displayLog("[DbM] UserDbManager", "[load_contents] The method is complete!");
        return userDataArrayList;
    }

    /* method : load, SQLite DB Helper 를 이용하여 해당 테이블에 모든 정보를 select 한다. */
    public UserData load_content() {
        /*
         * =========================================================================================
         * user table select query
         * -    userDbHelper 를 통해 readable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 에서 user 테이블의 첫 번째 내용을 select 한다.
         *
         * - return : 읽어온 내용이 담기 userData
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM] UserDbManager", "[load_content] The method is executing ............");

        // SQLiteDatabase : read database - userDbHelper 를 통해 read 용으로 가져오기
        SQLiteDatabase readDb = userDbHelper.getReadableDatabase();

        // Cursor : 해당 쿼리문으로 읽어온 테이블 내용을 readCursor 에 담는다.
        Cursor readCursor = readDb.rawQuery(UserTableSetting.SQL_SELECT_TABLE_ALL_ITEM, null);

        // UserData : userData declaration  - readCursor 를 통해 읽어온 한 행 정보를 담는 객체 선언
        UserData userData = new UserData();

        // check : Cursor 객체를 통해 가져온 데이터가 있는 지 검사한다.
        if (readCursor.moveToFirst()) {
            // UserData : userData 내용 넣기 - readCursor 을 통해 읽어온 내용 중 첫 번째 행의 내용을 넣는다.
            userData.setId(readCursor.getLong(0));                  // 0. id
            userData.setName(readCursor.getString(1));              // 1. name
            userData.setTargetScore(readCursor.getInt(2));          // 2. target score
            userData.setSpeciality(readCursor.getString(3));        // 3. speciality
            userData.setGameRecordWin(readCursor.getInt(4));        // 4. game record win
            userData.setGameRecordLoss(readCursor.getInt(5));       // 5. game record loss
            userData.setRecentGamePlayerId(readCursor.getLong(6));  // 6. recent game player id
            userData.setRecentPlayDate(readCursor.getString(7));    // 7. recent play time
            userData.setTotalPlayTime(readCursor.getInt(8));        // 8. total play time
            userData.setTotalCost(readCursor.getInt(9));            // 9. total cost
        } else {
            return null;
        }

        // SQLiteDatabase : DB close
        readDb.close();

        DeveloperManager.displayLog("[DbM] UserDbManager", "[load_content] The method is complete!");
        return userData;
    }

    /* method : delete - SQLite DB Open Helper 를 이용하여 user 테이블에 있던 내용을 모두 delete 한다.*/
    public int delete_contents() {
        /*
         * =========================================================================================
         * user table select query
         * -    userDbHelper 를 통해 writable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 에서 user 테이블의 모든 내용을 delete 한다.
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM] UserDbManager", "[delete_contents] The method is executing ............");

        // SQLiteDatabase : write database - userDbHelper 를 write 용으로 가져오기
        SQLiteDatabase deleteDb = userDbHelper.getWritableDatabase();

        // SQLiteDatabase : 모든 내용 삭제
        int deleteResult = deleteDb.delete(UserTableSetting.Entry.TABLE_NAME, null, null);

        // SQLiteDatabase : DB close
        deleteDb.close();

        DeveloperManager.displayLog("[DbM] UserDbManager", "[delete_contents] The method is complete!");
        return deleteResult;
    }

    /* method : update - SQLite DB Open Helper 를 이용하여 user 테이블에 있던 내용을 update 한다.*/
    public int update_content(String name, int targetScore, String speciality) {
        /*
         * =========================================================================================
         * user table insert query
         * -    데이터를 매개변수로 받아서 모든 값을 입력 받은지 확인한다.
         *      그리고 ContentValues 의 객체에 내용 setting 을 한다.
         *      userDbHelper 를 통해 writeable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 user 테이블에 해당 내용을 update 한다.
         * - 매개 변수
         *      1. name
         *      2. target score
         *      3. speciality
         * - return : update 성공 여부
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM] UserDbManager", "[load_contents] The method is executing ............");
        // SQLiteDatabase : userDbHelper 를 이용하여 write 용으로 가져오기
        SQLiteDatabase updateDb = userDbHelper.getWritableDatabase();

        // ContentValues :
        ContentValues updateValue = new ContentValues();
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_NAME, name);
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, speciality);

        // SQLiteDatabase : update query 수행
        int updateResult = updateDb.update(UserTableSetting.Entry.TABLE_NAME, updateValue, UserTableSetting.Entry._ID + "=1", null);

        DeveloperManager.displayLog("[DbM] UserDbManager", "[load_contents] The method is complete!");
        return updateResult;
    }

    /* method : update - SQLite DB Open Helper 를 이용하여 user 테이블에 있던 내용을 update 한다. */
    public int update_content(long id, int gameRecordWin, int gameRecordLoss, long recentGamePlayerId, String recentPlayDate, int totalPlayTime, int totalCost) {
        /*
         * =========================================================================================
         * user table insert query
         * -    데이터를 매개변수로 받아서 모든 값을 입력 받은지 확인한다.
         *      그리고 ContentValues 의 객체에 내용 setting 을 한다.
         *      userDbHelper 를 통해 writeable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 user 테이블에 해당 내용을 update 한다.
         * - 매개 변수
         *      0. id
         *      4. game record win
         *      5. game record loss
         *      6. recent game player id
         *      7. recent play date
         *      8. total play time
         *      9. total cost
         * - return : update 성공 여부
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM] UserDbManager", "[update_content] The method is executing ............");
        // SQLiteDatabase : userDbHelper 를 이용하여 write 용으로 가져오기
        SQLiteDatabase updateDb = userDbHelper.getWritableDatabase();

        // ContentValues :
        ContentValues updateValue = new ContentValues();
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_GAME_PLAYER_ID, recentGamePlayerId);
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_PLAY_DATE, recentPlayDate);
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);
        updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);

        // SQLiteDatabase : update query 수행
        int updateResult = updateDb.update(UserTableSetting.Entry.TABLE_NAME, updateValue, UserTableSetting.Entry._ID + "=" + id, null);

        DeveloperManager.displayLog("[DbM] UserDbManager", "[update_content] The method is complete!");
        return updateResult;
    }

    /* method : userDbHelper 를 close 한다. */
    public void closeUserDbHelper() {
        if (initDb) {
            userDbHelper.close();
            DeveloperManager.displayLog("[DbM] userDbHelper", "[closeUserDbHelper] userDbHelper is closed.");
        } else {
            DeveloperManager.displayLog("[DbM] userDbHelper", "[closeUserDbHelper] userDbHelper 가 초기화되지 않았습니다.");
        }
    }


    /*                                      private method
     * =============================================================================================
     * =============================================================================================
     * */

    /* method : display, toast 메시지 출력 */
    private void toastHandler(String content) {
        Toast myToast = Toast.makeText(targetContext, content, Toast.LENGTH_SHORT);
        myToast.show();
    }
}
