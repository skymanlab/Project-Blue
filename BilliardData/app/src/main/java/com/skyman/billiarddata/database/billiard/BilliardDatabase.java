package com.skyman.billiarddata.database.billiard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public  class BilliardDatabase {
    // SQL 1. create table setting
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME  + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_NAME_DATE + " TEXT, " +
                    Entry.COLUMN_NAME_VICTOREE + " TEXT, " +
                    Entry.COLUMN_NAME_SCORE + " TEXT, " +
                    Entry.COLUMN_NAME_COST + " TEXT, " +
                    Entry.COLUMN_NAME_PLAY_TIME + " TEXT)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // SQL 2. select table setting
    public static final String SQL_SELECT_TABLE_ALL_CONTENT =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // SQL 3. delete table all content
    public static final String SQL_DELETE_TABLE_ALL_CONTENT =
            "DELETE FROM " + Entry.TABLE_NAME;



    // constructor - private
    private BilliardDatabase() {}

    // inner class - Entry
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "billiardBasic";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_VICTOREE = "victoree";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_COST = "cost";
        public static final String COLUMN_NAME_PLAY_TIME = "play_time";
    }


}
