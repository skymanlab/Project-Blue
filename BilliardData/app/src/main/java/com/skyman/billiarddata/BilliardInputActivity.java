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
    private final int PLAYER_WIDGET_MAX_SIZE = 4;

    // instant variable
    private BilliardDbManager billiardDbManager = null;
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private PlayerDbManager playerDbManager = null;
    private UserData userData = null;
    private ArrayList<FriendData> friendDataArrayList = null;
    private ArrayList<PlayerData> playerDataArrayList = null;

    // instance variable : player widget
    private TextView playerName[] = new TextView[4];
    private Spinner playerTargetScore[] = new Spinner[4];
    private EditText playerScore[] = new EditText[4];
    private LinearLayout playerSection[] = new LinearLayout[4];

    // instance variable : billiard widget
    private TextView date;
    private TextView reDate;
    private Spinner gameMode;
    private Spinner playerNameList;
    private EditText playTime;
    private EditText cost;

    // instance variable
    private Button input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_input);

        final String METHOD_NAME = "[onCreate] ";

        // [lv/C]Intent : 전 Activity 에서 보낸 Intent 가져오기
        Intent intent = getIntent();

        // [iv/C]UserData : 세션에서 userData 를 가져온다. / "userData"
        this.userData = SessionManager.getUserDataInIntent(intent);

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "intent 로 넘어온 userData 가 있어요.");

        // [iv/C]ArrayList<FriendData> : 세션에서 friendDataArrayList 를 가져온다. / "friendPlayerList"
        this.friendDataArrayList = SessionManager.getFriendPlayerListInIntent(intent);

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
                setTextToModifiedDate();

            }
        });

        // [check 1] : userData, friendDataArrayList 의 내용이 있다.
        if ((this.userData != null) && (friendDataArrayList.size() > 0)) {

            // [method]createDBManager : billiard, user, friend, player 테이블 메니저 생성
            createDBManager();

            // [iv/C]ArrayList<PlayerData> : userData 와 friendDataArrayList 를 player 에 등록한다.
            this.playerDataArrayList = registerPlayerToPlayerDataArrayList(this.userData, this.friendDataArrayList);

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "등록 된 player 의 정보를 확인합니다.");
            DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, this.playerDataArrayList);

            // [check 2] : playerDataArrayList 에 등록되어 있는 player 가 있다.
            if (playerDataArrayList.size() != 0) {

                // [method] : 위에서 등록한 player 의 정보로 playerName 을 설정하고,
                setInitialDataOfPlayerWidget(this.playerDataArrayList);

                // [method] : billiard widget 의 gameMode, playerNameList 를 설정하기
                setInitialDataOfBilliardWidget(this.playerDataArrayList, this.userData);

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 와 friendDataArrayList 를 player 등록된 사람이 없습니다.");
            }

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "셋팅할 userData 와 friendDataArrayList 가 없습니다.");
        } // [check 1]

        // [iv/C]Button : input click listener
        this.input = (Button) findViewById(R.id.billiard_input_bt_input);
        this.input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]showDialogToCheckWhetherSave : 입력 받은 값으로 billiardData 를 저장할 건지 dialog 띄워 물어본 뒤 진행한다.
                showDialogToCheckWhetherSave();

            }
        });

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
     * [method] activity_billiard_input layout 의 widget 을 mapping(뜻: 하나의 값을 다른 값으로 대응시키는 것을 말한다.)
     */
    private void mappingOfWidget() {

        // [iv/C]TextView : playerName 배열 mapping
        this.playerName[0] = (TextView) findViewById(R.id.billiard_input_player_name_0);
        this.playerName[1] = (TextView) findViewById(R.id.billiard_input_player_name_1);
        this.playerName[2] = (TextView) findViewById(R.id.billiard_input_player_name_2);
        this.playerName[3] = (TextView) findViewById(R.id.billiard_input_player_name_3);

        // [iv/C]Spinner  : playerSpinner 배열 mapping
        this.playerTargetScore[0] = (Spinner) findViewById(R.id.billiard_input_player_target_score_0);
        this.playerTargetScore[1] = (Spinner) findViewById(R.id.billiard_input_player_target_score_1);
        this.playerTargetScore[2] = (Spinner) findViewById(R.id.billiard_input_player_target_score_2);
        this.playerTargetScore[3] = (Spinner) findViewById(R.id.billiard_input_player_target_score_3);

        // [iv/C]EditText : playerScore 배열 mapping
        this.playerScore[0] = (EditText) findViewById(R.id.billiard_input_player_score_0);
        this.playerScore[1] = (EditText) findViewById(R.id.billiard_input_player_score_1);
        this.playerScore[2] = (EditText) findViewById(R.id.billiard_input_player_score_2);
        this.playerScore[3] = (EditText) findViewById(R.id.billiard_input_player_score_3);

        // [iv/C]LinearLayout : playerSection 배열 mapping
        this.playerSection[0] = (LinearLayout) findViewById(R.id.billiard_input_ll_player_section_0);
        this.playerSection[1] = (LinearLayout) findViewById(R.id.billiard_input_ll_player_section_1);
        this.playerSection[2] = (LinearLayout) findViewById(R.id.billiard_input_ll_player_section_2);
        this.playerSection[3] = (LinearLayout) findViewById(R.id.billiard_input_ll_player_section_3);

        // [iv/C]TextView : date mapping / 날짜
        this.date = (TextView) findViewById(R.id.billiard_input_date);

        // [iv/C]TextView : reDate mapping / 날짜 다시
        this.reDate = (TextView) findViewById(R.id.billiard_input_re_date);

        // [iv/C]Spinner : gameMode mapping / 종목
        this.gameMode = (Spinner) findViewById(R.id.billiard_input_sp_game_mode);

        // [iv/C]Spinner : winnerName mapping / 승자
        this.playerNameList = (Spinner) findViewById(R.id.billiard_input_sp_player_name_list);

        // [iv/C]EditText : playTime mapping / 게임 시간
        this.playTime = (EditText) findViewById(R.id.billiard_input_play_time);

        // [iv/C]EditText : cost mapping / 비용
        this.cost = (EditText) findViewById(R.id.billiard_input_cost);


        // [iv/C]Button : input mapping / 입력 버튼
        this.input = (Button) findViewById(R.id.billiard_input_bt_input);

    } // End of method [mappingOfWidget]


    /**
     * [method] date widget 의 날짜를 다른 날짜로 변경하는 DateModify dialog 를 보여준다.
     */
    private void setTextToModifiedDate() {

        // [lv/C]DateModify : custom 된 DateModify Dialog 를 생성한다.
        DateModify dateModify = new DateModify(BilliardInputActivity.this);

        // [lv/C]DateModify : 위 에서 생성한 다이어로그를 셋팅하고 보여준다.
        dateModify.setDialog(this.date);

    } // End of method [setTextToModifiedDate]


    /**
     * [method] [A] playerDataArrayList 에 player 를 등록한다.
     *
     * <p>
     * player 0 은 userData 를 등록 / playerDataArrayList.get(0)
     * player 1 은 friendDataArrayList.get(0) 을 등록 / playerDataArrayList.get(1)
     * player 2 는 friendDataArrayList.get(1) 을 등록 / playerDataArrayList.get(2)
     * player 3 은 friendDataArrayList.get(2) 을 등록 / playerDataArrayList.get(3)
     * </p>
     *
     * @param userData            player 0
     * @param friendDataArrayList player 1~3
     * @return player 가 등록되어 있는
     */
    private ArrayList<PlayerData> registerPlayerToPlayerDataArrayList(UserData userData, ArrayList<FriendData> friendDataArrayList) {

        // [lv/C]ArrayList<PlayerData> : player 가 등록될 객체
        ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();

        // [lv/C]PlayerData : 등록할 player 의 id 와 name 을 설정한다.
        PlayerData player0 = new PlayerData();
        player0.setPlayerId(userData.getId());
        player0.setPlayerName(userData.getName());

        // [lv/C]ArrayList<PlayerData> : 위의 player 를 등록한다.
        playerDataArrayList.add(player0);

        // [cycle 1] : friendDataArrayList 의 size
        for (int index = 0; index < friendDataArrayList.size(); index++) {

            // [lv/C]PlayerData : 등록할 player 의 id 와 name 을 설정한다..
            PlayerData player = new PlayerData();
            player.setPlayerId(friendDataArrayList.get(index).getId());
            player.setPlayerName(friendDataArrayList.get(index).getName());

            // [lv/C]ArrayList<PlayerData> : 위의 player 를 등록한다.
            playerDataArrayList.add(player);

        } // [cycle 1]

        return playerDataArrayList;
    }


    /**
     * [method] [A] player widget 을 playerDataArrayList 로 초기 데이터를 설정한다.
     *
     * <P>
     * player widget 의 종류
     * 1. playerSection
     * 2. playerName
     * 3. playerTargetScore
     * 4. playerScore
     * </P>
     *
     * <p>
     * playerName widget 을 playerDataArrayList.get(index) 의 playerName 으로
     * playerTargetScore 를 R.array.targetScore 으로 연결한다.
     * playerTargetScore[0] 은 userData 에 해당하므로 userData 의 targetScore 값을 기본값으로 한다.
     * </p>
     *
     * <p>
     * 등록 안 된 나머지 player 의 widget 은 setVisible 을 false 로 설정한다.
     * </p>
     */
    private void setInitialDataOfPlayerWidget(ArrayList<PlayerData> playerDataArrayList) {

        // [cycle 1] : playerDataArrayList 에 등록되어 있는 player 의 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [iv/C]TextView : player 의 name 을 playerName widget 에 설정
            this.playerName[index].setText(playerDataArrayList.get(index).getPlayerName());

            //[method] : index 번째의 targetScore spinner 에 Adapter 연결
            setAdapterOfPlayerTargetScoreSpinner(index);

        } // [cycle 1]

        // [cycle 2] : 등록 한 player widget 다음 부터 마지막 widget 까지
        for (int hideIndex = playerDataArrayList.size(); hideIndex < PLAYER_WIDGET_MAX_SIZE; hideIndex++) {

            // [iv/C]LinearLayout : 등록 된 player 이외는 숨기기
            this.playerSection[hideIndex].setVisibility(LinearLayout.GONE);

        } // [cycle 2]

        // [iv/C]Spinner : player 0 번의 targetScore 의 초기값 정하기 / userData 의 targetScore 로
        this.playerTargetScore[0].setSelection(userData.getTargetScore() - 1);

    }

    /**
     * [method] [A] player widget 중 score spinner 를 R.array.targetScore 와 연결한다.
     */
    private void setAdapterOfPlayerTargetScoreSpinner(int index) {

        // [lv/C]ArrayAdapter : R.array.targetScore 으로 adapter 생성
        ArrayAdapter targetScoreAdapter = ArrayAdapter.createFromResource(this, R.array.targetScore, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위에서 생성한 adapter 를 playerTargetScore spinner 중 index 번째에 연결하기
        this.playerTargetScore[index].setAdapter(targetScoreAdapter);

    } // End of method [setAdapterOfPlayerTargetScoreSpinner]


    /**
     * [method] [B] billiard widget 을 초기 데이터를 설정한다.
     *
     * <p>
     * billiard widget 의 종류
     * 1. date
     * 2. gameMode
     * 3. playerName
     * 4. playTime
     * 5. cost
     * </p>
     */
    private void setInitialDataOfBilliardWidget(ArrayList<PlayerData> playerDataArrayList, UserData userData) {

        // [method] : gameMode spinner 의 초기 데이터 설정
        setAdapterOfBilliardGameModeSpinner();

        // [method] : playerNameList  spinner 의 초기 데이터 설정
        setAdapterOfBilliardPlayerNameListSpinner(playerDataArrayList);

        // [iv/C]Spinner : gameMode spinner 의 기본값 설정
        this.gameMode.setSelection(ProjectBlueDataFormatter.getSelectedIdOfBilliardGameModeSpinner(userData.getSpeciality()));

    } // End o method [setInitialDataOfBilliardWidget]


    /**
     * [method] [B] billiard widget 중 gameMode spinner 를 R.array.gameMode 와 연결한다.
     */
    private void setAdapterOfBilliardGameModeSpinner() {

        // [lv/C]ArrayAdapter : R.array.gameMode 로 adapter 생성
        ArrayAdapter gameModeAdapter = ArrayAdapter.createFromResource(this, R.array.gameMode, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위에서 생성한 adapter 를 gameMode spinner 에 연결하기
        this.gameMode.setAdapter(gameModeAdapter);

    } // End of method [setAdapterOfBilliardWinnerNameSpinner]


    /**
     * [method] [B] billiard widget 중 playerNameList spinner 를 playerDataArrayList 의 playerName 과 연결한다.
     */
    private void setAdapterOfBilliardPlayerNameListSpinner(ArrayList<PlayerData> playerDataArrayList) {

        // [lv/C]ArrayAdapter<String> : playerDataArrayList 에 등록되어 있는 playerName 과 연결하기 위한 adapter
        ArrayAdapter<String> playerNameListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        // [cycle 1] : playerDataArrayList 에 등록된 player 등록 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayAdapter<String> : playerName 을 adapter 에 추가하기
            playerNameListAdapter.add(playerDataArrayList.get(index).getPlayerName());

        } // [cycle 1]

        // [iv/C]Spinner : 위에서 생성한 adapter 를 playerNameList 에 연결하기
        this.playerNameList.setAdapter(playerNameListAdapter);

    } // End of method [setAdapterOfBilliardPlayerNameListSpinner]


    /**
     * [method] [C] 입력을 진행할 건지 물어보는 dialog 를 보여준다.
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

                        // [method] : 버튼 클릭 이벤트를 실시
                        setClickListenerOfInputButton();

                    }
                })
                .setNegativeButton(R.string.ad_billiard_input_bt_check_input_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherSave]


    /**
     * [method] [C] 모든 값을 입력 받아 billiard 테이블에 데이터를 저장하고,
     * 승리자, 패배자를 구분하여 UserData 와 FriendData 의 'id' 값을 이용하여
     * user, friend 테이블의 내용을 업데이트한다.
     */
    private void setClickListenerOfInputButton() {

        final String METHOD_NAME = "[setClickListenerOfInputButton] ";


        // [check 1] : player 의 데이터가 모두 입력 되었다.
        if (checkWhetherInputAllDataOfPlayerWidget(playerDataArrayList.size())) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player widget> 모든 데이터를 입력 받았습니다.");

            // [check 2] : player 의 score 가 범위에 맞게 입력되었다.
            if (checkWhetherInputWithinRangeOfPlayerScoreValue(playerDataArrayList.size())) {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<score> 범위에 맞는 데이터를 입력 받았습니다.");

                // [check 3] : billiard 의 데이터가 형식에 맞게 모두 입력 되었다.
                if (checkWhetherInputAllDataOfBilliardWidget()) {
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiard widget> 모든 데이터를 입력받았습니다.");

                    // [check 4] : winner 의 targetScore 와 score 가 같습니다.
                    if (checkWhetherEqualOfTargetScoreAndScore()) {
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<targetScore> 와 <score> 가 같으므로 승자가 맞습니다.");
                        
                        // [method] : playerDataArrayList 의 targetScore, score 값을 셋팅하기
                        setTargetScoreAndScoreOfPlayerList(this.playerDataArrayList);

                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "===== playerDataArrayList 의 데이터를 확인하겠습니다. =====");
                        DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, this.playerDataArrayList);

                        // date, gameMode, playerCount, winnerId, playTime, score, cost 의 값을 구한다.
                        String date = this.date.getText().toString();
                        String gameMode = this.gameMode.getSelectedItem().toString();
                        int playerCount = this.playerDataArrayList.size();
                        long winnerId = this.playerDataArrayList.get(this.playerNameList.getSelectedItemPosition()).getPlayerId();
                        String winnerName = this.playerNameList.getSelectedItem().toString();
                        int playTime = Integer.parseInt(this.playTime.getText().toString());
                        int cost = Integer.parseInt(this.cost.getText().toString());
                        String score = ProjectBlueDataFormatter.getFormatOfScore(makeScoreStringArray(playerDataArrayList));

                        // [lv/l]billiardCount : billiard 테이블에 저장을 하고, 그 결과값이 count 값을 받는다.
                        long billiardCount = saveDataOfBilliard(this.billiardDbManager, date, gameMode, playerCount, winnerId, winnerName, playTime, score, cost);

                        // [method] : 모든 player 의 gameRecordWin, gameRecordLoss, totalPlayTime, totalCost, recentGameBilliardCount 를 설정한다.
                        setDataOfPlayer(this.playerDataArrayList, this.userData,this.friendDataArrayList, winnerId, winnerName, playTime, cost, billiardCount);

                        // [method] : playerDataArrayList 의 billiardCount 를 저장 결과인 billiardCount 값을 저장한다.
                        setBilliardCountOfPlayerList(this.playerDataArrayList, billiardCount);

                        // [method] : userData 업데이트 하기
                        saveDataOfUser(this.userDbManager, this.userData);

                        // [method] : friendDataArrayList 업데이트 하기
                        saveDataFriendList(this.friendDbManager, this.friendDataArrayList);

                        // [method] : playerDataArrayList 저장하기
                        saveDataOfPlayer(this.playerDbManager, this.playerDataArrayList);

                        // [method] : player, billiard widget 을 초기화
                        initializeOfPlayerAndBilliardWidget(this.playerDataArrayList.size());

                        // [method] : BilliardDisplayActivity 로 이동할 지 물어본다.
                        showDialogToCheckWhetherToMoveBDA();
                    } else {
                        toastHandler("승리자의 점수가 수지와 같아야 합니다.");
                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<targetScore> 와 <score> 가 같아야지만 승리자요!");
                    } // [check 4]

                } else {
                    toastHandler("billiard 의 모든 데이터를 입력해주세요. ");
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiard widget> 모든 데이터를 입력해줘!");
                } // [check 3]

            } else {
                toastHandler("범위에 맞는 score 의 값을 입력해주세요.");
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<score> 범위에 맞는 점수를 입력해줘!");
            } // [check 2]

        } else {
            toastHandler("모든 player 의 데이터를 입력해주세요.");
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player widget> 모든 데이터를 입력해줘!");
        } // [check 1]

    } // End of method [setClickListenerOfInputButton]


    /**
     * [method] [check] player widget 에서 모든 값들이 다 입력되었는지 검사하여 그 값을 true 또는 false 로 반환한다.
     *
     * <p>
     * 검사 항목
     * 1. playerName
     * 2. playerTargetScore
     * 3. playerScore
     * </p>
     *
     * @param playerCount 등록 된 player 수
     * @return 모든 player 의 데이터가 입력되었는가? yes=true, no=false
     */
    private boolean checkWhetherInputAllDataOfPlayerWidget(int playerCount) {

        final String METHOD_NAME = "[checkWhetherInputAllDataOfPlayerWidget] ";

        // [lv/b]inputAllData : 모든 player 의 데이터가 입력되었는가?
        boolean inputAllData = true;

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 player widget 에 모든 데이터가 입력되었는지를 검사합니다.");

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerCount; index++) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + index + "번째 playerName = " + this.playerName[index].getText().toString());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + index + "번째 playerTargetScore = " + this.playerTargetScore[index].getSelectedItem().toString());
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + index + "번째 playerScore = " + this.playerScore[index].getText().toString());

            // [check 1] : playerName, playerTargetScore, playerScore 를 모두 입력 받았다.
            if (!this.playerName[index].getText().toString().equals("")
                    && !this.playerTargetScore[index].getSelectedItem().toString().equals("")
                    && !this.playerScore[index].getText().toString().equals("")) {

                // 모든 데이터가 입력 되었으므로 다음 player 검사하기 / 기존의 true 값 유지
                continue;
            } else {
                // 모든 데이터가 입력되지 않았다. 다음 player 검사할 필요가 없다. 그러므로 false 로 바꾸어라.
                inputAllData = false;
                break;
            } // [check 1]

        } // [cycle 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player widget 의 검사 결과는 = " + inputAllData);

        return inputAllData;

    } // End of method [checkWhetherInputAllDataOfPlayerWidget]


    /**
     * [method] [check] player widget 중 playerScore 의 범위를 검사하여 그 결과를 true 또는 false 로 반환한다.
     *
     * <p>
     * score 의 범위 = 0 < score < TargetScore
     * </p>
     *
     * @param playerCount 등록 된 player 수
     * @return 모든 player 의 score 범위가 맞게 입력되었는가? yes=true, no=false
     */
    private boolean checkWhetherInputWithinRangeOfPlayerScoreValue(int playerCount) {

        final String METHOD_NAME = "[checkWhetherInputWithinRangeOfPlayerScoreValue] ";
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "playerScore 의 범위를 검사합니다.");

        // [lv/b]isWithinRange : 범위 내의 값인가요?
        boolean isWithinRange = true;

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerCount; index++) {

            // [lv/i]targetScore : playerTargetScore 의 값
            int targetScore = Integer.parseInt(this.playerTargetScore[index].getSelectedItem().toString());

            // [lv/i]score : playerScore 의 값
            int score = Integer.parseInt(this.playerScore[index].getText().toString());

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + index + " 번째 targetScore = " + targetScore);
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + index + " 번째 score = " + score);

            // [check 1] : o <= score <= TargetScore
            if ((0 <= score) && (score <= targetScore)) {
                // 범위 내의 값
                continue;
            } else {
                // 이상한 값
                isWithinRange = false;
                break;
            } // [check 1]

        } // [cycle 1]

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "playerScore 의 검사 결과는 = " + isWithinRange);

        return isWithinRange;

    } // End of method [checkWhetherInputWithinRangeOfPlayerScoreValue]


    /**
     * [method] [check] billiard widget 의 모든 데이터가 입력되었는지 검사하여 그 결과를 true 또는 false 로 반환한다.
     *
     * <p>
     * 검사항목
     * 1. date
     * 2. gameMode
     * 3. playerNameList
     * 4. playTime
     * 5. cost
     * </p>
     *
     * @return billiard 의 데이터가 모두 입력되었는가? yes=true, no=false
     */
    private boolean checkWhetherInputAllDataOfBilliardWidget() {

        final String METHOD_NAME = "[checkWhetherInputAllBilliardData] ";

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard widget 에 모든 데이터가 입력되었는지 검사합니다.");

        // [check 1] : EditText 의 getText 으로 받아온 값으로 모두 입력 받은 것을 확인하였다.
        if (!date.getText().toString().equals("")
                && !gameMode.getSelectedItem().toString().equals("")
                && !playerNameList.getSelectedItem().toString().equals("")
                && !playTime.getText().toString().equals("")
                && !cost.getText().toString().equals("")
        ) {
            // 모두 입력 받았으면
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 billiard widget 을 검사한 결과 = true");
            return true;
        } else {
            // 하나라도 입력 안 받았으면
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "모든 billiard widget 을 검사한 결과 = false");
            return false;
        } // [check 1]

    } // End of method [checkInputAllEditText]


    /**
     * [method] [check] winner 의 targetScore 와 score 값이 같은지 판별하여 같으면 true, 다르면 false 를 반환한다.
     *
     */
    private boolean checkWhetherEqualOfTargetScoreAndScore() {

        final String METHOD_NAME = "[checkWhetherEqualOfTargetScoreAndScore] ";
        
        // [lv/i]winnerPosition : playerNameList spinner 의 winner 의 위치를 받아온다.
        int winnerPosition = this.playerNameList.getSelectedItemPosition();

        // [lv/i]targetScore : winner 의 playerTargetScore 의 값을 int type casting
        int targetScore =Integer.parseInt( this.playerTargetScore[winnerPosition].getSelectedItem().toString() );

        // [lv/i]score : winner 의 playerScore 의 값을 int type casting
        int score = Integer.parseInt(this.playerScore[winnerPosition].getText().toString());

        // [check 1] : winner 의 위치의 targetScore 와 score 가 같다.
        if (targetScore == score) {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "승리자의 <targetScore> 와 <score> 가 같습니다.");
            return true;
        } else {

            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "승리자의 <targetScore> 와 <score> 가 같지 않아요!");
            return false;
        } // [check 1]

    } // End of method [checkWhetherEqualOfTargetScoreAndScore]


    /**
     * [method] [CP] player widget 중 targetScore, score 으로 playerDataArrayList 의 targetScore, score 을 설정하기
     *
     * <p>
     * playerDataArrayList.get(4) / targetScore
     * playerDataArrayList.get(5) / score
     * </p>
     *
     * @param playerDataArrayList 등록된 player 의 데이터가 있는
     */
    private void setTargetScoreAndScoreOfPlayerList(ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[setTargetScoreAndScoreOfPlayerList] ";

        // [lv/C]String : score 값을 String 으로 임시 저장
        String tempScore = null;

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayList<PlayerData> : playerDataArrayList 의 targetScore 값을 설정
            playerDataArrayList.get(index).setTargetScore(Integer.parseInt(playerTargetScore[index].getSelectedItem().toString()));

            // [lv/C]ArrayList<PlayerData> : playerDataArrayList 의 score 값을 설정
            playerDataArrayList.get(index).setScore(Integer.parseInt(playerScore[index].getText().toString()));

        } // [cycle 1]

    } // End of method [setTargetScoreAndScoreOfPlayerList]


    /**
     * [method] [CP] playerDataArrayList 의 recentGameBilliardCount 를 billiard 테이블의 count 값으로 설정하기
     */
    private void setBilliardCountOfPlayerList(ArrayList<PlayerData> playerDataArrayList, long billiardCount) {

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayList<PlayerData> : playerDataArrayList 의 billiardCount 값을 설정
            playerDataArrayList.get(index).setBilliardCount(billiardCount);

        } // [cycle 1]

    } // End of method [setRecentGameBilliardCountOfPlayerList]


    /**
     * [method] 모든 playerData 를 player 테이블에 저장한다.
     *
     * @param playerDbManager
     * @param playerDataArrayList
     * @return player 테이블에 저정하면 얻는 count 값을 담은 배열
     */
    private long[] saveDataOfPlayer(PlayerDbManager playerDbManager, ArrayList<PlayerData> playerDataArrayList) {

        // [lv/l]count : 등록된 player 의 count 값들을 저장
        long[] count = new long[playerDataArrayList.size()];

        // [cycle 1] : 등록된 player 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {
            count[index] = playerDbManager.saveContent(
                    playerDataArrayList.get(index).getBilliardCount(),
                    playerDataArrayList.get(index).getPlayerId(),
                    playerDataArrayList.get(index).getPlayerName(),
                    playerDataArrayList.get(index).getTargetScore(),
                    playerDataArrayList.get(index).getScore()
            );
        } // [cycle 1]

        return count;
    } // End of method [saveDataOfPlayer]


    /**
     * [method] [CP] playerDataArrayList 에 있는 score 값을 ArrayList<String> 으로 담아서 반환한다.
     *
     * @param playerDataArrayList 등록된 player 의 데이터가 있는
     * @return score 만 담겨있는 ArrayList<String>
     */
    private ArrayList<String> makeScoreStringArray(ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[makeScoreStringArray] ";

        // [lv/C]ArrayList<String> : score 값을 담은 ArrayList 객체 생성
        ArrayList<String> scoreArrayList = new ArrayList<>();

        // [cycle 1] : 등록된 player 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayList<String> : playerDataArrayList 의 score 를 추가한다.
            scoreArrayList.add(playerDataArrayList.get(index).getScore() + "");

        } // [cycle 1]

        return scoreArrayList;
    } // End of method [makeScoreStringArray]


    /**
     * [method] [CB] billiardData 의 데이터를 billiard 테이블에 저장한다.
     */
    private long saveDataOfBilliard(BilliardDbManager billiardDbManager, String date, String gameMode, int playerCount, long winnerId, String winnerName, int playTime, String score, int cost) {

        // [lv/l]billiardCount  : billiardDbManager 를 통해서 billiard 테이블에 저장한다. 그리고 그 결과로 넘어온 행의 값(=count) 을 받아온다.
        long billiardCount = billiardDbManager.saveContent(date, gameMode, playerCount, winnerId, winnerName, playTime, score, cost);

        return billiardCount;

    } // End of method [saveDataOfBilliard]


    /**
     * [method] [CUF] playerDataArrayList 의 winnerId 와 winner 로 승리, 패배를 구분한다. 그리고 userData 와 friendDataArrayList 의 gameRecordWin 또는 gameRecordLoss 에 +1 을 한 값을 설정한다.
     *
     * <p>
     * player 0 은 userData 에 해당
     * player 1, 2, 3 은 friendDataArrayList 에 해당
     *
     * </p>
     *
     * @param playerDataArrayList 플레이어의 목록이 담긴
     * @param userData            player 0 의 데이터가 있는
     * @param friendDataArrayList player 1, 2, 3 의 데이터가 있는
     * @param winnerId            승리자의 id
     */
    private void setGameRecordOfPlayer(ArrayList<PlayerData> playerDataArrayList, UserData userData, ArrayList<FriendData> friendDataArrayList, long winnerId, String winnerName) {

        final String METHOD_NAME = "[setGameRecordOfPlayer] ";

        // [cycle 1] : playerDataArrayList 의 size 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {


            // [check 1] : player 0 이다. / userData
            if (index == 0) {

                // [check 2] : 승리이다.
                if ((playerDataArrayList.get(index).getPlayerId() == winnerId) && (playerDataArrayList.get(index).getPlayerName().equals(winnerName))) {

                    // [lv/C]UserData : userData 에서 기존 gameRecordWin 에 +1 하여 저장 / 승리 추가
                    userData.setGameRecordWin(userData.getGameRecordWin() + 1);

                } else {
                    // 패배이다.

                    // [lv/C]UserData : userData 에서 기존 gameRecordLoss 에 +1 하여 저장 / 패배 추가
                    userData.setGameRecordLoss(userData.getGameRecordLoss() + 1);

                } // [check 2]

            } else {
                // player 1, 2, 3 이다. / friendDataArrayList

                // [lv/i]friendIndex : player 1, 2, 3 의 friendDataArrayList 의 index 는 0, 1, 2 이다. 즉 index-1
                int friendIndex = index - 1;

                // [check 3] : 승리이다.
                if ((playerDataArrayList.get(index).getPlayerId() == winnerId) && (playerDataArrayList.get(index).getPlayerName().equals(winnerName))) {

                    // [lv/C]ArrayList<FriendData> : friendData 에서 기존 gameRecordWin 에 +1 하여 저장 / 승리 추가
                    friendDataArrayList.get(friendIndex).setGameRecordWin(friendDataArrayList.get(friendIndex).getGameRecordWin() + 1);

                } else {
                    // 패배이다.

                    // [lv/C]ArrayList<FriendData> : friendData 에서 기존 gameRecordLoss 에 +1 하여 저장 / 패배 추가
                    friendDataArrayList.get(friendIndex).setGameRecordLoss(friendDataArrayList.get(friendIndex).getGameRecordLoss() + 1);

                } // [check 3]

            } // [check 1]

        } // [cycle 1]

    } // End of method [setGameRecordOfPlayer]


    /**
     * [method] [CUF] playTime widget 의 값을 모든 player 의 totalPlayTime 에 더하기
     *
     * @param playerCount         player 의 수
     * @param userData            player 0 의 데이터가 있는
     * @param friendDataArrayList player 1, 2, 3 의 데이터가 있는
     * @param playTime            게임시간
     */
    private void setTotalPlayTimeOfPlayer(int playerCount, UserData userData, ArrayList<FriendData> friendDataArrayList, int playTime) {

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerCount; index++) {

            // [check 1] : player 0 이다. / userData
            if (index == 0) {

                // [lv/C]UserData : playTime widget 의 값을 userData 의 totalPlayTime 에 더한 값을 설정한다.
                userData.setTotalPlayTime(userData.getTotalPlayTime() + playTime);

            } else {
                // player 1, 2, 3 이다. / friendDataArrayList

                // [lv/i]friendIndex : player 1, 2, 3 의 friendDataArrayList 의 index 는 0, 1, 2 이다. 즉 index-1
                int friendIndex = index - 1;

                // [lv/C]ArrayList<FriendData> : playTime widget 의 값을 friendData 의 totalPlayTime 에 더한 값을 설정한다.
                friendDataArrayList.get(friendIndex).setTotalPlayTime(friendDataArrayList.get(friendIndex).getTotalPlayTime() + playTime);

            } // [check 1]

        } // [cycle 1]

    } // End of method [setTotalPlayTimeOfPlayer]


    /**
     * [method] [CUF] cost widget 의 값을 모든 player 의 totalCost 에 더한다.
     *
     * @param playerCount         player 의 수
     * @param userData            player 0 의 데이터가 있는
     * @param friendDataArrayList player 1, 2, 3 의 데이터가 있는
     * @param cost                비용
     */
    private void setTotalCostOfPlayer(int playerCount, UserData userData, ArrayList<FriendData> friendDataArrayList, int cost) {

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerCount; index++) {

            // [check 1] : player 0 이다. / userData
            if (index == 0) {

                // [lv/C]UserData : cost widget 의 값을 userData 의 totalCost 에 더한 값을 설정한다.
                userData.setTotalCost(userData.getTotalCost() + cost);

            } else {
                // player 1, 2, 3 이다. / friendDataArrayList

                // [lv/i]friendIndex : player 1, 2, 3 의 friendDataArrayList 의 index 는 0, 1, 2 이다. 즉 index-1
                int friendIndex = index - 1;

                // [lv/C]ArrayList<FriendData> : cost widget 의 값을 friendData 의 totalCost 에 더한 값을 설정한다.
                friendDataArrayList.get(friendIndex).setTotalCost(friendDataArrayList.get(friendIndex).getTotalCost() + cost);

            } // [check 1]

        } // [cycle 1]

    } // End of method [setTotalCostPlayer]


    /**
     * [method] [CUF] saveDataOfBilliard 의 결과값인 billiard 테이블의 count 값을 player 의 recentGameBilliardCount 로 설정하기
     *
     * @param playerCount         player 의 수
     * @param userData            player 0 의 데이터가 있는
     * @param friendDataArrayList player 1, 2, 3 의 데이터가 있는
     * @param billiardCount       billiard 테이블의 count
     */
    private void setRecentGameBilliardCountOfPlayer(int playerCount, UserData userData, ArrayList<FriendData> friendDataArrayList, long billiardCount) {

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerCount; index++) {

            // [check 1] : player 0 이다. / userData
            if (index == 0) {

                // [lv/C]UserData : billiardCount 의 값을 userData 의 recentGameBilliardCount 에 더한 값을 설정한다.
                userData.setRecentGameBilliardCount(billiardCount);

            } else {
                // player 1, 2, 3 이다. / friendDataArrayList

                // [lv/i]friendIndex : player 1, 2, 3 의 friendDataArrayList 의 index 는 0, 1, 2 이다. 즉 index-1
                int friendIndex = index - 1;

                // [lv/C]ArrayList<FriendData> : billiardCount 의 값을 friendData 의 recentGameBilliardCount 에 더한 값을 설정한다.
                friendDataArrayList.get(friendIndex).setRecentGameBilliardCount(billiardCount);

            } // [check 1]

        } // [cycle 1]

    } // End of method [setRecentGameBilliardCountOfPlayer]


    /**
     * [method] [CUF] player 의 gameRecordWin, gameRecordLoss, totalPlayTime, totalCost, recentBilliardCount 를 설정한다.
     *
     * @param playerDataArrayList 등록된 player 의 데이터
     * @param userData            player 0 의 데이터가 있는
     * @param friendDataArrayList player 1, 2, 3 의 데이터가 있는
     * @param winnerId            승리자의 id
     * @param winnerName          승리자의 이름
     * @param playTime            게임 시간
     * @param cost                비용
     * @param billiardCount       billiard 테이블의 count
     */
    private void setDataOfPlayer(ArrayList<PlayerData> playerDataArrayList,
                                 UserData userData,
                                 ArrayList<FriendData> friendDataArrayList,
                                 long winnerId,
                                 String winnerName,
                                 int playTime,
                                 int cost,
                                 long billiardCount) {

        // [cycle 1] : playerDataArrayList 의 size 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {


            // [check 1] : player 0 이다. / userData
            if (index == 0) {

                // [check 2] : 승리이다.
                if ((playerDataArrayList.get(index).getPlayerId() == winnerId) && (playerDataArrayList.get(index).getPlayerName().equals(winnerName))) {

                    // [lv/C]UserData : userData 에서 기존 gameRecordWin 에 +1 하여 저장 / 승리 추가
                    userData.setGameRecordWin(userData.getGameRecordWin() + 1);

                } else {
                    // 패배이다.

                    // [lv/C]UserData : userData 에서 기존 gameRecordLoss 에 +1 하여 저장 / 패배 추가
                    userData.setGameRecordLoss(userData.getGameRecordLoss() + 1);

                } // [check 2]

                // [lv/C]UserData : playTime widget 의 값을 userData 의 totalPlayTime 에 더한 값을 설정한다.
                userData.setTotalPlayTime(userData.getTotalPlayTime() + playTime);

                // [lv/C]UserData : cost widget 의 값을 userData 의 totalCost 에 더한 값을 설정한다.
                userData.setTotalCost(userData.getTotalCost() + cost);

                // [lv/C]UserData : billiardCount 의 값을 userData 의 recentGameBilliardCount 에 더한 값을 설정한다.
                userData.setRecentGameBilliardCount(billiardCount);

            } else {
                // player 1, 2, 3 이다. / friendDataArrayList

                // [lv/i]friendIndex : player 1, 2, 3 의 friendDataArrayList 의 index 는 0, 1, 2 이다. 즉 index-1
                int friendIndex = index - 1;

                // [check 3] : 승리이다.
                if ((playerDataArrayList.get(index).getPlayerId() == winnerId) && (playerDataArrayList.get(index).getPlayerName().equals(winnerName))) {

                    // [lv/C]ArrayList<FriendData> : friendData 에서 기존 gameRecordWin 에 +1 하여 저장 / 승리 추가
                    friendDataArrayList.get(friendIndex).setGameRecordWin(friendDataArrayList.get(friendIndex).getGameRecordWin() + 1);

                } else {
                    // 패배이다.

                    // [lv/C]ArrayList<FriendData> : friendData 에서 기존 gameRecordLoss 에 +1 하여 저장 / 패배 추가
                    friendDataArrayList.get(friendIndex).setGameRecordLoss(friendDataArrayList.get(friendIndex).getGameRecordLoss() + 1);

                } // [check 3]

                // [lv/C]ArrayList<FriendData> : playTime widget 의 값을 friendData 의 totalPlayTime 에 더한 값을 설정한다.
                friendDataArrayList.get(friendIndex).setTotalPlayTime(friendDataArrayList.get(friendIndex).getTotalPlayTime() + playTime);

                // [lv/C]ArrayList<FriendData> : cost widget 의 값을 friendData 의 totalCost 에 더한 값을 설정한다.
                friendDataArrayList.get(friendIndex).setTotalCost(friendDataArrayList.get(friendIndex).getTotalCost() + cost);

                // [lv/C]ArrayList<FriendData> : billiardCount 의 값을 friendData 의 recentGameBilliardCount 에 더한 값을 설정한다.
                friendDataArrayList.get(friendIndex).setRecentGameBilliardCount(billiardCount);

            } // [check 1]

        } // [cycle 1]

    } // End of method [setDataOfPlayer]


    /**
     * [method] [CU] userData 를 user 테이블에 특정 id 의 레코드를 갱신한다.
     *
     * @param userDbManager
     * @param userData
     */
    private void saveDataOfUser(UserDbManager userDbManager, UserData userData) {

        userDbManager.updateContent(
                userData.getId(),
                userData.getGameRecordWin(),
                userData.getGameRecordLoss(),
                userData.getRecentGameBilliardCount(),
                userData.getTotalPlayTime(),
                userData.getTotalCost()
        );

    } // End of method [saveDataOfUser]


    /**
     * [method] [CF] friendData 를 friend 테이블에 틀정 id 의 레코드를 갱신한다.
     */
    private void saveDataFriendList(FriendDbManager friendDbManager, ArrayList<FriendData> friendDataArrayList) {

        // [cycle 1] : friend 의 수 만큼
        for (int index = 0; index < friendDataArrayList.size(); index++) {

            friendDbManager.updateContentById(
                    friendDataArrayList.get(index).getId(),
                    friendDataArrayList.get(index).getGameRecordWin(),
                    friendDataArrayList.get(index).getGameRecordLoss(),
                    friendDataArrayList.get(index).getRecentGameBilliardCount(),
                    friendDataArrayList.get(index).getTotalPlayTime(),
                    friendDataArrayList.get(index).getTotalCost()
            );

        } // [cycle 1]

    } // End of method [saveDataFriendList]


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

    } // End of method [showDialogToCheckWhetherToMoveBDA]


    /**
     * [method] player, billiard widget 을 초기화
     *
     */
    private void initializeOfPlayerAndBilliardWidget(int playerCount) {

        // [cycle 1] : 등록된 player 수 만큼
        for (int index=0; index<playerCount; index++){

            // [iv/C]EditText : playerScore widget 을 초기화
            this.playerScore[index].setText("");

        } // [cycle 1]

        // [iv/C]Spinner : gameMode widget 초기화
        this.gameMode.setSelection(0);

        // [iv/C]Spinner : playerNameList widget 초기화
        this.playerNameList.setSelection(0);

        // [iv/C]EditText : playTime widget 초기화
        this.playTime.setText("");

        // [iv/C]EditText : cost widget 초기화
        this.cost.setText("");

        // [iv/C]Button : input widget 을 클릭하지 못하도록
        this.input.setEnabled(false);
        this.input.setBackgroundResource(R.color.colorWidgetDisable);

    } // End of method [initializeOfPlayerAndBilliardWidget]

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