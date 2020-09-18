package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.billiarddata.database.billiard.BilliardDataManager;
import com.skyman.billiarddata.database.billiard.BilliardDatabase;
import com.skyman.billiarddata.database.billiard.BilliardHelper;
import com.skyman.billiarddata.listview.DataListAdapter;
import com.skyman.billiarddata.listview.DataListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InputDataActivity extends AppCompatActivity {

    // helper 객체 선언
    BilliardHelper billiardHelper = null;

    // activity 에서 사용하는 객체 선언
    Spinner user;
    Spinner targetScore;
    Spinner speciality;
    TextView date;
    EditText score_1;
    EditText score_2;
    EditText cost;
    EditText playtime;
    Button input;
    Button display;
    Button delete;
    ListView dataList;
    TextView coment;

    // spinner selected item
    String userSelectedItem;
    String targetScoreSelectedItem;
    String specialitySelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        // TextView setting
        date = (TextView) findViewById(R.id.inputdate_date);
        coment = (TextView) findViewById(R.id.inputdata_coment);

        // EditText setting
        score_1 = (EditText) findViewById(R.id.inputdata_score_1);
        score_2 = (EditText) findViewById(R.id.inputdata_score_2);
        cost = (EditText) findViewById(R.id.inputdata_cost);
        playtime = (EditText) findViewById(R.id.inputdata_play_time);

        // user spinner setting
        user = (Spinner) findViewById(R.id.inputdata_user);
        ArrayAdapter userAdapter = ArrayAdapter.createFromResource(this, R.array.user, android.R.layout.simple_spinner_dropdown_item);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user.setAdapter(userAdapter);
        user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // target score spinner setting
        targetScore = (Spinner) findViewById(R.id.inputdata_target_score);
        ArrayAdapter targetScoreAdapter = ArrayAdapter.createFromResource(this, R.array.targetScore, android.R.layout.simple_spinner_dropdown_item);
        targetScore.setAdapter(targetScoreAdapter);

        // speciality spinner setting
        speciality = (Spinner) findViewById(R.id.inputdata_speciality);
        ArrayAdapter specialityAdapter = ArrayAdapter.createFromResource(this, R.array.speciality, android.R.layout.simple_spinner_dropdown_item);
        speciality.setAdapter(specialityAdapter);
        speciality.setSelection(2);

        // inputData list view setting ( ListView )
        dataList = (ListView) findViewById(R.id.inputdata_dataList);

        // input button setting
        input = (Button) findViewById(R.id.inputdata_input);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 해당 내용으로 billiard.db 의 billiardBasic 테이블에 insert
                if (billiardHelper != null) {
                    save_content();
                } else {

                }
            }
        });

        // display button setting
        display = (Button) findViewById(R.id.inputdate_display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (billiardHelper != null) {
                    load_contents();

//                    displayPlayTime();
                } else {

                }
            }
        });

        // delete button setting
        delete = (Button) findViewById(R.id.inputdate_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (billiardHelper != null) {
                    // 모든 데이터 삭제
                    delete_contents();
                    // 삭제 완료 메시지
                    toastHandler("모든 데이터를 삭제하였습니다.");
                    // 다시 보여주기
                    load_contents();
                } else {

                }
            }
        });

        // date init
        TextView date = (TextView) findViewById(R.id.inputdate_date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
        date.setText(format.format(new Date()));

        // =============================================================================================================

        // BilliardHelper 객체 init
        init_tables();

        // 모든 데이터 로드
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

    // DB 2. insert
    private void save_content() {
        /*
         * =====================================================
         * billiardHelper를 통해 쓰기용(Writable)으로
         * SQLiteDatabase 객체 생성
         *
         * 그리고,
         * billiard.db 의 billiardBasic 테이블의 내용 읽어오기
         * =====================================================
         * */

        //  쓰기로 객체 생성
        SQLiteDatabase writeDb = billiardHelper.getWritableDatabase();

        // get value
        String dateContent = date.getText().toString();
        String targetScoreContent = targetScore.getSelectedItem().toString();
        String specialityContent = speciality.getSelectedItem().toString();
        String victoreeContent = user.getSelectedItem().toString();
        String scoreContent = BilliardDataManager.setFormatToScore(score_1.getText().toString(), score_2.getText().toString());
        String costContent = cost.getText().toString();
        String palyTimeContent = playtime.getText().toString();

        // 빈 곳 없나 검사
        if (!dateContent.equals("")                             // date
                && !targetScoreContent.equals("")               // target score
                && !specialityContent.equals("")                // speciality
                && !palyTimeContent.equals("")                  // playtime
                && !victoreeContent.equals("")                  // victoree
                && !scoreContent.equals("")                     // score
                && !costContent.equals("")) {                   // cost

            // 입력할 내용 setting
            ContentValues values = new ContentValues();
            values.put(BilliardDatabase.Entry.COLUMN_NAME_DATE, dateContent);
            values.put(BilliardDatabase.Entry.COLUMN_NAME_TARGET_SCORE, targetScoreContent);
            values.put(BilliardDatabase.Entry.COLUMN_NAME_SPECIALITY, specialityContent);
            values.put(BilliardDatabase.Entry.COLUMN_NAME_PLAY_TIME, palyTimeContent);
            values.put(BilliardDatabase.Entry.COLUMN_NAME_VICTOREE, victoreeContent);
            values.put(BilliardDatabase.Entry.COLUMN_NAME_SCORE, scoreContent);
            values.put(BilliardDatabase.Entry.COLUMN_NAME_COST, costContent);

            // 해당 billiardBasic 테이블에 내용 insert
            // nullColumnHack 이 'null'로 지정 되었다면?       values 객체의 어떤 열에 값이 없으면 지금 내용을 insert 안함.
            //                이 '열 이름'이 지정 되었다면?    해당 열에 값이 없으면 null 값을 넣는다.
            long newRowId = writeDb.insert(BilliardDatabase.Entry.TABLE_NAME, null, values);

            // newRowId는 데이터베이스에 insert 가 실패하면 '-1'을 반환하고, 성공하면 해당 '행 번호'를 반환한다.
            if (newRowId == -1) {
                // 데이터 insert 실패
                toastHandler("데이터 입력에 실패했습니다.");
            } else {
                // 데이터 insert 성공
                toastHandler(newRowId + " 번째 데이터를 입력에 성공했습니다. ");
            }
        } else {
            toastHandler("빈곳을 채워주세요.");
        }

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

    // DB 4. delete 테이블 내용 전부 삭제
    public void delete_contents() {
        /*
         * =====================================================
         * billiardHelper를 통해 읽기용(Readable)으로
         * SQLiteDatabase 객체 생성
         *
         * 그리고,
         * billiard.db 의 billiardBasic 테이블의 전체 내용 삭제하기
         * =====================================================
         * */

        // 쓰기용 객체 생성
        SQLiteDatabase deleteDb = billiardHelper.getWritableDatabase();

        // all contents delete_query execute
        deleteDb.execSQL(BilliardDatabase.SQL_DELETE_TABLE_ALL_CONTENT);
    }


    // =============================================================================================================

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
        dataList.setAdapter(dataListAdapter);
    }


    // =============================================================================================================

    // toast 메시지
    private void toastHandler(String content) {
        Toast myToast = Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT);
        myToast.show();
    }

    // 가격 형태 변환한 값을 toast 메시지로 출력
    private void displayCost() {
        if (!playtime.getText().toString().equals("")) {
            toastHandler("포맷한 내용 : " + BilliardDataManager.setFormatToCost(playtime.getText().toString()));
        } else {
            toastHandler("데이터를 입력해주세요.");
        }
    }

    // 게임 시간 형태 변환한 값을 toast 메시지로 출력
    private void displayPlayTime() {
        if (!playtime.getText().toString().equals("")) {
            toastHandler("포맷한 내용 : " + BilliardDataManager.setFormatToPlayTime(playtime.getText().toString()));
        } else {
            toastHandler("데이터를 입력해주세요.");
        }
    }
}