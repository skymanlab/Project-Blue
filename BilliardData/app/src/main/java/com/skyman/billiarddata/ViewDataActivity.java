package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.skyman.billiarddata.database.billiard.BilliardDatabase;
import com.skyman.billiarddata.database.billiard.BilliardHelper;
import com.skyman.billiarddata.listview.DataListAdapter;
import com.skyman.billiarddata.listview.DataListItem;

import java.util.ArrayList;

public class ViewDataActivity extends AppCompatActivity {

    //  Billiard DB Helper 객체 선언
    private BilliardHelper billiardHelper = null;

    // activity 에서 사용하는 객체 선언
    private ListView allBilliardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        // allBilliardData setting
        allBilliardData = (ListView) findViewById(R.id.viewdata_all_billiard_data);

        // DB 초기화
        init_tables();

        // List view set adapter
        load_contents();

    }

    // DB 1. 초기화
    private void init_tables() {
        /*
         * =====================================================
         * billiardHelper 셋팅
         * 데이터 베이스 : billiard.db
         * 테이블 : billiardBasic
         * 생성 됨
         * =====================================================
         * */
        billiardHelper = new BilliardHelper(this);
    }

    // DB 3. select
    private void load_contents() {
        /*
         * =====================================================
         * billiardHelper를 통해 읽기용(Readable)으로
         * SQLiteDatabase 객체 생성
         *
         * 그리고,
         * billiard.db 의 billiardBasic 테이블의 내용 읽어오기
         * =====================================================
         * */

        // 읽기용 객체 생성
        SQLiteDatabase readDb = billiardHelper.getReadableDatabase();

        // 해당 쿼리문으로 읽어온 테이블 내용을 Cursor 객체를 통해 읽을 수 있도록 하기
        Cursor cursor = readDb.rawQuery(BilliardDatabase.SQL_SELECT_TABLE_ALL_CONTENT, null);

        // DataListItem 객체를 담을 ArrayList 객체 생성 - 한 행을 DataListItem 객체에 담는다.
        ArrayList<DataListItem> dataListItems = new ArrayList<>();

        // Cursor 객체를 통해 하나씩 읽어온다.
        while (cursor.moveToNext()) {
            long id = cursor.getInt(0);
            String date = cursor.getString(1);
            String targetScore = cursor.getString(2);
            String speciality = cursor.getString(3);
            String palyTime = cursor.getString(4);
            String victoree = cursor.getString(5);
            String score = cursor.getString(6);
            String cost = cursor.getString(7);

            Log.d("select", "읽어오는 내용");
            Log.d("select", "id : " + id);
            Log.d("select", "date : " + date);
            Log.d("select", "victoree : " + victoree);
            Log.d("select", "score : " + score);
            Log.d("select", "cost : " + cost);
            Log.d("select", "paly_time : " + palyTime);

            // 읽어온 dataListItem 을 items에 추가하기
            DataListItem dataListItem = new DataListItem();
            dataListItem.setId(id);
            dataListItem.setDate(date);
            dataListItem.setTarget_score(targetScore);
            dataListItem.setSpeciality(speciality);
            dataListItem.setPaly_time(palyTime);
            dataListItem.setVictoree(victoree);
            dataListItem.setScore(score);
            dataListItem.setCost(cost);

            // array list 에 insert
            dataListItems.add(dataListItem);
        }

        // 읽어온 내용이 들어있는 dataListItems를 DataListAdapter를 이용하여 ListView에 그리기
        setDataList(dataListItems);
    }

    // list view adapter
    private void setDataList(ArrayList<DataListItem> dataListItems) {

        // list view adapter 객체 생성
        DataListAdapter dataListAdapter = new DataListAdapter();

        // dataListAdapter 객체의 dataListItems 객체에 DB에서 읽어온 내용 넣기
        for (int position = 0; position < dataListItems.size(); position++) {
            dataListAdapter.addItem(
                    dataListItems.get(position).getId(),                // id
                    dataListItems.get(position).getDate(),              // date
                    dataListItems.get(position).getTarget_score(),      // target score
                    dataListItems.get(position).getSpeciality(),        // speciality
                    dataListItems.get(position).getPaly_time(),         // play time
                    dataListItems.get(position).getVictoree(),          // victoree
                    dataListItems.get(position).getScore(),             // score
                    dataListItems.get(position).getCost()               // cost
            );
        }

        // 읽어온 내용을 다 넣고, adapter를 dataList(ListView)에 연결 시키기
        allBilliardData.setAdapter(dataListAdapter);
    }
}