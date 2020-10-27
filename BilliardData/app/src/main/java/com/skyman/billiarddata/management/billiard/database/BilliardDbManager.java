package com.skyman.billiarddata.management.billiard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.projectblue.database.ProjectBlueDBManager;

import java.util.ArrayList;


/**
 * [class] project_blue.db 의 billiard 테이블을 관리하기 위한 클래스이다.
 *
 * <p>
 * ProjectBlueDBManger 를 상속 받아
 * ProjectBlueDBHelper 를 생성한다.
 * 이 openDBHelper 에서 readableDatabase 와 writeableDatabase 를 가져와서 query 문을 실행한다.
 */
public class BilliardDbManager extends ProjectBlueDBManager {

    // constant
    private final String CLASS_NAME_LOG = "[DbM]_BilliardDbManager";

    // constructor
    public BilliardDbManager(Context targetContext) {
        super(targetContext);
    }


    /**
     * [method] [insert] ProjectBlueDBHelper 에서 writeableDatabase 를 가져와 billiard 테이블에 데이터를 저장한다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 insert query 문을 실행한다.
     *
     * <p>
     * 반환값
     * '-2' : 매개변수로 받은 값들이 형식에 맞지 않는다.
     * '-1' : 데이터베이스 문제 발생
     * '0' : 코드들이 실행되지 안았다.
     * 그 외의 값 : billiard 테이블에 입력 된 행 번호
     *
     * <p>
     * ContentValues 의 nullColumnHack 이 'null' 이라면, values 객체의 어떤 열에 값이 없으면 지금 내용을 insert query 가 실행 안 된다.
     * 이 '열 이름' 이라면, 해당 열에 값이 없다면 'null' 값을 넣는다.
     *
     * @return billiard 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(String date,            // 1. date
                            String gameMode,        // 2. game mode
                            int playerCount,        // 3. player count
                            long winnerId,          // 4. winner Id
                            String winnerName,      // 5. winner name
                            int playTime,           // 6. play time
                            String score,           // 7. score
                            int cost) {             // 8. cost

        final String METHOD_NAME = "[saveContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing ............");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수의 형식이 맞는 지 검사한다.
            if (!date.equals("")                    // 1. date          / String
                    && !gameMode.equals("")         // 2. game mode     / String
                    && (playerCount >= 0)           // 3. player time   / int
                    && (winnerId > 0)               // 4. winner id     / long - 게임을 save 한다는 건 참가한 player 가 있다는 말, 모든 player 아이디는 0 보다 큰 값이다.
                    && !winnerName.equals("")       // 5. winner name   / string
                    && (playTime >= 0)              // 6. play time     / int
                    && !score.equals("")            // 7. score         / String
                    && (cost >= 0)) {               // 8. cost          / int

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기            ==> SQLiteDatabase 는 모든 조건이 만족 했을 때 가져와서
                SQLiteDatabase writeDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, date);                    // 1. date
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, gameMode);           // 2. game mode
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAYER_COUNT, playerCount);     // 3. player count
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_ID, winnerId);           // 4. winner id
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_NAME, winnerName);       // 5. winner name
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, playTime);           // 6. play time
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, score);                  // 7. score
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, cost);                    // 8. cost

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


    /**
     * [method] [insert] ProjectBlueDBHelper 에서 writeableDatabase 를 가져와 billiard 테이블에 데이터를 저장한다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 insert query 문을 실행한다.
     *
     * <p>
     * 반환값
     * '-2' : 매개변수로 받은 값들이 형식에 맞지 않는다.
     * '-1' : 데이터베이스 문제 발생
     * '0' : 코드들이 실행되지 안았다.
     * 그 외의 값 : billiard 테이블에 입력 된 행 번호
     *
     * <p>
     * ContentValues 의 nullColumnHack 이 'null' 이라면, values 객체의 어떤 열에 값이 없으면 지금 내용을 insert query 가 실행 안 된다.
     * 이 '열 이름' 이라면, 해당 열에 값이 없다면 'null' 값을 넣는다.
     *
     * @return billiard 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(BilliardData billiardData) {

        final String METHOD_NAME = "[saveContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing ............");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수의 형식이 맞다.
            if (checkFormatOfBilliardData(billiardData)) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기            ==> SQLiteDatabase 는 모든 조건이 만족 했을 때 가져와서
                SQLiteDatabase writeDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, billiardData.getDate());                  // 1. date
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, billiardData.getGameMode());         // 2. game mode
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAYER_COUNT, billiardData.getPlayerCount());   // 3. player count
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_ID, billiardData.getWinnerId());         // 4. winner id
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_NAME, billiardData.getWinnerName());     // 5. winner name
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, billiardData.getPlayTime());         // 6. play time
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, billiardData.getScore());                // 7. score
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, billiardData.getCost());                  // 8. cost

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


    /**
     * [method] [select] ProjectBlueDBHelper 에서 readableDatabase 를 가져와 billiard 테이블에 저장된 데이터를 모두 가져온다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 select query 문을 실행한다.
     *
     * <p>
     * 이 데이터는 ArrayList 로 만들어진 객체에 추가되어 참조값을 리턴한다.
     *
     * @return billiard 테이블에 저장된 모든 데이터
     */
    /* method : load, SQLite DB Helper 를 이용하여 해당 테이블의 정보를 가져온다. */
    public ArrayList<BilliardData> loadAllContent() {

        final String METHOD_NAME = "[loadAllContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing........");

