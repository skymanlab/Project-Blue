package com.skyman.billiarddata.management.user.database;

import android.provider.BaseColumns;

public class UserTableSetting {

    // constant : sql query, create table setting
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE  " + Entry.TABLE_NAME + "(" +
                    Entry.COLUMN_NAME_USERNAME + " TEXT NOT NULL, " +
                    Entry.COLUMN_NAME_TARGET_SCORE + " INTEGER NOT NULL, " +
                    Entry.COLUMN_NAME_SPECIALITY + " TEXT NOT NULL, " +
                    Entry.COLUMN_NAME_GAME_RECORD_WIN + " INTEGER, " +
                    Entry.COLUMN_NAME_GAME_RECORD_LOSS + " INTEGER, " +
                    Entry.COLUMN_NAME_TOTAL_PLAY_TIME + " INTEGER, " +
                    Entry.COLUMN_NAME_TOTAL_COST + " INTEGER )";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // constant : sql query,  table setting
    public static final String SQL_SELECT_TABLE_ALL_ITEM =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // constructor : private
    private UserTableSetting(){}

    // inner class : Entry, table desc
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "userName";
        public static final String COLUMN_NAME_TARGET_SCORE = "targetScore";
        public static final String COLUMN_NAME_SPECIALITY = "speciality";
        public static final String COLUMN_NAME_GAME_RECORD_WIN = "gameRecordWin";
        public static final String COLUMN_NAME_GAME_RECORD_LOSS = "gameRecordLoss";
        public static final String COLUMN_NAME_TOTAL_PLAY_TIME ="totalTime";
        public static final String COLUMN_NAME_TOTAL_COST = "totalCost";

    }

}
