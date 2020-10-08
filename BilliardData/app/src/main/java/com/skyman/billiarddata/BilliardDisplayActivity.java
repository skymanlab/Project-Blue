package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.database.BilliardDBManager;
import com.skyman.billiarddata.management.billiard.listview.BilliardLvManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;


public class BilliardDisplayActivity extends AppCompatActivity {

    // instance variable
    private BilliardDBManager billiardDbManager = null;
    private UserData userData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_display);

        // [lv/C]Intent : 전 Activity 에서 전달 된 Intent 가져오기
        Intent intent = getIntent();

        // [iv/C]UserData : 저장된 user 로 이 화면에 들어왔다.
        this.userData = SessionManager.getUserDataInIntent(intent);
        DeveloperManager.displayLog("[Ac]_BilliardDisplayActivity", "[onCreate] Intent 를 통해 user Data 를 가져왔습니다. 내용을 보겠습니다.");
        DeveloperManager.displayToUserData("[Ac]_BilliardDisplayActivity", this.userData);


        // [lv/C]ListView : allBilliardData mapping
        ListView allBilliardData = (ListView) findViewById(R.id.billiard_display_lv_all_billiard_data);

        // [iv/C]BilliardDBManager : billiard 테이블을 관리하는 메니저 생성과 초기화
        this.billiardDbManager = new BilliardDBManager(this);
        this.billiardDbManager.initDb();

        // [lv/C]BilliardLvManager : billiard 테이블의 모든 내용을 가져와 list view 와 연결하는 메니저 객체 생성
        BilliardLvManager billiardLvManager = new BilliardLvManager(allBilliardData);

        // [lv/C]BilliardLvManager : billiard 테이블의 모든 내용을 가져와 list view 와 연결
        billiardLvManager.setListViewOfBilliardData(this.billiardDbManager.loadAllContent());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // [check 1] : billiard 메니저가 생성되었다.
        if (this.billiardDbManager != null) {

            // [iv/C]BilliardDbManager : billiard 메니저를 종료한다.
            this.billiardDbManager.closeDb();

        } else {
           DeveloperManager.displayLog("[Ac]_BilliardDisplayActivity", "[onDestroy] billiard 메니저가 생성되지 않았습니다.");
        } // [check 1]

    } // End of method [onDestroy]
}