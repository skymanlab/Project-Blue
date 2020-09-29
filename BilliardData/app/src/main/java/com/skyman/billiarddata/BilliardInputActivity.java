package com.skyman.billiarddata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.dialog.DateModify;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.data.BilliardDataFormatter;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.listview.BilliardLvManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BilliardInputActivity extends AppCompatActivity {

    // value : helper manager 관련 객체 선언
    private BilliardDbManager billiardDbManager = null;
    private UserDbManager userDbManager = null;
    private UserData userData;
    private FriendDbManager friendDbManager = null;
    private FriendData friendData;

    // value : activity 에서 사용하는 객체 선언
    private Spinner player;
    private Spinner targetScore;
    private Spinner speciality;
    private TextView date;
    private TextView reDate;
    private TextView comment;
    private EditText score_1;
    private EditText score_2;
    private EditText cost;
    private EditText playTime;
    private Button input;
    private Button display;
    private Button delete;
    private ListView allBilliardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_input);

        // BilliardDbManager : billiardDbManager setting - SQLiteOpenHelper 를 관리하는 클래스
        billiardDbManager = new BilliardDbManager(this);
        billiardDbManager.init_db();

        // UserDbManager : userDbManager setting - SQLiteOpenHelper 를 관리하는 클래스
        userDbManager = new UserDbManager(this);
        userDbManager.init_db();

        // FriendDbManager : friendDbManager 내용 업데이트를 위한
        friendDbManager = new FriendDbManager(this);
        friendDbManager.init_db();

        // UserData : userDbManager 에서 받아온 데이터를 userData 에 넣는다.
        userData = userDbManager.load_content();
        DeveloperManager.displayToUserData("BilliardInputActivity", userData);

        // Intent : Intent 에 의해 전달될  player 에 대한 정보를 friendData 에 입력
        Intent intent = getIntent();
        friendData = (FriendData) intent.getSerializableExtra("player");

        DeveloperManager.displayLog("BilliardInputActivity", "받아온 player : " + friendData);
        DeveloperManager.displayToFriendData("BilliardInputActivity", friendData);

        // TextView : date setting
        date = (TextView) findViewById(R.id.billiard_input_date);
        setDateFormat();

        // TextView : reDate setting - 날짜 새로 받기
        reDate = (TextView) findViewById(R.id.billiard_input_re_date);
        reDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // refresh date
