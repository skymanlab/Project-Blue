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

        // [iv/C]Spinner : player 1 target score 및 초기값 설정
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

        // [iv/C]Spinner : player 2 target score
        this.playerTargetScore_2.setAdapter(adapter);

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

        // [iv/C]Spinner : player 3 target score
        this.playerTargetScore_3.setAdapter(adapter);

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

        // [iv/C]Spinner : player 4 target score
        this.playerTargetScore_4.setAdapter(adapter);

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

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "size 가 1 입니다.");

            // [method]addPlayerListOfFirstFriendData : 두번째 player 등록 / friendData, index=0
            addPlayerListOfFirstFriendData(friendDataArrayList.get(0), adapter);

            // [iv/C]LinearLayout : player3, player4 숨기다.
            this.player3.setVisibility(LinearLayout.GONE);
            this.player4.setVisibility(LinearLayout.GONE);

        } else if (friendDataArrayList.size() == 2) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "size 가 2 입니다.");

            // [method]addPlayerListOfFirstFriendData : 두번째 player 등록 / friendData, index=0
            addPlayerListOfFirstFriendData(friendDataArrayList.get(0), adapter);

            // [method]addPlayerListOfFirstFriendData : 세번째 player 등록 / friendData, index=1
            addPlayerListOfSecondFriendData(friendDataArrayList.get(1), adapter);

            // [iv/C]LinearLayout : player4 숨기다.
            this.player4.setVisibility(LinearLayout.GONE);

        } else if (friendDataArrayList.size() == 3) {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "size 가 3 입니다.");

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

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : Builder 초기화
        builder.setTitle(R.string.ad_billiard_input_check_input_title)
                .setMessage(R.string.ad_billiard_input_check_input_message)
                .setPositiveButton(R.string.ad_billiard_input_bt_check_input_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method]setClickListenerOfInputButton : 입력 받은 값으로 billiardData 를 저장하고, userData 와 friendData 의 내용을 갱신한다.
