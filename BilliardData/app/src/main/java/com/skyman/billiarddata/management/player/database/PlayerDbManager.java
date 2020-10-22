package com.skyman.billiarddata.management.player.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.database.BilliardTableSetting;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.projectblue.database.ProjectBlueDBManager;

public class PlayerDbManager extends ProjectBlueDBManager {

    // constant
    private final String CLASS_NAME_LOG = "[DbM]_PlayerDbManager";

    // constructor
    public PlayerDbManager(Context targetContext) {
        super(targetContext);
    }


    /**
     * [method] [insert] ProjectBlueDBHelper 에서 writeableDatabase 를 가져와 player 테이블에 데이터를 저장한다.
     *
     * <p>
     * project_blue.db 의 player 테이블에 데이터를 insert query 문을 실행한다.
     *
     * <p>
     * 반환값
     * '-2' : 매개변수로 받은 값들이 형식에 맞지 않는다.
     * '-1' : 데이터베이스 문제 발생
     * '0' : 코드들이 실행되지 안았다.
     * 그 외의 값 : player 테이블에 입력 된 행 번호
     *
     * <p>
     * ContentValues 의 nullColumnHack 이 'null' 이라면, values 객체의 어떤 열에 값이 없으면 지금 내용을 insert query 가 실행 안 된다.
     * 이 '열 이름' 이라면, 해당 열에 값이 없다면 'null' 값을 넣는다.
     *
     * @return player 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(long billiardCount,
                            long playerId,
                            String playerName,
                            int targetScore,
                            int score) {

        final String METHOD_NAME = "[saveContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing ............");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수의 형식이 맞는 지 검사한다.
            if ((billiardCount > 0)
                    && (playerId > 0)
                    && !playerName.equals("")
                    && (targetScore >= 0)
                    && (score >= 0)) {
                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기            ==> SQLiteDatabase 는 모든 조건이 만족 했을 때 가져와서
                SQLiteDatabase writeDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, billiardCount);
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerId);
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerName);
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, targetScore);
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, score);

                // [lv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.                == > 사용하고
                newRowId = writeDb.insert(BilliardTableSetting.Entry.TABLE_NAME, null, insertValues);

                // [lv/C]SQLiteDatabase : close             == > 닫은 다음 결과값을 반환하든 가공하든 하면 된다.
                writeDb.close();

                // [check 3] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {
                    // 데이터 insert 실패
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "입력 성공했습니다. " + newRowId + " 값을 리턴합니다.");
                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들의 형식이 맞지 않아요.");
                newRowId = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete");
        return newRowId;
    } // End of method [saveContent]


    /**
     * [method] [insert] ProjectBlueDBHelper 에서 writeableDatabase 를 가져와 player 테이블에 데이터를 저장한다.
     *
     * <p>
     * project_blue.db 의 player 테이블에 데이터를 insert query 문을 실행한다.
     *
     * <p>
     * 반환값
     * '-2' : 매개변수로 받은 값들이 형식에 맞지 않는다.
     * '-1' : 데이터베이스 문제 발생
     * '0' : 코드들이 실행되지 안았다.
     * 그 외의 값 : player 테이블에 입력 된 행 번호
     *
     * <p>
     * ContentValues 의 nullColumnHack 이 'null' 이라면, values 객체의 어떤 열에 값이 없으면 지금 내용을 insert query 가 실행 안 된다.
     * 이 '열 이름' 이라면, 해당 열에 값이 없다면 'null' 값을 넣는다.
     *
     * @return player 테이블에 데이터를 입력한 결과를 반환한다.
     */
    public long saveContent(PlayerData playerData) {

        final String METHOD_NAME = "[saveContent] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is executing ............");

        // [lv/l]newRowId : 이 메소드의 결과 값 저장
        long newRowId = 0;

        // [check 1] : openDbHelper 가 초기화 되었다.
        if (this.isInitializedDB()) {

            // [check 2] : 매개변수의 형식이 맞는 지 검사한다.
            if ((playerData.getBilliardCount() > 0)
                    && (playerData.getPlayerId()> 0)
                    && !playerData.getPlayerName().equals("")
                    && (playerData.getTargetScore() >= 0)
                    && (playerData.getScore() >= 0)) {
                // [lv/C]SQLiteDatabase : openDbHelper 를 이용하여 writeableDatabase 가져오기            ==> SQLiteDatabase 는 모든 조건이 만족 했을 때 가져와서
                SQLiteDatabase writeDb = super.getDbOpenHelper().getWritableDatabase();

                // [lv/C]ContentValues : query 의 값들을 매개변수의 값들로 셋팅한다.
                ContentValues insertValues = new ContentValues();
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_BILLIARD_COUNT, playerData.getBilliardCount());
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_ID, playerData.getPlayerId());
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_PLAYER_NAME, playerData.getPlayerName());
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_TARGET_SCORE, playerData.getTargetScore());
                insertValues.put(PlayerTableSetting.Entry.COLUMN_NAME_SCORE, playerData.getScore());

                // [lv/l]newRowId : writeDb 를 insert 한 값 결과 값을 받는다. 실패하면 '-1' 이고 성공하면 1 이상의 값이 반환된다.                == > 사용하고
                newRowId = writeDb.insert(BilliardTableSetting.Entry.TABLE_NAME, null, insertValues);

                // [lv/C]SQLiteDatabase : close             == > 닫은 다음 결과값을 반환하든 가공하든 하면 된다.
                writeDb.close();

                // [check 3] : newRowId 값이 어떤 값이진 구분하여 결과를 반환한다.
                if (newRowId == -1) {
                    // 데이터 insert 실패
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "DB 저장 실패 : " + newRowId + " 값을 리턴합니다.");
                } else {
                    // 데이터 insert 성공
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "입력 성공했습니다. " + newRowId + " 값을 리턴합니다.");
                } // [check 3]

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "매개변수들의 형식이 맞지 않아요.");
                newRowId = -2;
            } // [check 2]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "openDBHelper 가 생성되지 않았습니다. 초기화 해주세요.");
        } // [check 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "The method is complete");
        return newRowId;
    } // End of method [saveContent]

}
