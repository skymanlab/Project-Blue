package com.skyman.billiarddata.management.user.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.projectblue.database.ProjectBlueDBManager;
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
public class UserDbManager extends ProjectBlueDBManager {

    // instance variable : 그 전 class
    private UserDbHelper userDbHelper;
    private boolean initDb;

    // constructor
    public UserDbManager(Context targetContext) {
        super(targetContext);
        this.userDbHelper = null;
        this.initDb = false;
    }


    /**
     * [method] [insert] ProjectBlueDBHelper 에서 writeableDatabase 를 가져와 user 테이블에 데이터를 저장한다.
     *
     * <p>
     * project_blue.db 의 user 테이블에 데이터를 insert query 문을 실행한다.
     *
     * <p>
     * 반환값
     * '-2' : 매개변수로 받은 값들이 형식에 맞지 않는다.
     * '-1' : 데이터베이스 문제 발생
     * '0' : 코드들이 실행되지 안았다.
     * 그 외의 값 : user 테이블에 입력 된 행 번호
     *
     * <p>
     * ContentValues 의 nullColumnHack 이 'null' 이라면, values 객체의 어떤 열에 값이 없으면 지금 내용을 insert query 가 실행 안 된다.
     * 이 '열 이름' 이라면, 해당 열에 값이 없다면 'null' 값을 넣는다.
     *
     * @return user 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(String name,                    // 1. name
                            int targetScore,                // 2. target score
                            String speciality,              // 3. speciality
                            int gameRecordWin,              // 4. game record win
                            int gameRecordLoss,             // 5. game record loss
                            int recentGamePlayerId,         // 6. recent game player id
                            String recentPlayDate,          // 7. recent play date
                            int totalPlayTime,              // 8. total play time
                            int totalCost) {                // 9. total cost

        DeveloperManager.displayLog("[DbM]_userDbManager", "[saveContent] The method is executing ............");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : openDbHelper 가 초기화 되었다.
            if (!name.equals("") &&                                 // 1. name                      -- 내용이 있어야 함
                    (targetScore >= 0) &&                           // 2. target score              -- 0 보다 크거나 같다.
                    !speciality.equals("") &&                       // 3. speciality                -- not null
                    (gameRecordWin >= 0) &&                         // 4. game record win           -- 0 보다 크거나 같다.
                    (gameRecordLoss >= 0) &&                        // 5. game record loss          -- 0 보다 크거나 같다.
                    (recentGamePlayerId >= 0) &&                    // 6. recent game player id     -- 0 보다 크거나 같다.
                    !recentPlayDate.equals("") &&                   // 7. recent play date          -- 내용이 있어야 함
                    (totalPlayTime >= 0) &&                         // 8. total play time           -- 0 보다 크거나 같다.
                    (totalCost >= 0)) {                             // 9. total cost                -- 0 보다 크거나 같다.

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase writeDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
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

                // [lv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.
                newRowId = writeDb.insert(UserTableSetting.Entry.TABLE_NAME, null, insertValues);

                // [lv/C]SQLiteDatabase : close
                writeDb.close();

                // [check 3] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {

                    // 데이터 insert 실패
                    DeveloperManager.displayLog("[DbM]_userDbManager", "[saveContent] DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog("[DbM]_userDbManager", "[saveContent] " + newRowId + " 번째 입력이 성공하였습니다.");
                } // [check 3]

            } else {
                DeveloperManager.displayLog("[DbM]_userDbManager", "[saveContent] 매개변수들이 형식에 맞지 않아요.");
                newRowId = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_userDbManager", "[saveContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_userDbManager", "[saveContent] The method is complete!");
        return newRowId;

    } // End of method [saveContent]


    /**
     * [method] [select] ProjectBlueDBHelper 에서 readableDatabase 를 가져와 user 테이블에 저장된 데이터를 모두 가져온다.
     *
     * <p>
     * project_blue.db 의 user 테이블에 데이터를 select query 문을 실행한다.
     *
     * <p>
     * 이 데이터는 ArrayList 로 만들어진 객체에 추가되어 참조값을 리턴한다.
     *
     * @return user 테이블에 저장된 모든 데이터
     */
    public ArrayList<UserData> loadAllContent() {
        /*
         * =========================================================================================
         * user table select query
         * -    userDbHelper 를 통해 readable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 user 테이블의 모든 내용을 읽어온다.
         *
         * - return : 읽어온 내용이 담기 ArrayList<UserData>
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM]_UserDbManager", "[loadAllContent] The method is executing ............");

        // [lv/C]ArrayList<UserData> : user 테이블에 저장된 모든 데이커가 담길 ArrayList
        ArrayList<UserData> userDataArrayList = new ArrayList<>();

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
            SQLiteDatabase readDb = this.getDbOpenHelper().getReadableDatabase();

            // [lv/C]Cursor : select query 문의 실행 결과가 담길 Cursor
            Cursor readCursor = readDb.rawQuery(UserTableSetting.SQL_SELECT_ALL_CONTENT, null);

            // [cycle 1] : cursor 의 객체의 moveToNext method 를 이용하여 가져온 데이터가 있을 때까지
            while (readCursor.moveToNext()) {

                // [lv/C]UserData : user 테이블의 '한 행'의 정보를 담는다.
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

                // [lv/C]ArrayList<UserData> : 위 의 '한 행'의 내용을 배열 형태로 담는다.
                userDataArrayList.add(userData);

            } // [cycle 1]

            // SQLiteDatabase : DB close / cursor 를 다 이용한 뒤 close 한다. / 이유??
            readDb.close();

        } else {
            DeveloperManager.displayLog("[DbM]_UserDbManager", "[loadAllContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[loadAllContent] The method is complete!");
        return userDataArrayList;

    } // End of method [loadAllContent]


    /**
     * [method] [select] ProjectBlueDBHelper 에서 readableDatabase 를 가져와 user 테이블에 저장된 데이터를 특정 id 의 데이터만 가져온다.
     *
     * <p>
     * project_blue.db 의 user 테이블에 데이터를 select query 문을 실행한다.
     *
     * <p>
     * 이 데이터는 ArrayList 로 만들어진 객체에 추가되어 참조값을 리턴한다.
     *
     * @param id 가져오고자 하는 유저의 id
     * @return user 테이블에 저장된 모든 데이터
     */
    public UserData loadContent(long id) {

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[loadContent] The method is executing ............");

        // [lv/C]UserData : 가져온 데이터를 담을 UserData
        UserData userData = new UserData();

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : id 가 0 보다 크다.
            if (id > 0) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase readDb = this.getDbOpenHelper().getReadableDatabase();

                // [lv/C]Cursor : select query 문의 실행 결과가 담길 Cursor
                Cursor readCursor = readDb.rawQuery(UserTableSetting.SQL_SELECT_WHERE_ID + id, null);

                // [check 3] :  cursor 의 객체의 moveToFirst method 를 이용하여 가져온 데이터가 첫 번째 데이터가 있다.
                if (readCursor.moveToFirst()) {

                    // [lv/C]UserData : 가져온 데이터를 UserData 으로 만든다.
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
                    DeveloperManager.displayLog("[DbM]_UserDbManager", "[loadContent] userData 에서 가져온 데이터가 없습니다.");
                    return null;
                } // [check 3]

                // SQLiteDatabase : DB close
                readDb.close();

            } else {
                DeveloperManager.displayLog("[DbM]_UserDbManager", "[loadContent] 매개변수 id 가 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_UserDbManager", "[loadContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[loadContent] The method is complete!");
        return userData;
    } // End of method [loadContent]


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

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[updateContent] The method is executing ............");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : id, name, targetScore, speciality 형식 확인
            if ((id > 0)                                    // 0. id
                    && !name.equals("")                     // 1. name
                    && (targetScore >= 0)                    // 2. target score
                    && !speciality.equals("")) {            // 3. speciality

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase updateDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : 위 의 매개변수 값을 담을 객체 생성과 초기화
                ContentValues updateValue = new ContentValues();
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_NAME, name);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, speciality);

