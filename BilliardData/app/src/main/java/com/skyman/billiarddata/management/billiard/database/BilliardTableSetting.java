package com.skyman.billiarddata.management.billiard.database;

import android.provider.BaseColumns;

import com.skyman.billiarddata.management.friend.database.FriendTableSetting;

/**
 * [class] project_blue.db 의 billiard 테이블의 셋팅하기 위한 정보가 담겨 있는 클래스이다.
 *
 * <p>
 * billiard 테이블을 관리하기 위한 Query 문이 있다.
 *
 * <p>
 * Entry : billiard 테이블의 이름과 column 의 이름이 담겨 있다.
 */
public class BilliardTableSetting {

    // class constant : create table
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._COUNT + " INTEGER PRIMARY KEY, " +               // 0. count
                    Entry.COLUMN_NAME_USER_ID + " INTEGER, " +              // 1. user id
                    Entry.COLUMN_NAME_DATE + " TEXT, " +                    // 2. date
                    Entry.COLUMN_NAME_TARGET_SCORE + " INTEGER, " +         // 3. target score
                    Entry.COLUMN_NAME_SPECIALITY + " TEXT, " +              // 4. speciality
                    Entry.COLUMN_NAME_PLAY_TIME + " INTEGER," +             // 5. play time
                    Entry.COLUMN_NAME_WINNER + " TEXT, " +                  // 6. winner
                    Entry.COLUMN_NAME_SCORE + " TEXT, " +                   // 7. score
                    Entry.COLUMN_NAME_COST + " INTEGER)";                   // 8. cost

    // class constant : if exists drop table
    public static final String SQL_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // class constant : select - all content
    public static final String SQL_SELECT_ALL_CONTENT =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // class constant : select - where id
    public static final String SQL_SELECT_WHERE_ID =
            "SELECT * FROM " + Entry.TABLE_NAME + " WHERE " + Entry.COLUMN_NAME_USER_ID + "=";

    // class constant : delete - all content
    public static final String SQL_DELETE_ALL_CONTENT =
            "DELETE FROM " + Entry.TABLE_NAME;

    // constructor : private
    private BilliardTableSetting() {
    }


    // inner class : Entry, Table column name
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "billiard";
        public static final String COLUMN_NAME_DATE = "date";                           // 1. date
        public static final String COLUMN_NAME_USER_ID = "userId";                      // 2. user id
        public static final String COLUMN_NAME_TARGET_SCORE = "targetScore";            // 3. target score
        public static final String COLUMN_NAME_SPECIALITY = "speciality";               // 4. speciality
        public static final String COLUMN_NAME_PLAY_TIME = "playTime";                  // 5. play time
        public static final String COLUMN_NAME_WINNER = "winner";                       // 6. winner
        public static final String COLUMN_NAME_SCORE = "score";                         // 7. score
        public static final String COLUMN_NAME_COST = "cost";                           // 8. cost
    }
}
