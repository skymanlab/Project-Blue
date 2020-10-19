package com.skyman.billiarddata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.dialog.DateModify;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.ProjectBlueDataFormatter;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;
import java.util.Date;

public class BilliardInputActivity extends AppCompatActivity {

    // constant
    private final String CLASS_NAME_LOG = "[Ac]_BilliardInputActivity";
    private final int DEFAULT_TARGET_SCORE = 21;

    // instant variable
    private BilliardDbManager billiardDbManager = null;
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private PlayerDbManager playerDbManager = null;
    private UserData userData = null;
    private ArrayList<FriendData> friendDataArrayList = null;
    private ArrayList<PlayerData> playerDataArrayList = null;
    private ArrayList<String> playerNameList = null;

    // instant variable
    private TextView playerName_1;
    private Spinner playerTargetScore_1;
    private EditText playerScore_1;

    private TextView playerName_2;
    private Spinner playerTargetScore_2;
    private EditText playerScore_2;

    private LinearLayout player3;
    private TextView playerName_3;
    private Spinner playerTargetScore_3;
    private EditText playerScore_3;

    private LinearLayout player4;
    private TextView playerName_4;
    private Spinner playerTargetScore_4;
    private EditText playerScore_4;

    private TextView date;
    private TextView reDate;

    private Spinner gameMode;

    private Spinner winnerName;
    private EditText playTime;
    private EditText cost;

