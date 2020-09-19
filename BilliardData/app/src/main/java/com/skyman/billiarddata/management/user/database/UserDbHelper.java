package com.skyman.billiarddata.management.user.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.skyman.billiarddata.management.projectdb.BilliardDatabase;

public class UserDbHelper extends SQLiteOpenHelper {

    public UserDbHelper(Context context) {
        super(context, BilliardDatabase.DATABASE_NAME, null, BilliardDatabase.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTableSetting.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserTableSetting.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade (SQLiteDatabase db,int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
