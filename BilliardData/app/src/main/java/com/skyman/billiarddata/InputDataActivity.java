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

import com.skyman.billiarddata.management.billiard.data.BilliardDataFormatter;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.listview.BilliardLvManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputDataActivity extends AppCompatActivity {

    // value : helper manager 객체 선언
    BilliardDbManager billiardDbManager = null;

    // value : activity 에서 사용하는 객체 선언
    Spinner player;
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
    ListView billiardLv;

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
        player = (Spinner) findViewById(R.id.inputdata_player);
        ArrayAdapter userAdapter = ArrayAdapter.createFromResource(this, R.array.user, android.R.layout.simple_spinner_dropdown_item);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player.setAdapter(userAdapter);
        player.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        // list view : inputData list view setting
        billiardLv = (ListView) findViewById(R.id.inputdata_billiard_data);

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
                                    player.getSelectedItem().toString(),                                                                            // 5. winner
                                    BilliardDataFormatter.setFormatToScore(score_1.getText().toString(), score_2.getText().toString()),             // 6. score
                                    cost.getText().toString()                                                                                       // 7. cost
                    );
//                    save_content_Builder();
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
                    // object create : billiard data manager 를 통해 load
                    BilliardLvManager billiardLvManager = new BilliardLvManager(billiardLv);

                    // load : billiardDbManager 의 load_contents 메소드를 통해 받은 데이터를 이용하여 ListView 에 뿌려준다.
                    billiardLvManager.setListViewToBilliardData(billiardDbManager.load_contents());
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
                if (billiardDbManager != null) {
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
                // refresh date
                setDateFormat();
            }
        });
    }

    // =============================================================================================================
    /* method : date set, 지금 현태의 날짜를 특정 형태로 가져오기*/
    private void setDateFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
        date.setText(format.format(new Date()));
    }

    /* method : display, toast 메시지 형태로 화면 출력*/
    private void toastHandler(String content) {
        Toast myToast = Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT);
        myToast.show();
    }

    /* method : display, 가격 형태로 변환한 값을 toast 메시지 형태로 화면 출력*/
    private void displayCost() {
        // check : cost edit text 내용이 있습니까?
        if (!cost.getText().toString().equals("")) {
            toastHandler("포맷한 내용 : " + BilliardDataFormatter.setFormatToCost(cost.getText().toString()));
        } else {
            toastHandler("데이터를 입력해주세요.");
        }
    }

    /* method : display, 게임 시간 형태로 변환한 값을 toast 메시지 형태로 화면 출력*/
    private void displayPlayTime() {
        // check : playtime edit text 내용이 있습니까?
        if (!playTime.getText().toString().equals("")) {
            toastHandler("포맷한 내용 : " + BilliardDataFormatter.setFormatToPlayTime(playTime.getText().toString()));
        } else {
            toastHandler("데이터를 입력해주세요.");
        }
    }

   /* *//* method : insert, BilliardData 객체를 만들어 테이블에 추가하기*//*
    private void save_content_Builder(){
        if (!date.getText().toString().equals("")                                                  // 1. date
                && !targetScore.getSelectedItem().toString().equals("")                  // 2. target score
                && !speciality.getSelectedItem().toString().equals("")                                     // 3. speciality
                && !playTime.getText().toString().equals("")                     // 4. playtime
                && !player.getSelectedItem().toString().equals("")                                         // 5. winner
                && !BilliardDataFormatter.setFormatToScore(score_1.getText().toString(), score_2.getText().toString()).equals("")                                          // 6. score
                && !cost.getText().toString().equals("")) {                      // 7. cost
            toastHandler("빈 곳 없음");

            billiardDbManager.save_content(
                    new BilliardData.DataBuilder()
                            .setDate(date.getText().toString())
                            .setTargetScore(Integer.parseInt(targetScore.getSelectedItem().toString()))
                            .setSpeciality(speciality.getSelectedItem().toString())
                            .setPlayTime(Integer.parseInt(playTime.getText().toString()))
                            .setWinner(player.getSelectedItem().toString())
                            .setScore(BilliardDataFormatter.setFormatToScore(score_1.getText().toString(), score_2.getText().toString()))
                            .setCost(Integer.parseInt(cost.getText().toString())).builder()
            );
        } else {
            toastHandler("빈 곳  채워주세요.");
        }
    }*/
}