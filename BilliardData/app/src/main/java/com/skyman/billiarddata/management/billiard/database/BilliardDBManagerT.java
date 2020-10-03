package com.skyman.billiarddata.management.billiard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardDataT;
import com.skyman.billiarddata.management.projectblue.database.ProjectBlueDBManager;

import java.util.ArrayList;


/**
 * [class] project_blue.db 의 billiard 테이블을 관리하기 위한 클래스이다.
 *
 * <p>
 * ProjectBlueDBManger 를 상속 받아
 * ProjectBlueDBHelper 를 생성한다.
 * 이 openDBHelper 에서 readableDatabase 와 writeableDatabase 를 가져와서 query 문을 실행한다.
 * </p>
 */
public class BilliardDBManagerT extends ProjectBlueDBManager {

    // constructor
    public BilliardDBManagerT(Context targetContext) {
        super(targetContext);
    }


    /**
     * [method] ProjectBlueDBHelper 에서 writeableDatabase 를 가져와 billiard 테이블에 데이터를 저장한다.
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
    public long saveContent(String dateContent,
                            int targetScoreContent,
                            String specialityContent,
                            int playTimeContent,
                            String winnerContent,
                            String scoreContent,
                            int costContent) {

        DeveloperManager.displayLog("[DbM]BilliardDbManager", "[saveContent] The method is executing ............");

        // [iv/C]SQLiteDatabase : 이 method 에서 참조하기 위한 객체 선언
        SQLiteDatabase writeDb = null;

        // [iv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [iv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
            writeDb = this.getDbOpenHelper().getWritableDatabase();

            // [check 2] : 매개변수의 형식이 맞는 지 검사한다.
            if (!dateContent.equals("")                             // 1. date
                    && (targetScoreContent > 0)                     // 2. target score          -- 0 보다 큰
                    && !specialityContent.equals("")                // 3. speciality
                    && (playTimeContent > 0)                        // 4. playtime              -- 0 보다 큰
                    && !winnerContent.equals("")                    // 5. winner
                    && !scoreContent.equals("")                     // 6. score
                    && (costContent > 0)) {                         // 7. cost                  -- 0 보다 큰

                // [iv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(BilliardTableSettingT.Entry.COLUMN_NAME_DATE, dateContent);                             // 1. date
                insertValues.put(BilliardTableSettingT.Entry.COLUMN_NAME_TARGET_SCORE, targetScoreContent);              // 2. target score
                insertValues.put(BilliardTableSettingT.Entry.COLUMN_NAME_SPECIALITY, specialityContent);                 // 3. speciality
                insertValues.put(BilliardTableSettingT.Entry.COLUMN_NAME_PLAY_TIME, playTimeContent);                    // 4. play time
                insertValues.put(BilliardTableSettingT.Entry.COLUMN_NAME_WINNER, winnerContent);                         // 5. winner
                insertValues.put(BilliardTableSettingT.Entry.COLUMN_NAME_SCORE, scoreContent);                           // 6. score
                insertValues.put(BilliardTableSettingT.Entry.COLUMN_NAME_COST, costContent);                             // 7. cost

                // [iv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.
                newRowId = writeDb.insert(BilliardTableSettingT.Entry.TABLE_NAME, null, insertValues);

                // [check 2] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {

                    // 데이터 insert 실패
                    DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                    return newRowId;

                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] 입력 성공했습니다. " + newRowId + " 값을 리턴합니다.");
                } // End of [check 2]

            } else {
                DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] 입력 된 값이 조건에 만족하지 않습니다. : " + newRowId + " 값을 리턴합니다.");
                return -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        // [iv/C]SQLiteDatabase : close
        writeDb.close();

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[saveContent] The method is complete");
        return newRowId;
    } // End of method [saveContents]


    /**
     * [method] ProjectBlueDBHelper 에서 readableDatabase 를 가져와 billiard 테이블에 저장된 데이터를 가져온다.
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
    public ArrayList<BilliardDataT> loadAllContent() {

        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContent] The method is executing........");

        // [iv/C]SQLiteDatabase : 이 method 에서 참조하기 위한 객체 선언
        SQLiteDatabase readDb = null;

        // [iv/C]ArrayList<BilliardDataT> : billiard 테이블에 저장된 모든 데이커가 담길 ArrayList
        ArrayList<BilliardDataT> billiardDataArrayList = new ArrayList<>();

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [iv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
            readDb = this.getDbOpenHelper().getReadableDatabase();

            // [iv/C]Cursor : select query 문의 실행 결과가 담길 Cursor
            Cursor cursor = readDb.rawQuery(BilliardTableSettingT.SQL_SELECT_ALL_CONTENT, null);

            // [cycle 1] : cursor 의 객체의 moveToNext method 를 이용하여 가져온 데이터가 있을 때까지
            while (cursor.moveToNext()) {

                // [iv/C]BilliardData : billiard 테이블의 '한 행'의 정보를 담는다.
                BilliardDataT billiardData = new BilliardDataT();
                billiardData.setCount(cursor.getLong(0));
                billiardData.setDate(cursor.getString(1));
                billiardData.setTargetScore(cursor.getInt(2));
                billiardData.setSpeciality(cursor.getString(3));
                billiardData.setPlayTime(cursor.getInt(4));
                billiardData.setWinner(cursor.getString(5));
                billiardData.setScore(cursor.getString(6));
                billiardData.setCost(cursor.getInt(7));

                // [iv/C]ArrayList<BilliardData> : 위 의 '한 행'의 내용을 배열 형태로 담는다.
                billiardDataArrayList.add(billiardData);

            } // [cycle 1]

        } else {
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        // [iv/C]SQLiteDatabase : close
        readDb.close();
        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[loadAllContent] The method is complete!");

        return billiardDataArrayList;
    } // End of method [loadAllContent]


    /**
     * [method] ProjectBlueDBHelper 로 writeableDatabase 를 가져와서 billiard 테이블의 모든 내용을 삭제한다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 delete query 문을 실행한다.
     *
     * <p>
     * 반환 값
     * '-1' : 데이터베이스 문재 발생
     * '1' : 데이터베이스 delete query 실행 성공
     *
     * @return billiard 테이블의 모든 데이터를 삭제한 결과
     */
    public int deleteAllContent() {
        /*
         * =========================================================================================
         * billiard table select query
         * -    billiardDbHelper 를 통해 writable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 에서 billiard 테이블의 모든 내용을 delete 한다.
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContent] The method is executing............");

        // [iv/C]SQLiteDatabase : 이 method 에서 참조하기 위한 객체 선언
        SQLiteDatabase deleteDb = null;

        // [iv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [iv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
            deleteDb = this.getDbOpenHelper().getWritableDatabase();

            // [iv/i]methodResult : delete query 문이 실행 된 결과를 받는다.
            methodResult = deleteDb.delete(BilliardTableSettingT.Entry.TABLE_NAME, null, null);

        } else {
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        // SQLiteDatabase : close
        deleteDb.close();
        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteAllContent] The method is complete.");

        return methodResult;
    } // End of method [deleteAllContent]

    /**
     * [method] ProjectBlueDBHelper 로 writeableDatabase 를 가져와서 billiard 테이블의 count 행의 내용을 삭제한다.
     *
     * <p>
     * project_blue.db 의 billiard 테이블에 데이터를 delete query 문을 실행한다.
     *
     * <p>
     * 반환 값
     * '-1' : 데이터베이스 문재 발생
     * '1' : 데이터베이스 delete query 실행 성공
     *
     * @return billiard 테이블의 count 행 데이터를 삭제한 결과
     */
    public int deleteContent(long count) {
        /*
         * =========================================================================================
         * billiard table select query
         * -    billiardDbHelper 를 통해 writable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 에서 billiard 테이블의 모든 내용을 delete 한다.
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteContent] The method is executing............");

        // [iv/C]SQLiteDatabase : 이 method 에서 참조하기 위한 객체 선언
        SQLiteDatabase deleteDb = null;

        // [iv/l]methodResult : 이 메소드의 결과 값 저장
        int methodResult = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [iv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기
            deleteDb = this.getDbOpenHelper().getWritableDatabase();

            // [iv/i]methodResult : delete query 문이 실행 된 결과를 받는다.
            methodResult = deleteDb.delete(BilliardTableSetting.Entry.TABLE_NAME, "count="+count, null);

        } else {
            DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteContent] openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        // SQLiteDatabase : close
        deleteDb.close();
        DeveloperManager.displayLog("[DbM]_BilliardDbManager", "[deleteContent] The method is complete.");

        return methodResult;
    } // End of method [deleteAllContent]
}
