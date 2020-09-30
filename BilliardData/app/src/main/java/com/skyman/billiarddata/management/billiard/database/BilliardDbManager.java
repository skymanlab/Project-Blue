package com.skyman.billiarddata.management.billiard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.cursoradapter.widget.CursorAdapter;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;

import java.util.ArrayList;

/**
 * ===========================================================================================
 * BilliardDbHelper 를 관리하는 클래스
 * ===========================================================================================
 */
public class BilliardDbManager {

    // value : SQLite DB open helper 객체 선언
    private BilliardDbHelper billiardDbHelper;

    // value : 생성 요청하는 activity 관련 된 객체 선언
    private Context targetContext;

    // value : init_db 실행 여부 확인
    private boolean initDb;

    // constructor
    public BilliardDbManager(Context targetContext) {
        this.billiardDbHelper = null;                       // 직관성을 위해, 객체 생성은 init_db 에서 한다.
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
        billiardDbHelper = new BilliardDbHelper(targetContext);
        initDb = true;
        DeveloperManager.displayLog("[DbM] BilliardDbManager", "[init_db] The method is complete. - initDb : " + initDb);
    }

    /* method : insert, SQLite DB open helper 를 이용하여 해당 테이블에 정보를 insert 한다. */
    public long save_content(String dateContent,
                             String targetScoreContent,
                             String specialityContent,
                             String playTimeContent,
                             String winnerContent,
                             String scoreContent,
                             String costContent) {
        /*
         * =====================================================
         * billiard table insert query
         * -    데이터를 매개변수로 받아서 모든 값을 입력 받은지 확인한다.
         *      그리고 ContentValues 의 객체에 내용 setting 을 한다.
         *      billiardDbHelper 를 통해 writeable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 billiard 테이블에 해당 내용을 insert 한다.
         * - 입력 순서
         *      1. date
         *      2. target score
         *      3. speciality
         *      4. play time
         *      5. winner
         *      6. score
         *      7. cost
         * - return
         *      1. '-2' : 입력 조건이 만족하지 않음
         *      2. '-1' : 데이터베이스 insert 실패
         *      3. '0' : 기본 값
         *      4. 0 보다 큰 수 : insert 된 곳의 행 번호
         * =====================================================
         * */
        DeveloperManager.displayLog("[DbM] FriendDbManager", "[save_content] The method is executing ............");

        // int : 이 메소드의 결과를 저장하기 위한 변수 선언, 행 번호
        long newRowId = 0;

        // SQLiteDatabase : write database - billiardDbHelper 를 통해 write 용으로 가져오기
        SQLiteDatabase writeDb = billiardDbHelper.getWritableDatabase();

        // check : 매개변수의 내용 중에 빈 곳이 없나 검사
        if (!dateContent.equals("")                             // date
                && !targetScoreContent.equals("")               // target score
                && !specialityContent.equals("")                // speciality
                && !playTimeContent.equals("")                  // playtime
                && !winnerContent.equals("")                    // winner
                && !scoreContent.equals("")                     // score
                && !costContent.equals("")) {                   // cost

            // ContentValues : 매개변수의 내용을 insertValues 에 셋팅하기
            ContentValues insertValues = new ContentValues();
            insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, dateContent);                           // 1. date
            insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScoreContent);            // 2. target score
            insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SPECIALITY, specialityContent);               // 3. speciality
            insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, playTimeContent);                  // 4. play time
            insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER, winnerContent);                       // 5. winner
            insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, scoreContent);                         // 6. score
            insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, costContent);                           // 7. cost

            /*  해당 billiard 테이블에 내용 insert
                nullColumnHack 이 'null'로 지정 되었다면?       values 객체의 어떤 열에 값이 없으면 지금 내용을 insert 안함.
                               이 '열 이름'이 지정 되었다면?    해당 열에 값이 없으면 null 값을 넣는다.*/

            // newRowId는 데이터베이스에 insert 가 실패하면 '-1'을 반환하고, 성공하면 해당 '행 번호'를 반환한다.
            newRowId = writeDb.insert(BilliardTableSetting.Entry.TABLE_NAME, null, insertValues);

            // check : 데이터베이스 입력이 실패, 성공 했는지 구분하여
            if (newRowId == -1) {
                // 데이터 insert 실패
                DeveloperManager.displayLog("[DbM] BilliardDbManager", "[save_load] DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                return newRowId;
            } else {
                // 데이터 insert 성공
                DeveloperManager.displayLog("[DbM] BilliardDbManager", "[save_load] " + newRowId + " 번째 입력이 성공하였습니다.");
            }
        } else {
            DeveloperManager.displayLog("[DbM] BilliardDbManager", "[save_content] 입력 된 값이 조건에 만족하지 않습니다. : " + newRowId + " 값을 리턴합니다.");
            return -2;
        }

        // SQLiteDatabase : close
        writeDb.close();

        DeveloperManager.displayLog("[DbM] FriendDbManager", "[save_content] The method is complete");
        return newRowId;
    }

    /* method : load, SQLite DB Helper 를 이용하여 해당 테이블의 정보를 가져온다. */
    public ArrayList<BilliardData> load_contents() {
        /*
         * =====================================================
         * billiard table select query
         * -    billiardDbHelper 를 통해 readable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 billiard 테이블의 모든 내용을 읽어온다.
         * - return : 읽어온 내용이 담기 ArrayList<BilliardData>
         * =====================================================
         * */
        DeveloperManager.displayLog("[DbM] BilliardDbManager", "[load_contents] The method is executing........");

        // SQLiteDatabase : read database - billiardDbHelper 를 통해 read 용으로 가져오기
        SQLiteDatabase readDb = billiardDbHelper.getReadableDatabase();

        // Cursor : 해당 쿼리문으로 읽어온 테이블 내용을 Cursor 객체를 통해 읽을 수 있도록 하기
        Cursor cursor = readDb.rawQuery(BilliardTableSetting.SQL_SELECT_TABLE_ALL_ITEM, null);

        // ArrayList<BilliardData> : BilliardData 객체를 담을 ArrayList 객체 생성 - 한 행을 BilliardData 객체에 담는다.
        ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();

        // cycle : Cursor 객체의 다음 내용이 있을 때 마다 체크하여 모두 billiardDataArrayList 에 담는다.
        while (cursor.moveToNext()) {
            // BilliardData : cursor 에서 읽어 온 내용(한 행)을 billiardData 에 담는다.
            BilliardData billiardData = new BilliardData();
            billiardData.setId(cursor.getLong(0));
            billiardData.setDate(cursor.getString(1));
            billiardData.setTargetScore(cursor.getString(2));
            billiardData.setSpeciality(cursor.getString(3));
            billiardData.setPlayTime(cursor.getString(4));
            billiardData.setWinner(cursor.getString(5));
            billiardData.setScore(cursor.getString(6));
            billiardData.setCost(cursor.getString(7));

            // ArrayList<BilliardData> : 위 의 내용을 배열 형태로 담아둔다.
            billiardDataArrayList.add(billiardData);
        }

        // SQLiteDatabase : close
        readDb.close();

        DeveloperManager.displayLog("[DbM] BilliardDbManager", "[load_contents] The method is complete!");

        // return : 모든 내용이 담김 billiardDataArrayList 를 반환한다. ArrayList 의 size 값으로 뿌려줄지 말지 check 한다.
        return billiardDataArrayList;
    }

    /* method : delete,  */
    public void delete_contents() {
        /*
         * =========================================================================================
         * billiard table select query
         * -    billiardDbHelper 를 통해 writable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 에서 billiard 테이블의 모든 내용을 delete 한다.
         * =========================================================================================
         * */
        DeveloperManager.displayLog("[DbM] BilliardDbManager", "[delete_contents] The method is executing............");

        // SQLiteDatabase : write database - billiardDbHelper 를 write 용으로 가져오기
        SQLiteDatabase deleteDb = billiardDbHelper.getWritableDatabase();

        // SQLiteDatabase : 모든 내용 삭제
        int result = deleteDb.delete(BilliardTableSetting.Entry.TABLE_NAME, null, null);
        DeveloperManager.displayLog("[DbM] BilliardDbManager", "[delete_contents] 데이터베이스 delete 실행 결과 : " + result);

        // SQLiteDatabase : close
        deleteDb.close();

        DeveloperManager.displayLog("[DbM] BilliardDbManager", "[delete_contents] The method is complete.");
    }

    /* method : billiardDbHelper close */
    public void closeBilliardDbHelper() {
        if (initDb) {
            billiardDbHelper.close();
            DeveloperManager.displayLog("[DbM] BilliardDbManager", "[closeBilliardDbHelper] billiardDbHelper is closed.");
        } else {
            DeveloperManager.displayLog("[DbM] BilliardDbManager", "[closeBilliardDbHelper] billiardDbHelper 가 초기화되지 않았습니다.");
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

