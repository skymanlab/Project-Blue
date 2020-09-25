package com.skyman.billiarddata.management.friend.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardTableSetting;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.user.database.UserDbHelper;

import java.util.ArrayList;

public class FriendDbManager {

    // value : SQLiteOpenHelper 를 extends 한 FriendDbHelper 객체 선언
    private FriendDbHelper friendDbHelper;

    // value : FriendDbManager 를 생성한 activity 의 Context
    private Context targetContext;

    // constructor
    public FriendDbManager(Context targetContext) {
        this.friendDbHelper = null;
        this.targetContext = targetContext;
    }


    /*                                      public method
     * =============================================================================================
     * =============================================================================================
     * */

    /* method 0 : init, SQLite DB open helper 를 이용하여 초기화 */
    public void init_db() {
        /*
         * =========================================================================================
         * project_blue.db 에 billiard, user, friend 테이블의 존재 여부 확인한다.
         * 없으면 project_blue.db 생성 후 billiard, user, friend 테이블을 생성하고,
         * project_blue.db 를 open 한다.
         * =========================================================================================
         * */
        friendDbHelper = new FriendDbHelper(targetContext);
        DeveloperManager.displayLog("FriendDbManager", "** init_db function is complete!");
    }   // End of 'init_db'

    /* method 1 : insert, SQLite DB open helper 를 이용하여 해당 테이블에 정보를 insert 한다. */
    public void save_content(String name) {
        /*
         * =====================================================
         * friend table insert query
         * -    데이터를 매개변수로 받아서 모든 값을 입력 받은지 확인한다.
         *      그리고 ContentValues 의 객체에 내용 setting 을 한다.
         *      friendDbHelper 를 통해 writeable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 friend 테이블에 해당 내용을 insert 한다.
         * - 입력 순서
         *      1. name
         * =====================================================
         * */
        DeveloperManager.displayLog("FriendDbManager", "** save_content is executing ............");

        // SQLiteDatabase : write database - billiardDbHelper 를 통해 write 용으로 가져오기
        SQLiteDatabase writeDb = friendDbHelper.getWritableDatabase();

        // check : 매개변수의 내용 중에 빈 곳이 없나 검사
        if (!name.equals("")) {

            // ContentValues : 매개변수의 내용을 insertValues 에 셋팅하기
            ContentValues insertValues = new ContentValues();
            insertValues.put(FriendTableSetting.Entry.COLUMN_NAME_NAME, name);                           // 1. name

            /*  해당 friend 테이블에 내용 insert
                nullColumnHack 이 'null'로 지정 되었다면?       values 객체의 어떤 열에 값이 없으면 지금 내용을 insert 안함.
                               이 '열 이름'이 지정 되었다면?    해당 열에 값이 없으면 null 값을 넣는다.*/

            // newRowId는 데이터베이스에 insert 가 실패하면 '-1'을 반환하고, 성공하면 해당 '행 번호'를 반환한다.
            long newRowId = writeDb.insert(FriendTableSetting.Entry.TABLE_NAME, null, insertValues);

            // check : 데이터베이스 입력이 실패, 성공 했는지 구분하여
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

        // SQLiteDatabase : close
        writeDb.close();

        DeveloperManager.displayLog("FriendDbManager", "** save_content is complete");
    }       // End of 'save_contents'


    /* method : load, SQLite DB Helper를 이용하여 해당 테이블의 정보를 가져온다. */
    public ArrayList<FriendData> load_contents() {
        /*
         * =====================================================
         * friend table select query
         * -    friendDbHelper 를 통해 readable 으로 받아온 SQLiteDatabase 를 이용하여
         *      project_blue.db 의 friend 테이블의 모든 내용을 읽어온다.
         * - column title
         *      0: count
         *      1: name
         * - return : 읽어온 내용이 담기 ArrayList<FriendData>
         * =====================================================
         * */
        DeveloperManager.displayLog("FriendDbManager", "** load_contents is executing ............");

        // SQLiteDatabase : read database - billiardDbHelper 를 통해 read 용으로 가져오기
        SQLiteDatabase readDb = friendDbHelper.getReadableDatabase();

        // Cursor : 해당 쿼리문으로 읽어온 테이블 내용을 Cursor 객체를 통해 읽을 수 있도록 하기
        Cursor cursor = readDb.rawQuery(FriendTableSetting.SQL_SELECT_TABLE_ALL_ITEM, null);

        // ArrayList<friendData> : FriendData 객체를 담을 ArrayList 객체 생성 - 한 행을 FriendData 객체에 담는다.
        ArrayList<FriendData> friendDataArrayList = new ArrayList<>();

        // cycle : Cursor 객체의 다음 내용이 있을 때 마다 체크하여 모두 friendDataArrayList 에 담는다.
        while (cursor.moveToNext()) {
            // FriendData : cursor 에서 읽어 온 내용(한 행)을 friendData 에 담는다.
            FriendData friendData = new FriendData();
            friendData.setCount(cursor.getLong(0));
            friendData.setName(cursor.getString(1));

            // ArrayList<friendData> : 위 의 내용을 배열 형태로 담아둔다.
            friendDataArrayList.add(friendData);

            DeveloperManager.displayToFriendData("FriendDbManager",friendData);
        }

        // SQLiteDatabase : close
        readDb.close();

        DeveloperManager.displayLog("FriendDbManager", "** load_contents is complete!");

        // return : 모든 내용이 담김 friendDataArrayList 를 반환한다.
        return friendDataArrayList;
    }       // End of 'load_contents'

    /* method : friendDbHelper close */
    public void closeFriendDbHelper() {
        friendDbHelper.close();
        DeveloperManager.displayLog("FriendDbManager", "** friendDbHelper is closed.");
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