        // [lv/C]ArrayList<BilliardDataT> : billiard 테이블에 저장된 모든 데이커가 담길 ArrayList
        ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();

        // [check  1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기 / declaration & create
            SQLiteDatabase readDb = super.getDbOpenHelper().getReadableDatabase();

            // [lv/C]Cursor : select query 문의 실행 결과가 담길 Cursor / use
            Cursor readCursor = readDb.rawQuery(BilliardTableSetting.SQL_SELECT_ALL_CONTENT, null);

            // [cycle 1] : cursor 의 객체의 moveToNext method 를 이용하여 가져온 데이터가 있을 때까지
            while (readCursor.moveToNext()) {

                // [lv/C]BilliardData : billiard 테이블의 '한 행'의 정보를 담는다.
                BilliardData billiardData = new BilliardData();
                billiardData.setCount(readCursor.getLong(0));
                billiardData.setDate(readCursor.getString(1));
                billiardData.setGameMode(readCursor.getString(2));
                billiardData.setPlayerCount(readCursor.getInt(3));
                billiardData.setWinnerId(readCursor.getLong(4));
                billiardData.setWinnerName(readCursor.getString(5));
                billiardData.setPlayTime(readCursor.getInt(6));
                billiardData.setScore(readCursor.getString(7));
                billiardData.setCost(readCursor.getInt(8));

                // [lv/C]ArrayList<BilliardData> : 위 의 '한 행'의 내용을 배열 형태로 담는다.
                billiardDataArrayList.add(billiardData);

            } // [cycle 1]

            // [lv/C]SQLiteDatabase : close / end
            readDb.close();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");

