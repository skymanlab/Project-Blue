package com.skyman.billiarddata.management.projectblue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.database.BilliardTableSetting;
import com.skyman.billiarddata.management.billiard.database.BilliardTableSettingT;
import com.skyman.billiarddata.management.friend.database.FriendTableSetting;
import com.skyman.billiarddata.management.user.database.UserTableSetting;


/**
 * [class] 이 클래스는 SQLiteOpenHelper 를 상속받아 하나의 db 를 생성하고, 이 db 에 table 을 생성하는 역할을 한다.
 *         db 파일은 이 클래스가 생성될 때, DATABASE_NAME 과 DATABASE_VERSION 을 생성자 매개변수를 받아 생성한다.
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
 * - onOpen method 는 DATABASE_NAME 의 db를 열었을 때 불려지는 콜백함수이다.
 * <p>
 * 이 클래스로 Open 한 db 는 getReadableDatabase 와 getWriteableDatabase method 를 이용하여 SQLiteDatabase 객체를 반환한다.
 * 위 의 SQLiteDatabase 객체를 이용하여 해당 db 의 테이블의 내용을 create, insert, select, update, delete 문을 이용하여 사용할 수 있다.
 */
public class ProjectBlueDBHelper extends SQLiteOpenHelper {

    // constructor
    public ProjectBlueDBHelper(Context context) {
        super(context, ProjectBlueDBInfo.DATABASE_NAME, null, ProjectBlueDBInfo.DATABASE_VERSION);
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[constructor] The project_blue.db is ready to create table");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // onCreate 는 project_blue.db 가 없을 때 실행되는 메소드이다.
        // 즉, constructor 의 매개변수 값으로 들어간 데이터베이스 이름으로 project_blue.db 가 있는지 검사하는 것이다.
        // project_blue.db 를 생성하면서 모든 테이블을 생성한다. billiard, user 테이블을 생성한다.
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onCreate] The method is executing........");
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onCreate] The project_blue.db is not exist.");
        db.execSQL(BilliardTableSettingT.SQL_CREATE_TABLE);                // billiard
        db.execSQL(UserTableSetting.SQL_CREATE_ENTRIES);                    // user
        db.execSQL(FriendTableSetting.SQL_CREATE_ENTRIES);                  // friend
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onCreate] The project_blue.db has been created.");
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onCreate] billiard table has been created.");
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onCreate] user table has been created.");
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onCreate] friend table has been created.");
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onCreate] All tables must be created when creating a project_blue.db. ");
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onCreate] The method is complete.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // project_blue.db 버전 업그레이드 하면, project_blue.db의 모든 테이블을 삭제 후, 업그레이드 된 테이블을 생성한다.
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onUpgrade] The method is executing........");
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onUpgrade] The project_blue.db is upgrading.");
        db.execSQL(BilliardTableSettingT.SQL_DROP_TABLE_IF_EXISTS);                // billiard
        db.execSQL(UserTableSetting.SQL_DELETE_ENTRIES);                    // user
        db.execSQL(FriendTableSetting.SQL_DELETE_ENTRIES);                  // friend
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onUpgrade] already existed table delete.");
        onCreate(db);
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onUpgrade] The method is complete.");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // project_blue.db 버전 다운그레이드 하면, project_blue.db의 모든 테이블을 삭제 후, 다운그레이드 된 테이블을 생성한다.
        onUpgrade(db, oldVersion, newVersion);
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onDowngrade] The method is complete!");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // project_blue.db 에 billiard, user 테이블이 생성되면(존재하면), onOpen method 가 실행 된다.
        super.onOpen(db);
        DeveloperManager.displayLog("[DbH] ProjectBlueDatabaseHelper", "[onOpen] The method is complete.");
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