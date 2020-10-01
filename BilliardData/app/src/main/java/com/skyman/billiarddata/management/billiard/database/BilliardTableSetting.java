package com.skyman.billiarddata.management.billiard.database;

import android.provider.BaseColumns;

/**
 * ===========================================================================================
 * billiardBasic 테이블을 생성하고 필요한 설정을 하기위한 클래스
 * ===========================================================================================
 * */
public  class BilliardTableSetting {
    // constant : sql query, create table setting
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME  + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_NAME_DATE + " TEXT, " +
                    Entry.COLUMN_NAME_TARGET_SCORE + " TEXT, " +
                    Entry.COLUMN_NAME_SPECIALITY + " TEXT, " +
                    Entry.COLUMN_NAME_PLAY_TIME + " TEXT," +
                    Entry.COLUMN_NAME_WINNER + " TEXT, " +
                    Entry.COLUMN_NAME_SCORE + " TEXT, " +
                    Entry.COLUMN_NAME_COST + " TEXT)" ;
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // constant : sql query, select table setting - all item
    /**
     * @see
     * */
    public static final String SQL_SELECT_TABLE_ALL_ITEM =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // constant : sql query, delete table setting - all item
    public static final String SQL_DELETE_TABLE_ALL_ITEM =
            "DELETE FROM " + Entry.TABLE_NAME;

    // constructor : private
    private BilliardTableSetting() {}

    // inner class : Entry, Table column name
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "billiard";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TARGET_SCORE = "targetScore";
        public static final String COLUMN_NAME_SPECIALITY = "speciality";
        public static final String COLUMN_NAME_PLAY_TIME = "playTime";
        public static final String COLUMN_NAME_WINNER = "winner";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_COST = "cost";
    }


}
