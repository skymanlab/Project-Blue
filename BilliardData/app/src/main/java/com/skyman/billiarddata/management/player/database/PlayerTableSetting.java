package com.skyman.billiarddata.management.player.database;

import android.provider.BaseColumns;

public class PlayerTableSetting {

    // class constant : create table
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + Entry.TABLE_NAME + "(" +
                    Entry._COUNT + " INTEGER PRIMARY KEY, " +           // 0. count
                    Entry.COLUMN_NAME_BILLIARD_COUNT + " INTEGER, " +   // 1. billiard count
                    Entry.COLUMN_NAME_PLAYER_ID + " INTEGER, " +        // 2. player id
                    Entry.COLUMN_NAME_PLAYER_NAME + " TEXT, " +         // 3. player name
                    Entry.COLUMN_NAME_TARGET_SCORE + " INTEGER, " +     // 4. target score
                    Entry.COLUMN_NAME_SCORE + " INTEGER)";              // 5. score

    // class constant : if exists drop table
    public static final String SQL_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // class constant : select - all content
    public static final String SQL_SELECT_TABLE_ALL_ITEM =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // class constant : select - where billiard count
    public static final String SQL_SELECT_WHERE_BILLIARD_COUNT =
            "SELECT * FROM " + Entry.TABLE_NAME + " WHERE " + Entry.COLUMN_NAME_BILLIARD_COUNT + "=";

    // class constant : select - where billiard count
    public static final String SQL_SELECT_WHERE_PLAYER_ID =
            "SELECT * FROM " + Entry.TABLE_NAME + " WHERE " + Entry.COLUMN_NAME_PLAYER_ID + "=";

    // class constant : select - where billiard count
    public static final String SQL_SELECT_WHERE =
            "SELECT * FROM " + Entry.TABLE_NAME + " WHERE ";


    // inner class : Entry, Table column name
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "player";
        public static final String COLUMN_NAME_BILLIARD_COUNT = "billiardCount";    // 1. count
        public static final String COLUMN_NAME_PLAYER_ID = "playerId";               // 2. player id
        public static final String COLUMN_NAME_PLAYER_NAME = "playerName";           // 3. player name
        public static final String COLUMN_NAME_TARGET_SCORE = "targetScore";        // 4. target score
        public static final String COLUMN_NAME_SCORE = "score";                     // 5. score

    }
}
