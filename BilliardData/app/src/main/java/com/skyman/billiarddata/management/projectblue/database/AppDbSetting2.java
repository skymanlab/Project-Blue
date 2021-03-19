package com.skyman.billiarddata.management.projectblue.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * project_blue.db 의 billiard, user, friend, player 테이블의 Query 문을 처리하는 메소드들이 있는 관리자 클래스들의 부모 클래스이다.
 * 즉, ProjectBlueDBHelper 클래스를 component 의 Context 를 받아 생성하는 역할을 한다.
 *
 * <p>
 * 이 클래스를 사용하는 방법의 직관성을 위해 ProjectBlueDbHelper 를 init_db method 에서 생성하며,
 * project_blue.db 와 그에 해당하는 table 의 생성작업을 한다.
 * <p>
 * 이 클래스를 생성하는 곳의 Context 를 받아 ProjectBlueDbHelper 의 생성 매개변수로 넘긴다.
 */
public class AppDbSetting2 {

    // constant
    private final String CLASS_NAME_LOG = "[DbM]_ProjectBlueDBManager";

    // instance variable
    private AppDbOpenHelper appDbOpenHelper;
    private Context context;

    // constructor
    public AppDbSetting2(Context context) {
        this.appDbOpenHelper = null;
        this.context = context;
    }

    public void createOpenHelper() {
        appDbOpenHelper = new AppDbOpenHelper(context);
    }

    public void closeOpenHelper() {
        appDbOpenHelper.close();
    }


    // ==================================== Request Query : insert ====================================
    public void requestInsertQuery(InsertQueryListener queryListener) {

        // format check : 형식이 일치할 때만
        if (queryListener.checkFormat()) {

            SQLiteDatabase writable = appDbOpenHelper.getWritableDatabase();

            long result = queryListener.requestQuery(writable);

            // 결과에 따라
            // -1 이면 데이터베이스 에러이다.
            if (result == -1) {
                queryListener.errorDatabase();
            } else {
                queryListener.successRequest(result);
            }

        } else {
            queryListener.errorFormat();
        }

    }

    // ==================================== Request Query : select ====================================
    public void requestSelectQuery(SelectQueryListener queryListener) {

        SQLiteDatabase readable = appDbOpenHelper.getReadableDatabase();

        if (queryListener.checkFormat()) {

            Cursor result = queryListener.requestQuery(readable);

            queryListener.successRequest(result);

            // cursor 는 닫아 주세요!
            result.close();

        } else {
            queryListener.errorFormat();
        }


    }


    // ==================================== Request Query : update ====================================
    public void requestUpdateQuery(UpdateQueryListener queryListener) {

        SQLiteDatabase writable = appDbOpenHelper.getWritableDatabase();

        if (queryListener.checkFormat()) {

            int numberOfUpdatedRows = queryListener.requestQuery(writable);

            queryListener.successRequest(numberOfUpdatedRows);

        } else {
            queryListener.errorFormat();
        }

    }


    // ==================================== Request Query : delete ====================================
    public void requestDeleteQuery(DeleteQueryListener queryListener) {

        SQLiteDatabase writable = appDbOpenHelper.getWritableDatabase();

        if (queryListener.checkFormat()) {

            int numberOfDeletedRows = queryListener.requestQuery(writable);

            queryListener.successRequest(numberOfDeletedRows);

        } else {
            queryListener.errorFormat();
        }

    }


    // ==================================== Interface ====================================
    public interface InsertQueryListener {
        boolean checkFormat();                          // 각 Object 의 입력값이 형식에 맞는 값인지 체크하고 그 결과를 리턴한다.

        long requestQuery(SQLiteDatabase writable);     // writable 을 이용하여 insert query 를 요청하고 결과를 리턴한다.

        void successRequest(long result);               // requestQuery() 결과를 이용하여 다음 과정을 진행한다.

        void errorDatabase();                           // requestQuery() 에서 데이터베이스 에러가 발생했을 때 조치를 한다.

        void errorFormat();                             // checkFormat() 의 결과가 false 일 때 조치를 한다.
    }

    public interface SelectQueryListener {
        boolean checkFormat();

        Cursor requestQuery(SQLiteDatabase readable);   // select Query 를 요청하고

        void successRequest(Cursor result);

        void errorFormat();
    }

    public interface UpdateQueryListener {
        boolean checkFormat();                          // 각 Object 의 입력값이 형식에 맞는 값인지 체크하고 그 결과를 리턴한다.

        int requestQuery(SQLiteDatabase writable);      // writable 을 이용하여 update query 를 요청하고 결과를 리턴한다.

        void successRequest(int result);                // requestQuery() 결과를 이용하여 다음 과정을 진행한다.

        void errorFormat();                             // checkFormat() 의 결과가 false 일 때 조치를 한다.
    }

    public interface DeleteQueryListener {
        boolean checkFormat();                          // 각 Object 의 입력값이 형식에 맞는 값인지 체크하고 그 결과를 리턴한다.

        int requestQuery(SQLiteDatabase writable);      // writable 을 이용하여 update query 를 요청하고 결과를 리턴한다.

        void successRequest(int result);                // requestQuery() 결과를 이용하여 다음 과정을 진행한다.

        void errorFormat();                             // checkFormat() 의 결과가 false 일 때 조치를 한다.
    }

}
