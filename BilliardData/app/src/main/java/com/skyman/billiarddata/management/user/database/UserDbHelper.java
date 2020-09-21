package com.skyman.billiarddata.management.user.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.database.BilliardTableSetting;
import com.skyman.billiarddata.management.projectblue.ProjectBlueDatabase;

public class UserDbHelper extends SQLiteOpenHelper {

    public UserDbHelper(Context context) {
        super(context, ProjectBlueDatabase.DATABASE_NAME, null, ProjectBlueDatabase.DATABASE_VERSION);
        DeveloperManager.displayLog("UserDbHelper", "The project_blue.db is ready to create table");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // onCreate는 project_blue.db 가 없을 때 실행되는 메소드이다.
        // 즉, constructor 의 매개변수 값으로 들어간 데이터베이스 이름으로 project_blue.db 가 있는지 검사하는 것이다.
        // project_blue.db 를 생성하면서 모든 테이블을 생성한다. billiard, user 테이블을 생성한다.
        DeveloperManager.displayLog("UserDbHelper", "** onCreate function is executing........");
        DeveloperManager.displayLog("UserDbHelper", "The project_blue.db is not exist.");
        db.execSQL(BilliardTableSetting.SQL_CREATE_ENTRIES);
        db.execSQL(UserTableSetting.SQL_CREATE_ENTRIES);
        DeveloperManager.displayLog("UserDbHelper", "The project_blue.db has been created.");
        DeveloperManager.displayLog("UserDbHelper", "billiard table has been created.");
        DeveloperManager.displayLog("UserDbHelper", "user table has been created.");
        DeveloperManager.displayLog("UserDbHelper", "All tables must be created when creating a project_blue.db. ");
        DeveloperManager.displayLog("UserDbHelper", "** onCreate function is complete!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // billiard.db 버전 업그레이드 하면, project_blue.db의 모든 테이블을 삭제 후, 업그레이드 된 테이블을 생성한다.
        DeveloperManager.displayLog("UserDbHelper", "** onUpgrade function is executing........");
        DeveloperManager.displayLog("UserDbHelper", "The project_blue.db is upgrading.");
        db.execSQL(BilliardTableSetting.SQL_DELETE_ENTRIES);
        db.execSQL(UserTableSetting.SQL_DELETE_ENTRIES);
        DeveloperManager.displayLog("UserDbHelper", "already existed table delete.");
        onCreate(db);
        DeveloperManager.displayLog("UserDbHelper", "** onUpgrade function is complete!");
    }

    @Override
    public void onDowngrade (SQLiteDatabase db,int oldVersion, int newVersion){
        // billiard.db 버전 다운그레이드 하면, project_blue.db의 모든 테이블을 삭제 후, 다운그레이드 된 테이블을 생성한다.
        onUpgrade(db, oldVersion, newVersion);
        DeveloperManager.displayLog("UserDbHelper", "** onDowngrade function is complete!");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // project_blue.db 에 billiard, user 테이블이 생성되면(존재하면), onOpen method 가 실행 된다.
        super.onOpen(db);
        DeveloperManager.displayLog("UserDbHelper", "** onOpen function is complete.");
    }
}
