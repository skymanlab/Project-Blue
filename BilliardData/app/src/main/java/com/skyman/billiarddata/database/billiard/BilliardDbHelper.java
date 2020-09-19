package com.skyman.billiarddata.database.billiard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ===========================================================================================
 * billiardBasic 테이블을 접근하여 사용하기 위해 SQLiteOpenHelper를 상속받은 클래스
 * ===========================================================================================
 * */
public class BilliardDbHelper extends SQLiteOpenHelper {

    // database setting
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "billiard.db";

    // constructor
    public BilliardDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // billiardBasic 테이블 생성
        db.execSQL(BilliardTableSetting.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(BilliardTableSetting.SQL_DELETE_ENTRIES);
            onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       onUpgrade(db, oldVersion, newVersion);
    }
}
