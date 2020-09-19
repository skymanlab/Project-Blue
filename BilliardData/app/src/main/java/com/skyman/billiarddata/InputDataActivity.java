package com.skyman.billiarddata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.skyman.billiarddata.database.billiard.BilliardDbManager;
import com.skyman.billiarddata.listview.billiard.BilliardDataItem;
import com.skyman.billiarddata.listview.billiard.BilliardDataAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InputDataActivity extends AppCompatActivity {

    // helper manager 객체 선언
    BilliardDbManager billiardDbManager = null;

    // activity 에서 사용하는 객체 선언
    Spinner user;
    Spinner targetScore;
    Spinner speciality;
    TextView date;
    TextView reDate;
    TextView comment;
    EditText score_1;
    EditText score_2;
    EditText cost;
    EditText playTime;
    Button input;
    Button display;
    Button delete;
    ListView billiardDataList;

    // spinner selected item
    String userSelectedItem;
    String targetScoreSelectedItem;
    String specialitySelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        // BilliardDbManager class : helper manager setting
        billiardDbManager = new BilliardDbManager(this);
        billiardDbManager.init_tables();

        // text view : TextView setting
        date = (TextView) findViewById(R.id.inputdate_date);
        comment = (TextView) findViewById(R.id.inputdata_comment);

        // edit text : EditText setting
        score_1 = (EditText) findViewById(R.id.inputdata_score_1);
        score_2 = (EditText) findViewById(R.id.inputdata_score_2);
        cost = (EditText) findViewById(R.id.inputdata_cost);
        playTime = (EditText) findViewById(R.id.inputdata_play_time);

        // spinner : user spinner setting
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

        // spinner : target score spinner setting
        targetScore = (Spinner) findViewById(R.id.inputdata_target_score);
        ArrayAdapter targetScoreAdapter = ArrayAdapter.createFromResource(this, R.array.targetScore, android.R.layout.simple_spinner_dropdown_item);
        targetScore.setAdapter(targetScoreAdapter);

        // spinner : speciality spinner setting
        speciality = (Spinner) findViewById(R.id.inputdata_speciality);
        ArrayAdapter specialityAdapter = ArrayAdapter.createFromResource(this, R.array.speciality, android.R.layout.simple_spinner_dropdown_item);
        speciality.setAdapter(specialityAdapter);
        speciality.setSelection(2);

        // list view : inputData list view setting
        billiardDataList = (ListView) findViewById(R.id.inputdata_dataList);

        // button : input button setting
        input = (Button) findViewById(R.id.inputdata_input);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check : billiardDbManager가 생성 되었습니까?
                if (billiardDbManager != null) {
                    billiardDbManager.save_content(
                            date.getText().toString(),                                                                                      // 1. date
                            targetScore.getSelectedItem().toString(),                                                                       // 2. target score
                            speciality.getSelectedItem().toString(),                                                                        // 3. speciality
                            playTime.getText().toString(),                                                                                  // 4. play time
                            user.getSelectedItem().toString(),                                                                              // 5. victoree
                            BilliardDataManager.setFormatToScore(score_1.getText().toString(), score_2.getText().toString()),               // 6. score
                            cost.getText().toString()                                                                                       // 7. cost
                    );
                } else {

                }
            }
        });

        // button : display button setting
        display = (Button) findViewById(R.id.inputdate_display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check : billiardDbManager가 생성 되었습니까?
                if (billiardDbManager != null) {
                    setDataList(billiardDbManager.load_contents());
                } else {

                }
            }
        });

        // button : delete button setting
        delete = (Button) findViewById(R.id.inputdate_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check : billiardDbManager가 생성 되었습니까?
                if(billiardDbManager != null) {
                    billiardDbManager.delete_contents();
                } else {

                }
            }
        });

        // text view : TextView text setting, date format init
        TextView date = (TextView) findViewById(R.id.inputdate_date);
        setDateFormat();

        // text view : TextView onClick setting,
        reDate = (TextView) findViewById(R.id.inputdata_re_date);
        reDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateFormat();
            }
        });
    }

    // =============================================================================================================
    /* method : date set, 지금 현태의 날짜를 특정 형태로 가져오기*/
    private void setDateFormat(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
        date.setText(format.format(new Date()));
    }

    /* method : list view adapter, 받아온 내용을 adapter를 통해 dataList 에 뿌려 준다.*/
    private void setDataList(ArrayList<BilliardDataItem> dataListItems) {

        // object create : list view adapter 객체 생성
        BilliardDataAdapter dataListAdapter = new BilliardDataAdapter();

        // cycle : Array List add, dataListAdapter 객체의 dataListItems 객체에 DB에서 읽어온 내용 넣기
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
        // set : set adapter, 읽어온 내용을 다 넣고, adapter를 dataList(ListView)에 연결 시키기
        billiardDataList.setAdapter(dataListAdapter);
    }


    // =============================================================================================================

    /* method : display, toast 메시지 형태로 화면 출력*/
    private void toastHandler(String content) {
        Toast myToast = Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT);
        myToast.show();
    }

    /* method : display, 가격 형태로 변환한 값을 toast 메시지 형태로 화면 출력*/
    private void displayCost() {
        // check : cost edit text 내용이 있습니까?
        if (!cost.getText().toString().equals("")) {
            toastHandler("포맷한 내용 : " + BilliardDataManager.setFormatToCost(cost.getText().toString()));
        } else {
            toastHandler("데이터를 입력해주세요.");
        }
    }

    /* method : display, 게임 시간 형태로 변환한 값을 toast 메시지 형태로 화면 출력*/
    private void displayPlayTime() {
        // check : playtime edit text 내용이 있습니까?
        if (!playTime.getText().toString().equals("")) {
            toastHandler("포맷한 내용 : " + BilliardDataManager.setFormatToPlayTime(playTime.getText().toString()));
        } else {
            toastHandler("데이터를 입력해주세요.");
        }
    }
}