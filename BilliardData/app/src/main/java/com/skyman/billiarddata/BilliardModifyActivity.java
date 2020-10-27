package com.skyman.billiarddata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.skyman.billiarddata.developer.DeveloperManager;
import com.skyman.billiarddata.management.billiard.data.BilliardData;
import com.skyman.billiarddata.management.billiard.database.BilliardDbManager;
import com.skyman.billiarddata.management.friend.data.FriendData;
import com.skyman.billiarddata.management.friend.database.FriendDbManager;
import com.skyman.billiarddata.management.player.data.PlayerData;
import com.skyman.billiarddata.management.player.database.PlayerDbManager;
import com.skyman.billiarddata.management.projectblue.data.SessionManager;
import com.skyman.billiarddata.management.projectblue.database.ModifyManager;
import com.skyman.billiarddata.management.user.data.UserData;
import com.skyman.billiarddata.management.user.database.UserDbManager;

import java.util.ArrayList;

public class BilliardModifyActivity extends AppCompatActivity {

    // constant
    private final String CLASS_NAME_LOG = "[Ac]_BilliardModifyActivity";

    // instance variable
    private TextView count;

    private Spinner dateYear;
    private Spinner dateMonth;
    private Spinner dateDay;

    private Spinner gameMode;

    private TextView playerCount;
    private TextView winnerId;
    private Spinner winnerName;

    private EditText playTime;
    private LinearLayout playerSection[];
    private TextView playerName[];
    private Spinner targetScore[];
    private EditText score[];
    private EditText cost;

    private Button modify;
    private Button cancel;

    // instance variable
    private UserDbManager userDbManager = null;
    private FriendDbManager friendDbManager = null;
    private PlayerDbManager playerDbManager = null;
    private BilliardDbManager billiardDbManager = null;

    // instance variable
    private UserData userData = null;
    private ArrayList<FriendData> friendDataArrayList = null;
    private ArrayList<PlayerData> playerDataArrayList = null;
    private BilliardData billiardData = null;

    // instance variable
    private UserData changeUserData = new UserData();
    private ArrayList<FriendData> changeFriendDataArrayList = new ArrayList<>();
    private ArrayList<PlayerData> changePlayerDataArrayList = new ArrayList<>();
    private BilliardData changeBilliardData = new BilliardData();

    // instance variable
    private long initWinnerId;
    private String initWinnerName;
    private int initWinnerPlayerIndex;
    private int initPlayTime;
    private int initScore[];
    private int initCost;