//                        setClickListenerOfInputButton();

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
     * [method] 모든 값을 입력 받아 billiard 테이블에 데이터를 저장하고,
     * 승리자, 패배자를 구분하여 UserData 와 FriendData 의 'id' 값을 이용하여
     * user, friend 테이블의 내용을 업데이트한다.
     */
    private void setClickListenerOfInputButton() {

        final String METHOD_NAME = "[setClickListenerOfInputButton] ";



//        // [check 1] : EditText 의 모든 값을 입력 받지 않았다.
//        if (!checkInputAllEditText()) {
//            Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
//            // 버튼 이벤트 종료
//            return;
//        } // [check 1]
//
//        // [iv/C]BilliardDBManager : billiardData 값을 받아서 billiard 테이블에 저장한다.
//        this.billiardDbManager.saveContent(
//                userData.getId(),
//                date.getText().toString(),
//                Integer.parseInt(targetScore.getSelectedItem().toString()),
//                speciality.getSelectedItem().toString(),
//                Integer.parseInt(playTime.getText().toString()),
//                player.getSelectedItem().toString(),
//                BilliardDataFormatter.getFormatOfScore(score_1.getText().toString(), score_2.getText().toString()),
//                Integer.parseInt(cost.getText().toString())
//        );
//        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 테이블에 저장을 완료했습니다.");
//
//
//        // [check 2] : userData 데이터가 있다.
//        if (this.userData != null) {
//            // [lv/i]gameRecordWin : userData 가 승리했을 시, 그 값이 저장될 변수
//            int gameRecordWin = 0;
//
//            // [lv/i]gameRecordLoss : userData 가 패배했을 시, 그 값이 저장될 변수
//            int gameRecordLoss = 0;
//
//            // [check 2-1] : 승리자 이름과 userData 의 name 이 같다. 즉, userData 가 승리
//            if (this.player.getSelectedItem().equals(this.userData.getName())) {
//
//                // [lv/i] : userData 의 승리이므로, 기존의 gameRecordWin 값에 +1
//                gameRecordWin = this.userData.getGameRecordWin() + 1;
//
//                // [lv/i] : userData 의 승리이므로, 기존의 gameRecordLoss 값은 그대로
//                gameRecordLoss = this.userData.getGameRecordLoss();
//
//            } else {
//                // friendDAta 가 승리
//
//                // [lv/i] : userData 의 패배이므로, 기존의 gameRecordWin 값은 그대로
//                gameRecordWin = this.userData.getGameRecordWin();
//
//                // [lv/i] : userData 의 패배이므로, 기존의 gameRecordLoss 값에 +1
//                gameRecordLoss = this.userData.getGameRecordLoss() + 1;
//
//            } // [check 2-1]
//
//            // [lv/l]recentGamePlayerID : userData 와 최근에 게임한 플레이어의 id. 즉, playerData 의 id 이다.
//            long recentGamePlayerId = this.playerData.getId();
//
//            // [lv/S]recentPlayData : userData 가 최근에 게임한 날짜. 즉 오늘 날짜이다.
//            String recentPlayDate = this.date.getText().toString();
//
//            // [lv/i]totalPlayTime : 기존의 userData 의 totalPlayTime 에 오늘 게임한 시간을 더한 값이다.
//            int totalPlayTime = this.userData.getTotalPlayTime() + Integer.parseInt(this.playTime.getText().toString());
//
//            // [lv/i]totalCost : 기존의 userData 의 totalCost 에 오늘 게임한 비용을 더한 값이다.
//            int totalCost = this.userData.getTotalCost() + Integer.parseInt(this.cost.getText().toString());
//
//            DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData.getId(), gameRecordWin, gameRecordLoss, recentGamePlayerId, recentPlayDate, totalPlayTime, totalCost);
//
//            // [iv/C]UserDbManager : 위에서 셋팅된 값들을 이용하여 해당 userId 의 user 데이터를 갱신하기
//            this.userDbManager.updateContent(this.userData.getId(),
//                    gameRecordWin,
//                    gameRecordLoss,
//                    recentGamePlayerId,
//                    recentPlayDate,
//                    totalPlayTime,
//                    totalCost);
//
//            // [iv/C]UserData : 위에서 변경된 데이터를 해당 userId 로 가져오기
//            this.userData = this.userDbManager.loadContent(this.userData.getId());
//            DeveloperManager.displayToUserData(CLASS_NAME_LOG, this.userData);
//
//        } else {
//            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "어플의 이번 단계에서 userData 가 없다는 것이 말이 안됩니다. 이 데이터를 기반으로 업데이트를 진행해야 합니다.");
//        }
//
//
//        // [check 3] : friendData 데이터가 있다.
//        if (this.playerData != null) {
//
//            // [iv/i]gameRecordWin : FriendData 가 승리했을 시, 그 값이 저장될 변수
//            int gameRecordWin = 0;
//
//            // [lv/i]gameRecordLoss : FriendData 가 패배했을 시, 그 값이 저장될 변수
//            int gameRecordLoss = 0;
//
//            // [check 3-1] : 승리자 이름과 FriendData 의 name 이 같다. 즉, FriendData 가 승리
//            if (this.player.getSelectedItem().equals(this.playerData.getName())) {
//
//                // [lv/i] : FriendData 의 승리이므로, 기존의 gameRecordWin 값에 +1
//                gameRecordWin = this.playerData.getGameRecordWin() + 1;
//
//                // [lv/i] : FriendData 의 승리이므로, 기존의 gameRecordLoss 값은 그대로
//                gameRecordLoss = this.playerData.getGameRecordLoss();
//
//            } else {
//
//                // [lv/i] : FriendData 의 패배이므로, 기존의 gameRecordWin 값은 그대로
//                gameRecordWin = this.playerData.getGameRecordWin();
//
//                // [lv/i] : FriendData 의 패배이므로, 기존의 gameRecordLoss 값에 +1
//                gameRecordLoss = this.playerData.getGameRecordLoss() + 1;
//
//            } // [check 3-1]
//
//            // [lv/S]recentPlayData : friendData 가 최근에 게임한 날짜. 즉 오늘 날짜이다.
//            String recentPlayDate = this.date.getText().toString();
//
//            // [lv/i]totalPlayTime : 기존의 friendData 의 totalPlayTime 에 오늘 게임한 시간을 더한 값이다.
//            int totalPlayTime = this.playerData.getTotalPlayTime() + Integer.parseInt(this.playTime.getText().toString());
//
//            // [lv/i]totalCost : 기존의 friendData 의 totalCost 에 오늘 게임한 비용을 더한 값이다.
//            int totalCost = this.playerData.getTotalCost() + Integer.parseInt(this.cost.getText().toString());
//
//            DeveloperManager.displayToFriendData(CLASS_NAME_LOG, this.playerData.getId(), gameRecordWin, gameRecordLoss, recentPlayDate, totalPlayTime, totalCost);
//
//            // [iv/C]FriendDbManager : 위에서 셋팅된 값들을 이용해서 해당 id 로 Friend 데이터 갱신하기 / 해당 userId 로 가져온 친구목록이기 때문에 userId 는 필요없이 friend 의 id 값으로 찾으면 된다.
//            this.friendDbManager.updateContentById(
//                    this.playerData.getId(),
//                    gameRecordWin,
//                    gameRecordLoss,
//                    recentPlayDate,
//                    totalPlayTime,
//                    totalCost
//            );
//
//            // [iv/C]FriendData : 위에서 갱신된 데이터를 해당 id 로 가져오기
//            this.playerData = this.friendDbManager.loadContentById(this.playerData.getId());
//            DeveloperManager.displayToFriendData(CLASS_NAME_LOG, this.playerData);
//
//        } else {
//            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "friendDbManager 가 초기화되지 않았습니다. initDb 값을 확인하세요.");
//        }// [check 3]
//
//        // [method]showDialogToCheckWhetherToMoveBDA : 다음 BilliardDisplayActivity 로 이동할 지 묻는 dialog 를 보여준다.
//        showDialogToCheckWhetherToMoveBDA();
//
//        // [iv/C]Button : input disable, background color exchange
//        this.input.setEnabled(false);
//        this.input.setBackgroundResource(R.color.colorWidgetDisable);

    } // End of method [setClickListenerOfInputButton]





    /**
     * [method] EditText widget 의 값이 모두 입력되었는지 검사하여 모두 입력하였을 시 true 를 반환한다.
     *
     * @return 모두 입력하였을 시 true, 하나라도 입력 안 받았을 시 false 를 반환한다.
     */
    /* method : 모든 값을 입력 받았나요?*/