        return billiardDataArrayList;
    } // End of method [loadAllContent]


    /**
     * [method] [select] billiard 테이블의 count 값으로 해당 레코드 하나를 가져온다.
     *
     * @param count billiard 의 count
     * @return billiard 테이블에 저장된 모든 데이터
     */
    /* method : load, SQLite DB Helper 를 이용하여 해당 테이블의 정보를 가져온다. */
    public BilliardData loadAllContentByCount(long count) {

        final String METHOD_NAME = "[loadAllContentByCount] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing........");

        // [lv/C]BilliardData : billiard 테이블에 해당 count 로 가져온 데이터가 담기는 객체
        BilliardData billiardData = null;

        // [check  1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기 / declaration & create
            SQLiteDatabase readDb = super.getDbOpenHelper().getReadableDatabase();

            // [lv/C]Cursor : select query 문의 실행 결과가 담길 Cursor / use
            Cursor readCursor = readDb.rawQuery(BilliardTableSetting.SQL_SELECT_WHERE_COUNT + count, null);

            // [check 2] : readCursor 의 첫 번째 데이터가 있다.
            if (readCursor.moveToFirst()) {

                // [lv/C]BilliardData : billiard 테이블의 '한 행'의 정보를 담는다.
                billiardData = new BilliardData();
                billiardData.setCount(readCursor.getLong(0));
                billiardData.setDate(readCursor.getString(1));
                billiardData.setGameMode(readCursor.getString(2));
                billiardData.setPlayerCount(readCursor.getInt(3));
                billiardData.setWinnerId(readCursor.getLong(4));
                billiardData.setWinnerName(readCursor.getString(5));
                billiardData.setPlayTime(readCursor.getInt(6));
                billiardData.setScore(readCursor.getString(7));
                billiardData.setCost(readCursor.getInt(8));

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "해당 count 의 billiard 레코드가 없습니다.");
            } // [check 2]

            // [lv/C]SQLiteDatabase : close / end
            readDb.close();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");

        return billiardData;
    } // End of method [loadAllContentByCount]


    /**
     * [method] [update] name, targetScore, speciality 값을 받아서 해당 id 의 user 의 정보를 갱신한다.
     *
     * @return update query 문을 실행한 결과
     */
    public int updateContentByCount(long count,             // 0. count
                                    String date,            // 1. date
                                    String gameMode,        // 2. game mode
                                    int playerCount,        // 3. player count
                                    long winnerId,          // 4. winner id
                                    String winnerName,      // 5. winner name
                                    int playTime,           // 6. player time
                                    String score,           // 7. score
                                    int cost) {             // 8. cost

        final String METHOD_NAME = "[updateContentByCount] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing ............");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 일단 Count 의 값이 0보다 큰지 확인한다.
            if (count >= 0) {                                        // 0. count

                // [check 3] : billiardData 의 나머지 값들의 형식을 확인한다.
                if (!date.equals("")                    // 1. date          / String
                        && !gameMode.equals("")         // 2. game mode     / String
                        && (playerCount >= 0)           // 3. player count  / int
                        && (winnerId > 0)               // 4. winner id     / long
                        && !winnerName.equals("")       // 5. winner name   / String
                        && (playTime >= 0)              // 6. play time     / int
                        && !score.equals("")            // 7. score         / String
                        && (cost >= 0)) {               // 8. cost          / int

                    // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                    SQLiteDatabase updateDb = this.getDbOpenHelper().getWritableDatabase();

                    // [lv/C]ContentValues : 위 의 매개변수 값을 담을 객체 생성과 초기화
                    ContentValues updateValues = new ContentValues();
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, date);                      // 1. date
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, gameMode);             // 2. game mode
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAYER_COUNT, playerCount);       // 3. player count
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_ID, winnerId);             // 4. winner id
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_NAME, winnerName);         // 5. winner name
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, playTime);             // 6. play time
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, score);                    // 7. score
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, cost);                      // 8. cost

                    // [lv/i]methodResult : update query 문을 실행한 결과
                    methodResult = updateDb.update(BilliardTableSetting.Entry.TABLE_NAME, updateValues, BilliardTableSetting.Entry._COUNT + "=" + count, null);

                    // [lv/C]SQLiteDatabase : close
                    updateDb.close();

                } else {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "나머지 값들이 형식에 맞지 않아요.");

                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "count < 0 값들이므로 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return methodResult;
    } // End of method [updateContentByCount]


    /**
     * [method] [update] name, targetScore, speciality 값을 받아서 해당 id 의 user 의 정보를 갱신한다.
     *
     * @return update query 문을 실행한 결과
     */
    public int updateContentByCount(BilliardData billiardData) {

        final String METHOD_NAME = "[updateContentByCount] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing ............");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 일단 Count 의 값이 0보다 큰지 확인한다.
            if (billiardData.getCount() >= 0) {                                        // 0. count

                // [check 3] : billiardData 의 나머지 값들의 형식을 확인한다.
                if (checkFormatOfBilliardData(billiardData)) {

                    // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                    SQLiteDatabase updateDb = this.getDbOpenHelper().getWritableDatabase();

                    // [lv/C]ContentValues : 위 의 매개변수 값을 담을 객체 생성과 초기화
                    ContentValues updateValues = new ContentValues();
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, billiardData.getDate());                      // 1. date
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, billiardData.getGameMode());             // 2. game mode
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, billiardData.getPlayerCount());          // 3. player count
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_ID, billiardData.getWinnerId());             // 4. winner id
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, billiardData.getPlayTime());             // 5. play time
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, billiardData.getScore());                    // 6. score
                    updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, billiardData.getCost());                      // 7. cost

                    // [lv/i]methodResult : update query 문을 실행한 결과
                    methodResult = updateDb.update(BilliardTableSetting.Entry.TABLE_NAME, updateValues, BilliardTableSetting.Entry._COUNT + "=" + billiardData.getCount(), null);

                    // [lv/C]SQLiteDatabase : close
                    updateDb.close();

                } else {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "나머지 값들이 형식에 맞지 않아요.");

                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "count < 0 값들이므로 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");
        return methodResult;
    } // End of method [updateContentByCount]


    /**
     * [method] [delete] ProjectBlueDBHelper 로 writeableDatabase 를 가져와서 billiard 테이블의 모든 내용을 삭제한다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 delete query 문을 실행한다.
     *
     * <p>
     * 반환 값
     * '-1' : 데이터베이스 문재 발생
     * 0 이상 : 데이터베이스 delete query 실행 성공과 삭제한 데이터의 개수
     *
     * @return billiard 테이블의 데이터를 삭제한 개수 or 실패
     */
    public int deleteAllContent() {

        final String METHOD_NAME = "[deleteAllContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing............");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
            SQLiteDatabase deleteDb = super.getDbOpenHelper().getWritableDatabase();

            // [lv/i]methodResult : delete query 문이 실행 된 결과를 받는다.
            methodResult = deleteDb.delete(BilliardTableSetting.Entry.TABLE_NAME, null, null);

            // [lv/C]SQLiteDatabase : close
            deleteDb.close();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete.");

        return methodResult;
    } // End of method [deleteAllContent]


    /**
     * [method] [delete] ProjectBlueDBHelper 로 writeableDatabase 를 가져와서 billiard 테이블의 count 행의 내용을 삭제한다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 delete query 문을 실행한다.
     *
     * <p>
     * 반환 값
     * '-1' : 데이터베이스 문재 발생
     * '1' : 데이터베이스 delete query 실행 성공 - 삭제한 데이터는 1개 이므로
     *
     * @return billiard 테이블의 count 행 데이터를 삭제한 결과
     */
    public int deleteContentByCount(long count) {

        final String METHOD_NAME = "[deleteContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing............");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : count 는 0 보다 크다.
            if (count > 0) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기 / declaration & create
                SQLiteDatabase deleteDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/i]methodResult : delete query 문이 실행 된 결과를 받는다. / use
                methodResult = deleteDb.delete(BilliardTableSetting.Entry.TABLE_NAME, BilliardTableSetting.Entry._COUNT + "=" + count, null);

                // [lv/C]SQLiteDatabase : close / end
                deleteDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수 count 가 형식에 맞지 않아요.");
                methodResult = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete.");

        return methodResult;
    } // End of method [deleteAllContent]


    /**
     * [method] [delete] ProjectBlueDBHelper 로 writeableDatabase 를 가져와서 billiard 테이블의 해당 userID 의 모든 내용을 삭제한다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 delete query 문을 실행한다.
     *
     * <p>
     * 반환 값
     * '-1' : 데이터베이스 문재 발생
     * 0 이상 : 데이터베이스 delete query 실행 성공과 삭제한 데이터의 개수
     *
     * @param userId
     * @return billiard 테이블의 데이터를 삭제한 개수 or 실패
     */
    public int deleteAllContentByUserId(long userId) {

        final String METHOD_NAME = "[deleteAllContentByUserId] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing............");

        // [lv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : userId 가 0 보다 큰 값이 들어왔다.
            if (userId > 0) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
                SQLiteDatabase deleteDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/i]methodResult : delete query 문이 실행 된 결과를 받는다.
                methodResult = deleteDb.delete(BilliardTableSetting.Entry.TABLE_NAME, null, null);

                // [lv/C]SQLiteDatabase : close
                deleteDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수 userId 가 형식에 맞지 않아요.");
                methodResult = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete.");

        return methodResult;
    } // End of method [deleteAllContent]


    /**
     * [method] BilliardData 의 값들이 형식에 맞는지 검사한다. 모든 형식이 맞으면 true 를 하나라도 다르면 false 를 반환한다.
     */
    private boolean checkFormatOfBilliardData(BilliardData billiardData) {

        // [lv/b]isCheckedFormat : 형식이 맞는지 결과를 담는 변수
        boolean isCheckedFormat = false;

        // [check 1] : BilliardData 의 모든 값이 형식에 맞는지 검사한다.
        if (!billiardData.getDate().equals("")                  // 1. date
                && !billiardData.getGameMode().equals("")       // 2. game mode
                && (billiardData.getPlayerCount() >= 0)         // 3. player count
                && (billiardData.getWinnerId() > 0)             // 4. winner id
                && !billiardData.getWinnerName().equals("")     // 5. winner name
                && (billiardData.getPlayTime() >= 0)            // 6. play time
                && !billiardData.getScore().equals("")          // 7. score
                && (billiardData.getCost() >= 0)) {             // 8. cost

            // [lv/b]isCheckedFormat : 모든 값들의 형식이 맞다.
            isCheckedFormat = true;

        } // [check 1]

        return isCheckedFormat;

    } // End of method [checkFormatOfBilliardData]


    // ====================================================================================================
    /**
     * [method] [select] ProjectBlueDBHelper 에서 readableDatabase 를 가져와 billiard 테이블에서 해당 userID 로 저장된 데이터를 모두 가져온다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 select query 문을 실행한다.
     *
     * <p>
     * 이 데이터는 ArrayList 로 만들어진 객체에 추가되어 참조값을 리턴한다.
     *
     * @param userId [1] 이 데이터를 저장한 주체인 user 의 id
     * @return billiard 테이블에 저장된 모든 데이터
     */
