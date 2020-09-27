package com.skyman.billiarddata.management.user.database;

import android.provider.BaseColumns;

import com.skyman.billiarddata.management.billiard.database.BilliardTableSetting;

public class UserTableSetting {

    // constant : sql query, create table setting
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE  " + Entry.TABLE_NAME + "(" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +                              // 0. id
                    Entry.COLUMN_NAME_NAME + " TEXT NOT NULL, " +                       // 1. name
                    Entry.COLUMN_NAME_TARGET_SCORE + " INTEGER NOT NULL, " +            // 2. target score
                    Entry.COLUMN_NAME_SPECIALITY + " TEXT NOT NULL, " +                 // 3. speciality
                    Entry.COLUMN_NAME_GAME_RECORD_WIN + " INTEGER, " +                  // 4. game record win
                    Entry.COLUMN_NAME_GAME_RECORD_LOSS + " INTEGER, " +                 // 5. game record loss
                    Entry.COLUMN_NAME_RECENT_GAME_PLAYER_ID + " INTEGER, " +            // 6. recent game player id
                    Entry.COLUMN_NAME_RECENT_PLAY_DATE + " TEXT, " +                    // 7. recent play date
                    Entry.COLUMN_NAME_TOTAL_PLAY_TIME + " INTEGER, " +                  // 8. total play time
                    Entry.COLUMN_NAME_TOTAL_COST + " INTEGER )";                        // 9. total cost
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // constant : sql query,  table setting
    public static final String SQL_SELECT_TABLE_ALL_ITEM =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // constant : sql query, delete table setting - all item
    public static final String SQL_DELETE_TABLE_ALL_ITEM =
            "DELETE FROM " + Entry.TABLE_NAME;

    // constructor : private
    private UserTableSetting(){}

    // inner class : Entry, Table column name
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";                                       // 1. name
        public static final String COLUMN_NAME_TARGET_SCORE = "targetScore";                        // 2. target score
        public static final String COLUMN_NAME_SPECIALITY = "speciality";                           // 3. speciality
        public static final String COLUMN_NAME_GAME_RECORD_WIN = "gameRecordWin";                   // 4. game record win
        public static final String COLUMN_NAME_GAME_RECORD_LOSS = "gameRecordLoss";                 // 5. game record loss
        public static final String COLUMN_NAME_RECENT_GAME_PLAYER_ID = "recentGamePlayerId";        // 6. recent game player id
        public static final String COLUMN_NAME_RECENT_PLAY_DATE = "recentPlayDate";                 // 7. recent play date
        public static final String COLUMN_NAME_TOTAL_PLAY_TIME ="totalPlayTime";                    // 8. total play time
        public static final String COLUMN_NAME_TOTAL_COST = "totalCost";                            // 9. total cost

    }

}
