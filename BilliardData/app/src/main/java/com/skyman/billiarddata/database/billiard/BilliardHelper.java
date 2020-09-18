package com.skyman.billiarddata.database.billiard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BilliardHelper extends SQLiteOpenHelper {

    // database setting
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "billiard.db";

    // constructor
    public BilliardHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BilliardDatabase.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(BilliardDatabase.SQL_DELETE_ENTRIES);
            onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       onUpgrade(db, oldVersion, newVersion);
    }
}
