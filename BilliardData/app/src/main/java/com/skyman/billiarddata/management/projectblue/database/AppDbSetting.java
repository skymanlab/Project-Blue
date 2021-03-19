package com.skyman.billiarddata.management.projectblue.database;

import android.content.Context;

import com.skyman.billiarddata.developer.DeveloperManager;

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
public class AppDbSetting {

    // constant
    private final String CLASS_NAME_LOG = "[DbM]_ProjectBlueDBManager";

    // instance variable
    private AppDbOpenHelper dbOpenHelper;
    private Context targetContext;
    private boolean isInitializedDB;

    // constructor
    public AppDbSetting(Context targetContext) {
        this.dbOpenHelper = null;
        this.targetContext = targetContext;
        this.isInitializedDB = false;
    }


    /**
     * [method] isInitializedDB 의 값을 반환한다.
     *
     * @return dbOpenHelper 를 생성하여 project_blue.db 를 사용할 준비가 되었는지를 반환한다.
     * */
    public boolean isInitializedDB() {
        return isInitializedDB;
    } // End of method [isInitializedDB]


    /**
     * [method] dbOpenHelper 의 값을 반환한다.
     *
     * @return dbOpenHelper 객체를 반환한다.
     */
    public AppDbOpenHelper getDbOpenHelper() {
        return dbOpenHelper;
    }

    /**
     * [method] ProjectBlueDBHelper 를 생성한다.
     *
     * <p>
     * project_blue.db 를 만들고, 다음으로 billiard, user, friend 테이블을 만든다.
     * 만약 project_blue.db 가 없다면, 해당 db 를 만든고 table 도 만든다.
     * 그 밖의 다양한 경우를 고려해 db 와 table 을 만든다.
     * 그리고 위 의 과정이 끝나면 project_blue.db 를 open 한다.
     * </p>
     */
    public void initDb() {

        final String METHOD_NAME= "[initDb] ";

        // [iv/C]ProjectBlueDBHelper : 이 클래스를 생성한 Component 의 context 를 받아 생성한다.
        this.dbOpenHelper = new AppDbOpenHelper(this.targetContext);

        // [iv/b]isInitializedDB : dbOpenHelper 가 생성되었음을 알려준다.
        this.isInitializedDB = true;
//        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "project_blue.db 의 " + ProjectDbConstants.TABLES_NAME + " 을 사용할 준비가 완료되었습니다.");

    } // End of method [init_db]


    /**
     * [method] ProjectBlueDBHelper 를 초기화되었는지 검사하여, 초기화되었다면 close 한다.
     *
     */
    public void closeDb() {

        final String METHOD_NAME= "[closeDb] ";

        // [check] : inInitializedDB 를 통해 dbOpenHelper 가 초기화 되었다.
        if(this.isInitializedDB == true) {
            // [iv/C]ProjectBlueDBHelper : close
            this.dbOpenHelper.close();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "dbOpenHelper 가 초기화되지 않았습니다.");
        } // [check]
    } // End of method [closeDb]

}
