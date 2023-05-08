package com.skyman.billiarddata.etc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.developer.Display;
import com.skyman.billiarddata.table.billiard.database.BilliardTableSetting;
import com.skyman.billiarddata.table.friend.database.FriendTableSetting;
import com.skyman.billiarddata.table.player.database.PlayerTableSetting;
import com.skyman.billiarddata.table.user.database.UserTableSetting;


/**
 * [class] 이 클래스는 SQLiteOpenHelper 를 상속받아 하나의 db 를 생성하고, 이 db 에 table 을 생성하는 역할을 한다.
 * db 파일은 이 클래스가 생성될 때, DATABASE_NAME 과 DATABASE_VERSION 을 생성자 매개변수를 받아 생성한다.
 *
 * <p>
 * DATABASE_NAME 으로 db 파일이 만들어 지며, DATABASE_VERSION 의 이 db 에 version 가 적용된다.
 * 만약, 이 db에 변경된 내용이 있을 경우 어플을 삭제하거나, DATABASE_VERSION 을 변경해야지만
 * 변경된 db가 반영이 된다.
 * <p>
 * 이 클래스가 생성될 때, db 파일이 만들어지며 이때 필요한 모든 table 도 만들어 주어야 한다.
 * 즉, 이 클래스는 table 단위로 생성해야 하는 것이 아니고, db 파일을 만들 때 생성하여 모든 테이블에 접근할 때 사용된다.
 * <p>
 * - onCreate method 는 DATABASE_MAME 의 db 파일이 없을 때 불려지는 콜백 함수이다.
 * - onUpgrade method 는 DATABASE_VERSION 이 상위 버전으로 변경되면 불려지는 콜백 함수이다.
 * - onDowngrade method 는 DATABASE_VERSION 이 하위 버전으로 변경되면 불려지는 콜백 함수이다.
 * - onOpen method 는 getWriteableDatabase 나 getReadableDatabase method 를 실행하여 SQLiteDatabase 객체를 생성했을 때 실행 되는 콜백 함수이다.
 * <p>
 * 이 클래스로 Open 한 db 는 getReadableDatabase 와 getWriteableDatabase method 를 이용하여 SQLiteDatabase 객체를 반환한다.
 * 위 의 SQLiteDatabase 객체를 이용하여 해당 db 의 테이블의 내용을 create, insert, select, update, delete 문을 이용하여 사용할 수 있다.
 */
public class AppDbOpenHelper extends SQLiteOpenHelper {

    // constant
    private static final Display CLASS_LOG_SWITCH = Display.OFF;
    private static final String CLASS_NAME = "[DbH]_ProjectBlueDatabaseHelper";

    // constructor
    public AppDbOpenHelper(Context context) {
        super(context, AppDbConstants.DATABASE_NAME, null, AppDbConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // onCreate 는 project_blue.db 가 없을 때 실행되는 메소드이다.
        // 즉, constructor 의 매개변수 값으로 들어간 데이터베이스 이름으로 project_blue.db 가 있는지 검사하는 것이다.
        // project_blue.db 를 생성하면서 모든 테이블을 생성한다. billiard, user 테이블을 생성한다.

        final String METHOD_NAME = "[onCreate] ";

        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The method is executing........");
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The project_blue.db is not exist.");
        db.execSQL(UserTableSetting.SQL_CREATE_TABLE);                      // user
        db.execSQL(FriendTableSetting.SQL_CREATE_TABLE);                    // friend
        db.execSQL(BilliardTableSetting.SQL_CREATE_TABLE);                  // billiard
        db.execSQL(PlayerTableSetting.SQL_CREATE_TABLE);                    // player
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The project_blue.db has been created.");
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "user table has been created.");
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "friend table has been created.");
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "billiard table has been created.");
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "player table has been created.");
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "All tables must be created when creating a project_blue.db. ");
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The method is complete.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String METHOD_NAME = "[onUpgrade] ";
        // project_blue.db 버전 업그레이드 하면, project_blue.db의 모든 테이블을 삭제 후, 업그레이드 된 테이블을 생성한다.

        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The method is executing........");
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The project_blue.db is upgrading.");
        db.execSQL(UserTableSetting.SQL_DROP_TABLE_IF_EXISTS);                      // user
        db.execSQL(FriendTableSetting.SQL_DROP_TABLE_IF_EXISTS);                    // friend
        db.execSQL(BilliardTableSetting.SQL_DROP_TABLE_IF_EXISTS);                  // billiard
        db.execSQL(PlayerTableSetting.SQL_DROP_TABLE_IF_EXISTS);                    // player
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "already existed table delete.");
        onCreate(db);
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The method is complete.");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String METHOD_NAME = "[onDowngrade] ";
        // project_blue.db 버전 다운그레이드 하면, project_blue.db의 모든 테이블을 삭제 후, 다운그레이드 된 테이블을 생성한다.

        onUpgrade(db, oldVersion, newVersion);
        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The method is complete!");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        final String METHOD_NAME = "[onOpen] ";
        super.onOpen(db);
        // project_blue.db 에 billiard, user 테이블이 생성되면(존재하면), onOpen method 가 실행 된다.

        DeveloperManager.printLog(CLASS_LOG_SWITCH, CLASS_NAME, METHOD_NAME + "The method is complete.");
    }

    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        super.setWriteAheadLoggingEnabled(enabled);
    }

    @Override
    public void setLookasideConfig(int slotSize, int slotCount) {
        super.setLookasideConfig(slotSize, slotCount);
    }

    @Override
    public void setOpenParams(@NonNull SQLiteDatabase.OpenParams openParams) {
        super.setOpenParams(openParams);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }
}