    private Button input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_input);

        final String METHOD_NAME = "[onCreate] ";

        // [method]createDBManager : billiard, user, friend, player 테이블 메니저 생성
        createDBManager();

        // [lv/C]Intent : 전 Activity 에서 보낸 Intent 가져오기
        Intent intent = getIntent();

        // [iv/C]UserData : 세션에서 userData 를 가져온다. / "userData"
        this.userData = SessionManager.getUserDataInIntent(intent);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "intent 로 넘어온 userData 가 있어요.");

        // [iv/C]ArrayList<FriendData> : 세션에서 friendDataArrayList 를 가져온다. / "playerList"
        this.friendDataArrayList = SessionManager.getPlayerListInIntent(intent);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "intent 로 넘어온 friendDataArrayList 가 있어요.");

        // [method]mappingOfWidget : activity_billiard_input layout 의 widget 과 매핑한다.
        mappingOfWidget();

        // [iv/C]TextView : date 의 오늘 날짜를 특정 형태로 만들어서 보여주기
        this.date.setText(ProjectBlueDataFormatter.getFormatOfDate(new Date()));

        // [iv/C]TextView : reDate widget 의 클릭 이벤트를 셋팅한다.
        this.reDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setTextToDifferentDate : 다른 날짜로 변경할 수 있는 custom dialog 를 보여준다.
                setTextToDifferentDate();

            }
        });

        // [iv/C]Button : input click listener
        this.input = (Button) findViewById(R.id.billiard_input_bt_input);
        this.input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]showDialogToCheckWhetherSave : 입력 받은 값으로 billiardData 를 저장할 건지 dialog 띄워 물어본 뒤 진행한다.
                showDialogToCheckWhetherSave();

            }
        });

        // [check 1] : userData, friendDataArrayList 의 내용이 있다.
        if ((this.userData != null) && (friendDataArrayList.size() > 0)) {

            // [iv/C]ArrayList<PlayerData> : player 를 담기 위한 객체 생성
            this.playerDataArrayList = new ArrayList<>();

            // [iv/C]ArrayList<String> : playerName 을 담기 위한 객체 생성
            this.playerNameList = new ArrayList<>();

            // [method] : player 의 수에 따라 화면에 보여주기
            setPlayerList(this.userData, this.friendDataArrayList);

            DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, this.playerDataArrayList);

            for (int index = 0; index < this.playerNameList.size(); index++) {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 name : " + this.playerNameList.get(index));
            }

            // [method]setSpinnerWidgetOfWinnerNameAndGameMode : winnerName, gameMode spinner 의 초기값 설정
            setSpinnerWidgetOfWinnerNameAndGameMode(this.playerNameList);


        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "셋팅할 userData 와 friendDataArrayList 가 없습니다.");
        } // [check 1]


    } // End of method [onCreate]


    @Override
    protected void onDestroy() {
        super.onDestroy();

        final String METHOD_NAME = "[onDestroy] ";

        // [check 1] : billiardDbManager 가 생성되었을 때만 closeDb method 실행
        if (this.billiardDbManager != null) {
            this.billiardDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 메니저가 생성되지 않았습니다.");
        } // [check 1]

        // [check 2] : userDbManager 가 생성되었을 때만 closeDB method 실행
        if (this.userDbManager != null) {
            this.userDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user 메니저가 생성되지 않았습니다.");
        } // [check 2]

        // [check 3] : friendDbManager 가 생성되었을 때만 closeDB method 실행
        if (this.friendDbManager != null) {
            this.friendDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "friend 메니저가 생성되지 않았습니다.");
        } // [check 3]

        // [check 4] : playerDbManager 가 생성되었을 때만 closeDB method 실행
        if (this.playerDbManager != null) {
            this.playerDbManager.closeDb();
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 메니저가 생성되지 않았습니다.");
        } // [check 4]

    } // End of method [onDestroy]



    /*                                      private method
     *   ============================================================================================
     *  */


    /**
     * [method] project_blue.db 의 billiard, user, friend 테이블을 관리하는 메니저를 생성한다.
     */
    private void createDBManager() {

        // [iv/C]BilliardDBManager : billiard 테이블을 관리하는 메니저 생성과 초기화
        this.billiardDbManager = new BilliardDbManager(this);
        this.billiardDbManager.initDb();

        // [iv/C]UserDbManager : user 테이블을 관리하는 메니저 생성과 초기화
        this.userDbManager = new UserDbManager(this);
        this.userDbManager.initDb();

        // [iv/C]FriendDbManager : friend 테이블을 관리하는 메니저 생성과 초기화
        this.friendDbManager = new FriendDbManager(this);
        this.friendDbManager.initDb();

        // [iv/C]PlayerDbManager : player 테이블을 관리하는 메니저 생성과 초기화
        this.playerDbManager = new PlayerDbManager(this);
        this.playerDbManager.initDb();

    } // End of method [createDBManager]


    /**
     * [method] activity_billiard_input.xml 의 widget 을 mapping(뜻: 하나의 값을 다른 값으로 대응시키는 것을 말한다.)
     */
    private void mappingOfWidget() {

        // [iv/C]TextView : player 1 name mapping
        this.playerName_1 = (TextView) findViewById(R.id.billiard_input_player_name_1);

        // [iv/C]Spinner : player 1 target score mapping
        this.playerTargetScore_1 = (Spinner) findViewById(R.id.billiard_input_player_target_score_1);

        // [iv/C]EditText : player 1 score mapping
        this.playerScore_1 = (EditText) findViewById(R.id.billiard_input_player_score_1);


        // [iv/C]TextView : player 2 name mapping
        this.playerName_2 = (TextView) findViewById(R.id.billiard_input_player_name_2);

        // [iv/C]Spinner : player 2 target score mapping
        this.playerTargetScore_2 = (Spinner) findViewById(R.id.billiard_input_player_target_score_2);

        // [iv/C]EditText : player 2 score mapping
        this.playerScore_2 = (EditText) findViewById(R.id.billiard_input_player_score_2);


        // [iv/C]LinearLayout : player 3 mapping
        this.player3 = (LinearLayout) findViewById(R.id.billiard_input_ll_player_3);

        // [iv/C]TextView : player 3 name mapping
        this.playerName_3 = (TextView) findViewById(R.id.billiard_input_player_name_3);

        // [iv/C]Spinner : player 3 target score mapping
        this.playerTargetScore_3 = (Spinner) findViewById(R.id.billiard_input_player_target_score_3);

        // [iv/C]EditText : player 3 score mapping
        this.playerScore_3 = (EditText) findViewById(R.id.billiard_input_player_score_3);


        // [iv/C]LinearLayout : player 4 mapping
        this.player4 = (LinearLayout) findViewById(R.id.billiard_input_ll_player_4);

        // [iv/C]TextView : player 4 name mapping
        this.playerName_4 = (TextView) findViewById(R.id.billiard_input_player_name_4);

        // [iv/C]Spinner : player 4 target score mapping
        this.playerTargetScore_4 = (Spinner) findViewById(R.id.billiard_input_player_target_score_4);

        // [iv/C]EditText : player 4 score mapping
        this.playerScore_4 = (EditText) findViewById(R.id.billiard_input_player_score_4);


        // [iv/C]TextView : date mapping / 날짜
        this.date = (TextView) findViewById(R.id.billiard_input_date);

        // [iv/C]TextView : reDate mapping / 날짜 다시
        this.reDate = (TextView) findViewById(R.id.billiard_input_re_date);

        // [iv/C]Spinner : gameMode mapping / 종목
        this.gameMode = (Spinner) findViewById(R.id.billiard_input_sp_game_mode);

        // [iv/C]Spinner : winnerName mapping / 승자
        this.winnerName = (Spinner) findViewById(R.id.billiard_input_sp_winner);

        // [iv/C]EditText : playTime mapping / 게임 시간
        this.playTime = (EditText) findViewById(R.id.billiard_input_play_time);

        // [iv/C]EditText : cost mapping / 비용
        this.cost = (EditText) findViewById(R.id.billiard_input_cost);


        // [iv/C]Button : input mapping / 입력 버튼
        this.input = (Button) findViewById(R.id.billiard_input_bt_input);

    } // End of method [mappingOfWidget]


    /**
     * [method] date widget 의 날짜를 다른 날짜로 변경한다.
     */
    private void setTextToDifferentDate() {

        // [lv/C]DateModify : custom 된 DateModify Dialog 를 생성한다.
        DateModify dateModify = new DateModify(BilliardInputActivity.this);

        // [lv/C]DateModify : 위 에서 생성한 다이어로그를 셋팅하고 보여준다.
        dateModify.setDialog(this.date);

    } // End of method [setTextToDifferentDate]


    /**
     * [method] userData 의 player 1 등록
     */
    private void addPlayerListOfUserData(UserData userData, ArrayAdapter adapter) {

        final String METHOD_NAME = "[addPlayerListOfUserData] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 를 player 에 등록합니다.");

        // [iv/C]TextView : player 1 name
        this.playerName_1.setText(userData.getName());

        // [iv/C]Spinner : player 1 target score 의 adapter 연결과 초기값 설정
        this.playerTargetScore_1.setAdapter(adapter);
        this.playerTargetScore_1.setSelection(userData.getTargetScore() - 1);

        // [lv/C]PlayerData : userData 를 첫번째 player 로 만들기
        PlayerData playerData = new PlayerData();
        playerData.setPlayerId(userData.getId());

        // [iv/C]ArrayList<PlayerData> : 위 의 내용을 첫번째 player 을 등록 / player 1
        this.playerDataArrayList.add(playerData);

        // [iv/C]ArrayList<String> : 첫번째 player 의 name 을 등록 / player 1
        this.playerNameList.add(userData.getName());

    } // End of method [addPlayerListOfUserData]


    /**
     * [method] 첫번째 friend 데이터를 의 player 2 등록
     */
    private void addPlayerListOfFirstFriendData(FriendData friendData, ArrayAdapter adapter) {

        final String METHOD_NAME = "[addPlayerListOfUserData] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 를 player 에 등록합니다.");

        // [iv/C]TextView : player 2 name
        this.playerName_2.setText(friendData.getName());

        // [iv/C]Spinner : player 2 target score 의 adapter 연결과 초기값 설정
        this.playerTargetScore_2.setAdapter(adapter);
        this.playerTargetScore_2.setSelection(DEFAULT_TARGET_SCORE);

        // [lv/C]PlayerData : friendData 를 두번째 player 로 만들기
        PlayerData playerData = new PlayerData();
        playerData.setPlayerId(friendData.getId());

        // [iv/C]ArrayList<PlayerData> : 위 의 내용을 두번째 player 을 등록 / player 2
        this.playerDataArrayList.add(playerData);

        // [iv/C]ArrayList<String> : 두번째 player 의 name 을 등록 / player 2
        this.playerNameList.add(friendData.getName());

    } // End of method [addPlayerListOfFirstFriendData]


    /**
     * [method] 두번째 friend 데이터를 의 player 3 등록
     */
    private void addPlayerListOfSecondFriendData(FriendData friendData, ArrayAdapter adapter) {

        final String METHOD_NAME = "[addPlayerListOfSecondFriendData] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 를 player 에 등록합니다.");

        // [iv/C]TextView : player 3 name
        this.playerName_3.setText(friendData.getName());

        // [iv/C]Spinner : player 3 target score 의 adapter 연결과 초기값 설정
        this.playerTargetScore_3.setAdapter(adapter);
        this.playerTargetScore_3.setSelection(DEFAULT_TARGET_SCORE);

        // [lv/C]PlayerData : friendData 를 두번째 player 로 만들기
        PlayerData playerData = new PlayerData();
        playerData.setPlayerId(friendData.getId());

        // [iv/C]ArrayList<PlayerData> : 위 의 내용을 세번째 player 을 등록 / player 3
        this.playerDataArrayList.add(playerData);

        // [iv/C]ArrayList<String> : 세번째 player 의 name 을 등록 / player 3
        this.playerNameList.add(friendData.getName());

    } // End of method [addPlayerListOfSecondFriendData]


    /**
     * [method] 세번째 friend 데이터를 의 player 4 등록
     */
    private void addPlayerListOfThirdFriendData(FriendData friendData, ArrayAdapter adapter) {

        final String METHOD_NAME = "[addPlayerListOfThirdFriendData] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 를 player 에 등록합니다.");

        // [iv/C]TextView : player 4 name
        this.playerName_4.setText(friendData.getName());

        // [iv/C]Spinner : player 4 target score 의 adapter 연결과 초기값 설정
        this.playerTargetScore_4.setAdapter(adapter);
        this.playerTargetScore_4.setSelection(DEFAULT_TARGET_SCORE);

        // [lv/C]PlayerData : friendData 를 두번째 player 로 만들기
        PlayerData playerData = new PlayerData();
        playerData.setPlayerId(friendData.getId());

        // [iv/C]ArrayList<PlayerData> : 위 의 내용을 네번째 player 을 등록 / player 4
        this.playerDataArrayList.add(playerData);

        // [iv/C]ArrayList<String> : 네번째 player 의 name 을 등록 / player 4
        this.playerNameList.add(userData.getName());

    } // End of method [addPlayerListOfThirdFriendData]


    /**
     * [method] player 의 목록을 만들기
     */
    private void setPlayerList(UserData userData, ArrayList<FriendData> friendDataArrayList) {

        final String METHOD_NAME = "[setPlayerList] ";
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "메소드를 실행 합니다.");

        // [lv/C]ArrayAdapter : target score 의 초기값을 설정
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.targetScore, android.R.layout.simple_spinner_dropdown_item);

        // [method]addPlayerListOfUserData : 첫번째 player 등록 / userData
        addPlayerListOfUserData(userData, adapter);


        // [check 1] : friendDataArrayList 의 size 값으로 player 의 수 알아내기
        if (friendDataArrayList.size() == 1) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "참가인원 중 친구목록은 1명입니다.");

            // [method]addPlayerListOfFirstFriendData : 두번째 player 등록 / friendData, index=0
            addPlayerListOfFirstFriendData(friendDataArrayList.get(0), adapter);

            // [iv/C]LinearLayout : player3, player4 숨기다.
            this.player3.setVisibility(LinearLayout.GONE);
            this.player4.setVisibility(LinearLayout.GONE);

        } else if (friendDataArrayList.size() == 2) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "참가인원 중 친구목록은 2명입니다.");

            // [method]addPlayerListOfFirstFriendData : 두번째 player 등록 / friendData, index=0
            addPlayerListOfFirstFriendData(friendDataArrayList.get(0), adapter);

            // [method]addPlayerListOfFirstFriendData : 세번째 player 등록 / friendData, index=1
            addPlayerListOfSecondFriendData(friendDataArrayList.get(1), adapter);

            // [iv/C]LinearLayout : player4 숨기다.
            this.player4.setVisibility(LinearLayout.GONE);

        } else if (friendDataArrayList.size() == 3) {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "참가인원 중 친구목록은 3명입니다.");

            // [method]addPlayerListOfFirstFriendData : 두번째 player 등록 / friendData, index=0
            addPlayerListOfFirstFriendData(friendDataArrayList.get(0), adapter);

            // [method]addPlayerListOfFirstFriendData : 세번째 player 등록 / friendData, index=1
            addPlayerListOfSecondFriendData(friendDataArrayList.get(1), adapter);

            // [method]addPlayerListOfFirstFriendData : 네번째 player 등록 / friendData, index=2
            addPlayerListOfThirdFriendData(friendDataArrayList.get(2), adapter);

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "선택한 친구 목록이 없어요.");
        }

    } // End of method [setPlayList]


    /**
     * [method] player, targetScore, speciality spinner widget 의 초기값을 설정한다.
     *
     * <p>
     * player 는 UserDate 와 ArrayList<FriendData> 의 name 을 모두 추가한 adapter 를 이용하여 화면에 뿌려준다.
     *
     * <p>
     * gameMode 는 R.array.gameMode 을 adapter 로 연결하여 화면에 뿌려준다.
     * 그리고 UserData 의 gameMode 로 초기값으로 설정한다.
     */
    private void setSpinnerWidgetOfWinnerNameAndGameMode(ArrayList<String> playerNameList) {

        final String METHOD_NAME = "[setSpinnerWidget] ";

        // [lv/C]ArrayAdapter<String> : 일반적인 방법이 아니라 동적으로 받은 String 값을 셋팅해서 만들어야 하므로 아래의 생성자로 생성하고 add method 를 이용하여 문자열(player name)을 추가한다.
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, this.playerNameList);

        // [iv/C]Spinner : 위 에서 만들어진 adapter 와 연결하기
        this.winnerName.setAdapter(adapter);

        // [lv/C]ArrayAdapter : R.array.speciality 값을 speciality spinner 에 연결하는 adapter 를 생성한다.
        ArrayAdapter gameModeAdapter = ArrayAdapter.createFromResource(this, R.array.gameMode, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위 의 adapter 를 이용하여 speciality spinner 의 목록을 만든다.
        this.gameMode.setAdapter(gameModeAdapter);

        // [iv/C]Spinner : speciality spinner 의 초기값을 UserData 의 speciality 값으로 셋팅한다.
        this.gameMode.setSelection(getSelectedIdFromUserSpeciality(this.userData.getSpeciality()));

    } // End of method [setSpinnerWidget]


    /**
     * [method] UserData 의 getSpeciality 값으로 speciality spinner 의 id 값을 리턴한다.
     *
     * @return speciality spinner 의 id 값
     */
    private int getSelectedIdFromUserSpeciality(String speciality) {

        final String METHOD_NAME = "[getSelectedIdFromUserSpeciality] ";

        // [check 1] : "3구" 이면 0, "4구" 이면 1, "포켓볼" 이면 2  - java version 7 이후로 switch 문에서 String 을 지원한다.
        switch (speciality) {
            case "3구":
                return 0;
            case "4구":
                return 1;
            case "포켓볼":
                return 2;
            default:
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "현재 목록 중에 있는 값이 없네요.");
                return -1;
        }

    } // End of method [getSelectedIdFromUserSpeciality]


    /**
     * [method] 입력을 진행할 건지 물어보는 dialog 를 보여준다.
     */
    public void showDialogToCheckWhetherSave() {

        final String METHOD_NAME = "[showDialogToCheckWhetherSave] ";

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : Builder 초기화
        builder.setTitle(R.string.ad_billiard_input_check_input_title)
                .setMessage(R.string.ad_billiard_input_check_input_message)
                .setPositiveButton(R.string.ad_billiard_input_bt_check_input_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        setClickListenerOfInputButton();

                    }
                })
                .setNegativeButton(R.string.ad_billiard_input_bt_check_input_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }


    /**
     * [method] Player 의 name, targetScore, score 을 입력받았는지 확인하여 그 결과를 true, false 로 리턴한다.
     *
     * @return 모든 데이터 입력 여부
     */
    public boolean checkWhetherInputPlayerData(TextView playerName, Spinner playerTargetScore, EditText playerScore) {

        final String METHOD_NAME = "[checkWhetherInputPlayerData] ";

        // [check 1] : player 1 의 모든 데이터가 입력 되었다.
        if (!playerName.getText().toString().equals("")
                && !playerTargetScore.getSelectedItem().toString().equals("")
                && !playerScore.getText().toString().equals("")) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player name : " + playerName.getText());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player name 입력 여부 : " + playerName.getText().toString().equals(""));
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player target score : " + playerTargetScore.getSelectedItem());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player target score 입력 여부 : " + playerTargetScore.getSelectedItem().toString().equals(""));
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player score : " + playerScore.getText());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player score 입력 여부 : " + playerScore.getText().toString().equals(""));
            return true;
        } else {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player name : " + playerName.getText());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player name 입력 여부 : " + playerName.getText().toString().equals(""));
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player target score : " + playerTargetScore.getSelectedItem());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player target score 입력 여부 : " + playerTargetScore.getSelectedItem().toString().equals(""));
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player score : " + playerScore.getText());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 모든 데이터를 입력 받았습니다. player score 입력 여부 : " + playerScore.getText().toString().equals(""));
            return false;
        } // [check 1]

    } // End of method [checkWhetherInputPlayerData]


    /**
     * [method] Player 의 데이터를 모두 입력 받았는지 확인
     * name,
     */
    public boolean checkWhetherInputAllPlayerData(int playerCount) {

        final String METHOD_NAME = "[checkWhetherInputAllPlayerData] ";

        // [lv/b]isChecked : 모든 데이터가 형식에 맞게 입력되었는지 여부를 저장하는 변수
        boolean isFormat = false;

        // [check 1] : player 의 수는 2 명이다.
        if (playerCount == 2) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "Player 는 2 명입니다.");

            // [check 2] : player 1, 2 의 모든 값을 입력 받았나?
            if (checkWhetherInputPlayerData(this.playerName_1, this.playerTargetScore_1, this.playerScore_1)
                    && checkWhetherInputPlayerData(this.playerName_2, playerTargetScore_2, this.playerScore_2)) {

                isFormat = true;

            } else {
                isFormat = false;
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1, 2 의 점수 데이터가 입력되지 않았습니다.");
            } // [check 2]

        } else if (playerCount == 3) {
            // player 의 수는 3 명이다.
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "Player 는 3 명입니다.");

            // [check 3] : player 1, 2, 3 의 모든 값을 입력 받았나?
            if (checkWhetherInputPlayerData(this.playerName_1, this.playerTargetScore_1, this.playerScore_1)
                    && checkWhetherInputPlayerData(this.playerName_2, playerTargetScore_2, this.playerScore_2)
                    && checkWhetherInputPlayerData(this.playerName_3, playerTargetScore_3, this.playerScore_3)) {
                isFormat = true;
            } else {
                isFormat = false;
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1, 2, 3 의 점수 데이터가 입력되지 않았습니다.");
            } // [check 3]

        } else if (playerCount == 4) {
            // player 의 수는 4 명이다.
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "Player 는 4 명입니다.");

            // [check 4] : player 1, 2, 3, 4 의 모든 값을 입력 받았나?
            if (checkWhetherInputPlayerData(this.playerName_1, this.playerTargetScore_1, this.playerScore_1)
                    && checkWhetherInputPlayerData(this.playerName_2, playerTargetScore_2, this.playerScore_2)
                    && checkWhetherInputPlayerData(this.playerName_3, playerTargetScore_3, this.playerScore_3)
                    && checkWhetherInputPlayerData(this.playerName_4, playerTargetScore_4, this.playerScore_4)) {

                isFormat = true;

            } else {
                isFormat = false;
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1, 2, 3 의 점수 데이터가 입력되지 않았습니다.");
            } // [check 4]

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 수가 2~4 명 범위에 들지 않습니다.");
        } // [check 1]

        return isFormat;
    } // End of method [checkWhetherInputAllPlayerData]


    /**
     * [method] player 의 score 값이 targetScore 값의 범위에 맞게 되었는지 확인
     */
    private boolean checkWhetherRangeOfTargetScore(Spinner playerTargetScore, EditText playerScore) {

        final String METHOD_NAME = "[checkWhetherRangeOfTargetScore] ";

        // [lv/b]isRange : 범위에 맞는 값이 입력되었는지 확인
        boolean isRange = false;

        // [lv/i]maxRange : playerTargetScore 로 maxRange
        int maxRange = playerTargetScore.getSelectedItemPosition() + 1;
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "playerTargetScore 값으로 알아낸 maxRange : " + maxRange);

        // [lv/i]checkRageValue : playerScore 로 checkRageValue
        int checkRageValue = Integer.parseInt(playerScore.getText().toString());
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "playerScore 값으로 알아낸 checkRangeValue : " + checkRageValue);

        // [check 1] : 0 < playerScore <= (playerTargetScore 의 값)
        if ((0 < checkRageValue) && (checkRageValue <= maxRange)) {
            return true;
        } else {
            return false;
        }

    } // End of method [checkWhetherRangeOfTargetScore]


    /**
     * [method] player 의 score 값이 targetScore 값의 범위에 맞게 되었는지 확인
     */
    private boolean checkWhetherRangeOfTargetScoreAllPlayer(int playerCount) {

        final String METHOD_NAME = "[checkWhetherRangeOfTargetScoreAllPlayer] ";

        // [check 1] : player 수가 2 명이다.
        if (playerCount == 2) {

            // [check 2] : player 1, 2 의 범위가 모두 맞다.
            if (checkWhetherRangeOfTargetScore(this.playerTargetScore_1, this.playerScore_1)
                    && checkWhetherRangeOfTargetScore(this.playerTargetScore_2, this.playerScore_2)) {

                return true;

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1, 2 의 범위가 맞지 않습니다.");
                return false;
            } // [check 2]

        } else if (playerCount == 3) {

            // [check 3] : player 1, 2, 3 의 범위가 모두 맞다.
            if (checkWhetherRangeOfTargetScore(this.playerTargetScore_1, this.playerScore_1)
                    && checkWhetherRangeOfTargetScore(this.playerTargetScore_2, this.playerScore_2)
                    && checkWhetherRangeOfTargetScore(this.playerTargetScore_3, this.playerScore_3)) {
                return true;
            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1, 2 의 범위가 맞지 않습니다.");
                return false;
            } // [check 3]

        } else if (playerCount == 4) {

            // [check 4] : player 1, 2, 3, 4 의 범위가 모두 맞다.
            if (checkWhetherRangeOfTargetScore(this.playerTargetScore_1, this.playerScore_1)
                    && checkWhetherRangeOfTargetScore(this.playerTargetScore_2, this.playerScore_2)
                    && checkWhetherRangeOfTargetScore(this.playerTargetScore_3, this.playerScore_3)
                    && checkWhetherRangeOfTargetScore(this.playerTargetScore_4, this.playerScore_4)) {
                return true;
            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 1, 2 의 범위가 맞지 않습니다.");
                return false;
            } // [check 4]
        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 수가 맞지 않습니다.");
            return false;
        }

    } // End of method [checkWhetherRangeOfTargetScore]


    /**
     * [method] billiard 게임 데이터를 모두 입력 받았는지 확인하여 그 결과를 true, false 로 반환한다.
     * date, gameMode, winnerName, playTime
     *
     * @return 모두 입력하였을 시 true, 하나라도 입력 안 받았을 시 false 를 반환한다.
     */
    private boolean checkWhetherInputAllBilliardData() {

        final String METHOD_NAME = "[checkWhetherInputAllBilliardData] ";

        // [check 1] : EditText 의 getText 으로 받아온 값으로 모두 입력 받은 것을 확인하였다.
        if (!date.getText().toString().equals("")
                && !gameMode.getSelectedItem().toString().equals("")
                && !winnerName.getSelectedItem().toString().equals("")
                && !playTime.getText().toString().equals("")
                && !cost.getText().toString().equals("")
        ) {
            // 모두 입력 받았으면
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 의 모든 데이터를 입력 받았습니다.");
            return true;
        } else {
            // 하나라도 입력 안 받았으면
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 의 모든 데이터를 입력해주세요!");
            return false;
        } // [check 1]

    } // End of method [checkInputAllEditText]


    /**
     * [method] billiard 테이블에 게임 데이터를 저장하기
     */
    private long saveBilliardData(String date, String gameMode, int playerCount, long winnerId, int playTime, String score, int cost) {

        // [lv/l]billiardCount  : billiardDbManager 를 통해서 billiard 테이블에 저장한다. 그리고 그 결과로 넘어온 행의 값(=count) 을 받아온다.
        long billiardCount = this.billiardDbManager.saveContent(date, gameMode, playerCount, winnerId, playTime, score, cost);

        return billiardCount;

    } // End of method [saveBilliardData]


    /**
     * [method] playerDataArrayList 의 size 만큼 targetScore, score 의 값을 셋팅한다.
     */
    private void setTargetScoreAndScoreOfPlayerList(ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[setTargetScoreAndScoreOfPlayerList] ";


        // [lv/C]String : score 값을 String 으로 임시 저장
        String tempScore = null;

        // [iv/C]ArrayList<PlayerData> : player 1 의 targetScore 와 score 값 설정, scoreArrayList 에 담기
        tempScore = this.playerScore_1.getText().toString();
        playerDataArrayList.get(0).setTargetScore(Integer.parseInt(this.playerTargetScore_1.getSelectedItem().toString()));
        playerDataArrayList.get(0).setScore(Integer.parseInt(tempScore));

        // [iv/C]ArrayList<PlayerData> : player 2 의 targetScore 와 score 값 설정, scoreArrayList 에 담기
        tempScore = this.playerScore_2.getText().toString();
        playerDataArrayList.get(1).setTargetScore(Integer.parseInt(this.playerTargetScore_2.getSelectedItem().toString()));
        playerDataArrayList.get(1).setScore(Integer.parseInt(tempScore));

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "SIZE 는 = " + playerDataArrayList.size());


        // [check 1] : playerDataArrayList 의 size 값 구분
        switch (playerDataArrayList.size()) {
            case 3:
                // [iv/C]ArrayList<PlayerData> : player 3 의 targetScore 와 score 값 설정, scoreArrayList 에 담기
                tempScore = this.playerScore_3.getText().toString();
                playerDataArrayList.get(2).setTargetScore(Integer.parseInt(this.playerTargetScore_3.getSelectedItem().toString()));
                playerDataArrayList.get(2).setScore(Integer.parseInt(tempScore));
                break;
            case 4:

                // [iv/C]ArrayList<PlayerData> : player 3 의 targetScore 와 score 값 설정, scoreArrayList 에 담기
                tempScore = this.playerScore_3.getText().toString();
                playerDataArrayList.get(2).setTargetScore(Integer.parseInt(this.playerTargetScore_3.getSelectedItem().toString()));
                playerDataArrayList.get(2).setScore(Integer.parseInt(tempScore));

                // [iv/C]ArrayList<PlayerData> : player 4 의 targetScore 와 score 값 설정, scoreArrayList 에 담기
                tempScore = this.playerScore_4.getText().toString();
                playerDataArrayList.get(3).setTargetScore(Integer.parseInt(this.playerTargetScore_4.getSelectedItem().toString()));
                playerDataArrayList.get(3).setScore(Integer.parseInt(tempScore));
                break;
            default:
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 수가 맞지 않습니다.");
                break;
        } // [check 1]

    } // End of method [setTargetScoreAndScoreOfPlayerList]


    /**
     * [method] playerDataArrayList 에 있는 score 값을 ArrayList<String> 으로 담아서 반환한다.
     */
    private ArrayList<String> getScoreFormat(ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[getScoreFormat] ";

        // [lv/C]ArrayList<String> : score 값을 담은 ArrayList 객체 생성
        ArrayList<String> scoreArrayList = new ArrayList<>();

        // [lv/C]ArrayList<String> : player 1 의 score 를 저장
        scoreArrayList.add(Integer.toString(playerDataArrayList.get(0).getScore()));

        // [lv/C]ArrayList<String> : player 2 의 score 를 저장
        scoreArrayList.add(Integer.toString(playerDataArrayList.get(1).getScore()));


        // [cycle 1] : playerDataArrayList 의 size 만큼
        for (int index=2; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayList<PlayerData> : index=2 or 3 이 있을 때, playerDataArrayList 의 score 를 추가하기
            scoreArrayList.add(Integer.toString(playerDataArrayList.get(index).getScore()));

        } // [cycle 1]

        return scoreArrayList;
    } // End of method [getScoreFormat]

    /**
     * [method] userData 와 friendDataArrayList 의 데이터를 업데이트하여 데이터베이스에 저장한다.
     */
    private void updateUserDataAndAllFriendData(long userId, long winnerId) {

        String date = this.date.getText().toString();
        int playTime = Integer.parseInt(this.playTime.getText().toString());
        int cost = Integer.parseInt(this.cost.getText().toString());

        // [check 1] : 내가 승리자이다. / userId=winnerId
        if (userId == winnerId) {

        } else {
            // 패배자이다.

        } // [check 1]

    }


    /**
     * [method] 모든 값을 입력 받아 billiard 테이블에 데이터를 저장하고,
     * 승리자, 패배자를 구분하여 UserData 와 FriendData 의 'id' 값을 이용하여
     * user, friend 테이블의 내용을 업데이트한다.
     */
    private void setClickListenerOfInputButton() {

        final String METHOD_NAME = "[setClickListenerOfInputButton] ";



        // [check 1] : player 의 데이터가 모두 입력 되었다.
        if (checkWhetherInputAllPlayerData(playerDataArrayList.size())) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 player 의 데이터를 입력 받았습니다.");

            // [check 2] : player 의 score 가 범위에 맞게 입력되었다.
            if (checkWhetherRangeOfTargetScoreAllPlayer(playerDataArrayList.size())) {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "범위에 맞는 데이터를 입력 받았습니다.");

                // [check 3] : billiard 의 데이터가 형식에 맞게 모두 입력 되었다.
                if (checkWhetherInputAllBilliardData()) {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 데이터를 데이터 베이스에 입력합니다.");

                    // [method] : playerDataArrayList 의 targetScore, score 값을 셋팅하기
                    setTargetScoreAndScoreOfPlayerList(this.playerDataArrayList);

                    // date, gameMode, playerCount, winnerId, playTime, score, cost 의 값을 구한다.
                    String date = this.date.getText().toString();
                    String gameMode = this.gameMode.getSelectedItem().toString();
                    int playerCount = this.playerDataArrayList.size();
                    long winnerId = this.playerDataArrayList.get(this.winnerName.getSelectedItemPosition()).getPlayerId();
                    int playTime = Integer.parseInt(this.playTime.getText().toString());
                    int cost = Integer.parseInt(this.cost.getText().toString());
                    String score = ProjectBlueDataFormatter.getFormatOfScore(getScoreFormat(playerDataArrayList));


                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "해당 스코어로 만든 스코어 값은 = " + score);

                    DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, playerDataArrayList);

                } else {
                    toastHandler("billiard 의 모든 데이터를 입력해주세요. ");
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 의 모든 데이터를 입력 받지 못했습니다.");
                } // [check 3]

            } else {
                toastHandler("범위에 맞는 score 의 값을 입력해주세요.");
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 의 score 값이 범위에 맞는 값이 아닙니다.");
            } // [check 2]

        } else {
            toastHandler("모든 player 의 데이터를 입력해주세요.");
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 player 의 데이터를 입력 받지 않았습니다.");
        } // [check 1]


    } // End of method [setClickListenerOfInputButton]


    /**
     * [method] BilliardDisplayActivity 로 이동 할 것인지 물어보는 dialog 를 보여준다.
     */
    private void showDialogToCheckWhetherToMoveBDA() {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : 초기값 설정 및 화면보기
        builder.setTitle(R.string.ad_billiard_input_complete_input_title)
                .setMessage(R.string.ad_billiard_input_complete_input_message)
                .setPositiveButton(R.string.ad_billiard_input_bt_complete_input_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 intent 생성
                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);

                        // [lv/C]Intent : intent 에 userData 를 담아서 보내기
                        SessionManager.setIntentOfUserData(intent, userData);

                        // [method]finish : 이 BilliardInputActivity 화면 종료
                        finish();

                        // [method]startActivity : intent 설정 값으로 화면이동
                        startActivity(intent);
                    }
                })
                .show();

    }

    /**
     * [method] 해당 문자열을 toast 로 보여준다.
     */
    private void toastHandler(String content) {

        // [lv/C]Toast : toast 객체 생성
        Toast myToast = Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT);

        // [lv/C]Toast : 위에서 생성한 객체를 보여준다.
        myToast.show();

    } // End of method [toastHandler]

}