//                setDateFormat();

                // DateModify : date 를 수정하기 위한 dialog 를 생성한다.
                DateModify dateModify = new DateModify(BilliardInputActivity.this);

                // DateModify : dialog 를 셋팅하고 노출한다. 그리고 dialog 를 보여주는 custom 된 activity 의 widget 을 셋팅한다. 그리고 그 수정한 date 를 받아와서 'date' 를 수정한 값으로 셋팅한다.
                dateModify.setDialog(date);
            }
        });

        // TextView : comment setting - 상태 확인을 위한 TextView
        comment = (TextView) findViewById(R.id.billiard_input_comment);

        // EditText : score, cost, playtime setting
        score_1 = (EditText) findViewById(R.id.billiard_input_score_1);
        score_2 = (EditText) findViewById(R.id.billiard_input_score_2);
        cost = (EditText) findViewById(R.id.billiard_input_cost);
        playTime = (EditText) findViewById(R.id.billiard_input_play_time);

        // Spinner : target score setting - targetScoreAdapter 를 이용하여 R.array.targetScore 를 연결, 수지
        targetScore = (Spinner) findViewById(R.id.billiard_input_sp_target_score);
        ArrayAdapter targetScoreAdapter = ArrayAdapter.createFromResource(this, R.array.targetScore, android.R.layout.simple_spinner_dropdown_item);
        targetScore.setAdapter(targetScoreAdapter);

        // Spinner : speciality setting - specialityAdapter 를 이용하여 R.array.speciality 를 연결, 주 종목
        speciality = (Spinner) findViewById(R.id.billiard_input_sp_speciality);
        ArrayAdapter specialityAdapter = ArrayAdapter.createFromResource(this, R.array.speciality, android.R.layout.simple_spinner_dropdown_item);
        speciality.setAdapter(specialityAdapter);

        // Spinner : player setting - userAdapter 를 이용하여 R.array.player 를 연결, 나와 친구목록에 있는 선수들
        player = (Spinner) findViewById(R.id.billiard_input_sp_player);
        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        userAdapter.add(userData.getName());
        userAdapter.add(friendData.getName());
        player.setAdapter(userAdapter);

        if (userData != null) {
            // Spinner : userData 저장 된 값이 있을 경우 - '수지', '주 종목'을 해당 값으로 변경
            targetScore.setSelection(userData.getTargetScore() - 1);
            speciality.setSelection(getSelectedPositionToUserData(userData.getSpeciality()));
        }

        // ListView  : inputData setting - billiardDbManager 를 통해 가져온 내용을 billiardLvManger 로 화면에 뿌려줄 준비
        allBilliardData = (ListView) findViewById(R.id.billiard_input_lv_all_billiard_data);

        // Button 1 : input setting - '데이터 입력' 버튼
        input = (Button) findViewById(R.id.billiard_input_bt_input);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkAllInputData())
                    return;

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
                } else {
                    DeveloperManager.displayLog("BilliardInputActivity", "billiardDbManager 가 생성되지 않아 저장을 못 합니다." );
                }

                // check : userDbManager 가 생성 되었음?
                if (userDbManager != null) {
                    int gameRecordWin = 0;
                    int gameRecordLoss = 0;

                    // check : 승자가 나일때,
                    if (player.getSelectedItem().equals(userData.getName())) {
                        gameRecordWin = userData.getGameRecordWin() + 1;
                        gameRecordLoss = userData.getGameRecordLoss();
                    } else {
                        // 승자가 친구 일때,
                        gameRecordWin = userData.getGameRecordWin();
                        gameRecordLoss = userData.getGameRecordLoss() + 1;
                    }

                    // 최근 게임 플레이어 아이디
                    long recentGamePlayerId = friendData.getId();

                    // 최근 게임 날짜
                    String recentPlayDate = date.getText().toString();

                    // 총 게임 시간
                    int totalPlayTime = userData.getTotalPlayTime() + Integer.parseInt(playTime.getText().toString());

                    // 총 비용
                    int totalCost = userData.getTotalCost() + Integer.parseInt(cost.getText().toString());
                    DeveloperManager.displayLog("BilliardInputActivity", "userData 입력 처리 / id : " + userData.getId());
                    DeveloperManager.displayLog("BilliardInputActivity", "userData 입력 처리 / gameRecordWin : " + gameRecordWin);
                    DeveloperManager.displayLog("BilliardInputActivity", "userData 입력 처리 / gameRecordLoss : " + gameRecordLoss);
                    DeveloperManager.displayLog("BilliardInputActivity", "userData 입력 처리 / recentGamePlayerId : " + recentGamePlayerId);
                    DeveloperManager.displayLog("BilliardInputActivity", "userData 입력 처리 / recentPlayDate : " + recentPlayDate);
                    DeveloperManager.displayLog("BilliardInputActivity", "userData 입력 처리 / totalPlayTime : " + totalPlayTime);
                    DeveloperManager.displayLog("BilliardInputActivity", "userData 입력 처리 / totalCost : " + totalCost);

                    userDbManager.update_content(
                            userData.getId(),
                            gameRecordWin,
                            gameRecordLoss,
                            recentGamePlayerId,
                            recentPlayDate,
                            totalPlayTime,
                            totalCost
                    );
                } else {
                    DeveloperManager.displayLog("BilliardInputActivity", "userDbManager 가 생성되지 않아 저장을 못 합니다." );
                }

                // check : friendDbManager 가 생성 되었음?
                if (friendDbManager != null) {
                    int gameRecordWin = 0;
                    int gameRecordLoss = 0;

                    // check : 승자가 친구 일때,
                    if (player.getSelectedItem().equals(friendData.getName())) {
                        gameRecordWin = friendData.getGameRecordWin() + 1;
                        gameRecordLoss = friendData.getGameRecordLoss();
                    } else {
                        // 승자가 나 일때,
                        gameRecordWin = friendData.getGameRecordWin();
                        gameRecordLoss = friendData.getGameRecordLoss() + 1;
                    }

                    // 최근 경기 날짜
                    String recentPlayDate = date.getText().toString();

                    // 총 게임 시간
                    int totalPlayTime = friendData.getTotalPlayTime() + Integer.parseInt(playTime.getText().toString());

                    // 총 비용
                    int totalCost = friendData.getTotalCost() + Integer.parseInt(cost.getText().toString());

                    DeveloperManager.displayLog("BilliardInputActivity", "friendData 입력 처리 / id : " + friendData.getId());
                    DeveloperManager.displayLog("BilliardInputActivity", "friendData 입력 처리 / gameRecordWin : " + gameRecordWin);
                    DeveloperManager.displayLog("BilliardInputActivity", "friendData 입력 처리 / gameRecordLoss : " + gameRecordLoss);
                    DeveloperManager.displayLog("BilliardInputActivity", "friendData 입력 처리 / recentPlayDate : " + recentPlayDate);
                    DeveloperManager.displayLog("BilliardInputActivity", "friendData 입력 처리 / totalPlayTime : " + totalPlayTime);
                    DeveloperManager.displayLog("BilliardInputActivity", "friendData 입력 처리 / totalCost : " + totalCost);

                    friendDbManager.update_content(
                            friendData.getId(),
                            gameRecordWin,
                            gameRecordLoss,
                            recentPlayDate,
                            totalPlayTime,
                            totalCost
                    );
                } else {

                }
                showAlert();
            }
        });

        // Button 2 : display setting - '데이터 보기' 버튼
        display = (Button) findViewById(R.id.billiard_input_bt_display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check : billiardDbManager가 생성 되었습니까?
                if (billiardDbManager != null) {
                    // object create : billiard data manager 를 통해 load
                    BilliardLvManager billiardLvManager = new BilliardLvManager(allBilliardData);

                    // load : billiardDbManager 의 load_contents 메소드를 통해 받은 데이터를 이용하여 ListView 에 뿌려준다.
                    billiardLvManager.setListViewToAllBilliardData(billiardDbManager.load_contents());
                } else {
                    DeveloperManager.displayLog("BilliardInputActivity", "billiardDbManager 가 생성되지 않아 저장을 못 합니다." );
                }
            }
        });

        // button 3 : delete setting - '데이터 삭제' 버튼
        delete = (Button) findViewById(R.id.billiard_input_bt_delete);
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
    }

    /* method : 사용자 또는 시스템에 의한 액티비티 종료가 될 때 부르는 콜백함수 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // BilliardDbManager : billiardDbManager 가 사용하는 OpenDbHelper 를 종료.
        billiardDbManager.closeBilliardDbHelper();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    // =============================================================================================================
    /* method : date set, 지금 현태의 날짜를 특정 형태로 가져오기*/
    private void setDateFormat() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        date.setText(format.format(new Date()));
    }

    /* method : display, toast 메시지 형태로 화면 출력*/
    private void toastHandler(String content) {
        Toast myToast = Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT);
        myToast.show();
    }

    /* method: userData 의 getSpeciality 값으로 speciality spinner 의 선택 */
    private int getSelectedPositionToUserData(String speciality) {
        if (speciality.equals("3구")) {
            return 0;
        } else if (speciality.equals("4구")) {
            return 1;
        } else if (speciality.equals("포켓볼")) {
            return 2;
        } else {
            return 0;
        }
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

    /* method : alertDialog */
    private void showAlert(){
        // AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("다음 화면으로 넘어가겠습니다.")
                .setPositiveButton("다음 화면", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .show();
    }

    /* method : 모든 값을 입력 받았나요?*/
    private boolean checkAllInputData(){
        if(!score_1.getText().toString().equals("") &&
                !score_2.getText().toString().equals("") &&
                !playTime.getText().toString().equals("") &&
                !cost.getText().toString().equals("")
        ) {
            // 모두 입력 받았으면
            DeveloperManager.displayLog("BilliardInputActivity", "모든 값을 입력 받았습니다.");
            return true;
        } else {
            // 하나라도 입력 안 받았으면
            DeveloperManager.displayLog("BilliardInputActivity", "모든 값을 입력해주세요!");
            return false;
        }
    }
}