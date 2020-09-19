package com.skyman.billiarddata.management.user.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbHelper;
import com.skyman.billiarddata.management.billiard.database.BilliardTableSetting;
import com.skyman.billiarddata.management.user.data.UserData;

import java.util.ArrayList;

public class UserDbManager {

    // value : SQLite DB open helper 객체 선언
    private UserDbHelper userDbHelper;

    // value : 생성 요청하는 activity 관련 객체 선언
    private Context activityContext;


    // constructor
    public UserDbManager(Context activityContext){
        this.userDbHelper = null;
        this.activityContext = activityContext;
    }

    /* method : init, SQLite DB Helper를 이용하여 초기화 */
    public void init_tables() {
        /*
         * =====================================================
         * billiard.db 에 user 테이블 setting
         * - 해당 db에 table 생성 여부를 체크하여 없으면 만들고,
         * 있으면 해당 db의 table 을 연결한다.
         * =====================================================
         * */
        userDbHelper = new UserDbHelper(activityContext);
        DeveloperManager.displayLog("userDbManager", "초기화 완료");
    }

    /* method : insert, SQLite DB Helper를 이용하여 해당 테이블에 정보를 insert 한다. */
    public void save_content(String userName, int targetScore, String speciality, int gameRecordWin, int gameRecordLoss, int totalPlayTime, int totalCost) {
        /*
         * =====================================================
         * user table insert query
         * -    데이터를 매개변수로 받아서
         *      모든 값을 입력 받은지 확인한다. 그리고
         *      ContentValues의 객체에 내용 setting을 한 다음
         *      helper를 통해 쓰기용으로 받아온 SQLiteDatabase를
         *      이용하여 해당 db 해당 table에 isnert 한다.
         * - 입력 순서
         * =====================================================
         * */
        DeveloperManager.displayLog("userDbManager", "save_content 실행");
        //  쓰기로 객체 생성
        SQLiteDatabase writeDb = userDbHelper.getWritableDatabase();

        // 빈 곳 없나 검사
        if (!userName.equals("") &&                         // 0. user name         -- not null
                (targetScore >= 0) &&                        // 1. target score      -- 0 보다 커야
                !speciality.equals("") &&                   // 2. speciality        -- not null
                (gameRecordWin >= 0) &&                      // 3. game record win   -- 0 보다 커야
                (gameRecordLoss >= 0) &&                     // 4. game record loss  -- 0 보다 커야
                (totalPlayTime >=0) &&                       // 5. total play time   -- 0 보다 커야
                (totalCost >= 0)                              // 6. total cost        -- 0 보다 커야
            ) {                   //

//            DeveloperManager.displayLog("UserDbManager","userName : " + userName);
//            DeveloperManager.displayLog("UserDbManager","targetScore : " + targetScore);
//            DeveloperManager.displayLog("UserDbManager","speciality : " + speciality);
//            DeveloperManager.displayLog("UserDbManager","gameRecordWin : " + gameRecordWin);
//            DeveloperManager.displayLog("UserDbManager","gameRecordLoss : " + gameRecordLoss);
//            DeveloperManager.displayLog("UserDbManager","totalPlayTime : " + totalPlayTime);
//            DeveloperManager.displayLog("UserDbManager","totalCost : " + totalCost);

            // 입력할 내용 setting
            ContentValues insertValues = new ContentValues();
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_USERNAME, userName);                    // 0. user name
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);             // 1. target score
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_SPECIALITY, speciality);                // 2. speciality
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_WIN, gameRecordWin);        // 3. game record win
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_GAME_RECORD_LOSS, gameRecordLoss);      // 4. game record loss
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_PLAY_TIME, totalPlayTime);        // 5. total play time
            insertValues.put(UserTableSetting.Entry.COLUMN_NAME_TOTAL_COST, totalCost);                 // 6. total cost

            // 해당 billiardBasic 테이블에 내용 insert
            // nullColumnHack 이 'null'로 지정 되었다면?       values 객체의 어떤 열에 값이 없으면 지금 내용을 insert 안함.
            //                이 '열 이름'이 지정 되었다면?    해당 열에 값이 없으면 null 값을 넣는다.
            long newRowId = writeDb.insert(UserTableSetting.Entry.TABLE_NAME, null, insertValues);

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
        DeveloperManager.displayLog("userDbManager", "save_content 실행 완료");
    }

    /* method : load, SQLite DB Helper를 이용하여 해당 테이블에 정보를 select 한다. */
    public ArrayList<UserData> load_contents() {
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
        DeveloperManager.displayLog("UserDbManager", "load_contents 실행 ");

        // 읽기용 객체 생성
        SQLiteDatabase readDb = userDbHelper.getReadableDatabase();

        // 해당 쿼리문으로 읽어온 테이블 내용을 Cursor 객체를 통해 읽을 수 있도록 하기
        Cursor cursor = readDb.rawQuery(UserTableSetting.SQL_SELECT_TABLE_ALL_ITEM, null);

        // DataListItem 객체를 담을 ArrayList 객체 생성 - 한 행을 DataListItem 객체에 담는다.
        ArrayList<UserData> userDataArrayList = new ArrayList<>();
//        DeveloperManager.displayLog("UserDbManager", "읽어왔음" + cursor.getCount());
        // Cursor 객체를 통해 하나씩 읽어온다.
        while (cursor.moveToNext()) {
//            DeveloperManager.displayLog("UserDbManager", "내용이 있나벼");
            String userName = cursor.getString(0);
            int targetScore = cursor.getInt(1);
            String speciality = cursor.getString(2);
            int gameRecordWin = cursor.getInt(3);
            int gameRecordLoss = cursor.getInt(4);
            int totalPlayTime = cursor.getInt(5);
            int totalCost = cursor.getInt(6);

//            DeveloperManager.displayLog("userDbManager", "읽어온 내용");
//            DeveloperManager.displayLog("userDbManager", "userName : " + userName);
//            DeveloperManager.displayLog("userDbManager", "targetScore : " + targetScore);
//            DeveloperManager.displayLog("userDbManager", "speciality : " + speciality);
//            DeveloperManager.displayLog("userDbManager", "gameRecordWin : " + gameRecordWin);
//            DeveloperManager.displayLog("userDbManager", "gameRecordLoss : " + gameRecordLoss);
//            DeveloperManager.displayLog("userDbManager", "totalPlayTime : " + totalPlayTime);
//            DeveloperManager.displayLog("userDbManager", "totalCost : " + totalCost);

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

            UserData userData = new UserData();
            userData.setUserName(userName);
            userData.setTargetScore(targetScore);
            userData.setSpeciality(speciality);
            userData.setGameRecordWin(gameRecordWin);
            userData.setGameRecordLoss(gameRecordLoss);
            userData.setTotalPlayTime(totalPlayTime);
            userData.setTotalCost(totalCost);


//            DeveloperManager.displayLog("userDbManager", "---userName : " + userData.getUserName());
//            DeveloperManager.displayLog("userDbManager", "---targetScore : " + userData.getTargetScore());
//            DeveloperManager.displayLog("userDbManager", "---speciality : " + userData.getSpeciality());
//            DeveloperManager.displayLog("userDbManager", "---gameRecordWin : " + userData.getGameRecordWin());
//            DeveloperManager.displayLog("userDbManager", "---gameRecordLoss : " + userData.getGameRecordLoss());
//            DeveloperManager.displayLog("userDbManager", "---totalPlayTime : " + userData.getTotalPlayTime());
//            DeveloperManager.displayLog("userDbManager", "---totalCost : " + userData.getTotalCost());
            // array list 에 insert
            userDataArrayList.add(userData);
        }
        DeveloperManager.displayLog("userDbManager", "load_contents 실행 완료");
        return userDataArrayList;
    }

    /* method : display, toast 메시지 출력 */
    private void toastHandler(String content) {
        Toast myToast = Toast.makeText(activityContext, content, Toast.LENGTH_SHORT);
        myToast.show();
    }
}
