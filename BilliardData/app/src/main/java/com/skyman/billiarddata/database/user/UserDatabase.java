package com.skyman.billiarddata.database.user;

import android.provider.BaseColumns;

import com.skyman.billiarddata.database.billiard.BilliardDatabase;

import java.util.EmptyStackException;

public class UserDatabase {

    // SQLite query
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE  " + Entry.TABLE_NAME + "(" +
                    Entry.COLUMN_NAME_USERNAME + " TEXT NOT NULL, " +
                    Entry.COLUMN_NAME_TARGET_SCORE + " INTEGER NOT NULL, " +
                    Entry.COLUMN_NAME_GAME_RECORD + " TEXT, " +
                    Entry.COLUMN_NAME_TOTAL_COST + " INTEGER, " +
                    Entry.COLUMN_NAME_TOTAL_TIME + " INTEGER )";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;


    private UserDatabase(){}

    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "userName";
        public static final String COLUMN_NAME_TARGET_SCORE = "targetScore";
        public static final String COLUMN_NAME_GAME_RECORD = "gameRecord";
        public static final String COLUMN_NAME_TOTAL_TIME ="totalTime";
        public static final String COLUMN_NAME_TOTAL_COST = "totalCost";

    }

}
