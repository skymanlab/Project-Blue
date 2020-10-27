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
                    Entry.COLUMN_NAME_DATE + " TEXT, " +                    // 1. date
                    Entry.COLUMN_NAME_GAME_MODE + " TEXT, " +               // 2. game mode
                    Entry.COLUMN_NAME_PLAYER_COUNT + " INTEGER, " +         // 3. player count
                    Entry.COLUMN_NAME_WINNER_ID + " INTEGER, " +            // 4. winner id
                    Entry.COLUMN_NAME_WINNER_NAME + " TEXT, " +             // 5. winner name
                    Entry.COLUMN_NAME_PLAY_TIME + " INTEGER," +             // 7. play time
                    Entry.COLUMN_NAME_SCORE + " TEXT, " +                   // 8. score
                    Entry.COLUMN_NAME_COST + " INTEGER)";                   // 9. cost

    // class constant : if exists drop table
    public static final String SQL_DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    // class constant : select - all content
    public static final String SQL_SELECT_ALL_CONTENT =
            "SELECT * FROM " + Entry.TABLE_NAME;

    // class constant : select - where id
//    public static final String SQL_SELECT_WHERE_ID =
//            "SELECT * FROM " + Entry.TABLE_NAME + " WHERE " + Entry.COLUMN_NAME_USER_ID + "=";

    // class constant : select - where count
    public static final String SQL_SELECT_WHERE_COUNT =
            "SELECT * FROM " + Entry.TABLE_NAME + " WHERE " + Entry._COUNT + "=";

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
        public static final String COLUMN_NAME_GAME_MODE = "gameMode";                  // 2. game mode
        public static final String COLUMN_NAME_PLAYER_COUNT = "playerCount";            // 3. player count
        public static final String COLUMN_NAME_WINNER_ID = "winnerId";                  // 4. winner id
        public static final String COLUMN_NAME_WINNER_NAME = "winnerName";              // 5. winner name
        public static final String COLUMN_NAME_PLAY_TIME = "playTime";                  // 6. play time
        public static final String COLUMN_NAME_SCORE = "score";                         // 7. score
        public static final String COLUMN_NAME_COST = "cost";                           // 8. cost
    }
}
