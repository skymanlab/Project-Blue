package com.skyman.billiarddata.management.player.database;

import android.provider.BaseColumns;
import android.telephony.emergency.EmergencyNumber;

import com.skyman.billiarddata.management.user.database.UserTableSetting;

public class PlayerTableSetting {

    // class constant : create table
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + Entry.TABLE_NAME + "(" +
                    Entry._COUNT + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_NAME_BILLIARD_COUNT + " INTEGER, " +
                    Entry.COLUMN_NAME_PLAYER_ID + " INTEGER, " +
                    Entry.COLUMN_NAME_TARGET_SCORE + " INTEGER, " +
                    Entry.COLUMN_NAME_SCORE + " INTEGER)";

    // class constant : if exists drop table
    public static final String SQL_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // class constant : select - where billiard count
    public static final String SQL_SELECT_WHERE_BILLIARD_COUNT =
            "SELECT * FROM " + Entry.TABLE_NAME + " WHERE " + Entry.COLUMN_NAME_BILLIARD_COUNT + "=";

    // inner class : Entry, Table column name
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "player";
        public static final String COLUMN_NAME_BILLIARD_COUNT = "billiardCount";
        public static final String COLUMN_NAME_PLAYER_ID ="playerId";
        public static final String COLUMN_NAME_TARGET_SCORE = "targetScore";
        public static final String COLUMN_NAME_SCORE = "score";

    }
}
