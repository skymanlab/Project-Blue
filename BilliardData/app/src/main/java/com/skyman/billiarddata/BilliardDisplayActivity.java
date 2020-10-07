package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.skyman.billiarddata.management.billiard.database.BilliardDBManager;
import com.skyman.billiarddata.management.billiard.listview.BilliardLvManager;


public class BilliardDisplayActivity extends AppCompatActivity {

    // instance variable
    private BilliardDBManager billiardDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_display);

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
        if (this.billiardDbManager != null) {
            this.billiardDbManager.closeDb();
        }
    }
}