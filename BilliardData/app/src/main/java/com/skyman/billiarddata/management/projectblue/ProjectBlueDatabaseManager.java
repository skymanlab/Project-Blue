package com.skyman.billiarddata.management.projectblue;

import android.content.Context;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.user.database.UserDbHelper;

public class ProjectBlueDatabaseManager {

    // value : SQLite DB open helper 객체 선언
    ProjectBlueDatabaseHelper projectBlueDatabaseHelper;

    // value : 생성 요청하는 activity 관련 된 객체 선언
    Context targetContext;

    // constructor
    public ProjectBlueDatabaseManager(Context targetContext) {
        this.projectBlueDatabaseHelper = null;                       // 직관성을 위해, 객체 생성은 init_db 에서 한다.
        this.targetContext = targetContext;
    }

    /* method : init, SQLite DB open helper 를 이용하여 초기화 */
    public void init_db() {
        /*
         * ====================================================================
         * project_blue.db 에 billiard, user 테이블의 존재 여부 확인한다.
         * 없으면 project_blue.db 생성 후 billiard, user 테이블을 생성하고,
         * project_blue.db 를 open 한다.
         * ====================================================================
         * */
        projectBlueDatabaseHelper = new ProjectBlueDatabaseHelper(targetContext);
        DeveloperManager.displayLog("ProjectBlueDatabaseManager", "** init_db function is complete!");
    }
}
