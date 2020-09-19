package com.skyman.billiarddata.management.billiard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;

import java.util.ArrayList;

/**
 * ===========================================================================================
 * BilliardDbHelper를 관리하는 클래스
 * ===========================================================================================
 */
public class BilliardDbManager {

    // value : SQLite DB open helper 객체 선언
    private BilliardDbHelper billiardDbHelper;

    // value : 생성 요청하는 activity 관련 객체 선언
    private Context activityContext;

    // constructor
    public BilliardDbManager(Context activityContext) {
        this.billiardDbHelper = null;                                 // 이 클래스 모든 영역에서 사용하기 위해 null 값으로 , 생성은 method : init 에서
        this.activityContext = activityContext;
    }

    /* method : init, SQLite DB Helper를 이용하여 초기화 */
    public void init_tables() {
        /*
         * =====================================================
         * billiard.db setting
         * - 해당 db와 table 생성 여부를 체크하여 없으면 만들고,
         * 있으면 해당 db와 커넥션한다.
         * - 2020-9-18 : billiardBasic 테이블 생성
         * =====================================================
         * */
        billiardDbHelper = new BilliardDbHelper(activityContext);
        DeveloperManager.displayLog("BilliardDbManager", "초기화 완료");
    }

    /* method : insert, SQLite DB Helper를 이용하여 해당 테이블에 정보를 insert 한다. */
    public void save_content(String dateContent,
                             String targetScoreContent,
                             String specialityContent,
                             String playTimeContent,
                             String winnerContent,
                             String scoreContent,
                             String costContent) {
        /*
         * =====================================================
         * billiardBasic table insert query
         * -    데이터를 매개변수로 받아서
         *      모든 값을 입력 받은지 확인한다. 그리고
         *      ContentValues의 객체에 내용 setting을 한 다음
         *      helper를 통해 쓰기용으로 받아온 SQLiteDatabase를
         *      이용하여 해당 db 해당 table에 isnert 한다.
         * - 입력 순서
         *      1. date
         *      2. target score
         *      3. speciality
         *      4. play time
         *      5. victoree
         *      6. score
         *      7. cost
         * =====================================================
         * */
        DeveloperManager.displayLog("BilliardDbManager", "save_content 실행");

//        DeveloperManager.displayLog("BilliardDbManager", "=== date : " + dateContent);
//        DeveloperManager.displayLog("BilliardDbManager", "=== targetScoreContent : " + targetScoreContent);
//        DeveloperManager.displayLog("BilliardDbManager", "=== specialityContent : " + specialityContent);
//        DeveloperManager.displayLog("BilliardDbManager", "=== playTimeContent : " + playTimeContent);
//        DeveloperManager.displayLog("BilliardDbManager", "=== winnerContent : " + winnerContent);
//        DeveloperManager.displayLog("BilliardDbManager", "=== scoreContent : " + scoreContent);
//        DeveloperManager.displayLog("BilliardDbManager", "=== costContent : " + costContent);

        //  쓰기로 객체 생성
        SQLiteDatabase writeDb = billiardDbHelper.getWritableDatabase();

        // 빈 곳 없나 검사
        if (!dateContent.equals("")                             // date
                && !targetScoreContent.equals("")               // target score
                && !specialityContent.equals("")                // speciality
                && !playTimeContent.equals("")                  // playtime
                && !winnerContent.equals("")                    // winner
                && !scoreContent.equals("")                     // score
                && !costContent.equals("")) {                   // cost

            // 입력할 내용 setting
            ContentValues values = new ContentValues();
            values.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, dateContent);                           // 1. date
            values.put(BilliardTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScoreContent);            // 2. target score
            values.put(BilliardTableSetting.Entry.COLUMN_NAME_SPECIALITY, specialityContent);               // 3. speciality
            values.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, playTimeContent);                  // 4. play time
            values.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER, winnerContent);                       // 5. winner
            values.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, scoreContent);                         // 6. score
            values.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, costContent);                           // 7. cost

            // 해당 billiardBasic 테이블에 내용 insert
            // nullColumnHack 이 'null'로 지정 되었다면?       values 객체의 어떤 열에 값이 없으면 지금 내용을 insert 안함.
            //                이 '열 이름'이 지정 되었다면?    해당 열에 값이 없으면 null 값을 넣는다.
            long newRowId = writeDb.insert(BilliardTableSetting.Entry.TABLE_NAME, null, values);

            // newRowId는 데이터베이스에 insert 가 실패하면 '-1'을 반환하고, 성공하면 해당 '행 번호'를 반환한다.
            if (newRowId == -1) {
                // 데이터 insert 실패
                toastHandler("데이터 입력에 실패했습니다.");
            } else {
                // 데이터 insert 성공
                toastHandler(newRowId + " 번째 데이터를 입력에 성공했습니다. ");
            }
        } else {
            toastHandler("빈곳을 채워주세요.");
        }
        DeveloperManager.displayLog("BilliardDbManager", "save_content 실행 완료");
    }



    /* method : load, SQLite DB Helper를 이용하여 해당 테이블에 정보를 select 한다. */
    public ArrayList<BilliardData> load_contents() {
        /*
         * =====================================================
         * billiardBasic table select query
         * -    helper를 통해 읽기용으로 받아온 SQLiteDatabase를
         *      이용하여 해당 db 해당 table의 모든 내용을 select 한다.
         * - column title
         *      0: date
         *      1: target score
         *      2: speciality
         *      3: play time
         *      4: victoree
         *      5: score
         *      6: cost
         * - return : 읽어온 내용이 담기 ArrayList<DataListItem>
         * =====================================================
         * */
        DeveloperManager.displayLog("BilliardDbManager", "load_contents 실행");

        // 읽기용 객체 생성
        SQLiteDatabase readDb = billiardDbHelper.getReadableDatabase();

        // 해당 쿼리문으로 읽어온 테이블 내용을 Cursor 객체를 통해 읽을 수 있도록 하기
        Cursor cursor = readDb.rawQuery(BilliardTableSetting.SQL_SELECT_TABLE_ALL_ITEM, null);

        // DataListItem 객체를 담을 ArrayList 객체 생성 - 한 행을 DataListItem 객체에 담는다.
        ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();

        // Cursor 객체를 통해 하나씩 읽어온다.
        while (cursor.moveToNext()) {
            long id = cursor.getInt(0);
            String date = cursor.getString(1);
            String targetScore = cursor.getString(2);
            String speciality = cursor.getString(3);
            String playTime = cursor.getString(4);
            String winner = cursor.getString(5);
            String score = cursor.getString(6);
            String cost = cursor.getString(7);

            DeveloperManager.displayLog("BilliardDbManager", "읽어온 내용");
            DeveloperManager.displayLog("BilliardDbManager", "id : " + id);
            DeveloperManager.displayLog("BilliardDbManager", "date : " + date);
            DeveloperManager.displayLog("BilliardDbManager", "targetScore : " + targetScore);
            DeveloperManager.displayLog("BilliardDbManager", "speciality : " + speciality);
            DeveloperManager.displayLog("BilliardDbManager", "paly_time : " + playTime);
            DeveloperManager.displayLog("BilliardDbManager", "victoree : " + winner);
            DeveloperManager.displayLog("BilliardDbManager", "score : " + score);
            DeveloperManager.displayLog("BilliardDbManager", "cost : " + cost);

            // 읽어온 dataListItem 을 items에 추가하기
//            BilliardData billiardData = new BilliardData.DataBuilder()
//                    .setId(cursor.getLong(0))
//                    .setDate(cusor.)
//                    .setTargetScore()
//                    .setSpeciality()
//                    .setPlayTime()
//                    .setWinner()
//                    .setScore()
//                    .setCost().builder();


            BilliardData dataItem = new BilliardData();
            dataItem.setId(id);
            dataItem.setDate(date);
            dataItem.setTargetScore(targetScore);
            dataItem.setSpeciality(speciality);
            dataItem.setPlayTime(playTime);
            dataItem.setWinner(winner);
            dataItem.setScore(score);
            dataItem.setCost(cost);

            // array list 에 insert
            billiardDataArrayList.add(dataItem);
        }
        DeveloperManager.displayLog("BilliardDbManager", "load_contents 실행 완료");
        return billiardDataArrayList;
    }

    /* method : delete,  */
    public void delete_contents() {
        /*
         * =====================================================
         * billiardBasic table delete query
         * -    helper를 통해 쓰기용으로 받아온 SQLiteDatabase를
         *      이용하여 해당 db 해당 table의 모든 내용을 delete 한다.
         * =====================================================
         * */
        DeveloperManager.displayLog("BilliardDbManager", "delete_contents 실행");
        // 쓰기용 객체 생성
        SQLiteDatabase deleteDb = billiardDbHelper.getWritableDatabase();

        // all contents delete_query execute
        deleteDb.execSQL(BilliardTableSetting.SQL_DELETE_TABLE_ALL_ITEM);

        DeveloperManager.displayLog("BilliardDbManager", "delete_contents 실행 완료");
    }


    /* method : display, toast 메시지 출력 */
    private void toastHandler(String content) {
        Toast myToast = Toast.makeText(activityContext, content, Toast.LENGTH_SHORT);
        myToast.show();
    }

}