    // instance variable
    private ModifyManager modifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billiard_modify);

        final String METHOD_NAME = "[onCreate] ";

        // [lv/C]Intent : BilliardDisplayActivity 에서 전달 된 Intent 가져오기
        Intent intent = getIntent();

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "intent 를 통해 가져온 값 확인하기");
        // [iv/C]BilliardData : 선택한 게임의 billiardData 가져오기
        this.billiardData = SessionManager.getBilliardDataInIntent(intent);

        // [iv/C]UserData : 나의 정보를 가져오기
        this.userData = SessionManager.getUserDataInIntent(intent);

        // [iv/C]ArrayList<FriendData> : 이 게임에 참가한 player 중 friend 해당하는 데이터를 가져오기
        this.friendDataArrayList = SessionManager.getFriendPlayerListInIntent(intent);

        // [iv/C]ArrayList<PlayerData> : 이 게임에 참가한 모든 player 의 데이터를 가져오기
        this.playerDataArrayList = SessionManager.getPlayerListInIntent(intent);

        // [check 1] : intent 로 가져온 데이터 입력받았다.
        if ( (this.billiardData != null) && (this.userData != null) && (this.friendDataArrayList.size() != 0) && (this.playerDataArrayList.size() != 0) ) {

            // [method] : widget mapping
            mappingOfWidget();

            // [method] : modifyManager 생성 및 초기 설정
            setModifyManager();

            this.modifyManager.setRangeError();

        } else {

        } // [check 1]


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        final String METHOD_NAME = "[onDestroy] ";

        // [check 1] : user 테이블 메니저가 생성되었다.
        if (this.userDbManager != null) {

            // [iv/C]UserDbManager : user 테이블 메니저를 종료한다.
            this.userDbManager.closeDb();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "user 테이블 메니저가 생성되지 않았습니다.");
        } // [check 1]

        // [check 2] : friend 테이블 메니저가 생성되었다.
        if (this.friendDbManager != null) {

            // [iv/C]FriendDbManager : friend 테이블 메니저를 종료한다.

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "friend 테이블 메니저가 생성되지 않았습니다.");
        } // [check 2]

        // [check 3] : billiard 테이블 메니저가 생성되었다.
        if (this.billiardDbManager != null) {

            // [iv/C]BilliardDbManager : billiard 테이블 메니저를 종료한다.
            this.billiardDbManager.closeDb();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "billiard 테이블 메니저가 생성되지 않았습니다.");
        } // [check 3]

        // [check 4] : player 테이블 메니저가 생성되었다.
        if (this.playerDbManager != null) {

            // [iv/C]PlayerDbManager : player 테이블 메니저를 종료한다.
            this.playerDbManager.closeDb();

        } else {
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "player 테이블 메니저가 생성되지 않았습니다.");
        } // [check 4]

    } // End of method [onDestroy]


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
     * [method] [A] custom_dialog_billiard_modify layout 의 widget 들을 mapping 한다.
     */
    public void mappingOfWidget() {

        // [iv/C]TextView : count mapping
        this.count = (TextView) findViewById(R.id.billiard_modify_count);

        // [iv/C]Spinner : dateYear mapping
        this.dateYear = (Spinner) findViewById(R.id.billiard_modify_sp_date_year);

        // [iv/C]Spinner : dateMonth mapping
        this.dateMonth = (Spinner) findViewById(R.id.billiard_modify_sp_date_month);

        // [iv/C]Spinner : dateDay mapping
        this.dateDay = (Spinner) findViewById(R.id.billiard_modify_sp_date_day);

        // [iv/C]Spinner : gameMode mapping
        this.gameMode = (Spinner) findViewById(R.id.billiard_modify_sp_speciality);

        // [iv/C]TextView : playerCount mapping
        this.playerCount = (TextView) findViewById(R.id.billiard_modify_player_count);

        // [iv/C]TextView : playerCount mapping
        this.winnerId = (TextView) findViewById(R.id.billiard_modify_winner_id);

        // [iv/C]Spinner : winner mapping
        this.winnerName = (Spinner) findViewById(R.id.billiard_modify_winner_name);

        // [iv/C]EditText : playTime mapping
        this.playTime = (EditText) findViewById(R.id.billiard_modify_play_time);

        // [iv/C]LinearLayout : playerSection 배열 생생
        this.playerSection = new LinearLayout[4];

        // [iv/C]LinearLayout : playerSection mapping
        this.playerSection[0] = (LinearLayout) findViewById(R.id.billiard_modify_player_section_0);
        this.playerSection[1] = (LinearLayout) findViewById(R.id.billiard_modify_player_section_1);
        this.playerSection[2] = (LinearLayout) findViewById(R.id.billiard_modify_player_section_2);
        this.playerSection[3] = (LinearLayout) findViewById(R.id.billiard_modify_player_section_3);

        // [iv/C]TextView : playerName 배열 생성
        this.playerName = new TextView[4];

        // [iv/C]TextView : playerName mapping
        this.playerName[0] = (TextView) findViewById(R.id.billiard_modify_player_name_0);
        this.playerName[1] = (TextView) findViewById(R.id.billiard_modify_player_name_1);
        this.playerName[2] = (TextView) findViewById(R.id.billiard_modify_player_name_2);
        this.playerName[3] = (TextView) findViewById(R.id.billiard_modify_player_name_3);

        // [iv/C]Spinner : targetScore 배열 생성
        this.targetScore = new Spinner[4];

        // [iv/C]Spinner : targetScore mapping
        this.targetScore[0] = (Spinner) findViewById(R.id.billiard_modify_target_score_0);
        this.targetScore[1] = (Spinner) findViewById(R.id.billiard_modify_target_score_1);
        this.targetScore[2] = (Spinner) findViewById(R.id.billiard_modify_target_score_2);
        this.targetScore[3] = (Spinner) findViewById(R.id.billiard_modify_target_score_3);

        // [iv/C]EditText : score 배열 생성
        this.score = new EditText[4];

        // [iv/C]EditText : score mapping
        this.score[0] = (EditText) findViewById(R.id.billiard_modify_score_0);
        this.score[1] = (EditText) findViewById(R.id.billiard_modify_score_1);
        this.score[2] = (EditText) findViewById(R.id.billiard_modify_score_2);
        this.score[3] = (EditText) findViewById(R.id.billiard_modify_score_3);

        // [iv/C]EditText : cost mapping
        this.cost = (EditText) findViewById(R.id.billiard_modify_cost);

        // [iv/C]Button : modify mapping
        this.modify = (Button) findViewById(R.id.billiard_modify_bt_modify);

        // [iv/C]Button : cancel mapping
        this.cancel = (Button) findViewById(R.id.billiard_modify_bt_cancel);

    } // End of method [mappingOfWidget]


    /**
     * [method] [A0] init variable 설정하기
     *
     * <p>
     * 1. winnerId
     * 2. winnerName
     * 3. playTime
     * 4. score
     * 5. cost
     * </p>
     */
    private void setInitData(BilliardData billiardData, ArrayList<PlayerData> playerDataArrayList) {

        final String METHOD_NAME = "[setInitData] ";

        // [iv/l]initWinnerId : billiardData 의 처음 winnerId 값 저장
        this.initWinnerId = billiardData.getWinnerId();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winnerId 의 값 = " + this.initWinnerId);

        // [iv/C]String : initWinnerName / billiardData 의 처음 winnerName 값 저장
        this.initWinnerName = billiardData.getWinnerName();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winnerName 의 값 = " + this.initWinnerName);

        // [iv/i]initPlayTime : billiardData 의 처음 winnerName 값 저장
        this.initPlayTime = billiardData.getPlayTime();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 playTime 의 값 = " + this.initPlayTime);

        // [iv/i]initScore : playerDataArrayList 에 등록되어 있는 player 수 만큼 배열 객체 생성
        this.initScore = new int[playerDataArrayList.size()];

        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "playerDataArrayList 로 구한 초기 score 들을 확입합니다.");
        // [cycle 1] : player 의 수 만큼
        for (int playerIndex = 0; playerIndex < playerDataArrayList.size(); playerIndex++) {


            this.initScore[playerIndex] = playerDataArrayList.get(playerIndex).getScore();
            DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + playerIndex + "번째 player 의 score = " + this.initScore[playerIndex]);

            // [check 1] : 승리한 사람인가?
            if ((billiardData.getWinnerId() == playerDataArrayList.get(playerIndex).getPlayerId()) && (billiardData.getWinnerName().equals(playerDataArrayList.get(playerIndex).getPlayerName()))) {

                // [iv/i]initWinnerPlayerIndex : 승리한 player 의 index 를 저장
                this.initWinnerPlayerIndex = playerIndex;
                DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 winner 가 playerDataArrayList 에 몇 번째 player 인가? = " + this.initWinnerPlayerIndex);

            } // [check 1]

        } // [cycle 1]

        // [iv/i]initCost : billiardData 의 처음 winnerName 값 저장
        this.initCost = billiardData.getCost();
        DeveloperManager.displayLog(CLASS_NAME_LOG, METHOD_NAME + "초기 cost 의 값 = " + this.initCost);

    } // End of method [setInitData]


    /**
     * [method] ModifyManager 를 생성하고, billiardData, userData, friendDataArrayList, playerDataArrayList 를 설정하기
     *
     */
    private void setModifyManager() {

        // [iv/C]ModifyManager : 참가한 player 수로 객체 생성
        this.modifyManager = new ModifyManager(this.playerDataArrayList.size());

        // [iv/C]ModifyManager : billiardData 입력
        this.modifyManager.setBilliardData(this.billiardData);

        // [iv/C]ModifyManager : userData 입력
        this.modifyManager.setUserData(this.userData);

        // [iv/C]ModifyManager : friendDataArrayList 입력
        this.modifyManager.setFriendDataArrayList(this.friendDataArrayList);

        // [iv/C]ModifyManager : playerDataArrayList 입력
        this.modifyManager.setPlayerDataArrayList(this.playerDataArrayList);

        // [iv/C]ModifyManager : init 변수들 초기값 설정
        this.modifyManager.setInitData();

        // [iv/C]ModifyManager : 초기 화 완료 되었는지 세팅
        this.modifyManager.setCompleteSetting();

    } // End of method [setModifyManager]

}