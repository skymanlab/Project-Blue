package com.skyman.billiarddata.management.billiard.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.projectblue.database.AppDbLog;
import com.skyman.billiarddata.management.projectblue.database.AppDbSetting2;

import java.util.ArrayList;


public class BilliardDbManager2 {

    // constant
    private final String CLASS_NAME = BilliardDbManager2.class.getSimpleName();

    // instance variable
    private AppDbSetting2 appDbSetting2;

    // constructor
    public BilliardDbManager2(AppDbSetting2 appDbSetting2) {
        this.appDbSetting2 = appDbSetting2;
    }

    // 0. count
    // 1. date
    // 2. game mode
    // 3. player count
    // 4. winner id
    // 5. winner name
    // 6. play time
    // 7. score
    // 8. cost


    // ==================================================== Insert Query ====================================================
    public long saveContent(String date,            // 1. date
                            String gameMode,        // 2. game mode
                            int playerCount,        // 3. player count
                            long winnerId,          // 4. winner Id
                            String winnerName,      // 5. winner name
                            int playTime,           // 6. play time
                            String score,           // 7. score
                            int cost) {             // 8. cost

        final long[] rowNumber = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (!date.equals("") &&
                                !gameMode.equals("") &&
                                (playerCount >= 0) &&
                                (winnerId > 0) &&
                                !winnerName.equals("") &&
                                (playTime >= 0) &&
                                !score.equals("") &&
                                (cost >= 0)) ? true : false;
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, date);                    // 1. date
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, gameMode);           // 2. game mode
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAYER_COUNT, playerCount);     // 3. player count
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_ID, winnerId);           // 4. winner id
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_NAME, winnerName);       // 5. winner name
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, playTime);           // 6. play time
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, score);                  // 7. score
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, cost);                    // 8. cost

                        return writable.insert(
                                BilliardTableSetting.Entry.TABLE_NAME,
                                null,
                                insertValues
                        );
                    }

                    @Override
                    public void successRequest(long result) {
                        rowNumber[0] = result;
                    }

                    @Override
                    public void errorDatabase() {
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, BilliardDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, BilliardData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContent]

    /**
     * BilliardData 에 담긴 데이터를 billiard 테이블에 저장한다.
     * primary key 가 정해져 있으므로 해아 primary key 값으로 저장된다.
     *
     * @param billiardData 저장할 데이터가 담긴 객체
     * @return 새로 생성된 행의 ID 이거나 데이터 삽입 오류(-1)
     */
    public long saveContent(BilliardData billiardData) {

        final long[] rowNumber = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfBilliardData(billiardData, false);
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(BilliardTableSetting.Entry._COUNT, billiardData.getCount());                             // 0. count
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, billiardData.getDate());                    // 1. date
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, billiardData.getGameMode());           // 2. game mode
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAYER_COUNT, billiardData.getPlayerCount());     // 3. player count
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_ID, billiardData.getWinnerId());           // 4. winner id
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_NAME, billiardData.getWinnerName());       // 5. winner name
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, billiardData.getPlayTime());           // 6. play time
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, billiardData.getScore());                  // 7. score
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, billiardData.getCost());                    // 8. cost

                        return writable.insert(
                                BilliardTableSetting.Entry.TABLE_NAME,
                                null,
                                insertValues
                        );

                    }

                    @Override
                    public void successRequest(long result) {
                        rowNumber[0] = result;
                    }

                    @Override
                    public void errorDatabase() {
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, BilliardDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, BilliardData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];
    } // End of method [saveContent]


    /**
     * BilliardData 에 담긴 데이터를 billiard 테이블에 저장한다.
     * primary key 가 autoincrement 이므로, primary key 가 포함되어 있지 않으므로 billiard 테이블의 sqlite_sequence 에서 +1 한 값이 자동으로 할당된다.
     *
     * @param billiardData 저장할 데이터가 담긴 객체
     * @return 새로 생성된 행의 ID 이거나 데이터 삽입 오류(-1)
     */
    public long saveContentExceptPrimaryKey(BilliardData billiardData) {

        final long[] rowNumber = {0};

        appDbSetting2.requestInsertQuery(
                new AppDbSetting2.InsertQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfBilliardData(billiardData, true);
                    }

                    @Override
                    public long requestQuery(SQLiteDatabase writable) {

                        ContentValues insertValues = new ContentValues();
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, billiardData.getDate());                    // 1. date
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, billiardData.getGameMode());           // 2. game mode
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAYER_COUNT, billiardData.getPlayerCount());     // 3. player count
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_ID, billiardData.getWinnerId());           // 4. winner id
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_NAME, billiardData.getWinnerName());       // 5. winner name
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, billiardData.getPlayTime());           // 6. play time
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, billiardData.getScore());                  // 7. score
                        insertValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, billiardData.getCost());                    // 8. cost

                        return writable.insert(
                                BilliardTableSetting.Entry.TABLE_NAME,
                                null,
                                insertValues
                        );
                    }

                    @Override
                    public void successRequest(long result) {
                        rowNumber[0] = result;
                    }

                    @Override
                    public void errorDatabase() {
                        AppDbLog.printDatabaseErrorLog(CLASS_NAME, BilliardDbManager2.class.getSimpleName());
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, BilliardData.class.getSimpleName());
                    }
                }
        );

        return rowNumber[0];

    } // End of method [saveContentExceptPrimaryKey]


    // ==================================================== Select Query ====================================================

    /**
     * billiard 테이블에서 count 행에서 countOfBilliard 에 해당하는 열을 BilliardData 객체에 담아서 반환한다.
     *
     * @param count
     * @return
     */
    public BilliardData loadContentByCount(long count) {

        final BilliardData[] billiardData = {new BilliardData()};

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < count) ? true : false;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {

                        return readable.rawQuery(
                                BilliardTableSetting.SQL_SELECT_WHERE_COUNT + count,
                                null
                        );
                    }

                    @Override
                    public void successRequest(Cursor result) {

                        if (result.moveToFirst()) {

                            billiardData[0] = new BilliardData();
                            billiardData[0].setCount(result.getLong(0));
                            billiardData[0].setDate(result.getString(1));
                            billiardData[0].setGameMode(result.getString(2));
                            billiardData[0].setPlayerCount(result.getInt(3));
                            billiardData[0].setWinnerId(result.getLong(4));
                            billiardData[0].setWinnerName(result.getString(5));
                            billiardData[0].setPlayTime(result.getInt(6));
                            billiardData[0].setScore(result.getString(7));
                            billiardData[0].setCost(result.getInt(8));

                        }

                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, BilliardData.class.getSimpleName());
                    }
                }
        );

        return billiardData[0];
    } // End of method [loadAllContent]


    /**
     * billiard 테이블의 모든 데이터를 ArrayList 에 담아서 반환한다.
     *
     * @return billiard 테이블의 모든 행의 데이터가 담기 ArrayList
     */
    public ArrayList<BilliardData> loadAllContent() {

        // 결과를 담는다.
        ArrayList<BilliardData> billiardDataArrayList = new ArrayList<>();

        appDbSetting2.requestSelectQuery(
                new AppDbSetting2.SelectQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return true;
                    }

                    @Override
                    public Cursor requestQuery(SQLiteDatabase readable) {
                        return readable.rawQuery(
                                BilliardTableSetting.SQL_SELECT_ALL_CONTENT,
                                null
                        );
                    }

                    @Override
                    public void successRequest(Cursor result) {

                        while (result.moveToNext()) {

                            BilliardData billiardData = new BilliardData();
                            billiardData.setCount(result.getLong(0));
                            billiardData.setDate(result.getString(1));
                            billiardData.setGameMode(result.getString(2));
                            billiardData.setPlayerCount(result.getInt(3));
                            billiardData.setWinnerId(result.getLong(4));
                            billiardData.setWinnerName(result.getString(5));
                            billiardData.setPlayTime(result.getInt(6));
                            billiardData.setScore(result.getString(7));
                            billiardData.setCost(result.getInt(8));

                            billiardDataArrayList.add(billiardData);

                        }

                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, BilliardData.class.getSimpleName());
                    }
                }
        );

        return billiardDataArrayList;
    } // End of method [loadAllContent]


    // ==================================================== Update Query ====================================================

    /**
     * count 행에서 billiardData 의 count 에 해당하는 열의 date, gameMode, playerCount, winnerId, winnerName, playTime, score, cost 행을 업데이트한다.
     *
     * @param billiardData 업데이트 할 데이터가 담겨있는 객체
     * @return 업데이트된 행의 수
     */
    public int updateContent(BilliardData billiardData) {

        final int[] numberOfUpdatedRows = {0};

        appDbSetting2.requestUpdateQuery(
                new AppDbSetting2.UpdateQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return checkFormatOfBilliardData(billiardData, false);
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        // update 할 column 만 입력
                        // column : date, gameMode, playerCount, winnerId, winnerName, playTime, score, cost
                        ContentValues updateValues = new ContentValues();
                        updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_DATE, billiardData.getDate());                      // 1. date
                        updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_GAME_MODE, billiardData.getGameMode());             // 2. game mode
                        updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAYER_COUNT, billiardData.getPlayerCount());       // 3. player count
                        updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_ID, billiardData.getWinnerId());             // 4. winner id
                        updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_WINNER_NAME, billiardData.getWinnerName());         // 5. winner name
                        updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_PLAY_TIME, billiardData.getPlayTime());             // 6. play time
                        updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_SCORE, billiardData.getScore());                    // 7. score
                        updateValues.put(BilliardTableSetting.Entry.COLUMN_NAME_COST, billiardData.getCost());                      // 8. cost


                        // update 할 row 는 무엇인지 정하기
                        String selection = BilliardTableSetting.Entry._COUNT + " LIKE ?";
                        String[] selectionArgs = {billiardData.getCount() + ""};

                        return writable.update(
                                BilliardTableSetting.Entry.TABLE_NAME,
                                updateValues,
                                selection,
                                selectionArgs
                        );
                    }

                    @Override
                    public void successRequest(int result) {
                        numberOfUpdatedRows[0] = result;
                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, BilliardData.class.getSimpleName());
                    }
                }
        );

        return numberOfUpdatedRows[0];
    } // End of method [updateContentByCount]


    // ==================================================== Delete Query ====================================================

    /**
     * billiard 테이블의 모든 행을 삭제한다.
     *
     * @return 삭제된 행의 수
     */
    public int deleteAllContent() {

        final int numberOfDeletedRows[] = {0};

        appDbSetting2.requestDeleteQuery(
                new AppDbSetting2.DeleteQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return true;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {
                        return writable.delete(
                                BilliardTableSetting.Entry.TABLE_NAME,
                                null,
                                null
                        );
                    }

                    @Override
                    public void successRequest(int result) {
                        numberOfDeletedRows[0] = result;

                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, BilliardData.class.getSimpleName());
                    }
                }
        );

        return numberOfDeletedRows[0];
    } // End of method [deleteAllContent]


    /**
     * count 행에서 countOfBilliard 에 해당하는 열을 삭제한다.
     *
     * @param count billiardData 에서 count 값
     * @return 삭제된 행의 수
     */
    public int deleteContentByCount(long count) {

        final int numberOfDeletedRows[] = {0};

        appDbSetting2.requestDeleteQuery(
                new AppDbSetting2.DeleteQueryListener() {
                    @Override
                    public boolean checkFormat() {
                        return (0 < count) ? true : false;
                    }

                    @Override
                    public int requestQuery(SQLiteDatabase writable) {

                        String selection = BilliardTableSetting.Entry._COUNT + " LIKE ?";
                        String[] selectionArgs = {count + ""};

                        return writable.delete(
                                BilliardTableSetting.Entry.TABLE_NAME,
                                selection,
                                selectionArgs
                        );
                    }

                    @Override
                    public void successRequest(int result) {
                        numberOfDeletedRows[0] = result;

                    }

                    @Override
                    public void errorFormat() {
                        AppDbLog.printFormatErrorLog(CLASS_NAME, BilliardData.class.getSimpleName());
                    }
                }
        );

        return numberOfDeletedRows[0];
    } // End of method [deleteContentByCount]


    // ==================================================== Check Format ====================================================

    /**
     * [method] BilliardData 의 값들이 형식에 맞는지 검사한다. 모든 형식이 맞으면 true 를 하나라도 다르면 false 를 반환한다.
     */
    private boolean checkFormatOfBilliardData(BilliardData billiardData, boolean exceptsPrimaryKey) {

        // 형식이 일치하는가?
        boolean matchesFormat = false;

        if (!exceptsPrimaryKey) {

            // primary key 포함하여 형식 검사
            if ((billiardData.getCount() > 0) &&                    // 0. count (primary key)
                    !billiardData.getDate().equals("") &&           // 1. date
                    !billiardData.getGameMode().equals("") &&       // 2. game mode
                    (billiardData.getPlayerCount() >= 0) &&         // 3. player count
                    (billiardData.getWinnerId() > 0) &&             // 4. winner id
                    !billiardData.getWinnerName().equals("") &&     // 5. winner name
                    (billiardData.getPlayTime() >= 0) &&            // 6. play time
                    !billiardData.getScore().equals("") &&          // 7. score
                    (billiardData.getCost() >= 0)) {                // 8. cost
                matchesFormat = true;
            } else {
                matchesFormat = false;
            }

        } else {

            // primary key 제외하고 형식 검사
            if (!billiardData.getDate().equals("") &&               // 1. date
                    !billiardData.getGameMode().equals("") &&       // 2. game mode
                    (billiardData.getPlayerCount() >= 0) &&         // 3. player count
                    (billiardData.getWinnerId() > 0) &&             // 4. winner id
                    !billiardData.getWinnerName().equals("") &&     // 5. winner name
                    (billiardData.getPlayTime() >= 0) &&            // 6. play time
                    !billiardData.getScore().equals("") &&          // 7. score
                    (billiardData.getCost() >= 0)) {                // 8. cost
                matchesFormat = true;
            } else {
                matchesFormat = false;
            }
        }

        return matchesFormat;
    } // End of method [checkFormatOfBilliardData]

}
