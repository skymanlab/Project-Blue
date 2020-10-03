package com.skyman.billiarddata;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.dialog.DateModify;
import com.skyman.billiarddata.management.billiard.data.BilliardDataFormatter;
import com.skyman.billiarddata.management.billiard.database.BilliardDBManagerT;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.billiard.listview.BilliardLvManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BilliardInputActivity extends AppCompatActivity {

    // variable : helper manager 관련 객체 선언
    private BilliardDbManager billiardDbManager = null;
    private UserDbManager userDbManager = null;
    private UserData userData;
    private FriendDbManager friendDbManager = null;
    private FriendData friendData;

    // variable : activity 에서 사용하는 객체 선언
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

        // Intent : Intent 에 의해 전달된 친구에 대한 정보를 friendData 에 입력
        Intent intent = getIntent();
        friendData = (FriendData) intent.getSerializableExtra("player");
        DeveloperManager.displayToFriendData("[Ac] BilliardInputActivity", friendData);

        // BilliardDbManager : billiardDbManager setting - SQLiteOpenHelper 를 관리하는 클래스
        billiardDbManager = new BilliardDbManager(this);
        billiardDbManager.init_db();

        // UserDbManager : userDbManager setting - SQLiteOpenHelper 를 관리하는 클래스
        userDbManager = new UserDbManager(this);
        userDbManager.init_db();

        // UserData : userDbManager 에서 받아온 데이터를 userData 에 넣는다.
        userData = userDbManager.load_content();
        DeveloperManager.displayToUserData("[Ac] BilliardInputActivity", userData);

        // FriendDbManager : friendDbManager 내용 업데이트를 위한
        friendDbManager = new FriendDbManager(this);
        friendDbManager.init_db();

        // TextView : date setting
        date = (TextView) findViewById(R.id.billiard_input_date);
        setDateFormat();

        // TextView : reDate setting - 날짜 새로 받기
        reDate = (TextView) findViewById(R.id.billiard_input_re_date);
        reDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        if (userData != null && friendData != null) {
            userAdapter.add(userData.getName());
            userAdapter.add(friendData.getName());
        } else {

        }
        player.setAdapter(userAdapter);

        // check :
        if (userData != null) {
            // Spinner : userData 저장 된 값이 있을 경우 - '수지', '주 종목'을 해당 값으로 변경
            targetScore.setSelection(userData.getTargetScore() - 1);
            speciality.setSelection(getSelectedPositionToUserData(userData.getSpeciality()));
        } else {
            DeveloperManager.displayLog("[Ac] BilliardInputActivity", "[userData] userData 가 없으면 '수지'와 '종목'은 기본 값입니다.");
        }

        // ListView  : inputData setting - billiardDbManager 를 통해 가져온 내용을 billiardLvManger 로 화면에 뿌려줄 준비
        allBilliardData = (ListView) findViewById(R.id.billiard_input_lv_all_billiard_data);

        // Button 1 : input setting - '데이터 입력' 버튼
        input = (Button) findViewById(R.id.billiard_input_bt_input);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check : 모든 값을 입력 받지 않으면
                if (!checkAllInputData()) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                // check : billiardDbManager 가 초기화가 되었다.
//                if (billiardDbManager.isInitDb()) {
//                    // BilliardDbManager : 입력 정보를 billiard 테이블에 저장
//                    billiardDbManager.save_content(
//                            date.getText().toString(),                                                                                      // 1. date
//                            targetScore.getSelectedItem().toString(),                                                                       // 2. target score
//                            speciality.getSelectedItem().toString(),                                                                        // 3. speciality
//                            playTime.getText().toString(),                                                                                  // 4. play time
//                            player.getSelectedItem().toString(),                                                                            // 5. winner
//                            BilliardDataFormatter.setFormatToScore(score_1.getText().toString(), score_2.getText().toString()),             // 6. score
//                            cost.getText().toString()                                                                                       // 7. cost
//                    );
//                } else {
//                    DeveloperManager.displayLog("[Ac] BilliardInputActivity", "[input button] billiardDbManager 가 초기화되지 않았습니다. initDb 값을 확인하세요.");
//                }

                BilliardDBManagerT billiardDBManagerT = new BilliardDBManagerT(getApplicationContext());

                billiardDBManagerT.initDb();

                billiardDBManagerT.saveContent(
                        date.getText().toString(),
                        Integer.parseInt(targetScore.getSelectedItem().toString()),
                        speciality.getSelectedItem().toString(),
                        Integer.parseInt(playTime.getText().toString()),
                        player.getSelectedItem().toString(),
                        BilliardDataFormatter.setFormatToScore(score_1.getText().toString(), score_2.getText().toString()),
                        Integer.parseInt(cost.getText().toString())
                );
                DeveloperManager.displayLog("[Ac]_BilliardInputActivity", "[input button] 입력 완료");


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
                    DeveloperManager.displayToUserData("[Ac] BilliardInputActivity", userData.getId(), gameRecordWin, gameRecordLoss, recentGamePlayerId, recentPlayDate, totalPlayTime, totalCost);

                    // UserDbManager : 입력 정보를 user 테이블 해당 id 의 유저의 데이터를 갱신한다.
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
                    DeveloperManager.displayLog("[Ac] BilliardInputActivity", "[input button] userDbManager 가 초기화되지 않았습니다. initDb 값을 확인하세요.");
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

                    DeveloperManager.displayToFriendData("[Ac] BilliardInputActivity", friendData.getId(), gameRecordWin, gameRecordLoss, recentPlayDate, totalPlayTime, totalCost);

                    // FriendDbManager : 입력 정보를 friend 테이블에서 해당 id 의 친구의 데이터를 갱신한다.
                    friendDbManager.update_content(
                            friendData.getId(),
                            gameRecordWin,
                            gameRecordLoss,
                            recentPlayDate,
                            totalPlayTime,
                            totalCost
                    );
                } else {
                    DeveloperManager.displayLog("[Ac] BilliardInputActivity", "[input button] friendDbManager 가 초기화되지 않았습니다. initDb 값을 확인하세요.");
                }
                showAlertToInputData();
            }
        });

        // Button 2 : display setting - '데이터 보기' 버튼
        display = (Button) findViewById(R.id.billiard_input_bt_display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check : billiardDbManager 가 초기화가 되었다.
                if (billiardDbManager.isInitDb()) {
                    // object create : billiard data manager 를 통해 load
                    BilliardLvManager billiardLvManager = new BilliardLvManager(allBilliardData);

                    // load : billiardDbManager 의 load_contents 메소드를 통해 받은 데이터를 이용하여 ListView 에 뿌려준다.
                    billiardLvManager.setListViewToAllBilliardData(billiardDbManager.load_contents());
                } else {
                    DeveloperManager.displayLog("[Ac] BilliardInputActivity", "[display button] billiardDbManager 가 초기화되지 않았습니다. initDb 값을 확인하세요.");
                }
            }
        });

        // button 3 : delete setting - '데이터 삭제' 버튼
        delete = (Button) findViewById(R.id.billiard_input_bt_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check : billiardDbManager 가 초기화가 되었다.
                if (billiardDbManager.isInitDb()) {
                    billiardDbManager.delete_contents();
                } else {
                    DeveloperManager.displayLog("[Ac] BilliardInputActivity", "[delete button] billiardDbManager 가 초기화되지 않았습니다. initDb 값을 확인하세요.");
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
        friendDbManager.closeFriendDbHelper();
        userDbManager.closeUserDbHelper();
    }



    /*                                      private method
     *   ============================================================================================
     *  */

    /* method : display, toast 메시지 형태로 화면 출력*/
    private void toastHandler(String content) {
        Toast myToast = Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT);
        myToast.show();
    }

    /* method : date set, 지금 현태의 날짜를 특정 형태로 가져오기*/
    private void setDateFormat() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        date.setText(format.format(new Date()));
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

    /* method : 모든 값을 입력 받았나요?*/
    private boolean checkAllInputData() {
        if (!score_1.getText().toString().equals("") &&
                !score_2.getText().toString().equals("") &&
                !playTime.getText().toString().equals("") &&
                !cost.getText().toString().equals("")
        ) {
            // 모두 입력 받았으면
            DeveloperManager.displayLog("[Ac] BilliardInputActivity", "[checkAllInputData] 모든 값을 입력 받았습니다.");
            return true;
        } else {
            // 하나라도 입력 안 받았으면
            DeveloperManager.displayLog("[Ac] BilliardInputActivity", "[checkAllInputData] 모든 값을 입력해주세요!");
            return false;
        }
    }

    /* method : alertDialog */
    private void showAlertToInputData() {
        // AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ad_billiard_input_complete_input_title)
                .setMessage(R.string.ad_billiard_input_complete_input_message)
                .setPositiveButton(R.string.ad_billiard_input_bt_complete_input_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .show();
    }
    
    /**
     * [method] player spinner 의 선택된   UserData 와 FriendData 의 name 과 비교하여
     *
     */
}