                // [lv/i]methodResult : update query 문을 실행한 결과
                methodResult = updateDb.update(UserTableSetting.Entry.TABLE_NAME, updateValue, UserTableSetting.Entry._ID + "=" + id, null);

                // [lv/C]SQLiteDatabase : close
                updateDb.close();

            } else {
                DeveloperManager.displayLog("[DbM]_userDbManager", "[saveContent] 매개변수들이 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_UserDbManager", "[updateContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[updateContent] The method is complete!");
        return methodResult;
    } // End of method [updateContent]


    /**
     * [method] [update] gameRecordWin, gameRecordLoss, recentGamePlayerId, recentPlayDate, totalPlayTime, totalCost 값을 받아서
     * 해당 id 의 user 의 정보를 갱신한다.
     *
     * @param id                 [0] user 의 id
     * @param gameRecordWin      [4] user 의 게임 승리 수
     * @param gameRecordLoss     [5] user 의 게임 패배 수
     * @param recentGamePlayerId [6] user 의 최근 게임 플레이어 id (friend id)
     * @param recentPlayDate     [7] user 의 최근 게임 날짜
     * @param totalPlayTime      [8] user 의 총 게임 시간
     * @param totalCost          [9] user 의 총 비용
     * @return update query 문을 실행한 결과
     */
    public int updateContent(long id, int gameRecordWin, int gameRecordLoss, long recentGamePlayerId, String recentPlayDate, int totalPlayTime, int totalCost) {

        DeveloperManager.displayLog("[DbM]_userDbManager", "[updateContent] The method is executing ............");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : id, gameRecordWin, gameRecordLoss, recentGamePlayerId, recentPlayDate, totalPlayTime, totalCost 형식 확인
            if ((id > 0)                                // 0. id
                    && (gameRecordWin >= 0)              // 4. game record win
                    && (gameRecordLoss >= 0)             // 5. game record loss
                    && (recentGamePlayerId >= 0)         // 6. recent game player id
                    && !recentPlayDate.equals("")       // 7. recent play date
                    && (totalPlayTime >= 0)              // 8. total play time
                    && (totalCost >= 0)) {               // 9. total cost

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase updateDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : 매개변수 값을 담을 객체 생성과 초기화
                ContentValues updateValue = new ContentValues();
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_GAME_PLAYER_ID, recentGamePlayerId);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_PLAY_DATE, recentPlayDate);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);

                // [lv/i]methodResult : update query 문을 실행한 결과
                methodResult = updateDb.update(UserTableSetting.Entry.TABLE_NAME, updateValue, UserTableSetting.Entry._ID + "=" + id, null);

                // [lv/C]SQLiteDatabase : close
                updateDb.close();

            } else {
                DeveloperManager.displayLog("[DbM]_userDbManager", "[updateContent] 매개변수들이 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_userDbManager", "[updateContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_userDbManager", "[updateContent] The method is complete!");

        return methodResult;
    } // End of method [updateContent]


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

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[deleteAllContent] The method is executing ............");

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
            DeveloperManager.displayLog("[DbM]_UserDbManager", "[deleteAllContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[deleteAllContent] The method is complete!");
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

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[deleteContent] The method is executing ............");

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
                DeveloperManager.displayLog("[DbM]_UserDbManager", "[deleteContent] 매개변수 id 가 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_UserDbManager", "[deleteContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_UserDbManager", "[deleteContent] The method is complete!");
        return methodResult;

    } // End of method [deleteContent]












    /*                                              그 전 클래스의 메소드
     * ================================================================================================================================================
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
        userDbHelper = new UserDbHelper(super.getTargetContext());
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
    }// =============================


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
        Cursor readCursor = readDb.rawQuery(UserTableSetting.SQL_SELECT_ALL_CONTENT, null);

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
    }  // ===============================================================================================


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
        Cursor readCursor = readDb.rawQuery(UserTableSetting.SQL_SELECT_ALL_CONTENT, null);

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
    } // ===================================================


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
    }   // ===================================================


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
    } // =======================================


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
    }  //=================================


    /* method : userDbHelper 를 close 한다. */
    public void closeUserDbHelper() {
        if (initDb) {
            userDbHelper.close();
            DeveloperManager.displayLog("[DbM] userDbHelper", "[closeUserDbHelper] userDbHelper is closed.");
        } else {
            DeveloperManager.displayLog("[DbM] userDbHelper", "[closeUserDbHelper] userDbHelper 가 초기화되지 않았습니다.");
        }
    }

}
