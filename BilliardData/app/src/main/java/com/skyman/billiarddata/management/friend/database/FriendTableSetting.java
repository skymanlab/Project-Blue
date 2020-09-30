package com.skyman.billiarddata.management.friend.database;

import android.provider.BaseColumns;

public class FriendTableSetting {

    // constant : sql query, create table setting
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +                      // 0. id                - integer
                    Entry.COLUMN_NAME_USER_ID + " INTEGER, " +                  // 1. user id           - integer
                    Entry.COLUMN_NAME_NAME + " TEXT, " +                        // 2. name              - text
                    Entry.COLUMN_NAME_GAME_RECORD_WIN + " INTEGER, " +          // 3. game record win   - integer
                    Entry.COLUMN_NAME_GAME_RECORD_LOSS + " INTEGER, " +         // 4. game record loss  - integer
                    Entry.COLUMN_NAME_RECENT_PLAY_DATE + " TEXT, " +            // 5. recent play date  - text
                    Entry.COLUMN_NAME_TOTAL_PLAY_TIME + " INTEGER, " +          // 6. total play time   - integer
                    Entry.COLUMN_NAME_TOTAL_COST + " INTEGER )";                // 7. total cost        - integer
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME ;

    // constant : sql query, select table setting
    public static final String SQL_SELECT_TABLE_ALL_ITEM =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // constant : sql query, where select
    public static final String SQL_SELECT_TABLE_WHERE_ID =
            "SELECT * FROM " + Entry.TABLE_NAME + "WHERE ID=";

    // constructor : private
    private FriendTableSetting() {

    }

    // inner class : Entry, Table column name
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "friend";
        public static final String COLUMN_NAME_USER_ID = "userId";                          // 1. user id
        public static final String COLUMN_NAME_NAME = "name";                               // 2. name
        public static final String COLUMN_NAME_GAME_RECORD_WIN = "gameRecordWin";           // 3. game record win
        public static final String COLUMN_NAME_GAME_RECORD_LOSS = "gameRecordLoss";         // 4. game record loss
        public static final String COLUMN_NAME_RECENT_PLAY_DATE = "recentPlayDate";         // 5. recent play date
        public static final String COLUMN_NAME_TOTAL_PLAY_TIME = "totalPlayTime";           // 6. total play time
        public static final String COLUMN_NAME_TOTAL_COST = "totalCost";                    // 7. total cost


    }
}
