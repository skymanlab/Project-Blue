package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.listview.BilliardLvManager;

import java.util.ArrayList;

public class BilliardDisplayActivity extends AppCompatActivity {

    // value : helper manager 객체 선언
    private BilliardDbManager billiardDbManager = null;

    // value : activity 에서 사용하는 객체 선언
    private ListView allBilliardLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_display);

        // allBilliardData setting
        allBilliardLv = (ListView) findViewById(R.id.viewdata_all_billiard_data);

        // BilliardDbManager class : helper manager setting
        billiardDbManager = new BilliardDbManager(this);
        billiardDbManager.init_tables();

        ArrayList<BilliardData> billiardDataArrayList = billiardDbManager.load_contents();

        if(billiardDataArrayList.size() != 0) {
            // billiard data manager setting : ListView 화면 뿌려주기
            BilliardLvManager billiardLvManager = new BilliardLvManager(allBilliardLv);
            billiardLvManager.setListViewToBilliardData(billiardDataArrayList);
        } else {
            Toast.makeText(this, "notting", Toast.LENGTH_SHORT).show();
        }


    }
}