//    private boolean checkInputAllEditText() {
//
//        final String METHOD_NAME= "[checkInputAllEditText] ";
//
//        // [check 1] : EditText 의 getText 으로 받아온 값으로 모두 입력 받은 것을 확인하였다.
//        if (!this.score_1.getText().toString().equals("") &&
//                !this.score_2.getText().toString().equals("") &&
//                !this.playTime.getText().toString().equals("") &&
//                !this.cost.getText().toString().equals("")
//        ) {
//            // 모두 입력 받았으면
//            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 값을 입력 받았습니다.");
//            return true;
//        } else {
//            // 하나라도 입력 안 받았으면
//            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 값을 입력해주세요!");
//            return false;
//        } // End of method [ch]
//
//    } // End of method [checkInputAllEditText]


    /**
     * [method] BilliardDisplayActivity 로 이동 할 것인지 물어보는 dialog 를 보여준다.
     */
    private void showDialogToCheckWhetherToMoveBDA() {

//        // [lv/C]AlertDialog : Builder 객체 생성
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // [lv/C]AlertDialog : 초기값 설정 및 화면보기
//        builder.setTitle(R.string.ad_billiard_input_complete_input_title)
//                .setMessage(R.string.ad_billiard_input_complete_input_message)
//                .setPositiveButton(R.string.ad_billiard_input_bt_complete_input_positive, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 intent 생성
//                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
//
//                        // [lv/C]Intent : intent 에 userData 를 담아서 보내기
//                        SessionManager.setIntentOfUserData(intent, userData);
//
//                        // [method]finish : 이 BilliardInputActivity 화면 종료
//                        finish();
//
//                        // [method]startActivity : intent 설정 값으로 화면이동
//                        startActivity(intent);
//                    }
//                })
//                .show();

    }


    /**
     * [method] billiard 테이블에 저장된 모든 데이터를 custom layout 에 adapter 로 연결하여 뿌려준다.
     */
    private void displayBilliardData() {

        final String METHOD_NAME = "[displayBilliardData] ";

//        // [check 1] : userData 가 있다.
//        if (this.userData != null) {
//
//            // [lv/C]BilliardLvManager : 위 의 내용을 토대로 custom list view 에 뿌리는 메니저 객체 생성
//            BilliardLvManager billiardLvManager = new BilliardLvManager(this.allBilliardData, this.billiardDbManager);
//
//            // [lv/C]BilliardLvManager : userData 의 id 로 모든 billiardData 와 userData 의 name 을 추가한다.
////            billiardLvManager.addData(this.billiardDbManager.loadAllContentByUserID(this.userData.getId()), this.userData.getName());
//
//            // [lv/C]BilliardLvManager : allBilliardList 를 adapter 로 연결하기
//            billiardLvManager.setListViewToAdapter();
//
//            // [iv/C]ListView : allBilliardData 에 있는 개수로 마지막 item 을 선택하여 보여주기
//            this.allBilliardData.setSelection(this.allBilliardData.getCount());
//
//            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "allBilliardData 에 총 item 개수는 : " + this.allBilliardData.getCount());
//
//
//        } else {
//            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 가 없습니다.");
//        } // [check 1]

    } // End of method [displayBilliardData]


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