/*    public ArrayList<BilliardData> loadAllContentByUserID(long userId) {

        final String METHOD_NAME = "[loadAllContentByUserID] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing........");

        // [lv/C]ArrayList<BilliardDataT> : billiard 테이블에 저장된 모든 데이커가 담길 ArrayList
        ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : userId 의 값이 0 보다 크다
            if (userId > 0) {

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기 / declaration & create
                SQLiteDatabase readDb = super.getDbOpenHelper().getReadableDatabase();

                // [lv/C]Cursor : select query 문의 실행 결과가 담길 Cursor / use
                Cursor readCursor = readDb.rawQuery(BilliardTableSetting.SQL_SELECT_WHERE_ID + userId, null);

                // [cycle 1] : cursor 의 객체의 moveToNext method 를 이용하여 가져온 데이터가 있을 때까지
                while (readCursor.moveToNext()) {

                    // [lv/C]BilliardData : billiard 테이블의 '한 행'의 정보를 담는다.
                    BilliardData billiardData = new BilliardData();
                    billiardData.setCount(readCursor.getLong(0));               // 0. count
                    billiardData.setUserId(readCursor.getLong(1));              // 1. user id
                    billiardData.setDate(readCursor.getString(2));              // 2. date
                    billiardData.setTargetScore(readCursor.getInt(3));          // 3. target score
                    billiardData.setSpeciality(readCursor.getString(4));        // 4. speciality
                    billiardData.setPlayTime(readCursor.getInt(5));             // 5. play time
                    billiardData.setWinner(readCursor.getString(6));            // 6. winner
                    billiardData.setScore(readCursor.getString(7));             // 7. score
                    billiardData.setCost(readCursor.getInt(8));                 // 8. cost

                    // [lv/C]ArrayList<BilliardData> : 위 의 '한 행'의 내용을 배열 형태로 담는다.
                    billiardDataArrayList.add(billiardData);

                } // [cycle 1]

                // [lv/C]SQLiteDatabase : close / end
                readDb.close();

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수 userId 가 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete!");

        return billiardDataArrayList;
    } // End of method [loadAllContentByUserID]*/
}
