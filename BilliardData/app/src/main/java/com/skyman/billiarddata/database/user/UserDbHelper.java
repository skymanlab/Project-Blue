package com.skyman.billiarddata.database.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDbHelper extends SQLiteOpenHelper {

    // database setting
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "user.db";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDatabase.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserDatabase.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade (SQLiteDatabase db,int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
