package com.skyman.billiarddata.management.friend.database;

import android.provider.BaseColumns;

public class FriendTableSetting {

    // constant : sql query, create table setting
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._COUNT + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_NAME_NAME + " TEXT)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME ;

    // constant : sql query, select table setting
    public static final String SQL_SELECT_TABLE_ALL_ITEM =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // constructor : private
    private FriendTableSetting() {

    }

    // inner class : Entry, Table column name
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "friend";
        public static final String COLUMN_NAME_NAME = "name";
    }
}
