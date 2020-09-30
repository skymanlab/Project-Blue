package com.skyman.billiarddata.management.billiard.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.friend.database.FriendTableSetting;
import com.skyman.billiarddata.management.projectblue.database.ProjectBlueDatabase;
import com.skyman.billiarddata.management.user.database.UserTableSetting;

/**
 * ===========================================================================================
 * billiardBasic 테이블을 접근하여 사용하기 위해 SQLiteOpenHelper를 상속받은 클래스
 * ===========================================================================================
 */
public class BilliardDbHelper extends SQLiteOpenHelper {

    // constructor
    public BilliardDbHelper(Context context){
        super(context, ProjectBlueDatabase.DATABASE_NAME, null, ProjectBlueDatabase.DATABASE_VERSION);
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[constructor] The project_blue.db is ready to create table");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // onCreate는 project_blue.db 가 없을 때 실행되는 메소드이다.
        // 즉, constructor 의 매개변수 값으로 들어간 데이터베이스 이름으로 project_blue.db 가 있는지 검사하는 것이다.
        // project_blue.db 를 생성하면서 모든 테이블을 생성한다. billiard, user 테이블을 생성한다.
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onCreate] The method is executing........");
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onCreate] The project_blue.db is not exist.");
        db.execSQL(BilliardTableSetting.SQL_CREATE_ENTRIES);                // billiard
        db.execSQL(UserTableSetting.SQL_CREATE_ENTRIES);                    // user
        db.execSQL(FriendTableSetting.SQL_CREATE_ENTRIES);                  // friend
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onCreate] The project_blue.db has been created.");
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onCreate] billiard table has been created.");
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onCreate] user table has been created.");
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onCreate] friend table has been created.");
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onCreate] All tables must be created when creating a project_blue.db. ");
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onCreate] The method is complete.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // project_blue.db 버전 업그레이드 하면, project_blue.db의 모든 테이블을 삭제 후, 업그레이드 된 테이블을 생성한다.
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onUpgrade] The method is executing........");
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onUpgrade] The project_blue.db is upgrading.");
        db.execSQL(BilliardTableSetting.SQL_DELETE_ENTRIES);                // billiard
        db.execSQL(UserTableSetting.SQL_DELETE_ENTRIES);                    // user
        db.execSQL(FriendTableSetting.SQL_DELETE_ENTRIES);                  // friend
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onUpgrade] already existed table delete.");
        onCreate(db);
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onUpgrade] The method is complete.");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // project_blue.db 버전 다운그레이드 하면, project_blue.db의 모든 테이블을 삭제 후, 다운그레이드 된 테이블을 생성한다.
        onUpgrade(db, oldVersion, newVersion);
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onDowngrade] The method is complete!");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // project_blue.db 에 billiard, user 테이블이 생성되면(존재하면), onOpen method 가 실행 된다.
        super.onOpen(db);
        DeveloperManager.displayLog("[DbH] BilliardDbHelper", "[onOpen] The method is complete.");
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
