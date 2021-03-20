package com.skyman.billiarddata;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.SectionManager;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.ProjectBlueDataFormatter;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.projectblue.database.AppDbManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BilliardInputActivity extends AppCompatActivity implements SectionManager.Initializable {

    // constant
    private final String CLASS_NAME_LOG = "[Ac]_BilliardInputActivity";
    private final int DEFAULT_TARGET_SCORE = 21;
    private final int PLAYER_WIDGET_MAX_SIZE = 4;

    // instant variable
    private UserData userData = null;

    // instant variable
    private AppDbManager appDbManager;

    // instant variable
    private ArrayList<FriendData> friendDataArrayList = null;
    private ArrayList<PlayerData> playerDataArrayList = null;

    private BilliardDbManager billiardDbManager = null;
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private PlayerDbManager playerDbManager = null;

    // instance variable : player section widget
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
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "[onCreate] ";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_input);

        // session manager : userData, friendDataArrayList(게임에 참가한 친구)
        this.userData = SessionManager.getUserDataFromIntent(getIntent());
        this.friendDataArrayList = SessionManager.getParticipatedFriendListInGameFromIntent(getIntent());

        // AppDbManager
        initAppDbManager();

        // Widget : connect -> init
        connectWidget();
        initWidget();

    } // End of method [onCreate]


    @Override
    protected void onDestroy() {
        final String METHOD_NAME = "[onDestroy] ";
        super.onDestroy();

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


    @Override
    public void initAppDbManager() {
        appDbManager = new AppDbManager(this);
        appDbManager.connectDb(
                true,
                true,
                true,
                true
        );
    }

    @Override
    public void connectWidget() {

        // [iv/C]TextView : playerName 배열 mapping
        this.playerName[0] = (TextView) findViewById(R.id.billiardInput_player_name_0);
        this.playerName[1] = (TextView) findViewById(R.id.billiardInput_player_name_1);
        this.playerName[2] = (TextView) findViewById(R.id.billiardInput_player_name_2);
        this.playerName[3] = (TextView) findViewById(R.id.billiardInput_player_name_3);

        // [iv/C]Spinner  : playerSpinner 배열 mapping
        this.playerTargetScore[0] = (Spinner) findViewById(R.id.billiardInput_player_target_score_0);
        this.playerTargetScore[1] = (Spinner) findViewById(R.id.billiardInput_player_target_score_1);
        this.playerTargetScore[2] = (Spinner) findViewById(R.id.billiardInput_player_target_score_2);
        this.playerTargetScore[3] = (Spinner) findViewById(R.id.billiardInput_player_target_score_3);

        // [iv/C]EditText : playerScore 배열 mapping
        this.playerScore[0] = (EditText) findViewById(R.id.billiardInput_player_score_0);
        this.playerScore[1] = (EditText) findViewById(R.id.billiardInput_player_score_1);
        this.playerScore[2] = (EditText) findViewById(R.id.billiardInput_player_score_2);
        this.playerScore[3] = (EditText) findViewById(R.id.billiardInput_player_score_3);

        // [iv/C]LinearLayout : playerSection 배열 mapping
        this.playerSection[0] = (LinearLayout) findViewById(R.id.billiardInput_ll_player_section_0);
        this.playerSection[1] = (LinearLayout) findViewById(R.id.billiardInput_ll_player_section_1);
        this.playerSection[2] = (LinearLayout) findViewById(R.id.billiardInput_ll_player_section_2);
        this.playerSection[3] = (LinearLayout) findViewById(R.id.billiardInput_ll_player_section_3);

        // [iv/C]TextView : date mapping / 날짜
        this.date = (TextView) findViewById(R.id.billiardInput_date);

        // [iv/C]TextView : reDate mapping / 날짜 다시
        this.reDate = (TextView) findViewById(R.id.billiardInput_re_date);

        // [iv/C]Spinner : gameMode mapping / 종목
        this.gameMode = (Spinner) findViewById(R.id.billiardInput_sp_game_mode);

        // [iv/C]Spinner : winnerName mapping / 승자
        this.playerNameList = (Spinner) findViewById(R.id.billiardInput_sp_player_name_list);

        // [iv/C]EditText : playTime mapping / 게임 시간
        this.playTime = (EditText) findViewById(R.id.billiardInput_play_time);

        // [iv/C]EditText : cost mapping / 비용
        this.cost = (EditText) findViewById(R.id.billiardInput_cost);


        // [iv/C]Button : input mapping / 입력 버튼
        this.save = (Button) findViewById(R.id.billiardInput_bt_save);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [check 1] : userData, friendDataArrayList 의 내용이 있다.
        if ((this.userData != null) && (friendDataArrayList.size() > 0)) {

            createDBManager();

            // 게임에 참가한 선수 목록을 만들기 
            this.playerDataArrayList = makePlayerDataArrayList(this.userData, this.friendDataArrayList);

//            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "등록 된 player 의 정보를 확인합니다.");
//            DeveloperManager.displayToPlayerData(CLASS_NAME_LOG, this.playerDataArrayList);

            // 게임에 참가한 선수가 있을 때
            // 1. player list section 초기화
            // 2. billiard input section 초기화
            if (playerDataArrayList.size() != 0) {

                // 1. player list section 초기화
                initWidgetOfPlayerListSection(this.playerDataArrayList);

                // 2. billiard input section 초기화
                initWidgetOfBilliardInputSection(this.playerDataArrayList, this.userData);

            } else {
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "userData 와 friendDataArrayList 를 player 등록된 사람이 없습니다.");
            }

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "셋팅할 userData 와 friendDataArrayList 가 없습니다.");
        } // [check 1]


        // [iv/C]TextView : date 의 오늘 날짜를 특정 형태로 만들어서 보여주기
        this.date.setText(ProjectBlueDataFormatter.getFormatOfDate(new Date()));

        // [iv/C]TextView : reDate widget 의 클릭 이벤트를 셋팅한다.
        this.reDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method]setTextToDifferentDate : 다른 날짜로 변경할 수 있는 custom dialog 를 보여준다.
                showDatePickerDialog();

            }
        });

        // Button / save : click listener
        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 입력한 데이터 저장하는 과정 진행
                setClickListenerOfSave();


            }
        });


    }


    // =============================================== DB manager ===============================================

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


    // =============================================== Player 초기내용 등록 ===============================================

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
    private ArrayList<PlayerData> makePlayerDataArrayList(UserData userData, ArrayList<FriendData> friendDataArrayList) {

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


    // =============================================== initWidget : reDate ===============================================

    /**
     *
     */
    private void showDatePickerDialog() {

        // 현재 날짜를 가져오기 위한 Calendar 객체
        Calendar calendar = Calendar.getInstance();

        // 
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        DeveloperManager.displayLog(
                                CLASS_NAME_LOG,
                                "year : " + year + " / month : " + month + " / dayOfMonth : " + dayOfMonth

                        );

                        // DatePicker 에서 선택된 값을 날짜 형식으로 변환
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        date.setText(dateFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();

    } // End of method [showDatePickerDialog]


    // =============================================== initWidget : player section ===============================================

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
    private void initWidgetOfPlayerListSection(ArrayList<PlayerData> playerDataArrayList) {

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
    private void setAdapterOfPlayerTargetScoreSpinner(int playerNumber) {

        // [lv/C]ArrayAdapter : R.array.targetScore 으로 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.targetScore, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위에서 생성한 adapter 를 playerTargetScore spinner 중 index 번째에 연결하기
        this.playerTargetScore[playerNumber].setAdapter(adapter);

    } // End of method [setAdapterOfPlayerTargetScoreSpinner]


    // =============================================== initWidget : billiard input section ===============================================

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
    private void initWidgetOfBilliardInputSection(ArrayList<PlayerData> playerDataArrayList, UserData userData) {

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


    // =============================================== Save Button : init widget ===============================================
    private void setClickListenerOfSave() {
        final String METHOD_NAME = "[setClickListenerOfSave] ";

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

                        // [check 5] : 경기시간(playTime)과 비용(cost)이 0 보다 큰 값만 입력했을 때만
                        if (checkInputRangeOfPlayTimeAndCost()) {
                            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<playTime> 와 <cost> 가 0 보다 큰 값이 입력되었습니다.");

                            // <사용자 확인>
                            new AlertDialog.Builder(this)
                                    .setTitle(R.string.billiardInput_dialog_saveData_title)
                                    .setMessage(R.string.billiardInput_dialog_saveData_message)
                                    .setPositiveButton(R.string.billiardInput_dialog_saveData_positive, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // 데이터베이스에 저장하는 과정 진행
                                            saveData();
                                        }
                                    })
                                    .setNegativeButton(R.string.billiardInput_dialog_saveData_negative, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();


                        } else {
                            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<playTime> 와 <cost> 은 0 보다 큰 값만 입력해야 되요!");
                            // <사용자 알림>
                            Toast.makeText(
                                    this,
                                    R.string.billiardInput_noticeUser_inputDataCheck_playTimeAndCost,
                                    Toast.LENGTH_SHORT
                            ).show();
                        } // [check 5]

                    } else {

                        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<targetScore> 와 <score> 가 같아야지만 승리자요!");
                        // <사용자 알림>
                        Toast.makeText(
                                this,
                                R.string.billiardInput_noticeUser_winnerScoreCheck,
                                Toast.LENGTH_SHORT
                        ).show();

                    } // [check 4]

                } else {

                    // <사용자 알림>
                    DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<billiard widget> 모든 데이터를 입력해줘!");
                    Toast.makeText(
                            this,
                            R.string.billiardInput_noticeUser_billiardDataCheck,
                            Toast.LENGTH_SHORT
                    ).show();

                } // [check 3]

            } else {

                // <사용자 알림>
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<score> 범위에 맞는 점수를 입력해줘!");
                Toast.makeText(
                        this,
                        R.string.billiardInput_noticeUser_scoreRangeCheck,
                        Toast.LENGTH_SHORT
                ).show();

            } // [check 2]

        } else {

            // <사용자 알림>
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "<player widget> 모든 데이터를 입력해줘!");
            Toast.makeText(
                    this,
                    R.string.billiardInput_noticeUser_playerDataCheck,
                    Toast.LENGTH_SHORT
            ).show();

        } // [check 1]

    } // End of method [setClickListenerOfSave]

    /**
     * [method] [C] 모든 값을 입력 받아 billiard 테이블에 데이터를 저장하고,
     * 승리자, 패배자를 구분하여 UserData 와 FriendData 의 'id' 값을 이용하여
     * user, friend 테이블의 내용을 업데이트한다.
     */
    private void saveData() {
        final String METHOD_NAME = "[saveData] ";
        // 0. playerDataArrayList : score 에 playerDataArrayList 이 사용되므로 먼저 설정

        // widget 에서 내용 가져오기
        String date = this.date.getText().toString();
        String gameMode = this.gameMode.getSelectedItem().toString();
        int playerCount = this.playerDataArrayList.size();
        long winnerId = this.playerDataArrayList.get(this.playerNameList.getSelectedItemPosition()).getPlayerId();
        String winnerName = this.playerNameList.getSelectedItem().toString();
        String score = ProjectBlueDataFormatter.getFormatOfScore(getScoreArrayList(playerDataArrayList.size()));
        int playTime = Integer.parseInt(this.playTime.getText().toString());
        int cost = Integer.parseInt(this.cost.getText().toString());

        DeveloperManager.displayLog(
                CLASS_NAME_LOG,
                "====================>>>>>> score : " + score
        );

        // 1. BilliardDbManager : Billiard Data 저장 및 count 값 가져오기
        long billiardCount = saveBilliardData(this.billiardDbManager, date, gameMode, playerCount, winnerId, winnerName, playTime, score, cost);

        // 2-1. userData, friendDataArrayList, playerDataArrayList : 파라미터를 각 객체의 데이터에 반영하여 업데이트하기
        setDataOfObjectsRelatedToThePlayer(winnerId, winnerName, playTime, cost, billiardCount);

        // 2-2. playerDataArrayList : billiardCount, targetScore, score 내용 채우기
        setDataOfTargetScoreAndScore_PlayerDataArrayList();
        setDataOfBilliardCount_PlayerDataArrayList(billiardCount);

        // 3. PlayerDbManager : 모든 플레이어 데이터 업데이트
        savePlayerData(this.playerDbManager, this.playerDataArrayList);

        // 4. UserDbManager : 나의 데이터 업데이트
        updateUserData(this.userDbManager, this.userData);

        // 5. FriendDbManager : 모든 친구 데이터 업데이트
        updateFriendData(this.friendDbManager, this.friendDataArrayList);

        // 6. 이동
        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);
        SessionManager.setUserDataFromIntent(intent, userData);
        finish();
        startActivity(intent);


    } // End of method [saveData]


    // =============================================== Save Button : data Checker ===============================================

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
     */
    private boolean checkWhetherEqualOfTargetScoreAndScore() {

        final String METHOD_NAME = "[checkWhetherEqualOfTargetScoreAndScore] ";

        // [lv/i]winnerPosition : playerNameList spinner 의 winner 의 위치를 받아온다.
        int winnerPosition = this.playerNameList.getSelectedItemPosition();

        // [lv/i]targetScore : winner 의 playerTargetScore 의 값을 int type casting
        int targetScore = Integer.parseInt(this.playerTargetScore[winnerPosition].getSelectedItem().toString());

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


    private boolean checkInputRangeOfPlayTimeAndCost() {
        try {

            int playTimeValue = Integer.parseInt(playTime.getText().toString());
            int costValue = Integer.parseInt(cost.getText().toString());

            if (0 < playTimeValue && 0 < costValue) {
                return true;
            } else {
                return false;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return false;
    }


    // =============================================== Save Button : 스코어 포맷으로 변환 ===============================================

    /**
     * [method] [CP] playerDataArrayList 에 있는 score 값을 ArrayList<String> 으로 담아서 반환한다.
     * playerSection 의 score widget(playerScore) 의 내용을 순서대로
     *
     * @param playerCount 현재 게임에 참가한 플레이어의 인원수
     * @return score 만 담겨있는 ArrayList<String>
     */
    private ArrayList<String> getScoreArrayList(int playerCount) {
        final String METHOD_NAME = "[getScoreArrayList] ";

        ArrayList<String> scoreArrayList = new ArrayList<>();

        try {

            // playerScore(spinner widget) 에서 입력한 숫자를 가져와서
            // scoreArrayList 에 추가한다.
            for (int index = 0; index < playerCount; index++) {

                scoreArrayList.add(playerScore[index].getText().toString());

            }

        } catch (NumberFormatException e) {
            // 숫자만 입력되지 않으면 Exception 발생
            e.printStackTrace();
        }

        return scoreArrayList;
    } // End of method [getScoreArrayList]


    // =============================================== Save Button : 입력된 내용으로 playerDataArrayList 데이터 설정 ===============================================

    /**
     * 전역변수(playerDataArrayList) 의 targetScore, score 데이터를 입력한다.
     *
     * <p>
     * playerDataArrayList.get(4) / targetScore
     * playerDataArrayList.get(5) / score
     * </p>
     */
    private void setDataOfTargetScoreAndScore_PlayerDataArrayList() {
        final String METHOD_NAME = "[setDataOfTargetScoreAndScoreInPlayerDataArrayList] ";

        // [lv/C]String : score 값을 String 으로 임시 저장
        String tempScore = null;

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayList<PlayerData> : playerDataArrayList 의 targetScore 값을 설정
            playerDataArrayList.get(index).setTargetScore(Integer.parseInt(playerTargetScore[index].getSelectedItem().toString()));

            // [lv/C]ArrayList<PlayerData> : playerDataArrayList 의 score 값을 설정
            playerDataArrayList.get(index).setScore(Integer.parseInt(playerScore[index].getText().toString()));

        } // [cycle 1]

    } // End of method [setDataOfTargetScoreAndScoreInPlayerDataArrayList]


    /**
     * 전역변수(playerDataArrayList) 의 billiardCount 데이터를 입력한다.
     *
     * @param billiardCount
     */
    private void setDataOfBilliardCount_PlayerDataArrayList(long billiardCount) {

        // [cycle 1] : 등록된 player 의 수 만큼
        for (int index = 0; index < playerDataArrayList.size(); index++) {

            // [lv/C]ArrayList<PlayerData> : playerDataArrayList 의 billiardCount 값을 설정
            playerDataArrayList.get(index).setBilliardCount(billiardCount);

        } // [cycle 1]

    } // End of method [setDataOfBilliardCountInPlayerDataArrayList]


    // =============================================== Save Button : 입력된 내용으로 userData, friendDataArrayList 데이터 설정 ===============================================

    /**
     * player 와 관련된 객체(userData, playerDataArrayList, friendDataArrayList) 에
     * 파라미터로 받은 데이터에 해당하는 내용을 각 객체에 반영한다.
     *
     * @param winnerId      승리자의 id
     * @param winnerName    승리자의 이름
     * @param playTime      게임 시간
     * @param cost          비용
     * @param billiardCount billiard 테이블의 count
     */
    private void setDataOfObjectsRelatedToThePlayer(
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

    } // End of method [setDataOfObjectsRelatedToThePlayer]


    // =============================================== Save Button : 데이터베이스 저장 ===============================================

    /**
     * Save Database : Billiard
     */
    private long saveBilliardData(BilliardDbManager billiardDbManager, String date, String gameMode, int playerCount, long winnerId, String winnerName, int playTime, String score, int cost) {

        // [lv/l]billiardCount  : billiardDbManager 를 통해서 billiard 테이블에 저장한다. 그리고 그 결과로 넘어온 행의 값(=count) 을 받아온다.
        long billiardCount = billiardDbManager.saveContent(date, gameMode, playerCount, winnerId, winnerName, playTime, score, cost);

        return billiardCount;

    } // End of method [saveBilliardData]


    /**
     * Save Database : Player
     *
     * @param playerDbManager
     * @param playerDataArrayList
     * @return player 테이블에 저정하면 얻는 count 값을 담은 배열
     */
    private long[] savePlayerData(PlayerDbManager playerDbManager, ArrayList<PlayerData> playerDataArrayList) {

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


    // =============================================== Save Button : 데이터베이스 업데이트  ===============================================

    /**
     * Update Database : User
     *
     * @param userDbManager
     * @param userData
     */
    private void updateUserData(UserDbManager userDbManager, UserData userData) {

        userDbManager.updateContent(
                userData.getId(),
                userData.getGameRecordWin(),
                userData.getGameRecordLoss(),
                userData.getRecentGameBilliardCount(),
                userData.getTotalPlayTime(),
                userData.getTotalCost()
        );

    } // End of method [updateUserData]


    /**
     * Update Database : Friend
     */
    private void updateFriendData(FriendDbManager friendDbManager, ArrayList<FriendData> friendDataArrayList) {

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


    // =============================================== Save Button : 후반 작업  ===============================================

    /**
     * 후반작업 1. 입력된 내용 모두 지우기
     */
    private void clearSection(int playerCount) {

        // [cycle 1] : 등록된 player 수 만큼
        for (int index = 0; index < playerCount; index++) {

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
        this.save.setEnabled(false);
        this.save.setBackgroundResource(R.color.colorWidgetDisable);

    } // End of method [clearSection]


    /**
     * 후반작업 2. BilliardDisplayActivity 이동 할 것인지 물어보기
     */
    private void showDialogToCheckWhetherToMoveBDA() {

        // [lv/C]AlertDialog : Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // [lv/C]AlertDialog : 초기값 설정 및 화면보기
        builder.setTitle(R.string.billiardInput_dialog_nextActivityMove_title)
                .setMessage(R.string.billiardInput_dialog_nextActivityMove_message)
                .setPositiveButton(R.string.billiardInput_dialog_nextActivityMove_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]Intent : BilliardDisplayActivity 로 이동하기 위한 intent 생성
                        Intent intent = new Intent(getApplicationContext(), BilliardDisplayActivity.class);

                        // [lv/C]Intent : intent 에 userData 를 담아서 보내기
                        SessionManager.setUserDataFromIntent(intent, userData);

                        // [method]finish : 이 BilliardInputActivity 화면 종료
                        finish();

                        // [method]startActivity : intent 설정 값으로 화면이동
                        startActivity(intent);
                    }
                })
                .show();

    } // End of method [showDialogToCheckWhetherToMoveBDA]


}