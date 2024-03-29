package com.skyman.billiarddata.table.user.database;

import android.provider.BaseColumns;

public class UserTableSetting {

    // class constant : create table
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE  " + Entry.TABLE_NAME + "(" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +                              // 0. id
                    Entry.COLUMN_NAME_NAME + " TEXT NOT NULL, " +                       // 1. name
                    Entry.COLUMN_NAME_TARGET_SCORE + " INTEGER NOT NULL, " +            // 2. target score
                    Entry.COLUMN_NAME_SPECIALITY + " TEXT NOT NULL, " +                 // 3. speciality
                    Entry.COLUMN_NAME_GAME_RECORD_WIN + " INTEGER, " +                  // 4. game record win
                    Entry.COLUMN_NAME_GAME_RECORD_LOSS + " INTEGER, " +                 // 5. game record loss
                    Entry.COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT + " INTEGER, " +       // 6. recent game billiard count
                    Entry.COLUMN_NAME_TOTAL_PLAY_TIME + " INTEGER, " +                  // 7. total play time
                    Entry.COLUMN_NAME_TOTAL_COST + " INTEGER )";                        // 8. total cost

    // class constant : if exists drop table
    public static final String SQL_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // class constant : select - all content
    public static final String SQL_SELECT_ALL_CONTENT =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // class constant : select - where id
    public static final String SQL_SELECT_WHERE_ID =
            "SELECT * FROM " + Entry.TABLE_NAME + " WHERE " + Entry._ID + "=";

    // class constant : delete - all content
    public static final String SQL_DELETE_ALL_CONTENT =
            "DELETE FROM " + Entry.TABLE_NAME;

    // constructor : private
    private UserTableSetting(){}

    // inner class : Entry, Table column name
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";                                               // 1. name
        public static final String COLUMN_NAME_TARGET_SCORE = "targetScore";                                // 2. target score
        public static final String COLUMN_NAME_SPECIALITY = "speciality";                                   // 3. speciality
        public static final String COLUMN_NAME_GAME_RECORD_WIN = "gameRecordWin";                           // 4. game record win
        public static final String COLUMN_NAME_GAME_RECORD_LOSS = "gameRecordLoss";                         // 5. game record loss
        public static final String COLUMN_NAME_RECENT_GAME_BILLIARD_COUNT = "recentGameBilliardCount";      // 6. recent game billiard count  -- 최근 게임에 대한 정보는 이 값으로 가져온다.
        public static final String COLUMN_NAME_TOTAL_PLAY_TIME ="totalPlayTime";                            // 7. total play time
        public static final String COLUMN_NAME_TOTAL_COST = "totalCost";                                    // 8. total cost

    }

}
