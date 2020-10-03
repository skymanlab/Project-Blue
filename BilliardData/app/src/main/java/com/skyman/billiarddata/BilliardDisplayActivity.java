package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.listview.BilliardLvManager;

import java.util.ArrayList;

public class BilliardDisplayActivity extends AppCompatActivity {

    // variable : helper manager 객체 선언
    private BilliardDbManager billiardDbManager = null;

    // variable : activity 에서 사용하는 객체 선언
    private ListView allBilliardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_display);

        // ListView : allBilliardData setting
        allBilliardData = (ListView) findViewById(R.id.billiard_display_lv_all_billiard_data);

        // BilliardDbManager : billiardDbManager setting - SQLiteOpenHelper 를 관리하는 클래스
        billiardDbManager = new BilliardDbManager(this);
        billiardDbManager.init_db();

        // BilliardDbManager : load_contents method - 저장 되어 있는 billiard 데이터를 billiardDbManager 를 통해 가져온다.
        ArrayList<BilliardData> billiardDataArrayList = billiardDbManager.load_contents();

        // check : billiard 테이블에서 가져온 내용이 있다.
        if(billiardDataArrayList.size() != 0) {
            // BilliardLvManager : allBilliardData setting - 위에서 가져온 데이터를 allBilliardData 에 뿌려준다.
            BilliardLvManager billiardLvManager = new BilliardLvManager(allBilliardData);
            billiardLvManager.setListViewToAllBilliardData(billiardDataArrayList);
        } else {
            DeveloperManager.displayLog("[Ac] BilliardDisplayActivity", "[billiardDataArrayList] billiardDataArrayList 의 size 가 0 입니다.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        billiardDbManager.closeBilliardDbHelper();
    }
}