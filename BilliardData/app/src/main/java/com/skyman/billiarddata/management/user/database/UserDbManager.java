package com.skyman.billiarddata.management.user.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.projectblue.database.AppDbSetting;
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
public class UserDbManager extends AppDbSetting {

    // constant
    private final String CLASS_NAME_LOG = "[DbM]_userDbManager";

    // constructor
    public UserDbManager(Context targetContext) {
        super(targetContext);
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


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
     * @param name [1] 이름
     * @param targetScore [2] 수지
     * @param speciality [3] 주종목
     * @param gameRecordWin [4] 승리 횟수
     * @param gameRecordLoss [5] 패배 횟수
     * @param recentGameBilliardCount [6] 최근 게임 billiard count
     * @param totalPlayTime [7] 총 게임 시간
     * @param totalCost [8] 총 비용
     * @return user 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(String name,                    // 1. name
                            int targetScore,                // 2. target score
                            String speciality,              // 3. speciality
                            int gameRecordWin,              // 4. game record win
                            int gameRecordLoss,             // 5. game record loss
                            int recentGameBilliardCount,    // 6. recent game billiard count
                            int totalPlayTime,              // 7. total play time
                            int totalCost) {                // 8. total cost

        final String METHOD_NAME= "[saveContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<name> <targetScore> <speciality> <gameRecordWin> <gameRecordLoss> <recentGameBilliardCount> <totalPlayTime> <totalCost> 를 저장합니다.");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : openDbHelper 가 초기화 되었다.
            if (!name.equals("") &&                                 // 1. name                          -- 내용이 있어야 함
                    (targetScore >= 0) &&                           // 2. target score                  -- 0 보다 크거나 같다.
                    !speciality.equals("") &&                       // 3. speciality                    -- not null
                    (gameRecordWin >= 0) &&                         // 4. game record win               -- 0 보다 크거나 같다.
                    (gameRecordLoss >= 0) &&                        // 5. game record loss              -- 0 보다 크거나 같다.
                    (recentGameBilliardCount >= 0) &&               // 6. recent game billiard count    -- 0 보다 크거나 같다.
                    (totalPlayTime >= 0) &&                         // 7. total play time               -- 0 보다 크거나 같다.
                    (totalCost >= 0)) {                             // 8. total cost                    -- 0 보다 크거나 같다.

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase writeDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_NAME, name);                                            // 1. name
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);                             // 2. target score
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, speciality);                                // 3. speciality
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);                        // 4. game record win
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);                      // 5. game record loss
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, recentGameBilliardCount);   // 6. recent game billiard count
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);                        // 7. total play time
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);                                 // 8. total cost

                // [lv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.
                newRowId = writeDb.insert(UserTableSetting.Entry.TABLE_NAME, null, insertValues);

                // [lv/C]SQLiteDatabase : close
                writeDb.close();

                // [check 3] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {

                    // 데이터 insert 실패
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + newRowId + " 번째 입력이 성공하였습니다.");
                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들이 형식에 맞지 않아요.");
                newRowId = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return newRowId;

    } // End of method [saveContent]


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
     * @param userData user 의 데이터가 들어있는 DTO
     * @return user 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(UserData userData) {                // 8. total cost

        final String METHOD_NAME= "[saveContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiardData> 를 저장하고 있습니다.");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : openDbHelper 가 초기화 되었다.
            if (!userData.getName().equals("") &&                                 // 1. name                          -- 내용이 있어야 함
                    (userData.getTargetScore() >= 0) &&                           // 2. target score                  -- 0 보다 크거나 같다.
                    !userData.getSpeciality().equals("") &&                       // 3. speciality                    -- not null
                    (userData.getGameRecordWin() >= 0) &&                         // 4. game record win               -- 0 보다 크거나 같다.
                    (userData.getGameRecordLoss() >= 0) &&                        // 5. game record loss              -- 0 보다 크거나 같다.
                    (userData.getRecentGameBilliardCount() >= 0) &&               // 6. recent game billiard count    -- 0 보다 크거나 같다.
                    (userData.getTotalPlayTime() >= 0) &&                         // 7. total play time               -- 0 보다 크거나 같다.
                    (userData.getTotalCost() >= 0)) {                             // 8. total cost                    -- 0 보다 크거나 같다.

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase writeDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_NAME, userData.getName());                                            // 1. name
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, userData.getTargetScore());                             // 2. target score
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, userData.getSpeciality());                                // 3. speciality
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, userData.getGameRecordWin());                        // 4. game record win
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, userData.getGameRecordLoss());                      // 5. game record loss
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT, userData.getRecentGameBilliardCount());   // 6. recent game billiard count
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, userData.getTotalPlayTime());                        // 7. total play time
                insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, userData.getTotalCost());                                 // 8. total cost

                // [lv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.
                newRowId = writeDb.insert(UserTableSetting.Entry.TABLE_NAME, null, insertValues);

                // [lv/C]SQLiteDatabase : close
                writeDb.close();

                // [check 3] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {

                    // 데이터 insert 실패
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + newRowId + " 번째 입력이 성공하였습니다.");
                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들이 형식에 맞지 않아요.");
                newRowId = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return newRowId;

    } // End of method [saveContent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


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

        final String METHOD_NAME= "[loadAllContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 데이터를 가져오고 있습니다.");

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
                userData.setId(readCursor.getLong(0));                          // 0. id
                userData.setName(readCursor.getString(1));                      // 1. name
                userData.setTargetScore(readCursor.getInt(2));                  // 2. target score
                userData.setSpeciality(readCursor.getString(3));                // 3. speciality
                userData.setGameRecordWin(readCursor.getInt(4));                // 4. game record win
                userData.setGameRecordLoss(readCursor.getInt(5));               // 5. game record loss
                userData.setRecentGameBilliardCount(readCursor.getLong(6));     // 6. recent game billiard count
                userData.setTotalPlayTime(readCursor.getInt(7));                // 7. total play time
                userData.setTotalCost(readCursor.getInt(8));                    // 8. total cost

                // [lv/C]ArrayList<UserData> : 위 의 '한 행'의 내용을 배열 형태로 담는다.
                userDataArrayList.add(userData);

            } // [cycle 1]

            // SQLiteDatabase : DB close / cursor 를 다 이용한 뒤 close 한다. / 이유??
            readDb.close();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
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

        final String METHOD_NAME= "[loadContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<id> 로 해당 데이터를 가져오고 있습니다.");

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
                    userData.setId(readCursor.getLong(0));                          // 0. id
                    userData.setName(readCursor.getString(1));                      // 1. name
                    userData.setTargetScore(readCursor.getInt(2));                  // 2. target score
                    userData.setSpeciality(readCursor.getString(3));                // 3. speciality
                    userData.setGameRecordWin(readCursor.getInt(4));                // 4. game record win
                    userData.setGameRecordLoss(readCursor.getInt(5));               // 5. game record loss
                    userData.setRecentGameBilliardCount(readCursor.getLong(6));     // 6. recent game billiard count
                    userData.setTotalPlayTime(readCursor.getInt(7));                // 8. total play time
                    userData.setTotalCost(readCursor.getInt(8));                    // 9. total cost

                } else {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 에서 가져온 데이터가 없습니다.");
                    return null;
                } // [check 3]

                // SQLiteDatabase : DB close
                readDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수 id 가 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return userData;
    } // End of method [loadContent]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    /**
     * [method] [update] name, targetScore, speciality 값을 받아서 해당 id 의 user 의 정보를 갱신한다.
     *
     * @param id          [0] user 의 id
     * @param targetScore [2] user 의 수지
     * @param speciality  [3] user 의 종목
     * @return update query 문을 실행한 결과
     */
    public int updateContent(long id, int targetScore, String speciality) {

        final String METHOD_NAME= "[updateContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<id> 에 해당하는 <targetScore> <speciality> 를 갱신합니다.");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : id, name, targetScore, speciality 형식 확인
            if ((id > 0)                                    // 0. id
                    && (targetScore >= 0)                    // 2. target score
                    && !speciality.equals("")) {            // 3. speciality

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase updateDb = this.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : 위 의 매개변수 값을 담을 객체 생성과 초기화
                ContentValues updateValue = new ContentValues();
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                updateValue.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, speciality);

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

        final String METHOD_NAME= "[updateContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<id> 에 해당하는 <name> <targetScore>, <speciality> 를 갱신합니다.");

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
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들이 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return methodResult;
    } // End of method [updateContent]


    /**
     * [method] [update] gameRecordWin, gameRecordLoss, recentGamePlayerId, recentPlayDate, totalPlayTime, totalCost 값을 받아서
     * 해당 id 의 user 의 정보를 갱신한다.
     *
     * @param id                        [0] user 의 id
     * @param gameRecordWin             [4] user 의 게임 승리 수
     * @param gameRecordLoss            [5] user 의 게임 패배 수
     * @param recentGameBilliardCount   [6] user 의 최근 게임의 billiard count
     * @param totalPlayTime             [7] user 의 총 게임 시간
     * @param totalCost                 [8] user 의 총 비용
     * @return update query 문을 실행한 결과
     */
    public int updateContent(long id, int gameRecordWin, int gameRecordLoss, long recentGameBilliardCount, int totalPlayTime, int totalCost) {

        final String METHOD_NAME= "[updateContent] ";

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

        final String METHOD_NAME= "[deleteAllContent] ";

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

        final String METHOD_NAME= "[deleteContent] ";

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

}
