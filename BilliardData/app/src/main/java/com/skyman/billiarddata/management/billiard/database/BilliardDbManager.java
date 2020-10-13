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
public class BilliardDBManager extends ProjectBlueDBManager {

    // constant
    private final String CLASS_NAME_LOG = "";

    // constructor
    public BilliardDBManager(Context targetContext) {
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
     * @param dateContent        [1] date : 날짜
     * @param targetScoreContent [2] target score : 수지
     * @param specialityContent  [3] speciality : 주 종목
     * @param playTimeContent    [4] play time : 게임 시간
     * @param winnerContent      [5] winner id : 승자 id
     * @param scoreContent       [6] score : 스코터
     * @param costContent        [7] cost : 가격
     * @return billiard 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(long userId,
                            String dateContent,
                            int targetScoreContent,
                            String specialityContent,
                            int playTimeContent,
                            String winnerContent,
                            String scoreContent,
                            int costContent) {

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] The method is executing ............");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수의 형식이 맞는 지 검사한다.
            if ((userId > 0)                                        // 1. user id               -- 0 보다 큰
                    && !dateContent.equals("")                      // 2. date
                    && (targetScoreContent >= 0)                    // 3. target score          -- 0 보다 큰
                    && !specialityContent.equals("")                // 4. speciality
                    && (playTimeContent >= 0)                       // 5. playtime              -- 0 보다 큰
                    && !winnerContent.equals("")                    // 6. winner
                    && !scoreContent.equals("")                     // 7. score
                    && (costContent >= 0)) {                        // 8. cost                  -- 0 보다 큰

                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기            ==> SQLiteDatabase 는 모든 조건이 만족 했을 때 가져와서
                SQLiteDatabase writeDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_USER_ID, userId);                               // 1. user id
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, dateContent);                             // 2. date
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScoreContent);              // 3. target score
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SPECIALITY, specialityContent);                 // 4. speciality
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, playTimeContent);                    // 5. play time
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER, winnerContent);                         // 6. winner
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, scoreContent);                           // 7. score
                insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, costContent);                             // 8. cost

                // [lv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.                == > 사용하고
                newRowId = writeDb.insert(BilliardTableSetting.Entry.TABLE_NAME, null, insertValues);

                // [lv/C]SQLiteDatabase : close             == > 닫은 다음 결과값을 반환하든 가공하든 하면 된다.
                writeDb.close();

                // [check 3] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {
                    // 데이터 insert 실패
                    DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] 입력 성공했습니다. " + newRowId + " 값을 리턴합니다.");
                } // [check 3]

            } else {
                DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] 매개변수들의 형식이 맞지 않아요.");
                newRowId = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] The method is complete");
        return newRowId;
    } // End of method [saveContents]


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

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContent] The method is executing........");

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
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContent] The method is complete!");

        return billiardDataArrayList;
    } // End of method [loadAllContent]


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
    /* method : load, SQLite DB Helper 를 이용하여 해당 테이블의 정보를 가져온다. */
    public ArrayList<BilliardData> loadAllContentByUserID(long userId) {

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContentByUserID] The method is executing........");

        // [lv/C]ArrayList<BilliardDataT> : billiard 테이블에 저장된 모든 데이커가 담길 ArrayList
        ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : userId 의 값이 0 보다 크다
            if (userId >0) {

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
                DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContentByUserID] 매개변수 userId 가 형식에 맞지 않아요.");
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContentByUserID] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContentByUserID] The method is complete!");

        return billiardDataArrayList;
    } // End of method [loadAllContentByUserID]


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

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContent] The method is executing............");

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
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContent] The method is complete.");

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
    public int deleteContent(long count) {

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteContent] The method is executing............");

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
                DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteContent] 매개변수 count 가 형식에 맞지 않아요.");
                methodResult = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteContent] The method is complete.");

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

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContentByUserId] The method is executing............");

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
                DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContentByUserId] 매개변수 userId 가 형식에 맞지 않아요.");
                methodResult = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContentByUserId] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContentByUserId] The method is complete.");

        return methodResult;
    } // End of method [deleteAllContent]
}
